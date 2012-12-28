package com.mehdi.abbes.tm.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "toolsList")
public class CatalogToolList {

	private List<CatalogTool> list = new ArrayList<CatalogTool>();

	public List<CatalogTool> getList() {
		return list;
	}

	public void setList(final List<CatalogTool> list) {
		this.list = list;
	}

}
