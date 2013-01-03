package com.mehdi.abbes.tm.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mehdi.abbes.tm.domain.CatalogTool;
import com.mehdi.abbes.tm.domain.CatalogToolList;
import com.mehdi.abbes.tm.repository.CatalogToolRepository;
import com.mehdi.abbes.tm.rest.CatalogToolResource;
import com.mehdi.abbes.tm.rest.CatalogToolResourceAssembler;

@Controller
@RequestMapping("/tools")
public class CatalogToolController {

	@Autowired
	private CatalogToolResourceAssembler toolAssembler;

	@Autowired
	private CatalogToolRepository catalogToolRepository;

	@RequestMapping(method = RequestMethod.POST, value = "")
	ResponseEntity<Void> createTool() {
		CatalogTool tool = new CatalogTool();
		catalogToolRepository.save(tool);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ControllerLinkBuilder
				.linkTo(CatalogToolController.class).slash(tool.getId())
				.toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{toolId}", produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	ResponseEntity<CatalogToolResource> showTool(
			@PathVariable final String toolId) {
		CatalogTool tool = catalogToolRepository.findOne(toolId);
		CatalogToolResource toolResource = toolAssembler.toResource(tool);
		return new ResponseEntity<CatalogToolResource>(toolResource,
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	ResponseEntity<CatalogTool> updateTool(@RequestBody final CatalogTool tool) {
		CatalogTool newTool = catalogToolRepository.save(tool);
		return new ResponseEntity<CatalogTool>(newTool, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "{toolId}")
	ResponseEntity<Void> deleteTool(@PathVariable final String toolId) {
		catalogToolRepository.delete(toolId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	ResponseEntity<CatalogToolList> getTools() {
		List<CatalogTool> toolsList = catalogToolRepository.findAll();
		CatalogToolList catalogToolList = new CatalogToolList();
		List<CatalogToolResource> resourcesList = toolAssembler
				.toResources(toolsList);
		catalogToolList.setList(resourcesList);
		return new ResponseEntity<CatalogToolList>(catalogToolList,
				HttpStatus.OK);
	}

	@ExceptionHandler({ Exception.class })
	ResponseEntity<String> handleException(final Exception e) {
		return new ResponseEntity<String>(e.getMessage(),
				HttpStatus.BAD_REQUEST);
	}

}
