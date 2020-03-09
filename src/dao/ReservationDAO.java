package dao;


import entities.Reservation;
import entities.Seance;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

//DAO - Data Access Object for Person entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class ReservationDAO {
    private final static String UNIT_NAME = "jsfcourse-simplePU";

    // Dependency injection (no setter method is needed)
    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    public void create(Reservation reservation) {
        em.persist(reservation);
    }

    public Reservation merge(Reservation reservation) {
        return em.merge(reservation);
    }

    public void remove(Reservation reservation) {
        em.remove(em.merge(reservation));
    }

    public Reservation find(Object id) {
        return em.find(Reservation.class, id);
    }

    public List<Reservation> getFullList() {
        List<Reservation> list = null;

        Query query = em.createQuery("select r from Reservation r");

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Reservation> getUserReservation(String nickname) {
        List<Reservation> list = null;

        Query query = em.createQuery("select r from Reservation r where r.userByIduser.nick like :nickname");
        query.setParameter("nickname", nickname);
        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


}
