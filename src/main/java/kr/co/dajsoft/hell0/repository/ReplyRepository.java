package kr.co.dajsoft.hell0.repository;

import kr.co.dajsoft.hell0.entity.Board;
import kr.co.dajsoft.hell0.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    //글번호를 가지고 댓글을 삭제하는 메서드
    @Modifying
    @Query("delete from Reply r where r.board.boardNUMBER = :boardNUMBER")
    public void deleteByBoardNUMBER(Long boardNUMBER);

    //게시글에 해당하는 모든 댓글을 가져오는 메서드
    public List<Reply> getRepliesByBoardOrderByReplyNUMBER(Board board);

}
