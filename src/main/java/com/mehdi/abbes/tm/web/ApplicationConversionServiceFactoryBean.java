package com.mehdi.abbes.tm.web;

import com.mehdi.abbes.tm.domain.ProfileDocument;
import com.mehdi.abbes.tm.domain.SkillDocument;
import com.mehdi.abbes.tm.domain.ToolDocument;
import com.mehdi.abbes.tm.repository.ProfileRepository;
import com.mehdi.abbes.tm.repository.SkillRepository;
import com.mehdi.abbes.tm.repository.ToolRepository;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	@Autowired
    ProfileRepository profileRepository;

	@Autowired
    SkillRepository skillRepository;

	@Autowired
    ToolRepository toolRepository;

	public Converter<ProfileDocument, String> getProfileDocumentToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.mehdi.abbes.tm.domain.ProfileDocument, java.lang.String>() {
            public String convert(ProfileDocument profileDocument) {
                return new StringBuilder().append(profileDocument.getFirstname()).append(' ').append(profileDocument.getLastname()).append(' ').append(profileDocument.getEmail()).append(' ').append(profileDocument.getExperienceYears()).toString();
            }
        };
    }

	public Converter<BigInteger, ProfileDocument> getIdToProfileDocumentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.mehdi.abbes.tm.domain.ProfileDocument>() {
            public com.mehdi.abbes.tm.domain.ProfileDocument convert(java.math.BigInteger id) {
                return profileRepository.findOne(id);
            }
        };
    }

	public Converter<String, ProfileDocument> getStringToProfileDocumentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.mehdi.abbes.tm.domain.ProfileDocument>() {
            public com.mehdi.abbes.tm.domain.ProfileDocument convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), ProfileDocument.class);
            }
        };
    }

	public Converter<SkillDocument, String> getSkillDocumentToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.mehdi.abbes.tm.domain.SkillDocument, java.lang.String>() {
            public String convert(SkillDocument skillDocument) {
                return new StringBuilder().append(skillDocument.getCategory()).append(' ').append(skillDocument.getConcept()).append(' ').append(skillDocument.getScore()).toString();
            }
        };
    }

	public Converter<BigInteger, SkillDocument> getIdToSkillDocumentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.mehdi.abbes.tm.domain.SkillDocument>() {
            public com.mehdi.abbes.tm.domain.SkillDocument convert(java.math.BigInteger id) {
                return skillRepository.findOne(id);
            }
        };
    }

	public Converter<String, SkillDocument> getStringToSkillDocumentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.mehdi.abbes.tm.domain.SkillDocument>() {
            public com.mehdi.abbes.tm.domain.SkillDocument convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), SkillDocument.class);
            }
        };
    }

	public Converter<ToolDocument, String> getToolDocumentToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.mehdi.abbes.tm.domain.ToolDocument, java.lang.String>() {
            public String convert(ToolDocument toolDocument) {
                return new StringBuilder().append(toolDocument.getTool()).append(' ').append(toolDocument.getScore()).toString();
            }
        };
    }

	public Converter<BigInteger, ToolDocument> getIdToToolDocumentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.mehdi.abbes.tm.domain.ToolDocument>() {
            public com.mehdi.abbes.tm.domain.ToolDocument convert(java.math.BigInteger id) {
                return toolRepository.findOne(id);
            }
        };
    }

	public Converter<String, ToolDocument> getStringToToolDocumentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.mehdi.abbes.tm.domain.ToolDocument>() {
            public com.mehdi.abbes.tm.domain.ToolDocument convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), ToolDocument.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getProfileDocumentToStringConverter());
        registry.addConverter(getIdToProfileDocumentConverter());
        registry.addConverter(getStringToProfileDocumentConverter());
        registry.addConverter(getSkillDocumentToStringConverter());
        registry.addConverter(getIdToSkillDocumentConverter());
        registry.addConverter(getStringToSkillDocumentConverter());
        registry.addConverter(getToolDocumentToStringConverter());
        registry.addConverter(getIdToToolDocumentConverter());
        registry.addConverter(getStringToToolDocumentConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
