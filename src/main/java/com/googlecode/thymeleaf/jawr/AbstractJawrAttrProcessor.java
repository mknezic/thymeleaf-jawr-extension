package com.googlecode.thymeleaf.jawr;

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
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.IAttributeNameProcessorMatcher;
import org.thymeleaf.processor.IProcessorMatcher;
import org.thymeleaf.processor.attr.AbstractUnescapedTextChildModifierAttrProcessor;
import org.thymeleaf.spring3.naming.SpringContextVariableNames;

/**
 * 
 * @author Miloš Milivojević
 * 
 *         An abstract parent class that uses a Jawr renderer to get the
 *         unescaped text that will be used to render a tag's content; because
 *         this processor writes the renderer-provided text inside of it's
 *         enclosing tag, the result will often be malformed HTML so it is
 *         therefore suggested to use an instance of
 *         {@link JawrTagRemovingAttrProcessor} to wrap your attribute
 *         processors
 * 
 */
public abstract class AbstractJawrAttrProcessor extends AbstractUnescapedTextChildModifierAttrProcessor {

	public AbstractJawrAttrProcessor(String attributeName) {
		super(attributeName);
	}
	
	public AbstractJawrAttrProcessor(IAttributeNameProcessorMatcher matcher) {
		super(matcher);
	}

	private static final int PRECEDENCE = 900;

	protected abstract BundleRenderer createRenderer(final WebContext p_pageContext);

	@Override
	public int getPrecedence() {
		return PRECEDENCE;
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
	protected final String getText(final Arguments p_arguments, final Element p_element, final String p_attributeName) {

		String result = "";

		try {
			final WebContext context = (WebContext) p_arguments.getContext();
			final String attributeValue = p_element.getAttributeValue(p_attributeName);
			result = getRenderedString(attributeValue, context);
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
