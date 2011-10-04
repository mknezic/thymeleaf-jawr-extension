package com.google.code.thymeleaf.jawr;

import java.util.HashSet;
import java.util.Set;

import net.jawr.web.JawrConstant;
import net.jawr.web.resource.bundle.handler.ResourceBundlesHandler;
import net.jawr.web.resource.bundle.renderer.BundleRenderer;
import net.jawr.web.resource.bundle.renderer.CSSHTMLBundleLinkRenderer;

import org.thymeleaf.context.WebContext;
import org.thymeleaf.processor.applicability.AttrApplicability;
import org.thymeleaf.processor.applicability.TagNameApplicabilityFilter;

public class JawrStyleAttrProcessor extends AbstractJawrAttrProcessor {

	private static final String MEDIA = "all";

	public static final AttrApplicability LINK_TAG_SRC_ATTR_APPLICABILITY = new AttrApplicability("style", new TagNameApplicabilityFilter("link"));

	@Override
	public final Set<AttrApplicability> getAttributeApplicabilities() {
		final Set<AttrApplicability> set = new HashSet<AttrApplicability>(1);
		set.add(LINK_TAG_SRC_ATTR_APPLICABILITY);
		return set;
	}

	@Override
	protected final BundleRenderer createRenderer(final WebContext p_pageContext) {
		final ResourceBundlesHandler rsHandler = getResourceBundleHandlerFromContext(p_pageContext, JawrConstant.CSS_CONTEXT_ATTRIBUTE);
		return new CSSHTMLBundleLinkRenderer(rsHandler, false, MEDIA, false, false, null);
	}

}
