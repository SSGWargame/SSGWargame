package SSG.SSGWargame.service;

import SSG.SSGWargame.domain.Account.Account;
import SSG.SSGWargame.domain.Problems;
import SSG.SSGWargame.domain.Solution;
import SSG.SSGWargame.service.dto.AccountValue;
import SSG.SSGWargame.service.dto.SolutionValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class SolutionServiceTest {
    @Autowired
    private SolutionService solutionService;

    private AccountServiceTest accountServiceTest = new AccountServiceTest();
    @Autowired
    private AccountService accountService;

    @Autowired private ProblemsService problemsService;

    @Test
    public void 풀이등록(){
        //given
        //Register Account
        AccountValue accountValue = accountServiceTest.makeAccount("tony", "tony2022", "I'm tony", "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com", "temp");
        Account account = accountService.join(accountValue);

        //Register Problem
        Problems problem = new Problems(
                "Title: Test",
                "Flag test : 111-111",
                123,
                "Test Description",
                100,
                "fields",
                "hmm?"
        );
        problemsService.saveProblems(problem);

        //Regsiter Solution
        SolutionValue value = new SolutionValue();
        value.setProblemId(problem.getId());
        value.setAccountUsername(accountValue.getUsername());
        value.setTitle("Title : Test, Solution");
        value.setContent("Content : Test, Solution");

        //when
        Long solutionId = solutionService.add(value).getId();

        //then
        assertThat(solutionService.getById(solutionId))
                .returns(problem, from(Solution::getProblems))
                .returns(account, from(Solution::getAccount))
                .returns(value.getTitle(), from(Solution::getTitle))
                .returns(value.getContent(), from(Solution::getContent));
        assertThat(solutionService.getById(solutionId).getTime()).isNotNull();
    }

    @Test
    public void 문제별풀이조회(){
        //given
        //Register Account
        AccountValue accountValue = accountServiceTest.makeAccount("tony", "tony2022", "I'm tony", "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com", "temp");
        accountService.join(accountValue);
        //Register Problem
        Problems problem = new Problems(
                "Title: Test",
                "Flag test : 111-111",
                123,
                "Test Description",
                100,
                "fields",
                "hmm?"
        );
        problemsService.saveProblems(problem);

        //Register Solution
        SolutionValue value = new SolutionValue();
        value.setProblemId(problem.getId());
        value.setAccountUsername(accountValue.getUsername());
        value.setTitle("Title : Test, Solution");
        value.setContent("Content : Test, Solution");

        SolutionValue value1 = new SolutionValue();
        value1.setProblemId(problem.getId());
        value1.setAccountUsername(accountValue.getUsername());
        value1.setTitle("Title1 : Test, Solution");
        value1.setContent("Content1 : Test, Solution");

        Solution solution = solutionService.add(value);
        Solution solution1 = solutionService.add(value1);

        //when
        List<Solution> solutions = solutionService.getByProblem(problem.getId());

        //then
        assertThat(solutions)
                .contains(solution)
                .contains(solution1);

    }

//    @Test // 위 등록에서 다 함.
//    public void 풀이조회(){
//        //given
//
//        //when
//
//        //then
//    }
}