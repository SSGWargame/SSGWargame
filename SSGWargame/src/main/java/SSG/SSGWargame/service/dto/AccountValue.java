package SSG.SSGWargame.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.OptionalInt;

@Getter
@Setter
public class AccountValue { //이런게 DTO인가?
    private String username;

    private String pw;

    private String introduce;

    private String nickname;

    private String link_github;
    private String link_sns;
    private String link_mail;

    private String profileImgLink;


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

    private OptionalInt pwnable = OptionalInt.empty();
    private OptionalInt webhacking = OptionalInt.empty();
    private OptionalInt reversing = OptionalInt.empty();
    private OptionalInt misc = OptionalInt.empty();
    private OptionalInt etc = OptionalInt.empty();
    private OptionalInt total = OptionalInt.empty();
}
