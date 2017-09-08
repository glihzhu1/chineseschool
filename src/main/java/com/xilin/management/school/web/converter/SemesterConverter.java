package com.xilin.management.school.web.converter;
import com.xilin.management.school.model.Semester;
import com.xilin.management.school.model.SemesterRepository;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@FacesConverter("com.xilin.management.school.web.converter.SemesterConverter")
public class SemesterConverter implements Converter {

	@Autowired
    SemesterRepository semesterRepository;

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Integer id = Integer.parseInt(value);
        return semesterRepository.findOne(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Semester ? ((Semester) value).getId().toString() : "";
    }
}
