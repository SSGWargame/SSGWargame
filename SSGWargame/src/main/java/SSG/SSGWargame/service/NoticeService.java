package SSG.SSGWargame.service;

import SSG.SSGWargame.domain.Account.Account;
import SSG.SSGWargame.domain.Notice;
import SSG.SSGWargame.repository.AccountRepository;
import SSG.SSGWargame.repository.NoticeRepository;
import SSG.SSGWargame.service.dto.NoticeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private AccountRepository accountRepository;

    //findOne : id로 조회. /notice/{id} : get
    public Notice getById(Long id) {
        return noticeRepository.findOne(id).orElseThrow(IllegalStateException::new);
    }

    //findAll : list로 조회. /notice : get
    public List<Notice> getAll(){
        return noticeRepository.findAll();
    }

    //add /notice : post
    public Notice add(NoticeValue value) {
        Notice notice = new Notice();

        Account targetAccount = accountRepository.findById(value.getAccountId()).orElseThrow(IllegalStateException::new);
        notice.setAccount(targetAccount);

        notice.setTitle(value.getTitle());
        notice.setContent(value.getContent());
        notice.setTime(LocalDateTime.now());

        noticeRepository.save(notice);
        return notice;
    }

    //update /notice/{id} : put
    public void update(Long id, NoticeValue value){
        Notice notice = noticeRepository.findOne(id).orElseThrow(IllegalAccessError::new);
        if (!StringUtils.isEmpty(value.getContent()))
            notice.setContent(value.getContent());
        if (!StringUtils.isEmpty(value.getTitle()))
            notice.setTitle(value.getTitle());
    }

    //delete /notice/{id} : delete
    public void delete(Long id) {
        noticeRepository.deleteById(id);
    }
}
