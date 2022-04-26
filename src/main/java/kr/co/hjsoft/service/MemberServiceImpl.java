package kr.co.hjsoft.service;

import kr.co.hjsoft.dto.MemberDTO;
import kr.co.hjsoft.entity.Member;
import kr.co.hjsoft.repository.BoardRepository;
import kr.co.hjsoft.repository.MemberRepository;
import kr.co.hjsoft.repository.ReplyRepository;
import kr.co.hjsoft.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.*;

@Service
@RequiredArgsConstructor

public class MemberServiceImpl implements UserDetailsService, MemberService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    //회원가입 시, 유효성 체크
    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        //유효성 검사에 실패한 필드 목록을 받음
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    @Transactional
    public String joinUser(MemberDTO memberdto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberdto.setMemberPW(passwordEncoder.encode(memberdto.getMemberPW()));
        return memberRepository.save(memberdto.toEntity()).getMemberNICKNAME();
    }


    @Override
    public UserDetails loadUserByUsername(String memberEMAIL) throws UsernameNotFoundException {
        Optional<Member> memberWrapper = memberRepository.findBymemberEMAIL(memberEMAIL);
        System.out.println(memberWrapper.toString());
        Member member = memberWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (("admin@example.com").equals(memberEMAIL)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }
        return new User(member.getMemberEMAIL(), member.getMemberPW(), authorities);
    }

    @Transactional
    @Override
    public void modify(MemberDTO memberdto) {
        System.out.println(memberdto);
        Optional<Member> member = memberRepository.findBymemberEMAIL(memberdto.getMemberEMAIL());
        if (member.isPresent()) {
            member.get().changeName(memberdto.getMemberNAME());
            member.get().changePw(memberdto.getMemberPW());
            member.get().changeGender(memberdto.getMemberGENDER());
            member.get().changePhone(memberdto.getMemberPHONE());
            member.get().changeAddress(memberdto.getMemberADDRESS());

            memberRepository.save(member.get());
        }
    }

    @Transactional
    @Override
    public void delete(String memberEMAIL,String memberNICKNAME) {
        replyRepository.deleteReplyByReplywriter(memberNICKNAME);
        boardRepository.deleteBoardByWriter(memberNICKNAME);
        memberRepository.deleteBymemberEMAIL(memberEMAIL);
    }

    @Override
    public MemberDTO getmember(String memberEMAIL) {
        Object member = memberRepository.getMemberNickname(memberEMAIL);

        return entityToDTO((Member) member);
    }

    @Override
    public int emailCheck(String memberEMAIL) {
        int cnt = memberRepository.emailCheck(memberEMAIL);

        return cnt;
    }

    @Override
    public int nicknameCheck(String memberNICKNAME) {
        int cnt = memberRepository.nicknameCheck(memberNICKNAME);
        return cnt;
    }
}



