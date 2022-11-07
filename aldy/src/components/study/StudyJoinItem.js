import { useState, useEffect } from "react";

import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";
import { useNavigate, useParams, useLocation } from "react-router-dom";
import { deleteStudy, getStudyMember } from "../../api/study";
import TierData from "../../data/tier";
import styled from "styled-components";
import StudyMember from "../../components/study/StudyMember";

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

const StudyJoinItem = (props) => {
  const [dropdown, setDropdown] = useState("none");

  return (
    <div className="study-list-item">
      <div className="study-list-title">
        <div className="study-id">{props.item.studyJoinId}</div>
        <h5 className="study-name">{props.item.studyJoinName}</h5>
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
        <div>{props.item.description}</div>
        <RedButton>수락</RedButton>
      </div>
    </div>
  );
};
export default StudyJoinItem;
