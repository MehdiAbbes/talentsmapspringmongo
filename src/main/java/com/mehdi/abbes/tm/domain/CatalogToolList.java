package com.mehdi.abbes.tm.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.mehdi.abbes.tm.rest.CatalogToolResource;

@XmlRootElement(name = "ToolList")
public class CatalogToolList {

	private List<CatalogToolResource> list = new ArrayList<CatalogToolResource>();

	@XmlElement(name = "Tool")
	public List<CatalogToolResource> getList() {
		return list;
	}

	public void setList(final List<CatalogToolResource> list) {
		this.list = list;
	}

}
