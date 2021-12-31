package SSG.SSGWargame.Controller;

import SSG.SSGWargame.domain.QnA;
import SSG.SSGWargame.domain.Request.QnASaveRequest;
import SSG.SSGWargame.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // json 형태로 결과값 반환 ResponseBody 사용할 필요 없음
@RequiredArgsConstructor // final 객체 constructor injection 해준다. Autowired 역할
@RequestMapping("/v1")
public class QnAController {
    private final QnaService qnaService;

    /**
     * 질문 조회
     * @param id
     * @return
     */
    @GetMapping("qna")
    public ResponseEntity findQnA(@RequestParam(required = false) Long id ){
        if(id == null){
            return ResponseEntity.ok(qnaService.findQnA());
        }
        else{
            return ResponseEntity.ok(qnaService.findQnA(id));
        }
    }

    /**
     * 질문 등록
     * @param request
     * @return
     */
    @PostMapping("qna")
    public ResponseEntity<QnA> saveQnA(@RequestBody QnASaveRequest request){
        return new ResponseEntity<QnA>(qnaService.saveQnA(request), HttpStatus.OK);
    }

    /**
     * 질문 삭제
     * @param id
     * @return
     */
    @DeleteMapping("qna")
    public ResponseEntity deleteQnA(@RequestParam(required = true) Long id){
        qnaService.deleteQnA(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 질문 수정
     * @param id
     * @param request
     * @return
     */
    @PutMapping("qna")
    public ResponseEntity<QnA> updateQnA(@RequestParam Long id, @RequestBody QnASaveRequest request){
        return ResponseEntity.ok(qnaService.updateQnA(id, request));
    }
}
