package business;

import entity.Ue;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class UeFacade extends AbstractFacade<Ue> {

    @PersistenceContext(unitName = "com.github.adminfaces_admin-starter_war_0.1-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UeFacade() {
        super(Ue.class);
    }
    
    public List<Ue> findByEntity(Ue ue) {
        
        String sQuery = "SELECT u FROM Ue u WHERE ";
        
        if(ue.getName() != null && !"".equals(ue.getName())) {
            sQuery += "u.name = :name AND ";
        }
        if(ue.getDescription() != null && !"".equals(ue.getDescription())) {
            sQuery += "u.description = :description AND ";
        }
        if(ue.getSection() != null) {
            sQuery += "u.section.sectionId = :sectionId AND ";
        }
        if(ue.getCode()!= null && !"".equals(ue.getCode())) {
            sQuery += "u.code = :code AND ";
        }
        if(ue.getNumOfPeriods() != null) {
            sQuery += "u.numOfPeriods = :numOfPeriods AND ";
        }
        if(ue.getIsDecisive() != null) {
            sQuery += "u.isDecisive = :isDecisive AND ";
        }
        sQuery = sQuery.substring(0, sQuery.length() - 5);
        Query query = em.createQuery(sQuery);
        if(ue.getName() != null && !"".equals(ue.getName())) {
            query.setParameter("name", "%" + ue.getName() + "%");
        }
        if(ue.getDescription() != null && !"".equals(ue.getDescription())) {
            query.setParameter("description", "%" + ue.getDescription() + "%");
        }
        if(ue.getSection() != null) {
            query.setParameter("sectionid", ue.getSection().getSectionId());
        }
        if(ue.getCode()!= null && !"".equals(ue.getCode())) {
            query.setParameter("code", ue.getCode());
        }
        if(ue.getNumOfPeriods() != null) {
            query.setParameter("numOfPeriods", ue.getNumOfPeriods());
        }
        if(ue.getIsDecisive() != null) {
            query.setParameter("isDecisive", ue.getIsDecisive());
        }
        
        return query.getResultList();    
    }
    
}
