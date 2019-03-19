package business;


import entity.OrganizedUe;
import entity.Section;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class OrganizedUeFacade extends AbstractFacade<OrganizedUe> {

    @PersistenceContext(unitName = "UE_PrimeFaces_SB_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrganizedUeFacade() {
        super(OrganizedUe.class);
    }
    
    public List<OrganizedUe> findByEntity(OrganizedUe o) {
        
        String sQuery = "SELECT o FROM OrganizedUe o WHERE ";
        
        if(o.getName() != null && !"".equals(o.getName())) {
            sQuery += "o.name = :name AND ";
        }
        if(o.getStartDate() != null) {
            sQuery += "o.startDate = :startDate AND ";
        }
        if(o.getEndDate() != null) {
            sQuery += "o.endDate = :endDate AND ";
        }
        if(o.getUe() != null) {
            sQuery += "o.ue.ueId = :ueId AND ";
        }
        if(o.getLevel() != null) {
            sQuery += "o.level.levelId = :levelId AND ";
        }
        sQuery = sQuery.substring(0, sQuery.length() - 5);
        Query query = em.createQuery(sQuery);
        if(o.getName() != null && !"".equals(o.getName())) {
            query.setParameter("name", o.getName());
        }
        if(o.getStartDate() != null) {
            query.setParameter("startDate", o.getStartDate());
        }
        if(o.getEndDate() != null) {
            query.setParameter("endDate", o.getEndDate());
        }
        if(o.getUe() != null) {
            query.setParameter("ueId", o.getUe().getUeId());
        }
        if(o.getUe() != null) {
            query.setParameter("levelId", o.getLevel().getLevelId());
        }
        
        return query.getResultList();
    
    }
    
    public List<OrganizedUe> findBySection(Section s) {
        Query query = em.createNamedQuery("OrganizedUe.findBySection");
        query.setParameter("sectionId", s.getSectionId());
        
        return query.getResultList();
        
    }
    
    public List<OrganizedUe> findByAcademicYear(Integer y) {
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, y);
        cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        cal.set(Calendar.YEAR, y + 1);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        Date secondDate = cal.getTime();
        Query query = em.createNamedQuery("OrganizedUe.findByAcademicYear");
        query.setParameter("firstDate", firstDate)
             .setParameter("secondDate", secondDate);
        
        return query.getResultList();
        
    }
    
    public List<OrganizedUe> findBySectionAndAcademicYear(Section s, Integer y) {
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, y);
        cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        cal.set(Calendar.YEAR, y + 1);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        Date secondDate = cal.getTime();
        Query query = em.createNamedQuery("OrganizedUe.findBySectionAndAcademicYear");
        query.setParameter("sectionId", s.getSectionId())
             .setParameter("firstDate", firstDate)
             .setParameter("secondDate", secondDate);
        
        return query.getResultList();
        
    }
    
}
