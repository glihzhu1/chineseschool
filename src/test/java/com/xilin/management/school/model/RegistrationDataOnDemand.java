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
public class RegistrationDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Registration> data;

	@Autowired
    FamilyDataOnDemand familyDataOnDemand;

	@Autowired
    SemesterDataOnDemand semesterDataOnDemand;

	@Autowired
    StudentDataOnDemand studentDataOnDemand;

	@Autowired
    RegistrationRepository registrationRepository;

	public Registration getNewTransientRegistration(int index) {
        Registration obj = new Registration();
        setActive(obj, index);
        setCoursecode(obj, index);
        setOrdercode(obj, index);
        setPaiddate(obj, index);
        setRegistereddate(obj, index);
        setRegistrationsemesterids(obj, index);
        setStatus(obj, index);
        setUpdatedby(obj, index);
        setUpdatedtime(obj, index);
        return obj;
    }

	public void setActive(Registration obj, int index) {
        Boolean active = true;
        obj.setActive(active);
    }

	public void setCoursecode(Registration obj, int index) {
        String coursecode = "coursecode_" + index;
        if (coursecode.length() > 20) {
            coursecode = coursecode.substring(0, 20);
        }
        obj.setCoursecode(coursecode);
    }

	public void setOrdercode(Registration obj, int index) {
        Integer ordercode = new Integer(index);
        obj.setOrdercode(ordercode);
    }

	public void setPaiddate(Registration obj, int index) {
        Calendar paiddate = Calendar.getInstance();
        obj.setPaiddate(paiddate);
    }

	public void setRegistereddate(Registration obj, int index) {
        Calendar registereddate = Calendar.getInstance();
        obj.setRegistereddate(registereddate);
    }

	public void setRegistrationsemesterids(Registration obj, int index) {
        String registrationsemesterids = "registrationsemesterids_" + index;
        if (registrationsemesterids.length() > 100) {
            registrationsemesterids = registrationsemesterids.substring(0, 100);
        }
        obj.setRegistrationsemesterids(registrationsemesterids);
    }

	public void setStatus(Registration obj, int index) {
        Integer status = new Integer(index);
        obj.setStatus(status);
    }

	public void setUpdatedby(Registration obj, int index) {
        String updatedby = "updatedby_" + index;
        if (updatedby.length() > 40) {
            updatedby = updatedby.substring(0, 40);
        }
        obj.setUpdatedby(updatedby);
    }

	public void setUpdatedtime(Registration obj, int index) {
        Calendar updatedtime = Calendar.getInstance();
        obj.setUpdatedtime(updatedtime);
    }

	public Registration getSpecificRegistration(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Registration obj = data.get(index);
        Integer id = obj.getId();
        return registrationRepository.findOne(id);
    }

	public Registration getRandomRegistration() {
        init();
        Registration obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return registrationRepository.findOne(id);
    }

	public boolean modifyRegistration(Registration obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = registrationRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Registration' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Registration>();
        for (int i = 0; i < 10; i++) {
            Registration obj = getNewTransientRegistration(i);
            try {
                registrationRepository.save(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            registrationRepository.flush();
            data.add(obj);
        }
    }
}
