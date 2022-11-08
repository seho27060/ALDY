import { useState, useEffect } from "react";
import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";
import { kickMemberApi } from "../../api/study";
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
  margin-left: auto;
`;

const StudyMemberItem = (props) => {
  const [dropdown, setDropdown] = useState("none");
  const [penalty, setPenalty] = useState(props.item.penalty);
  console.log(props, "프롭스");

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

  const kickMember = () => {
    console.log("멤버 강퇴");
    kickMemberApi();
  };

  return (
    <div className={`${penalty} study-list-item`}>
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
        <div>함께 푼 문제 수: {props.item.solvedTogether}개</div>
        <RedButton onClick={kickMember(props.item.id)}>강퇴</RedButton>
      </div>
    </div>
  );
};

export default StudyMemberItem;
