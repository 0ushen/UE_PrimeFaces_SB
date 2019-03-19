package service;

import entity.Section;
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
@Api(value = "section", description = "Operations about a section")
@Path("section")
public class SectionFacadeREST extends AbstractFacade<Section> {

    @PersistenceContext(unitName = "UE_PrimeFaces_SB_PU")
    private EntityManager em;

    public SectionFacadeREST() {
        super(Section.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Add a new section to the database")
    public void create(@ApiParam(value = "Section object that needs to be added to the database", required = true) Section entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Update an existing section")
    public void edit(@ApiParam(value = "Section object that needs to be updated", required = true) Section entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(value = "Delete a section from the database")
    public void removeREST(@ApiParam(value = "Id of the section that needs to be deleted", required = true) @PathParam("id") Integer id) {
        super.remove(super.find(id));
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Find a specific section using an id")
    public Section findREST(@ApiParam(value = "Id of the section to return", required = true) @PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Path("/search")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Find a specific section with multiple search fields", 
            notes = "Each field is optionnal. It returns a list of section matching the query.")
    public List<Section> findByEntity(
            @ApiParam(value = "name of the section to search. works with substring matching.") @DefaultValue("") @QueryParam("name") String name,
            @ApiParam(value = "keyword to match in the description of the section . works with substring matching.") @DefaultValue("") @QueryParam("description") String description, 
            @ApiParam(value = "Id of the teacher responsable for this section") @DefaultValue("") @QueryParam("teacherId") Integer teacherId) {
        
                
        String sQuery = "SELECT s FROM Section s WHERE ";
        
        if(!"".equals(name)) {
            sQuery += "LOWER(s.name) LIKE LOWER(:name) AND ";
        }
        if(!"".equals(description)) {
            sQuery += "LOWER(s.description) LIKE LOWER(:description) AND ";
        }
        if(teacherId != null) {
            sQuery += "s.teacher.teacherId = :teacherId AND ";
        }
        sQuery = sQuery.substring(0, sQuery.length() - 5);
        Query query = em.createQuery(sQuery);
        if(!"".equals(name)) {
            query.setParameter("name", "%" + name + "%");
        }
        if(!"".equals(description)) {
            query.setParameter("description", "%" + description + "%");
        }
        if(teacherId != null) {
            query.setParameter("teacherId", teacherId);
        }
        
        return query.getResultList();
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Return all sections in the database")
    public List<Section> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Return all sections between 2 Id")
    public List<Section> findRangeREST(@ApiParam(value = "First id") @PathParam("from") Integer from, @ApiParam(value = "Second id") @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Return the number of elements in the section table")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
