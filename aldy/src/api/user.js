import { api } from "./api";

// 회원정보 조회
export const getUserInfo = () => {
  return api.get(`/member/mypage`);
};

// 마이페이지 리뷰한 코드, 리뷰 받은 코드
export const mypageCode = () => {
  return api.get(`/member/review`);
};

// 회원정보 수정
export const updateUserInfo = () => {
  return api.put("/member/info");
};

// 회원 비밀번호 수정
export const changepassword = (data) => {
  return api.put("/member/password", data);
};

// 회원탈퇴
export const withdraw = (data) => {
  return api.post("/member/withdrawal", data);
};
