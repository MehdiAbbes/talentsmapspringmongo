package com.mehdi.abbes.tm.web;

import com.mehdi.abbes.tm.domain.SkillDocument;
import com.mehdi.abbes.tm.repository.SkillRepository;
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

@RequestMapping("/skilldocuments")
@Controller
public class SkillDocumentController {

	@Autowired
    SkillRepository skillRepository;

	@Autowired
    ToolRepository toolRepository;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid SkillDocument skillDocument, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, skillDocument);
            return "skilldocuments/create";
        }
        uiModel.asMap().clear();
        skillRepository.save(skillDocument);
        return "redirect:/skilldocuments/" + encodeUrlPathSegment(skillDocument.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new SkillDocument());
        return "skilldocuments/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") BigInteger id, Model uiModel) {
        uiModel.addAttribute("skilldocument", skillRepository.findOne(id));
        uiModel.addAttribute("itemId", id);
        return "skilldocuments/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("skilldocuments", skillRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / sizeNo, sizeNo)).getContent());
            float nrOfPages = (float) skillRepository.count() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("skilldocuments", skillRepository.findAll());
        }
        return "skilldocuments/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid SkillDocument skillDocument, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, skillDocument);
            return "skilldocuments/update";
        }
        uiModel.asMap().clear();
        skillRepository.save(skillDocument);
        return "redirect:/skilldocuments/" + encodeUrlPathSegment(skillDocument.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") BigInteger id, Model uiModel) {
        populateEditForm(uiModel, skillRepository.findOne(id));
        return "skilldocuments/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        SkillDocument skillDocument = skillRepository.findOne(id);
        skillRepository.delete(skillDocument);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/skilldocuments";
    }

	void populateEditForm(Model uiModel, SkillDocument skillDocument) {
        uiModel.addAttribute("skillDocument", skillDocument);
        uiModel.addAttribute("tooldocuments", toolRepository.findAll());
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
