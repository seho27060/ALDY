import "./ChangePW.css";
import React, { useState, useEffect, useRef } from "react";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { changepassword } from "../../api/user";
import AlertModal from "../../components/AlertModal";
import AlertRefreshModal from "../../components/AlertRefreshModal";
import { useRecoilState } from "recoil";
import { isNav } from "../../store/states";

const RedButton = styled.button`
  width: 200px;
  border-radius: 8px;
  background-color: rgb(40, 80, 15);
  border: 2px solid rgb(40, 80, 15);
  outline: none;
  color: white;
  font-weight: bold;
  transition: all 200ms ease-in;
  padding: 10px 0 7px;
  &:hover {
    background-color: white;
    color: rgb(40, 80, 15);
  }
`;

const Changepw = () => {
  const navigate = useNavigate();

  const [nav, setNav] = useRecoilState(isNav);
  setNav(true);

  const navigateMypage = () => {
    navigate("/mypage");
  };
  const [sendPw, setSendPw] = useState({ password: "" });

  const passwordInput = useRef(null);
  const passwordCheckInput = useRef(null);

  const [message, setMessage] = useState("");
  const [alertModalShow, setAlertModalShow] = useState(false);
  const [alertRefreshModalShow, setAlertRefreshModalShow] = useState(false);

  const passwordDoubleCheck = () => {
    if (passwordInput.current.value === passwordCheckInput.current.value) {
      return true;
    }
    return false;
  };

  const onChangePassword = () => {
    setSendPw((sendPw.password = passwordInput.current.value));
    console.log(sendPw);
    if (passwordDoubleCheck()) {
      console.log("성공");
      changepassword(sendPw)
        .then((res) => {
          // alert("비밀번호가 변경 되었습니다.");
          setMessage("비밀번호가 변경 되었습니다.");
          setAlertModalShow(true);
        })
        .catch((err) => {
          console.log(err, "에러ㅠㅠ");
        });
    } else {
      // alert("비밀번호가 일치하지 않습니다.");
      setMessage("비밀번호가 일치하지 않습니다");
      setAlertRefreshModalShow(true); //새로고침
    }
  };

  return (
    <main className="userinfo-page-main">
      <AlertModal
        show={alertModalShow}
        onHide={() => {
          setAlertModalShow(false);
          navigateMypage();
        }}
        message={message}
      />
      <AlertRefreshModal
        show={alertRefreshModalShow}
        onHide={() => setAlertRefreshModalShow(false)}
        message={message}
      />
      <div className="Changepw-page-bg">
        <section className="userinfo-page-left">
          <div className="sign-title">비밀번호 수정</div>
          <div className="sign-title-info">
            ✨변경할 비밀번호를 입력해주세요.✨
          </div>
          <div>
            <div className="sign-form-title">
              <div>비밀번호</div>
              <div className="form-title-id">
                <input
                  type="password"
                  name="password"
                  ref={passwordInput}
                  placeholder="비밀번호를 입력해주세요."
                  class="study-create-input"
                ></input>
              </div>
            </div>
            <div className="sign-form-title">
              <div>비밀번호 확인</div>
              <input
                type="password"
                name="passwordCheck"
                ref={passwordCheckInput}
                placeholder="비밀번호를 다시 입력해주세요."
                class="study-create-input"
              ></input>
            </div>
            <div className="Changepw-submit-btn">
              <RedButton onClick={onChangePassword}>
                비밀번호 변경하기
              </RedButton>
            </div>
          </div>
        </section>
        <section className="userinfo-page-right">
          <div className="userinfo-page-right-title">✨Welcome to Aldy✨</div>
          <div className="userinfo-page-right-sub-title">
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
