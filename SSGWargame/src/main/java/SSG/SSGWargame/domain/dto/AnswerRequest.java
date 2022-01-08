package SSG.SSGWargame.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerRequest {
    private String title;
    private String content;
    private int upvote;
    private LocalDateTime writeTime;
    private Long qnaid;
    private Long accountIdx;
}
