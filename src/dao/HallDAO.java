package dao;

import entities.Category;
import entities.Hall;
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
public class HallDAO {
    private final static String UNIT_NAME = "jsfcourse-simplePU";

    // Dependency injection (no setter method is needed)
    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    public void create(Hall hall) {
        em.persist(hall);
    }

    public Hall merge(Hall hall) {
        return em.merge(hall);
    }

    public void remove(Hall hall) {
        em.remove(em.merge(hall));
    }

    public Hall find(Object id) {
        return em.find(Hall.class, id);
    }

    public List<Hall> getFullList() {
        List<Hall> list = null;

        Query query = em.createQuery("select h from Hall h");

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Hall findSingleHall(int id) {
        try {
            Query query = em.createQuery("SELECT h FROM Hall h WHERE h.idhall = :id ", Hall.class);
            query.setParameter("id", id);
            return (Hall) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }



}
