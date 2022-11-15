package com.example.demo.service.code;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.code.*;
import com.example.demo.domain.dto.study.ProblemDto;
import com.example.demo.domain.dto.study.StudyMemberDto;
import com.example.demo.domain.dto.study.StudyStatusDto;
import com.example.demo.domain.entity.Code.Code;
import com.example.demo.domain.entity.Code.EditedCode;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.domain.entity.Code.RequestedCode;
import com.example.demo.domain.entity.Study.Calendar;
import com.example.demo.domain.entity.Study.Problem;
import com.example.demo.domain.entity.Study.Study;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.code.CodeRepository;
import com.example.demo.repository.code.EditedCodeRepository;
import com.example.demo.repository.member.MemberRepository;
import com.example.demo.repository.code.RequestedCodeRepository;
import com.example.demo.repository.study.CalendarRepository;
import com.example.demo.repository.study.ProblemRepository;
import com.example.demo.repository.study.StudyRepository;
import com.example.demo.service.study.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
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

    private final ProblemRepository problemRepository;

    private final CalendarRepository calendarRepository;

    @Override
    public CodeResponseDto getCodesByStudy_idAndProblem_idAndMember_id(long study_id, long problem_id, HttpServletRequest request) {

        String baekjoonId = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        Member writer = memberRepository.findByBaekjoonId(baekjoonId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        List<Code> codeList = codeRepository.findByStudy_idAndProblem_idAndWriter_id(study_id, problem_id, writer.getId());
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
        Code code = codeRepository.findByStudy_idAndProblem_idAndWriter_idAndProcess(study_id, problem_id, receiver_index,2).orElseThrow(
                () -> new CustomException(ErrorCode.CODE_NOT_FOUND)
        );
        // 내가 첨삭한 코드가 있는지
        Optional<EditedCode> preEditedCode = ecRepository.findAllByCode_idAndReceiver_idAndSender_id(
                code.getId(), receiver.getId(), sender.getId()
        );
        EditedCode editedCode = null;
        // 첨삭한 코드가 있다면 첨삭 내용만 바꿈
        if(preEditedCode.isPresent()){
            editedCode = preEditedCode.get();
            editedCode.changeCode(text);
        }else {
        // 첨삭한 코드가 없으면 새로 만들어줌.
            editedCode = EditedCode.builder()
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
        Member writer = memberRepository.findByBaekjoonId(baekjoonId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Study study = studyRepository.findById(codeSaveRequestDto.getStudyId())
                .orElseThrow(()->new CustomException(ErrorCode.STUDY_NOT_FOUND));
//        System.out.println("-----------------------"+codeSaveRequestDto.getProblemId()+" "+ calendar.getId());
        Problem problem = problemRepository.
                findById(codeSaveRequestDto.getProblemId())
                .orElseThrow(()->new CustomException(ErrorCode.PROBLEMTABLE_NOT_FOUND));
        // 로직을 더 짧게 JPA를 활용해서 해줄수도 있을 듯.
        // exist 함수로
        boolean preProcessCodeExists = checkPreCodeExists(codeSaveRequestDto, writer, study);
        if(!preProcessCodeExists){
            throw new CustomException(ErrorCode.CODE_NOT_FOUND);
        }
        // 있으면 덮어 쓰기 없으면 새로 생성
        Optional<Code> preCode = codeRepository.findByStudy_idAndProblem_idAndWriter_idAndProcess(
                study.getId(),
                codeSaveRequestDto.getProblemId(),
                writer.getId(),
                codeSaveRequestDto.getProcess()
        );
        Code code = null;
        if(preCode.isPresent()){
            code = preCode.get();
            code.updateCode(codeSaveRequestDto.getCode());
        }else{
            code = Code.builder()
                    .code(codeSaveRequestDto.getCode())
                    .writer(writer)
                    .study(study)
                    .process(codeSaveRequestDto.getProcess())
                    .problem(problem)
    //                .problemName(codeSaveRequestDto.getProblemName())
    //                .problemTier(codeSaveRequestDto.getProblemTier())
                    .build();

            try{
                codeRepository.save(code);
            }catch(Exception e){
                throw new CustomException(ErrorCode.SAVE_ERROR);
            }
        }


        return new CodeDto(code);
    }
    @Override
    public List<RequestedCodeDto> requestCode(CodeReviewRequestDto codeReviewRequestDto, HttpServletRequest request) {

        String baekjoonId = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        Member sender = memberRepository.findByBaekjoonId(baekjoonId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Code original_code = codeRepository.findByStudy_idAndProblem_idAndWriter_idAndProcess(
                codeReviewRequestDto.getStudyId(),codeReviewRequestDto.getProblemId(),sender.getId(),2
        ).orElseThrow(() -> new CustomException(ErrorCode.CODE_NOT_FOUND));

        List<RequestedCodeDto> response = new ArrayList<>();

        for(String receiverId : codeReviewRequestDto.getReceiverId()){
            Member receiver = memberRepository.findByBaekjoonId(receiverId).orElseThrow(
                    () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
            );
            Optional<RequestedCode> preRequestedCode = rcRepository.findByCode_idAndSender_idAndReceiver_id(
                    original_code.getId(), sender.getId(), receiver.getId()
            );
            RequestedCode requestedCode = null;
            if(preRequestedCode.isPresent()){
                requestedCode = preRequestedCode.get();
            }else{
                requestedCode = RequestedCode.builder()
                    .code(original_code)
                    .receiver(receiver)
                    .sender(sender)
                    .isDone(false)
                    .build();

                rcRepository.save(requestedCode);
            }

            Study study = studyRepository.findById(codeReviewRequestDto.getStudyId()).orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));
            emailServiceImpl.sendCodeAlertEmail(study, receiver.getEmail(), sender.getNickname(), receiver.getNickname(), "request");

            response.add(new RequestedCodeDto(requestedCode));
        }

        return response;
    }

    @Override
    public List<EditedCodeDto> getEditedCodes(long studyId, int problemId, HttpServletRequest request) {

        String baekjoonId = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));
        Member receiver = memberRepository.findByBaekjoonId(baekjoonId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Code code = codeRepository.findByStudy_idAndProblem_idAndWriter_idAndProcess(studyId, problemId, receiver.getId(), 2).
                orElseThrow(() -> new CustomException(ErrorCode.CODE_NOT_FOUND));
        List<EditedCode> editedCodeList = ecRepository.findAllByCode_idAndReceiver_id(code.getId(), receiver.getId());
        List<EditedCodeDto> editedCodeDtoList = editedCodeList.stream().map(EditedCodeDto::new).collect(Collectors.toList());
        return editedCodeDtoList;
    }

    @Override
    public Page<RequestedCodeDto> getMyRequestedCodeList(int page, int size, String loginMember) {
        Member me = memberRepository.findByBaekjoonId(loginMember).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size, Sort.by("isDone").ascending().
                and(Sort.by("requestDate").descending()));
        Page<RequestedCode> requestedCodePage = rcRepository.findByReceiver_id(me.getId(), pageable);

        Page<RequestedCodeDto> requestedCodeDtoPage = requestedCodePage.map(
                o -> new RequestedCodeDto(o));

        return requestedCodeDtoPage;
    }

    @Override
    public Page<RequestedCodeDto> getMyRequestingCodeList(int page, int size, String loginMember) {
        Member me = memberRepository.findByBaekjoonId(loginMember).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size, Sort.by("isDone").ascending().
                and(Sort.by("requestDate").descending()));
        Page<RequestedCode> requestedCodePage = rcRepository.findBySender_id(me.getId(), pageable);

        Page<RequestedCodeDto> requestedCodeDtoPage = requestedCodePage.map(
                o -> new RequestedCodeDto(o));

        return requestedCodeDtoPage;
    }

    @Override
    public Page<EditedCodeDto> getMyEditedCodeList(int page, int size, String loginMember) {
        Member me = memberRepository.findByBaekjoonId(loginMember)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size, Sort.by("editedDate").descending());
        Page<EditedCode> requestedCodePage = ecRepository.findByReceiver_id(me.getId(), pageable);

        Page<EditedCodeDto> editedCodeDtoPage = requestedCodePage.map(
                o -> new EditedCodeDto(o));

        return editedCodeDtoPage;
    }

    @Override
    public Page<EditedCodeDto> getMyEditingCodeList(int page, int size, String loginMember) {
        Member me = memberRepository.findByBaekjoonId(loginMember)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size, Sort.by("editedDate").descending());
        Page<EditedCode> requestedCodePage = ecRepository.findBySender_id(me.getId(), pageable);

        Page<EditedCodeDto> editedCodeDtoPage = requestedCodePage.map(
                o -> new EditedCodeDto(o));

        return editedCodeDtoPage;
    }

    @Override
    public StudyStatusDto getProcessOfStudy(long study_id, long problem_id, HttpServletRequest request) {
        List<Code> codeList = codeRepository.findByStudy_idAndProblem_id(study_id, problem_id);
        HashMap<Long, Integer> data = new HashMap<>();
        HashMap<Long, Member> memberData = new HashMap<>();
        for(Code code : codeList){
            long memberId = code.getWriter().getId();
            int process = code.getProcess();
            System.out.println("-------"+memberId+" "+process);
            data.put(memberId, process);
            memberData.put(memberId, code.getWriter());
        }
        List<StudyMemberDto> studyMemberDtoList = new ArrayList<>();
        for(Map.Entry<Long,Integer> entry : data.entrySet()){
            StudyMemberDto studyMemberDto = StudyMemberDto.builder()
                    .memberId(entry.getKey())
                    .baekjoonId(memberData.get(entry.getKey()).getBaekjoonId())
                    .nickname(memberData.get(entry.getKey()).getNickname())
                    .process(entry.getValue())
                    .build();
            studyMemberDtoList.add(studyMemberDto);
        }
        StudyStatusDto studyStatusDto = StudyStatusDto.builder()
                .studyMemberDtoList(studyMemberDtoList)
                .build();
        return studyStatusDto;
    }

    @Override
    public List<ProblemDto> getProblemsOfDay(long study_id, int year, int month, int day, HttpServletRequest request) {
        Calendar calendar = calendarRepository.findByStudy_idAndCalendarYearAndCalendarMonth(study_id, year, month)
                .orElseThrow(() -> new CustomException(ErrorCode.CALENDAR_NOT_FOUND));
        List<Problem> problemList = problemRepository.findByCalendar_idAndProblemDay(calendar.getId(), day);
        List<ProblemDto> problemDtoList = problemList.stream().map(ProblemDto::new).collect(Collectors.toList());
        return problemDtoList;
    }

    @Override
    public Page<CodeDto> getMyFinalCodeList(int page, int size, String loginMember) {
        Member member = memberRepository.findByBaekjoonId(loginMember)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Long memberId = member.getId();

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<Code> codePage = codeRepository.findByWriterIdAndProcess(memberId,3,pageable);
        Page<CodeDto> codeDtoPage = codePage.map(o -> new CodeDto(o));
        return codeDtoPage;
    }


    public boolean checkPreCodeExists(CodeSaveRequestDto codeSaveRequestDto, Member writer, Study study){
        int nowProcess = codeSaveRequestDto.getProcess();
        int preProcess = nowProcess-1;
        if(preProcess == 0){
            return true;
        }
        else{
            Optional<Code> preCode = codeRepository.findByStudy_idAndProblem_idAndWriter_idAndProcess(
                    study.getId(),
                    codeSaveRequestDto.getProblemId(),
                    writer.getId(),
                    preProcess
            );
            return preCode.isPresent();
        }
    }
}
