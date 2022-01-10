package SSG.SSGWargame.auth;

import SSG.SSGWargame.domain.Account.Account;
import SSG.SSGWargame.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExService implements UserDetailsService {

    //private final ExRepository repository;
    private AccountRepository repository;

    @Transactional
    public void joinUser(Account account){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        account.setPw(passwordEncoder.encode(account.getPw()));
        repository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Account account = repository.findById(id).orElseThrow(IllegalStateException::new);
        return new AccountDetail(account);
    }
}
