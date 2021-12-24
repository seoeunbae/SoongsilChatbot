package ssu.haksik.haksik.dodam;

import ssu.haksik.haksik.common.crawling.EatingTime;

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

    public Dodam(String foods, EatingTime eatingtime){
        this.foods = foods;
        this.eatingTime = eatingTime;
    }

    public String getFoods(){
        return foods;
    }

    public void setFoods(String foods) { this.foods = foods; }

}
