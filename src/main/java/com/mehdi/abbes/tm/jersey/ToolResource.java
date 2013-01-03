package com.mehdi.abbes.tm.jersey;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mehdi.abbes.tm.domain.CatalogTool;
import com.mehdi.abbes.tm.domain.JerseyToolList;
import com.mehdi.abbes.tm.repository.CatalogToolRepository;

@Component
@Path("/jerseyTools")
public class ToolResource {
    
    @Autowired
    private CatalogToolRepository repo;
    
    @GET
    @Produces({
        MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
    })
    public JerseyToolList getTools() {
        final JerseyToolList toolList = new JerseyToolList();
        toolList.setList(this.repo.findAll());
        return toolList;
    }
    
    @GET
    @Produces({
        MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
    })
    @Path("/{id}")
    public CatalogTool getTool(@PathParam("id") final String toolId) {
        return this.repo.findOne(toolId);
    }
    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String createNewTool() {
        final CatalogTool newTool = this.repo.save(new CatalogTool());
        return newTool.getId();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({
        MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
    })
    public CatalogTool saveTool(final CatalogTool newTool) {
        return this.repo.save(newTool);
    }
    
    @DELETE
    @Path("/{id}")
    public void deleteToom(@PathParam("id") final String toolId) {
        this.repo.delete(toolId);
    }
}
