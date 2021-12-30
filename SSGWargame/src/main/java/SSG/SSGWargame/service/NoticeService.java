package SSG.SSGWargame.service;

import SSG.SSGWargame.domain.Notice;
import SSG.SSGWargame.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    //findOne : id로 조회. /notice/{id} : get
    public Notice getById(Long id) {
        return noticeRepository.findOne(id).orElseThrow(IllegalStateException::new);
    }

    //findAll : list로 조회. /notice : get
    public List<Notice> getAll(){
        return noticeRepository.findAll();
    }

    //add /notice : post
    public Long add(Notice notice) {
        noticeRepository.save(notice);
        return notice.getId();
    }

    //update /notice/{id} : put
    public void update(Long id, Notice value){
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
