package ssu.haksik.haksik.gisik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ssu.haksik.haksik.gisik.entity.Gisik;

import java.util.Optional;

@Repository
public interface GisikRepository extends JpaRepository<Gisik,Long> {
    Gisik findByEatingTimeAndDay(int eatingTime, int day);

//    @Transactional
//    @Modifying
//    @Query("update Gisik g set g.foods = :foods where g.eatingTime=:time and g.day = :day")
//    void updateGisikFoods(@Param("foods") String foods,@Param("day") int day, @Param("time") int time);


}