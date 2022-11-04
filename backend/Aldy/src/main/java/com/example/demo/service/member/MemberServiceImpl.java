package com.example.demo.service.member;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.member.request.*;
import com.example.demo.domain.dto.member.response.CodeReviewNumberResponseDto;
import com.example.demo.domain.dto.member.response.MemberResponseDto;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.EditedCodeRepository;
import com.example.demo.repository.Member.MemberRepository;

import com.example.demo.repository.RequestedCodeRepository;
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
    private final RequestedCodeRepository requestedCodeRepository;

    private final EditedCodeRepository editedCodeRepository;

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
    public MemberResponseDto modifyInfo(MemberModifyRequestDto memberModifyRequestDto, HttpServletRequest request) {
        String loginMemberBaekjoonId = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        Member member = memberRepository.findByBaekjoonId(loginMemberBaekjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        member.modifyInfo(memberModifyRequestDto);
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
        Member member = memberRepository.findByBaekjoonId(backjoonId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        long member_id = member.getId();

        Long answerReviewNumber = Optional.ofNullable(editedCodeRepository.countByReceiver_id(member_id)).orElse(0L);
        Long replyCodeReviewNumber = Optional.ofNullable(editedCodeRepository.countBySender_id(member_id)).orElse(0L);

        CodeReviewNumberResponseDto codeReviewNumberResponseDto = new CodeReviewNumberResponseDto(answerReviewNumber,replyCodeReviewNumber);
        return codeReviewNumberResponseDto;
    }


}
