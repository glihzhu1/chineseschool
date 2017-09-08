package com.xilin.management.school.web.converter;
import com.xilin.management.school.model.Family;
import com.xilin.management.school.model.FamilyRepository;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@FacesConverter("com.xilin.management.school.web.converter.FamilyConverter")
@Configurable
public class FamilyConverter implements Converter {

	@Autowired
    FamilyRepository familyRepository;

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Integer id = Integer.parseInt(value);
        return familyRepository.findOne(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Family ? ((Family) value).getId().toString() : "";
    }
}
