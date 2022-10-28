import "./Login.css";
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

const Login = () => {
  return (
    <main className="login-page-main">
      <div className="login-page-bg">
        <section className="login-page-left">
          <div className="nnnnnn">Login</div>
          <form>
            <div className="form-title">
              <div>아이디</div>
              <div className="form-title-id">
                <input placeholder="아이디를 입력해주세요."></input>
              </div>
            </div>
            <div className="form-title">
              <div>비밀번호</div>
              <input placeholder="비밀번호를 입력해주세요."></input>
            </div>
            <div className="login-submit-btn">
              <RedButton>Log In</RedButton>
            </div>
          </form>
          <div>
            <p>아직 계정이 없으신가요? </p>
            <p> 회원가입 하러 가기</p>
          </div>
        </section>
        <section className="login-page-right">
          <div className="login-page-right-title">✨Welcome to Aldy✨</div>
          <div className="login-page-right-text">
            if (not a member?)
            {<button>JOIN US</button>}
          </div>
          <img
            src={process.env.PUBLIC_URL + "/login_dinosaur.png"}
            alt=""
          ></img>
        </section>
      </div>
    </main>
  );
};

export default Login;
