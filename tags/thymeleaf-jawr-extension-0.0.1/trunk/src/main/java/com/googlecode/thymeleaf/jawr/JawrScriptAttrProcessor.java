package com.googlecode.thymeleaf.jawr;

import net.jawr.web.JawrConstant;
import net.jawr.web.resource.bundle.handler.ResourceBundlesHandler;
import net.jawr.web.resource.bundle.renderer.BundleRenderer;
import net.jawr.web.resource.bundle.renderer.JavascriptHTMLBundleLinkRenderer;

import org.thymeleaf.context.WebContext;
import org.thymeleaf.processor.AttributeNameProcessorMatcher;

/**
 * 
 * @author Miloš Milivojević
 * 
 *         Jawr attribute processor used for rendering the javascript bundle
 *         imports; applied only to script attributes inside of script tags
 */
public class JawrScriptAttrProcessor extends AbstractJawrAttrProcessor {
    
	public static final String ATTR_NAME = "script";
	
	public JawrScriptAttrProcessor() {
		super(new AttributeNameProcessorMatcher(ATTR_NAME, ATTR_NAME));
	}

	@Override
	protected final BundleRenderer createRenderer(final WebContext p_pageContext) {
		final ResourceBundlesHandler rsHandler = getResourceBundleHandlerFromContext(p_pageContext, JawrConstant.JS_CONTEXT_ATTRIBUTE);
		return new JavascriptHTMLBundleLinkRenderer(rsHandler, false);
	}

}
