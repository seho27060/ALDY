import { useState, useEffect } from "react";
import "./MyStudyListItem.css";
import { acceptMemberApi, rejectMemberApi } from "../../api/study";
import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";
import styled from "styled-components";
import AlertModal from "../../components/AlertModal";
import AlertRefreshModal from "../../components/AlertRefreshModal";

const RedButton = styled.button`
  width: 70px;
  border-radius: 8px;
  background-color: red;
  outline: none;
  color: white;
  font-weight: bold;
  margin-left: 10px;
  font-size: 13px;
  padding-top: 3px;
  border: 2px solid red;
  transition: all 200ms ease-in;
  &:hover {
    background-color: white;
    color: red;
    transition: all 200ms ease-in;
    border: 2px solid red;
  }
`;

const GreenButton = styled.button`
  width: 70px;
  border-radius: 8px;
  background-color: green;
  outline: none;
  color: white;
  font-weight: bold;
  margin-left: 10px;
  font-size: 13px;
  padding-top: 3px;
  border: 2px solid green;
  transition: all 200ms ease-in;
  &:hover {
    background-color: white;
    color: green;
    transition: all 200ms ease-in;
    border: 2px solid green;
  }
`;

const StudyJoinItem = (props) => {
  const [dropdown, setDropdown] = useState("none");

  const [credentials, setCredentials] = useState({
    memberId: null,
    studyId: null,
  });

  const [message, setMessage] = useState("");
  const [alertModalShow, setAlertModalShow] = useState(false);
  const [alertRefreshModalShow, setAlertRefreshModalShow] = useState(false);

  const onAccept = () => {
    setCredentials((credentials.memberId = props.item.memberId));
    setCredentials((credentials.studyId = props.item.studyId));

    console.log(credentials, "멤버 가입 수락");
    acceptMemberApi(credentials)
      .then((res) => {
        // alert(`${props.item.nickname}님 가입이 수락 되었습니다`);
        // window.location.reload(); //새로고침
        setMessage(`${props.item.nickname}님 가입이 수락 되었습니다`);
        setAlertRefreshModalShow(true); //새로고침
      })
      .catch((err) => {
        // alert(
        //   `에러입니다. ${props.item.nickname}님 가입 수락을 다시 실행해주세요`
        // );
        setMessage(
          `에러입니다. ${props.item.nickname}님 가입 수락을 다시 실행해주세요`
        );
        setAlertRefreshModalShow(true); //새로고침
      });
  };

  const onReject = () => {
    setCredentials((credentials.memberId = props.item.memberId));
    setCredentials((credentials.studyId = props.item.studyId));
    console.log(credentials, "멤버 가입 거절");
    rejectMemberApi(credentials)
      .then((res) => {
        // alert(`${props.item.nickname}님 가입이 거절 되었습니다`);
        // window.location.reload(); //새로고침
        setMessage(`${props.item.nickname}님 가입이 거절 되었습니다.`);
        setAlertRefreshModalShow(true); //새로고침
      })
      .catch((err) => {
        console.log(err);
        // alert(
        //   `에러입니다. ${props.item.nickname}님 가입 거절을 다시 실행해주세요`
        // );
        setMessage(
          `에러입니다. ${props.item.nickname}님 가입 거절을 다시 실행해주세요`
        );
        setAlertRefreshModalShow(true); //새로고침
      });
  };

  return (
    <div className="study-list-item">
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
      <div className="study-list-title">
        <div className="study-id">{props.num + 1}</div>
        <h5 className="study-name" style={{ margin: "3px" }}>
          {props.item.nickname}
        </h5>
        <img
          src={`https://d2gd6pc034wcta.cloudfront.net/tier/${props.item.tier}.svg`}
          alt="티어 이미지"
          className="tier-image"
          style={{ width: "15px" }}
        ></img>
        <div className="study-number"></div>
        {dropdown === "none" && (
          <FaChevronCircleDown
            className="down-icon"
            onClick={() => {
              setDropdown("active");
            }}
          />
        )}
        {dropdown === "active" && (
          <FaChevronCircleUp
            className="down-icon"
            onClick={() => {
              setDropdown("none");
            }}
          />
        )}
      </div>

      <div
        className={`study-list-content ${
          dropdown === "active" ? "content-active" : ""
        }`}
      >
        <div>백준 아이디 : {props.item.baekjoonId}</div>
        <div>가입 신청 메시지 : {props.item.message}</div>
        <div className="Join-btns">
          <GreenButton onClick={onAccept}>수락</GreenButton>
          <RedButton onClick={onReject}>거절</RedButton>
        </div>
      </div>
    </div>
  );
};
export default StudyJoinItem;
