package com.xilin.management.school.web.converter;
import com.xilin.management.school.model.Student;
import com.xilin.management.school.model.StudentRepository;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@FacesConverter("com.xilin.management.school.web.converter.StudentConverter")
@Configurable
public class StudentConverter implements Converter {

	@Autowired
    StudentRepository studentRepository;

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        Integer id = Integer.parseInt(value);
        return studentRepository.findOne(id);
    }

	public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Student ? ((Student) value).getId().toString() : "";
    }
}
