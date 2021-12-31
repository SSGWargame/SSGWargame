package SSG.SSGWargame.repository;

import SSG.SSGWargame.domain.Problems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemsRepository extends JpaRepository<Problems, Long> {
}
