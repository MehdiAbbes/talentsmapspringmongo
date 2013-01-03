package com.mehdi.abbes.tm.jersey;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mehdi.abbes.tm.domain.CatalogTool;
import com.mehdi.abbes.tm.repository.CatalogToolRepository;

@Component
@Path("/jerseyTools")
public class ToolResource {

	@Autowired
	private CatalogToolRepository repo;

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<CatalogTool> getTools() {
		List<CatalogTool> tools = repo.findAll();
		return tools;
	}
}
