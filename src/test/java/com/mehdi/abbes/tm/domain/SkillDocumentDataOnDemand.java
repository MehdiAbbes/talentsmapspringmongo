package com.mehdi.abbes.tm.domain;

import com.mehdi.abbes.tm.repository.SkillRepository;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class SkillDocumentDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<SkillDocument> data;

	@Autowired
    SkillRepository skillRepository;

	public SkillDocument getNewTransientSkillDocument(int index) {
        SkillDocument obj = new SkillDocument();
        setCategory(obj, index);
        setConcept(obj, index);
        setScore(obj, index);
        return obj;
    }

	public void setCategory(SkillDocument obj, int index) {
        String category = "category_" + index;
        obj.setCategory(category);
    }

	public void setConcept(SkillDocument obj, int index) {
        String concept = "concept_" + index;
        obj.setConcept(concept);
    }

	public void setScore(SkillDocument obj, int index) {
        Integer score = new Integer(index);
        obj.setScore(score);
    }

	public SkillDocument getSpecificSkillDocument(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        SkillDocument obj = data.get(index);
        BigInteger id = obj.getId();
        return skillRepository.findOne(id);
    }

	public SkillDocument getRandomSkillDocument() {
        init();
        SkillDocument obj = data.get(rnd.nextInt(data.size()));
        BigInteger id = obj.getId();
        return skillRepository.findOne(id);
    }

	public boolean modifySkillDocument(SkillDocument obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = skillRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'SkillDocument' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<SkillDocument>();
        for (int i = 0; i < 10; i++) {
            SkillDocument obj = getNewTransientSkillDocument(i);
            try {
                skillRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            data.add(obj);
        }
    }
}
