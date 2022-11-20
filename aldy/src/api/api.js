import axios from "axios";

const baseURL = process.env.REACT_APP_API_URL + "/api";

export const api = axios.create({
  baseURL: baseURL
});

// api 요청 인터셉터
api.interceptors.request.use((config) => {
  config.headers.Authorization = `Bearer ${sessionStorage.getItem('accessToken')}`;
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
    // 1) 토큰 만료 이슈인 경우
    console.log('api error', error)
    if (
      error.response.status === 401 &&
      error.response.data.code === "ACCESSTOKEN_EXPIRED"
    ) {
      // a) 갱신 요청
      const body = {refreshToken : sessionStorage.getItem('refreshToken')}
      const res = await axios.post(baseURL + '/auth/refresh',
      body);
      if (res.status === 200) {
        sessionStorage.setItem("accessToken", res.data.accessToken);
        console.log('토큰이 갱신되었습니다.') // 나중에 지울것

        // 원래 요청에서 토큰 변경 후 다시 요청하기
        const originalRequest = error.config;
        api.defaults.headers.common.Authorization = `Bearer ${sessionStorage.getItem('accessToken')}`;
        originalRequest.headers.Authorization = `Bearer ${sessionStorage.getItem('accessToken')}`;
        return api({
          ...originalRequest,
          headers: {
            ...originalRequest.headers, Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`
          }
        });
      }
    }
    // 2) 토큰 이슈 아닌 경우 및 refreshToken 만료 이슈
    return Promise.reject(error);
  }
);
