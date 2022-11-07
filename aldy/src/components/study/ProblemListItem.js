import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";
import styled from "styled-components";
import { useState } from "react";

const WhiteButton = styled.button`
  width: 70px;
  border-radius: 8px;
  background-color: white;
  border: 2px solid red;
  outline: none;
  color: red;
  font-weight: bold;
  font-size: 12px;
  transition: transform 30ms ease-in;
`;

const ProblemListItem = (props) => {
  const [dropdown, setDropdown] = useState("none");

  return (
    <div className="problem-list-item">
      <div className="problem-list-title">
        <h5 className="problem-name">{props.item.problemNum}번</h5>
        <div className="problem-name">{props.item.problemName}</div>
        <div className="problem-list-right">
          <WhiteButton>코드 리뷰</WhiteButton>
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
      </div>

      <div
        className={`problem-list-content ${
          dropdown === "active" ? "content-active" : ""
        }`}
      >
        <div>스터디원 1 : {props.item.study1}</div>
        <div>스터디원 2 : {props.item.study2}</div>
        <div>스터디원 3 : {props.item.study3}</div>
        <div>스터디원 4 : {props.item.study4}</div>
        <div>스터디원 5 : {props.item.study5}</div>
      </div>
    </div>
  );
};

export default ProblemListItem;
