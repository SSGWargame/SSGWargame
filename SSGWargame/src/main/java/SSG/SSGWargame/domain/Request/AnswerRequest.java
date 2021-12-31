package SSG.SSGWargame.domain.Request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerRequest {
    private String title;
    private String content;
    private int upvote;
    private LocalDateTime writeTime;
    private Long qnaid;
}
