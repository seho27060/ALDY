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
export const acceptMemberApi = (data) => {
  return api.patch("/memberinstudy/accept", data);
};

// 스터디원 강퇴
export const kickMemberApi = (data) => {
  return api.patch("/memberinstudy/kick", data);
};

// 스터디원 가입 거절
export const rejectMemberApi = (data) => {
  return api.delete("/memberinstudy/reject", {
    data: data,
  });
};

// 스터디 탈퇴
export const studyWithdrawal = (studyId, data) => {
  return api.patch("/memberinstudy/withdrawal", data, {
    params: {
      studyId: studyId,
    },
  });
};

// 스터디 문제 추천
export const getStudyProblem = (algoList, tierList, baekjoonIdList) => {
  return api.get("/solvedac", {
    params: {
      algoList: algoList,
      tierList: tierList,
      baekjoonIdList: baekjoonIdList,
    },
  });
};

// 문제 필터 페이지에 리스트 가져오기
export const getOptionList = (studyId) => {
  return api.get("/solvedac/list", {
    params: {
      studyId: studyId,
    },
  });
};

// 스터디 문제 검색 API
export const getSearchProblem = (keyword) => {
  return api.get("/solvedac/search", {
    params: {
      keyword: keyword,
    },
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
export const deleteStudy = (studyId) => {
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
  return api.post("/study/problem", data);
};

// 달력에서 문제 삭제
export const deleteProblem = (problemId) => {
  return api.delete(`/study/problem/${problemId}`, {
    params: {
      problem_id: problemId,
    },
  });
};

// 달력 문제 조회
export const getProblem = (studyId, year, month, day) => {
  return api.get(`/study/problem/${studyId}/${year}/${month}/${day}`);
};

// 각 팀원들의 문제별 단계 조회
export const getProblemStage = (studyId, problemId) => {
  return api.get(`/study/process/${studyId}/${problemId}`);
};
