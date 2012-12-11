package com.mehdi.abbes.tm.domain;

import com.mehdi.abbes.tm.repository.ToolRepository;
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
public class ToolDocumentDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<ToolDocument> data;

	@Autowired
    ToolRepository toolRepository;

	public ToolDocument getNewTransientToolDocument(int index) {
        ToolDocument obj = new ToolDocument();
        setScore(obj, index);
        setTool(obj, index);
        return obj;
    }

	public void setScore(ToolDocument obj, int index) {
        Integer score = new Integer(index);
        obj.setScore(score);
    }

	public void setTool(ToolDocument obj, int index) {
        String tool = "tool_" + index;
        obj.setTool(tool);
    }

	public ToolDocument getSpecificToolDocument(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        ToolDocument obj = data.get(index);
        BigInteger id = obj.getId();
        return toolRepository.findOne(id);
    }

	public ToolDocument getRandomToolDocument() {
        init();
        ToolDocument obj = data.get(rnd.nextInt(data.size()));
        BigInteger id = obj.getId();
        return toolRepository.findOne(id);
    }

	public boolean modifyToolDocument(ToolDocument obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = toolRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'ToolDocument' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<ToolDocument>();
        for (int i = 0; i < 10; i++) {
            ToolDocument obj = getNewTransientToolDocument(i);
            try {
                toolRepository.save(obj);
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
