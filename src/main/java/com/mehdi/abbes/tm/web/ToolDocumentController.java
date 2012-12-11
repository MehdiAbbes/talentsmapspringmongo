package com.mehdi.abbes.tm.web;

import com.mehdi.abbes.tm.domain.ToolDocument;
import com.mehdi.abbes.tm.repository.ToolRepository;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/tooldocuments")
@Controller
public class ToolDocumentController {

	@Autowired
    ToolRepository toolRepository;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid ToolDocument toolDocument, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, toolDocument);
            return "tooldocuments/create";
        }
        uiModel.asMap().clear();
        toolRepository.save(toolDocument);
        return "redirect:/tooldocuments/" + encodeUrlPathSegment(toolDocument.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new ToolDocument());
        return "tooldocuments/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") BigInteger id, Model uiModel) {
        uiModel.addAttribute("tooldocument", toolRepository.findOne(id));
        uiModel.addAttribute("itemId", id);
        return "tooldocuments/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("tooldocuments", toolRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / sizeNo, sizeNo)).getContent());
            float nrOfPages = (float) toolRepository.count() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("tooldocuments", toolRepository.findAll());
        }
        return "tooldocuments/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid ToolDocument toolDocument, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, toolDocument);
            return "tooldocuments/update";
        }
        uiModel.asMap().clear();
        toolRepository.save(toolDocument);
        return "redirect:/tooldocuments/" + encodeUrlPathSegment(toolDocument.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") BigInteger id, Model uiModel) {
        populateEditForm(uiModel, toolRepository.findOne(id));
        return "tooldocuments/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        ToolDocument toolDocument = toolRepository.findOne(id);
        toolRepository.delete(toolDocument);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/tooldocuments";
    }

	void populateEditForm(Model uiModel, ToolDocument toolDocument) {
        uiModel.addAttribute("toolDocument", toolDocument);
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
