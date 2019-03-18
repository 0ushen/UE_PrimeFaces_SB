package ui;

import business.LevelFacade;
import entity.OrganizedUe;
import business.OrganizedUeFacade;
import util.AcademicYear;
import util.Utils;
import entity.Section;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
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
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.Visibility;



@Named("organizedUeController")
@SessionScoped
public class OrganizedUeController implements Serializable {
    
    @Inject
    private OrganizedUeFacade ejbFacade;
    @Inject
    private LevelFacade ejbFacadeLevel;
    
    private OrganizedUe organizedUe;
    private OrganizedUe selectedOrganizedUe; //If only 1 row is selected it will have the data of that row
    private List<OrganizedUe> organizedUeList;
    private List<OrganizedUe> selectedOrganizedUes;
    private List<OrganizedUe> filteredOrganizedUes;
    private List<Boolean> visibleColumnsList;
    private Section selectedSection;
    private Integer selectedYear;
    private Set<AcademicYear> existingAcademicYearsSet;
    
    
    @PostConstruct
    public void init() {
        
        organizedUe = new OrganizedUe();
        selectedOrganizedUe = new OrganizedUe();
        selectedSection = new Section();
        organizedUeList = ejbFacade.findAll();
        visibleColumnsList = Arrays.asList(true, true, true, true, true, true);
        existingAcademicYearsSet = Utils.findExistingAcademicYears(organizedUeList);
        
    }
    
     public void listAll() {
        
        organizedUeList = ejbFacade.findAll();
        
    }
    
    public void search() {
        
        organizedUeList = ejbFacade.findByEntity(organizedUe);
        
    }
    
    public void create() {
        
        ejbFacade.create(organizedUe);
        refresh();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "New organizedUe added to the database : ", organizedUe.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        organizedUe = new OrganizedUe();
        
    }
    
    public void update() {
        
        ejbFacade.edit(selectedOrganizedUe);
        refresh();
        
    }
    
    public void delete() {
        
        for(OrganizedUe o : selectedOrganizedUes) {
            ejbFacade.remove(o);
            try{
                filteredOrganizedUes.remove(o);
            } catch(NullPointerException e) {
                // Means fileteredOrganizedUes has not been initialized. Do nothing.
            }
        }
        selectedOrganizedUes.clear();
        refresh();
        
    }
    
    private void refresh() {
        
        customFilter();
        
    }

    public List<OrganizedUe> getItemsAvailableSelectOne() {
        return ejbFacade.findAll();
    }
    
    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Organized UE Edited", ((OrganizedUe) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((OrganizedUe) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onCellEdit(CellEditEvent event) {
        
	DataTable d =(DataTable) event.getSource();
        OrganizedUe organizedUeToEdit = (OrganizedUe) d.getRowData();
        ejbFacade.edit(organizedUeToEdit);
        
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
    
    public void onRowSelectCheckbox(SelectEvent event){
        if(selectedOrganizedUes.size() == 1){
            selectedOrganizedUe = selectedOrganizedUes.get(0);
        }
    }
    
    public void onRowUnselectCheckbox(UnselectEvent event) {
        if(selectedOrganizedUes.size() == 1){
            selectedOrganizedUe = selectedOrganizedUes.get(0);
        }
    }
    
    public String customFormatDate(Date date) {
        return Utils.customFormatDate(date);
    }
    
