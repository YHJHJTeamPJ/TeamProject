package kr.co.hjsoft.service;

import kr.co.hjsoft.dto.BoardDTO;
import kr.co.hjsoft.dto.MemberDTO;
import kr.co.hjsoft.dto.PageRequestDTO;
import kr.co.hjsoft.dto.PageResultDTO;
import kr.co.hjsoft.entity.Board;
import kr.co.hjsoft.entity.Member;

public interface BoardService {

    //게시물 등록을 위한 메서드
     Long register(BoardDTO dto);

    //목록 보기 요청을 처리할 메서드
     PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO dto);
    //상세 보기 요청을 처리 할 메서드
    BoardDTO get(Long boardNUMBER);
    MemberDTO getmember(String memberEMAIL);
    //이 메서드를 인터페이스에서 선언하고 클래스에서 구현해도 되고
    //클래스에 private 로 만들어서 사용해도 되는데
    //인터페이스에 만든 이유는 클래스에는 실제 비지니스 로직에 관련된 메서드만
    //존재하게 하고 싶어서.
    //이러한 메서드를 별도의 클래스에 static 메서드로 만들어 두어도
    //되는데 이러한 경우 클래스 이름에는 Wrapper를 붙이는 것이 좋다.

    //Board DTO를 Board Entity로 변환 해주는 메서드
    default Board dtoToEntity(BoardDTO dto){
        Member member = Member.builder()
                .memberNICKNAME(dto.getBoardNICKNAME())
                .build();

        Board board = Board.builder()
                .boardNUMBER(dto.getBoardNUMBER())
                .boardTITLE(dto.getBoardTITLE())
                .boardCONTENT(dto.getBoardCONTENT())
                .boardNICKNAME(dto.getBoardNICKNAME())
                .writer(member)
                .build();
        return board;
    }
    default BoardDTO entityToDTO(Board board,
                                 Member member,
                                 Long replyCount){
        BoardDTO dto = BoardDTO .builder()
                .boardNUMBER(board.getBoardNUMBER())
                .boardTITLE(board.getBoardTITLE())
                .boardCONTENT(board.getBoardCONTENT())
                .regDATE(board.getRegDate())
                .modDATE(board.getModDate())
                .boardNICKNAME(member.getMemberNICKNAME())
                .build();
        return dto;
    }
        //Member Entity를 ReplyDTO로 변환해주는 메서드
    default MemberDTO memberentityToDTO(Member member){
        MemberDTO memberdto = MemberDTO.builder()
                .memberADDRESS(member.getMemberADDRESS())
                .memberEMAIL(member.getMemberEMAIL())
                .memberGENDER(member.getMemberGENDER())
                .memberNAME(member.getMemberNAME())
                .memberNICKNAME(member.getMemberNICKNAME())
                .memberPHONE(member.getMemberPHONE())
                .memberPW(member.getMemberPW())
                .build();
        return memberdto;
    }
     void removeWithReplies(Long boardNUMBER);

     void modify(BoardDTO dto);

}
