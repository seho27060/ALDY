package com.example.demo.service.study;

import com.example.demo.domain.dto.study.ApplicateStudyRequestDto;
import com.example.demo.domain.dto.study.MemberInStudyChangeAuthDto;
import com.example.demo.domain.dto.study.MemberInStudyDto;
import com.example.demo.domain.dto.solvedac.response.SolvedacMemberResponseDto;
import com.example.demo.domain.entity.Code.Code;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.domain.entity.Study.MemberInStudy;
import com.example.demo.domain.entity.Study.Problem;
import com.example.demo.domain.entity.Study.Study;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.code.CodeRepository;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.repository.study.MemberInStudyRepository;
import com.example.demo.repository.study.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
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

    private final CodeRepository codeRepository;

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
            if(m.getAuth() == 0){
                throw new CustomException(ErrorCode.UNAUTHORIZED_REQUEST);
            } else {
                throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
            }
        });

        // 인원 수 체크
        int currentMember = memberInStudyRepository.countAllByStudyIdAndAuthIn(study.getId(), authList);
        if( currentMember >= study.getUpperLimit() ) {
            throw new CustomException(ErrorCode.MEMBER_LIMIT_EXCEEDED);
        }

        // tier 체크
        SolvedacMemberResponseDto solvedacMemberResponseDto = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/user/show")
                                .queryParam("handle", baekjoonId)
                                .build())
                .acceptCharset(StandardCharsets.UTF_8)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(SolvedacMemberResponseDto.class)
                .blockOptional().get();

        if( solvedacMemberResponseDto.getTier() < study.getThreshold() ) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REQUEST);
        }

        // save
        if(study.getVisibility() == 1) {
            return new MemberInStudyDto(
                    memberInStudyRepository.save(new MemberInStudy(study, member, 2, requestDto.getMessage()))
            );
        }
        return new MemberInStudyDto(
                memberInStudyRepository.save(new MemberInStudy(study, member, 3, requestDto.getMessage()))
        );

    }

    @Override
    public List<MemberInStudyDto> getAllMemberInStudy(Long studyId) {

        List<MemberInStudy> memberInStudyList = memberInStudyRepository.findAllByStudyIdAndAuthIn(studyId, authList);

        return memberInStudyList.stream().map(e ->
                new MemberInStudyDto(e, getNumberOfSolvedTogether(e))).collect(Collectors.toList()
        );

    }

    @Override
    public List<MemberInStudyDto> getAllApplicateMemberInStudy(Long studyId) {

        List<MemberInStudy> memberInStudyList = memberInStudyRepository.findAllByStudyIdAndAuthIn(studyId, List.of(3));

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
    public MemberInStudyDto changeAuth(Long studyId, String loginMemberBaekjoonId, int auth) {

        Member loginMember = memberRepository.findByBaekjoonId(loginMemberBaekjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        MemberInStudy memberInStudy = memberInStudyRepository.findByStudy_IdAndMember_Id(studyId, loginMember.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBERINSTUDY_NOT_FOUND));

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

    public int getNumberOfSolvedTogether(MemberInStudy memberInStudy) {

        List<Code> codeList = codeRepository.findByStudy_idAndWriter_id(memberInStudy.getStudy().getId(), memberInStudy.getMember().getId());

        HashMap<String, Integer> countProblemNum = new HashMap<>();
        for(Code code : codeList) {
            Problem problem = code.getProblem();

            int count = 1;
            if(countProblemNum.containsKey(problem.getProblemName())) {
                count = countProblemNum.get(problem.getProblemName()) + 1;
            }
            countProblemNum.put(problem.getProblemName(), count);
        }

        return countProblemNum.size();
    }
}
