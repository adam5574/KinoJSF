package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Movie {
    private int idmovie;
    private String title;
    private int time;
    private Category categoryByIdcategory;

    @Id
    @Column(name = "idmovie", nullable = false)
    public int getIdmovie() {
        return idmovie;
    }

    public void setIdmovie(int idmovie) {
        this.idmovie = idmovie;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 40)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "time", nullable = false)
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return idmovie == movie.idmovie &&
                time == movie.time &&
                Objects.equals(title, movie.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idmovie, title, time);
    }

    @ManyToOne
    @JoinColumn(name = "idcategory", referencedColumnName = "idcategory", nullable = false)
    public Category getCategoryByIdcategory() {
        return categoryByIdcategory;
    }

    public void setCategoryByIdcategory(Category categoryByIdcategory) {
        this.categoryByIdcategory = categoryByIdcategory;
    }
}
