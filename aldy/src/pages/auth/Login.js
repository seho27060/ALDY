import "./Login.css";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { FcLike } from "react-icons/fc";

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
  const navigate = useNavigate();

  const navigateSignup = () => {
    navigate("/signup");
  };

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
          <div className="login-page-join">
            <div>아직 계정이 없으신가요? </div>
            <div className="login-page-link" onClick={navigateSignup}>
              <FcLike />
              회원가입 하러 가기
              <FcLike />
            </div>
          </div>
        </section>
        <section className="login-page-right">
          <div className="login-page-right-title">✨Welcome to Aldy✨</div>
          <div className="login-page-right-text">
            if (not a member?) '
            {
              <button className="join-btn" onClick={navigateSignup}>
                JOIN US
              </button>
            }
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
