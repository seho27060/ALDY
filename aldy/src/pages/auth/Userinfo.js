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

  const onClickEmail = (e) => {
    // e.preventDefault();
    setEmailShow((prev) => !prev);
  };

  const onClickNickname = (e) => {
    // e.preventDefault();
    setNicknameShow(!nicknameShow);
  };

  const [emailChecked, setEmailChecked] = useState(false);
  const [nicknameChecked, setNicknameChecked] = useState(false);

  const ChangeEmail = () => (
    <div className="form-title">
      <div>변경 할 이메일</div>
      <div className="form-title-id">
        <input
          name="email"
          ref={emailInput}
          // placeholder={email}
        ></input>
        <RedButton>중복확인</RedButton>
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
                setNicknameChecked(true);
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
                <RedButton onClick={onClickEmail}>수정하기</RedButton>
              </div>
            </div>
            {emailShow ? <ChangeEmail /> : null}
            <div className="form-title">
              <div>닉네임</div>
              <div className="userinfo-form-title-id">
                <div>{nickname}</div>
                <RedButton onClick={onClickNickname}>수정하기</RedButton>
              </div>
            </div>
            {nicknameShow ? <ChangeNickname /> : null}
          </div>
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
