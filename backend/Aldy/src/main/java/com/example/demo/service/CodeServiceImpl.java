package com.example.demo.service;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.*;
import com.example.demo.domain.entity.Code;
import com.example.demo.domain.entity.EditedCode;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.domain.entity.RequestedCode;
import com.example.demo.domain.entity.Study;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.CodeRepository;
import com.example.demo.repository.EditedCodeRepository;
import com.example.demo.repository.Member.MemberRepository;
import com.example.demo.repository.RequestedCodeRepository;
import com.example.demo.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CodeServiceImpl implements CodeService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CodeRepository codeRepository;
    private final EditedCodeRepository ecRepository;
    private final RequestedCodeRepository rcRepository;

    private final MemberRepository memberRepository;

    private final StudyRepository studyRepository;

    private final RequestedCodeRepository requestedCodeRepository;

    @Override
    public CodeResponseDto getCodesByStudy_idAndProblem_idAndMember_id(long study_id, long problem_id, HttpServletRequest request) {

        String backjoonId = jwtTokenProvider.getBackjoonId(request.getHeader("Authorization"));
        Member writer = memberRepository.findByBackjoonId(backjoonId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        System.out.println("잘 됨?");
        List<Code> codeList = codeRepository.findByStudy_idAndProblemIdAndWriter_id(study_id, problem_id, writer.getId());
        System.out.println("잘 됨??????");
        List<CodeDto> codeDtoList = codeList.stream().map(o -> new CodeDto(o)).collect(Collectors.toList());
        CodeResponseDto codeResponseDto = new CodeResponseDto(codeDtoList);
        return codeResponseDto;
    }

    @Override
    public CodeReviewPageResponseDto getCodesByMember_id(HttpServletRequest request) {

        String backjoonId = jwtTokenProvider.getBackjoonId(request.getHeader("Authorization"));
        Member member = memberRepository.findByBackjoonId(backjoonId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        long member_id = member.getId();

        List<EditedCode> editingCode = ecRepository.findBySender_id(member_id);
        List<EditedCodeDto> editingCodeList = editingCode.stream().map(o -> new EditedCodeDto(o)).collect(Collectors.toList());
        List<EditedCode> editedCode = ecRepository.findByReceiver_id(member_id);
        List<EditedCodeDto> editedCodeList = editedCode.stream().map(o -> new EditedCodeDto(o)).collect(Collectors.toList());

        List<RequestedCode> requestingCode = rcRepository.findBySender_id(member_id);
        List<RequestedCodeDto> requestingCodeList = requestingCode.stream().map(o -> new RequestedCodeDto(o)).collect(Collectors.toList());

        List<RequestedCode> requestedCode = rcRepository.findByReceiver_id(member_id);
        List<RequestedCodeDto> requestedCodeList = requestedCode.stream().map(o -> new RequestedCodeDto(o)).collect(Collectors.toList());
        CodeReviewPageResponseDto codeReviewPageResponseDto = new CodeReviewPageResponseDto(editingCodeList, editedCodeList, requestingCodeList, requestedCodeList);
        return codeReviewPageResponseDto;
    }


//    첨삭된 코드는 3단계 코드에 종속되어야 함.
    @Override
    public EditedCodeDto replyEditedCode(CodeReviewReplyDto codeReviewReplyDto, HttpServletRequest request) {
        // 첨삭한 코드 내용
        String text = codeReviewReplyDto.getCode();


        // 첨삭한 사람 아이디
        String sender_id = jwtTokenProvider.getBackjoonId(request.getHeader("Authorization"));

        // 첨삭한 사람
        Member sender = memberRepository.findByBackjoonId(sender_id).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 첨삭 받은 사람 아이디
        String receiver_id = codeReviewReplyDto.getReceiver_id();
        // 첨삭 받은 사람
        Member receiver = memberRepository.findByBackjoonId(codeReviewReplyDto.getReceiver_id()).orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 어떤 스터디의 어떤 문제의 누가 푼 코드에 종속되는지 알기 위해 필요한 변수들
        long receiver_index = receiver.getId();
        long study_id = codeReviewReplyDto.getStudy_id();
        long problem_id = codeReviewReplyDto.getProblem_id();

        // 어떤 코드를 보고 첨삭했는지
        Code code = codeRepository.findByStudy_idAndProblemIdAndWriter_idAndProcess(study_id, problem_id, receiver_index,3).orElseThrow(
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
    public CodeDto saveCode(CodeSaveRequestDto codeSaveRequestDto, HttpServletRequest request) {
        // 작성자 min61037

        String backjoonId = jwtTokenProvider.getBackjoonId(request.getHeader("Authorization"));
        Member writer = memberRepository.findByBackjoonId(backjoonId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Study study = studyRepository.findById(codeSaveRequestDto.getStudy_id()).orElseThrow(()->new CustomException(ErrorCode.STUDY_NOT_FOUND));
        System.out.println(study.getId());
//        Code 객체를 생성할 때, 생성자 vs 빌더패턴,
//        OCP Open-Closed-principle
//        Code code = new Code(codeSaveRequestDto, writer);
        Code code = Code.builder()
                .code(codeSaveRequestDto.getCode())
                .writer(writer)
                .study(study)
                .process(codeSaveRequestDto.getProcess())
                .problemId(codeSaveRequestDto.getProblem_id())
                .problemName(codeSaveRequestDto.getProblem_name())
                .problemTier(codeSaveRequestDto.getProblem_tier())
                .build();

        try{
            codeRepository.save(code);
        }catch(Exception e){
            throw new CustomException(ErrorCode.SAVE_ERROR);
        }
        return new CodeDto(code);
    }
    @Override
    public RequestedCodeDto requestCode(CodeReviewRequestDto codeReviewRequestDto, HttpServletRequest request) {

        String backjoonId = jwtTokenProvider.getBackjoonId(request.getHeader("Authorization"));
        Member sender = memberRepository.findByBackjoonId(backjoonId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

//        Member sender = memberRepository.findByBackjoonId(codeReviewRequestDto.getSender_id()).orElseThrow(
//                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
//        );
        Member receiver = memberRepository.findByBackjoonId(codeReviewRequestDto.getReceiver_id()).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );

        Code original_code = codeRepository.findByStudy_idAndProblemIdAndWriter_idAndProcess(
            codeReviewRequestDto.getStudy_id(),codeReviewRequestDto.getProblem_id(),sender.getId(),3
                ).orElseThrow(() -> new CustomException(ErrorCode.CODE_NOT_FOUND));
        RequestedCode requestedCode = RequestedCode.builder()
                .code(original_code)
                .receiver(receiver)
                .sender(sender)
                .build();

        requestedCodeRepository.save(requestedCode);

        return new RequestedCodeDto(requestedCode);
    }
}
