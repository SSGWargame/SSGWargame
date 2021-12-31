package SSG.SSGWargame.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problems {

    @Column(name = "problems_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String title; // public 사용해서 다른곳에서 접근

   // @Column(columnDefinition = "varchar(120)")
   // private String title; // private

    @Column(columnDefinition = "varchar(255)")
    private String flag;

    @Column(columnDefinition = "int")
    private int fields;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "int")
    private int score;

    //@Column(columnDefinition = "int")
    //private int ProblemMakerIndex;

    @Column(columnDefinition = "varchar(200)")
    private String filelink;

    @Column(columnDefinition = "varchar(200)")
    private String filename;

    /* 2022.1.13 test 위해 account restapi 개발 전까지는 주석처리해둠
    @ManyToOne // ProblemMakerIndex
    @JoinColumn(name="account_id")
    private Account account;
     */

    public Problems(String title, String flag, int fields, String description, int score, String filelink, String filename){
        this.title=title;
        this.flag=flag;
        this.fields=fields;
        this.description=description;
        this.score=score;
        this.filelink=filelink;
        this.filename=filename;

    }

}
