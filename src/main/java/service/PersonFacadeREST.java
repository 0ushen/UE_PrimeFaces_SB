package service;

import entity.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/* Methods should not have the same name as one in the AbstractFacade parent class
 * if it doesn't @Override it. Or else swagger won't work.
 */


@Stateless
@Api(value = "person")
@Path("person")
public class PersonFacadeREST extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "UE_PrimeFaces_SB_PU")
    private EntityManager em;

    public PersonFacadeREST() {
        super(Person.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Add a new person to the database")
    public void create(@ApiParam(value = "Person object that needs to be added to the database", required = true) Person entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Update an existing person")
    public void edit(@ApiParam(value = "Person object that needs to be updated", required = true)  Person entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(value = "Delete a person from the database")
    public void removeREST(@ApiParam(value = "Id of the person that needs to be deleted", required = true) @PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Find a specific person using an id")
    public Person findREST(@ApiParam(value = "Id of the person to return", required = true) @PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Path("/teacherList")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @ApiOperation(value = "return a list of person based on if they are a teacher or not", 
            notes = "this query returns each persons for which isTeacher attribute has the same value of this method param")
    public List<Person> findTeacherList(@ApiParam(value = "true if we search for teachers only, false if teachers are to be excluded from the list") @DefaultValue("true") @QueryParam("isTeacher") boolean isTeacher) {
        Query query = em.createNamedQuery("Person.findByIsTeacher");
        query.setParameter("isTeacher", isTeacher);
        
        return query.getResultList();
    }
    

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Return all persons in the database")
    public List<Person> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Return all persons between 2 Id")
    public List<Person> findRange(@ApiParam(value = "First id") @PathParam("from") Integer from, @ApiParam(value = "Second id") @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Return the number of elements in the person table")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
