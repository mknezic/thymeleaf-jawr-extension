package com.googlecode.thymeleaf.jawr;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.NestableNode;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.IAttributeNameProcessorMatcher;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;

/**
 * 
 * @author Miloš Milivojević
 * 
 *         A wrapper class for Jawr processors that removes the attribute's
 *         enclosing tag after using the provided Jawr processor to render the
 *         tag's content
 * 
 */
public class JawrTagRemovingAttrProcessor extends AbstractAttrProcessor {
	private final AbstractJawrAttrProcessor processor;

	public JawrTagRemovingAttrProcessor(final AbstractJawrAttrProcessor p_processor) {
		super((IAttributeNameProcessorMatcher) p_processor.getMatcher());
		processor = p_processor;
	}

	@Override
	public int getPrecedence() {
		return Integer.MAX_VALUE;
	}
	
	@Override
	public ProcessorResult processAttribute(final Arguments p_arguments, final Element p_element, final String p_attributeName) {
		processor.processAttribute(p_arguments, p_element, p_attributeName);
		final NestableNode parent = p_element.getParent();
		
		for(final Node child : p_element.getChildren()) {
			p_element.removeChild(child);
			parent.insertBefore(p_element, child);
		}
		
		parent.removeChild(p_element);
		return ProcessorResult.OK;
	}

}
