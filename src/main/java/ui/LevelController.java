package ui;

import business.LevelFacade;
import entity.Level;
import java.io.Serializable;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


@Named("levelController")
@ViewScoped
public class LevelController implements Serializable {
    
    @Inject
    private LevelFacade ejbFacade;
    
    public List<Level> getItemsAvailableSelectOne() {
        return ejbFacade.findAll();
    }
    
    @FacesConverter(forClass = Level.class)
    public static class LevelControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LevelController controller = (LevelController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "levelController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Level) {
                Level o = (Level) object;
                return getStringKey(o.getLevelId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Level.class.getName());
            }
        }

    }

    public LevelFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(LevelFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }
    
    
}

