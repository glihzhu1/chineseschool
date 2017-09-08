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

@Configurable
@Component
public class SemesterpodDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Semesterpod> data;

	@Autowired
    SemesterweekDataOnDemand semesterweekDataOnDemand;

	@Autowired
    SemesterpodRepository semesterpodRepository;

	public Semesterpod getNewTransientSemesterpod(int index) {
        Semesterpod obj = new Semesterpod();
        setCapacity(obj, index);
        setFilled(obj, index);
        setPodhour(obj, index);
        setUpdatedby(obj, index);
        setUpdatedtime(obj, index);
        return obj;
    }

	public void setCapacity(Semesterpod obj, int index) {
        Integer capacity = new Integer(index);
        obj.setCapacity(capacity);
    }

	public void setFilled(Semesterpod obj, int index) {
        Integer filled = new Integer(index);
        obj.setFilled(filled);
    }

	public void setPodhour(Semesterpod obj, int index) {
        String podhour = "podhour_" + index;
        if (podhour.length() > 40) {
            podhour = podhour.substring(0, 40);
        }
        obj.setPodhour(podhour);
    }

	public void setUpdatedby(Semesterpod obj, int index) {
        String updatedby = "updatedby_" + index;
        if (updatedby.length() > 40) {
            updatedby = updatedby.substring(0, 40);
        }
        obj.setUpdatedby(updatedby);
    }

	public void setUpdatedtime(Semesterpod obj, int index) {
        Calendar updatedtime = Calendar.getInstance();
        obj.setUpdatedtime(updatedtime);
    }

	public Semesterpod getSpecificSemesterpod(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Semesterpod obj = data.get(index);
        Integer id = obj.getId();
        return semesterpodRepository.findOne(id);
    }

	public Semesterpod getRandomSemesterpod() {
        init();
        Semesterpod obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return semesterpodRepository.findOne(id);
    }

	public boolean modifySemesterpod(Semesterpod obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = semesterpodRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Semesterpod' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Semesterpod>();
        for (int i = 0; i < 10; i++) {
            Semesterpod obj = getNewTransientSemesterpod(i);
            try {
                semesterpodRepository.save(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            semesterpodRepository.flush();
            data.add(obj);
        }
    }
}
