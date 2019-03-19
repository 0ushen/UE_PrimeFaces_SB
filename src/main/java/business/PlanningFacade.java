package business;

import entity.Level;
import entity.Planning;
import entity.Section;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class PlanningFacade extends AbstractFacade<Planning> {

    @PersistenceContext(unitName = "UE_PrimeFaces_SB_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlanningFacade() {
        super(Planning.class);
    }
    
    public List<Planning> findByEntity(Planning p) {
        
        String sQuery = "SELECT p FROM Planning p WHERE ";
        
        if(p.getRoom() != null && !"".equals(p.getRoom())) {
            sQuery += "p.room = :room AND ";
        }
        if(p.getSeanceDate() != null) {
            sQuery += "o.seanceDate = :seanceDate AND ";
        }
        if(p.getStartHour() != null) {
            sQuery += "p.startHour = :startHour AND ";
        }
        if(p.getOrganizedUe() != null) {
            sQuery += "p.organizedUe.organizedUeId = :organizedUeId AND ";
        }
        if(p.getTeacher() != null) {
            sQuery += "p.teacher.teacherId = :teacherId AND ";
        }
        sQuery = sQuery.substring(0, sQuery.length() - 5);
        Query query = em.createQuery(sQuery);
        if(p.getRoom() != null && !"".equals(p.getRoom())) {
            query.setParameter("room", p.getRoom());
        }
        if(p.getSeanceDate() != null) {
            query.setParameter("seanceDate", p.getSeanceDate());
        }
        if(p.getStartHour() != null) {
            query.setParameter("startHour", p.getStartHour());
        }
        if(p.getOrganizedUe() != null) {
            query.setParameter("organizedUeId", p.getOrganizedUe().getOrganizedUeId());
        }
        if(p.getTeacher() != null) {
            query.setParameter("teacherId", p.getTeacher().getPersonId());
        }
        
        return query.getResultList();
    
    }
    
    public List<Planning> findWithFilter(Section section, Integer year, Level level) {
        
        Date firstDate = new Date(); 
        Date secondDate = new Date();
        
        String sQuery = "SELECT p FROM Planning p WHERE ";
        
        if(section != null) {
            sQuery += "p.organizedUe.ue.section.sectionId = :sectionId AND ";
        }
        if(level != null) {
            sQuery += "p.organizedUe.level.levelId = :levelId AND ";
        }
        if(year != null) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            firstDate = cal.getTime();
            cal.set(Calendar.YEAR, year + 1);
            cal.set(Calendar.MONTH, Calendar.AUGUST);
            cal.set(Calendar.DAY_OF_MONTH, 31);
            secondDate = cal.getTime();
            sQuery += "p.organizedUe.startDate BETWEEN :firstDate AND :secondDate AND ";
        }
        sQuery = sQuery.substring(0, sQuery.length() - 5);
        Query query = em.createQuery(sQuery);
        if(section != null) {
            query.setParameter("sectionId", section.getSectionId());
        }
        if(level != null) {
            query.setParameter("levelId", level.getLevelId());
        }
        if(year != null) {
            query.setParameter("firstDate", firstDate)
                 .setParameter("secondDate", secondDate);
        }
        
        return query.getResultList();
    }
    
}
