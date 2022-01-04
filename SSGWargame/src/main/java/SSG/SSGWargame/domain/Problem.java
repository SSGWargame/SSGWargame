package SSG.SSGWargame.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class Problem {

    @Id @GeneratedValue
    @Column(name = "problem_id")
    private Long id;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL) //mappedby있음 -> 연관관계의 slave임.
    private List<Solution> solutions;
}
