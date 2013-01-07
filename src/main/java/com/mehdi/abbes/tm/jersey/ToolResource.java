package com.mehdi.abbes.tm.jersey;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mehdi.abbes.tm.domain.CatalogTool;
import com.mehdi.abbes.tm.domain.JerseyToolList;
import com.mehdi.abbes.tm.repository.CatalogToolRepository;

@Component
@Path("/jerseyTools")
public class ToolResource {
    
    @Context
    private UriInfo uriInfo;
    
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
    public Response getTool(@PathParam("id") final String toolId) {
        final CatalogTool tool = this.repo.findOne(toolId);
        if (tool == null) {
            throw new IllegalStateException("no tool with id [" + toolId + "] exists");
        }
        return Response.ok(tool).build();
    }
    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response createNewTool() {
        final CatalogTool newTool = this.repo.save(new CatalogTool());
        return Response.created(UriBuilder.fromUri(this.uriInfo.getAbsolutePath()).path("/" + newTool.getId()).build())
            .build();
    }
    
    @PUT
    @Consumes({
        MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
    })
    @Produces({
        MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
    })
    public Response createTool(final CatalogTool newTool) {
        final CatalogTool createdTool = this.repo.save(newTool);
        return Response.created(UriBuilder.fromUri(this.uriInfo.getAbsolutePath()).path("/" + createdTool.getId())
                                    .build()).build();
        
    }
    
    @PUT
    @Consumes({
        MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
    })
    @Produces({
        MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML
    })
    @Path("/{id}")
    public Response saveTool(@PathParam("id") final String toolId, final CatalogTool newTool) {
        final CatalogTool oldTool = this.repo.findOne(toolId);
        if (oldTool == null) {
            throw new IllegalStateException("WTF the tool to update does not exist");
        }
        newTool.setId(toolId);
        return Response.ok(this.repo.save(newTool)).build();
        
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteToom(@PathParam("id") final String toolId) {
        this.repo.delete(toolId);
        return Response.ok().build();
    }
}