    @FacesConverter(forClass = OrganizedUe.class)
    public static class OrganizedUeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext,
                UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OrganizedUeController controller = 
                    (OrganizedUeController) facesContext.getApplication().
                    getELResolver().getValue(facesContext.getELContext(),
                    null, "organizedUeController");
            
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
        public String getAsString(FacesContext facesContext,
                UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof OrganizedUe) {
                OrganizedUe o = (OrganizedUe) object;
                return getStringKey(o.getOrganizedUeId());
            } else {
                throw new IllegalArgumentException("object " 
                        + object + " is of type " + object.getClass().getName() 
                        + "; expected type: " + OrganizedUe.class.getName());
            }
        }

    }
    
    public void customFilter(){
        
        if(selectedSection == null && selectedYear == null){
            organizedUeList = ejbFacade.findAll();
            existingAcademicYearsSet = Utils.findExistingAcademicYears(organizedUeList);
        }
        else if(selectedSection != null && selectedYear == null){
            organizedUeList = ejbFacade.findBySection(selectedSection);
            existingAcademicYearsSet = Utils.findExistingAcademicYears(organizedUeList);
        }
        else if(selectedSection ==  null && selectedYear != null){
            
            /* If i select a section then i select a year and
               then i unselect a section, my year combo box will not have all
               the existing academic years in the database. These 2 lines of
               code are a quick fix in order to have a correct year combo box. 
               Not good for performance. */
            organizedUeList = ejbFacade.findAll();
            existingAcademicYearsSet = Utils.findExistingAcademicYears(organizedUeList);
            
            organizedUeList = ejbFacade.findByAcademicYear(selectedYear);
        }
        else{
            
            /* If i add an organizedUe to an empty academic year while a section
               is selected and a year is selected, the (now not empty) academic
               year doesn't show up in the year combo box. These 2 lines of
               code are a quick fix in order to have a correct year combo box. 
               Not good for performance. */
            organizedUeList = ejbFacade.findBySection(selectedSection);
            existingAcademicYearsSet = Utils.findExistingAcademicYears(organizedUeList);
            
            organizedUeList = ejbFacade.findBySectionAndAcademicYear(selectedSection, selectedYear);
        }
        
    }
    
    
    public void copyToNextYear() {
        
        Calendar c = Calendar.getInstance();
        OrganizedUe copy;
        for(OrganizedUe o : organizedUeList) {
            copy = new OrganizedUe(o);
            c.setTime(copy.getStartDate());
            c.add(Calendar.YEAR, 1);
            copy.setStartDate(c.getTime());
            c.setTime(copy.getEndDate());
            c.add(Calendar.YEAR, 1);
            copy.setEndDate(c.getTime());
            ejbFacade.create(copy);
        }
        customFilter();
        
    }

    
    
    // ======================================
    // =          Getters & Setters         =
    // ======================================
    

    public OrganizedUeFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(OrganizedUeFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public OrganizedUe getOrganizedUe() {
        return organizedUe;
    }

    public void setOrganizedUe(OrganizedUe organizedUe) {
        this.organizedUe = organizedUe;
    }

    public List<OrganizedUe> getOrganizedUeList() {
        return organizedUeList;
    }

    public void setOrganizedUeList(List<OrganizedUe> organizedUeList) {
        this.organizedUeList = organizedUeList;
    }

    public List<OrganizedUe> getSelectedOrganizedUes() {
        return selectedOrganizedUes;
    }

    public void setSelectedOrganizedUes(List<OrganizedUe> selectedOrganizedUes) {
        this.selectedOrganizedUes = selectedOrganizedUes;
    }

    public List<OrganizedUe> getFilteredOrganizedUes() {
        return filteredOrganizedUes;
    }

    public void setFilteredOrganizedUes(List<OrganizedUe> filteredOrganizedUes) {
        this.filteredOrganizedUes = filteredOrganizedUes;
    }

    public List<Boolean> getVisibleColumnsList() {
        return visibleColumnsList;
    }

    public void setVisibleColumnsList(List<Boolean> visibleColumnsList) {
        this.visibleColumnsList = visibleColumnsList;
    }

    public OrganizedUe getSelectedOrganizedUe() {
        return selectedOrganizedUe;
    }

    public void setSelectedOrganizedUe(OrganizedUe selectedOrganizedUe) {
        this.selectedOrganizedUe = selectedOrganizedUe;
    }

    public LevelFacade getEjbFacadeLevel() {
        return ejbFacadeLevel;
    }

    public void setEjbFacadeLevel(LevelFacade ejbFacadeLevel) {
        this.ejbFacadeLevel = ejbFacadeLevel;
    }

    public Section getSelectedSection() {
        return selectedSection;
    }

    public void setSelectedSection(Section selectedSection) {
        this.selectedSection = selectedSection;
    }
    
    public Integer getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(Integer selectedYear) {
        this.selectedYear = selectedYear;
    }

    public Set<AcademicYear> getExistingAcademicYearsSet() {
        return existingAcademicYearsSet;
    }

    public void setExistingAcademicYearsSet(Set<AcademicYear> existingAcademicYearsSet) {
        this.existingAcademicYearsSet = existingAcademicYearsSet;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
