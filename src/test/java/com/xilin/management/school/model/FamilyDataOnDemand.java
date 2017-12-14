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
public class FamilyDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Family> data;

	@Autowired
    FamilyRepository familyRepository;

	public Family getNewTransientFamily(int index) {
        Family obj = new Family();
        setActive(obj, index);
        setAddress1(obj, index);
        setAddress2(obj, index);
        setCellphone(obj, index);
        setCity(obj, index);
        setEmail(obj, index);
        setExternaluserid(obj, index);
        setFatherchinesename(obj, index);
        setFatherfirstname(obj, index);
        setFatherlastname(obj, index);
        setFathermiddlename(obj, index);
        setHomephone(obj, index);
        setMotherchinesename(obj, index);
        setMotherfirstname(obj, index);
        setMotherlastname(obj, index);
        setMothermiddlename(obj, index);
        setState(obj, index);
        setType(obj, index);
        setUpdatedBy(obj, index);
        setUpdatedtime(obj, index);
        setXilinemail(obj, index);
        setZip(obj, index);
        setLoginId(obj, index);
        return obj;
    }

	public void setActive(Family obj, int index) {
        Boolean active = true;
        obj.setActive(active);
    }

	public void setAddress1(Family obj, int index) {
        String address1 = "address1_" + index;
        if (address1.length() > 200) {
            address1 = address1.substring(0, 200);
        }
        obj.setAddress1(address1);
    }

	public void setAddress2(Family obj, int index) {
        String address2 = "address2_" + index;
        if (address2.length() > 200) {
            address2 = address2.substring(0, 200);
        }
        obj.setAddress2(address2);
    }

	public void setCellphone(Family obj, int index) {
        String cellphone = "cellphon_" + index;
        if (cellphone.length() > 10) {
            cellphone = cellphone.substring(0, 10);
        }
        obj.setCellphone(cellphone);
    }

	public void setCity(Family obj, int index) {
        String city = "city_" + index;
        if (city.length() > 200) {
            city = city.substring(0, 200);
        }
        obj.setCity(city);
    }

	public void setEmail(Family obj, int index) {
        String email = "foo" + index + "@bar.com";
        if (email.length() > 100) {
            email = email.substring(0, 100);
        }
        obj.setEmail(email);
    }

	public void setExternaluserid(Family obj, int index) {
        Integer externaluserid = new Integer(index);
        obj.setExternaluserid(externaluserid);
    }

	public void setFatherchinesename(Family obj, int index) {
        String fatherchinesename = "fatherchinesename_" + index;
        if (fatherchinesename.length() > 50) {
            fatherchinesename = fatherchinesename.substring(0, 50);
        }
        obj.setFatherchinesename(fatherchinesename);
    }

	public void setFatherfirstname(Family obj, int index) {
        String fatherfirstname = "fatherfirstname_" + index;
        if (fatherfirstname.length() > 50) {
            fatherfirstname = fatherfirstname.substring(0, 50);
        }
        obj.setFatherfirstname(fatherfirstname);
    }

	public void setFatherlastname(Family obj, int index) {
        String fatherlastname = "fatherlastname_" + index;
        if (fatherlastname.length() > 50) {
            fatherlastname = fatherlastname.substring(0, 50);
        }
        obj.setFatherlastname(fatherlastname);
    }

	public void setFathermiddlename(Family obj, int index) {
        String fathermiddlename = "fathermiddlename_" + index;
        if (fathermiddlename.length() > 50) {
            fathermiddlename = fathermiddlename.substring(0, 50);
        }
        obj.setFathermiddlename(fathermiddlename);
    }

	public void setHomephone(Family obj, int index) {
        String homephone = "homephon_" + index;
        if (homephone.length() > 10) {
            homephone = homephone.substring(0, 10);
        }
        obj.setHomephone(homephone);
    }

	public void setMotherchinesename(Family obj, int index) {
        String motherchinesename = "motherchinesename_" + index;
        if (motherchinesename.length() > 50) {
            motherchinesename = motherchinesename.substring(0, 50);
        }
        obj.setMotherchinesename(motherchinesename);
    }

	public void setMotherfirstname(Family obj, int index) {
        String motherfirstname = "motherfirstname_" + index;
        if (motherfirstname.length() > 50) {
            motherfirstname = motherfirstname.substring(0, 50);
        }
        obj.setMotherfirstname(motherfirstname);
    }

	public void setMotherlastname(Family obj, int index) {
        String motherlastname = "motherlastname_" + index;
        if (motherlastname.length() > 50) {
            motherlastname = motherlastname.substring(0, 50);
        }
        obj.setMotherlastname(motherlastname);
    }

	public void setMothermiddlename(Family obj, int index) {
        String mothermiddlename = "mothermiddlename_" + index;
        if (mothermiddlename.length() > 50) {
            mothermiddlename = mothermiddlename.substring(0, 50);
        }
        obj.setMothermiddlename(mothermiddlename);
    }

	public void setState(Family obj, int index) {
        String state = "s" + index;
        if (state.length() > 2) {
            state = state.substring(0, 2);
        }
        obj.setState(state);
    }

	public void setType(Family obj, int index) {
        Character type = new Character('N');
        obj.setType(type);
    }

	public void setUpdatedBy(Family obj, int index) {
        String updatedBy = "updatedBy_" + index;
        if (updatedBy.length() > 40) {
            updatedBy = updatedBy.substring(0, 40);
        }
        obj.setUpdatedBy(updatedBy);
    }

	public void setUpdatedtime(Family obj, int index) {
        Calendar updatedtime = Calendar.getInstance();
        obj.setUpdatedtime(updatedtime);
    }

	public void setXilinemail(Family obj, int index) {
        String xilinemail = "foo" + index + "@bar.com";
        if (xilinemail.length() > 100) {
            xilinemail = xilinemail.substring(0, 100);
        }
        obj.setXilinemail(xilinemail);
    }

	public void setZip(Family obj, int index) {
        String zip = "zip_" + index;
        if (zip.length() > 5) {
            zip = zip.substring(0, 5);
        }
        obj.setZip(zip);
    }

	public void setLoginId(Family obj, int index) {
        String loginId = "loginId_" + index;
        if (loginId.length() > 40) {
        	loginId = loginId.substring(0, 40);
        }
        obj.setLoginId(loginId);
    }
	
	public Family getSpecificFamily(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Family obj = data.get(index);
        Integer id = obj.getId();
        return familyRepository.findOne(id);
    }

	public Family getRandomFamily() {
        init();
        Family obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return familyRepository.findOne(id);
    }

	public boolean modifyFamily(Family obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = familyRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Family' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Family>();
        for (int i = 0; i < 10; i++) {
            Family obj = getNewTransientFamily(i);
            try {
                familyRepository.save(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            familyRepository.flush();
            data.add(obj);
        }
    }
}
