package ssu.haksik.haksik.facultyLounge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssu.haksik.haksik.facultyLounge.entity.FacultyLounge;

public interface FacultyLoungeRepository extends JpaRepository<FacultyLounge, Long> {
    FacultyLounge findByEatingTime(int eatingTime);
}
