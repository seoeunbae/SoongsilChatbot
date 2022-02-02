package ssu.haksik.haksik.gisik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssu.haksik.haksik.gisik.entity.Gisik;


@Repository
public interface GisikRepository extends JpaRepository<Gisik,Long> {
    Gisik findByEatingTimeAndDay(int eatingTime, int day);
}