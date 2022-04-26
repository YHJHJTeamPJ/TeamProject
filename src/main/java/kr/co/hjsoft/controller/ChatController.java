package kr.co.hjsoft.controller;

import kr.co.hjsoft.dto.MemberDTO;
import kr.co.hjsoft.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
@Controller
@AllArgsConstructor
@Log4j2
public class ChatController {

    private final BoardService boardService;

    // 로그인 결과 페이지
    @GetMapping("/chat/chatting")
    public String chatResult(Model model, Principal principal) {
        MemberDTO dto = boardService.getmember(principal.getName());
        model.addAttribute("dto",dto);
        return "/chat/chatting";
    }
}
