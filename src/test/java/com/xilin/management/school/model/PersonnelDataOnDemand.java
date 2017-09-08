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
public class PersonnelDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Personnel> data;

	@Autowired
    PersonnelRepository personnelRepository;

	public Personnel getNewTransientPersonnel(int index) {
        Personnel obj = new Personnel();
        setActive(obj, index);
        setAddress1(obj, index);
        setAddress2(obj, index);
        setCellphone(obj, index);
        setChinesename(obj, index);
        setCity(obj, index);
        setJobtitle(obj, index);
        setEmail(obj, index);
        setExternaluserid(obj, index);
        setFirstname(obj, index);
        setGender(obj, index);
        setHiredate(obj, index);
        setHomephone(obj, index);
        setLastname(obj, index);
        setMiddlename(obj, index);
        setResume(obj, index);
        setState(obj, index);
        setType(obj, index);
        setUpdatedBy(obj, index);
        setUpdatedtime(obj, index);
        setXilinemail(obj, index);
        setZip(obj, index);
        setLoginId(obj, index);
        return obj;
    }

	public void setActive(Personnel obj, int index) {
        Boolean active = true;
        obj.setActive(active);
    }

	public void setAddress1(Personnel obj, int index) {
        String address1 = "address1_" + index;
        if (address1.length() > 200) {
            address1 = address1.substring(0, 200);
        }
        obj.setAddress1(address1);
    }

	public void setAddress2(Personnel obj, int index) {
        String address2 = "address2_" + index;
        if (address2.length() > 200) {
            address2 = address2.substring(0, 200);
        }
        obj.setAddress2(address2);
    }

	public void setCellphone(Personnel obj, int index) {
        String cellphone = "cellphon_" + index;
        if (cellphone.length() > 10) {
            cellphone = cellphone.substring(0, 10);
        }
        obj.setCellphone(cellphone);
    }

	public void setChinesename(Personnel obj, int index) {
        String chinesename = "chinesename_" + index;
        if (chinesename.length() > 50) {
            chinesename = chinesename.substring(0, 50);
        }
        obj.setChinesename(chinesename);
    }

	public void setCity(Personnel obj, int index) {
        String city = "city_" + index;
        if (city.length() > 200) {
            city = city.substring(0, 200);
        }
        obj.setCity(city);
    }

	public void setJobtitle(Personnel obj, int index) {
        String jobtitle = "jobtitle_" + index;
        if (jobtitle.length() > 50) {
            jobtitle = jobtitle.substring(0, 50);
        }
        obj.setJobtitle(jobtitle);
    }

	public void setEmail(Personnel obj, int index) {
        String email = "foo" + index + "@bar.com";
        if (email.length() > 100) {
            email = email.substring(0, 100);
        }
        obj.setEmail(email);
    }

	public void setExternaluserid(Personnel obj, int index) {
        Integer externaluserid = new Integer(index);
        obj.setExternaluserid(externaluserid);
    }

	public void setFirstname(Personnel obj, int index) {
        String firstname = "firstname_" + index;
        if (firstname.length() > 50) {
            firstname = firstname.substring(0, 50);
        }
        obj.setFirstname(firstname);
    }

	public void setGender(Personnel obj, int index) {
        Character gender = new Character('N');
        obj.setGender(gender);
    }

	public void setHiredate(Personnel obj, int index) {
        Calendar hiredate = Calendar.getInstance();
        obj.setHiredate(hiredate);
    }

	public void setHomephone(Personnel obj, int index) {
        String homephone = "homephon_" + index;
        if (homephone.length() > 10) {
            homephone = homephone.substring(0, 10);
        }
        obj.setHomephone(homephone);
    }

	public void setLastname(Personnel obj, int index) {
        String lastname = "lastname_" + index;
        if (lastname.length() > 50) {
            lastname = lastname.substring(0, 50);
        }
        obj.setLastname(lastname);
    }

	public void setMiddlename(Personnel obj, int index) {
        String middlename = "middlename_" + index;
        if (middlename.length() > 50) {
            middlename = middlename.substring(0, 50);
        }
        obj.setMiddlename(middlename);
    }

	public void setResume(Personnel obj, int index) {
        String resume = "resume_" + index;
        if (resume.length() > 2000) {
            resume = resume.substring(0, 2000);
        }
        obj.setResume(resume);
    }

	public void setState(Personnel obj, int index) {
        String state = "s" + index;
        if (state.length() > 2) {
            state = state.substring(0, 2);
        }
        obj.setState(state);
    }

	public void setType(Personnel obj, int index) {
        Character type = new Character('N');
        obj.setType(type);
    }

	public void setUpdatedBy(Personnel obj, int index) {
        String updatedBy = "updatedBy_" + index;
        if (updatedBy.length() > 40) {
            updatedBy = updatedBy.substring(0, 40);
        }
        obj.setUpdatedBy(updatedBy);
    }

	public void setUpdatedtime(Personnel obj, int index) {
        Calendar updatedtime = Calendar.getInstance();
        obj.setUpdatedtime(updatedtime);
    }

	public void setXilinemail(Personnel obj, int index) {
        String xilinemail = "foo" + index + "@bar.com";
        if (xilinemail.length() > 100) {
            xilinemail = xilinemail.substring(0, 100);
        }
        obj.setXilinemail(xilinemail);
    }

	public void setZip(Personnel obj, int index) {
        String zip = "zip_" + index;
        if (zip.length() > 5) {
            zip = zip.substring(0, 5);
        }
        obj.setZip(zip);
    }

	public void setLoginId(Personnel obj, int index) {
        String login = "loginId_" + index;
        if (login.length() > 200) {
        	login = login.substring(0, 200);
        }
        obj.setLoginId(login);
    }
	
	public Personnel getSpecificPersonnel(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Personnel obj = data.get(index);
        Integer id = obj.getId();
        return personnelRepository.findOne(id);
    }

	public Personnel getRandomPersonnel() {
        init();
        Personnel obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return personnelRepository.findOne(id);
    }

	public boolean modifyPersonnel(Personnel obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = personnelRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Personnel' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Personnel>();
        for (int i = 0; i < 10; i++) {
            Personnel obj = getNewTransientPersonnel(i);
            try {
                personnelRepository.save(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            personnelRepository.flush();
            data.add(obj);
        }
    }
}
