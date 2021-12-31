package ssu.haksik.haksik.facultyLounge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyLoungeRepository extends JpaRepository<FacultyLounge, Long> {
    FacultyLounge findByEatingTime(int eatingTime);
}
