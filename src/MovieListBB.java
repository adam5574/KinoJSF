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

    public List<Movie> getCatName(){ return movieDAO.getCatName();
    }
    public List<Movie> getList(){
        List<Movie> list = null;

        //1. Prepare search params
        Map<String,Object> searchParams = new HashMap<String, Object>();

        if (title != null && title.length() > 0){
            searchParams.put("title", title);
        }

        //2. Get list
        list = movieDAO.getList(searchParams);

        return list;
    }
    public String menu(){

        return PAGE_PERSON_LIST;
    }

    public String movie(){

        return PAGE_MOVIE;
    }



}
