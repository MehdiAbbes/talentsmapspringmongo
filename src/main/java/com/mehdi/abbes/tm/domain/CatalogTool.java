package com.mehdi.abbes.tm.domain;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Persistent;
import org.springframework.hateoas.Identifiable;

@XmlRootElement
@Persistent
public class CatalogTool implements Identifiable<String> {

	private String id;

	private String toolLabel;

	private String toolDescription;

	private int score;

	private boolean modified;

	public String getToolLabel() {
		return toolLabel;
	}

	public void setToolLabel(final String toolLabel) {
		this.toolLabel = toolLabel;
	}

	public String getToolDescription() {
		return toolDescription;
	}

	public void setToolDescription(final String toolDescription) {
		this.toolDescription = toolDescription;
	}

	public int getScore() {
		return score;
	}

	public void setScore(final int score) {
		this.score = score;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(final boolean modified) {
		this.modified = modified;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CatalogTool other = (CatalogTool) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
