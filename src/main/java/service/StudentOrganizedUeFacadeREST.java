package service;

import entity.StudentOrganizedUe;
import entity.StudentOrganizedUePK;
import io.swagger.annotations.Api;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;


@Stateless
//@Api(value = "studentOrganizedUe")
@Path("studentorganizedue")
public class StudentOrganizedUeFacadeREST extends AbstractFacade<StudentOrganizedUe> {

    @PersistenceContext(unitName = "UE_PrimeFaces_SB_PU")
    private EntityManager em;

    private StudentOrganizedUePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;personId=personIdValue;organizedUeId=organizedUeIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entity.StudentOrganizedUePK key = new entity.StudentOrganizedUePK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> personId = map.get("personId");
        if (personId != null && !personId.isEmpty()) {
            key.setPersonId(new java.lang.Integer(personId.get(0)));
        }
        java.util.List<String> organizedUeId = map.get("organizedUeId");
        if (organizedUeId != null && !organizedUeId.isEmpty()) {
            key.setOrganizedUeId(new java.lang.Integer(organizedUeId.get(0)));
        }
        return key;
    }

    public StudentOrganizedUeFacadeREST() {
        super(StudentOrganizedUe.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(StudentOrganizedUe entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, StudentOrganizedUe entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entity.StudentOrganizedUePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public StudentOrganizedUe find(@PathParam("id") PathSegment id) {
        entity.StudentOrganizedUePK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<StudentOrganizedUe> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<StudentOrganizedUe> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
