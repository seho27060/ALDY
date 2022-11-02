import "./Signup.css";
import React, { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import Modal from "react-bootstrap/Modal";
import styled from "styled-components";
import axios from "axios";

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
  // const { signInList, setSignInList, articleNumber } = props;

  // 일단 여기까지가.....아오아오 여기 위에가 다시 해보는 코드
  const navigate = useNavigate();

  const navigateMain = () => {
    navigate("/");
  };

  const credentials = {
    id: "",
    password: "",
    email: "",
    nickname: "",
  };

  const onSubmit = (e) => {
    e.preventDefault();
    console.log(idInput.current);
    console.log(credentials);
    // navigateMain();
  };

  const [bojModalShow, setBojModalShow] = useState(false);

  const handleBojModalShow = (e) => {
    e.preventDefault();
    setBojModalShow((prev) => !prev);
  };

  const idInput = useRef(null);
  const passwordInput = useRef(null);
  const passwordCheckInput = useRef(null);
  const emailInput = useRef(null);
  const nicknameInput = useRef(null);
  //Ref 안의 값에 접근 할 땐 idInput.current

  const signUpByRef = () => {
    const newSignInForm = {
      id: idInput.current.value,
      password: passwordInput.current.value,
      email: emailInput.current.value,
      nickname: nicknameInput.current.value,
    };

    console.log(newSignInForm);

    // setSignInList([...signInList, newSignInForm]);

    alert("회원가입되셨습니다.");

    idInput.current.value = "";
    passwordInput.current.value = "";
    emailInput.current.value = "";
    nicknameInput.current.value = "";
  };

  // const reset = () => {
  //   idInput.current.value = "";
  //   passwordInput.current.value = "";
  //   emailInput.current.value = "";
  //   nicknameInput.current.value = "";
  // };

  const solvedAc = () => {
    window.open("https://solved.ac/", "_blank");
  };

  return (
    <main className="signup-page-main">
      <Modal size="lg" show={bojModalShow} onHide={handleBojModalShow}>
        <Modal.Body className="review-modal-body">
          <div className="review-modal-header">
            <div>
              <p className="review-modal-title">인증 코드 : 2dk@wk</p>
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
            <RedButton onClick={solvedAc}>인증완료 하기</RedButton>
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
                  name="id"
                  ref={idInput}
                  placeholder="백준 아이디를 입력해주세요."
                ></input>
                <RedButton onClick={handleBojModalShow}>인증하기</RedButton>
              </div>
            </div>
            <div className="signup-form-title">
              <div>비밀번호</div>
              <input
                name="password"
                ref={passwordInput}
                placeholder="비밀번호를 입력해주세요."
              ></input>
            </div>
            <div className="signup-form-title">
              <div>비밀번호 확인</div>
              <input
                name="passwordCheck"
                ref={passwordCheckInput}
                placeholder="비밀번호를 다시 입력해주세요."
              ></input>
            </div>
            <div className="signup-form-title">
              <div>이메일</div>
              <div className="form-title-id">
                <input
                  name="email"
                  ref={emailInput}
                  placeholder="이메일을 입력해주세요."
                ></input>
                <RedButton>중복확인</RedButton>
              </div>
            </div>
            <div className="signup-form-title">
              <div>닉네임</div>
              <div className="form-title-id">
                <input
                  name="nickname"
                  ref={nicknameInput}
                  placeholder="닉네임을 입력해주세요."
                ></input>
                <RedButton>중복확인</RedButton>
              </div>
            </div>
            <div className="signup-submit-btn">
              <RedButton onClick={signUpByRef}>Sign Up</RedButton>
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
