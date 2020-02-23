package dao;

import entities.Seance;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

//DAO - Data Access Object for Person entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class SeanceDAO {
    private final static String UNIT_NAME = "jsfcourse-simplePU";

    // Dependency injection (no setter method is needed)
    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    public void create(Seance seance) {
        em.persist(seance);
    }

    public Seance merge(Seance seance) {
        return em.merge(seance);
    }

    public void remove(Seance seance) { em.remove(em.merge(seance)); }

    public Seance find(Object id) {
        return em.find(Seance.class, id);
    }

    public List<Seance> getFullList() {
        List<Seance> list = null;

        Query query = em.createQuery("select s from Seance s");

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }








}
