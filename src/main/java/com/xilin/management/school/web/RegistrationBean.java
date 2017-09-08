package com.xilin.management.school.web;
import com.xilin.management.school.model.Family;
import com.xilin.management.school.model.FamilyRepository;
import com.xilin.management.school.model.Registration;
import com.xilin.management.school.model.RegistrationRepository;
import com.xilin.management.school.model.Semester;
import com.xilin.management.school.model.SemesterRepository;
import com.xilin.management.school.model.Student;
import com.xilin.management.school.model.StudentRepository;
import com.xilin.management.school.web.converter.FamilyConverter;
import com.xilin.management.school.web.converter.SemesterConverter;
import com.xilin.management.school.web.converter.StudentConverter;
import com.xilin.management.school.web.util.MessageFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.validator.LengthValidator;
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.message.Message;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.component.spinner.Spinner;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@ManagedBean(name = "registrationBean")
@SessionScoped
@Configurable
public class RegistrationBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
    RegistrationRepository registrationRepository;

	@Autowired
    FamilyRepository familyRepository;

	@Autowired
    SemesterRepository semesterRepository;

	@Autowired
    StudentRepository studentRepository;

	private String name = "Registrations";

	private Registration registration;

	private List<Registration> allRegistrations;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("registrationsemesterids");
        columns.add("coursecode");
        columns.add("ordercode");
        columns.add("registereddate");
        columns.add("paiddate");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Registration> getAllRegistrations() {
        return allRegistrations;
    }

	public void setAllRegistrations(List<Registration> allRegistrations) {
        this.allRegistrations = allRegistrations;
    }

	public String findAllRegistrations() {
        allRegistrations = registrationRepository.findAll();
        dataVisible = !allRegistrations.isEmpty();
        return null;
    }

	public boolean isDataVisible() {
        return dataVisible;
    }

	public void setDataVisible(boolean dataVisible) {
        this.dataVisible = dataVisible;
    }

	public HtmlPanelGrid getCreatePanelGrid() {
        if (createPanelGrid == null) {
            createPanelGrid = populateCreatePanel();
        }
        return createPanelGrid;
    }

	public void setCreatePanelGrid(HtmlPanelGrid createPanelGrid) {
        this.createPanelGrid = createPanelGrid;
    }

	public HtmlPanelGrid getEditPanelGrid() {
        if (editPanelGrid == null) {
            editPanelGrid = populateEditPanel();
        }
        return editPanelGrid;
    }

	public void setEditPanelGrid(HtmlPanelGrid editPanelGrid) {
        this.editPanelGrid = editPanelGrid;
    }

	public HtmlPanelGrid getViewPanelGrid() {
        return populateViewPanel();
    }

	public void setViewPanelGrid(HtmlPanelGrid viewPanelGrid) {
        this.viewPanelGrid = viewPanelGrid;
    }

	public HtmlPanelGrid populateCreatePanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        OutputLabel familyidCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        familyidCreateOutput.setFor("familyidCreateInput");
        familyidCreateOutput.setId("familyidCreateOutput");
        familyidCreateOutput.setValue("Familyid:");
        htmlPanelGrid.getChildren().add(familyidCreateOutput);
        
        AutoComplete familyidCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        familyidCreateInput.setId("familyidCreateInput");
        familyidCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.familyid}", Family.class));
        familyidCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{registrationBean.completeFamilyid}", List.class, new Class[] { String.class }));
        familyidCreateInput.setDropdown(true);
        familyidCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "familyid", String.class));
        familyidCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{familyid.type} #{familyid.fatherlastname} #{familyid.fatherfirstname} #{familyid.fathermiddlename}", String.class));
        familyidCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{familyid}", Family.class));
        familyidCreateInput.setConverter(new FamilyConverter());
        familyidCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(familyidCreateInput);
        
        Message familyidCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        familyidCreateInputMessage.setId("familyidCreateInputMessage");
        familyidCreateInputMessage.setFor("familyidCreateInput");
        familyidCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(familyidCreateInputMessage);
        
        OutputLabel semesteridCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        semesteridCreateOutput.setFor("semesteridCreateInput");
        semesteridCreateOutput.setId("semesteridCreateOutput");
        semesteridCreateOutput.setValue("Semesterid:");
        htmlPanelGrid.getChildren().add(semesteridCreateOutput);
        
        AutoComplete semesteridCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        semesteridCreateInput.setId("semesteridCreateInput");
        semesteridCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.semesterid}", Semester.class));
        semesteridCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{registrationBean.completeSemesterid}", List.class, new Class[] { String.class }));
        semesteridCreateInput.setDropdown(true);
        semesteridCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "semesterid", String.class));
        semesteridCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{semesterid.description} #{semesterid.startdate} #{semesterid.enddate} #{semesterid.updatedtime}", String.class));
        semesteridCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{semesterid}", Semester.class));
        semesteridCreateInput.setConverter(new SemesterConverter());
        semesteridCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(semesteridCreateInput);
        
        Message semesteridCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        semesteridCreateInputMessage.setId("semesteridCreateInputMessage");
        semesteridCreateInputMessage.setFor("semesteridCreateInput");
        semesteridCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(semesteridCreateInputMessage);
        
        OutputLabel studentidCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        studentidCreateOutput.setFor("studentidCreateInput");
        studentidCreateOutput.setId("studentidCreateOutput");
        studentidCreateOutput.setValue("Studentid:");
        htmlPanelGrid.getChildren().add(studentidCreateOutput);
        
        AutoComplete studentidCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        studentidCreateInput.setId("studentidCreateInput");
        studentidCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.studentid}", Student.class));
        studentidCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{registrationBean.completeStudentid}", List.class, new Class[] { String.class }));
        studentidCreateInput.setDropdown(true);
        studentidCreateInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "studentid", String.class));
        studentidCreateInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{studentid.lastname} #{studentid.firstname} #{studentid.middlename} #{studentid.chinesename}", String.class));
        studentidCreateInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{studentid}", Student.class));
        studentidCreateInput.setConverter(new StudentConverter());
        studentidCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(studentidCreateInput);
        
        Message studentidCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        studentidCreateInputMessage.setId("studentidCreateInputMessage");
        studentidCreateInputMessage.setFor("studentidCreateInput");
        studentidCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(studentidCreateInputMessage);
        
        OutputLabel registrationsemesteridsCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        registrationsemesteridsCreateOutput.setFor("registrationsemesteridsCreateInput");
        registrationsemesteridsCreateOutput.setId("registrationsemesteridsCreateOutput");
        registrationsemesteridsCreateOutput.setValue("Registrationsemesterids:");
        htmlPanelGrid.getChildren().add(registrationsemesteridsCreateOutput);
        
        InputTextarea registrationsemesteridsCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        registrationsemesteridsCreateInput.setId("registrationsemesteridsCreateInput");
        registrationsemesteridsCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.registrationsemesterids}", String.class));
        LengthValidator registrationsemesteridsCreateInputValidator = new LengthValidator();
        registrationsemesteridsCreateInputValidator.setMaximum(100);
        registrationsemesteridsCreateInput.addValidator(registrationsemesteridsCreateInputValidator);
        registrationsemesteridsCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(registrationsemesteridsCreateInput);
        
        Message registrationsemesteridsCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        registrationsemesteridsCreateInputMessage.setId("registrationsemesteridsCreateInputMessage");
        registrationsemesteridsCreateInputMessage.setFor("registrationsemesteridsCreateInput");
        registrationsemesteridsCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(registrationsemesteridsCreateInputMessage);
        
        OutputLabel coursecodeCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        coursecodeCreateOutput.setFor("coursecodeCreateInput");
        coursecodeCreateOutput.setId("coursecodeCreateOutput");
        coursecodeCreateOutput.setValue("Coursecode:");
        htmlPanelGrid.getChildren().add(coursecodeCreateOutput);
        
        InputText coursecodeCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        coursecodeCreateInput.setId("coursecodeCreateInput");
        coursecodeCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.coursecode}", String.class));
        LengthValidator coursecodeCreateInputValidator = new LengthValidator();
        coursecodeCreateInputValidator.setMaximum(20);
        coursecodeCreateInput.addValidator(coursecodeCreateInputValidator);
        coursecodeCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(coursecodeCreateInput);
        
        Message coursecodeCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        coursecodeCreateInputMessage.setId("coursecodeCreateInputMessage");
        coursecodeCreateInputMessage.setFor("coursecodeCreateInput");
        coursecodeCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(coursecodeCreateInputMessage);
        
        OutputLabel ordercodeCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        ordercodeCreateOutput.setFor("ordercodeCreateInput");
        ordercodeCreateOutput.setId("ordercodeCreateOutput");
        ordercodeCreateOutput.setValue("Ordercode:");
        htmlPanelGrid.getChildren().add(ordercodeCreateOutput);
        
        Spinner ordercodeCreateInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        ordercodeCreateInput.setId("ordercodeCreateInput");
        ordercodeCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.ordercode}", Integer.class));
        ordercodeCreateInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(ordercodeCreateInput);
        
        Message ordercodeCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        ordercodeCreateInputMessage.setId("ordercodeCreateInputMessage");
        ordercodeCreateInputMessage.setFor("ordercodeCreateInput");
        ordercodeCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(ordercodeCreateInputMessage);
        
        OutputLabel registereddateCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        registereddateCreateOutput.setFor("registereddateCreateInput");
        registereddateCreateOutput.setId("registereddateCreateOutput");
        registereddateCreateOutput.setValue("Registereddate:");
        htmlPanelGrid.getChildren().add(registereddateCreateOutput);
        
        Calendar registereddateCreateInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        registereddateCreateInput.setId("registereddateCreateInput");
        registereddateCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.registereddate}", Date.class));
        registereddateCreateInput.setNavigator(true);
        registereddateCreateInput.setEffect("slideDown");
        registereddateCreateInput.setPattern("dd/MM/yyyy");
        registereddateCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(registereddateCreateInput);
        
        Message registereddateCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        registereddateCreateInputMessage.setId("registereddateCreateInputMessage");
        registereddateCreateInputMessage.setFor("registereddateCreateInput");
        registereddateCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(registereddateCreateInputMessage);
        
        OutputLabel paiddateCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        paiddateCreateOutput.setFor("paiddateCreateInput");
        paiddateCreateOutput.setId("paiddateCreateOutput");
        paiddateCreateOutput.setValue("Paiddate:");
        htmlPanelGrid.getChildren().add(paiddateCreateOutput);
        
        Calendar paiddateCreateInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        paiddateCreateInput.setId("paiddateCreateInput");
        paiddateCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.paiddate}", Date.class));
        paiddateCreateInput.setNavigator(true);
        paiddateCreateInput.setEffect("slideDown");
        paiddateCreateInput.setPattern("dd/MM/yyyy");
        paiddateCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(paiddateCreateInput);
        
        Message paiddateCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        paiddateCreateInputMessage.setId("paiddateCreateInputMessage");
        paiddateCreateInputMessage.setFor("paiddateCreateInput");
        paiddateCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(paiddateCreateInputMessage);
        
        OutputLabel statusCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        statusCreateOutput.setFor("statusCreateInput");
        statusCreateOutput.setId("statusCreateOutput");
        statusCreateOutput.setValue("Status:");
        htmlPanelGrid.getChildren().add(statusCreateOutput);
        
        Spinner statusCreateInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        statusCreateInput.setId("statusCreateInput");
        statusCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.status}", Integer.class));
        statusCreateInput.setRequired(true);
        
        htmlPanelGrid.getChildren().add(statusCreateInput);
        
        Message statusCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        statusCreateInputMessage.setId("statusCreateInputMessage");
        statusCreateInputMessage.setFor("statusCreateInput");
        statusCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(statusCreateInputMessage);
        
        OutputLabel activeCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        activeCreateOutput.setFor("activeCreateInput");
        activeCreateOutput.setId("activeCreateOutput");
        activeCreateOutput.setValue("Active:");
        htmlPanelGrid.getChildren().add(activeCreateOutput);
        
        SelectBooleanCheckbox activeCreateInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        activeCreateInput.setId("activeCreateInput");
        activeCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.active}", Boolean.class));
        activeCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(activeCreateInput);
        
        Message activeCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        activeCreateInputMessage.setId("activeCreateInputMessage");
        activeCreateInputMessage.setFor("activeCreateInput");
        activeCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(activeCreateInputMessage);
        
        OutputLabel updatedbyCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        updatedbyCreateOutput.setFor("updatedbyCreateInput");
        updatedbyCreateOutput.setId("updatedbyCreateOutput");
        updatedbyCreateOutput.setValue("Updatedby:");
        htmlPanelGrid.getChildren().add(updatedbyCreateOutput);
        
        InputTextarea updatedbyCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        updatedbyCreateInput.setId("updatedbyCreateInput");
        updatedbyCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.updatedby}", String.class));
        LengthValidator updatedbyCreateInputValidator = new LengthValidator();
        updatedbyCreateInputValidator.setMaximum(40);
        updatedbyCreateInput.addValidator(updatedbyCreateInputValidator);
        updatedbyCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(updatedbyCreateInput);
        
        Message updatedbyCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        updatedbyCreateInputMessage.setId("updatedbyCreateInputMessage");
        updatedbyCreateInputMessage.setFor("updatedbyCreateInput");
        updatedbyCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(updatedbyCreateInputMessage);
        
        OutputLabel updatedtimeCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        updatedtimeCreateOutput.setFor("updatedtimeCreateInput");
        updatedtimeCreateOutput.setId("updatedtimeCreateOutput");
        updatedtimeCreateOutput.setValue("Updatedtime:");
        htmlPanelGrid.getChildren().add(updatedtimeCreateOutput);
        
        Calendar updatedtimeCreateInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        updatedtimeCreateInput.setId("updatedtimeCreateInput");
        updatedtimeCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.updatedtime}", Date.class));
        updatedtimeCreateInput.setNavigator(true);
        updatedtimeCreateInput.setEffect("slideDown");
        updatedtimeCreateInput.setPattern("dd/MM/yyyy");
        updatedtimeCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(updatedtimeCreateInput);
        
        Message updatedtimeCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        updatedtimeCreateInputMessage.setId("updatedtimeCreateInputMessage");
        updatedtimeCreateInputMessage.setFor("updatedtimeCreateInput");
        updatedtimeCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(updatedtimeCreateInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateEditPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        OutputLabel familyidEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        familyidEditOutput.setFor("familyidEditInput");
        familyidEditOutput.setId("familyidEditOutput");
        familyidEditOutput.setValue("Familyid:");
        htmlPanelGrid.getChildren().add(familyidEditOutput);
        
        AutoComplete familyidEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        familyidEditInput.setId("familyidEditInput");
        familyidEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.familyid}", Family.class));
        familyidEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{registrationBean.completeFamilyid}", List.class, new Class[] { String.class }));
        familyidEditInput.setDropdown(true);
        familyidEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "familyid", String.class));
        familyidEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{familyid.type} #{familyid.fatherlastname} #{familyid.fatherfirstname} #{familyid.fathermiddlename}", String.class));
        familyidEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{familyid}", Family.class));
        familyidEditInput.setConverter(new FamilyConverter());
        familyidEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(familyidEditInput);
        
        Message familyidEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        familyidEditInputMessage.setId("familyidEditInputMessage");
        familyidEditInputMessage.setFor("familyidEditInput");
        familyidEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(familyidEditInputMessage);
        
        OutputLabel semesteridEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        semesteridEditOutput.setFor("semesteridEditInput");
        semesteridEditOutput.setId("semesteridEditOutput");
        semesteridEditOutput.setValue("Semesterid:");
        htmlPanelGrid.getChildren().add(semesteridEditOutput);
        
        AutoComplete semesteridEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        semesteridEditInput.setId("semesteridEditInput");
        semesteridEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.semesterid}", Semester.class));
        semesteridEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{registrationBean.completeSemesterid}", List.class, new Class[] { String.class }));
        semesteridEditInput.setDropdown(true);
        semesteridEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "semesterid", String.class));
        semesteridEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{semesterid.description} #{semesterid.startdate} #{semesterid.enddate} #{semesterid.updatedtime}", String.class));
        semesteridEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{semesterid}", Semester.class));
        semesteridEditInput.setConverter(new SemesterConverter());
        semesteridEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(semesteridEditInput);
        
        Message semesteridEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        semesteridEditInputMessage.setId("semesteridEditInputMessage");
        semesteridEditInputMessage.setFor("semesteridEditInput");
        semesteridEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(semesteridEditInputMessage);
        
        OutputLabel studentidEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        studentidEditOutput.setFor("studentidEditInput");
        studentidEditOutput.setId("studentidEditOutput");
        studentidEditOutput.setValue("Studentid:");
        htmlPanelGrid.getChildren().add(studentidEditOutput);
        
        AutoComplete studentidEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        studentidEditInput.setId("studentidEditInput");
        studentidEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.studentid}", Student.class));
        studentidEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{registrationBean.completeStudentid}", List.class, new Class[] { String.class }));
        studentidEditInput.setDropdown(true);
        studentidEditInput.setValueExpression("var", expressionFactory.createValueExpression(elContext, "studentid", String.class));
        studentidEditInput.setValueExpression("itemLabel", expressionFactory.createValueExpression(elContext, "#{studentid.lastname} #{studentid.firstname} #{studentid.middlename} #{studentid.chinesename}", String.class));
        studentidEditInput.setValueExpression("itemValue", expressionFactory.createValueExpression(elContext, "#{studentid}", Student.class));
        studentidEditInput.setConverter(new StudentConverter());
        studentidEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(studentidEditInput);
        
        Message studentidEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        studentidEditInputMessage.setId("studentidEditInputMessage");
        studentidEditInputMessage.setFor("studentidEditInput");
        studentidEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(studentidEditInputMessage);
        
        OutputLabel registrationsemesteridsEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        registrationsemesteridsEditOutput.setFor("registrationsemesteridsEditInput");
        registrationsemesteridsEditOutput.setId("registrationsemesteridsEditOutput");
        registrationsemesteridsEditOutput.setValue("Registrationsemesterids:");
        htmlPanelGrid.getChildren().add(registrationsemesteridsEditOutput);
        
        InputTextarea registrationsemesteridsEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        registrationsemesteridsEditInput.setId("registrationsemesteridsEditInput");
        registrationsemesteridsEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.registrationsemesterids}", String.class));
        LengthValidator registrationsemesteridsEditInputValidator = new LengthValidator();
        registrationsemesteridsEditInputValidator.setMaximum(100);
        registrationsemesteridsEditInput.addValidator(registrationsemesteridsEditInputValidator);
        registrationsemesteridsEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(registrationsemesteridsEditInput);
        
        Message registrationsemesteridsEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        registrationsemesteridsEditInputMessage.setId("registrationsemesteridsEditInputMessage");
        registrationsemesteridsEditInputMessage.setFor("registrationsemesteridsEditInput");
        registrationsemesteridsEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(registrationsemesteridsEditInputMessage);
        
        OutputLabel coursecodeEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        coursecodeEditOutput.setFor("coursecodeEditInput");
        coursecodeEditOutput.setId("coursecodeEditOutput");
        coursecodeEditOutput.setValue("Coursecode:");
        htmlPanelGrid.getChildren().add(coursecodeEditOutput);
        
        InputText coursecodeEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        coursecodeEditInput.setId("coursecodeEditInput");
        coursecodeEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.coursecode}", String.class));
        LengthValidator coursecodeEditInputValidator = new LengthValidator();
        coursecodeEditInputValidator.setMaximum(20);
        coursecodeEditInput.addValidator(coursecodeEditInputValidator);
        coursecodeEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(coursecodeEditInput);
        
        Message coursecodeEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        coursecodeEditInputMessage.setId("coursecodeEditInputMessage");
        coursecodeEditInputMessage.setFor("coursecodeEditInput");
        coursecodeEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(coursecodeEditInputMessage);
        
        OutputLabel ordercodeEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        ordercodeEditOutput.setFor("ordercodeEditInput");
        ordercodeEditOutput.setId("ordercodeEditOutput");
        ordercodeEditOutput.setValue("Ordercode:");
        htmlPanelGrid.getChildren().add(ordercodeEditOutput);
        
        Spinner ordercodeEditInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        ordercodeEditInput.setId("ordercodeEditInput");
        ordercodeEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.ordercode}", Integer.class));
        ordercodeEditInput.setRequired(false);
        
        htmlPanelGrid.getChildren().add(ordercodeEditInput);
        
        Message ordercodeEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        ordercodeEditInputMessage.setId("ordercodeEditInputMessage");
        ordercodeEditInputMessage.setFor("ordercodeEditInput");
        ordercodeEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(ordercodeEditInputMessage);
        
        OutputLabel registereddateEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        registereddateEditOutput.setFor("registereddateEditInput");
        registereddateEditOutput.setId("registereddateEditOutput");
        registereddateEditOutput.setValue("Registereddate:");
        htmlPanelGrid.getChildren().add(registereddateEditOutput);
        
        Calendar registereddateEditInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        registereddateEditInput.setId("registereddateEditInput");
        registereddateEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.registereddate}", Date.class));
        registereddateEditInput.setNavigator(true);
        registereddateEditInput.setEffect("slideDown");
        registereddateEditInput.setPattern("dd/MM/yyyy");
        registereddateEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(registereddateEditInput);
        
        Message registereddateEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        registereddateEditInputMessage.setId("registereddateEditInputMessage");
        registereddateEditInputMessage.setFor("registereddateEditInput");
        registereddateEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(registereddateEditInputMessage);
        
        OutputLabel paiddateEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        paiddateEditOutput.setFor("paiddateEditInput");
        paiddateEditOutput.setId("paiddateEditOutput");
        paiddateEditOutput.setValue("Paiddate:");
        htmlPanelGrid.getChildren().add(paiddateEditOutput);
        
        Calendar paiddateEditInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        paiddateEditInput.setId("paiddateEditInput");
        paiddateEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.paiddate}", Date.class));
        paiddateEditInput.setNavigator(true);
        paiddateEditInput.setEffect("slideDown");
        paiddateEditInput.setPattern("dd/MM/yyyy");
        paiddateEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(paiddateEditInput);
        
        Message paiddateEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        paiddateEditInputMessage.setId("paiddateEditInputMessage");
        paiddateEditInputMessage.setFor("paiddateEditInput");
        paiddateEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(paiddateEditInputMessage);
        
        OutputLabel statusEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        statusEditOutput.setFor("statusEditInput");
        statusEditOutput.setId("statusEditOutput");
        statusEditOutput.setValue("Status:");
        htmlPanelGrid.getChildren().add(statusEditOutput);
        
        Spinner statusEditInput = (Spinner) application.createComponent(Spinner.COMPONENT_TYPE);
        statusEditInput.setId("statusEditInput");
        statusEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.status}", Integer.class));
        statusEditInput.setRequired(true);
        
        htmlPanelGrid.getChildren().add(statusEditInput);
        
        Message statusEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        statusEditInputMessage.setId("statusEditInputMessage");
        statusEditInputMessage.setFor("statusEditInput");
        statusEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(statusEditInputMessage);
        
        OutputLabel activeEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        activeEditOutput.setFor("activeEditInput");
        activeEditOutput.setId("activeEditOutput");
        activeEditOutput.setValue("Active:");
        htmlPanelGrid.getChildren().add(activeEditOutput);
        
        SelectBooleanCheckbox activeEditInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        activeEditInput.setId("activeEditInput");
        activeEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.active}", Boolean.class));
        activeEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(activeEditInput);
        
        Message activeEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        activeEditInputMessage.setId("activeEditInputMessage");
        activeEditInputMessage.setFor("activeEditInput");
        activeEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(activeEditInputMessage);
        
        OutputLabel updatedbyEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        updatedbyEditOutput.setFor("updatedbyEditInput");
        updatedbyEditOutput.setId("updatedbyEditOutput");
        updatedbyEditOutput.setValue("Updatedby:");
        htmlPanelGrid.getChildren().add(updatedbyEditOutput);
        
        InputTextarea updatedbyEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        updatedbyEditInput.setId("updatedbyEditInput");
        updatedbyEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.updatedby}", String.class));
        LengthValidator updatedbyEditInputValidator = new LengthValidator();
        updatedbyEditInputValidator.setMaximum(40);
        updatedbyEditInput.addValidator(updatedbyEditInputValidator);
        updatedbyEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(updatedbyEditInput);
        
        Message updatedbyEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        updatedbyEditInputMessage.setId("updatedbyEditInputMessage");
        updatedbyEditInputMessage.setFor("updatedbyEditInput");
        updatedbyEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(updatedbyEditInputMessage);
        
        OutputLabel updatedtimeEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        updatedtimeEditOutput.setFor("updatedtimeEditInput");
        updatedtimeEditOutput.setId("updatedtimeEditOutput");
        updatedtimeEditOutput.setValue("Updatedtime:");
        htmlPanelGrid.getChildren().add(updatedtimeEditOutput);
        
        Calendar updatedtimeEditInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        updatedtimeEditInput.setId("updatedtimeEditInput");
        updatedtimeEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.updatedtime}", Date.class));
        updatedtimeEditInput.setNavigator(true);
        updatedtimeEditInput.setEffect("slideDown");
        updatedtimeEditInput.setPattern("dd/MM/yyyy");
        updatedtimeEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(updatedtimeEditInput);
        
        Message updatedtimeEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        updatedtimeEditInputMessage.setId("updatedtimeEditInputMessage");
        updatedtimeEditInputMessage.setFor("updatedtimeEditInput");
        updatedtimeEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(updatedtimeEditInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateViewPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText familyidLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        familyidLabel.setId("familyidLabel");
        familyidLabel.setValue("Familyid:");
        htmlPanelGrid.getChildren().add(familyidLabel);
        
        HtmlOutputText familyidValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        familyidValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.familyid}", Family.class));
        familyidValue.setConverter(new FamilyConverter());
        htmlPanelGrid.getChildren().add(familyidValue);
        
        HtmlOutputText semesteridLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        semesteridLabel.setId("semesteridLabel");
        semesteridLabel.setValue("Semesterid:");
        htmlPanelGrid.getChildren().add(semesteridLabel);
        
        HtmlOutputText semesteridValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        semesteridValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.semesterid}", Semester.class));
        semesteridValue.setConverter(new SemesterConverter());
        htmlPanelGrid.getChildren().add(semesteridValue);
        
        HtmlOutputText studentidLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        studentidLabel.setId("studentidLabel");
        studentidLabel.setValue("Studentid:");
        htmlPanelGrid.getChildren().add(studentidLabel);
        
        HtmlOutputText studentidValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        studentidValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.studentid}", Student.class));
        studentidValue.setConverter(new StudentConverter());
        htmlPanelGrid.getChildren().add(studentidValue);
        
        HtmlOutputText registrationsemesteridsLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        registrationsemesteridsLabel.setId("registrationsemesteridsLabel");
        registrationsemesteridsLabel.setValue("Registrationsemesterids:");
        htmlPanelGrid.getChildren().add(registrationsemesteridsLabel);
        
        InputTextarea registrationsemesteridsValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        registrationsemesteridsValue.setId("registrationsemesteridsValue");
        registrationsemesteridsValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.registrationsemesterids}", String.class));
        registrationsemesteridsValue.setReadonly(true);
        registrationsemesteridsValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(registrationsemesteridsValue);
        
        HtmlOutputText coursecodeLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        coursecodeLabel.setId("coursecodeLabel");
        coursecodeLabel.setValue("Coursecode:");
        htmlPanelGrid.getChildren().add(coursecodeLabel);
        
        HtmlOutputText coursecodeValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        coursecodeValue.setId("coursecodeValue");
        coursecodeValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.coursecode}", String.class));
        htmlPanelGrid.getChildren().add(coursecodeValue);
        
        HtmlOutputText ordercodeLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        ordercodeLabel.setId("ordercodeLabel");
        ordercodeLabel.setValue("Ordercode:");
        htmlPanelGrid.getChildren().add(ordercodeLabel);
        
        HtmlOutputText ordercodeValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        ordercodeValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.ordercode}", String.class));
        htmlPanelGrid.getChildren().add(ordercodeValue);
        
        HtmlOutputText registereddateLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        registereddateLabel.setId("registereddateLabel");
        registereddateLabel.setValue("Registereddate:");
        htmlPanelGrid.getChildren().add(registereddateLabel);
        
        HtmlOutputText registereddateValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        registereddateValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.registereddate}", Calendar.class));
        DateTimeConverter registereddateValueConverter = (DateTimeConverter) application.createConverter(DateTimeConverter.CONVERTER_ID);
        registereddateValueConverter.setPattern("dd/MM/yyyy");
        registereddateValue.setConverter(registereddateValueConverter);
        htmlPanelGrid.getChildren().add(registereddateValue);
        
        HtmlOutputText paiddateLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        paiddateLabel.setId("paiddateLabel");
        paiddateLabel.setValue("Paiddate:");
        htmlPanelGrid.getChildren().add(paiddateLabel);
        
        HtmlOutputText paiddateValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        paiddateValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.paiddate}", Calendar.class));
        DateTimeConverter paiddateValueConverter = (DateTimeConverter) application.createConverter(DateTimeConverter.CONVERTER_ID);
        paiddateValueConverter.setPattern("dd/MM/yyyy");
        paiddateValue.setConverter(paiddateValueConverter);
        htmlPanelGrid.getChildren().add(paiddateValue);
        
        HtmlOutputText statusLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        statusLabel.setId("statusLabel");
        statusLabel.setValue("Status:");
        htmlPanelGrid.getChildren().add(statusLabel);
        
        HtmlOutputText statusValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        statusValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.status}", String.class));
        htmlPanelGrid.getChildren().add(statusValue);
        
        HtmlOutputText activeLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        activeLabel.setId("activeLabel");
        activeLabel.setValue("Active:");
        htmlPanelGrid.getChildren().add(activeLabel);
        
        HtmlOutputText activeValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        activeValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.active}", String.class));
        htmlPanelGrid.getChildren().add(activeValue);
        
        HtmlOutputText updatedbyLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        updatedbyLabel.setId("updatedbyLabel");
        updatedbyLabel.setValue("Updatedby:");
        htmlPanelGrid.getChildren().add(updatedbyLabel);
        
        InputTextarea updatedbyValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        updatedbyValue.setId("updatedbyValue");
        updatedbyValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.updatedby}", String.class));
        updatedbyValue.setReadonly(true);
        updatedbyValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(updatedbyValue);
        
        HtmlOutputText updatedtimeLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        updatedtimeLabel.setId("updatedtimeLabel");
        updatedtimeLabel.setValue("Updatedtime:");
        htmlPanelGrid.getChildren().add(updatedtimeLabel);
        
        HtmlOutputText updatedtimeValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        updatedtimeValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{registrationBean.registration.updatedtime}", Calendar.class));
        DateTimeConverter updatedtimeValueConverter = (DateTimeConverter) application.createConverter(DateTimeConverter.CONVERTER_ID);
        updatedtimeValueConverter.setPattern("dd/MM/yyyy");
        updatedtimeValue.setConverter(updatedtimeValueConverter);
        htmlPanelGrid.getChildren().add(updatedtimeValue);
        
        return htmlPanelGrid;
    }

	public Registration getRegistration() {
        if (registration == null) {
            registration = new Registration();
        }
        return registration;
    }

	public void setRegistration(Registration registration) {
        this.registration = registration;
    }

	public List<Family> completeFamilyid(String query) {
        List<Family> suggestions = new ArrayList<Family>();
        for (Family family : familyRepository.findAll()) {
            String familyStr = String.valueOf(family.getType() +  " "  + family.getFatherlastname() +  " "  + family.getFatherfirstname() +  " "  + family.getFathermiddlename());
            if (familyStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(family);
            }
        }
        return suggestions;
    }

	public List<Semester> completeSemesterid(String query) {
        List<Semester> suggestions = new ArrayList<Semester>();
        for (Semester semester : semesterRepository.findAll()) {
            String semesterStr = String.valueOf(semester.getDescription() +  " "  + semester.getStartdate() +  " "  + semester.getEnddate() +  " "  + semester.getUpdatedtime());
            if (semesterStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(semester);
            }
        }
        return suggestions;
    }

	public List<Student> completeStudentid(String query) {
        List<Student> suggestions = new ArrayList<Student>();
        for (Student student : studentRepository.findAll()) {
            String studentStr = String.valueOf(student.getLastname() +  " "  + student.getFirstname() +  " "  + student.getMiddlename() +  " "  + student.getChinesename());
            if (studentStr.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(student);
            }
        }
        return suggestions;
    }

	public String onEdit() {
        return null;
    }

	public boolean isCreateDialogVisible() {
        return createDialogVisible;
    }

	public void setCreateDialogVisible(boolean createDialogVisible) {
        this.createDialogVisible = createDialogVisible;
    }

	public String displayList() {
        createDialogVisible = false;
        findAllRegistrations();
        return "registration";
    }

	public String displayCreateDialog() {
        registration = new Registration();
        createDialogVisible = true;
        return "registration";
    }

	public String persist() {
        String message = "";
        if (registration.getId() != null) {
            registrationRepository.save(registration);
            message = "message_successfully_updated";
        } else {
            registrationRepository.save(registration);
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Registration");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllRegistrations();
    }

	public String delete() {
        registrationRepository.delete(registration);
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Registration");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllRegistrations();
    }

	public void reset() {
        registration = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}
