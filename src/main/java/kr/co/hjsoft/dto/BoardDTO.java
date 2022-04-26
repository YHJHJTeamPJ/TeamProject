package kr.co.hjsoft.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDTO {

 private Long boardNUMBER;
 private String boardTITLE;
 private String  boardCONTENT;
 private String boardNICKNAME;

 //작성된 날짜와 수정된 날짜
 private LocalDateTime regDATE;
 private LocalDateTime modDATE;

 private int replyCount;


}
