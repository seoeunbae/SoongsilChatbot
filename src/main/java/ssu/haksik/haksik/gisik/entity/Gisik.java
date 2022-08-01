package ssu.haksik.haksik.gisik.entity;

import lombok.Getter;
import ssu.haksik.haksik.gisik.enums.GisikEatingTime;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
@Getter
public class Gisik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String foods;
    @Column
    private String date;
    @Column
    private String dayOfWeek;

    @Column
    @Enumerated(EnumType.STRING)
    private GisikEatingTime eatingTime;

    public Gisik(String foods, GisikEatingTime eatingTime, String date, String dayOfWeek){
        this.foods = foods;
        this.date = date;
        this.eatingTime = eatingTime;
        this.dayOfWeek = dayOfWeek;
    }

    public Gisik() { }

    public String getFoods(){
        return foods;
    }

    public void changeFoods(String foods) { this.foods = foods; }
}