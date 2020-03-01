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
public class MovieListBB {

    private static final String PAGE_PERSON_LIST = "personList?faces-redirect=true";
    private static final String PAGE_STAY_AT_THE_SAME = null;
    private static final String PAGE_MOVIE = "movie?faces-redirect=true";
    private static final String PAGE_MOVIE_ADD = "movieAdd?faces-redirect=true";

    private String title;

    @Inject
    ExternalContext extcontext;

    @Inject
    Flash flash;

    @EJB
   MovieDAO movieDAO;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Movie> getFullList(){ return movieDAO.getFullList();
    }



    public String menu(){

        return PAGE_PERSON_LIST;
    }



    public String newMovie(){
        Movie movie = new Movie();

        //1. Pass object through session
        //HttpSession session = (HttpSession) extcontext.getSession(true);
        //session.setAttribute("person", person);

        //2. Pass object through flash
        flash.put("movie", movie);

        return PAGE_MOVIE_ADD;
    }

}
