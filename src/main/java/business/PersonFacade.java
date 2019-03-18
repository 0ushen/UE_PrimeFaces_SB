package business;


import entity.Person;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class PersonFacade extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "com.github.adminfaces_admin-starter_war_0.1-SNAPSHOTPU")
    private EntityManager em;
    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonFacade() {
        super(Person.class);
    }
    
    public List<Person> findByEntity(Person p) {
        
        String sQuery = "SELECT p FROM Person p WHERE ";
        
        if(p.getFirstName() != null && !"".equals(p.getFirstName())) {
            sQuery += "p.firstName = :firstName AND ";
        }
        if(p.getLastName() != null && !"".equals(p.getLastName())) {
            sQuery += "p.lastName = :lastName AND ";
        }
        if(p.getCountry() != null && !"".equals(p.getCountry())) {
            sQuery += "p.country = :country AND ";
        }
        if(p.getCity() != null && !"".equals(p.getCity())) {
            sQuery += "p.city = :city AND ";
        }
        if(p.getPostalCode() != null && !"".equals(p.getPostalCode())) {
            sQuery += "p.postalCode = :postalCode AND ";
        }
        if(p.getAddress() != null && !"".equals(p.getAddress())) {
            sQuery += "p.address = :address AND ";
        }
        if(p.getDateOfBirth() != null) {
            sQuery += "p.dateOfBirth = CAST(:dateOfBirth AS DATE)) AND ";
        }
        if(p.getEmail() != null && !"".equals(p.getEmail())) {
            sQuery += "p.email = :email AND ";
        }
        if(p.getIsTeacher() != null) {
            sQuery += "p.isTeacher = :isTeacher AND ";
        }
        if(p.getMobile() != null && !"".equals(p.getMobile())) {
            sQuery += "p.mobile = :mobile AND ";
        }
        sQuery = sQuery.substring(0, sQuery.length() - 5);
        Query query = em.createQuery(sQuery);
        if(p.getFirstName() != null && !"".equals(p.getFirstName())) {
            query.setParameter("firstName", p.getFirstName());
        }
        if(p.getLastName() != null && !"".equals(p.getLastName())) {
            query.setParameter("lastName", p.getLastName());
        }
        if(p.getCountry() != null && !"".equals(p.getCountry())) {
            query.setParameter("country", p.getCountry());
        }
        if(p.getCity() != null && !"".equals(p.getCity())) {
            query.setParameter("city", p.getCity());
        }
        if(p.getPostalCode() != null && !"".equals(p.getPostalCode())) {
            query.setParameter("postalCode", p.getPostalCode());
        }
        if(p.getAddress() != null && !"".equals(p.getAddress())) {
            query.setParameter("address", "%" + p.getAddress() + "%");
        }
        if(p.getDateOfBirth() != null) {
            query.setParameter("dateOfBirth", p.getDateOfBirth());
        }
        if(p.getEmail() != null && !"".equals(p.getEmail())) {
            query.setParameter("email", "%" + p.getEmail() + "%");
        }
        if(p.getIsTeacher() != null) {
            query.setParameter("isTeacher", p.getIsTeacher());
        }
        if(p.getMobile() != null && !"".equals(p.getMobile())) {
            query.setParameter("mobile", p.getMobile());
        }
        
        return query.getResultList();
        
    }
    
    public List<Person> findByIsTeacher(boolean isTeacher) {
        
        Query query = em.createNamedQuery("Person.findByIsTeacher");
        query.setParameter("isTeacher", isTeacher);
        
        return query.getResultList();
    }
    
}
