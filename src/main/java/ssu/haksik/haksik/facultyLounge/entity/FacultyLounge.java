package ssu.haksik.haksik.facultyLounge.entity;

import ssu.haksik.haksik.common.enums.EatingTime;
import javax.persistence.*;

@Entity
public class FacultyLounge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String foods;
    @Column
    @Enumerated(EnumType.STRING)
    private EatingTime eatingTime;

    public FacultyLounge(){}

    public FacultyLounge(String foods, EatingTime eatingTime) {
        this.foods = foods;
        this.eatingTime = eatingTime;
    }

    public String getFoods(){ return foods; }

    public void changeFood(String foods){ this.foods = foods; }
}
