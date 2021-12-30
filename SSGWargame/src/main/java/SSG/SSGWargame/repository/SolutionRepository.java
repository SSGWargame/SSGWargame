package SSG.SSGWargame.repository;

import SSG.SSGWargame.domain.Problem;
import SSG.SSGWargame.domain.Solution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SolutionRepository {

    @Autowired private EntityManager em;

//    public List<Solution> findAllByProblem(SolutionSearch solutionSearch){
//        em.createQuery("select s from Solution s join ") //우선 패스.. 양방향 연관관계써서 우선 해결하자.
//        return new ArrayList<>();
//    }

    public List<Solution> findByProblem(Problem problem){
        return problem.getSolutions();
    }
}
