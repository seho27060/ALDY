import "./Signup.css";
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

const Signup = () => {
  return (
    <main className="signup-page-main">
      <div className="signup-page-bg">
        <section>
          <div className="left-title">Sign Up</div>
          <div>
            <div>아이디</div>
            <input></input>
            <RedButton>인증하기</RedButton>
          </div>
          <div>
            <div>비밀번호</div>
            <input></input>
          </div>
          <div>
            <div>비밀번호 확인</div>
            <input></input>
          </div>
        </section>
        <section className="signup-page-right">
          <div className="signup-page-right-title">✨Welcome to Aldy✨</div>
          <div className="signup-page-right-text">
            Aldy와 함께 알고리즘 스터디를 키워보세요!
          </div>
          <img
            src={process.env.PUBLIC_URL + "/signup_dinosaur.png"}
            alt=""
          ></img>
        </section>
      </div>
    </main>
  );
};

export default Signup;
