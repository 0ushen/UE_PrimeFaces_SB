package ui;

import business.PersonFacade;
import util.Utils;
import entity.Person;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;


@Named("personController")
@SessionScoped
public class PersonController implements Serializable {
    
    @Inject
    private PersonFacade ejbFacade;
    
    private Person person;
    private List<Person> selectedPersons;
    private List<Person> personList;
    private List<Person> filteredPersons;
    private List<Boolean> visibleColumnsList;
    
    @PostConstruct
    public void init() {
        
        person = new Person();
        personList = ejbFacade.findAll();
        visibleColumnsList = Arrays.asList(true, true, true, true, true, true, false, false, false, false, true);
        
    }
    
    public void listAll() {
        
        personList = ejbFacade.findAll();
        
    }
    
    public void search() {
        
        personList = ejbFacade.findByEntity(person);
        
    }
    
    public void create() {
        
        ejbFacade.create(person);
        refresh();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "New person added to the database", 
                "Lastname: " + person.getLastName() + 
                ", Firstname: " + person.getFirstName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        person = new Person();
        
    }
    
    public void update() {
        
        ejbFacade.edit(selectedPersons.get(0));
        refresh();
        
    }
    
    public void delete() {

        for(Person p : selectedPersons) {
            ejbFacade.remove(p);
            try{
                filteredPersons.remove(p);
            } catch(NullPointerException e) {
                // Means fileteredPersons has not been initialized. Do nothing.
            }
        }
        selectedPersons.clear();
        refresh();
        
    }
    
    private void refresh() {
        
        personList = ejbFacade.findAll();
        
    }

    public List<Person> getItemsAvailableSelectOne() {
        return ejbFacade.findAll();
    }
    
    public List<Person> getItemsAvailableSelectOneTeacher() {
        return ejbFacade.findByIsTeacher(true);
    }

    
    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Person Edited", ((Person) event.getObject()).getLastName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Person) event.getObject()).getLastName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onCellEdit(CellEditEvent event) {
        
	DataTable d =(DataTable) event.getSource();
        Person personToEdit = (Person) d.getRowData();
        ejbFacade.edit(personToEdit);
        
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void onToggle(ToggleEvent e) {
        visibleColumnsList.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }

    public String customFormatDate(Date date) {
        return Utils.customFormatDate(date);
    }
    @FacesConverter(forClass = Person.class)
    public static class PersonControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PersonController controller = (PersonController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "personController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Person) {
                Person o = (Person) object;
                return getStringKey(o.getPersonId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Person.class.getName());
            }
        }

    }
    
    // ======================================
    // =          Getters & Setters         =
    // ======================================
    
    public PersonFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(PersonFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    
    public List<Person> getPersonList() {
        return personList;
    }
    
    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public void setPersonList(ArrayList<Person> personList) {
        this.personList = personList;
    }
    

    public List<Person> getFilteredPersons() {
        return filteredPersons;
    }

    public void setFilteredPersons(List<Person> filteredPersons) {
        this.filteredPersons = filteredPersons;
    }

    public List<Person> getSelectedPersons() {
        return selectedPersons;
    }

    public void setSelectedPersons(List<Person> selectedPersons) {
        this.selectedPersons = selectedPersons;
    }

    public List<Boolean> getVisibleColumnsList() {
        return visibleColumnsList;
    }

    public void setVisibleColumnsList(List<Boolean> visibleColumnsList) {
        this.visibleColumnsList = visibleColumnsList;
    }
    
    
    
    

    
    
    
    
    
    

}