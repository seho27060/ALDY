import "./MainPage.css";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import React, { useEffect } from "react";
import AOS from "aos";
import "aos/dist/aos.css";
import { getUserInfo } from "../api/user";
import { useRecoilState } from "recoil";
import { isLoggedIn } from "../store/states";

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
  width: 200px;
  border-radius: 8px;
  background-color: white;
  border: 2px solid red;
  outline: none;
  color: red;
  font-weight: bold;
  transition: transform 30ms ease-in;
  font-size: 20px;
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
  return (
    <main>
      <section className="main-page-banner">
        <img src={process.env.PUBLIC_URL + "/MainDinosaur.png"} alt=""></img>
        <br></br>
        <br></br>
        <div className="main-page-banner-text">코드리뷰를 통해 공룡 키우기</div>
        <h1>ALDY</h1>
        <div className="board">
          <div className="board-image">
            <span>
              <img
                src={process.env.PUBLIC_URL + "/blackboard.png"}
                alt=""
              ></img>
            </span>
          </div>
          <div className="board-title">
            ✨ 스터디 레벨에 따라 성장하는 공룡 ✨
          </div>
          <div className="board-text">스터디원들과 함께 공룡을 키워봐요!</div>
        </div>
        <RedButton>스터디 생성하기</RedButton>
      </section>
      <section className="main-page-description">
        <div className="section1-left" data-aos="fade-right">
          <div className="main-page-description-title">
            ALDY만의 코드리뷰 이용하기
          </div>
          <div className="main-page-description-text">
            <p>
              <span>문제풀이 과정을 </span>
              <span className="main-page-highlight">4단계로 세분화!</span>
            </p>
            <img
              src={process.env.PUBLIC_URL + "/codereview4.png"}
              alt=""
              width="500px"
            ></img>
          </div>
        </div>
        <div className="section1-right" data-aos="fade-left">
          <div>
            <WhiteButton onClick={navigateSignUp}>
              회원가입 하러가기
            </WhiteButton>
          </div>
        </div>
      </section>
      <section className="aldy_step_info">
        <div className="aldy_step_info_title">Game + Education </div>
        <img
          src={process.env.PUBLIC_URL + "/joystick.png"}
          alt=""
          width="150px"
        ></img>
        <div className="aldy_step_info_text">
          알디는 게이미피케이션을 활용하여 스터디를 진행합니다.
        </div>
      </section>
      <div className="main-page-description-title">ALDY의 메인 캐릭터 소개</div>
      <section className="main-page-description">
        <div className="section1-left" data-aos="fade-up-right">
          <div className="main-page-description-text">
            <p>
              <span>1단계 </span>
              <span className="main-page-highlight">알에서 깬 알디</span>
            </p>
            <img
              src={process.env.PUBLIC_URL + "/step1.gif"}
              alt=""
              width="300px"
            ></img>
          </div>
        </div>
        <div className="section1-right" data-aos="fade-up-left">
          <div className="main-page-description-title"></div>
          <div className="main-page-description-text">
            <p>
              <span>2단계 </span>
              <span className="main-page-highlight">아기 공룡 알디</span>
            </p>
            <img
              src={process.env.PUBLIC_URL + "/step2.gif"}
              alt=""
              width="300px"
            ></img>
          </div>
        </div>
      </section>
      <section className="main-page-description">
        <div className="section1-left" data-aos="fade-up-right">
          <div className="main-page-description-title"></div>
          <div className="main-page-description-text">
            <p>
              <span>3단계 </span>
              <span className="main-page-highlight">인사하는 알디</span>
            </p>
            <img
              src={process.env.PUBLIC_URL + "/step3.gif"}
              alt=""
              width="200px"
            ></img>
          </div>
        </div>
        <div className="section1-right" data-aos="fade-up-left">
          <div className="main-page-description-title"></div>
          <div className="main-page-description-text">
            <p>
              <span>4단계 </span>
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
      <div className="main-page-description-title">
        ALDY에 접속한지 오래되었을 경우
      </div>
      <section className="main-page-description">
        <div className="section1-left" data-aos="fade-up-right">
          <div className="main-page-description-text">
            <p>
              <span>소멸 직전 </span>
              <span className="main-page-highlight">울고있는 알디</span>
            </p>
            <img
              src={process.env.PUBLIC_URL + "/end1.gif"}
              alt=""
              width="300px"
            ></img>
          </div>
        </div>
        <div className="section1-right" data-aos="fade-up-left">
          <div className="main-page-description-title"></div>
          <div className="main-page-description-text">
            <p>
              <span>소멸 직후 </span>
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
    </main>
  );
};

export default MainPage;
