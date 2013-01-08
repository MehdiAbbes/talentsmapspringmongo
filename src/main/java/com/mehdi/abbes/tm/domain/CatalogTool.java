package com.mehdi.abbes.tm.domain;

import java.net.URI;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Persistent;
import org.springframework.hateoas.Identifiable;

import com.mehdi.abbes.tm.jersey.ToolResource;
import com.sun.jersey.server.linking.Ref;
import com.sun.jersey.server.linking.Ref.Style;

@XmlRootElement
@Persistent
public class CatalogTool implements Identifiable<String> {
    
    @Ref(resource = ToolResource.class, style = Style.ABSOLUTE)
    private URI toolsURI;
    
    private String id;
    
    private String toolLabel = "";
    
    private String toolDescription = "";
    
    private int score;
    
    private boolean modified;
    
    public String getToolLabel() {
        return this.toolLabel;
    }
    
    public void setToolLabel(final String toolLabel) {
        this.toolLabel = toolLabel;
    }
    
    public String getToolDescription() {
        return this.toolDescription;
    }
    
    public void setToolDescription(final String toolDescription) {
        this.toolDescription = toolDescription;
    }
    
    public int getScore() {
        return this.score;
    }
    
    public void setScore(final int score) {
        this.score = score;
    }
    
    public boolean isModified() {
        return this.modified;
    }
    
    public void setModified(final boolean modified) {
        this.modified = modified;
    }
    
    public URI getToolsURI() {
        return this.toolsURI;
    }
    
    public void setToolsURI(final URI toolsURI) {
        this.toolsURI = toolsURI;
    }
    
    @Override
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
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
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final CatalogTool other = (CatalogTool) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
    
}
