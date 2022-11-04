package com.example.demo.service.code;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.code.*;
import com.example.demo.domain.entity.Code.Code;
import com.example.demo.domain.entity.Code.EditedCode;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.domain.entity.Code.RequestedCode;
import com.example.demo.domain.entity.Study.Calendar;
import com.example.demo.domain.entity.Study.ProblemTable;
import com.example.demo.domain.entity.Study.Study;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.code.CodeRepository;
import com.example.demo.repository.code.EditedCodeRepository;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.repository.code.RequestedCodeRepository;
import com.example.demo.repository.study.CalendarRepository;
import com.example.demo.repository.study.ProblemTableRepository;
import com.example.demo.repository.study.StudyRepository;
import com.example.demo.service.study.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CodeServiceImpl implements CodeService {

    private final EmailServiceImpl emailServiceImpl;

    private final JwtTokenProvider jwtTokenProvider;
    private final CodeRepository codeRepository;
    private final EditedCodeRepository ecRepository;
    private final RequestedCodeRepository rcRepository;

    private final MemberRepository memberRepository;

    private final StudyRepository studyRepository;

    private final ProblemTableRepository problemTableRepository;

    private CalendarRepository calendarRepository;

    @Override
    public CodeResponseDto getCodesByStudy_idAndProblem_idAndMember_id(long study_id, long problem_id, HttpServletRequest request) {

        String baekjoonId = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        Member writer = memberRepository.findByBaekjoonId(baekjoonId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        List<Code> codeList = codeRepository.findByStudy_idAndProblemIdAndWriter_id(study_id, problem_id, writer.getId());
        List<CodeDto> codeDtoList = codeList.stream().map(o -> new CodeDto(o)).collect(Collectors.toList());
        CodeResponseDto codeResponseDto = new CodeResponseDto(codeDtoList);
        return codeResponseDto;
    }

    @Override
    public CodeReviewPageResponseDto getCodesByMember_id(HttpServletRequest request) {

        String baekjoonId = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        Member member = memberRepository.findByBaekjoonId(baekjoonId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

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
        String sender_id = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));

        // 첨삭한 사람
        Member sender = memberRepository.findByBaekjoonId(sender_id).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 첨삭 받은 사람 아이디
        String receiver_id = codeReviewReplyDto.getReceiverId();
        // 첨삭 받은 사람
        Member receiver = memberRepository.findByBaekjoonId(codeReviewReplyDto.getReceiverId()).orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        // 어떤 스터디의 어떤 문제의 누가 푼 코드에 종속되는지 알기 위해 필요한 변수들
        long receiver_index = receiver.getId();
        long study_id = codeReviewReplyDto.getStudyId();
        long problem_id = codeReviewReplyDto.getProblemId();

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

        RequestedCode requestedCode = rcRepository.findByCode_idAndSender_idAndReceiver_id(code.getId(), receiver.getId(),
                sender.getId()).orElseThrow(()->new CustomException(ErrorCode.CODE_NOT_FOUND));
        requestedCode.replyCheck();

        Study study = studyRepository.findById(study_id).orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));
        emailServiceImpl.sendCodeAlertEmail(study, receiver.getEmail(), sender.getNickname(), receiver.getNickname(), "reply");
        return new EditedCodeDto(editedCode);
    }

    @Override
    public CodeDto saveCode(CodeSaveRequestDto codeSaveRequestDto, HttpServletRequest request) {
        String baekjoonId = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        Member writer = memberRepository.findByBaekjoonId(baekjoonId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Study study = studyRepository.findById(codeSaveRequestDto.getStudyId()).orElseThrow(()->new CustomException(ErrorCode.STUDY_NOT_FOUND));

        int month = LocalDateTime.now().getMonth().getValue();
        int year = LocalDateTime.now().getYear();
        Calendar calendar = calendarRepository.findByStudy_idAndCalendarMonthAndCalendarYear(study.getId(),month, year)
                .orElseThrow(()->new CustomException(ErrorCode.CALENDAR_NOT_FOUND));

        ProblemTable problemTable = problemTableRepository.
                findByCalendar_idAndProblemId(calendar.getId(), codeSaveRequestDto.getProblemId())
                .orElseThrow(()->new CustomException(ErrorCode.PROBLEMTABLE_NOT_FOUND));

        Code code = Code.builder()
                .code(codeSaveRequestDto.getCode())
                .writer(writer)
                .study(study)
                .process(codeSaveRequestDto.getProcess())
                .problemTable(problemTable)
//                .problemName(codeSaveRequestDto.getProblemName())
//                .problemTier(codeSaveRequestDto.getProblemTier())
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

        String baekjoonId = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        Member sender = memberRepository.findByBaekjoonId(baekjoonId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Member receiver = memberRepository.findByBaekjoonId(codeReviewRequestDto.getReceiverId()).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
        );

        Code original_code = codeRepository.findByStudy_idAndProblemIdAndWriter_idAndProcess(
            codeReviewRequestDto.getStudyId(),codeReviewRequestDto.getProblemId(),sender.getId(),3
                ).orElseThrow(() -> new CustomException(ErrorCode.CODE_NOT_FOUND));
        RequestedCode requestedCode = RequestedCode.builder()
                .code(original_code)
                .receiver(receiver)
                .sender(sender)
                .isDone(false)
                .build();

        rcRepository.save(requestedCode);

        Study study = studyRepository.findById(codeReviewRequestDto.getStudyId()).orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));
        emailServiceImpl.sendCodeAlertEmail(study, receiver.getEmail(), sender.getNickname(), receiver.getNickname(), "request");
        return new RequestedCodeDto(requestedCode);
    }

    @Override
    public List<EditedCodeDto> getEditedCodes(long studyId, int problemId, HttpServletRequest request) {

        String baekjoonId = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        Member receiver = memberRepository.findByBaekjoonId(baekjoonId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Code code = codeRepository.findByStudy_idAndProblemIdAndWriter_idAndProcess(studyId, problemId, receiver.getId(), 3).
                orElseThrow(() -> new CustomException(ErrorCode.CODE_NOT_FOUND));
        List<EditedCode> editedCodeList = ecRepository.findAllByCode_idAndReceiver_id(code.getId(), receiver.getId());
        List<EditedCodeDto> editedCodeDtoList = editedCodeList.stream().map(EditedCodeDto::new).collect(Collectors.toList());
        return editedCodeDtoList;
    }
}