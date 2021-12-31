package SSG.SSGWargame.service.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class SolutionValue {
    private Long id;

    private Long problemId;

    private String accountId;

    private String title;

    private String content;

    private LocalDateTime time; //시,분,초
}