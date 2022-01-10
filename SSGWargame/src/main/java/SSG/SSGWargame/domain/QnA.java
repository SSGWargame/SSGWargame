package SSG.SSGWargame.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class QnA {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    private Long id;

    @Column(columnDefinition = "varchar(120)")
    private String title;

    //@Column(columnDefinition = "int")
    //private String writerId;

    @Column(columnDefinition = "text")
    private String content;

    @Column(columnDefinition = "int")
    private int answerNumber;

    @Column(columnDefinition = "datetime")
    private LocalDateTime writeTime;

    @Column(columnDefinition = "int")
    private int upvote;

    @Column(columnDefinition = "int")
    private int viewCount;

    //@Column //참조 위한 것.
    //private Long targetProblem;

    @ManyToOne
    @JoinColumn(name="problems_id")
    private Problems problems;

    /* 나중에 account 만들어 진 후에 추가
    @ManyToOne
    @JoinColumn(name="Account_id")
    private Account account;
    */

}
