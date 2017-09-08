package com.xilin.management.school.web.util;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.xilin.management.school.web.util.TransientuserTransformer;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class TransientUser {

	private String loginId;

	private String passwordHash;

	private String email;

	private Date lastUpdateDate;

	private String appRole;

	private Boolean userActive;
	
	public String getLoginId() {
        return loginId;
    }

	public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

	public String getPasswordHash() {
        return passwordHash;
    }

	public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

	public String getEmail() {
        return email;
    }

	public void setEmail(String email) {
        this.email = email;
    }

	public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

	public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

	public String getAppRole() {
        return appRole;
    }

	public void setAppRole(String appRole) {
        this.appRole = appRole;
    }

	public Boolean getUserActive() {
        return userActive;
    }

	public void setUserActive(Boolean userActive) {
        this.userActive = userActive;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class")
        	.transform(new TransientuserTransformer(), TransientUser.class).serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }

	public static TransientUser fromJsonToTransientUser(String json) {
        /*return new JSONDeserializer<TransientUser>()
        .use(null, TransientUser.class)
        
        .deserialize(json);*/
		
		
		return new JSONDeserializer<TransientUser>()
				.use(null, TransientUser.class)
				.use(Date.class, new DateTransformer("MM/dd/yyyy"))
				.deserialize( json );
		
		
    }

	public static String toJsonArray(Collection<TransientUser> collection) {
        return new JSONSerializer().exclude("*.class")
        	.transform(new TransientuserTransformer(), TransientUser.class).serialize(collection);
    }

	public static String toJsonArray(Collection<TransientUser> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class")
        .transform(new TransientuserTransformer(), TransientUser.class)
        .serialize(collection);
    }

	public static Collection<TransientUser> fromJsonArrayToTransientUsers(String json) {
        return new JSONDeserializer<List<TransientUser>>()
        .use("values", TransientUser.class)
        .use(Date.class, new DateTransformer("MM/dd/yyyy"))
        .deserialize(json);
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((loginId == null) ? 0 : loginId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransientUser other = (TransientUser) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (loginId == null) {
			if (other.loginId != null)
				return false;
		} else if (!loginId.equals(other.loginId))
			return false;
		return true;
	}

	private Integer id;

	public Integer getId() {
        return this.id;
    }

	public void setId(Integer id) {
        this.id = id;
    }
}