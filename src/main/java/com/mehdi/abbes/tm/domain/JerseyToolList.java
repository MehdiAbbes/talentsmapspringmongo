package com.mehdi.abbes.tm.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ToolList")
public class JerseyToolList {
    
    private List<CatalogTool> list = new ArrayList<CatalogTool>();
    
    @XmlElement(name = "Tool")
    public List<CatalogTool> getList() {
        return this.list;
    }
    
    public void setList(final List<CatalogTool> list) {
        this.list = list;
    }
    
}
