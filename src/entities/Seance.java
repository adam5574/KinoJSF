package entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Seance {
    private int idseanse;
    private Date seanceDate;
    private int tickets;
    private Movie movieByIdmovie;
    private Hall hallByIdhall;

    @Id
    @Column(name = "idseanse", nullable = false)
    public int getIdseanse() {
        return idseanse;
    }

    public void setIdseanse(int idseanse) {
        this.idseanse = idseanse;
    }

    @Basic
    @Column(name = "seance_date", nullable = false)
    public Date getSeanceDate() {
        return seanceDate;
    }

    public void setSeanceDate(Date seanceDate) {
        this.seanceDate = seanceDate;
    }

    @Basic
    @Column(name = "tickets", nullable = false)
    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seance seance = (Seance) o;
        return idseanse == seance.idseanse &&
                tickets == seance.tickets &&
                Objects.equals(seanceDate, seance.seanceDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idseanse, seanceDate, tickets);
    }

    @ManyToOne
    @JoinColumn(name = "idmovie", referencedColumnName = "idmovie", nullable = false)
    public Movie getMovieByIdmovie() {
        return movieByIdmovie;
    }

    public void setMovieByIdmovie(Movie movieByIdmovie) {
        this.movieByIdmovie = movieByIdmovie;
    }

    @ManyToOne
    @JoinColumn(name = "idhall", referencedColumnName = "idhall", nullable = false)
    public Hall getHallByIdhall() {
        return hallByIdhall;
    }

    public void setHallByIdhall(Hall hallByIdhall) {
        this.hallByIdhall = hallByIdhall;
    }
}
