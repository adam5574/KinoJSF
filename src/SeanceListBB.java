import dao.ReservationDAO;
import dao.SeanceDAO;
import dao.UserDAO;
import entities.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;


@Named
@RequestScoped
public class SeanceListBB {


    private static final String PAGE_STAY_AT_THE_SAME = null;
    private static final String PAGE_SEANCE_ADD = "seanceAdd?faces-redirect=true";
    private static final String PAGE_PROFIL = "myAcc?faces-redirect=true";
    private static final String PAGE_LOG_IN = "login?faces-redirect=true";


    private String title;

    private int ticketNumber;

    private int seanceNr;

    private List<Seance> aseance;


    @PostConstruct
    public void init() {
        aseance = seanceDAO.getAvailableSeance();
    }

    public void setAseance(List<Seance> aseance) {
        this.aseance = aseance;
    }

    public List<Seance> getAseance() {
        return aseance;
    }



    @Inject
    ExternalContext extcontext;
    @Inject
    FacesContext context;

    @Inject
    Flash flash;

    @EJB
    SeanceDAO seanceDAO;
    @EJB
    ReservationDAO reservationDAO;
    @EJB
    UserDAO userDAO;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Seance> getFullList() {
        return seanceDAO.getFullList();
    }

    public List<Seance> getAvailableSeance() {
        return seanceDAO.getAvailableSeance();
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }


    public int getSeanceNr() {
        return seanceNr;
    }

    public void setSeanceNr(int seanceNr) {
        this.seanceNr = seanceNr;
    }

    public String newSeance() {
        Seance seance = new Seance();

        //1. Pass object through session
        //HttpSession session = (HttpSession) extcontext.getSession(true);
        //session.setAttribute("person", person);

        //2. Pass object through flash
        flash.put("seance", seance);

        return PAGE_SEANCE_ADD;
    }

    public String newReservation(String nicks) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Reservation reservation = new Reservation();

//        if(seanceDAO.findNr(getSeanceNr())==false){
////            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
////                    "Brak wybranego seansu.", null));
////            return PAGE_STAY_AT_THE_SAME;
////        }

            boolean go=false;
            otherloop:
        for(Seance seance : aseance) {
            if(seance.getIdseanse()==getSeanceNr()){
                go=true;
                break otherloop;

            }

        }

        if(go==false){
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Brak wybranego seansu.", null));
            return PAGE_STAY_AT_THE_SAME;

        }


        Seance idsec=seanceDAO.findSingleSeance(getSeanceNr());

        Seance toupdate= seanceDAO.findSingleSeance(idsec.getIdseanse());
        int minus=toupdate.getTickets();
            if(ticketNumber<1 || ticketNumber>4){
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Dozwolona ilość biletów to 1-4 szt.", null));
                return PAGE_STAY_AT_THE_SAME;

            }

            else {

                if (minus < ticketNumber) {
                    ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Brak tylu wolnych biletów. Pozostało : " + minus + " sztuk.", null));
                    return PAGE_STAY_AT_THE_SAME;

                } else {

                    try {
                        reservation.setAmount(ticketNumber);
                        reservation.setSeanceByIdseance(seanceDAO.findSingleSeance(idsec.getIdseanse()));
                        reservation.setUserByIduser(userDAO.findSinglePerson(nicks));

                        reservationDAO.create(reservation);
                        minus = minus - ticketNumber;
                        toupdate.setTickets(minus);
                        seanceDAO.merge(toupdate);

                    } catch (Exception e) {
                        e.printStackTrace();
                        context.addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
                        return PAGE_STAY_AT_THE_SAME;
                    }
                }
            }
        return PAGE_PROFIL;

    }

    public String login(){

        return PAGE_LOG_IN;
    }

    public String deleteSeance(Seance seance){
       seanceDAO.remove(seance);
        return PAGE_STAY_AT_THE_SAME;
    }

}
