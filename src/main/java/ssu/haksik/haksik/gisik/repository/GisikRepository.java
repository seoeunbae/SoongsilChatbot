package ssu.haksik.haksik.gisik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssu.haksik.haksik.gisik.entity.Gisik;
import ssu.haksik.haksik.gisik.enums.GisikEatingTime;

import java.time.DayOfWeek;


@Repository
public interface GisikRepository extends JpaRepository<Gisik,Long> {
    Gisik findByEatingTimeAndDay(GisikEatingTime eatingTime, DayOfWeek day);
}