package com.xilin.management.school.web;
import com.xilin.management.school.model.Family;
import com.xilin.management.school.model.FamilyRepository;
import com.xilin.management.school.model.Registration;
import com.xilin.management.school.model.Student;
import com.xilin.management.school.model.StudentRepository;
import com.xilin.management.school.web.converter.FamilyConverter;
import com.xilin.management.school.web.util.MessageFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@ManagedBean(name = "studentBean")
@SessionScoped
@Configurable
public class StudentBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
    StudentRepository studentRepository;

	@Autowired
    FamilyRepository familyRepository;

	private String name = "Students";

	private Student student;

	private List<Student> allStudents;

	private boolean dataVisible = false;

	private List<String> columns;

	private HtmlPanelGrid createPanelGrid;

	private HtmlPanelGrid editPanelGrid;

	private HtmlPanelGrid viewPanelGrid;

	private boolean createDialogVisible = false;

	private List<Registration> selectedRegistrations;

	@PostConstruct
    public void init() {
        columns = new ArrayList<String>();
        columns.add("lastname");
        columns.add("firstname");
        columns.add("middlename");
        columns.add("chinesename");
        columns.add("dob");
    }

	public String getName() {
        return name;
    }

	public List<String> getColumns() {
        return columns;
    }

	public List<Student> getAllStudents() {
        return allStudents;
    }

	public void setAllStudents(List<Student> allStudents) {
        this.allStudents = allStudents;
    }

	public String findAllStudents() {
        allStudents = studentRepository.findAll();
        dataVisible = !allStudents.isEmpty();
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
        
        HtmlOutputText registrationsCreateOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        registrationsCreateOutput.setId("registrationsCreateOutput");
        registrationsCreateOutput.setValue("Registrations:");
        htmlPanelGrid.getChildren().add(registrationsCreateOutput);
        
        HtmlOutputText registrationsCreateInput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        registrationsCreateInput.setId("registrationsCreateInput");
        registrationsCreateInput.setValue("This relationship is managed from the Registration side");
        htmlPanelGrid.getChildren().add(registrationsCreateInput);
        
        Message registrationsCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        registrationsCreateInputMessage.setId("registrationsCreateInputMessage");
        registrationsCreateInputMessage.setFor("registrationsCreateInput");
        registrationsCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(registrationsCreateInputMessage);
        
        OutputLabel familyidCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        familyidCreateOutput.setFor("familyidCreateInput");
        familyidCreateOutput.setId("familyidCreateOutput");
        familyidCreateOutput.setValue("Familyid:");
        htmlPanelGrid.getChildren().add(familyidCreateOutput);
        
        AutoComplete familyidCreateInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        familyidCreateInput.setId("familyidCreateInput");
        familyidCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.familyid}", Family.class));
        familyidCreateInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{studentBean.completeFamilyid}", List.class, new Class[] { String.class }));
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
        
        OutputLabel lastnameCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        lastnameCreateOutput.setFor("lastnameCreateInput");
        lastnameCreateOutput.setId("lastnameCreateOutput");
        lastnameCreateOutput.setValue("Lastname:");
        htmlPanelGrid.getChildren().add(lastnameCreateOutput);
        
        InputTextarea lastnameCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        lastnameCreateInput.setId("lastnameCreateInput");
        lastnameCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.lastname}", String.class));
        LengthValidator lastnameCreateInputValidator = new LengthValidator();
        lastnameCreateInputValidator.setMaximum(50);
        lastnameCreateInput.addValidator(lastnameCreateInputValidator);
        lastnameCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(lastnameCreateInput);
        
        Message lastnameCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        lastnameCreateInputMessage.setId("lastnameCreateInputMessage");
        lastnameCreateInputMessage.setFor("lastnameCreateInput");
        lastnameCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(lastnameCreateInputMessage);
        
        OutputLabel firstnameCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        firstnameCreateOutput.setFor("firstnameCreateInput");
        firstnameCreateOutput.setId("firstnameCreateOutput");
        firstnameCreateOutput.setValue("Firstname:");
        htmlPanelGrid.getChildren().add(firstnameCreateOutput);
        
        InputTextarea firstnameCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        firstnameCreateInput.setId("firstnameCreateInput");
        firstnameCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.firstname}", String.class));
        LengthValidator firstnameCreateInputValidator = new LengthValidator();
        firstnameCreateInputValidator.setMaximum(50);
        firstnameCreateInput.addValidator(firstnameCreateInputValidator);
        firstnameCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(firstnameCreateInput);
        
        Message firstnameCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        firstnameCreateInputMessage.setId("firstnameCreateInputMessage");
        firstnameCreateInputMessage.setFor("firstnameCreateInput");
        firstnameCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(firstnameCreateInputMessage);
        
        OutputLabel middlenameCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        middlenameCreateOutput.setFor("middlenameCreateInput");
        middlenameCreateOutput.setId("middlenameCreateOutput");
        middlenameCreateOutput.setValue("Middlename:");
        htmlPanelGrid.getChildren().add(middlenameCreateOutput);
        
        InputTextarea middlenameCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        middlenameCreateInput.setId("middlenameCreateInput");
        middlenameCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.middlename}", String.class));
        LengthValidator middlenameCreateInputValidator = new LengthValidator();
        middlenameCreateInputValidator.setMaximum(50);
        middlenameCreateInput.addValidator(middlenameCreateInputValidator);
        middlenameCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(middlenameCreateInput);
        
        Message middlenameCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        middlenameCreateInputMessage.setId("middlenameCreateInputMessage");
        middlenameCreateInputMessage.setFor("middlenameCreateInput");
        middlenameCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(middlenameCreateInputMessage);
        
        OutputLabel chinesenameCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        chinesenameCreateOutput.setFor("chinesenameCreateInput");
        chinesenameCreateOutput.setId("chinesenameCreateOutput");
        chinesenameCreateOutput.setValue("Chinesename:");
        htmlPanelGrid.getChildren().add(chinesenameCreateOutput);
        
        InputTextarea chinesenameCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        chinesenameCreateInput.setId("chinesenameCreateInput");
        chinesenameCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.chinesename}", String.class));
        LengthValidator chinesenameCreateInputValidator = new LengthValidator();
        chinesenameCreateInputValidator.setMaximum(50);
        chinesenameCreateInput.addValidator(chinesenameCreateInputValidator);
        chinesenameCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(chinesenameCreateInput);
        
        Message chinesenameCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        chinesenameCreateInputMessage.setId("chinesenameCreateInputMessage");
        chinesenameCreateInputMessage.setFor("chinesenameCreateInput");
        chinesenameCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(chinesenameCreateInputMessage);
        
        OutputLabel dobCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        dobCreateOutput.setFor("dobCreateInput");
        dobCreateOutput.setId("dobCreateOutput");
        dobCreateOutput.setValue("Dob:");
        htmlPanelGrid.getChildren().add(dobCreateOutput);
        
        Calendar dobCreateInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        dobCreateInput.setId("dobCreateInput");
        dobCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.dob}", Date.class));
        dobCreateInput.setNavigator(true);
        dobCreateInput.setEffect("slideDown");
        dobCreateInput.setPattern("dd/MM/yyyy");
        dobCreateInput.setRequired(false);
        htmlPanelGrid.getChildren().add(dobCreateInput);
        
        Message dobCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        dobCreateInputMessage.setId("dobCreateInputMessage");
        dobCreateInputMessage.setFor("dobCreateInput");
        dobCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(dobCreateInputMessage);
        
        OutputLabel genderCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        genderCreateOutput.setFor("genderCreateInput");
        genderCreateOutput.setId("genderCreateOutput");
        genderCreateOutput.setValue("Gender:");
        htmlPanelGrid.getChildren().add(genderCreateOutput);
        
        InputText genderCreateInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        genderCreateInput.setId("genderCreateInput");
        genderCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.gender}", Character.class));
        genderCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(genderCreateInput);
        
        Message genderCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        genderCreateInputMessage.setId("genderCreateInputMessage");
        genderCreateInputMessage.setFor("genderCreateInput");
        genderCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(genderCreateInputMessage);
        
        OutputLabel activeCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        activeCreateOutput.setFor("activeCreateInput");
        activeCreateOutput.setId("activeCreateOutput");
        activeCreateOutput.setValue("Active:");
        htmlPanelGrid.getChildren().add(activeCreateOutput);
        
        SelectBooleanCheckbox activeCreateInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        activeCreateInput.setId("activeCreateInput");
        activeCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.active}", Boolean.class));
        activeCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(activeCreateInput);
        
        Message activeCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        activeCreateInputMessage.setId("activeCreateInputMessage");
        activeCreateInputMessage.setFor("activeCreateInput");
        activeCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(activeCreateInputMessage);
        
        OutputLabel updatedtimeCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        updatedtimeCreateOutput.setFor("updatedtimeCreateInput");
        updatedtimeCreateOutput.setId("updatedtimeCreateOutput");
        updatedtimeCreateOutput.setValue("Updatedtime:");
        htmlPanelGrid.getChildren().add(updatedtimeCreateOutput);
        
        Calendar updatedtimeCreateInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        updatedtimeCreateInput.setId("updatedtimeCreateInput");
        updatedtimeCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.updatedtime}", Date.class));
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
        
        OutputLabel updatedByCreateOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        updatedByCreateOutput.setFor("updatedByCreateInput");
        updatedByCreateOutput.setId("updatedByCreateOutput");
        updatedByCreateOutput.setValue("Updated By:");
        htmlPanelGrid.getChildren().add(updatedByCreateOutput);
        
        InputTextarea updatedByCreateInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        updatedByCreateInput.setId("updatedByCreateInput");
        updatedByCreateInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.updatedBy}", String.class));
        LengthValidator updatedByCreateInputValidator = new LengthValidator();
        updatedByCreateInputValidator.setMaximum(40);
        updatedByCreateInput.addValidator(updatedByCreateInputValidator);
        updatedByCreateInput.setRequired(true);
        htmlPanelGrid.getChildren().add(updatedByCreateInput);
        
        Message updatedByCreateInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        updatedByCreateInputMessage.setId("updatedByCreateInputMessage");
        updatedByCreateInputMessage.setFor("updatedByCreateInput");
        updatedByCreateInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(updatedByCreateInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateEditPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText registrationsEditOutput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        registrationsEditOutput.setId("registrationsEditOutput");
        registrationsEditOutput.setValue("Registrations:");
        htmlPanelGrid.getChildren().add(registrationsEditOutput);
        
        HtmlOutputText registrationsEditInput = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        registrationsEditInput.setId("registrationsEditInput");
        registrationsEditInput.setValue("This relationship is managed from the Registration side");
        htmlPanelGrid.getChildren().add(registrationsEditInput);
        
        Message registrationsEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        registrationsEditInputMessage.setId("registrationsEditInputMessage");
        registrationsEditInputMessage.setFor("registrationsEditInput");
        registrationsEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(registrationsEditInputMessage);
        
        OutputLabel familyidEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        familyidEditOutput.setFor("familyidEditInput");
        familyidEditOutput.setId("familyidEditOutput");
        familyidEditOutput.setValue("Familyid:");
        htmlPanelGrid.getChildren().add(familyidEditOutput);
        
        AutoComplete familyidEditInput = (AutoComplete) application.createComponent(AutoComplete.COMPONENT_TYPE);
        familyidEditInput.setId("familyidEditInput");
        familyidEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.familyid}", Family.class));
        familyidEditInput.setCompleteMethod(expressionFactory.createMethodExpression(elContext, "#{studentBean.completeFamilyid}", List.class, new Class[] { String.class }));
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
        
        OutputLabel lastnameEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        lastnameEditOutput.setFor("lastnameEditInput");
        lastnameEditOutput.setId("lastnameEditOutput");
        lastnameEditOutput.setValue("Lastname:");
        htmlPanelGrid.getChildren().add(lastnameEditOutput);
        
        InputTextarea lastnameEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        lastnameEditInput.setId("lastnameEditInput");
        lastnameEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.lastname}", String.class));
        LengthValidator lastnameEditInputValidator = new LengthValidator();
        lastnameEditInputValidator.setMaximum(50);
        lastnameEditInput.addValidator(lastnameEditInputValidator);
        lastnameEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(lastnameEditInput);
        
        Message lastnameEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        lastnameEditInputMessage.setId("lastnameEditInputMessage");
        lastnameEditInputMessage.setFor("lastnameEditInput");
        lastnameEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(lastnameEditInputMessage);
        
        OutputLabel firstnameEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        firstnameEditOutput.setFor("firstnameEditInput");
        firstnameEditOutput.setId("firstnameEditOutput");
        firstnameEditOutput.setValue("Firstname:");
        htmlPanelGrid.getChildren().add(firstnameEditOutput);
        
        InputTextarea firstnameEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        firstnameEditInput.setId("firstnameEditInput");
        firstnameEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.firstname}", String.class));
        LengthValidator firstnameEditInputValidator = new LengthValidator();
        firstnameEditInputValidator.setMaximum(50);
        firstnameEditInput.addValidator(firstnameEditInputValidator);
        firstnameEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(firstnameEditInput);
        
        Message firstnameEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        firstnameEditInputMessage.setId("firstnameEditInputMessage");
        firstnameEditInputMessage.setFor("firstnameEditInput");
        firstnameEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(firstnameEditInputMessage);
        
        OutputLabel middlenameEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        middlenameEditOutput.setFor("middlenameEditInput");
        middlenameEditOutput.setId("middlenameEditOutput");
        middlenameEditOutput.setValue("Middlename:");
        htmlPanelGrid.getChildren().add(middlenameEditOutput);
        
        InputTextarea middlenameEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        middlenameEditInput.setId("middlenameEditInput");
        middlenameEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.middlename}", String.class));
        LengthValidator middlenameEditInputValidator = new LengthValidator();
        middlenameEditInputValidator.setMaximum(50);
        middlenameEditInput.addValidator(middlenameEditInputValidator);
        middlenameEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(middlenameEditInput);
        
        Message middlenameEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        middlenameEditInputMessage.setId("middlenameEditInputMessage");
        middlenameEditInputMessage.setFor("middlenameEditInput");
        middlenameEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(middlenameEditInputMessage);
        
        OutputLabel chinesenameEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        chinesenameEditOutput.setFor("chinesenameEditInput");
        chinesenameEditOutput.setId("chinesenameEditOutput");
        chinesenameEditOutput.setValue("Chinesename:");
        htmlPanelGrid.getChildren().add(chinesenameEditOutput);
        
        InputTextarea chinesenameEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        chinesenameEditInput.setId("chinesenameEditInput");
        chinesenameEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.chinesename}", String.class));
        LengthValidator chinesenameEditInputValidator = new LengthValidator();
        chinesenameEditInputValidator.setMaximum(50);
        chinesenameEditInput.addValidator(chinesenameEditInputValidator);
        chinesenameEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(chinesenameEditInput);
        
        Message chinesenameEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        chinesenameEditInputMessage.setId("chinesenameEditInputMessage");
        chinesenameEditInputMessage.setFor("chinesenameEditInput");
        chinesenameEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(chinesenameEditInputMessage);
        
        OutputLabel dobEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        dobEditOutput.setFor("dobEditInput");
        dobEditOutput.setId("dobEditOutput");
        dobEditOutput.setValue("Dob:");
        htmlPanelGrid.getChildren().add(dobEditOutput);
        
        Calendar dobEditInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        dobEditInput.setId("dobEditInput");
        dobEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.dob}", Date.class));
        dobEditInput.setNavigator(true);
        dobEditInput.setEffect("slideDown");
        dobEditInput.setPattern("dd/MM/yyyy");
        dobEditInput.setRequired(false);
        htmlPanelGrid.getChildren().add(dobEditInput);
        
        Message dobEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        dobEditInputMessage.setId("dobEditInputMessage");
        dobEditInputMessage.setFor("dobEditInput");
        dobEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(dobEditInputMessage);
        
        OutputLabel genderEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        genderEditOutput.setFor("genderEditInput");
        genderEditOutput.setId("genderEditOutput");
        genderEditOutput.setValue("Gender:");
        htmlPanelGrid.getChildren().add(genderEditOutput);
        
        InputText genderEditInput = (InputText) application.createComponent(InputText.COMPONENT_TYPE);
        genderEditInput.setId("genderEditInput");
        genderEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.gender}", Character.class));
        genderEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(genderEditInput);
        
        Message genderEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        genderEditInputMessage.setId("genderEditInputMessage");
        genderEditInputMessage.setFor("genderEditInput");
        genderEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(genderEditInputMessage);
        
        OutputLabel activeEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        activeEditOutput.setFor("activeEditInput");
        activeEditOutput.setId("activeEditOutput");
        activeEditOutput.setValue("Active:");
        htmlPanelGrid.getChildren().add(activeEditOutput);
        
        SelectBooleanCheckbox activeEditInput = (SelectBooleanCheckbox) application.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
        activeEditInput.setId("activeEditInput");
        activeEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.active}", Boolean.class));
        activeEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(activeEditInput);
        
        Message activeEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        activeEditInputMessage.setId("activeEditInputMessage");
        activeEditInputMessage.setFor("activeEditInput");
        activeEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(activeEditInputMessage);
        
        OutputLabel updatedtimeEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        updatedtimeEditOutput.setFor("updatedtimeEditInput");
        updatedtimeEditOutput.setId("updatedtimeEditOutput");
        updatedtimeEditOutput.setValue("Updatedtime:");
        htmlPanelGrid.getChildren().add(updatedtimeEditOutput);
        
        Calendar updatedtimeEditInput = (Calendar) application.createComponent(Calendar.COMPONENT_TYPE);
        updatedtimeEditInput.setId("updatedtimeEditInput");
        updatedtimeEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.updatedtime}", Date.class));
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
        
        OutputLabel updatedByEditOutput = (OutputLabel) application.createComponent(OutputLabel.COMPONENT_TYPE);
        updatedByEditOutput.setFor("updatedByEditInput");
        updatedByEditOutput.setId("updatedByEditOutput");
        updatedByEditOutput.setValue("Updated By:");
        htmlPanelGrid.getChildren().add(updatedByEditOutput);
        
        InputTextarea updatedByEditInput = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        updatedByEditInput.setId("updatedByEditInput");
        updatedByEditInput.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.updatedBy}", String.class));
        LengthValidator updatedByEditInputValidator = new LengthValidator();
        updatedByEditInputValidator.setMaximum(40);
        updatedByEditInput.addValidator(updatedByEditInputValidator);
        updatedByEditInput.setRequired(true);
        htmlPanelGrid.getChildren().add(updatedByEditInput);
        
        Message updatedByEditInputMessage = (Message) application.createComponent(Message.COMPONENT_TYPE);
        updatedByEditInputMessage.setId("updatedByEditInputMessage");
        updatedByEditInputMessage.setFor("updatedByEditInput");
        updatedByEditInputMessage.setDisplay("icon");
        htmlPanelGrid.getChildren().add(updatedByEditInputMessage);
        
        return htmlPanelGrid;
    }

	public HtmlPanelGrid populateViewPanel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        javax.faces.application.Application application = facesContext.getApplication();
        ExpressionFactory expressionFactory = application.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        
        HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) application.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
        
        HtmlOutputText registrationsLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        registrationsLabel.setId("registrationsLabel");
        registrationsLabel.setValue("Registrations:");
        htmlPanelGrid.getChildren().add(registrationsLabel);
        
        HtmlOutputText registrationsValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        registrationsValue.setId("registrationsValue");
        registrationsValue.setValue("This relationship is managed from the Registration side");
        htmlPanelGrid.getChildren().add(registrationsValue);
        
        HtmlOutputText familyidLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        familyidLabel.setId("familyidLabel");
        familyidLabel.setValue("Familyid:");
        htmlPanelGrid.getChildren().add(familyidLabel);
        
        HtmlOutputText familyidValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        familyidValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.familyid}", Family.class));
        familyidValue.setConverter(new FamilyConverter());
        htmlPanelGrid.getChildren().add(familyidValue);
        
        HtmlOutputText lastnameLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        lastnameLabel.setId("lastnameLabel");
        lastnameLabel.setValue("Lastname:");
        htmlPanelGrid.getChildren().add(lastnameLabel);
        
        InputTextarea lastnameValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        lastnameValue.setId("lastnameValue");
        lastnameValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.lastname}", String.class));
        lastnameValue.setReadonly(true);
        lastnameValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(lastnameValue);
        
        HtmlOutputText firstnameLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        firstnameLabel.setId("firstnameLabel");
        firstnameLabel.setValue("Firstname:");
        htmlPanelGrid.getChildren().add(firstnameLabel);
        
        InputTextarea firstnameValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        firstnameValue.setId("firstnameValue");
        firstnameValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.firstname}", String.class));
        firstnameValue.setReadonly(true);
        firstnameValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(firstnameValue);
        
        HtmlOutputText middlenameLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        middlenameLabel.setId("middlenameLabel");
        middlenameLabel.setValue("Middlename:");
        htmlPanelGrid.getChildren().add(middlenameLabel);
        
        InputTextarea middlenameValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        middlenameValue.setId("middlenameValue");
        middlenameValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.middlename}", String.class));
        middlenameValue.setReadonly(true);
        middlenameValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(middlenameValue);
        
        HtmlOutputText chinesenameLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        chinesenameLabel.setId("chinesenameLabel");
        chinesenameLabel.setValue("Chinesename:");
        htmlPanelGrid.getChildren().add(chinesenameLabel);
        
        InputTextarea chinesenameValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        chinesenameValue.setId("chinesenameValue");
        chinesenameValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.chinesename}", String.class));
        chinesenameValue.setReadonly(true);
        chinesenameValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(chinesenameValue);
        
        HtmlOutputText dobLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        dobLabel.setId("dobLabel");
        dobLabel.setValue("Dob:");
        htmlPanelGrid.getChildren().add(dobLabel);
        
        HtmlOutputText dobValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        dobValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.dob}", Calendar.class));
        DateTimeConverter dobValueConverter = (DateTimeConverter) application.createConverter(DateTimeConverter.CONVERTER_ID);
        dobValueConverter.setPattern("dd/MM/yyyy");
        dobValue.setConverter(dobValueConverter);
        htmlPanelGrid.getChildren().add(dobValue);
        
        HtmlOutputText genderLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        genderLabel.setId("genderLabel");
        genderLabel.setValue("Gender:");
        htmlPanelGrid.getChildren().add(genderLabel);
        
        HtmlOutputText genderValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        genderValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.gender}", String.class));
        htmlPanelGrid.getChildren().add(genderValue);
        
        HtmlOutputText activeLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        activeLabel.setId("activeLabel");
        activeLabel.setValue("Active:");
        htmlPanelGrid.getChildren().add(activeLabel);
        
        HtmlOutputText activeValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        activeValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.active}", String.class));
        htmlPanelGrid.getChildren().add(activeValue);
        
        HtmlOutputText updatedtimeLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        updatedtimeLabel.setId("updatedtimeLabel");
        updatedtimeLabel.setValue("Updatedtime:");
        htmlPanelGrid.getChildren().add(updatedtimeLabel);
        
        HtmlOutputText updatedtimeValue = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        updatedtimeValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.updatedtime}", Calendar.class));
        DateTimeConverter updatedtimeValueConverter = (DateTimeConverter) application.createConverter(DateTimeConverter.CONVERTER_ID);
        updatedtimeValueConverter.setPattern("dd/MM/yyyy");
        updatedtimeValue.setConverter(updatedtimeValueConverter);
        htmlPanelGrid.getChildren().add(updatedtimeValue);
        
        HtmlOutputText updatedByLabel = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
        updatedByLabel.setId("updatedByLabel");
        updatedByLabel.setValue("Updated By:");
        htmlPanelGrid.getChildren().add(updatedByLabel);
        
        InputTextarea updatedByValue = (InputTextarea) application.createComponent(InputTextarea.COMPONENT_TYPE);
        updatedByValue.setId("updatedByValue");
        updatedByValue.setValueExpression("value", expressionFactory.createValueExpression(elContext, "#{studentBean.student.updatedBy}", String.class));
        updatedByValue.setReadonly(true);
        updatedByValue.setDisabled(true);
        htmlPanelGrid.getChildren().add(updatedByValue);
        
        return htmlPanelGrid;
    }

	public Student getStudent() {
        if (student == null) {
            student = new Student();
        }
        return student;
    }

	public void setStudent(Student student) {
        this.student = student;
    }

	public List<Registration> getSelectedRegistrations() {
        return selectedRegistrations;
    }

	public void setSelectedRegistrations(List<Registration> selectedRegistrations) {
        if (selectedRegistrations != null) {
            student.setRegistrations(new HashSet<Registration>(selectedRegistrations));
        }
        this.selectedRegistrations = selectedRegistrations;
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

	public String onEdit() {
        if (student != null && student.getRegistrations() != null) {
            selectedRegistrations = new ArrayList<Registration>(student.getRegistrations());
        }
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
        findAllStudents();
        return "student";
    }

	public String displayCreateDialog() {
        student = new Student();
        createDialogVisible = true;
        return "student";
    }

	public String persist() {
        String message = "";
        if (student.getId() != null) {
            studentRepository.save(student);
            message = "message_successfully_updated";
        } else {
            studentRepository.save(student);
            message = "message_successfully_created";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("createDialogWidget.hide()");
        context.execute("editDialogWidget.hide()");
        
        FacesMessage facesMessage = MessageFactory.getMessage(message, "Student");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllStudents();
    }

	public String delete() {
        studentRepository.delete(student);
        FacesMessage facesMessage = MessageFactory.getMessage("message_successfully_deleted", "Student");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return findAllStudents();
    }

	public void reset() {
        student = null;
        selectedRegistrations = null;
        createDialogVisible = false;
    }

	public void handleDialogClose(CloseEvent event) {
        reset();
    }
}
