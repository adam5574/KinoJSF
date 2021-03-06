import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.UserDAO;
import entities.User;

@Named
@ViewScoped
public class PersonEditBB implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String PAGE_MENU = "menu?faces-redirect=true";
    private static final String PAGE_PERSON = "personList?faces-redirect=true";
    private static final String PAGE_STAY_AT_THE_SAME = null;

    private User user = new User();
    private User loaded = null;

    @EJB
    UserDAO userDAO;

    @Inject
    FacesContext context;

    @Inject
    Flash flash;

    public User getUser() {
        return user;
    }

    public void onLoad() throws IOException {
        // 1. load person passed through session
        // HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        // loaded = (Person) session.getAttribute("person");

        // 2. load person passed through flash
        loaded = (User) flash.get("user");

        // cleaning: attribute received => delete it from session
        if (loaded != null) {
            user = loaded;
            // session.removeAttribute("person");
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
            // if (!context.isPostback()) { //possible redirect
            // context.getExternalContext().redirect("personList.xhtml");
            // context.responseComplete();
            // }
        }

    }

    public String saveData() {
        // no Person object passed
        if (loaded == null) {
            return PAGE_STAY_AT_THE_SAME;
        }

        try {
            if (user.getNick() == null) {
                // new record
                userDAO.create(user);
            } else {
                // existing record
                userDAO.merge(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
            return PAGE_STAY_AT_THE_SAME;
        }

        return PAGE_MENU;
    }

    public String register() {
        // no Person object passed
        if (loaded == null) {
            return PAGE_STAY_AT_THE_SAME;
        }
        user.setRole("user");

        try {
            if (userDAO.find(user.getNick())== null) {
                // new record
                userDAO.create(user);
            } else {
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "taki użytkownik już istnieje", null));
                return PAGE_STAY_AT_THE_SAME;
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
            return PAGE_STAY_AT_THE_SAME;
        }

        return PAGE_PERSON;
    }


}
