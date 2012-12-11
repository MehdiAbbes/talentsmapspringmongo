package com.mehdi.abbes.tm.domain;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;

@Persistent
public class SkillDocument {

	@NotNull
	private String category;

	@NotNull
	private String concept;

	private Set<ToolDocument> tools = new HashSet<ToolDocument>();

	private Integer score;

	public String getCategory() {
		return category;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(final String concept) {
		this.concept = concept;
	}

	public Set<ToolDocument> getTools() {
		return tools;
	}

	public void setTools(final Set<ToolDocument> tools) {
		this.tools = tools;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(final Integer score) {
		this.score = score;
	}

	@Id
	private BigInteger id;

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
