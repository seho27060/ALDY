import "./App.css";
import { Routes, Route } from "react-router-dom";
import MainPage from "./pages/MainPage.js";
import Changepw from "./pages/auth/Changepw";
import Login from "./pages/auth/Login";
import Mypage from "./pages/auth/Mypage";
import Signup from "./pages/auth/Signup";
import StudyCreate from "./pages/study/StudyCreate";
import StudyDetail from "./pages/study/StudyDetail";
import StudyList from "./pages/study/StudyList";
import StudyManage from "./pages/study/StudyManage";
import StudySelect from "./pages/study/StudySelect";
import CodeCorrect from "./pages/code/CodeCorrect";
import CodeReview from "./pages/code/CodeReview";
import CodeReviewList from "./pages/code/CodeReviewList";
import AldyNav from "./components/AldyNav";

function App() {
  return (
    <div className="App">
      <AldyNav />
      <Routes>
        <Route element={<MainPage />} path="/"></Route>
        <Route element={<Login />} path="/login"></Route>
        <Route element={<Signup />} path="/signup"></Route>
        <Route element={<Mypage />} path="/mypage"></Route>
        <Route element={<Changepw />} path="/changepw"></Route>
        <Route element={<StudyList />} path="/study/list"></Route>
        <Route element={<StudyCreate />} path="/study/create"></Route>
        <Route element={<StudyDetail />} path="/study/detail/:id"></Route>
        <Route element={<StudyManage />} path="/study/manage/:id"></Route>
        <Route element={<StudySelect />} path="/study/select"></Route>
        <Route element={<CodeReviewList />} path="/review/list"></Route>
        <Route element={<CodeCorrect />} path="/correct/:id"></Route>
        <Route element={<CodeReview />} path="/review"></Route>
      </Routes>
    </div>
  );
}

export default App;
