package ssu.haksik.haksik.gisik.entity;

import ssu.haksik.haksik.gisik.enums.GisikEatingTime;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
public class Gisik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String foods;
    @Column
    @Enumerated(EnumType.STRING)
    private DayOfWeek day;
    @Column
    @Enumerated(EnumType.STRING)
    private GisikEatingTime eatingTime;

    public Gisik(String foods, GisikEatingTime eatingTime, DayOfWeek day){
        this.foods = foods;
        this.day = day;
        this.eatingTime = eatingTime;
    }

    public Gisik() { }

    public String getFoods(){
        return foods;
    }

    public void changeFoods(String foods) { this.foods = foods; }
}