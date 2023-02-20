package ssu.haksik.haksik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssu.haksik.haksik.entity.Gisik;
import ssu.haksik.haksik.common.enums.GisikEatingTime;

import java.util.List;


@Repository
public interface GisikRepository extends JpaRepository<Gisik,Long> {
    Gisik findByEatingTimeAndDate(GisikEatingTime eatingTime, String date);
    List<Gisik> findAll();
}