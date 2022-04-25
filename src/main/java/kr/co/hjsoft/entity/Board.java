package kr.co.hjsoft.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "writer")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardNUMBER;

    @Column(length = 100, nullable = false)
    private String boardTITLE;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String boardCONTENT;

    @Column(length = 100)
    private String boardNICKNAME;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    //title을 수정하는 메서드
    public void changeTitle(String title){
        this.boardTITLE = title;
    }

    //content를 수정하는 메서드
    public void changeContent(String content){
        this.boardCONTENT = content;
    }

}
