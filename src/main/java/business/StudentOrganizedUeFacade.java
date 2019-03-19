package business;

import entity.StudentOrganizedUe;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class StudentOrganizedUeFacade extends AbstractFacade<StudentOrganizedUe> {

    @PersistenceContext(unitName = "UE_PrimeFaces_SB_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentOrganizedUeFacade() {
        super(StudentOrganizedUe.class);
    }
    
}
