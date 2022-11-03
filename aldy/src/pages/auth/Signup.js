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

  const [bojModalShow, setBojModalShow] = useState(false);
  const [bojValidationCode, setBojValidationCode] = useState("");
  const [sendId, setSendId] = useState({ baekjoonId: "" });
  // 유효성검사
  const [idChecked, setIdChecked] = useState(false);
  const [emailChecked, setEmailChecked] = useState(false);
  const [nicknameChecked, setNicknameChecked] = useState(false);

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
        alert("아이디 인증을 해주세요.");
      }
      if (!emailChecked) {
        alert("이메일 중복 확인을 해주세요.");
      }
      if (!nicknameChecked) {
        alert("닉네임 중복 확인을 해주세요.");
      }
    }
  };

  const solvedAc = () => {
    window.open("https://solved.ac/", "_blank");
  };

  return (
    <main className="signup-page-main">
      <Modal size="lg" show={bojModalShow} onHide={handleBojModalShow}>
        <Modal.Body className="review-modal-body">
          <div className="review-modal-header">
            <div>
              <p className="review-modal-title">
                인증 코드 : {bojValidationCode}
              </p>
              <p style={{ fontSize: "12px", color: "#646464" }}>
                solved.ac 가입 후, solved.ac 자기소개 끝에 인증코드를
                추가해주세요.
              </p>
            </div>
            <div>
              <button
                className="review-modal-close-btn"
                onClick={handleBojModalShow}
              >
                X
              </button>
            </div>
          </div>
          <div className="review-modal-content">
            <img src={process.env.PUBLIC_URL + "/solved.png"} alt=""></img>
          </div>
          <div className="solved-btn">
            <RedButton onClick={solvedAc}>solved.ac로 이동</RedButton>
            <RedButton
              onClick={() => {
                interLock(idInput.current.value).then((res) => {
                  if (res.data.interlock === true) {
                    setIdChecked(true);
                    alert("인증이 성공하였습니다.");
                    handleBojModalShow(); //모달창 닫기
                  } else {
                    alert("인증에 실패하였습니다. 다시 인증해주세요.");
                    window.location.reload(); //새로고침
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
          <div className="nnnnnn">Sign Up</div>
          <div>
            <div className="signup-form-title">
              <div>백준 아이디</div>
              <div className="form-title-id">
                <input
                  type="text"
                  name="id"
                  ref={idInput}
                  placeholder="백준 아이디를 입력해주세요."
                ></input>
                <RedButton
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
                        alert("백준 회원이 아닙니다.");
                      });
                  }}
                >
                  인증하기
                </RedButton>
              </div>
            </div>
            <div className="signup-form-title">
              <div>비밀번호</div>
              <input
                type="password"
                name="password"
                ref={passwordInput}
                placeholder="비밀번호를 입력해주세요."
              ></input>
            </div>
            <div className="signup-form-title">
              <div>비밀번호 확인</div>
              <input
                type="password"
                name="passwordCheck"
                ref={passwordCheckInput}
                placeholder="비밀번호를 다시 입력해주세요."
              ></input>
            </div>
            <div className="signup-form-title">
              <div>이메일</div>
              <div className="form-title-id">
                <input
                  type="email"
                  name="email"
                  ref={emailInput}
                  placeholder="이메일을 입력해주세요."
                ></input>
                <RedButton
                  onClick={() => {
                    if (checkIt()) {
                      console.log(emailInput.current.value);
                      emailValid(emailInput.current.value).then((res) => {
                        if (res.data.doubleCheck === true) {
                          setEmailChecked(true);
                          alert("중복 확인 완료");
                        } else {
                          alert("중복 된 이메일입니다.");
                        }
                      });
                    } else {
                      alert("이메일형식이 올바르지 않습니다.");
                    }
                  }}
                >
                  중복확인
                </RedButton>
              </div>
            </div>
            <div className="signup-form-title">
              <div>닉네임</div>
              <div className="form-title-id">
                <input
                  type="text"
                  name="nickname"
                  ref={nicknameInput}
                  placeholder="닉네임을 입력해주세요."
                ></input>
                <RedButton
                  onClick={() => {
                    console.log(nicknameInput.current.value);
                    nicknameValid(nicknameInput.current.value).then((res) => {
                      if (res.data.doubleCheck === true) {
                        setNicknameChecked(true);
                        alert("중복 확인 완료");
                      } else {
                        alert("중복 된 닉네임입니다.");
                      }
                    });
                  }}
                >
                  중복확인
                </RedButton>
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
                    alert("비밀번호가 일치하지 않습니다.");
                  }
                }}
              >
                Sign Up
              </RedButton>
            </div>
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
