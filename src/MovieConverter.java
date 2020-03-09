import dao.CategoryDAO;
import entities.Category;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
@ManagedBean
@RequestScoped
public class MovieConverter  implements Converter {
    @EJB
    CategoryDAO categoryDAO;

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        if (modelValue == null) {
            return "";
        }

        if (modelValue instanceof Category) {
            return String.valueOf(((Category) modelValue).getCategory());
        } else {
            throw new ConverterException(new FacesMessage(modelValue + " is not a valid Warehouse"));
        }
    }
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        if (submittedValue == null || submittedValue.isEmpty()) {
            return null;
        }

        try {
            return categoryDAO.find(Long.valueOf(submittedValue));
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(submittedValue + " is not a valid Warehouse ID"), e);
        }
    }



}
