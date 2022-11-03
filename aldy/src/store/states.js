import { atom } from "recoil";

export const isLoggedIn = atom({
  key: "isLoggedIn",
  default: false,
});

export const userName = atom({
  key: "userName",
  default: "",
})
