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
public class SemesterweekDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Semesterweek> data;

	@Autowired
    SemesterDataOnDemand semesterDataOnDemand;

	@Autowired
    SemesterweekRepository semesterweekRepository;

	public Semesterweek getNewTransientSemesterweek(int index) {
        Semesterweek obj = new Semesterweek();
        setDescription(obj, index);
        setDisplaynumber(obj, index);
        setDisplayweekid(obj, index);
        setHasclass(obj, index);
        setUpdatedby(obj, index);
        setUpdatedtime(obj, index);
        setWeekdate(obj, index);
        return obj;
    }

	public void setDescription(Semesterweek obj, int index) {
        String description = "description_" + index;
        if (description.length() > 200) {
            description = description.substring(0, 200);
        }
        obj.setDescription(description);
    }

	public void setDisplaynumber(Semesterweek obj, int index) {
		String displaynumber = "" + index;
        obj.setDisplaynumber(displaynumber);
    }

	public void setDisplayweekid(Semesterweek obj, int index) {
        Integer displayweekid = new Integer(index);
        obj.setDisplayweekid(displayweekid);
    }

	public void setHasclass(Semesterweek obj, int index) {
        Boolean hasclass = Boolean.TRUE;
        obj.setHasclass(hasclass);
    }

	public void setUpdatedby(Semesterweek obj, int index) {
        String updatedby = "updatedby_" + index;
        if (updatedby.length() > 40) {
            updatedby = updatedby.substring(0, 40);
        }
        obj.setUpdatedby(updatedby);
    }

	public void setUpdatedtime(Semesterweek obj, int index) {
        Calendar updatedtime = Calendar.getInstance();
        obj.setUpdatedtime(updatedtime);
    }

	public void setWeekdate(Semesterweek obj, int index) {
        Calendar weekdate = Calendar.getInstance();
        obj.setWeekdate(weekdate);
    }

	public Semesterweek getSpecificSemesterweek(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Semesterweek obj = data.get(index);
        Integer id = obj.getId();
        return semesterweekRepository.findOne(id);
    }

	public Semesterweek getRandomSemesterweek() {
        init();
        Semesterweek obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return semesterweekRepository.findOne(id);
    }

	public boolean modifySemesterweek(Semesterweek obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = semesterweekRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Semesterweek' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Semesterweek>();
        for (int i = 0; i < 10; i++) {
            Semesterweek obj = getNewTransientSemesterweek(i);
            try {
                semesterweekRepository.save(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            semesterweekRepository.flush();
            data.add(obj);
        }
    }
}
