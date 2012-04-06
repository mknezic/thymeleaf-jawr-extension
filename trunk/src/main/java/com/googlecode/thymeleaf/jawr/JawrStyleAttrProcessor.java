package com.googlecode.thymeleaf.jawr;

import net.jawr.web.JawrConstant;
import net.jawr.web.resource.bundle.handler.ResourceBundlesHandler;
import net.jawr.web.resource.bundle.renderer.BundleRenderer;
import net.jawr.web.resource.bundle.renderer.CSSHTMLBundleLinkRenderer;

import org.thymeleaf.context.WebContext;
import org.thymeleaf.processor.AttributeNameProcessorMatcher;

/**
 * 
 * @author Miloš Milivojević
 * 
 *         Jawr attribute processor used for rendering the CSS bundle imports;
 *         applied only to style attributes inside of link tags
 */
public class JawrStyleAttrProcessor extends AbstractJawrAttrProcessor {

	private static final String MEDIA = "all";

	public JawrStyleAttrProcessor() {
		super(new AttributeNameProcessorMatcher("style", "link"));
	}
	
	@Override
	protected final BundleRenderer createRenderer(final WebContext p_pageContext) {
		final ResourceBundlesHandler rsHandler = getResourceBundleHandlerFromContext(p_pageContext, JawrConstant.CSS_CONTEXT_ATTRIBUTE);
		return new CSSHTMLBundleLinkRenderer(rsHandler, false, MEDIA, false, false, null);
	}

}
