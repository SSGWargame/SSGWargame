package SSG.SSGWargame.service;

import SSG.SSGWargame.domain.Account.Account;
import SSG.SSGWargame.domain.Problems;
import SSG.SSGWargame.domain.Solution;
import SSG.SSGWargame.repository.ProblemsRepository;
import SSG.SSGWargame.repository.SolutionRepository;
import SSG.SSGWargame.service.dto.SolutionValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SolutionService {
    @Autowired private SolutionRepository solutionRepository;
    @Autowired private AccountService accountService;
    @Autowired private ProblemsRepository problemRepository;
    @Autowired private EntityManager em; // problemRepository 들어오면 바꾸고 없애자


    //등록
    public Solution add(SolutionValue value) {
        Solution solution = new Solution();

        //Entity find
        Problems problem = em.find(Problems.class, value.getProblemId());
        Account account = accountService.getOne(value.getAccountId())
                .orElseThrow(IllegalStateException::new);

        solution.setProblems(problem);
        solution.setAccount(account);
        solution.setTitle(value.getTitle());
        solution.setContent(value.getContent());
        solution.setTime(LocalDateTime.now());

        //persist
        solutionRepository.save(solution);
        return solution;
    }
    
    //조회
    //문제별 조회
    public List<Solution> getByProblem(Long problemId){
        return solutionRepository.findAllByProblem(problemId);
    }

    //단건조회
    public Solution getById(Long id){
        return solutionRepository.findOne(id)
                .orElseThrow(IllegalStateException::new);
    }
    
    //수정
    public void update(Long id, SolutionValue value){
        Solution solution = solutionRepository.findOne(id).orElseThrow(IllegalStateException::new);
        if (!StringUtils.isEmpty(value.getContent()))
            solution.setContent(value.getContent());
        if (!StringUtils.isEmpty(value.getTitle()))
            solution.setTitle(value.getTitle());
    }
    
    //삭제
    public void delete(Long id){
        solutionRepository.deleteById(id);
    }
}
