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
        <section className="signup-page-left">
          <div className="nnnnnn">Sign Up</div>
          <form>
            <div className="form-title">
              <div>백준 아이디</div>
              <div className="form-title-id">
                <input placeholder="백준 아이디를 입력해주세요."></input>
                <RedButton>인증하기</RedButton>
              </div>
            </div>
            <div className="form-title">
              <div>비밀번호</div>
              <input placeholder="비밀번호를 입력해주세요."></input>
            </div>
            <div className="form-title">
              <div>비밀번호 확인</div>
              <input placeholder="비밀번호를 다시 입력해주세요."></input>
            </div>
            <div className="signup-submit-btn">
              <RedButton>제출하기</RedButton>
            </div>
          </form>
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
