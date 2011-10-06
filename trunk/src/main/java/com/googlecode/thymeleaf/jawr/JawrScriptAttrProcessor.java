package com.googlecode.thymeleaf.jawr;

import java.util.HashSet;
import java.util.Set;

import net.jawr.web.JawrConstant;
import net.jawr.web.resource.bundle.handler.ResourceBundlesHandler;
import net.jawr.web.resource.bundle.renderer.BundleRenderer;
import net.jawr.web.resource.bundle.renderer.JavascriptHTMLBundleLinkRenderer;

import org.thymeleaf.context.WebContext;
import org.thymeleaf.processor.applicability.AttrApplicability;
import org.thymeleaf.processor.applicability.TagNameApplicabilityFilter;

/**
 * 
 * @author Miloš Milivojević
 * 
 *         Jawr attribute processor used for rendering the javascript bundle
 *         imports; applied only to script attributes inside of script tags
 */
public class JawrScriptAttrProcessor extends AbstractJawrAttrProcessor {

	public static final AttrApplicability SOURCE_TAG_SRC_ATTR_APPLICABILITY = new AttrApplicability("script", new TagNameApplicabilityFilter("script"));

	@Override
	public final Set<AttrApplicability> getAttributeApplicabilities() {
		final Set<AttrApplicability> set = new HashSet<AttrApplicability>(1);
		set.add(SOURCE_TAG_SRC_ATTR_APPLICABILITY);
		return set;
	}

	@Override
	protected final BundleRenderer createRenderer(final WebContext p_pageContext) {
		final ResourceBundlesHandler rsHandler = getResourceBundleHandlerFromContext(p_pageContext, JawrConstant.JS_CONTEXT_ATTRIBUTE);
		return new JavascriptHTMLBundleLinkRenderer(rsHandler, false);
	}

}
