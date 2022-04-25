package kr.co.hjsoft.controller;

import kr.co.hjsoft.dto.PageRequestDTO;
import kr.co.hjsoft.service.ApiService;
import kr.co.hjsoft.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class HomeController {
    private final BoardService boardService;
    @GetMapping({"/", "/board/list"})
    public String index(){
        return "/board/list";
    }
    @GetMapping("board/login")
    public String login(){
        return "/login/index";
    }
    @GetMapping("login/myinfo")
    public String myinfo(){
        return "/login/myinfo";
    }
    @GetMapping("board/member")
    public String member(){
        return "/login/myinfo";
    }
    @GetMapping("board/board")
    public String board(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", boardService.getList(pageRequestDTO));
        return "board/board";
    }

    @GetMapping("board/football")
    public String football(){
        return "/board/football";
    }


}
