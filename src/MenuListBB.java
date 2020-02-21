import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.Flash;

import dao.MovieDAO;
import entities.Movie;
import entities.User;


@Named
@RequestScoped
public class MenuListBB {

    private static final String PAGE_PERSON_LIST = "personList?faces-redirect=true";
    private static final String PAGE_STAY_AT_THE_SAME = null;
    private static final String PAGE_MOVIE = "movie?faces-redirect=true";
    private static final String PAGE_MENU = "menu?faces-redirect=true";

    @Inject
    ExternalContext extcontext;

    @Inject
    Flash flash;


    public String menu(){

        return PAGE_MENU;
    }

    public String movie(){

        return PAGE_MOVIE;
    }
    public String personList(){
    return PAGE_PERSON_LIST;
    }
}