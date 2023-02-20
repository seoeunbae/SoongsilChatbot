package ssu.haksik.haksik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssu.haksik.haksik.common.enums.EatingTime;
import ssu.haksik.haksik.entity.FacultyLounge;

public interface FacultyLoungeRepository extends JpaRepository<FacultyLounge, Long> {
    FacultyLounge findByEatingTime(EatingTime eatingTime);
}
