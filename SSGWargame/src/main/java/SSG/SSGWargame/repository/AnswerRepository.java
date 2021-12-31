package SSG.SSGWargame.repository;

import SSG.SSGWargame.domain.Answer;
import SSG.SSGWargame.domain.QnA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
    List<Answer> findByQna(QnA qna);
    void deleteByQna(QnA qna);
}
