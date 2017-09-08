package com.xilin.management.school.web.converter;
import com.xilin.management.school.model.Registration;
import com.xilin.management.school.model.RegistrationRepository;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@FacesConverter("com.xilin.management.school.web.converter.RegistrationConverter")
public class RegistrationConverter implements Converter {

	@Autowired
    RegistrationRepository registrationRepository;

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Integer id = Integer.parseInt(value);
        return registrationRepository.findOne(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Registration ? ((Registration) value).getId().toString() : "";
    }
}
