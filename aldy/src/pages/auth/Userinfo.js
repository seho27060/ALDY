import "./Userinfo.css";
import React, { useState, useEffect, useRef } from "react";
import styled from "styled-components";
import { updateEmail, updateNickname } from "../../api/user";
import { getUserInfo } from "../../api/user";
import { emailValid, nicknameValid } from "../../api/auth";

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

const WhiteButton = styled.button`
  width: 110px;
  border-radius: 8px;
  background-color: white;
  border: 2px solid rgb(40, 80, 15);
  outline: none;
  color: rgb(40, 80, 15);
  font-weight: bold;
  transition: transform 30ms ease-in;
  margin: 4px;
`;

const WhiteButtonL = styled.button`
  width: 200px;
  border-radius: 8px;
  background-color: white;
  border: 2px solid rgb(40, 80, 15);
  outline: none;
  color: rgb(40, 80, 15);
  font-weight: bold;
  transition: transform 30ms ease-in;
  font-size: 20px;
`;

const Userinfo = () => {
  const [nickname, setNickname] = useState(null);
  const [email, setEmail] = useState(null);

  useEffect(() => {
    getUserInfo()
      .then((res) => {
        console.log(res.data);
        setNickname(res.data.nickname);
        setEmail(res.data.email);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  const [emailShow, setEmailShow] = useState(false);
  const [nicknameShow, setNicknameShow] = useState(false);
  const emailInput = useRef();
  const nicknameInput = useRef();
  const [sendEmail, setSendEmail] = useState({ email: "" });
  const [sendNickname, setSendNickname] = useState({ nickname: "" });

  const onClickEmail = () => {
    setEmailShow((prev) => !prev);
  };

  const onClickNickname = () => {
    setNicknameShow((prev) => !prev);
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

  const ChangeEmail = () => (
    <div className="form-title">
      <div>변경 할 이메일</div>
      <div className="form-title-id">
        <input name="email" ref={emailInput}></input>
        <RedButton
          onClick={() => {
            if (checkIt()) {
              emailValid(emailInput.current.value).then((res) => {
                if (res.data.doubleCheck === true) {
                  alert("중복 확인 완료");
                  setSendEmail((sendEmail.email = emailInput.current.value));
                  updateEmail(sendEmail).then((res) => {
                    console.log(res);
                  });
                  alert("이메일 변경 완료");
                  window.location.reload(); //새로고침
                } else {
                  alert("중복 된 이메일입니다. 다시 입력해주세요");
                }
              });
            } else {
              alert("이메일 형식이 올바르지 않습니다.");
            }
          }}
        >
          중복확인
        </RedButton>
      </div>
    </div>
  );

  const ChangeNickname = () => (
    <div className="form-title">
      <div>변경 할 닉네임</div>
      <div className="form-title-id">
        <input name="nickname" ref={nicknameInput}></input>
        <RedButton
          onClick={() => {
            nicknameValid(nicknameInput.current.value).then((res) => {
              if (res.data.doubleCheck === true) {
                alert("중복 확인 완료");
                setSendNickname(
                  (sendNickname.nickname = nicknameInput.current.value)
                );
                updateNickname(sendNickname).then((res) => {
                  console.log(res);
                });
                alert("닉네임 변경 완료");
                console.log(nicknameInput.current.value);
                window.location.reload(); //새로고침
              } else {
                alert("중복 된 닉네임입니다. 다시 입력해주세요.");
              }
            });
          }}
        >
          중복확인
        </RedButton>
      </div>
    </div>
  );

  return (
    <main className="userinfo-page-main">
      <div className="userinfo-page-bg">
        <section className="userinfo-page-left">
          <div>✨닉네임과 이메일을 변경할 수 있습니다.✨</div>
          <div className="nnnnnn">회원 정보</div>
          <div>
            <div className="form-title">
              <div>이메일</div>
              <div className="userinfo-form-title-id">
                <div>{email}</div>
                <WhiteButton onClick={onClickEmail}>수정하기</WhiteButton>
              </div>
            </div>
            {emailShow ? <ChangeEmail /> : null}
            <div className="form-title">
              <div>닉네임</div>
              <div className="userinfo-form-title-id">
                <div>{nickname}</div>
                <WhiteButton onClick={onClickNickname}>수정하기</WhiteButton>
              </div>
            </div>
            {nicknameShow ? <ChangeNickname /> : null}
          </div>
          <WhiteButton onClick={onClickNickname}>회원 탈퇴</WhiteButton>
        </section>
        <section className="userinfo-page-right">
          <div className="userinfo-page-right-title">✨Welcome to Aldy✨</div>
          <div className="userinfo-page-right-text">
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

export default Userinfo;
