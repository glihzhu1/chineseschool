package com.xilin.management.school.model;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
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
public class CourseinformationDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Courseinformation> data;

	/*@Autowired
    CoursecategoryDataOnDemand coursecategoryDataOnDemand;*/

	@Autowired
    CourseinformationRepository courseinformationRepository;

	public Courseinformation getNewTransientCourseinformation(int index) {
        Courseinformation obj = new Courseinformation();
        setChinesecoursename(obj, index);
        setCoursecode(obj, index);
        setCoursegrade(obj, index);
        setCoursename(obj, index);
        setCoursetype(obj, index);
        setUpdatedby(obj, index);
        setUpdatedtime(obj, index);
        return obj;
    }

	public void setChinesecoursename(Courseinformation obj, int index) {
        String chinesecoursename = "chinesecoursename_" + index;
        if (chinesecoursename.length() > 50) {
            chinesecoursename = chinesecoursename.substring(0, 50);
        }
        obj.setChinesecoursename(chinesecoursename);
    }

	public void setCoursecode(Courseinformation obj, int index) {
        String coursecode = "coursecode_" + index;
        if (coursecode.length() > 50) {
            coursecode = coursecode.substring(0, 50);
        }
        obj.setCoursecode(coursecode);
    }

	public void setCoursegrade(Courseinformation obj, int index) {
        String coursegrade = "coursegrade_" + index;
        if (coursegrade.length() > 50) {
            coursegrade = coursegrade.substring(0, 50);
        }
        obj.setCoursegrade(coursegrade);
    }

	public void setCoursename(Courseinformation obj, int index) {
        String coursename = "coursename_" + index;
        if (coursename.length() > 50) {
            coursename = coursename.substring(0, 50);
        }
        obj.setCoursename(coursename);
    }

	public void setCoursetype(Courseinformation obj, int index) {
        String coursetype = "coursetype_" + index;
        if (coursetype.length() > 50) {
            coursetype = coursetype.substring(0, 50);
        }
        obj.setCoursetype(coursetype);
    }

	public void setUpdatedby(Courseinformation obj, int index) {
        String updatedby = "updatedby_" + index;
        if (updatedby.length() > 40) {
            updatedby = updatedby.substring(0, 40);
        }
        obj.setUpdatedby(updatedby);
    }

	public void setUpdatedtime(Courseinformation obj, int index) {
        Calendar updatedtime = Calendar.getInstance();
        obj.setUpdatedtime(updatedtime);
    }

	public Courseinformation getSpecificCourseinformation(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Courseinformation obj = data.get(index);
        Integer id = obj.getId();
        return courseinformationRepository.findOne(id);
    }

	public Courseinformation getRandomCourseinformation() {
        init();
        Courseinformation obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return courseinformationRepository.findOne(id);
    }

	public boolean modifyCourseinformation(Courseinformation obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = courseinformationRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Courseinformation' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Courseinformation>();
        for (int i = 0; i < 10; i++) {
            Courseinformation obj = getNewTransientCourseinformation(i);
            try {
                courseinformationRepository.save(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            courseinformationRepository.flush();
            data.add(obj);
        }
    }
}
