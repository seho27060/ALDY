import "./MainPage.css";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

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
  width: 170px;
  border-radius: 8px;
  background-color: white;
  border: 2px solid red;
  outline: none;
  color: red;
  font-weight: bold;
  transition: transform 30ms ease-in;
`;

const MainPage = () => {
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
        <div className="main-page-description-title">
          ALDY만의 코드리뷰 이용하기
        </div>
        <div>
          <p>
            <span>문제풀이 과정을 </span>
            <span className="main-page-highlight">4단계로 세분화!</span>
          </p>
          <WhiteButton onClick={navigateSignUp}>회원가입 하러가기</WhiteButton>
        </div>
      </section>
    </main>
  );
};

export default MainPage;
