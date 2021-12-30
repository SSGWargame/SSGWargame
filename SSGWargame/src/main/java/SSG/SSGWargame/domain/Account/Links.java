package SSG.SSGWargame.domain.Account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@ToString
@EqualsAndHashCode
public class Links {
    protected Links(){} //굳이 이렇게 해야하는 이유가 있나? setter를 없애는 이유.. -> 리플렉션이 이유라고 하는데..
    //변경이 불가능하면 불편하지 않나?
    public Links(String linkGithub, String linkSns, String linkMail){
        this.lintGithub = linkGithub;
        this.linkSns = linkSns;
        this.linkMail = linkMail;
    }
    private String lintGithub;

    private String linkSns;

    private String linkMail;

}
