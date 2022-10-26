package com.example.demo.service;

import com.example.demo.domain.dto.*;
import com.example.demo.domain.entity.Code;
import com.example.demo.domain.entity.EditedCode;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.domain.entity.RequestedCode;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.CodeRepository;
import com.example.demo.repository.EditedCodeRepository;
import com.example.demo.repository.Member.MemberRepository;
import com.example.demo.repository.RequestedCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CodeServiceImpl implements CodeService {

    private CodeRepository codeRepository;
    private EditedCodeRepository ecRepository;
    private RequestedCodeRepository rcRepository;

    private MemberRepository memberRepository;

    // 백준 아이디에 관해 1,2,3,4단계 코드들 반환
    @Override
    public CodeResponseDto getCodesByStudy_idAndProblem_idAndMember_id(long study_id, long problem_id, long member_id) {

        List<Code> codeList = codeRepository.findByStudy_idAndProblemIdAndMember_id(study_id, problem_id, member_id);
        List<CodeDto> codeDtoList = codeList.stream().map(o -> new CodeDto(o)).collect(Collectors.toList());
        Collections.sort(codeDtoList);
        CodeResponseDto codeResponseDto = new CodeResponseDto(codeDtoList);
        return codeResponseDto;
    }

    // 각종 요청, 응답 리스트 반환
    @Override
    public CodeReviewResponseDto getCodesByMember_id(long member_id) {
        // 내가 첨삭한 코드들 모음
        List<EditedCode> editingCode = ecRepository.findBySender_id(member_id);
        List<EditedCodeDto> editingCodeList = editingCode.stream().map(o -> new EditedCodeDto(o)).collect(Collectors.toList());
        // 내가 첨삭받은 코드들 모음
        List<EditedCode> editedCode = ecRepository.findByReceiver_id(member_id);
        List<EditedCodeDto> editedCodeList = editedCode.stream().map(o -> new EditedCodeDto(o)).collect(Collectors.toList());
        // 내가 리뷰 요청한 코드들 모음
        List<RequestedCode> requestingCode = rcRepository.findBySender_id(member_id);
        List<RequestedCodeDto> requestingCodeList = requestingCode.stream().map(o -> new RequestedCodeDto(o)).collect(Collectors.toList());
        // 내가 리뷰 요청 받은 코드들 모음
        List<RequestedCode> requestedCode = rcRepository.findByReceiver_id(member_id);
        List<RequestedCodeDto> requestedCodeList = requestedCode.stream().map(o -> new RequestedCodeDto(o)).collect(Collectors.toList());
        // 위의 네 개를 하나로 묶어서 프론트에 주는 DTO 만듬.
        CodeReviewResponseDto codeReviewResponseDto = new CodeReviewResponseDto(editingCodeList, editedCodeList, requestingCodeList, requestedCodeList);
        return codeReviewResponseDto;
    }


//    첨삭된 코드는 3단계 코드에 종속되어야 함.
    @Override
    public EditedCodeDto replyEditedCode(CodeReviewDto codeReviewDto) {
        // 첨삭한 코드 내용
        String text = codeReviewDto.getCode();
        // 첨삭한 사람 아이디
        String sender_id = codeReviewDto.getBackjoon_id();
        // 첨삭한 사람
        Member sender = memberRepository.findByBackjoonId(sender_id).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 첨삭 받은 사람 아이디
        String receiver_id = codeReviewDto.getReceiver_id();
        // 첨삭 받은 사람
        Member receiver = memberRepository.findByBackjoonId(codeReviewDto.getReceiver_id()).orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 어떤 스터디의 어떤 문제의 누가 푼 코드에 종속되는지 알기 위해 필요한 변수들
        long receiver_index = receiver.getId();
        long study_id = codeReviewDto.getStudy_id();
        long problem_id = codeReviewDto.getProblem_id();

        // 어떤 코드를 보고 첨삭했는지
        Code code = codeRepository.findByStudy_idAndProblemIdAndMember_idAndProcess(study_id, problem_id, receiver_index,3).orElseThrow(
                () -> new CustomException(ErrorCode.CODE_NOT_FOUND)
        );

//      첨삭한 코드 저장
        EditedCode editedCode = EditedCode.builder()
                .code(code)
                .receiver(receiver)
                .sender(sender)
                .text(text)
                .build();
        try{
            ecRepository.save(editedCode);
        }catch(Exception e){
            throw new CustomException(ErrorCode.SAVE_ERROR);
        }
        return new EditedCodeDto(editedCode);
    }

//      1,2,3,4단계 코드 제출에 해당하는 API, 저장된 코드를 반환해줌.
    @Override
    public CodeDto saveCode(CodeSaveRequestDto codeSaveRequestDto) {
        Code code = new Code(codeSaveRequestDto);
        try{
            codeRepository.save(code);
        }catch(Exception e){
            throw new CustomException(ErrorCode.SAVE_ERROR);
        }
        return new CodeDto(code);
    }

//     리뷰 요청 코드, 3단계 코드 제출 후에 사람을 고르고 요청되는 API
    @Override
    public RequestedCodeDto requestCode(CodeReviewDto codeReviewDto) {
        // 리뷰 요청 보내는 사람
        Member sender = memberRepository.findByBackjoonId(codeReviewDto.getBackjoon_id()).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );
        // 리뷰 요청 받는 사람
        Member receiver = memberRepository.findByBackjoonId(codeReviewDto.getReceiver_id()).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );

        long sender_index = sender.getId();
        long study_id = codeReviewDto.getStudy_id();
        long problem_id = codeReviewDto.getProblem_id();

        // 요청되는 코드는 3단계 코드임.
        Code third_process_code = codeRepository.findByStudy_idAndProblemIdAndMember_idAndProcess(
            study_id,problem_id, sender_index, 3
                ).orElseThrow(() -> new CustomException(ErrorCode.CODE_NOT_FOUND));

        // 요청되는 코드 저장
        RequestedCode requestedCode = rcRepository.save(RequestedCode.builder()
                .code(third_process_code)
                .sender(sender)
                .receiver(receiver)
                .build());
        return new RequestedCodeDto(requestedCode);
    }
}
