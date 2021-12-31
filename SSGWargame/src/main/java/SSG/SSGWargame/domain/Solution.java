package SSG.SSGWargame.domain;

import SSG.SSGWargame.domain.Account.Account;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@ToString
public class Solution {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solution_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //mappedby없음, 연관관계 주인
    @JoinColumn(name = "problems_id") //Solution table에서 Problem으로의 FK지정.
    private Problems problems;

    @OneToOne(fetch = FetchType.LAZY) //mappedby없음, 연관관계 주인
    @JoinColumn(name = "account_id") //bidirectional onetoone -> join table사용?
    private Account account;

    private String title;

    private String content;

    private LocalDateTime time; //시,분,초
}
