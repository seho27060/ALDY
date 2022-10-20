package com.example.demo.service;

import com.example.demo.domain.dto.*;
import com.example.demo.domain.entity.Code;
import com.example.demo.domain.entity.EditedCode;
import com.example.demo.domain.entity.Member;
import com.example.demo.domain.entity.RequestedCode;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.CodeRepository;
import com.example.demo.repository.EditedCodeRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.RequestedCodeRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@RequiredArgsConstructor
@Transactional
public class CodeServiceImpl implements CodeService {

    private CodeRepository codeRepository;
    private EditedCodeRepository ecRepository;
    private RequestedCodeRepository rcRepository;

    private MemberRepository memberRepository;

    @Override
    public CodeResponseDto getCodesByStudy_idAndProblem_idAndMember_id(long study_id, long problem_id, long member_id) {

        List<Code> codeList = codeRepository.findByStudy_idAndProblem_idAndMember_id(study_id, problem_id, member_id);
        List<CodeDto> codeDtoList = codeList.stream().map(o -> new CodeDto(o)).collect(Collectors.toList());
        CodeResponseDto codeResponseDto = new CodeResponseDto(codeDtoList);
        return codeResponseDto;
    }

    @Override
    public CodeReviewResponseDto getCodesByMember_id(long member_id) {
        List<EditedCode> editingCode = ecRepository.findBySender_id(member_id);
        List<EditedCodeDto> editingCodeList = editingCode.stream().map(o -> new EditedCodeDto(o)).collect(Collectors.toList());
        List<EditedCode> editedCode = ecRepository.findByReceiver_id(member_id);
        List<EditedCodeDto> editedCodeList = editedCode.stream().map(o -> new EditedCodeDto(o)).collect(Collectors.toList());

        List<RequestedCode> requestingCode = rcRepository.findBySender_id(member_id);
        List<RequestedCodeDto> requestingCodeList = requestingCode.stream().map(o -> new RequestedCodeDto(o)).collect(Collectors.toList());

        List<RequestedCode> requestedCode = rcRepository.findByReceiver_id(member_id);
        List<RequestedCodeDto> requestedCodeList = requestedCode.stream().map(o -> new RequestedCodeDto(o)).collect(Collectors.toList());
        CodeReviewResponseDto codeReviewResponseDto = new CodeReviewResponseDto(editingCodeList, editedCodeList, requestingCodeList, requestedCodeList);
        return codeReviewResponseDto;
    }


//    첨삭된 코드는 3단계 코드에 종속되어야 함.
    @Override
    public EditedCodeDto replyEditedCode(CodeReviewRequestDto codeReviewRequestDto) {
        // 첨삭한 코드 내용
        String text = codeReviewRequestDto.getCode();
        // 첨삭한 사람 아이디
        String sender_id = codeReviewRequestDto.getBackjoon_id();
        // 첨삭한 사람
        Member sender = memberRepository.findByBackjoon_id(sender_id).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 첨삭 받은 사람 아이디
        String receiver_id = codeReviewRequestDto.getReceiver_id();
        // 첨삭 받은 사람
        Member receiver = memberRepository.findByBackjoon_id(codeReviewRequestDto.getReceiver_id()).orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 어떤 스터디의 어떤 문제의 누가 푼 코드에 종속되는지 알기 위해 필요한 변수들
        long receiver_index = receiver.getId();
        long study_id = codeReviewRequestDto.getStudy_id();
        long problem_id = codeReviewRequestDto.getProblem_id();

        // 어떤 코드를 보고 첨삭했는지
        Code code = codeRepository.findByStudy_idAndProblem_idAndMember_idAndLevel(study_id, problem_id, receiver_index,3).orElseThrow(
                () -> new CustomException(ErrorCode.CODE_NOT_FOUND)
        );


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
    {
”backjoon_id” : id,
”code” : code_text,
”process” : 1,
”study_id” : 스터디 번호,
”problem_id” : 문제 번호,
”problem_name” : 문제 이름,
”problem_tier” : 문제 티어(숫자)
    }
    @Override
    public RequestedCodeDto requestCode(CodeSaveRequestDto codeSaveRequestDto) {
        Member sender = memberRepository.findByBackjoon_id(codeSaveRequestDto.getBackjoon_id()).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );
        Code original_code = codeRepository.findByStudy_idAndProblem_idAndMember_idAndLevel(
            codeSaveRequestDto.getStudy_id(),codeSaveRequestDto.getProblem_id(),codeSaveRequestDto.get
                )
    }
}
