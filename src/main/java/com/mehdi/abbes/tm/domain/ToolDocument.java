package com.mehdi.abbes.tm.domain;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;

@Persistent
public class ToolDocument {

	@NotNull
	private String tool;

	private Integer score;

	public String getTool() {
		return tool;
	}

	public void setTool(final String tool) {
		this.tool = tool;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(final Integer score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Id
	private BigInteger id;

	public BigInteger getId() {
		return id;
	}

	public void setId(final BigInteger id) {
		this.id = id;
	}
}
