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
public class Notice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL) //연관관계 주인, mappedBy없음.
    //여기서 연관관계 로딩을 지연로딩으로 설정해놓으면, 에러난다.
    //LAZY 상태로 controller에서 get -> 준영속 상태의 account entity object를 가져오게 되는 것이다.
    //lazy이니까, 해당 엔티티를 실제로 코드 상에서 사용하기 전까지 이를 가져오지 않는다. -> 에러난다.
    //-> lazy한 상태로 이를 해결하려면, get등으로 객체를 사용해주어야한다. -> 안되네?(controller에서..) : 추후에 알아보자.
    @JoinColumn(name = "account_id")
    /*
    JoinColumn이 ManyToOne에 있음, 이것이 주인임 => 여기에 FK있음.
    ( 뭐 양방향이 아니기에 이것이 연관관계 주인인 것은 확실하나(?맞나?), 양방향이어도 여기에 FK가 있다는 것은 맞지 않을까? )
    If the join is for a OneToOne or ManyToOne mapping using a foreign key mapping strategy, the foreign key column is in the table of the source entity or embeddable.
    ^ : JoinColumn.java의 내용.
     */
    private Account account;

    private String title;

    private String content;

    private LocalDateTime time;
}
