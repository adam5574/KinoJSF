package dao;

import entities.Category;

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
public class CategoryDAO {
    private final static String UNIT_NAME = "jsfcourse-simplePU";

    // Dependency injection (no setter method is needed)
    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    public void create(Category category) {
        em.persist(category);
    }

    public Category merge(Category category) {
        return em.merge(category);
    }

    public void remove(Category category) {
        em.remove(em.merge(category));
    }

    public Category find(Object id) {
        return em.find(Category.class, id);
    }

    public List<Category> getFullList() {
        List<Category> list = null;

        Query query = em.createQuery("select c from Category c");

        try {
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }



}
