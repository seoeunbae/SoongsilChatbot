package ssu.haksik.haksik.dodam;

import org.springframework.data.jpa.repository.JpaRepository;
import ssu.haksik.haksik.common.crawling.EatingTime;
import ssu.haksik.haksik.gisik.Gisik;

public interface DodamRepository extends JpaRepository<Dodam, Long> {
    Dodam findByEatingTime(EatingTime eatingTime);
}
