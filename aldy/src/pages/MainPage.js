import "./MainPage.css";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import React, { useEffect, useState } from "react";
import AOS from "aos";
import "aos/dist/aos.css";
import { getUserInfo } from "../api/user";
import { useRecoilState } from "recoil";
import { isLoggedIn } from "../store/states";
import MoveBar from "../components/MobeBar";
import Lottie from "lottie-react";
import codeTyping from "../lotties/codeTyping.json";
import recommend from "../lotties/recommend.json";
import codingReview from "../lotties/codingReview.json";
import trafficLight from "../lotties/trafficLight.json";
import onlineStudy from "../lotties/onlineStudy.json";

const RedButton = styled.button`
  width: 170px;
  border-radius: 8px;
  background-color: red;
  border: none;
  outline: none;
  color: white;
  font-weight: bold;
  transition: transform 30ms ease-in;
`;

const WhiteButton = styled.button`
  width: 180px;
  border-radius: 8px;
  background-color: white;
  border: 2px solid red;
  outline: none;
  color: red;
  font-weight: bold;
  transition: transform 30ms ease-in;
  font-size: 24px;
  margin: 4px;
  font-family: "uhbeeBold";
  &:hover {
    background-color: red;
    color: white;
    transition: all 200ms ease-in;
  }
`;

