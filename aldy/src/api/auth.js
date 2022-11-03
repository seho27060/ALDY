import axios from "axios";
import { api } from "./api";

const baseURL = process.env.REACT_APP_API_URL + "/api";

// 로그인
export const login = (credentials) => {
  return axios.post(baseURL + "/auth/login", credentials);
};
// 로그아웃
export const logout = () => {
  sessionStorage.clear();
};
// 회원가입
export const join = (credentials) => {
  return axios.post(baseURL + "/auth/join", credentials);
};

// email 중복 체크
export const emailValid = (email) => {
  return api.get(`/auth/email/${email}`);
};

// solved.ac 연동여부 확인
export const interLock = (baekjoonId) => {
  return api.get(`auth/interlock/${baekjoonId}`);
};

// 닉네임 중복 체크
export const nicknameValid = (nickname) => {
  return api.get(`/auth/nickname/${nickname}`);
};

// 백준 연동 인증용 문자열 발급
export const baekjoonVerify = (data) => {
  return api.post("/auth/verification", data);
};
