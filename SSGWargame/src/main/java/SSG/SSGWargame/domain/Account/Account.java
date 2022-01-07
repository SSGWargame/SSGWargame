package SSG.SSGWargame.domain.Account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
@EqualsAndHashCode// equals와 hashcode메소드를 오버라이드하여 각각 논리적 동등함, 물리적 동등함을 확인할 수 있게 함.
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long idx;

    private String id;

    private String pw;

    private String introduce;

    private String nickname;

    @Embedded
    private Links links;

    private String ProfileImgLink;


    /** domainFields
     * Fields
     *- 1 : Pwnable
     * - 2 : Webhacking
     * - 3 : Reversing
     * - 4 : Misc
     * - 5 : ETC
     * ex> String : 100001 -> Pwnable, ETC에 관심있다.
     */
    private String domainFields;

    @Embedded
    private Score score;

    /** role
     * USER
     * ADMIN
     */
    private String role;
}
