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
public class SchoolannouncementDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Schoolannouncement> data;

	@Autowired
    SchoolannouncementRepository schoolannouncementRepository;

	public Schoolannouncement getNewTransientSchoolannouncement(int index) {
        Schoolannouncement obj = new Schoolannouncement();
        setActive(obj, index);
        setAnnouncetype(obj, index);
        setCreatetime(obj, index);
        setCreatoremail(obj, index);
        setCreatorname(obj, index);
        setImagefilename(obj, index);
        setInfofilename(obj, index);
        setNewscontent(obj, index);
        setNewstitle(obj, index);
        setUpdatedby(obj, index);
        setUpdatedtime(obj, index);
        return obj;
    }

	public void setActive(Schoolannouncement obj, int index) {
        Boolean active = Boolean.TRUE;
        obj.setActive(active);
    }

	public void setAnnouncetype(Schoolannouncement obj, int index) {
        String announcetype = "announcetype_" + index;
        if (announcetype.length() > 20) {
            announcetype = announcetype.substring(0, 20);
        }
        obj.setAnnouncetype(announcetype);
    }

	public void setCreatetime(Schoolannouncement obj, int index) {
        Calendar createtime = Calendar.getInstance();
        obj.setCreatetime(createtime);
    }

	public void setCreatoremail(Schoolannouncement obj, int index) {
        String creatoremail = "foo" + index + "@bar.com";
        if (creatoremail.length() > 50) {
            creatoremail = creatoremail.substring(0, 50);
        }
        obj.setCreatoremail(creatoremail);
    }

	public void setCreatorname(Schoolannouncement obj, int index) {
        String creatorname = "creatorname_" + index;
        if (creatorname.length() > 100) {
            creatorname = creatorname.substring(0, 100);
        }
        obj.setCreatorname(creatorname);
    }

	public void setImagefilename(Schoolannouncement obj, int index) {
        String imagefilename = "imagefilename_" + index;
        if (imagefilename.length() > 50) {
            imagefilename = imagefilename.substring(0, 50);
        }
        obj.setImagefilename(imagefilename);
    }

	public void setInfofilename(Schoolannouncement obj, int index) {
        String infofilename = "infofilename_" + index;
        if (infofilename.length() > 50) {
            infofilename = infofilename.substring(0, 50);
        }
        obj.setInfofilename(infofilename);
    }

	public void setNewscontent(Schoolannouncement obj, int index) {
        String newscontent = "newscontent_" + index;
        if (newscontent.length() > 50) {
            newscontent = newscontent.substring(0, 50);
        }
        obj.setNewscontent(newscontent);
    }

	public void setNewstitle(Schoolannouncement obj, int index) {
        String newstitle = "newstitle_" + index;
        if (newstitle.length() > 50) {
            newstitle = newstitle.substring(0, 50);
        }
        obj.setNewstitle(newstitle);
    }

	public void setUpdatedby(Schoolannouncement obj, int index) {
        String updatedby = "updatedby_" + index;
        if (updatedby.length() > 40) {
            updatedby = updatedby.substring(0, 40);
        }
        obj.setUpdatedby(updatedby);
    }

	public void setUpdatedtime(Schoolannouncement obj, int index) {
        Calendar updatedtime = Calendar.getInstance();
        obj.setUpdatedtime(updatedtime);
    }

	public Schoolannouncement getSpecificSchoolannouncement(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Schoolannouncement obj = data.get(index);
        Integer id = obj.getId();
        return schoolannouncementRepository.findOne(id);
    }

	public Schoolannouncement getRandomSchoolannouncement() {
        init();
        Schoolannouncement obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return schoolannouncementRepository.findOne(id);
    }

	public boolean modifySchoolannouncement(Schoolannouncement obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = schoolannouncementRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Schoolannouncement' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Schoolannouncement>();
        for (int i = 0; i < 10; i++) {
            Schoolannouncement obj = getNewTransientSchoolannouncement(i);
            try {
                schoolannouncementRepository.save(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            schoolannouncementRepository.flush();
            data.add(obj);
        }
    }
}
