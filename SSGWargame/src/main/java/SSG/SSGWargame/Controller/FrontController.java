package SSG.SSGWargame.Controller;

import SSG.SSGWargame.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FrontController {

    @GetMapping("/")
    public String main(){
        boolean tmpLogin = true;
        if(tmpLogin)
            return "/main";
        else
            return "/login";
    }

}
