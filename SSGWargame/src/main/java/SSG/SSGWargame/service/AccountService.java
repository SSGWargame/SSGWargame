package SSG.SSGWargame.service;

import SSG.SSGWargame.auth.AccountDetail;
import SSG.SSGWargame.domain.Account.Account;
import SSG.SSGWargame.domain.Account.Links;
import SSG.SSGWargame.domain.Account.Score;
import SSG.SSGWargame.repository.AccountRepository;
import SSG.SSGWargame.service.dto.AccountValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountService implements UserDetailsService {
    //여기에서의 REST는 기능명세같은 느낌이다. 실제 REST의 처리는 controller에서 한다.
    //controller는 정보를 취합하여 service에 호출하는 역할을 한다.
    @Autowired AccountRepository accountRepository;
    @Autowired PasswordEncoder passwordEncoder;


    //find /account : get
    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    //findOne /account/{id} : get
    //Optional이 안되네? -> findOne을 Optional로 바꿈
    public Optional<Account> getOne(Long id){
        return accountRepository.findOne(id);
    }

    public Optional<Account> getOne(String id){
        return accountRepository.findById(id);
    }

    /**
     * 회원가입
     * @param value
     * @return 회원 index
     */
    //join /value : post
    public Account join(AccountValue value) {
        //Validate whether there is account which has value.id as it's id
        validDuplication(value.getUsername());

       //Create Account object
        Account account = new Account();
        account.setUsername(value.getUsername());
        account.setPw(
                passwordEncoder.encode(value.getPw())
        );
        account.setIntroduce(value.getIntroduce());
        account.setNickname(value.getNickname());
        account.setLinks(new Links(
                value.getLink_github(),
                value.getLink_sns(),
                value.getLink_mail()
        ));
        account.setProfileImgLink(value.getProfileImgLink());
        account.setDomainFields(value.getDomainFields());
        account.setScore(
                new Score(
                        value.getPwnable().orElse(0),
                        value.getWebhacking().orElse(0),
                        value.getReversing().orElse(0),
                        value.getMisc().orElse(0),
                        value.getEtc().orElse(0)
                )
        );
        //Spring Security
        account.setRole("ROLE_USER");

        //persist object
        accountRepository.save(account);

        return account;
    }

    /**
     * ID(index아님)가 중복되면, RuntimeError발생.
     * @param id
     */
    private void validDuplication(String id) {
        Optional<Account> temp = accountRepository.findById(id);
        if(temp.isPresent()){
            throw new IllegalStateException("이미 존재하는 ID입니다.");
        }
    }


    //update /account/{id} : put
    //transaction기준으로 entity의 내용이 변경되면 transaction commit시에 dirty checking으로 자동 수정된다.
    //save, update등의 메소드(update는 애초에 존재x)를 사용하지 않아도 된다.
    public void update(Long id, AccountValue value) {
        Account account = accountRepository.findOne(id).orElseThrow(EntityNotFoundException::new);
        if (!StringUtils.isEmpty(value.getUsername()))
            account.setUsername(value.getUsername());

        if (!StringUtils.isEmpty(value.getPw())) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            account.setPw(
                    passwordEncoder.encode(value.getPw())
            );
        }

        if (!StringUtils.isEmpty(value.getIntroduce()))
            account.setIntroduce(value.getIntroduce());

        if (!StringUtils.isEmpty(value.getNickname()))
            account.setNickname(value.getNickname());

        String linkGithub = value.getLink_github() != null ? value.getLink_github() : account.getLinks().getLintGithub();
        String linkSns = value.getLink_sns() != null ? value.getLink_sns() : account.getLinks().getLinkSns();
        String linkMail = value.getLink_mail() != null ? value.getLink_mail() : account.getLinks().getLinkMail();
        account.setLinks(new Links(linkGithub, linkSns, linkMail));


        if (!StringUtils.isEmpty(value.getProfileImgLink()))
            account.setProfileImgLink(value.getProfileImgLink());

        if (!StringUtils.isEmpty(value.getDomainFields()))
            account.setDomainFields(value.getDomainFields());

        Integer pwnable = value.getPwnable().orElse(account.getScore().getPwnable());
        Integer webhacking = value.getWebhacking().orElse(account.getScore().getWebhacking());
        Integer reversing = value.getReversing().orElse(account.getScore().getReversing());
        Integer misc = value.getMisc().orElse(account.getScore().getMisc());
        Integer etc = value.getEtc().orElse(account.getScore().getEtc());

        account.setScore(new Score(pwnable, webhacking, reversing, misc, etc));
    }

    //delete /account/{id} : delete
    public void delete(Long id){
        accountRepository.deleteByIdx(id);
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Account account = accountRepository.findById(id).orElseThrow(IllegalStateException::new);
        return new AccountDetail(account);
    }
}
