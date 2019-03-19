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
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/webresources");
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
