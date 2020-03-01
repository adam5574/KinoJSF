import dao.MovieDAO;
import dao.CategoryDAO;
import entities.Category;
import entities.Movie;
import entities.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
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

    private Movie movie = new Movie();
    private Category category;
    private Movie loaded = null;
    private List<Category> categories;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @EJB
    MovieDAO movieDAO;
    CategoryDAO categoryDAO;

    @PostConstruct
    public void init() {
        categories =categoryDAO.getFullList();
    }

    @Inject
    FacesContext context;

    @Inject
    Flash flash;




    public List<Movie> getcat() {
         List<Movie> cat;
        return cat = movieDAO.getCatList();
    }

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
                movieDAO.create(movie);


        return PAGE_PERSON_LIST;
    }
}
