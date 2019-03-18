package business;

import entity.Section;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SectionFacade extends AbstractFacade<Section> {

    @PersistenceContext(unitName = "com.github.adminfaces_admin-starter_war_0.1-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SectionFacade() {
        super(Section.class);
    }
    
    public List<Section> findByEntity(Section s) {
        
        String sQuery = "SELECT s FROM Section s WHERE ";
        
        if(s.getName() != null && !"".equals(s.getName())) {
            sQuery += "s.name = :name AND ";
        }
        if(s.getDescription() != null && !"".equals(s.getDescription())) {
            sQuery += "s.description = :description AND ";
        }
        if(s.getTeacher() != null) {
            sQuery += "s.teacher.teacherId = :teacherId AND ";
        }
        sQuery = sQuery.substring(0, sQuery.length() - 5);
        Query query = em.createQuery(sQuery);
        if(s.getName() != null && !"".equals(s.getName())) {
            query.setParameter("name", "%" + s.getName() + "%");
        }
        if(s.getDescription() != null && !"".equals(s.getDescription())) {
            query.setParameter("description", "%" + s.getDescription() + "%");
        }
        if(s.getTeacher() != null) {
            query.setParameter("teacherId", s.getTeacher().getPersonId());
        }
        
        return query.getResultList();
        
    }
    
}
