package SSG.SSGWargame.repository;

import SSG.SSGWargame.domain.Solution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@Repository
public class SolutionRepository {

    @Autowired private EntityManager em;

    public Long save(Solution solution){
        em.persist(solution);
        return solution.getId();
    }

    public Optional<Solution> findOne(Long id) {
        return Optional.ofNullable(em.find(Solution.class, id));
    }


    //Criteria이용 -> JPQL로는 다음번에 해보자.
//    "select s from Solution s where s.id = :id" //이거 아닐까?
    public List<Solution> findAllByProblem(Long problemId){
        //Criteria 쿼리 빌더
        CriteriaBuilder cb = em.getCriteriaBuilder();

        //Criteria 생성, 반환타입 지정
        CriteriaQuery<Solution> cq = cb.createQuery(Solution.class);

        //from 절
        Root<Solution> m = cq.from(Solution.class);

        //검색조건
        Predicate problemEqual = cb.equal(m.<Integer>get("problems"), problemId);// column명 아니고, entity의 attribute명이다.

        //정렬
        Order idDesc = cb.desc(m.get("id"));

        //쿼리 생성
        cq.select(m)
                .where(problemEqual) // list로 전달한다.
                .orderBy(idDesc);

        TypedQuery<Solution> query = em.createQuery(cq); //반환 타입이 정해진 쿼리.

        return query.getResultList();
    }

    public void deleteById(Long id) {
        Solution solution = findOne(id).orElseThrow(IllegalStateException::new);
        em.remove(solution);
    }
}
