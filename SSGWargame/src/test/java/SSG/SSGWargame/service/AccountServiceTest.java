package SSG.SSGWargame.service;

import SSG.SSGWargame.domain.Account.Account;
import SSG.SSGWargame.domain.Account.Links;
import SSG.SSGWargame.repository.AccountRepository;
import SSG.SSGWargame.service.dto.AccountValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AccountServiceTest {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void 회원가입(){
        //given
        AccountValue value = makeAccount("tony", "tony2022", "I'm Tony. Hello~", "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com", "temporary");

        //when
        Account account = accountService.join(value);

        //then
        assertEquals(account, accountRepository.findOne(account.getId()).get());
    }


    @Test
    public void 중복회원(){
        //given
        AccountValue value1 = makeAccount("tony", "tony2022", "I'm Tony. Hello~", "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com", "temporary");
        AccountValue value2 = makeAccount("tony", "tony2022_1", "I'm Tony. Hello~1", "PMDC1", "github.com/yj-anthonyjo/1", "instagram.com/1", "yj.anthonyjo@gmail.com/1", "temporary1");

        //when
        accountService.join(value1);

        //then
        IllegalStateException e = assertThrows(
                IllegalStateException.class, () -> accountService.join(value2)
        );
        assertEquals(e.getMessage(), "이미 존재하는 ID입니다.");
    }

    @Test
    public void 로그인(){
        //given

        //when

        //then
    }

    @Test
    public void 전체조회(){
        //given
        AccountValue value1 = makeAccount("tony", "tony2022", "I'm Tony. Hello~", "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com", "temporary");
        AccountValue value2 = makeAccount("tony1", "tony2022", "I'm Tony1. Hello~", "PMDC1", "github.com/yj-anthonyjo1", "instagram.com/1", "yj.anthonyjo1@gmail.com", "temporary1");

        //when
        Account account1 = accountService.join(value1);
        Account account2 = accountService.join(value2);
        List<Account> all = accountService.getAll();

        //then
        assertThat(all)
                .contains(account1)
                .contains(account2);
    }

    @Test
    public void 단건조회_idx(){
        //given
        AccountValue value1 = makeAccount("tony", "tony2022", "I'm Tony. Hello~", "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com", "temporary");
        AccountValue value2 = makeAccount("tony1", "tony2022", "I'm Tony1. Hello~", "PMDC1", "github.com/yj-anthonyjo1", "instagram.com/1", "yj.anthonyjo1@gmail.com", "temporary1");
        Long savedId1 = accountService.join(value1).getId();
        Long savedId2 = accountService.join(value2).getId();

        //when
        Account account = accountService.getOne(savedId1).orElseThrow(IllegalStateException::new);

        //then
        assertThat(account.getNickname()).isEqualTo("PMDC");
    }

    @Test
    public void 단건조회_id(){
        //given
        AccountValue value1 = makeAccount("tony", "tony2022", "I'm Tony. Hello~", "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com", "temporary");
        AccountValue value2 = makeAccount("tony1", "tony2022", "I'm Tony1. Hello~", "PMDC1", "github.com/yj-anthonyjo1", "instagram.com/1", "yj.anthonyjo1@gmail.com", "temporary1");
        Long savedId1 = accountService.join(value1).getId();
        Long savedId2 = accountService.join(value2).getId();

        //when
        Account account = accountService.getOne("tony1").orElseThrow(IllegalStateException::new);

        //then
        assertThat(account.getNickname()).isEqualTo("PMDC1");
    }

    @Test
    public void 업데이트(){
        //given
        AccountValue value = makeAccount("tony", "tony2022", "I'm Tony. Hello~", "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com", "temporary");
//        Account account1 = makeAccount("tony", "tony2022", "I'm Tony. Hello~", "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com", "temporary");
//        Account account2 = makeAccount("tony", "tony2022", "I'm Tony. Hello~", "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com", "temporary");
        Long savedId = accountService.join(value).getId();

        //when
        AccountValue updateValue = new AccountValue();
        updateValue.setIntroduce("I'm anthony");
        System.out.println(value);
        accountService.update(savedId, updateValue);

        //then
        //https://newbedev.com/junit5-how-to-assert-several-properties-of-an-object-with-a-single-assert-call
        Account target = accountService.getOne(savedId).orElseThrow(EntityNotFoundException::new);
//        System.out.println(value);
//        System.out.println(target);
//        assertEquals(target, account1);
//        System.out.println("omg : "+account1.equals(account2));
//        Assertions.assertThat(account1).isEqualTo(account2);
//        Account account2 = makeAccount("tony", "tony2022", "I'm Tony. Hello~",
//        "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com",
//        "temporary");

        //임베디드 타입은 어떻게 검사하나?
        Links links = new Links("github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com");
        assertThat(target)
                .returns("tony", from(Account::getUsername))
                .returns("I'm anthony", from(Account::getIntroduce)) //update된 부분.
                .returns("PMDC", from(Account::getNickname))
                .returns("temporary", from(Account::getProfileImgLink))
                .returns(links, from(Account::getLinks)); //임베디드 타입 검사
        assertThat(passwordEncoder.matches("tony2022", target.getPw())).isTrue();
    }

    @Test
    public void 삭제(){
        //given
        AccountValue value = makeAccount("tony", "tony2022", "I'm Tony. Hello~", "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com", "temporary");
        Long savedId = accountService.join(value).getId();

        //when
        Account result = accountService.getOne(savedId).orElseThrow(IllegalStateException::new);
        assertThat(result.getUsername()).isEqualTo("tony");
        accountService.delete(savedId);

        //then
        //모르겄다.. 알아보자.
        assertThrows(IllegalStateException.class,
                () -> accountService.getOne(savedId).orElseThrow(IllegalStateException::new));

    }

    public AccountValue makeAccount(String id, String pw, String introduce, String nickname, String githubLink, String snsLink, String mailLink, String profileImgLink) {
        AccountValue value = new AccountValue();
        value.setUsername(id);
        value.setPw(pw);
        value.setIntroduce(introduce);
        value.setNickname(nickname);

        value.setLink_github(githubLink);
        value.setLink_sns(snsLink);
        value.setLink_mail(mailLink);
        value.setProfileImgLink(profileImgLink);
        return value;
    }
}
