package SSG.SSGWargame.Controller;

import SSG.SSGWargame.domain.Answer;
import SSG.SSGWargame.domain.dto.AnswerRequest;
import SSG.SSGWargame.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // json 형태로 결과값 반환 ResponseBody 사용할 필요 없음
@RequiredArgsConstructor // final 객체 constructor injection 해준다. Autowired 역할
@RequestMapping(value = "/api/v1.0/Answer")
public class AnswerController {
    private final AnswerService answerService;

    @GetMapping("/")
    public ResponseEntity findAnswer(@RequestParam(required = false) Long id, @RequestParam Long QnAID){
        if(id == null){
            return ResponseEntity.ok(answerService.findAnswerByQnAId(QnAID));
        }
        else{
            return ResponseEntity.ok(answerService.findAnswer(id));
        }
    }

    @PostMapping("/")
    public ResponseEntity<Answer> saveAnswer(@RequestBody AnswerRequest request){
        return new ResponseEntity<Answer>(answerService.savaAnswer(request), HttpStatus.OK);
    }

    @DeleteMapping("/")
    public void deleteAnswer(@RequestParam Long id){
        answerService.deleteAnswer(id);
    }

    @DeleteMapping("/qna")
    public void deleteAllAnswerByQnAID(@RequestParam Long QnA_ID){
        answerService.deleteAllAnswerByQnaID(QnA_ID);
    }

    @PutMapping("/")
    public ResponseEntity<Answer> updateAnswer(@RequestParam Long id, @RequestBody AnswerRequest request){
        return ResponseEntity.ok(answerService.updateAnswer(id,request));
    }
}
