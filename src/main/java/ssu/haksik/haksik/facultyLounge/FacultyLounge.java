package ssu.haksik.haksik.facultyLounge;

import javax.persistence.*;

@Entity
public class FacultyLounge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String foods;

    public FacultyLounge(){}

    public FacultyLounge(String foods){
        this.foods = foods;
    }

    public String getFoods(){return foods;}

    public void setFood(String foods){this.foods = foods;}
}
