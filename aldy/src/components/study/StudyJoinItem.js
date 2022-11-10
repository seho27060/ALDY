import { useState, useEffect } from "react";
import "./MyStudyListItem.css";

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
        <div>가입 신청 메시지 : {props.item.message}</div>
        <div className="Join-btns">
          <RedButton>수락</RedButton>
          <RedButton>거절</RedButton>
        </div>
      </div>
    </div>
  );
};
export default StudyJoinItem;
