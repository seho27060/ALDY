import "./ChangePW.css";
import React, { useState, useEffect, useRef } from "react";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { changepassword } from "../../api/user";

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

const Changepw = () => {
  const navigate = useNavigate();

  const navigateMypage = () => {
    navigate("/mypage");
  };
  const [sendPw, setSendPw] = useState({ password: "" });
  const passwordInput = useRef(null);
  const passwordCheckInput = useRef(null);
  const passwordDoubleCheck = () => {
    if (passwordInput.current.value === passwordCheckInput.current.value) {
      return true;
    }
    return false;
  };

  return (
    <main className="Changepw-page-main">
      <div className="Changepw-page-bg">
        <section className="Changepw-page-left">
          <div>✨변경할 비밀번호를 입력해주세요.✨</div>
          {/* 여기 수정 */}
          <div className="nnnnnn">비밀번호 수정</div>
          <div>
            <div className="form-title">
              <div>비밀번호</div>
              <div className="form-title-id">
                <input
                  type="password"
                  name="password"
                  ref={passwordInput}
                  placeholder="비밀번호를 입력해주세요."
                ></input>
              </div>
            </div>
            <div className="form-title">
              <div>비밀번호 확인</div>
              <input
                type="password"
                name="passwordCheck"
                ref={passwordCheckInput}
                placeholder="비밀번호를 다시 입력해주세요."
              ></input>
            </div>
            <div className="Changepw-submit-btn">
              <RedButton
                onClick={() => {
                  setSendPw((sendPw.password = passwordInput.current.value));
                  console.log(sendPw);
                  if (passwordDoubleCheck()) {
                    console.log("성공");
                    changepassword(sendPw);
                    alert("비밀번호가 변경 되었습니다.");
                    navigateMypage();
                  } else {
                    alert("비밀번호가 일치하지 않습니다.");
                  }
                }}
              >
                비밀번호 변경하기
              </RedButton>
            </div>
          </div>
        </section>
        <section className="Changepw-page-right">
          <div className="Changepw-page-right-title">✨Welcome to Aldy✨</div>
          <div className="Changepw-page-right-text">
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

export default Changepw;
