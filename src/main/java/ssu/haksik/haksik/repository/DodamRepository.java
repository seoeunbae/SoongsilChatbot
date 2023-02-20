package ssu.haksik.haksik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssu.haksik.haksik.common.enums.EatingTime;
import ssu.haksik.haksik.entity.Dodam;

@Repository
public interface DodamRepository extends JpaRepository<Dodam, Long> {
    Dodam findByEatingTime(EatingTime eatingTime);
}
