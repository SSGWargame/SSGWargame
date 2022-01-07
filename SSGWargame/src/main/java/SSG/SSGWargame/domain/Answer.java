package SSG.SSGWargame.domain;

import SSG.SSGWargame.domain.Account.Account;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Answer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 맨 밑에 qna_id로 대체
    @Column
    private Long targetQuestion;

     */

    @Column(columnDefinition = "varchar(120)")
    private String title;

    @Column(columnDefinition = "text")
    private String content;

    @Column(columnDefinition = "int")
    private int upvote;

    @Column(columnDefinition = "datetime")
    private LocalDateTime writeTime;

    @ManyToOne
    @JoinColumn(name="qnaid")
    private QnA qna;


    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

}
