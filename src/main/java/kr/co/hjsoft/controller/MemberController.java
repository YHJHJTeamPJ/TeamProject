package kr.co.hjsoft.controller;

import kr.co.hjsoft.dto.MemberDTO;
import kr.co.hjsoft.dto.PageRequestDTO;
import kr.co.hjsoft.repository.MemberRepository;
import kr.co.hjsoft.service.MemberService;
import kr.co.hjsoft.service.MemberServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@Controller
@AllArgsConstructor
//@RequiredArgsConstructor
public class MemberController {
    private MemberServiceImpl memberService;
    private final MemberService memberservice2;
    private MemberRepository memberRepository;

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
    public String dispLoginResult() {
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
        return "redirect:/login/modify";
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
        return "redirect:/login/delete";
    }
}
