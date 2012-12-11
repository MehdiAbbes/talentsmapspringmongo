package com.mehdi.abbes.tm.web;

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

import com.mehdi.abbes.tm.domain.ProfileDocument;
import com.mehdi.abbes.tm.repository.ProfileRepository;
import com.mehdi.abbes.tm.repository.SkillRepository;

@RequestMapping("/profiledocuments")
@Controller
public class ProfileDocumentController {

	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	SkillRepository skillRepository;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid final ProfileDocument profileDocument,
			final BindingResult bindingResult, final Model uiModel,
			final HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, profileDocument);
			return "profiledocuments/create";
		}
		uiModel.asMap().clear();
		profileRepository.save(profileDocument);
		return "redirect:/profiledocuments/"
				+ encodeUrlPathSegment(profileDocument.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(final Model uiModel) {
		populateEditForm(uiModel, new ProfileDocument());
		return "profiledocuments/create";
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") final BigInteger id,
			final Model uiModel) {
		uiModel.addAttribute("profiledocument", profileRepository.findOne(id));
		uiModel.addAttribute("itemId", id);
		return "profiledocuments/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(
			@RequestParam(value = "page", required = false) final Integer page,
			@RequestParam(value = "size", required = false) final Integer size,
			final Model uiModel) {
		if ((page != null) || (size != null)) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1)
					* sizeNo;
			uiModel.addAttribute(
					"profiledocuments",
					profileRepository.findAll(
							new org.springframework.data.domain.PageRequest(
									firstResult / sizeNo, sizeNo)).getContent());
			float nrOfPages = (float) profileRepository.count() / sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) (((nrOfPages > (int) nrOfPages) || (nrOfPages == 0.0)) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("profiledocuments",
					profileRepository.findAll());
		}
		return "profiledocuments/list";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid final ProfileDocument profileDocument,
			final BindingResult bindingResult, final Model uiModel,
			final HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, profileDocument);
			return "profiledocuments/update";
		}
		uiModel.asMap().clear();
		profileRepository.save(profileDocument);
		return "redirect:/profiledocuments/"
				+ encodeUrlPathSegment(profileDocument.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") final BigInteger id,
			final Model uiModel) {
		populateEditForm(uiModel, profileRepository.findOne(id));
		return "profiledocuments/update";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") final BigInteger id,
			@RequestParam(value = "page", required = false) final Integer page,
			@RequestParam(value = "size", required = false) final Integer size,
			final Model uiModel) {
		ProfileDocument profileDocument = profileRepository.findOne(id);
		profileRepository.delete(profileDocument);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/profiledocuments";
	}

	void populateEditForm(final Model uiModel,
			final ProfileDocument profileDocument) {
		uiModel.addAttribute("profileDocument", profileDocument);
		uiModel.addAttribute("profiledocuments", profileRepository.findAll());
		uiModel.addAttribute("skilldocuments", skillRepository.findAll());
	}

	String encodeUrlPathSegment(String pathSegment,
			final HttpServletRequest httpServletRequest) {
		String enc = httpServletRequest.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}
}
