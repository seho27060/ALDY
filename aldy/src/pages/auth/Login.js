import "./Login.css";
import { useNavigate } from "react-router-dom";
import { useRef, useState } from "react";
import styled from "styled-components";
import { FcLike } from "react-icons/fc";
import { login } from "../../api/auth";
import { useRecoilState } from "recoil";
import { isLoggedIn, userName } from "../../store/states";

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
  const idInput = useRef(null);
  const passwordInput = useRef(null);
  const [credentials, setCredentials] = useState({
    baekjoonId: "",
    password: "",
  });
  const [logged, setLogged] = useRecoilState(isLoggedIn);
  const [username, setUsername] = useRecoilState(userName);
  const navigateSignup = () => {
    navigate("/signup");
  };
  const navigateMain = () => {
    navigate("/");
  };

  return (
    <main className="login-page-main">
      <div className="login-page-bg">
        <section className="login-page-left">
          <div className="nnnnnn">Login</div>
          <div>
            <div className="form-title">
              <div>아이디</div>
              <div className="form-title-id">
                <input
                  placeholder="아이디를 입력해주세요."
                  ref={idInput}
                ></input>
              </div>
            </div>
            <div className="form-title">
              <div>비밀번호</div>
              <input
                type="password"
                placeholder="비밀번호를 입력해주세요."
                ref={passwordInput}
              ></input>
            </div>
            <div className="login-submit-btn">
              <RedButton
                onClick={() => {
                  setCredentials(
                    (credentials.baekjoonId = idInput.current.value)
                  );
                  setCredentials(
                    (credentials.password = passwordInput.current.value)
                  );
                  console.log(credentials);
                  login(credentials)
                    .then((res) => {
                      sessionStorage.setItem(
                        "accessToken",
                        res.data.accessToken
                      );
                      sessionStorage.setItem(
                        "refreshToken",
                        res.data.refreshToken
                      );
                      setLogged(true);
                      sessionStorage.setItem("userName", idInput.current.value);
                      setUsername(idInput.current.value);
                      navigateMain();
                    })
                    .catch((err) => {
                      alert("로그인에 실패하였습니다.");
                      window.location.reload(); //새로고침
                    });
                }}
              >
                Log In
              </RedButton>
            </div>
          </div>
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
