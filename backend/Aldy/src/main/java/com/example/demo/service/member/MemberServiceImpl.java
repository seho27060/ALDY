package com.example.demo.service.member;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.member.request.*;
import com.example.demo.domain.dto.member.response.CodeReviewNumberResponseDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.dto.solvedac.response.SolvedacMemberResponseDto;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.code.EditedCodeRepository;
import com.example.demo.repository.member.MemberRepository;

import com.example.demo.service.solvedac.SolvedacService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final EditedCodeRepository editedCodeRepository;

    private final SolvedacService solvedacService;
    private final AuthService authService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public MemberResponseDto withdrawal(MemberPasswordRequestDto memberPasswordRequestDto, HttpServletRequest request) {
        String loginMember = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        Member member = memberRepository.findByBaekjoonId(loginMember).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        MemberResponseDto response;

        if(bCryptPasswordEncoder.matches( memberPasswordRequestDto.getPassword(),member.getPassword())){
            memberRepository.delete(member);
            response = new MemberResponseDto(member);
        } else {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }

        return response;
    }

    @Override
    public MemberResponseDto findMember(HttpServletRequest request) {
        String baekjoonId = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        Member member = memberRepository.findByBaekjoonId(baekjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return new MemberResponseDto(member);
    }

    @Override
    public MemberResponseDto modifyNickname(MemberModifyNicknameRequestDto memberModifyNicknameRequestDto, HttpServletRequest request) {
        String loginMemberBaekjoonId = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        Member member = memberRepository.findByBaekjoonId(loginMemberBaekjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        member.modifyNickname(memberModifyNicknameRequestDto);
        return new MemberResponseDto(member);
    }

    @Override
    public MemberResponseDto modifyEmail(MemberModifyEmailRequestDto memberModifyEmailRequestDto, HttpServletRequest request) {
        String loginMemberBaekjoonId = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        Member member = memberRepository.findByBaekjoonId(loginMemberBaekjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        member.modifyEmail(memberModifyEmailRequestDto);
        return new MemberResponseDto(member);
    }

    @Override
    public MemberResponseDto modifyPassword(MemberPasswordRequestDto memberPasswordRequestDto, HttpServletRequest request) {
        String loginMemberBaekjoonId = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        Member member = memberRepository.findByBaekjoonId(loginMemberBaekjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        String rawPassword = memberPasswordRequestDto.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
        member.modifyPassword(encodedPassword);
        return new MemberResponseDto(member);
    }

    @Override
    public CodeReviewNumberResponseDto findCodeReviewNumberRelatedMember(HttpServletRequest request) {
// 리뷰 한거 -> 받는 사람이 나이고, done
// 리뷰 받은거 -> 보내는 사람이 나이고, done
        String backjoonId = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        Member member = memberRepository.findByBaekjoonId(backjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        long member_id = member.getId();

        Long answerReviewNumber = Optional.ofNullable(editedCodeRepository.countByReceiver_id(member_id))
                .orElse(0L);
        Long replyCodeReviewNumber = Optional.ofNullable(editedCodeRepository.countBySender_id(member_id))
                .orElse(0L);

        CodeReviewNumberResponseDto codeReviewNumberResponseDto = new CodeReviewNumberResponseDto(answerReviewNumber,replyCodeReviewNumber);
        return codeReviewNumberResponseDto;
    }

    @Override
    public MemberResponseDto renewTier(HttpServletRequest request) {
        String baekjoonId = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        SolvedacMemberResponseDto solvedacMemberResponseDto = solvedacService.solvedacMemberFindAPI(baekjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Member loginMember = memberRepository.findByBaekjoonId(baekjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        loginMember.renewTier(solvedacMemberResponseDto.getTier());

        MemberResponseDto memberResponseDto = new MemberResponseDto(loginMember);

        return memberResponseDto;
    }


}
