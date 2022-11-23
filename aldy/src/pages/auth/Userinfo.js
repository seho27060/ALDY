import React, { useState, useEffect, useRef } from "react";
import { useRecoilState } from "recoil";
import {
  updateEmail,
  updateNickname,
  withdrawApi,
  getUserInfo,
} from "../../api/user";
import { emailValid, nicknameValid } from "../../api/auth";
import AlertModal from "../../components/modal/AlertModal";
import AlertRefreshModal from "../../components/modal/AlertRefreshModal";
import { isNav } from "../../store/states";

import Button from "../../components/styled/Button";
import "./Userinfo.css";

const Userinfo = () => {
  const [nav, setNav] = useRecoilState(isNav);
  setNav(true);
  const [nickname, setNickname] = useState(null);
  const [email, setEmail] = useState(null);

  useEffect(() => {
    getUserInfo()
      .then((res) => {
        setNickname(res.data.nickname);
        setEmail(res.data.email);
      })
      .catch((err) => {
        // console.log(err);
      });
  }, []);

  const [emailShow, setEmailShow] = useState(false);
  const [nicknameShow, setNicknameShow] = useState(false);
  const [passwordShow, setPasswordShow] = useState(false);

  const emailInput = useRef();
  const nicknameInput = useRef();
  const passwordInput = useRef();

  const [sendEmail, setSendEmail] = useState({ email: "" });
  const [sendNickname, setSendNickname] = useState({ nickname: "" });
  const [sendPassword, setSendPassword] = useState({ password: "" });

  const [message, setMessage] = useState("");
  const [alertModalShow, setAlertModalShow] = useState(false);
  const [alertRefreshModalShow, setAlertRefreshModalShow] = useState(false);

  const onClickEmail = () => {
    setEmailShow((prev) => !prev);
  };

  const onClickNickname = () => {
    setNicknameShow((prev) => !prev);
  };

  const onClickWithdraw = () => {
    setPasswordShow((prev) => !prev);
  };

  // 이메일 유효성 검사
  const checkIt = () => {
    const email = emailInput.current.value;
    const exptext = /^[A-Za-z0-9_.-]+@[A-Za-z0-9-]+.[A-Za-z0-9-]+/;
    if (exptext.test(email) === false) {
      //이메일 형식이 알파벳+숫자@알파벳+숫자.알파벳+숫자 형식이 아닐경우
      return false;
    }
    return true;
  };

  // 이메일 중복 확인
  const checkEmail = () => {
    if (checkIt()) {
      emailValid(emailInput.current.value).then((res) => {
        if (res.data.doubleCheck === true) {
          setMessage("중복 확인이 완료되었습니다.");
          setAlertModalShow(true);
          setSendEmail((sendEmail.email = emailInput.current.value));
          updateEmail(sendEmail).then((res) => {});
          setMessage("이메일 변경이 완료 되었습니다.");
          setAlertRefreshModalShow(true);
        } else {
          setMessage("중복 된 이메일입니다. 다시 입력해주세요.");
          setAlertModalShow(true);
        }
      });
    } else {
      setMessage("이메일 형식이 올바르지 않습니다.");
      setAlertModalShow(true);
    }
  };

  // 닉네임 중복 확인
  const checkNickname = () => {
    nicknameValid(nicknameInput.current.value).then((res) => {
      if (res.data.doubleCheck === true) {
        setMessage("중복 확인이 완료 되었습니다.");
        setAlertModalShow(true);
        setSendNickname((sendNickname.nickname = nicknameInput.current.value));
        updateNickname(sendNickname).then((res) => {});
        setMessage("닉네임 변경이 완료 되었습니다.");
        sessionStorage.setItem("nickname", sendNickname.nickname);
        setAlertRefreshModalShow(true);
      } else {
        setMessage("중복 된 닉네임입니다. 다시 입력해주세요.");
        setAlertModalShow(true);
      }
    });
  };

  // 회원탈퇴
  const userWithdraw = () => {
    setSendPassword((sendPassword.password = passwordInput.current.value));
    withdrawApi(sendPassword)
      .then((res) => {
        setMessage("탈퇴 되었습니다.");
        setAlertRefreshModalShow(true);
      })
      .catch((err) => {
        setMessage("탈퇴에 실패하였습니다. 다시 시도해주세요.");
        setAlertRefreshModalShow(true);
      });
  };

  // 이메일 변경 Input
  const ChangeEmail = () => (
    <div className="sign-form-title">
      <div>변경 할 이메일</div>
      <div className="form-title-id">
        <input class="study-create-input" name="email" ref={emailInput}></input>
        <Button green small onClick={checkEmail}>
          중복 확인
        </Button>
      </div>
    </div>
  );
  // 닉네임 변경 Input
  const ChangeNickname = () => (
    <div className="sign-form-title">
      <div>변경 할 닉네임</div>
      <div className="form-title-id">
        <input
          name="nickname"
          ref={nicknameInput}
          maxlength="10"
          placeholder="최대 10자까지 입력 가능합니다."
          class="study-create-input"
        ></input>
        <Button green small onClick={checkNickname}>
          중복 확인
        </Button>
      </div>
    </div>
  );
  // 회원탈퇴 Input
  const Withdraw = () => (
    <div className="form-title">
      <div>비밀번호</div>
      <div className="form-title-id">
        <input
          name="password"
          type="password"
          placeholder="비밀번호를 입력해주세요."
          ref={passwordInput}
          class="study-create-input"
        ></input>
        <Button green small onClick={userWithdraw}>
          탈퇴하기
        </Button>
      </div>
    </div>
  );

  return (
    <main className="userinfo-page-main">
      <AlertModal
        show={alertModalShow}
        onHide={() => setAlertModalShow(false)}
        message={message}
      />
      <AlertRefreshModal
        show={alertRefreshModalShow}
        onHide={() => setAlertRefreshModalShow(false)}
        message={message}
      />
      <div className="userinfo-page-bg">
        <section className="userinfo-page-left">
          <div className="sign-title">회원 정보</div>
          <div className="sign-title-info">
            ✨닉네임과 이메일을 변경할 수 있습니다.✨
          </div>
          <div>
            <div className="sign-form-title">
              <div>이메일</div>
              <div className="userinfo-form-title-id">
                <div>{email}</div>
                <Button greenLine small onClick={onClickEmail}>
                  수정하기
                </Button>
              </div>
            </div>
            {emailShow ? <ChangeEmail /> : null}
            <div className="sign-form-title">
              <div>닉네임</div>
              <div className="userinfo-form-title-id">
                <div>{nickname}</div>
                <Button greenLine small onClick={onClickNickname}>
                  수정하기
                </Button>
              </div>
            </div>
            {nicknameShow ? <ChangeNickname /> : null}
            <div className="sign-form-title">
              <div className="userinfo-withdraw-title-id">
                <div
                  className="userinfo-withdraw"
                  style={{ color: "rgb(40, 80, 15)", marginTop: "20px" }}
                  onClick={onClickWithdraw}
                >
                  <b>회원 탈퇴😥</b>
                </div>
              </div>
            </div>
            {passwordShow ? <Withdraw /> : null}
          </div>
        </section>
        <section className="userinfo-page-right">
          <div className="userinfo-page-right-title">✨Welcome to Aldy✨</div>
          <div className="userinfo-page-right-sub-title">
            알디에서 알고리즘 스터디를 하며 공룡을 키워보세요!
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

export default Userinfo;
