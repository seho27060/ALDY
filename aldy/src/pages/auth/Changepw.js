import React, { useState, useRef } from "react";
import AlertModal from "../../components/modal/AlertModal";
import AlertRefreshModal from "../../components/modal/AlertRefreshModal";
import { useNavigate } from "react-router-dom";
import { changepassword } from "../../api/user";
import { useRecoilState } from "recoil";
import { isNav } from "../../store/states";

import Button from "../../components/styled/Button";
import "./ChangePW.css";

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
    if (passwordDoubleCheck()) {
      changepassword(sendPw)
        .then((res) => {
          setMessage("비밀번호가 변경 되었습니다.");
          setAlertModalShow(true);
        })
        .catch((err) => {
          // console.log(err, "에러ㅠㅠ");
        });
    } else {
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
              <Button green large onClick={onChangePassword}>
                비밀번호 변경하기
              </Button>
            </div>
          </div>
        </section>
        <section className="userinfo-page-right">
          <div className="userinfo-page-right-title">✨Welcome to Aldy✨</div>
          <div className="userinfo-page-right-sub-title">
            Aldy와 함께 알고리즘 스터디를 키워보세요!
          </div>
          <img
            src={process.env.PUBLIC_URL + "/ALDY/signup_dinosaur.png"}
            alt=""
          ></img>
        </section>
      </div>
    </main>
  );
};

export default Changepw;