const MainPage = () => {
  useEffect(() => {
    AOS.init();
    userInfoSession();
  });

  const [logged, setLogged] = useRecoilState(isLoggedIn);

  const userInfoSession = () => {
    if (logged) {
      console.log("메인페이지 로그인 됨");
      getUserInfo().then((res) => {
        console.log(res.data);
        sessionStorage.setItem("nickname", res.data.nickname);
        sessionStorage.setItem("tier", res.data.tier);
        sessionStorage.setItem("id", res.data.id);
      });
    } else {
      console.log("메인페이지 로그인 안됨");
    }
  };

  const navigate = useNavigate();

  const navigateSignUp = () => {
    navigate("/signup");
  };

  const navigateStudy = () => {
    navigate("study/list");
  };

  const navigateMyPage = () => {
    navigate("mypage");
  };
  const [tutorialLoginShow, setTutorialLoginShow] = useState(true);
  const [tutorialStudyShow, setTutorialStudyShow] = useState(false);
  const [tutorialCodeReviewShow, setTutorialCodeReviewShow] = useState(false);
  const [tutorialPenaltyShow, setTutorialPenaltyShow] = useState(false);

  const TutorialChangeLogin = () => {
    setTutorialLoginShow((prev) => !prev);
  };

  const TutorialChangeStudy = () => {
    setTutorialStudyShow((prev) => !prev);
  };

  const TutorialChangeCodeReview = () => {
    setTutorialCodeReviewShow((prev) => !prev);
  };

  const TutorialChangePenalty = () => {
    setTutorialPenaltyShow((prev) => !prev);
  };

  return (
    <main>
      <MoveBar></MoveBar>
      <section className="main-page-banner">
        <img src={process.env.PUBLIC_URL + "/MainDinosaur.png"} alt=""></img>
        <br></br>
        <br></br>
        <br></br>
        <div className="main-page-banner-text">코드리뷰를 통해 공룡 키우기</div>
        <br></br>
        <h1>ALDY</h1>
        <div className="board" style={{ margin: "30px" }}>
          <div className="board-image">
            <span>
              <img
                src={process.env.PUBLIC_URL + "/mainboardLL.png"}
                alt=""
              ></img>
            </span>
          </div>
          <div className="board-title">
            ✨ 스터디 레벨에 따라 성장하는 공룡 ✨
          </div>
          <div className="board-text">
            <div className="board-text">스터디원들과 함께 공룡을 키워봐요!</div>
            {logged ? (
              <WhiteButton onClick={navigateStudy}>스터디하러 가기</WhiteButton>
            ) : (
              <WhiteButton onClick={navigateSignUp}>
                회원가입 하러가기
              </WhiteButton>
            )}
          </div>
        </div>
      </section>
      <section className="main-page-description">
        <div className="section1-left">
          <div
            className="main-page-description-title"
            data-aos="fade-up"
            // data-aos-anchor-placement="bottom-center"
          >
            ALDY만의 코드리뷰 이용하기
          </div>
          <div className="main-page-description-text" data-aos="fade-up">
            <p>
              <span>문제풀이 과정을 </span>
              <span className="main-page-highlight">3단계로 세분화!</span>
            </p>
            <img
              src={process.env.PUBLIC_URL + "/mainReview3.gif"}
              alt=""
              width="500px"
              data-aos="fade-right"
            ></img>
            <Lottie
              animationData={codeTyping}
              style={{ width: "500px" }}
            ></Lottie>
          </div>
        </div>
        <div className="section1-right" data-aos="fade-left">
          <div>
            <div>
              <div
                className="main-page-description-title"
                data-aos="fade-up"
                // data-aos-anchor-placement="bottom-center"
              >
                {/* <Lottie
                  animationData={recommend}
                  style={{ width: "400px" }}
                  data-aos="fade-right"
                ></Lottie> */}
                나만을 위한 문제 추천
              </div>
              <div className="main-page-description-text" data-aos="fade-up">
                <p>
                  <span>최근 푼 문제를 바탕으로 유사한 유형의 </span>
                  <span className="main-page-highlight">안 푼 문제 추천!</span>
                </p>
                <img
                  src={process.env.PUBLIC_URL + "/mainRecommend.gif"}
                  alt=""
                  width="500px"
                  style={{ paddingBottom: "30px" }}
                  data-aos="fade-left"
                ></img>
              </div>
            </div>
            {/* <WhiteButton onClick={navigateMyPage} data-aos="fade-up">
              추천 문제 풀기
            </WhiteButton> */}
          </div>
        </div>
      </section>
      <section className="aldy_step_info">
        <div className="aldy_step_info_title">Game + Education </div>
        <img
          src={process.env.PUBLIC_URL + "/joystick.gif"}
          alt=""
          style={{ width: "150px", margin: "25px" }}
        ></img>
        <div className="aldy_step_info_text">
          알디는 게이미피케이션을 활용하여 스터디를 진행합니다.
        </div>
      </section>

      <div className="main-page-green-title">
        <div
          className="main-page-green-title"
          style={{ paddingTop: "150px" }}
          data-aos="fade-up"
        >
          ALDY의 메인 캐릭터 소개
        </div>
      </div>
      <section
        className="main-page-description-aldylist"
        id="main-page-description-aldylist"
      >
        <div className="section1-left" data-aos="fade-up">
          <div className="main-page-description-text">
            <p>
              <span style={{ color: "#373737" }}>1단계 </span>
              <span className="main-page-highlight">알에서 깬 알디</span>
            </p>
            <img
              src={process.env.PUBLIC_URL + "/step1.gif"}
              alt=""
              width="300px"
            ></img>
          </div>
        </div>
        <div className="section1-right" data-aos="fade-up">
          <div className="main-page-description-title"></div>
          <div className="main-page-description-text">
            <p>
              <span style={{ color: "#373737" }}>2단계 </span>
              <span className="main-page-highlight">아기 공룡 알디</span>
            </p>
            <img
              src={process.env.PUBLIC_URL + "/step2.gif"}
              alt=""
              width="300px"
            ></img>
          </div>
        </div>
        <div className="section1-left" data-aos="fade-up">
          <div className="main-page-description-title"></div>
          <div className="main-page-description-text">
            <p>
              <span style={{ color: "#373737" }}>3단계 </span>
              <span className="main-page-highlight">인사하는 알디</span>
            </p>
            <img
              src={process.env.PUBLIC_URL + "/step3.gif"}
              alt=""
              width="200px"
              style={{ paddingTop: "41px" }}
            ></img>
          </div>
        </div>
        <div className="section1-right" data-aos="fade-up">
          <div className="main-page-description-title"></div>
          <div className="main-page-description-text">
            <p>
              <span style={{ color: "#373737" }}>4단계 </span>
              <span className="main-page-highlight">불 뿜는 알디</span>
            </p>
            <img
              src={process.env.PUBLIC_URL + "/step4.gif"}
              alt=""
              width="300px"
            ></img>
          </div>
        </div>
      </section>
      <div className="main-page-green-title" style={{ paddingTop: "70px" }}>
        ALDY에 접속한지 오래되었을 경우 😥
      </div>
      <section className="main-page-description-aldylist">
        <div className="section1-left" data-aos="fade-up">
          <div className="main-page-description-text">
            <p>
              <span style={{ color: "#373737" }}>소멸 직전 </span>
              <span className="main-page-highlight">울고있는 알디</span>
            </p>
            <img
              src={process.env.PUBLIC_URL + "/end1.gif"}
              alt=""
              width="300px"
            ></img>
          </div>
        </div>
        <div className="section1-right" data-aos="fade-up">
          <div className="main-page-description-title"></div>
          <div className="main-page-description-text">
            <p>
              <span style={{ color: "#373737" }}>소멸 직후 </span>
              <span className="main-page-highlight">화석이 된 알디</span>
            </p>
            <img
              src={process.env.PUBLIC_URL + "/end2.gif"}
              alt=""
              width="300px"
            ></img>
          </div>
        </div>
      </section>
      <section className="main-page-study-description">
        {/* 이곳에 스터디 설명을 작성하세요 */}
        알디 스터디 규칙 🎈
        <div className="main-page-study-description-flex">
          <div className="section1-left" data-aos="fade-up">
            <div className="main-page-description-text">
              <p>
                <span style={{ fontSize: "25px", margin: "60px" }}>
                  스터디 설명
                </span>
              </p>
              <Lottie
                animationData={trafficLight}
                style={{ width: "300px" }}
              ></Lottie>
            </div>
          </div>

          <div style={{ marginTop: "100px" }}>
            🚨 매일 밤 12시, 정해진 날짜까지 문제를 안 풀었거나 코드 리뷰를
            안했다면 경고가 쌓여요!
            <br></br>
            🚨 경고를 3회 받을 경우 스터디에서 강제 퇴장되니까 조심하세요!
            <br></br>
            <img
              src={process.env.PUBLIC_URL + "/aldyhead3.png"}
              alt=""
              width="80px"
              style={{ margin: "10px" }}
            ></img>
            <img
              src={process.env.PUBLIC_URL + "/aldyhead9.png"}
              alt=""
              width="80px"
              style={{ margin: "10px" }}
            ></img>
            <img
              src={process.env.PUBLIC_URL + "/aldyhead6.png"}
              alt=""
              width="80px"
              style={{ margin: "10px" }}
            ></img>
          </div>
        </div>
      </section>
      <section className="main-page-description-codereview">
        <div className="section1-left" data-aos="fade-up">
          <div className="main-page-description-text">
            <p>
              <span>코드리뷰 설명</span>
            </p>
            <Lottie
              animationData={codingReview}
              style={{ width: "500px" }}
            ></Lottie>
          </div>
        </div>
        <div className="section1-right" data-aos="fade-left">
          <div className="main-page-description-title"></div>
          <div className="main-page-description-text">
            <p>
              <span>알디의 3단계 </span>
              <span className="main-page-highlight">코드 리뷰</span>
            </p>
            <Lottie
              animationData={onlineStudy}
              style={{ width: "500px" }}
              data-aos="fade-right"
            ></Lottie>
          </div>
        </div>
      </section>

      <section className="main-page-tutorial-description">
        <div className="main-page-tutorial-title">
          '<span>알고리즘 스터디</span>'을 위한 <span>알디</span>, 처음이신가요?
        </div>

        <div className="tutorial-table">
          {" "}
          {/* 이용 방법표를 작성할거야 */}
          <div style={{ fontSize: "25px", marginBottom: "20px" }}>
            <img
              src={process.env.PUBLIC_URL + "/aldyhead7.png"}
              alt=""
              width="50px"
              style={{ margin: "10px" }}
            ></img>
            ALDY 이용 방법{" "}
            <img
              src={process.env.PUBLIC_URL + "/aldyhead2.png"}
              alt=""
              width="50px"
              style={{ margin: "5px" }}
            ></img>
          </div>
          <div>
            {/* 이용방법 표{" "} */}
            <div onClick={TutorialChangeLogin} className="TutorialChangeLogin">
              1. 회원 가입하기🦕
            </div>
            {tutorialLoginShow ? (
              <div className="tutorial-content">
                <div>
                  저희 사이트는 백준과 연동하는 회원가입이 필수예요! 🙏
                  <br></br>
                  <img
                    src={process.env.PUBLIC_URL + "/mainSignup.gif"}
                    alt=""
                    width="550px"
                    style={{ margin: "10px" }}
                  ></img>
                  <br></br>
                  😘 회원가입을 완료하셨다면, 스터디에 가입해보세요!
                </div>
              </div>
            ) : null}
            <div onClick={TutorialChangeStudy} className="TutorialChangeLogin">
              2. 스터디 생성, 가입하기📚
            </div>
            {tutorialStudyShow ? (
              <div className="tutorial-content">
                회원가입을 완료하셨다면, 스터디에 가입해보세요! 😘
                <br></br>
                <br></br>
                마음에 드는 스터디가 없으신가요? 그렇다면 스터디를 만들어보세요!
                <br></br>
                <img
                  src={process.env.PUBLIC_URL + "/mainStudyCreate.gif"}
                  alt=""
                  width="350px"
                  style={{ margin: "10px" }}
                ></img>
                <br></br>
                스터디에 대한 설명과 가입제한 백준 티어, 인원수를 설정할 수
                있어요!
                <br></br>
                <br></br>
                📅📅
                <br></br>
                스터디장이시라면 달력에서 요일을 눌러 문제 선정이 가능해요!
                <br></br>
                팀원들이 안 푼 문제를 유형별, 티어별로 선정할 수 있어요! 👍🏼
              </div>
            ) : null}
            <div
              onClick={TutorialChangeCodeReview}
              className="TutorialChangeLogin"
            >
              3. 코드 리뷰 하기📑
            </div>
            {tutorialCodeReviewShow ? (
              <div className="tutorial-content">
                <div>
                  문제가 선정된 요일을 누르면 코드 리뷰를 할 수 있어요!
                  <br></br>
                  코드 리뷰는 총 3단계로 나누어져 있어요! 😎
                  <br></br>
                  <br></br>
                  <img
                    src={process.env.PUBLIC_URL + "/mainReview1.gif"}
                    alt=""
                    width="350px"
                    style={{ margin: "10px" }}
                  ></img>
                  <br></br>
                  1️⃣ 1단계는 백준에서 푼 문제를 그대로 제출하는 단계예요!
                  회원님이 문제를 풀었는지 체크하는 단계이기도 해요!
                  <br></br>
                  <br></br>
                  <img
                    src={process.env.PUBLIC_URL + "/mainReview2.gif"}
                    alt=""
                    width="350px"
                    style={{ margin: "10px" }}
                  ></img>
                  <br></br>
                  2️⃣ 2단계는 다른 스터디원들에게 코드 리뷰 요청을 보내기 전
                  단계예요! <br></br>주석을 활용해서 코드 리뷰를 받고 싶은 부분,
                  코드에 대한 설명을 적으면 돼요!
                  <br></br>
                  <br></br>
                  <img
                    src={process.env.PUBLIC_URL + "/mainReview3.gif"}
                    alt=""
                    width="350px"
                    style={{ margin: "10px" }}
                  ></img>
                  <br></br>
                  3️⃣ 3단계는 코드 리뷰를 받은 뒤 자신의 최종 코드를 제출하는
                  단계예요! 알디에서는 이 세 단계를 거쳐야 코드 리뷰가 다
                  되었다고 생각해요!
                  <br></br>
                  <br></br>
                  스터디 가입, 코드 리뷰 요청, 응답 시 이메일 알림이 가요!📩
                  매번 사이트에 들려서 확인할 필요 없어요!
                </div>
              </div>
            ) : null}
            <div
              onClick={TutorialChangePenalty}
              className="TutorialChangeLogin"
            >
              4. 스터디 페널티 조심하기🚦
            </div>
            {tutorialPenaltyShow ? (
              <div className="tutorial-content">
                <div>
                  🚨 매일 밤 12시, 정해진 날짜까지 문제를 안 풀었거나 코드
                  리뷰를 안했다면 경고가 쌓여요!
                  <br></br>
                  🚨 경고를 3회 받을 경우 스터디에서 강제 퇴장되니까 조심하세요!
                  <br></br>
                </div>
              </div>
            ) : null}
          </div>
        </div>
      </section>
    </main>
  );
};

export default MainPage;
