import { api } from "./api";

// 회원정보 조회
export const getUserInfo = () => {
  return api.get(`/member/mypage`);
};

// 로그인 사용자 문제 추천
export const recommendation = () => {
  return api.get(`/member/recommendation`);
};

// 마이페이지 리뷰한 코드, 리뷰 받은 코드
export const mypageCode = () => {
  return api.get(`/member/review`);
};

// 이메일 수정
export const updateEmail = (data) => {
  return api.put("/member/email", data);
};

// 닉네임 수정
export const updateNickname = (data) => {
  return api.put("/member/nickname", data);
};

// 회원 비밀번호 수정
export const changepassword = (data) => {
  return api.put("/member/password", data);
};

// 회원탈퇴
export const withdrawApi = (data) => {
  return api.post("/member/withdrawal", data);
};

// 티어 갱신
export const tierRenewApi = () => {
  return api.put("/member/renew");
};
