package kr.co.hjsoft.controller;

import kr.co.hjsoft.dto.MemberDTO;
import kr.co.hjsoft.dto.PageRequestDTO;
import kr.co.hjsoft.entity.Member;
import kr.co.hjsoft.repository.MemberRepository;
import kr.co.hjsoft.service.BoardService;
import kr.co.hjsoft.service.MemberService;
import kr.co.hjsoft.service.MemberServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@AllArgsConstructor
@Log4j2
public class MemberController {
    private MemberServiceImpl memberService;
    private final MemberService memberservice2;
    private final BoardService boardService;

    @GetMapping("/login/signup")
    public String dispSignup(MemberDTO memberdto) {
        return "/login/signup";
    }

    @PostMapping("/login/signup")
    public String execSignup(@Valid MemberDTO memberdto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            // 회원가입 실패시, 입력 데이터를 유지
            model.addAttribute("memberdto", memberdto);

            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "/login/signup";
        }

        memberService.joinUser(memberdto);
        return "/login/login";
    }
    @PostMapping("/signup/emailCheck")
    @ResponseBody
    public int emailCheck(@RequestParam("memberEMAIL") String memberEMAIL){
        log.info("userEmailCheck 진입");
        log.info("전달받은 email:" + memberEMAIL);
        int cnt = memberservice2.emailCheck(memberEMAIL);
        log.info("확인 결과 :" + cnt);
        return cnt;
    }

    @PostMapping("/signup/nicknameCheck")
    @ResponseBody
    public int nicknameCheck(@RequestParam("memberNICKNAME") String memberNICKNAME){
        log.info("userNicknameCheck 진입");
        log.info("전달받은 nickname:" + memberNICKNAME);
        int cnt = memberservice2.nicknameCheck(memberNICKNAME);
        log.info("확인 결과 :" + cnt);
        return cnt;
    }

    @GetMapping("/login/login")
    public String login(@RequestParam(value = "error", required = false)String error,
                        @RequestParam(value = "exception", required = false)String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/login/login";
    }

    // 로그인 결과 페이지
    @GetMapping("/login/login/result")
    public String dispLoginResult(Model model,Principal principal) {
        MemberDTO dto = boardService.getmember(principal.getName());
        model.addAttribute("dto",dto);
        return "/login/loginSuccess";
    }

    // 로그아웃 결과 페이지
    @GetMapping("/login/logout/result")
    public String dispLogout() {
        return "/login/logout";
    }

    // 접근 거부 페이지
    @GetMapping("/login/denied")
    public String dispDenied() {
        return "/login/denied";
    }

    // 내 정보 페이지
    @GetMapping("/login/info")
    public String dispMyInfo() {
        return "/login/myinfo";
    }

    // 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "/login/admin";
    }


    @GetMapping("/login/modify")
    public void dispModify(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Principal principal, Model model){
        MemberDTO memberdto = memberservice2.getmember(principal.getName());
        model.addAttribute("memberdto", memberdto);
    }

    @PostMapping("/login/modify")
    public String modify(MemberDTO memberdto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes rattr){
        memberservice2.modify(memberdto);
        rattr.addAttribute("memberEAMIL", memberdto.getMemberEMAIL());
        return "redirect:/login/myinfo";
    }

    // 회원 탈퇴 페이지
    @GetMapping("/login/delete")
    public String dispDelete() {
        return "/login/delete";
    }

    @PostMapping("/login/delete")
    public String remove(String memberEMAIL, RedirectAttributes rattr){
        memberservice2.delete(memberEMAIL);
        //출력할 메시지 저장
        rattr.addFlashAttribute("msg",memberEMAIL + "탈퇴");
        SecurityContextHolder.clearContext(); //회원 탈퇴 시 로그아웃 처리
        return "redirect:/login/delete";
    }
}
