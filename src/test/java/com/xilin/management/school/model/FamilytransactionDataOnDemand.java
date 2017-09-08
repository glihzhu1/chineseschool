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
public class FamilytransactionDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Familytransaction> data;

	@Autowired
    BookitemDataOnDemand bookitemDataOnDemand;

	@Autowired
    FamilyDataOnDemand familyDataOnDemand;

	@Autowired
    SemesterDataOnDemand semesterDataOnDemand;

	@Autowired
    StudentDataOnDemand studentDataOnDemand;

	@Autowired
    FamilytransactionRepository familytransactionRepository;
	
	@Autowired
    SemestercourseDataOnDemand semestercourseDataOnDemand;

	public Familytransaction getNewTransientFamilytransaction(int index) {
        Familytransaction obj = new Familytransaction();
        setActive(obj, index);
        setPaiddate(obj, index);
        setRegistereddate(obj, index);
        setStatus(obj, index);
        setUpdatedby(obj, index);
        setTransactiontype(obj, index);
        setUpdatedtime(obj, index);
        return obj;
    }

	public void setActive(Familytransaction obj, int index) {
        Boolean active = Boolean.TRUE;
        obj.setActive(active);
    }

	public void setPaiddate(Familytransaction obj, int index) {
        Calendar paiddate = Calendar.getInstance();
        obj.setPaiddate(paiddate);
    }

	public void setRegistereddate(Familytransaction obj, int index) {
        Calendar registereddate = Calendar.getInstance();
        obj.setRegistereddate(registereddate);
    }

	public void setStatus(Familytransaction obj, int index) {
        String status = "status_" + index;
        if (status.length() > 40) {
            status = status.substring(0, 40);
        }
        obj.setStatus(status);
    }

	public void setUpdatedby(Familytransaction obj, int index) {
        String updatedby = "updatedby_" + index;
        if (updatedby.length() > 40) {
            updatedby = updatedby.substring(0, 40);
        }
        obj.setUpdatedby(updatedby);
    }

	public void setTransactiontype(Familytransaction obj, int index) {
        String transactiontype = "transactiontype_" + index;
        if (transactiontype.length() > 20) {
        	transactiontype = transactiontype.substring(0, 20);
        }
        obj.setTransactiontype(transactiontype);
    }
	
	public void setUpdatedtime(Familytransaction obj, int index) {
        Calendar updatedtime = Calendar.getInstance();
        obj.setUpdatedtime(updatedtime);
    }

	public Familytransaction getSpecificFamilytransaction(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Familytransaction obj = data.get(index);
        Integer id = obj.getId();
        return familytransactionRepository.findOne(id);
    }

	public Familytransaction getRandomFamilytransaction() {
        init();
        Familytransaction obj = data.get(rnd.nextInt(data.size()));
        Integer id = obj.getId();
        return familytransactionRepository.findOne(id);
    }

	public boolean modifyFamilytransaction(Familytransaction obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = familytransactionRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Familytransaction' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Familytransaction>();
        for (int i = 0; i < 10; i++) {
            Familytransaction obj = getNewTransientFamilytransaction(i);
            try {
            	familytransactionRepository.save(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            familytransactionRepository.flush();
            data.add(obj);
        }
    }
}
