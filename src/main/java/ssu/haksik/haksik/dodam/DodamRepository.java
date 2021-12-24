package ssu.haksik.haksik.dodam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DodamRepository extends JpaRepository<Dodam, Long> {
    Dodam findByEatingTime(int eatingTime);
}
