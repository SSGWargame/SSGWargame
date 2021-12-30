package SSG.SSGWargame.service;

import SSG.SSGWargame.domain.Account.Links;
import SSG.SSGWargame.domain.Account.Score;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;

@Getter
@Setter
public class AccountValue { //이런게 DTO인가?
    private String id;

    private String pw;

    private String introduce;

    private String nickname;

    private String link_github;
    private String link_sns;
    private String link_mail;

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

    private Integer pwnable;
    private Integer webhacking;
    private Integer reversing;
    private Integer misc;
    private Integer etc;
    private Integer total;
}
