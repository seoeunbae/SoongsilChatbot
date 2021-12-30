package ssu.haksik.haksik.facultyLounge;

import javax.persistence.*;

@Entity
public class FacultyLounge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String foods;

    @Column
    private int eatingTime;

    public FacultyLounge(){}

    public FacultyLounge(String foods, int eatingTime) {
        this.foods = foods;
        this.eatingTime = eatingTime;
    }

    public String getFoods(){return foods;}

    public void setFood(String foods){this.foods = foods;}
}
