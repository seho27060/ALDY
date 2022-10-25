import "./MainPage.css";
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
  border: none;
  outline: none;
  color: red;
  font-weight: bold;
  transition: transform 30ms ease-in;
`;

const MainPage = () => {
  return (
    <main>
      <section className="main-page-banner">
        <img src={process.env.PUBLIC_URL + "/MainDinosaur.png"} alt=""></img>
        <br></br>
        <br></br>
        <div>코드리뷰를 통해 공룡 키우기</div>
        <h1> ALDY</h1>
        <div>
          <div>✨ 스터디 레벨에 따라 성장하는 공룡 ✨</div>
          <div>스터디원들과 함께 공룡을 키워봐요!</div>
        </div>
        <RedButton>스터디 생성하기</RedButton>
      </section>
      <section className="main-page-description">
        <div>ALDY만의 코드리뷰 이용하기</div>
        <div>
          <p>
            <span>문제풀이 과정을</span>
            <span>4단계로 세분화!</span>
          </p>
          <WhiteButton>회원가입 하러가기</WhiteButton>
        </div>
      </section>
    </main>
  );
};

export default MainPage;
