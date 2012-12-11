package com.mehdi.abbes.tm.domain;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;

@Persistent
public class ProfileDocument {

	@NotNull
	@Size(min = 2)
	private String firstname;

	@NotNull
	@Size(min = 2)
	private String lastname;

	@NotNull
	@Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-z]+")
	private String email;

	@Max(30L)
	private int experienceYears;

	private Set<com.mehdi.abbes.tm.domain.ProfileDocument> subordinates = new HashSet<com.mehdi.abbes.tm.domain.ProfileDocument>();

	private com.mehdi.abbes.tm.domain.ProfileDocument manager;

	private Set<SkillDocument> skills = new HashSet<SkillDocument>();

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

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(final String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(final String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public int getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(final int experienceYears) {
		this.experienceYears = experienceYears;
	}

	public Set<ProfileDocument> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(final Set<ProfileDocument> subordinates) {
		this.subordinates = subordinates;
	}

	public ProfileDocument getManager() {
		return manager;
	}

	public void setManager(final ProfileDocument manager) {
		this.manager = manager;
	}

	public Set<SkillDocument> getSkills() {
		return skills;
	}

	public void setSkills(final Set<SkillDocument> skills) {
		this.skills = skills;
	}
}
