package SSG.SSGWargame.domain;

import SSG.SSGWargame.domain.Account.Account;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Notice {

    @Id
    @GeneratedValue
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //연관관계 주인, mappedBy없음.
    @JoinColumn(name = "account_id")
    /*
    JoinColumn이 ManyToOne에 있음, 이것이 주인임 => 여기에 FK있음. ( 뭐 양방향이 아니기에 이것이 연관관계 주인인 것은 확실하나(?맞나?), 양방향이어도 여기에 FK가 있다는 것은 맞지 않을까? )
    If the join is for a OneToOne or ManyToOne mapping using a foreign key mapping strategy, the foreign key column is in the table of the source entity or embeddable.
    ^ : JoinColumn.java의 내용.
     */
    private Account account;

    private String title;

    private String content;

    private LocalDateTime time;
}
