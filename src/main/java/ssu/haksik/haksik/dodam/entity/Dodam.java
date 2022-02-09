package ssu.haksik.haksik.dodam.entity;

import ssu.haksik.haksik.common.enums.EatingTime;

import javax.persistence.*;

@Entity
public class Dodam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String foods;
    @Column
    @Enumerated(EnumType.STRING)
    private EatingTime eatingTime;

    public Dodam() {}

    public Dodam(String foods, EatingTime eatingTime){
        this.foods = foods;
        this.eatingTime = eatingTime;
    }

    public String getFoods(){
        return foods;
    }

    public void changeFoods(String foods) { this.foods = foods; }

}
