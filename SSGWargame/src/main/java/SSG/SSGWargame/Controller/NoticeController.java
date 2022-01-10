package SSG.SSGWargame.Controller;

import SSG.SSGWargame.domain.Notice;
import SSG.SSGWargame.service.NoticeService;
import SSG.SSGWargame.service.dto.NoticeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1.0/Notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    //get all notices
    @GetMapping("/")
    public List<Notice> readNotices() {
        return noticeService.getAll();
    }

    //get one notice by id
    @GetMapping("/{noticeId}")
    public Notice readNotice(@PathVariable Long noticeId) {
        Notice notice = noticeService.getById(noticeId);
//        System.out.println(notice.getAccount().getIdx());
        return notice;
    }

    //add new notice
    @PostMapping("/")
    public Notice newNotice(@RequestBody NoticeValue value) {
        return noticeService.add(value);
    }

    //update notice by id
    @PutMapping("/{noticeId}")
    public void update(@PathVariable Long noticeId,@RequestBody NoticeValue value) {
        noticeService.update(noticeId, value);
    }

    //delete notice by id
    @DeleteMapping("/{noticeId}")
    public void delete(@PathVariable Long noticeId) {
        noticeService.delete(noticeId);
    }
}
