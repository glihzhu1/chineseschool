package com.xilin.management.school.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import flexjson.TypeContext;
import flexjson.transformer.AbstractTransformer;

public class TransientuserTransformer extends AbstractTransformer {

    @Override
    public Boolean isInline() {
        return Boolean.TRUE;
    }
    
	@Override
	public void transform(Object object) {
		
		// Start the object
		TypeContext typeContext = getContext().writeOpenObject();
		typeContext.setFirst(false);

		TransientUser person = (TransientUser) object;
		
		// Write out the fields
	    getContext().writeName("id");
	    getContext().transform(person.getId());
	    getContext().writeComma();
	    getContext().writeName("loginId");
	    getContext().transform(person.getLoginId());
	    getContext().writeComma();
	    //getContext().writeName("lastUpdateDate");
	    //getContext().transform(person.getLastUpdateDate());
	    //getContext().writeComma();
	    
	    Date lastupdateDate = person.getLastUpdateDate();
	    if (lastupdateDate != null) {
	    	getContext().writeName("lastUpdateDate");
	    	//String strlastupdateDate = lastupdateDate.toString();
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    	//dateFormat.setTimeZone(lastupdateDate.getTimeZone());
	    	String strlastupdateDate = dateFormat.format(lastupdateDate.getTime());
	    	getContext().transform(strlastupdateDate);
	    	getContext().writeComma();
	    }
	    getContext().writeName("passwordHash");
	    getContext().transform(person.getPasswordHash());
	    getContext().writeComma();
	    getContext().writeName("appRole");
	    getContext().transform(person.getAppRole());
	    getContext().writeComma();
	    getContext().writeName("userActive");
	    getContext().transform(person.getUserActive());
	    getContext().writeComma();
	    getContext().writeName("email");
	    getContext().transform(person.getEmail());
	    
	    getContext().writeCloseObject();
		
	}

}