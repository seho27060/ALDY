import "./Login.css";
import { useNavigate } from "react-router-dom";
import { useRef, useState } from "react";
import styled from "styled-components";
import { FcLike } from "react-icons/fc";
import { login } from "../../api/auth";
import { useRecoilState } from "recoil";
import { isLoggedIn, userName } from "../../store/states";
import AlertRefreshModal from "../../components/AlertRefreshModal";

const WhiteRedButton = styled.button`
  width: 120px;
  border-radius: 8px;
  background-color: white;
  outline: none;
  border: 2px solid red;
  color: red;
  font-family: "GmarketSansMedium";
  font-weight: bold;
  font-size: 15px;
  padding: 5px 0px 3px 0px;
  transition: all 200ms ease-in;
  user-select: none;
  &:hover {
    background-color: red;
    color: white;
    transition: all 200ms ease-in;
  }
`;

const RedButton = styled.button`
  width: 120px;
  height: 30px;
  border-radius: 8px;
  background-color: red;
  border: none;
  outline: none;
  color: white;
  font-weight: bold;
  transition: transform 30ms ease-in;
  font-size: 15px;
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

  const [message, setMessage] = useState("");
  const [alertRefreshModalShow, setAlertRefreshModalShow] = useState(false);

  const navigateSignup = () => {
    navigate("/signup");
  };
  const navigateMain = () => {
    navigate("/");
  };

  const onKeypress = (e) => {
    if (e.key === "Enter") {
      onSubmit();
    }
  };

  const onSubmit = () => {
    setCredentials((credentials.baekjoonId = idInput.current.value));
    setCredentials((credentials.password = passwordInput.current.value));
    console.log(credentials);
    login(credentials)
      .then((res) => {
        sessionStorage.setItem("accessToken", res.data.accessToken);
        sessionStorage.setItem("refreshToken", res.data.refreshToken);
        setLogged(true);
        sessionStorage.setItem("userName", idInput.current.value);
        setUsername(idInput.current.value);
        navigateMain();
      })
      .catch((err) => {
        // alert("로그인에 실패하였습니다.");
        // window.location.reload(); //새로고침
        setMessage("로그인에 실패하였습니다.");
        setAlertRefreshModalShow(true); //새로고침
      });
  };

  return (
    <main className="login-page-main">
      <AlertRefreshModal
        show={alertRefreshModalShow}
        onHide={() => setAlertRefreshModalShow(false)}
        message={message}
      />
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
                onKeyPress={onKeypress}
              ></input>
            </div>
            <div className="login-submit-btn">
              <RedButton onClick={onSubmit}>LOGIN</RedButton>
            </div>
          </div>
          <div className="login-page-join">
            <div>아직 계정이 없으신가요? </div>
            <div className="login-page-link" onClick={navigateSignup}>
              <div class="box-vibration">
                <FcLike />
              </div>
              &#32;회원가입 하러 가기&#32;
              <div class="box-vibration">
                &#32;
                <FcLike />
                &#32;
              </div>
            </div>
          </div>
        </section>
        <section className="login-page-right">
          <div className="login-page-right-title">✨Welcome to Aldy✨</div>
          <div className="login-page-right-sub-title">
            알디에 오신 것을 환영합니다 <span>♡</span>ᐡ・_・ᐡ<span>♡</span>
          </div>
          <div className="login-page-right-text">
            <div className="login-page-right-item">
              if (not&#32; &#32;a&#32; &#32;member?){" "}
            </div>
            <div
              className="login-page-right-item"
              style={{ textAlign: "left" }}
            >
              &#123;
            </div>
            <WhiteRedButton onClick={navigateSignup}>
              <div style={{ userSelect: "none" }}>JOIN US</div>
            </WhiteRedButton>
            <div
              className="login-page-right-item"
              style={{ textAlign: "left" }}
            >
              &#125;
            </div>
          </div>
          <img
            className="login_img"
            src={process.env.PUBLIC_URL + "/keyboardAldy.gif"}
            alt=""
          ></img>
        </section>
      </div>
    </main>
  );
};

export default Login;
