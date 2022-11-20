import "./Signup.css";
import React, { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import Modal from "react-bootstrap/Modal";
import styled from "styled-components";
import {
  join,
  emailValid,
  interLock,
  nicknameValid,
  baekjoonVerify,
} from "../../api/auth";
import AlertModal from "../../components/AlertModal";
import AlertRefreshModal from "../../components/AlertRefreshModal";
import { CopyToClipboard } from "react-copy-to-clipboard";
import { useRecoilState } from "recoil";
import { isNav } from "../../store/states";

const RedButtonSmall = styled.button`
  width: 100px;
  height: 32px;
  border-radius: 8px;
  background-color: red;
  border: none;
  outline: none;
  color: white;
  transition: transform 30ms ease-in;
  padding: 2px 0px 0px 0px;
  font-size: 15px;
  margin-top: 5px;
`;

const RedButton = styled.button`
  width: 160px;
  border-radius: 8px;
  background-color: red;
  border: 2px solid red;
  outline: none;
  color: white;
  font-weight: bold;
  transition: all 200ms ease-in;
  padding: 8px 0px 6px 0px;
  &:hover {
    background-color: white;
    border: 2px solid red;
    color: red;
    transition: all 200ms ease-in;
  }
`;

const YellowButton = styled.button`
  width: 50px;
  border-radius: 8px;
  background-color: lightgoldenrodyellow;
  border: none;
  outline: none;
  color: white;
  font-weight: bold;
  transition: transform 30ms ease-in;
  margin-left: 5px;
`;

const GrayButton = styled.button`
  width: 100px;
  height: 32px;
  border-radius: 8px;
  background-color: gray;
  border: none;
  outline: none;
  color: white;
  transition: transform 30ms ease-in;
  padding: 2px 0px 0px 0px;
  font-size: 15px;
  margin-top: 5px;
`;

const Signup = () => {
  const navigate = useNavigate();

  const navigateMain = () => {
    navigate("/");
  };
  function checkIt() {
    const email = emailInput.current.value;
    const exptext = /^[A-Za-z0-9_.-]+@[A-Za-z0-9-]+.[A-Za-z0-9-]+/;
    if (exptext.test(email) === false) {
      //이메일 형식이 알파벳+숫자@알파벳+숫자.알파벳+숫자 형식이 아닐경우
      return false;
    }
    return true;
  }

  const [nav, setNav] = useRecoilState(isNav);
  setNav(true);

  const [bojModalShow, setBojModalShow] = useState(false);
  const [bojValidationCode, setBojValidationCode] = useState("");
  const [sendId, setSendId] = useState({ baekjoonId: "" });
  // 유효성검사
  const [idChecked, setIdChecked] = useState(false);
  const [emailChecked, setEmailChecked] = useState(false);
  const [nicknameChecked, setNicknameChecked] = useState(false);

  // 모달
  const [message, setMessage] = useState("");
  const [alertModalShow, setAlertModalShow] = useState(false);
  const [alertRefreshModalShow, setAlertRefreshModalShow] = useState(false);

  const [credentials, setCredentials] = useState({
    baekjoonId: "",
    password: "",
    email: "",
    nickname: "",
  });

  const handleBojModalShow = (e) => {
    setBojModalShow((prev) => !prev);
  };

  const idInput = useRef(null);
  const passwordInput = useRef(null);
  const passwordCheckInput = useRef(null);
  const emailInput = useRef(null);
  const nicknameInput = useRef(null);

  const passwordDoubleCheck = () => {
    if (passwordInput.current.value === passwordCheckInput.current.value) {
      return true;
    }
    return false;
  };

  const allInputCheck = (credentials) => {
    if (idChecked && emailChecked && nicknameChecked) {
      join(credentials).then(() => {
        navigateMain();
      });
    } else {
      if (!idChecked) {
        // alert("아이디 인증을 해주세요.");
        setMessage("아이디 인증을 해주세요.");
        setAlertModalShow(true);
      }
      if (!emailChecked) {
        // alert("이메일 중복 확인을 해주세요.");
        setMessage("이메일 중복 확인을 해주세요.");
        setAlertModalShow(true);
      }
      if (!nicknameChecked) {
        // alert("닉네임 중복 확인을 해주세요.");
        setMessage("닉네임 중복 확인을 해주세요.");
        setAlertModalShow(true);
      }
    }
  };

  const solvedAc = () => {
    window.open("https://solved.ac/", "_blank");
  };

  return (
    <main className="signup-page-main">
      <AlertModal
        show={alertModalShow}
        onHide={() => {
          setAlertModalShow(false);
        }}
        message={message}
      />
      <AlertRefreshModal
        show={alertRefreshModalShow}
        onHide={() => setAlertRefreshModalShow(false)}
        message={message}
      />
      <Modal
        size="lg"
        show={bojModalShow}
        onHide={handleBojModalShow}
        backdrop="static"
      >
        <Modal.Body className="review-modal-body">
          <div className="review-modal-header">
            <div>
              <p className="beakjonn-modal-title">
                인증 코드 : <span>{bojValidationCode}</span>
                <CopyToClipboard text={bojValidationCode}>
                  <YellowButton className="CopyToClipboard-Btn">
                    <img
                      src={process.env.PUBLIC_URL + "/copyIcon.png"}
                      alt=""
                      style={{ width: "30px" }}
                    ></img>
                  </YellowButton>
                </CopyToClipboard>
              </p>
              <p style={{ fontSize: "16px", color: "#646464" }}>
                solved.ac 프로필 편집을 누르신 뒤, 자기소개 끝에 인증코드를
                추가해주세요.
              </p>
            </div>
            <div>
              <button
                className="review-modal-close-btn"
                onClick={() => {
                  handleBojModalShow();
                  window.location.reload();
                }}
                style={{ padding: "1px 0 0" }}
              >
                X
              </button>
            </div>
          </div>
          <div className="review-modal-content">
            <img
              src={process.env.PUBLIC_URL + "/solved.png"}
              alt=""
              style={{ width: "100%", marginBottom: "30px" }}
            ></img>
          </div>
          <div className="solved-btn">
            <RedButton onClick={solvedAc}>solved.ac로 이동</RedButton>
            <RedButton
              onClick={() => {
                interLock(idInput.current.value).then((res) => {
                  if (res.data.interlock === true) {
                    setIdChecked(true);
                    // alert("인증이 성공하였습니다.");
                    setMessage("인증이 성공하였습니다.");
                    setAlertModalShow(true);
                    handleBojModalShow(); //모달창 닫기
                  } else {
                    // alert("인증에 실패하였습니다. 다시 인증해주세요.");
                    // window.location.reload(); //새로고침
                    setMessage("인증에 실패하였습니다. 다시 인증해주세요.");
                    setAlertRefreshModalShow(true);
                  }
                });
              }}
            >
              인증완료 하기
            </RedButton>
          </div>
        </Modal.Body>
      </Modal>
      <div className="signup-page-bg">
        <section className="signup-page-left">
          <div className="sign-title">SIGN UP</div>
          <div className="sign-title-info">
            ✨ ALDY는 solved.ac를 연동하여 서비스를 제공하므로
            <br></br>solved.ac 회원만 사이트 이용이 가능합니다. ✨
          </div>
          <div>
            <div className="sign-form-title">
              <div className="study-create-form-info">solved.ac 아이디</div>
              <div className="form-title-id">
                <input
                  type="text"
                  name="id"
                  ref={idInput}
                  placeholder="solved.ac 아이디를 입력해주세요."
                  className="study-create-input"
                ></input>
                {!idChecked ? (
                  <RedButtonSmall
                    onClick={() => {
                      setSendId((sendId.baekjoonId = idInput.current.value));
                      console.log(idInput.current.value);
                      console.log(sendId);
                      baekjoonVerify(sendId)
                        .then((res) => {
                          setBojValidationCode(res.data.authString);
                          handleBojModalShow();
                          console.log(res.data.authString);
                        })
                        .catch((err) => {
                          console.log(err);
                          console.log(err.response.status);

                          err.response.status === 409
                            ? // alert("백준 회원이 아닙니다.");
                              // window.location.reload(); //새로고침
                              setMessage("이미 가입된 회원입니다.")
                            : setMessage("백준 회원이 아닙니다.");
                          setAlertRefreshModalShow(true);
                        });
                    }}
                  >
                    인증하기
                  </RedButtonSmall>
                ) : (
                  <GrayButton>인증 완료</GrayButton>
                )}
              </div>
            </div>
            <div className="sign-form-title">
              <div className="study-create-form-info">비밀번호</div>
              <input
                type="password"
                name="password"
                ref={passwordInput}
                placeholder="비밀번호를 입력해주세요."
                className="study-create-input"
              ></input>
            </div>
            <div className="sign-form-title">
              <div className="study-create-form-info">비밀번호 확인</div>
              <input
                type="password"
                name="passwordCheck"
                ref={passwordCheckInput}
                placeholder="비밀번호를 다시 입력해주세요."
                className="study-create-input"
              ></input>
            </div>
            <div className="sign-form-title">
              <div className="study-create-form-info">이메일</div>
              <div className="form-title-id">
                <input
                  type="email"
                  name="email"
                  ref={emailInput}
                  placeholder="이메일을 입력해주세요."
                  className="study-create-input"
                ></input>
                {!emailChecked ? (
                  <RedButtonSmall
                    onClick={() => {
                      if (checkIt()) {
                        console.log(emailInput.current.value);
                        emailValid(emailInput.current.value).then((res) => {
                          if (res.data.doubleCheck === true) {
                            setEmailChecked(true);
                            // alert("중복 확인 완료");
                            setMessage("중복 확인이 완료 되었습니다.");
                            setAlertModalShow(true);
                          } else {
                            // alert("중복 된 이메일입니다.");
                            setMessage("중복 된 이메일입니다.");
                            setAlertModalShow(true);
                          }
                        });
                      } else {
                        // alert("이메일형식이 올바르지 않습니다.");
                        setMessage("이메일형식이 올바르지 않습니다.");
                        setAlertModalShow(true);
                      }
                    }}
                  >
                    중복 확인
                  </RedButtonSmall>
                ) : (
                  <GrayButton>확인 완료</GrayButton>
                )}
              </div>
            </div>
            <div className="sign-form-title">
              <div className="study-create-form-info">닉네임</div>
              <div className="form-title-id">
                <input
                  type="text"
                  name="nickname"
                  ref={nicknameInput}
                  maxlength="10"
                  placeholder="최대 10자까지 입력 가능합니다."
                  className="study-create-input"
                ></input>
                {!nicknameChecked ? (
                  <RedButtonSmall
                    onClick={() => {
                      console.log(nicknameInput.current.value);
                      nicknameValid(nicknameInput.current.value).then((res) => {
                        if (res.data.doubleCheck === true) {
                          setNicknameChecked(true);
                          // alert("중복 확인 완료");
                          setMessage("중복 확인 완료 되었습니다.");
                          setAlertModalShow(true);
                        } else {
                          // alert("중복 된 닉네임입니다.");
                          setMessage("중복 된 닉네임입니다.");
                          setAlertModalShow(true);
                        }
                      });
                    }}
                  >
                    중복 확인
                  </RedButtonSmall>
                ) : (
                  <GrayButton>확인 완료</GrayButton>
                )}
              </div>
            </div>
            <div className="signup-submit-btn">
              <RedButton
                onClick={() => {
                  setCredentials(
                    (credentials.baekjoonId = idInput.current.value)
                  );
                  setCredentials(
                    (credentials.password = passwordInput.current.value)
                  );
                  setCredentials(
                    (credentials.email = emailInput.current.value)
                  );
                  setCredentials(
                    (credentials.nickname = nicknameInput.current.value)
                  );
                  console.log(credentials);
                  if (passwordDoubleCheck()) {
                    allInputCheck(credentials);
                  } else {
                    // alert("비밀번호가 일치하지 않습니다.");
                    setMessage("비밀번호가 일치하지 않습니다.");
                    setAlertModalShow(true);
                  }
                }}
              >
                SIGN UP
              </RedButton>
            </div>
          </div>
        </section>
        <section className="signup-page-right">
          <div className="signup-page-right-title">✨Welcome to ALDY✨</div>
          <div className="signup-page-right-sub-title">
            알디와 함께 알고리즘 스터디를 하면서 공룡을 길러보세요♡
            <div className="signup-page-right-sub-title">
              진화하는 공룡과 함께 알고리즘 실력도 키울 수 있어요!
            </div>
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
