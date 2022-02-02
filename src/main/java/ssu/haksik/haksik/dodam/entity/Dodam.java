package ssu.haksik.haksik.dodam.entity;

import ssu.haksik.haksik.common.EatingTime;

import javax.persistence.*;

@Entity
public class Dodam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String foods;
    @Column
    private EatingTime eatingTime;

    public Dodam() {}

    public Dodam(String foods, EatingTime eatingTime){
        this.foods = foods;
        this.eatingTime = eatingTime;
    }

    public String getFoods(){
        return foods;
    }

    public void setFoods(String foods) { this.foods = foods; }

}
