package SSG.SSGWargame.repository;

import SSG.SSGWargame.domain.Problems;
import SSG.SSGWargame.domain.QnA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnARepository extends JpaRepository<QnA, Long> {
    List<QnA> findByProblems(Problems problems);
}
