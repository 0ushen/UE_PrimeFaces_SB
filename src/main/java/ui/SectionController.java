package ui;

import entity.Section;
import business.SectionFacade;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

@Named("sectionController")
@SessionScoped
public class SectionController implements Serializable {
    
    @Inject
    private SectionFacade ejbFacade;
    
    private Section section;
    private List<Section> sectionList;
    private List<Section> selectedSections;
    private List<Section> filteredSections;
    private List<Boolean> visibleColumnsList;
    
    
    
    @PostConstruct
    public void init() {
        
        section= new Section();
        sectionList = ejbFacade.findAll();
        visibleColumnsList = Arrays.asList(true, true, true, true);
        
    }
    
    public void listAll() {
        
        sectionList = ejbFacade.findAll();
        
    }
    
    public void search() {
        
        sectionList = ejbFacade.findByEntity(section);
        
    }
    
    public void create() {
        
        ejbFacade.create(section);
        refresh();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "New section added to the database : ", section.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        section = new Section();
        
    }
    
    public void update() {
        
        ejbFacade.edit(selectedSections.get(0));
        refresh();
        
    }
    
    public void delete() {
        
        for(Section s : selectedSections) {
            ejbFacade.remove(s);
            try{
                filteredSections.remove(s);
            } catch(NullPointerException e) {
                // Means fileteredSections has not been initialized. Do nothing.
            }
        }
        selectedSections.clear();
        refresh();
        
    }
    
    private void refresh() {
        
        sectionList = ejbFacade.findAll();
        
    }

    public List<Section> getItemsAvailableSelectOne() {
        return ejbFacade.findAll();
    }
    
    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Section Edited", ((Section) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Section) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onCellEdit(CellEditEvent event) {
        
	DataTable d =(DataTable) event.getSource();
        Section sectionToEdit = (Section) d.getRowData();
        ejbFacade.edit(sectionToEdit);
        
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


    @FacesConverter(forClass = Section.class)
    public static class SectionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext,
                UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SectionController controller = 
                    (SectionController) facesContext.getApplication().
                    getELResolver().getValue(facesContext.getELContext(),
                    null, "sectionController");
            
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
            if (object instanceof Section) {
                Section o = (Section) object;
                return getStringKey(o.getSectionId());
            } else {
                throw new IllegalArgumentException("object " 
                        + object + " is of type " + object.getClass().getName() 
                        + "; expected type: " + Section.class.getName());
            }
        }

    }
    
    
       
    // ======================================
    // =          Getters & Setters         =
    // ======================================
    

    public SectionFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(SectionFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public List<Section> getSelectedSections() {
        return selectedSections;
    }

    public void setSelectedSections(List<Section> selectedSections) {
        this.selectedSections = selectedSections;
    }

    public List<Section> getFilteredSections() {
        return filteredSections;
    }

    public void setFilteredSections(List<Section> filteredSections) {
        this.filteredSections = filteredSections;
    }

    public List<Boolean> getVisibleColumnsList() {
        return visibleColumnsList;
    }

    public void setVisibleColumnsList(List<Boolean> visibleColumnsList) {
        this.visibleColumnsList = visibleColumnsList;
    }
    
    
    

}
