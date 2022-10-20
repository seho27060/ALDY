package com.example.demo.service;

import com.example.demo.domain.entity.Member;
import com.example.demo.domain.entity.MemberInStudy;
import com.example.demo.repository.MemberInStudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberInStudyServiceImpl implements MemberInStudyService {

    private final MemberInStudyRepository memberInStudyRepository;

    @Override
    public int countMember(Long studyId) {
        // MemberDTO 나오면 수정
        List<MemberInStudy> memberList = memberInStudyRepository.findByStudy_Id(studyId);

        return memberList.size();

    }

}
