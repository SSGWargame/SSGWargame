package SSG.SSGWargame.service;

import SSG.SSGWargame.domain.Account.Account;
import SSG.SSGWargame.domain.Notice;
import SSG.SSGWargame.service.dto.NoticeValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NoticeServiceTest {
    @Autowired
    private NoticeService noticeService;
    private AccountServiceTest accountServiceTest = new AccountServiceTest();
    @Autowired
    private AccountService accountService;
    //^ : makeAccount를 위함.

    @Test
    public void 공지작성(){
        //given
        Account account = accountServiceTest.makeAccount("tony", "tony2022", "I'm tony", "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com", "temp");
        accountService.join(account);
        NoticeValue value = makeNotice(account.getId(),"Title : Notice Test", "Content : Test1");

        //when
        Long savedId = noticeService.add(value).getId();

        //then
        assertThat(noticeService.getById(savedId))
                .returns(value.getContent(), from(Notice::getContent))
                .returns(value.getTitle(), from(Notice::getTitle))
                .returns(account, from(Notice::getAccount));
    }

    @Test
    public void 공지전체조회(){
        //given
        Account account1 = accountServiceTest.makeAccount("tony", "tony2022", "I'm tony", "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com", "temp");
        Account account2 = accountServiceTest.makeAccount("anthony", "anthony2022", "I'm tony", "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com", "temp");
        accountService.join(account1);
        accountService.join(account2);

        NoticeValue value1 = makeNotice(account1.getId(),"Title : Notice Test1", "Content : Test1");
        NoticeValue value2 = makeNotice(account1.getId(),"Title : Notice Test2", "Content : Test2");

        //when
        Long notice1 = noticeService.add(value1).getId();
        Long notice2 = noticeService.add(value2).getId();
        List<Notice> all = noticeService.getAll();

        //then
        System.out.println(all);

//        assertThat(all).contains(notice1);
//        assertThat(all).contains(notice2);
    }

    @Test
    public void 공지조회(){
        //given
        Account account1 = accountServiceTest.makeAccount("tony", "tony2022", "I'm tony", "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com", "temp");
        Account account2 = accountServiceTest.makeAccount("anthony", "anthony2022", "I'm tony", "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com", "temp");
        accountService.join(account1);
        accountService.join(account2);

        NoticeValue value1 = makeNotice(account1.getId(),"Title : Notice Test1", "Content : Test1");
        NoticeValue value2 = makeNotice(account2.getId(),"Title : Notice Test2", "Content : Test2");

        //when
        Notice notice1 = noticeService.add(value1);
        Notice notice2 = noticeService.add(value2);

        //then
        assertThat(noticeService.getById(notice1.getId()))
                .returns(value1.getContent(), from(Notice::getContent))
                .returns(value1.getTitle(), from(Notice::getTitle))
                .returns(account1, from(Notice::getAccount));
        assertThat(noticeService.getById(notice2.getId()))
                .returns(value2.getContent(), from(Notice::getContent))
                .returns(value2.getTitle(), from(Notice::getTitle))
                .returns(account2, from(Notice::getAccount));
    }


    @Test
    public void 공지수정(){
        //given
        Account account1 = accountServiceTest.makeAccount("tony", "tony2022", "I'm tony", "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com", "temp");
        accountService.join(account1);

        NoticeValue value1 = makeNotice(account1.getId(),"Title : Notice Test1", "Content : Test1");
        Notice notice = noticeService.add(value1);

        //when
        NoticeValue modify = new NoticeValue();
        modify.setContent("Content : Test1, Updated");
        noticeService.update(notice.getId(), modify);

        //then
        Notice target = noticeService.getById(notice.getId());
        System.out.println(target);
        assertThat(target)
                .returns("Title : Notice Test1", from(Notice::getTitle))
                .returns("Content : Test1, Updated", from(Notice::getContent))
                .returns(account1, from(Notice::getAccount));
    }

    @Test
    public void 공지삭제(){
        //given
        Account account1 = accountServiceTest.makeAccount("tony", "tony2022", "I'm tony", "PMDC", "github.com/yj-anthonyjo", "instagram.com", "yj.anthonyjo@gmail.com", "temp");
        accountService.join(account1);

        NoticeValue value1 = makeNotice(account1.getId(),"Title : Notice Test1", "Content : Test1");
        Notice notice = noticeService.add(value1);
        Long savedId = notice.getId();

        //when
        noticeService.delete(savedId);

        //then
        assertThrows(IllegalStateException.class,
                () -> noticeService.getById(savedId));
    }

    private NoticeValue makeNotice(String id, String title, String content) {
        NoticeValue value = new NoticeValue();
        value.setAccountId(id);
        value.setTitle(title);
        value.setContent(content);
        return value;
    }
}
