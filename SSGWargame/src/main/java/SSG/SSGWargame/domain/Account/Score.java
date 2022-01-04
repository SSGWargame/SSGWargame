package SSG.SSGWargame.domain.Account;

import lombok.Getter;

import javax.persistence.*;

@Embeddable
@Getter
public class Score {
    protected Score(){}

    public Score(Integer pwnable, Integer webhacking, Integer reversing, Integer misc, Integer etc) {
        this.pwnable = pwnable;
        this.webhacking = webhacking;
        this.reversing = reversing;
        this.misc = misc;
        this.etc = etc;
        this.total = pwnable + webhacking + reversing + misc + etc;
    }
    private Integer pwnable;
    private Integer webhacking;
    private Integer reversing;
    private Integer misc;
    private Integer etc;
    private Integer total;
}
