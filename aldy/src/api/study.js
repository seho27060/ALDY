import { api } from "./api";

// 스터디 가입신청
export const studyRegister = (data) => {
  return api.post("/memberinstudy", data);
};

// 스티원 리스트 조회
export const getStudyMember = (studyId) => {
  return api.get(`/memberinstudy/${studyId}`);
};

// 스터디원 가입수락
export const acceptMember = (data) => {
  return api.patch("/memberinstudy/accpet", data);
};

// 스터디원 강퇴
export const kickMember = (data) => {
  return api.patch("/memberinsutdy/kick", data);
};

// 스터디원 가입 거절
export const rejectMember = (data) => {
  api.delete("/memberinstudy/reject", {
    data: data,
  });
};

// 전체 스터디 목록 조회
export const getStudyList = (page, size, keyword) => {
  return api.get("/study", {
    params: {
      page: page,
      size: size,
      keyword: keyword,
    },
  });
};

// 스터디 생성
export const createStudy = (data) => {
  return api.post("/study", data);
};

// 문제 선정된 요일 반환
export const getSelectedDay = (studyId, year, month) => {
  return api.get(`/study/${studyId}/${year}/${month}`);
};

// 스터디 상세 조회
export const getStudyDetail = (studyId) => {
  return api.get(`/study/${studyId}`);
};

// 스터디 삭제
export const delteStudy = (studyId) => {
  return api.delete(`/study/${studyId}`);
};

// 알림 이메일 전송
export const sendEmail = (data) => {
  return api.post("/study/mail/send", data);
};

// 내 스터디 목록 조회
export const getMyStudy = () => {
  return api.get("/study/mystudy");
};

// 달력에 문제 추가
export const addProblem = (data) => {
  return api.post("/calendar/problem", data);
};

// 달력에서 문제 삭제
export const deleteProblem = (studyId, date, problemId) => {
  return api.delete(`/calendar/problem/${studyId}/${date}/${problemId}`);
};
