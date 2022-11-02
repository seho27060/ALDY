import axios from "axios";

export const baseURL = process.env.REACT_APP_API_URL + "/api";

export const login = (credentials) => {
  axios.post(baseURL + "/auth/login", credentials);
};

export const logout = () => {
  sessionStorage.clear();
};

export const signup = (credentials) => {
  axios.post(baseURL + "/auth/join", credentials);
};
