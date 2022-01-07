package SSG.SSGWargame.service;

import SSG.SSGWargame.domain.Account.Account;
import SSG.SSGWargame.domain.Answer;
import SSG.SSGWargame.domain.QnA;
import SSG.SSGWargame.domain.dto.AnswerRequest;
import SSG.SSGWargame.repository.AccountRepository;
import SSG.SSGWargame.repository.AnswerRepository;
import SSG.SSGWargame.repository.QnARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QnARepository qnaRepository;
    private final AccountRepository accountRepository;

    public Answer findAnswer(Long id){ // id로 답변 조회
        Optional<Answer> answer = answerRepository.findById(id);
        if(answer.isPresent()){
            return answer.get();
        }
        throw new EntityNotFoundException(
                "Cant find any answer under given ID");
    }

    //QnA_Id로 답변 조회해서 해당 질문글 답변 모두 조회 아마도?
    public List<Answer> findAnswerByQnAId(Long QnA_Id){ // id로 답변 조회
        Optional<QnA> qna= qnaRepository.findById(QnA_Id);
        QnA q = qna.get();
        return(answerRepository.findByQna(q));
    }

    //
    public Answer savaAnswer(AnswerRequest request){
        Optional<QnA> qna= qnaRepository.findById(request.getQnaid());
        if(!qna.isPresent()){
            throw new EntityNotFoundException(
                    "QnA Not Found"
            );
        }
        Answer answerToSave = new Answer();
        BeanUtils.copyProperties(request,answerToSave);
        answerToSave.setQna(qna.get());
        return answerRepository.save(answerToSave);
    }

    public void deleteAnswer(Long id){
        answerRepository.deleteById(id);
    }
    public void deleteAllAnswerByQnaID(Long QnA_ID){
        Optional<QnA> qna= qnaRepository.findById(QnA_ID);
        QnA q= qna.get();
        answerRepository.deleteByQna(q);
    }

    public Answer updateAnswer(Long id,AnswerRequest request){
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        Answer a= optionalAnswer.get();
        Optional<QnA> qna = qnaRepository.findById(request.getQnaid());
        QnA q = qna.get();
        Optional<Account> account = accountRepository.findOne(request.getAccountIdx());
        Account account1 = account.get();
        if (!qna.isPresent()) {
            throw new EntityNotFoundException(
                    "qna Not Found");
        }
        a.setTitle(request.getTitle());
        a.setContent(request.getContent());
        a.setWriteTime(request.getWriteTime());
        a.setUpvote(request.getUpvote());
        a.setQna(q);
        a.setAccount(account1); // account 개발 후 추가
        return(answerRepository.save(a));
    }

}
