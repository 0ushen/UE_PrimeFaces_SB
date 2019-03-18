package ui;

import business.CapacityFacade;
import business.UeFacade;
import entity.Capacity;
import entity.Ue;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.Visibility;

@Named("ueController")
@SessionScoped
public class UeController implements Serializable {

    @EJB
    private business.UeFacade ejbFacade;
    
    private Ue ue;
    private Ue selectedUe; //If only 1 row is selected it will have the data of that row
    private List<Ue> ueList;
    private List<Ue> selectedUes;
    private List<Ue> filteredUes;
    private List<Boolean> visibleColumnsList;
    private boolean isHidden; //Render an inputDiv below the capacityTable if sets to false.
    
    //Properties for capacityTable
    @Inject
    private CapacityFacade ejbFacadeCapacity;
    
    private Capacity capacity;
    private List<Capacity> selectedCapacities;
    private List<Capacity> filteredCapacities;
    
    
    @PostConstruct
    public void init() {
        
        isHidden = true; // inputDiv for the capacity table hidden at first
        ue = new Ue();
        selectedUe = new Ue();
        ueList = ejbFacade.findAll();
        visibleColumnsList = Arrays.asList(true, true, true, true, true, true, true);
        capacity = new Capacity();
        
    }
    
    public void listAll() {
        
        ueList = ejbFacade.findAll();
        
    }
    
    public void search() {
        
        ueList = ejbFacade.findByEntity(ue);
        
    }
    
    public void create() {
        
        ejbFacade.create(ue);
        refresh();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "New ue added to the database : ", ue.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        ue = new Ue();
        
    }
    
    public void update() {
        
        selectedUes.forEach((u) -> {
            ejbFacade.edit(u);
        });
        refresh();
        
    }
    
    public void delete() {
        
        for(Ue u : selectedUes) {
            ejbFacade.remove(u);
            try{
                filteredUes.remove(u);
            } catch(NullPointerException e) {
                // Means fileteredUes has not been initialized. Do nothing.
            }
        }
        selectedUes.clear();
        refresh();
        
    }
    
    private void refresh() {
        
        ueList = ejbFacade.findAll();
        
    }

    public List<Ue> getItemsAvailableSelectOne() {
        return ejbFacade.findAll();
    }
    
    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("UE Edited", ((Ue) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Ue) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onCellEdit(CellEditEvent event) {
        
	DataTable d =(DataTable) event.getSource();
        Ue ueToEdit = (Ue) d.getRowData();
        ejbFacade.edit(ueToEdit);
        
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
        if(selectedUes.size() == 1){
            selectedUe = selectedUes.get(0);
        }
    }
    
    public void onRowUnselectCheckbox(UnselectEvent event) {
        if(selectedUes.size() == 1){
            selectedUe = selectedUes.get(0);
        }
    }
    
    public void hideOrShow() {
        isHidden = !isHidden;
    }

    @FacesConverter(forClass = Ue.class)
    public static class UeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UeController controller = (UeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ueController");
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
            if (object instanceof Ue) {
                Ue o = (Ue) object;
                return getStringKey(o.getUeId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Ue.class.getName());
            }
        }

    }
    
    
    // ======================================
    // =  Methods for the capacity table    =
    // ======================================
    
    
    public List<Capacity> getCapacityList() {
        return ejbFacadeCapacity.findByUe(selectedUe);
    }
    
    //Bug with the cell editor , edited value doesn't get registered
    public void onCellEditCapacity(CellEditEvent event) {
        
        System.out.println("In onCellEditCapacity()\n");
	DataTable d =(DataTable) event.getSource();
        Capacity capacityToEdit = (Capacity) d.getRowData();
        System.out.println(capacityToEdit);
        ejbFacadeCapacity.edit(capacityToEdit);
        
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    
    public void createCapacity() {
        
        capacity.setUe(selectedUe);
        ejbFacadeCapacity.create(capacity);
        refresh();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "New capacity added to the database : ", capacity.getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        capacity = new Capacity();
        
    }
    
    public void deleteCapacity() {
        
        for(Capacity c : selectedCapacities) {
            ejbFacadeCapacity.remove(c);
        }
        selectedCapacities.clear();
        refresh();
        
    }
    
    // ======================================
    // =          Getters & Setters         =
    // ======================================

    public UeFacade getUeFacade() {
        return ejbFacade;
    }

    public void setUeFacade(UeFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }
    
    public Ue getUe() {
        return ue;
    }

    public void setUe(Ue ue) {
        this.ue = ue;
    }

    public List<Ue> getUeList() {
        return ueList;
    }

    public void setUeList(List<Ue> ueList) {
        this.ueList = ueList;
    }
    public UeFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(UeFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<Ue> getSelectedUes() {
        return selectedUes;
    }

    public void setSelectedUes(List<Ue> selectedUes) {
        this.selectedUes = selectedUes;
    }

    public List<Ue> getFilteredUes() {
        return filteredUes;
    }

    public void setFilteredUes(List<Ue> filteredUes) {
        this.filteredUes = filteredUes;
    }

    public List<Boolean> getVisibleColumnsList() {
        return visibleColumnsList;
    }

    public void setVisibleColumnsList(List<Boolean> visibleColumnsList) {
        this.visibleColumnsList = visibleColumnsList;
    }

    public Ue getSelectedUe() {
        return selectedUe;
    }

    public void setSelectedUe(Ue selectedUe) {
        this.selectedUe = selectedUe;
    }

    public boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public CapacityFacade getEjbFacadeCapacity() {
        return ejbFacadeCapacity;
    }

    public void setEjbFacadeCapacity(CapacityFacade ejbFacadeCapacity) {
        this.ejbFacadeCapacity = ejbFacadeCapacity;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
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
    
    
   
    
    
}
