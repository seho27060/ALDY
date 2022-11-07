import { useState, useEffect } from "react";

import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";
import { getStudyMember } from "../../api/study";
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

  return (
    <div className={`${penalty} study-list-item`}>
      <div className="study-list-title">
        <div className="study-id">{props.item.studyMemberId}</div>
        <h5 className="study-name">{props.item.baeckjoonId}</h5>
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
        <div>함께 푼 문제 : {props.item.problemNum}</div>
        <div>들어온 날짜 : {props.item.studyJoinDate}</div>
        <RedButton>강퇴</RedButton>
      </div>
    </div>
  );
};

export default StudyMemberItem;
