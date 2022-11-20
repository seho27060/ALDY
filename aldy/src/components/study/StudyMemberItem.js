import { useState, useEffect } from "react";
import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";
import { kickMemberApi } from "../../api/study";
import styled from "styled-components";
import AlertRefreshModal from "../AlertRefreshModal";

const RedButton = styled.button`
  width: 55px;
  border-radius: 8px;
  background-color: red;
  border: 2px solid red;
  outline: none;
  color: white;
  font-weight: bold;
  margin-left: auto;
  font-size: 13px;
  padding-top: 3px;
  transition: all 200ms ease-in;
  &:hover {
    background-color: white;
    color: red;
    transition: all 200ms ease-in;
    border: 2px solid red;
  }
`;

const StudyMemberItem = (props) => {
  const [dropdown, setDropdown] = useState("none");
  const [penalty, setPenalty] = useState(props.item.numberOfAlerts);
  const sendLeaderId = sessionStorage.getItem("sendLeaderId");

  // alert modal
  const [message, setMessage] = useState("");
  const [AlertRefreshModalShow, setAlertRefreshModalShow] = useState(false);

  const penaltyColor = () => {
    if (penalty === 1) {
      setPenalty("penalty-green");
    } else if (penalty === 2) {
      setPenalty("penalty-orange");
    } else if (penalty === 3) {
      setPenalty("penalty-red");
    } else {
      setPenalty("penalty-white");
    }
  };

  useEffect(() => {
    penaltyColor();
  }, []);
  const myId = sessionStorage.getItem("userName");
  const [credentials, setCredentials] = useState({
    memberId: null,
    studyId: null,
  });

  const onKickMember = () => {
    setCredentials((credentials.memberId = props.item.memberId));
    setCredentials((credentials.studyId = props.item.studyId));
    console.log("멤버 강퇴");
    console.log(credentials);
    kickMemberApi(credentials)
      .then((res) => {
        // alert("강퇴되었습니다");
        // window.location.reload(); //새로고침
        setMessage("강퇴되었습니다");
        setAlertRefreshModalShow(true);
      })
      .catch((err) => {
        console.log(err);
        // alert("에러입니다. 다시 실행해주세요.");
        setMessage("에러입니다. 다시 실행해주세요.");
        setAlertRefreshModalShow(true);
      });
  };

  return (
    <div className={`${penalty} study-list-item`}>
      <AlertRefreshModal
        show={AlertRefreshModalShow}
        onHide={() => {
          setAlertRefreshModalShow(false);
        }}
        message={message}
      />
      <div className="study-list-title">
        <div className="study-id">{props.num + 1}</div>
        <p className="study-name" style={{ margin: "3px" }}>
          {props.item.nickname}
        </p>
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
        <div>함께 푼 문제 수 : {props.item.solvedTogether}개</div>
        <div>경고 누적 {props.item.numberOfAlerts}회</div>
        {myId === sendLeaderId && !("Leader" === props.item.message) && (
          <RedButton onClick={onKickMember}>강퇴</RedButton>
        )}
      </div>
    </div>
  );
};

export default StudyMemberItem;
