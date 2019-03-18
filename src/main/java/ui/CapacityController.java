package ui;

import entity.Capacity;
import business.CapacityFacade;
import entity.Ue;
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

@Named("capacityController")
@SessionScoped
public class CapacityController implements Serializable {

    @Inject
    private CapacityFacade ejbFacade;
    
    private Capacity capacity;
    private List<Capacity> capacityList;
    private List<Capacity> selectedCapacities;
    private List<Capacity> filteredCapacities;
    private List<Boolean> visibleColumnsList;

    @PostConstruct
    public void init() {
        
        capacity = new Capacity();
        capacityList = ejbFacade.findAll();
        visibleColumnsList = Arrays.asList(true, true, true);
        
    }
    
    public void listAll() {
        
        capacityList = ejbFacade.findAll();
        
    }
    
    public void search() {
        
        capacityList = ejbFacade.findByEntity(capacity);
        
    }
    
    public void create() {
        
        ejbFacade.create(capacity);
        refresh();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "New capacity added to the database : ", capacity.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        capacity = new Capacity();
        
    }
    
    public void createFromUeView(Ue ue) {
        
        capacity.setUe(ue);
        create();
        
    }
    
    public List<Capacity> getCapacityListForUeView(Ue ue) {
        
        return ejbFacade.findByUe(ue);
    }
    
    public void update() {
        
        ejbFacade.edit(selectedCapacities.get(0));
        refresh();
        
    }
    
    public void delete() {
        
        for(Capacity c : selectedCapacities) {
            ejbFacade.remove(c);
            try{
                filteredCapacities.remove(c);
            } catch(NullPointerException e) {
                // Means fileteredSections has not been initialized. Do nothing.
            }
        }
        selectedCapacities.clear();
        refresh();
        
    }
    
    private void refresh() {
        
        capacityList = ejbFacade.findAll();
        
    }

    public List<Capacity> getItemsAvailableSelectOne() {
        return ejbFacade.findAll();
    }
    
    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Capacity Edited", ((Capacity) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Capacity) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onCellEdit(CellEditEvent event) {
        
	DataTable d =(DataTable) event.getSource();
        Capacity capacityToEdit = (Capacity) d.getRowData();
        ejbFacade.edit(capacityToEdit);
        
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

    @FacesConverter(forClass = Capacity.class)
    public static class CapacityControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CapacityController controller = (CapacityController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "capacityController");
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
            if (object instanceof Capacity) {
                Capacity o = (Capacity) object;
                return getStringKey(o.getCapacityId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Capacity.class.getName());
            }
        }

    }
    
    
    // ======================================
    // =          Getters & Setters         =
    // ======================================

    public CapacityFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(CapacityFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }

    public List<Capacity> getCapacityList() {
        return capacityList;
    }

    public void setCapacityList(List<Capacity> capacityList) {
        this.capacityList = capacityList;
    }

    public List<Capacity> getSelectedCapacities() {
        return selectedCapacities;
    }

    public void setSelectedCapacities(List<Capacity> selectedCapacities) {
        this.selectedCapacities = selectedCapacities;
    }

    public List<Capacity> getFilteredCapacities() {
        return filteredCapacities;
    }

    public void setFilteredCapacities(List<Capacity> filteredCapacities) {
        this.filteredCapacities = filteredCapacities;
    }

    public List<Boolean> getVisibleColumnsList() {
        return visibleColumnsList;
    }

    public void setVisibleColumnsList(List<Boolean> visibleColumnsList) {
        this.visibleColumnsList = visibleColumnsList;
    }
    
    
    
    
    
    

}
