import dao.SeanceDAO;
import entities.Movie;
import entities.Seance;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Named
@RequestScoped
public class SeanceListBB {


    private static final String PAGE_STAY_AT_THE_SAME = null;



    private String title;

    @Inject
    ExternalContext extcontext;

    @Inject
    Flash flash;

    @EJB
    SeanceDAO seanceDAO;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Seance> getFullList(){ return seanceDAO.getFullList();
    }




}
