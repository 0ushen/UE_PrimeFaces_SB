package ui;


import business.PlanningFacade;
import util.AcademicYear;
import util.Utils;
import entity.Level;
import entity.Planning;
import entity.Section;
import java.io.Serializable;
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
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;


@Named("planningController")
@SessionScoped
public class PlanningController implements Serializable {
    
    @Inject
    private PlanningFacade ejbFacade;
    
    private Planning planning;
    private Planning selectedPlanning;
    private List<Planning> planningList;
    private Section selectedSection;
    private Level selectedLevel;
    private Integer selectedYear;
    private Set<AcademicYear> existingAcademicYearsSet;
    private ScheduleModel eventModel;
    
    @PostConstruct
    public void init() {
        
        planning = new Planning();
        selectedPlanning = new Planning();
        planningList = ejbFacade.findAll();
        eventModel = new DefaultScheduleModel();
        
    }
    
    public void listAll() {
        
        planningList = ejbFacade.findAll();
        
    }
    
    public void search() {
        
        planningList = ejbFacade.findByEntity(planning);
        
    }
    
    public void create() {
        
        ejbFacade.create(planning);
        refresh();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "New event added to the database : ", planning.getOrganizedUe().getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        planning = new Planning();
        
    }
    
    public void update() {
        
        ejbFacade.edit(selectedPlanning);
        refresh();
        
    }
    
    public void delete() {
        
        ejbFacade.remove(selectedPlanning);
        refresh();
    
    }
    
    private void refresh() {
        
        customFilter();
        
    }
    
    public void customFilter(){
        
        if(selectedSection == null && selectedYear == null && selectedLevel == null) {
            planningList = ejbFacade.findAll();
        }
        else {
            planningList = ejbFacade.findWithFilter(selectedSection, selectedYear, selectedLevel);
        }
        buildPlanning();
        
    }
    
    public void buildPlanning() {
        
        eventModel = new DefaultScheduleModel();
        
        for(Planning p : planningList) {
            if(p.getSeanceDate() != null && p.getStartHour()!= null) {
                Date seanceDateTime = Utils.combineDateAndTime(p.getSeanceDate(), p.getStartHour());
                DefaultScheduleEvent e = new DefaultScheduleEvent(
                        p.getOrganizedUe().getName(), 
                        seanceDateTime, 
                        dateFourHoursAfter(seanceDateTime), 
                        p);
                eventModel.addEvent(e);
            }
        }
        
    }
    
    public Date dateFourHoursAfter(Date date) {
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 4);
        
        return cal.getTime();
    }
    
    public void onDateSelect(SelectEvent selectEvent) {
        Date date = (Date) selectEvent.getObject();
        planning.setSeanceDate(date);
    }
    
    public void onEventSelect(SelectEvent selectEvent) {
        ScheduleEvent event = (ScheduleEvent) selectEvent.getObject();
        selectedPlanning = (Planning) event.getData();
    }
    
    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
     
    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    
    @FacesConverter(forClass = Planning.class)
    public static class PlanningControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext,
                UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PlanningController controller = 
                    (PlanningController) facesContext.getApplication().
                    getELResolver().getValue(facesContext.getELContext(),
                    null, "planningController");
            
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
            if (object instanceof Planning) {
                Planning o = (Planning) object;
                return getStringKey(o.getPlanningId());
            } else {
                throw new IllegalArgumentException("object " 
                        + object + " is of type " + object.getClass().getName() 
                        + "; expected type: " + Planning.class.getName());
            }
        }

    }
    
    
    
    // ======================================
    // =          Getters & Setters         =
    // ======================================
    
    

    public PlanningFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(PlanningFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public Planning getPlanning() {
        return planning;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

    public Planning getSelectedPlanning() {
        return selectedPlanning;
    }

    public void setSelectedPlanning(Planning selectedPlanning) {
        this.selectedPlanning = selectedPlanning;
    }

    public List<Planning> getPlanningList() {
        return planningList;
    }

    public void setPlanningList(List<Planning> planningList) {
        this.planningList = planningList;
    }

    public Section getSelectedSection() {
        return selectedSection;
    }

    public void setSelectedSection(Section selectedSection) {
        this.selectedSection = selectedSection;
    }

    public Level getSelectedLevel() {
        return selectedLevel;
    }

    public void setSelectedLevel(Level selectedLevel) {
        this.selectedLevel = selectedLevel;
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

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }
    
    
    
    
    

    
    
    
}
