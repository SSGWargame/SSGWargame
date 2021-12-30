package SSG.SSGWargame.service;

import SSG.SSGWargame.domain.Account.Links;
import SSG.SSGWargame.domain.Account.Score;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;

@Getter
@Setter
public class AccountValue {
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
}
