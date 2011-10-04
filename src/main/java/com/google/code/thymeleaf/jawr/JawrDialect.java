package com.google.code.thymeleaf.jawr;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractXHTMLEnabledDialect;
import org.thymeleaf.doctype.translation.IDocTypeTranslation;
import org.thymeleaf.processor.attr.IAttrProcessor;
import org.thymeleaf.spring3.dialect.SpringStandardDialect;

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
	public final Set<IAttrProcessor> getAttrProcessors() {
		final Set<IAttrProcessor> processors = new HashSet<IAttrProcessor>();
		processors.add(new JawrTagRemovingAttrProcessor(new JawrScriptAttrProcessor()));
		processors.add(new JawrTagRemovingAttrProcessor(new JawrStyleAttrProcessor()));
		return processors;
	}

}
