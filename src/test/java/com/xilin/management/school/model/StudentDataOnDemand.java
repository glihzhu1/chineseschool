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
public class StudentDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Student> data;

	@Autowired
    FamilyDataOnDemand familyDataOnDemand;

	@Autowired
    StudentRepository studentRepository;

	public Student getNewTransientStudent(int index) {
        Student obj = new Student();
        setActive(obj, index);
        setChinesename(obj, index);
        setDob(obj, index);
        setFirstname(obj, index);
        setGender(obj, index);
        setLastname(obj, index);
        setMiddlename(obj, index);
        setUpdatedby(obj, index);
        setUpdatedtime(obj, index);
        return obj;
    }

	public void setActive(Student obj, int index) {
        Boolean active = true;
        obj.setActive(active);
    }

	public void setChinesename(Student obj, int index) {
        String chinesename = "chinesename_" + index;
        if (chinesename.length() > 50) {
            chinesename = chinesename.substring(0, 50);
        }
        obj.setChinesename(chinesename);
    }

	public void setDob(Student obj, int index) {
        Calendar dob = Calendar.getInstance();
        obj.setDob(dob);
    }

	public void setFirstname(Student obj, int index) {
        String firstname = "firstname_" + index;
        if (firstname.length() > 50) {
            firstname = firstname.substring(0, 50);
        }
        obj.setFirstname(firstname);
    }

	public void setGender(Student obj, int index) {
        Character gender = new Character('N');
        obj.setGender(gender);
    }

	public void setLastname(Student obj, int index) {
        String lastname = "lastname_" + index;
        if (lastname.length() > 50) {
            lastname = lastname.substring(0, 50);
        }
        obj.setLastname(lastname);
    }

	public void setMiddlename(Student obj, int index) {
        String middlename = "middlename_" + index;
        if (middlename.length() > 50) {
            middlename = middlename.substring(0, 50);
        }
        obj.setMiddlename(middlename);
    }

	public void setUpdatedby(Student obj, int index) {
        String updatedby = "updatedby_" + index;
        if (updatedby.length() > 40) {
        	updatedby = updatedby.substring(0, 40);
        }
        obj.setUpdatedby(updatedby);
    }

	public void setUpdatedtime(Student obj, int index) {
        Calendar updatedtime = Calendar.getInstance();
        obj.setUpdatedtime(updatedtime);
    }

	public Student getSpecificStudent(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Student obj = data.get(index);
        Integer id = obj.getId();
        return studentRepository.findOne(id);
    }

	public Student getRandomStudent() {
        init();
        Student obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return studentRepository.findOne(id);
    }

	public boolean modifyStudent(Student obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = studentRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Student' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Student>();
        for (int i = 0; i < 10; i++) {
            Student obj = getNewTransientStudent(i);
            try {
                studentRepository.save(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            studentRepository.flush();
            data.add(obj);
        }
    }
}
