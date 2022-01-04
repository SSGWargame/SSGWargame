package SSG.SSGWargame.Controller;

import SSG.SSGWargame.domain.Account.Account;
import SSG.SSGWargame.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping(value = "/api/Account")
@RequestMapping(value = "/Account")
//이 Controller의 공동 상위 경로
@RequiredArgsConstructor // ??
public class AccountController {

    @Autowired
    private AccountService accountService;

    //get all accounts
    /*
    public List<AccountController> readAccounts(@RequestParam(value = "ID", defaultValue = ""))
    우선, 이 Parameter 전달 사용하지 말아보자. 리스트 반환과, 단건반환이 같이있으면, 로직이 복잡해진다.
    또, API Server와 Front Server를 분리하지 않을 것이다. Rest API는 포기하고,
    RESTful로 전환하자.
    //RESTful이 맞는 표현인가?
    -> 그냥.. 영한님 강의 비슷하게 따라가자..
     */
//    @GetMapping("/") //상위경로가 /api/Account이니, 결과적으로 /api/Account인 셈이다.
    @GetMapping("/")
    public String readAccounts(Model model) {
        List<Account> accounts = accountService.getAll();
        model.addAttribute("accounts", accounts);
        return "Accounts/accountList";
    }

    @GetMapping("/{id}")
    public String readOne(Model model, @PathVariable final Long id) {
        Optional<Account> account = accountService.getOne(id);
        model.addAttribute("account", account);
        return "Accounts/" + Long.toString(id);
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("accountForm", new AccountForm());
        return "Accounts/createAccountFrom";
    }

//    @PostMapping("new")
//    public String create(@Valid AccountForm form, BuildingResult result) {

//    }

//    @PostMapping("/Account")
//    public String joinMember()

//    @PutMapping("/Account")
//    public AccountController updateAccounts() {
//
//    }
//
//    @DeleteMapping("/Account")
//    public AccountController deleteAccounts() {
//
//    }
}


//api와 view를 분리해서 controller로 각각 작성하자.
