package kr.co.dajsoft.hell0.service;

import kr.co.dajsoft.hell0.dto.BoardDTO;
import kr.co.dajsoft.hell0.dto.PageRequestDTO;
import kr.co.dajsoft.hell0.dto.PageResultDTO;
import kr.co.dajsoft.hell0.entity.Board;
import kr.co.dajsoft.hell0.entity.Member;

public interface BoardService {

    //게시물 등록을 위한 메서드
    public Long register(BoardDTO dto);

    //목록 보기 요청을 처리할 메서드
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO dto);
    //상세 보기 요청을 처리 할 메서드
    public BoardDTO get(Long board_number);

    //이 메서드를 인터페이스에서 선언하고 클래스에서 구현해도 되고
    //클래스에 private 로 만들어서 사용해도 되는데
    //인터페이스에 만든 이유는 클래스에는 실제 비지니스 로직에 관련된 메서드만
    //존재하게 하고 싶어서.
    //이러한 메서드를 별도의 클래스에 static 메서드로 만들어 두어도
    //되는데 이러한 경우 클래스 이름에는 Wrapper를 붙이는 것이 좋다.

    //Board DTO를 Board Entity로 변환 해주는 메서드
    default Board dtoToEntity(BoardDTO dto){
        Member member = Member.builder()
                .memberNICKNAME(dto.getMemberNICKNAME())
                .build();

        Board board = Board.builder()
                .boardNUMBER((long) dto.getBoardNUMBER())
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
                .boardNUMBER(Math.toIntExact(board.getBoardNUMBER()))
                .boardTITLE(board.getBoardTITLE())
                .boardCONTENT(board.getBoardCONTENT())
                .regDATE(board.getRegDate())
                .modDATE(board.getModDate())
                .memberNICKNAME(member.getMemberNICKNAME())
                .replyCount(replyCount.intValue())
                .boardNICKNAME(board.getBoardNICKNAME())
                .build();
        return dto;
    }

    public void removeWithReplies(Long board_number);

    public void modify(BoardDTO dto);

}
