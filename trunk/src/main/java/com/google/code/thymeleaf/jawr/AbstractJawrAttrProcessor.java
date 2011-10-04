package com.google.code.thymeleaf.jawr;



import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import net.jawr.web.resource.bundle.handler.ResourceBundlesHandler;
import net.jawr.web.resource.bundle.renderer.BundleRenderer;
import net.jawr.web.resource.bundle.renderer.BundleRendererContext;
import net.jawr.web.servlet.RendererRequestUtils;

import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestUtil;
import org.thymeleaf.Arguments;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.processor.attr.AbstractUnescapedTextChildModifierAttrProcessor;
import org.thymeleaf.spring3.naming.SpringContextVariableNames;
import org.thymeleaf.templateresolver.TemplateResolution;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AbstractJawrAttrProcessor extends AbstractUnescapedTextChildModifierAttrProcessor {

	private static final int PRECEDENCE = 1000;

	protected abstract BundleRenderer createRenderer(final WebContext p_pageContext);

	@Override
	public Integer getPrecedence() {
		return Integer.valueOf(PRECEDENCE);
	}

	protected ResourceBundlesHandler getResourceBundleHandlerFromContext(final WebContext p_pageContext, final String p_attributeName) {
		if (null == p_pageContext.getServletContext().getAttribute(p_attributeName)) {
			throw new IllegalStateException("ResourceBundlesHandler not present in servlet context. Initialization of Jawr either failed or never occurred.");
		}

		return (ResourceBundlesHandler) p_pageContext.getServletContext().getAttribute(p_attributeName);
	}

	protected String getRenderedString(final String p_attributeValue, final WebContext p_context) throws IOException {
		final BundleRenderer renderer = createRenderer(p_context);
		final BundleRendererContext ctx = getRendererContext(p_context, renderer);
		final StringWriter out = new StringWriter();

		renderer.renderBundleLinks(p_attributeValue, ctx, out);
		out.flush();
		return out.toString();
	}

	@Override
	protected final String getText(final Arguments p_arguments, final TemplateResolution p_templateResolution, final Document p_document, final Element p_element,
			final Attr p_attribute, final String p_attributeName, final String p_attributeValue) {

		String result = "";

		try {
			final WebContext context = (WebContext) p_arguments.getContext();
			result = getRenderedString(p_attributeValue, context);
		} catch (final IOException e) {
		}

		return result;

	}

	private BundleRendererContext getRendererContext(final WebContext p_pageContext, final BundleRenderer p_renderer) {
		final RequestContext requestContext = (RequestContext) p_pageContext.getVariables().get(SpringContextVariableNames.SPRING_REQUEST_CONTEXT);
		final HttpServletRequest request = RequestUtil.getRequestFromContext(requestContext);
		return RendererRequestUtils.getBundleRendererContext(request, p_renderer);
	}
}
