import { atom } from "recoil";

export const isLoggedIn = atom({
  key: "isLoggedIn",
  default: sessionStorage.getItem("accessToken") && true,
});

export const userName = atom({
  key: "userName",
  default: sessionStorage.getItem("userName"),
});

export const correctCode = atom({
  key: "correctCode",
  default: sessionStorage.getItem("correctCode"),
});

export const recoilStep = atom({
  key: "recoilStep",
  default: 1,
});

export const recoilMyCode = atom({
  key: "recoilMyCode",
  default: sessionStorage.getItem("myCode"),
});

export const recoilYourCode = atom({
  key: "recoilYourCode",
  default: sessionStorage.getItem("yourCode"),
});

export const recoilLeaderBaekjoonId = atom({
  key: "recoilLeaderBaekjoonId",
  default: null,
});

export const isFooter = atom({
  key: "isFooter",
  default: true,
});

export const isNav = atom({
  key: "isNav",
  default: true,
});
