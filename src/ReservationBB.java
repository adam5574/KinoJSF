import dao.ReservationDAO;
import entities.Reservation;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;


@Named
@RequestScoped
public class ReservationBB {


    private static final String PAGE_STAY_AT_THE_SAME = null;




    @Inject
    ExternalContext extcontext;

    @Inject
    Flash flash;

    @EJB
    ReservationDAO reservationDAO;



    public List<Reservation> getFullList(){ return reservationDAO.getFullList();
    }
    public List<Reservation> getUserReservation(String nickname){

        return reservationDAO.getUserReservation(nickname);
    }







}
