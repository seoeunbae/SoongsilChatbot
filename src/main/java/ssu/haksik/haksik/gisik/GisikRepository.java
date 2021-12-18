package ssu.haksik.haksik.gisik;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssu.haksik.haksik.common.crawling.EatingTime;

@Repository
public interface GisikRepository extends JpaRepository<Gisik,Long> {
    Gisik findByEatingTimeAndDay(int eatingTime, int day);
}