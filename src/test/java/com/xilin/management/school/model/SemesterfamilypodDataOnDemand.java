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
public class SemesterfamilypodDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Semesterfamilypod> data;

	@Autowired
    FamilyDataOnDemand familyDataOnDemand;

	@Autowired
    SemesterpodDataOnDemand semesterpodDataOnDemand;

	@Autowired
    SemesterfamilypodRepository semesterfamilypodRepository;

	public Semesterfamilypod getNewTransientSemesterfamilypod(int index) {
        Semesterfamilypod obj = new Semesterfamilypod();
        setPerformed(obj, index);
        setUpdatedby(obj, index);
        setUpdatedtime(obj, index);
        return obj;
    }

	public void setPerformed(Semesterfamilypod obj, int index) {
        Boolean performed = Boolean.TRUE;
        obj.setPerformed(performed);
    }

	public void setUpdatedby(Semesterfamilypod obj, int index) {
        String updatedby = "updatedby_" + index;
        if (updatedby.length() > 40) {
            updatedby = updatedby.substring(0, 40);
        }
        obj.setUpdatedby(updatedby);
    }

	public void setUpdatedtime(Semesterfamilypod obj, int index) {
        Calendar updatedtime = Calendar.getInstance();
        obj.setUpdatedtime(updatedtime);
    }

	public Semesterfamilypod getSpecificSemesterfamilypod(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Semesterfamilypod obj = data.get(index);
        Integer id = obj.getId();
        return semesterfamilypodRepository.findOne(id);
    }

	public Semesterfamilypod getRandomSemesterfamilypod() {
        init();
        Semesterfamilypod obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return semesterfamilypodRepository.findOne(id);
    }

	public boolean modifySemesterfamilypod(Semesterfamilypod obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = semesterfamilypodRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Semesterfamilypod' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Semesterfamilypod>();
        for (int i = 0; i < 10; i++) {
            Semesterfamilypod obj = getNewTransientSemesterfamilypod(i);
            try {
                semesterfamilypodRepository.save(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            semesterfamilypodRepository.flush();
            data.add(obj);
        }
    }
}
