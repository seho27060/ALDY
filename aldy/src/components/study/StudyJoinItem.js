import { useState, useEffect } from "react";
import "./MyStudyListItem.css";
import { acceptMemberApi, rejectMemberApi } from "../../api/study";
import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";
import styled from "styled-components";

const RedButton = styled.button`
  width: 70px;
  border-radius: 8px;
  background-color: red;
  border: none;
  outline: none;
  color: white;
  font-weight: bold;
  transition: transform 30ms ease-in;
  margin-left: 10px;
`;

const StudyJoinItem = (props) => {
  const [dropdown, setDropdown] = useState("none");
  console.log(props.item, "후아유");

  const [credentials, setCredentials] = useState({
    memberId: null,
    studyId: null,
  });

  const onAccept = () => {
    setCredentials((credentials.memberId = props.item.memberId));
    setCredentials((credentials.studyId = props.item.studyId));
    console.log("멤버 가입 수락");
    console.log(credentials);
    acceptMemberApi(credentials)
      .then((res) => {
        alert(`${props.item.nickname}님 가입이 수락 되었습니다`);
        window.location.reload(); //새로고침
      })
      .catch((err) => {
        alert(
          `에러입니다. ${props.item.nickname}님 가입 수락을 다시 실행해주세요`
        );
      });
  };

  const onReject = () => {
    setCredentials((credentials.memberId = props.item.memberId));
    setCredentials((credentials.studyId = props.item.studyId));
    console.log("멤버 가입 거절");
    console.log(credentials);
    rejectMemberApi(credentials)
      .then((res) => {
        alert(`${props.item.nickname}님 가입이 거절 되었습니다`);
        window.location.reload(); //새로고침
      })
      .catch((err) => {
        console.log(err);
        alert(
          `에러입니다. ${props.item.nickname}님 가입 거절을 다시 실행해주세요`
        );
      });
  };

  return (
    <div className="study-list-item">
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
          <RedButton onClick={onAccept}>수락</RedButton>
          <RedButton onClick={onReject}>거절</RedButton>
        </div>
      </div>
    </div>
  );
};
export default StudyJoinItem;
