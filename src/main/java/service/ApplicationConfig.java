package service;

import io.swagger.jaxrs.config.BeanConfig;
import java.util.Set;
import javax.ws.rs.core.Application;


@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {
    
    public ApplicationConfig() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("35.180.119.33:8080");
        beanConfig.setBasePath("/UE_PrimeFaces_SB/webresources");
        beanConfig.setResourcePackage(SectionFacadeREST.class.getPackage().getName());
        beanConfig.setScan(true);
    }
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        //addRestResourceClasses(resources);
        
        resources.add(service.CapacityFacadeREST.class);
        resources.add(service.EvaluationFacadeREST.class);
        resources.add(service.IndicatorFacadeREST.class);
        resources.add(service.LevelFacadeREST.class);
        resources.add(service.OrganizedUeFacadeREST.class);
        resources.add(service.PersonFacadeREST.class);
        resources.add(service.PlanningFacadeREST.class);
        resources.add(service.PresenceFacadeREST.class);
        resources.add(service.SectionFacadeREST.class);
        resources.add(service.StudentOrganizedUeFacadeREST.class);
        resources.add(service.UeFacadeREST.class);
        
        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.ClassNotFoundExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.ConversionExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.DatabaseExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.EntityExistsExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.EntityNotFoundExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.IOExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.IllegalAccessExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.IllegalArgumentExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.IllegalStateExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.InvocationTargetExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.JAXBExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.JPARSConfigurationExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.JPARSExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.MalformedURLExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.NamingExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.NoResultExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.NoSuchMethodExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.NonUniqueResultExceptionExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.OptimisticLockExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.PersistenceExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.PessimisticLockExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.QueryTimeoutExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.RollbackExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.TransactionRequiredExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.exceptions.UnsupportedMediaTypeExceptionMapper.class);
        resources.add(org.eclipse.persistence.jpa.rs.resources.EntityResource.class);
        resources.add(org.eclipse.persistence.jpa.rs.resources.PersistenceResource.class);
        resources.add(org.eclipse.persistence.jpa.rs.resources.PersistenceUnitResource.class);
        resources.add(org.eclipse.persistence.jpa.rs.resources.QueryResource.class);
        resources.add(org.eclipse.persistence.jpa.rs.resources.SingleResultQueryResource.class);
        resources.add(org.eclipse.persistence.jpa.rs.resources.unversioned.EntityResource.class);
        resources.add(org.eclipse.persistence.jpa.rs.resources.unversioned.PersistenceResource.class);
        resources.add(org.eclipse.persistence.jpa.rs.resources.unversioned.PersistenceUnitResource.class);
        resources.add(org.eclipse.persistence.jpa.rs.resources.unversioned.QueryResource.class);
        resources.add(org.eclipse.persistence.jpa.rs.resources.unversioned.SingleResultQueryResource.class);
        resources.add(service.CapacityFacadeREST.class);
        resources.add(service.EvaluationFacadeREST.class);
        resources.add(service.IndicatorFacadeREST.class);
        resources.add(service.LevelFacadeREST.class);
        resources.add(service.OrganizedUeFacadeREST.class);
        resources.add(service.PersonFacadeREST.class);
        resources.add(service.PlanningFacadeREST.class);
        resources.add(service.PresenceFacadeREST.class);
        resources.add(service.SectionFacadeREST.class);
        resources.add(service.StudentOrganizedUeFacadeREST.class);
        resources.add(service.UeFacadeREST.class);
    }
    
}
