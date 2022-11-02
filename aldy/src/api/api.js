import axios from "axios";

export const baseURL = process.env.REACT_APP_API_URL + "/api";

export const api = axios.create({
  baseURL: process.env.REACT_APP_API_URL + "/api",
  headers: {
    Authorization: `Bearer ${sessionStorage.getItem("accessToken")}`,
    "Content-Type": "application/json",
  },
});

// api 요청 인터셉터
api.interceptors.request.use((config) => {
  config.headers.Authorization = `Bearer ${sessionStorage.getItem(
    "accessToken"
  )}`;
  return config;
});

// api 응답 인터셉터
api.interceptors.response.use(
  // 성공 응답일 때,
  (response) => {
    return response;
  },

  // 실패 응답일 때,
  async (error) => {
    // A) 토큰 만료 이슈인 경우
    if (error.response.data?.message.includes("만료")) {
      // a) 갱신 요청
      const { data, status } = await axios.post(baseURL + `/member/refresh`, {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem("accessToken")}`,
        },
      });
      if (status === 201) {
        localStorage.setItem("accessToken", data.accessToken);

        // 헤더 변경 후 다시 쏘기
        const originalRequest = error.config;
        axios.defaults.headers.common.Authorization = `Bearer ${sessionStorage.getItem(
          "accessToken"
        )}`;
        originalRequest.headers.Authorization = `Bearer ${localStorage.getItem(
          "accessToken"
        )}`;

        return await axios(originalRequest);
      }
    }
    // B) 토큰 이슈 아닌 경우 및 refreshToken 만료 이슈
    return Promise.reject(error);
  }
);
