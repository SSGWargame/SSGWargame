package SSG.SSGWargame.Controller;

import SSG.SSGWargame.domain.Account.Account;
import SSG.SSGWargame.domain.Problems;
import SSG.SSGWargame.domain.Solution;
import SSG.SSGWargame.service.AccountService;
import SSG.SSGWargame.service.ProblemsService;
import SSG.SSGWargame.service.SolutionService;
import SSG.SSGWargame.service.dto.SolutionValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin
@RestController
@RequestMapping("/api/v1.0/Solution")
public class SolutionController {

    /**
     * 풀이 등록 : 인증한 사람만.
     * 조회 : 인증한 사람만.
     */
    @Autowired
    private SolutionService solutionService;
    @Autowired
    private ProblemsService problemsService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/{solutionId}")
    public Solution readSolution(@PathVariable Long solutionId) {
        return solutionService.getById(solutionId);
    }

    @PostMapping("/")
    public Solution save(@RequestBody SolutionValue value) {
        Solution solution = solutionService.add(value);
        return solution;
    }

    @PutMapping("/{solutionId}")
    public void modifySolution(@PathVariable Long solutionId, @RequestBody SolutionValue value) {
        solutionService.update(
                solutionId,
                value
        );
    }

    @DeleteMapping("/{solutionId}")
    public void deleteSolution(@PathVariable Long solutionId) {
        solutionService.delete(solutionId);
    }
}
