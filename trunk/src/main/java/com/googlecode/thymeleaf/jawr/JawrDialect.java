package com.googlecode.thymeleaf.jawr;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractXHTMLEnabledDialect;
import org.thymeleaf.doctype.translation.IDocTypeTranslation;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.spring3.dialect.SpringStandardDialect;

/**
 * 
 * @author Miloš Milivojević
 * 
 *         Jawr dialect class that defines it's prefix as {@code "jawr"} and
 *         uses tag removing versions of attribute processors
 * 
 */
public class JawrDialect extends AbstractXHTMLEnabledDialect {

	public static final String PREFIX = "jawr";

	public static final boolean LENIENT = true;

	@Override
	public final String getPrefix() {
		return PREFIX;
	}

	@Override
	public final boolean isLenient() {
		return LENIENT;
	}

	@Override
	public final Set<IDocTypeTranslation> getDocTypeTranslations() {
		return SpringStandardDialect.SPRING3_DOC_TYPE_TRANSLATIONS;
	}

	@Override
	public final Set<IProcessor> getProcessors() {
		final Set<IProcessor> processors = new HashSet<IProcessor>();
		processors.add(new JawrTagRemovingAttrProcessor(new JawrScriptAttrProcessor()));
		processors.add(new JawrTagRemovingAttrProcessor(new JawrStyleAttrProcessor()));
		return processors;
	}

}
