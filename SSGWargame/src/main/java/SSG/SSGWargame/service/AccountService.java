package SSG.SSGWargame.service;

import SSG.SSGWargame.domain.Account.Account;
import SSG.SSGWargame.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountService {
    //여기에서의 REST는 기능명세같은 느낌이다. 실제 REST의 처리는 controller에서 한다.
    //controller는 정보를 취합하여 service에 호출하는 역할을 한다.
    @Autowired AccountRepository accountRepository;


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
     * @param account
     * @return 회원 index
     */
    //join /account : post
    public Long join(Account account) {
        validDuplication(account);
        accountRepository.save(account);
        return account.getIdx();
    }

    /**
     * ID(index아님)가 중복되면, RuntimeError발생.
     * @param account
     */
    private void validDuplication(Account account) {
        Optional<Account> temp = accountRepository.findById(account.getId());
        if(temp.isPresent()){
            throw new IllegalStateException("이미 존재하는 ID입니다.");
        }
    }


    //update /account/{id} : put
    //transaction기준으로 entity의 내용이 변경되면 transaction commit시에 dirty checking으로 자동 수정된다.
    //save, update등의 메소드(update는 애초에 존재x)를 사용하지 않아도 된다.
//    public void update(Long id, AccountValue value) {
//        Account account = accountRepository.findOne(id).orElseThrow(EntityNotFoundException::new);
//        account.setIntroduce(value.getIntroduce());
//        account.setDomainFields(value.getDomainFields());
//        account.setLinks(value.getLinks());
//        account.setNickname(value.getNickname());
//        account.setId(value.getId());
//        account.setPw(value.getPw());
//        // 이 코드가 맞는지 모르겠다. 그리고 controller와 어떻게 연결될지도 모르겠다.
//        // 우선 이렇게 짜두고, Controller작성 후에 다시 해결하자
//    }
    public void update(Long id, AccountValue value) {
        Account account = accountRepository.findOne(id).orElseThrow(EntityNotFoundException::new);
        if (!StringUtils.isEmpty(value.getIntroduce()))
            account.setIntroduce(value.getIntroduce());
        if (!StringUtils.isEmpty(value.getDomainFields()))
            account.setDomainFields(value.getDomainFields());
        if (value.getLinks() != null)
            account.setLinks(value.getLinks());
        if (!StringUtils.isEmpty(value.getNickname()))
            account.setNickname(value.getNickname());
        if (!StringUtils.isEmpty(value.getId()))
            account.setId(value.getId());
        if (!StringUtils.isEmpty(value.getPw()))
            account.setPw(value.getPw());
        // 이 코드가 맞는지 모르겠다. 그리고 controller와 어떻게 연결될지도 모르겠다.
        // 우선 이렇게 짜두고, Controller작성 후에 다시 해결하자
    }

    //delete /account/{id} : delete
    public void delete(Long id){
        accountRepository.deleteByIdx(id);
    }

}
