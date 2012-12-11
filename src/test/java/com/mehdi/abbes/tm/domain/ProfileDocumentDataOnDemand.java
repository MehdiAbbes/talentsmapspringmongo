package com.mehdi.abbes.tm.domain;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.mehdi.abbes.tm.repository.ProfileRepository;

@Component
@Configurable
public class ProfileDocumentDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<ProfileDocument> data;

	@Autowired
	ProfileRepository profileRepository;

	public ProfileDocument getNewTransientProfileDocument(final int index) {
		ProfileDocument obj = new ProfileDocument();
		setEmail(obj, index);
		setExperienceYears(obj, index);
		setFirstname(obj, index);
		setLastname(obj, index);
		setManager(obj, index);
		return obj;
	}

	public void setEmail(final ProfileDocument obj, final int index) {
		String email = "foo" + index + "@bar.com";
		obj.setEmail(email);
	}

	public void setExperienceYears(final ProfileDocument obj, final int index) {
		int experienceYears = index;
		if (experienceYears > 30) {
			experienceYears = 30;
		}
		obj.setExperienceYears(experienceYears);
	}

	public void setFirstname(final ProfileDocument obj, final int index) {
		String firstname = "firstname_" + index;
		obj.setFirstname(firstname);
	}

	public void setLastname(final ProfileDocument obj, final int index) {
		String lastname = "lastname_" + index;
		obj.setLastname(lastname);
	}

	public void setManager(final ProfileDocument obj, final int index) {
		ProfileDocument manager = obj;
		obj.setManager(manager);
	}

	public ProfileDocument getSpecificProfileDocument(int index) {
		init();
		if (index < 0) {
			index = 0;
		}
		if (index > (data.size() - 1)) {
			index = data.size() - 1;
		}
		ProfileDocument obj = data.get(index);
		BigInteger id = obj.getId();
		return profileRepository.findOne(id);
	}

	public ProfileDocument getRandomProfileDocument() {
		init();
		ProfileDocument obj = data.get(rnd.nextInt(data.size()));
		BigInteger id = obj.getId();
		return profileRepository.findOne(id);
	}

	public boolean modifyProfileDocument(final ProfileDocument obj) {
		return false;
	}

	public void init() {
		int from = 0;
		int to = 10;
		data = profileRepository.findAll(
				new org.springframework.data.domain.PageRequest(from / to, to))
				.getContent();
		if (data == null) {
			throw new IllegalStateException(
					"Find entries implementation for 'ProfileDocument' illegally returned null");
		}
		if (!data.isEmpty()) {
			return;
		}

		data = new ArrayList<ProfileDocument>();
		for (int i = 0; i < 10; i++) {
			ProfileDocument obj = getNewTransientProfileDocument(i);
			try {
				profileRepository.save(obj);
			} catch (ConstraintViolationException e) {
				StringBuilder msg = new StringBuilder();
				for (ConstraintViolation<?> cv : e.getConstraintViolations()) {
					msg.append("[").append(cv.getConstraintDescriptor())
							.append(":").append(cv.getMessage()).append("=")
							.append(cv.getInvalidValue()).append("]");
				}
				throw new RuntimeException(msg.toString(), e);
			}
			data.add(obj);
		}
	}
}
