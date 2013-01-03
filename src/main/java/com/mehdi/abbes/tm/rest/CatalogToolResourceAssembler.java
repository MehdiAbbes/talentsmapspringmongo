package com.mehdi.abbes.tm.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mehdi.abbes.tm.domain.CatalogTool;
import com.mehdi.abbes.tm.web.CatalogToolController;

@Component
public class CatalogToolResourceAssembler extends
		ResourceAssemblerSupport<CatalogTool, CatalogToolResource> {

	public CatalogToolResourceAssembler() {
		super(CatalogToolController.class, CatalogToolResource.class);
	}

	@Override
	public CatalogToolResource toResource(final CatalogTool entity) {
		CatalogToolResource resource = createResource(entity);
		resource.setLabel(entity.getToolLabel());
		resource.setDescription(entity.getToolDescription());
		resource.setScore(entity.getScore());
		resource.add(linkTo(CatalogToolController.class).withRel("tools"));
		return resource;
	}

}
