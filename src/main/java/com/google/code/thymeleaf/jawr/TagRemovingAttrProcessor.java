package com.google.code.thymeleaf.jawr;

import java.util.Set;

import org.thymeleaf.Arguments;
import org.thymeleaf.processor.applicability.AttrApplicability;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;
import org.thymeleaf.processor.attr.AttrProcessResult;
import org.thymeleaf.templateresolver.TemplateResolution;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * @author Miloš Milivojević
 * 
 *         A wrapper class for Jawr processors that removes the attribute's
 *         enclosing tag after using the provided Jawr processor to render the
 *         tag's content
 * 
 */
public class TagRemovingAttrProcessor extends AbstractAttrProcessor {
	private final AbstractJawrAttrProcessor processor;

	public TagRemovingAttrProcessor(final AbstractJawrAttrProcessor p_processor) {
		processor = p_processor;
	}

	@Override
	public Set<AttrApplicability> getAttributeApplicabilities() {
		return processor.getAttributeApplicabilities();
	}

	@Override
	public Integer getPrecedence() {
		return Integer.MAX_VALUE;
	}

	@Override
	public AttrProcessResult process(final Arguments p_arguments, final TemplateResolution p_templateResolution, final Document p_document, final Element p_element,
			final Attr p_attribute) {
		processor.process(p_arguments, p_templateResolution, p_document, p_element, p_attribute);
		return AttrProcessResult.REMOVE_TAG;
	}

}
