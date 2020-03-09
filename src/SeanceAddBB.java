import dao.*;
import entities.*;

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
import java.sql.Date;
import java.util.List;


@Named
@ViewScoped
public class SeanceAddBB implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String PAGE_PERSON_LIST = "personList?faces-redirect=true";
    private static final String PAGE_STAY_AT_THE_SAME = null;
    private static final String PAGE_MENU = "menu?faces-redirect=true";

    private Seance seance = new Seance();
    private Seance loaded = null;


    private String workerName;
    private List<Movie> movies;
    private String movieName;
    private List<User> workers;


    private List<Hall> halls;

    public String getHallnumber() {
        return hallnumber;
    }

    public void setHallnumber(String hallnumber) {
        this.hallnumber = hallnumber;
    }

    private String hallnumber;

    private int hallNumber;


    private int tickets;


    private String date;


    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }


    public int getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(int hallNumber) {
        this.hallNumber = hallNumber;
    }

    public List<User> getWorkers() {
        return workers;
    }

    public void setWorkers(List<User> workers) {
        this.workers = workers;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }

    @FacesConverter(forClass = User.class)


    @PostConstruct
    public void init() {
        movies = movieDAO.getFullList();
        workers = userDAO.getWorkers();
        halls = hallDAO.getFullList();
    }


    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }


    @EJB
    MovieDAO movieDAO;

    @EJB
    UserDAO userDAO;
    @EJB
    HallDAO hallDAO;
    @EJB
    SeanceDAO seanceDAO;

    @Inject
    FacesContext context;

    @Inject
    Flash flash;


    public void onLoad() throws IOException {

        loaded = (Seance) flash.get("seance");


        if (loaded != null) {
            seance = loaded;

        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));

        }

    }

    public String saveData() {
        if (loaded == null) {
            return PAGE_STAY_AT_THE_SAME;
        }


        try {

            seance.setTickets(tickets);
            // seance.setSeanceDate(date);
            User workerUser = null;
            for (User user : workers) {
                if (user.getNick().equals(workerName))
                    workerUser = user;
            }
            seance.setUserByIduser(workerUser);


            Movie movieN = null;
            for (Movie movie : movies) {
                if (movie.getTitle().equals(movieName))
                    movieN = movie;
            }
            seance.setMovieByIdmovie(movieN);

            seance.setHallByIdhall(hallDAO.findSingleHall(hallNumber));

            Date date=Date.valueOf(getDate());
            seance.setSeanceDate(date);
            seanceDAO.create(seance);


        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
            return PAGE_STAY_AT_THE_SAME;
        }


        return PAGE_MENU;

    }
}

