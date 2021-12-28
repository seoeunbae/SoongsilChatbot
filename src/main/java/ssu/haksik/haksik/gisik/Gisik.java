package ssu.haksik.haksik.gisik;

import javax.persistence.*;

@Entity
public class Gisik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String foods;
    @Column
    private int day;
    @Column
    private int eatingTime;

    public Gisik(String foods, int day, int eatingTime){
        this.foods = foods;
        this.day = day;
        this.eatingTime = eatingTime;
    }

    public Gisik() { }

    public String getFoods(){
        return foods;
    }

    public void setFoods(String foods) { this.foods = foods; }
}