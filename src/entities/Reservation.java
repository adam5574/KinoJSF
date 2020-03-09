package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Reservation {
    private int idreservation;
    private int amount;
    private User userByIduser;
    private Seance seanceByIdseance;

    @Id
    @Column(name = "idreservation", nullable = false)
    public int getIdreservation() {
        return idreservation;
    }

    public void setIdreservation(int idreservation) {
        this.idreservation = idreservation;
    }

    @Basic
    @Column(name = "amount", nullable = false)
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return idreservation == that.idreservation &&
                amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idreservation, amount);
    }
    @ManyToOne
    @JoinColumn(name = "nick", referencedColumnName = "nick", nullable = false)
    public User getUserByIduser() {
        return  userByIduser;
    }

    public void setUserByIduser(User userByIduser) {
        this.userByIduser = userByIduser;
    }

    @ManyToOne
    @JoinColumn(name = "idseance", referencedColumnName = "idseanse", nullable = false)
    public Seance getSeanceByIdseance() {
        return seanceByIdseance;
    }

    public void setSeanceByIdseance(Seance seanceByIdseance) {
        this.seanceByIdseance = seanceByIdseance;
    }
}
