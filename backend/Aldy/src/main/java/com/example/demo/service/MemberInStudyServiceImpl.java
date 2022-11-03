package com.example.demo.service;

import com.example.demo.domain.dto.ApplicateStudyRequestDto;
import com.example.demo.domain.dto.MemberInStudyChangeAuthDto;
import com.example.demo.domain.dto.MemberInStudyDto;
import com.example.demo.domain.dto.member.response.SolvedacResponseDto;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.domain.entity.MemberInStudy;
import com.example.demo.domain.entity.Study;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.Member.MemberRepository;
import com.example.demo.repository.MemberInStudyRepository;
import com.example.demo.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberInStudyServiceImpl implements MemberInStudyService {

    private final WebClient webClient;

    private final MemberInStudyRepository memberInStudyRepository;

    private final MemberRepository memberRepository;

    private final StudyRepository studyRepository;

    private final List<Integer> authList = List.of(1, 2);

    @Override
    public void setRoomLeader(Long studyId, String baekjoonId) {

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        Member member = memberRepository.findByBaekjoonId(baekjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        memberInStudyRepository.save(new MemberInStudy(study, member, 1, "Leader"));

    }

    @Override
    public int getAuthByBaekjoonId(String baekjoonId, Long studyId) {
        MemberInStudy memberInStudy = memberInStudyRepository.findByStudy_IdAndMember_BaekjoonId(studyId, baekjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBERINSTUDY_NOT_FOUND));

        return memberInStudy.getAuth();
    }


    @Override
    public MemberInStudyDto applicateStudy(ApplicateStudyRequestDto requestDto, String baekjoonId) {

        Member member = memberRepository.findByBaekjoonId(baekjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Study study = studyRepository.findById(requestDto.getStudyId())
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        // 중복 검사
        Optional<MemberInStudy> memberInStudy = memberInStudyRepository.findByStudy_IdAndMember_Id(study.getId(), member.getId());
        memberInStudy.ifPresent(m -> {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        });

        // 인원 수 체크
        int currentMember = memberInStudyRepository.countAllByStudyIdAndAuthIn(study.getId(), authList);
        if( currentMember >= study.getUpperLimit() ) {
            throw new CustomException(ErrorCode.MEMBER_LIMIT_EXCEEDED);
        }

        // tier 체크
//        tier Member Entity에 추가
        SolvedacResponseDto solvedacResponseDto = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/user/show")
                                .queryParam("handle", baekjoonId)
                                .build())
                .acceptCharset(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(SolvedacResponseDto.class)
                .blockOptional().get();

        if( solvedacResponseDto.getTier() < study.getThreshold() ) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REQUEST);
        }

        // save
        return new MemberInStudyDto(
                memberInStudyRepository.save(new MemberInStudy(study, member, 3, requestDto.getMessage()))
        );
    }

    @Override
    public List<MemberInStudyDto> getAllMemberInStudy(Long studyId) {

        List<MemberInStudy> memberInStudyList = memberInStudyRepository.findAllByStudyIdAndAuthIn(studyId, authList);

        return memberInStudyList.stream().map(e ->
                new MemberInStudyDto(e)).collect(Collectors.toList()
        );

    }

    @Override
    public MemberInStudyDto changeAuth(MemberInStudyChangeAuthDto requestDto, String loginMemberBaekjoonId, int auth) {

        MemberInStudy loginMemberInStudy = memberInStudyRepository.findByStudy_IdAndMember_BaekjoonId(requestDto.getStudyId(), loginMemberBaekjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBERINSTUDY_NOT_FOUND));

        MemberInStudy memberInStudy = memberInStudyRepository.findByStudy_IdAndMember_Id(requestDto.getStudyId(), requestDto.getMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBERINSTUDY_NOT_FOUND));

        if(loginMemberInStudy.getAuth() != 1) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REQUEST);
        }

        memberInStudy.setAuth(auth);

        return new MemberInStudyDto(memberInStudy);

    }

    @Override
    public void rejectMember(MemberInStudyChangeAuthDto requestDto, String loginMemberBaekjoonId) {

        MemberInStudy loginMemberInStudy = memberInStudyRepository.findByStudy_IdAndMember_BaekjoonId(requestDto.getStudyId(), loginMemberBaekjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBERINSTUDY_NOT_FOUND));

        MemberInStudy memberInStudy = memberInStudyRepository.findByStudy_IdAndMember_Id(requestDto.getStudyId(), requestDto.getMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBERINSTUDY_NOT_FOUND));

        if(loginMemberInStudy.getAuth() != 1) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REQUEST);
        }

        memberInStudyRepository.delete(memberInStudy);

    }

}
