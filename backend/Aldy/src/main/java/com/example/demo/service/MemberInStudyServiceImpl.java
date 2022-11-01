package com.example.demo.service;

import com.example.demo.domain.dto.ApplicateStudyRequestDto;
import com.example.demo.domain.dto.MemberInStudyDto;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.domain.entity.MemberInStudy;
import com.example.demo.domain.entity.Study;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.Member.MemberRepository;
import com.example.demo.repository.MemberInStudyRepository;
import com.example.demo.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberInStudyServiceImpl implements MemberInStudyService {

    private  final MemberInStudyRepository memberInStudyRepository;

    private final MemberRepository memberRepository;

    private final StudyRepository studyRepository;

    private List<Integer> authList = List.of(1, 2);

    @Override
    public MemberInStudyDto applicateStudy(ApplicateStudyRequestDto requestDto) {

        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Study study = studyRepository.findById(requestDto.getStudyId())
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        // 중복 검사
        Optional<MemberInStudy> memberInStudy = memberInStudyRepository.findByStudy_IdAndMember_Id(study.getId(), member.getId());
        memberInStudy.ifPresent(m -> {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        });

        // 인원 수 체크
        int currentMember = memberInStudyRepository.findAllByStudyIdAndAuthIn(study.getId(), authList).size();
        if(currentMember >= study.getUpperLimit()) {
            throw new CustomException(ErrorCode.MEMBER_LIMIT_EXCEEDED);
        }

        // save
        MemberInStudyDto memberInStudyDto = new MemberInStudyDto(
                memberInStudyRepository.save(new MemberInStudy(study, member, 3))
        );

        return memberInStudyDto;

    }

    @Override
    public List<MemberInStudyDto> getAllMemberInStudy(Long studyId) {

        List<MemberInStudy> memberInStudyList = memberInStudyRepository.findAllByStudyIdAndAuthIn(studyId, authList);

        List<MemberInStudyDto> memberInStudyDtoList = memberInStudyList.stream().map(e ->
                new MemberInStudyDto(e)).collect(Collectors.toList());

        return memberInStudyDtoList;

    }

    @Override
    public MemberInStudyDto changeAuth(ApplicateStudyRequestDto requestDto, Long loginMemberId, int auth) {

        MemberInStudy loginMemberInStudy = memberInStudyRepository.findByStudy_IdAndMember_Id(requestDto.getStudyId(), loginMemberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBERINSTUDY_NOT_FOUND));

        MemberInStudy memberInStudy = memberInStudyRepository.findByStudy_IdAndMember_Id(requestDto.getStudyId(), requestDto.getMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBERINSTUDY_NOT_FOUND));

        if(loginMemberInStudy.getAuth() == 1) {
            memberInStudy.setAuth(auth);
        }

        return new MemberInStudyDto(memberInStudy);

    }

}
