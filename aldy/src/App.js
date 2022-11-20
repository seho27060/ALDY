import "./App.css";
import { Routes, Route, useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import MainPage from "./pages/MainPage.js";
import Changepw from "./pages/auth/Changepw";
import Login from "./pages/auth/Login";
import Mypage from "./pages/auth/Mypage";
import Signup from "./pages/auth/Signup";
import Userinfo from "./pages/auth/Userinfo";
import StudyCreate from "./pages/study/StudyCreate";
import StudyDetail from "./pages/study/StudyDetail";
import StudyList from "./pages/study/StudyList";
import StudyManage from "./pages/study/StudyManage";
import StudySelect from "./pages/study/StudySelect";
import CodeCorrect from "./pages/code/CodeCorrect";
import CodeReview from "./pages/code/CodeReview";
import CodeReviewList from "./pages/code/CodeReviewList";
import AldyNav from "./components/AldyNav";
import AldyFooter from "./components/AldyFooter";
import MoveTopBtn from "./components/MoveTopBtn";
import PageNotFound from "./pages/PageNotFound";
import { useRecoilState } from "recoil";
import { isFooter, isNav } from "../src/store/states";

function App() {
  const [footer] = useRecoilState(isFooter);
  const [nav] = useRecoilState(isNav);

  const params = useParams();
  console.log(params, "네브");

  return (
    <div className="App">
      {nav ? <AldyNav /> : null}
      <Routes>
        <Route element={<MainPage />} path="/"></Route>
        <Route element={<Login />} path="/login"></Route>
        <Route element={<Signup />} path="/signup"></Route>
        <Route element={<Mypage />} path="/mypage"></Route>
        <Route element={<Changepw />} path="/changepw"></Route>
        <Route element={<Userinfo />} path="/userinfo"></Route>
        <Route element={<StudyList />} path="/study/list"></Route>
        <Route element={<StudyCreate />} path="/study/create"></Route>
        <Route element={<StudyDetail />} path="/study/detail/:id"></Route>
        <Route element={<StudyManage />} path="/study/manage/:id"></Route>
        <Route element={<StudySelect />} path="/study/select"></Route>
        <Route element={<CodeReviewList />} path="/review/list"></Route>
        <Route element={<CodeCorrect />} path="/correct"></Route>
        <Route element={<CodeReview />} path="/review"></Route>
        <Route element={<PageNotFound />} path="*"></Route>
      </Routes>
      <MoveTopBtn />
      {/* {footer ? <AldyFooter /> : null} */}
    </div>
  );
}

export default App;
