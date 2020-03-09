import dao.MovieDAO;
import dao.CategoryDAO;
import entities.Category;
import entities.Movie;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class MovieAddBB implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String PAGE_PERSON_LIST = "personList?faces-redirect=true";
    private static final String PAGE_STAY_AT_THE_SAME = null;
    private static final String PAGE_MENU = "menu?faces-redirect=true";

    private Movie movie = new Movie();
    private Category category;
    private Movie loaded = null;
    private List<Category> categories;
    private String movieName;
    private int movieTime;
    private String categoryName;
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    @FacesConverter(forClass = Category.class)

    @PostConstruct
    public void init() {
        categories = categoryDAO.getFullList();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public int getMovieTime() {
        return movieTime;
    }

    public void setMovieTime(int movieTime) {
        this.movieTime = movieTime;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @EJB
    MovieDAO movieDAO;

    @EJB
    CategoryDAO categoryDAO;


    @Inject
    FacesContext context;

    @Inject
    Flash flash;


    public Movie getMovie() {
        return movie;
    }


    public void onLoad() throws IOException {
        // 1. load person passed through session
        // HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        // loaded = (Person) session.getAttribute("person");

        // 2. load person passed through flash
        loaded = (Movie) flash.get("movie");

        // cleaning: attribute received => delete it from session
        if (loaded != null) {
            movie = loaded;
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
        if (loaded == null) {
            return PAGE_STAY_AT_THE_SAME;
        }


        try {
            if (movieDAO.findMovieByName(movieName) == null) {
                // new record
                movie.setTitle(movieName);
                movie.setTime(movieTime);
                Category movieCategory = null;
                for(Category category : categories) {
                    if(category.getCategory().equals(categoryName))
                        movieCategory = category;
                }
                movie.setCategoryByIdcategory(movieCategory);
                movieDAO.create(movie);
            } else {
                context.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "taki film już istnieje", null));
                return PAGE_STAY_AT_THE_SAME;
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
            return PAGE_STAY_AT_THE_SAME;
        }


        return PAGE_MENU;

    }
}
