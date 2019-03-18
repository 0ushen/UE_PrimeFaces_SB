package business;

import entity.Capacity;
import entity.Ue;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class CapacityFacade extends AbstractFacade<Capacity> {

    @PersistenceContext(unitName = "com.github.adminfaces_admin-starter_war_0.1-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CapacityFacade() {
        super(Capacity.class);
    }
    
    public List<Capacity> findByEntity(Capacity capacity) {
        
        // Quick fix
        java.lang.Integer ueId = null;
        if(capacity.getUe() != null)
            ueId = capacity.getUe().getUeId();
        
        Query query = em.createNamedQuery("Capacity.findByEntity");
        query.setParameter("name", capacity.getName())
             .setParameter("description", capacity.getDescription())
             .setParameter("ueId", ueId)
             .setParameter("isThresholdOfSuccess", capacity.getIsThresholdOfSuccess());
        
        return query.getResultList();
    }
    
    public List<Capacity> findByUe(Ue ue)
    {
        Query query = em.createNamedQuery("Capacity.findByUe");
        query.setParameter("ueId", ue.getUeId());
        
        return query.getResultList();
    }
    
}
