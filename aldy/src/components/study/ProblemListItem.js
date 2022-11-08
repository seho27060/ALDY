import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";
import styled, { ServerStyleSheet } from "styled-components";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

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
  const studyId = props.studyId;
  const studyName = props.studyName;
  const year = props.year;
  const month = props.month;
  const navigate = useNavigate();
  return (
    <div className="problem-list-item">
      <div className="problem-list-title">
        <b className="problem-name">{props.item.problemNum}번</b>
        <div className="problem-name">{props.item.problemName}</div>
        <div className="problem-list-right">
          <WhiteButton
            onClick={() => {
              sessionStorage.setItem("reviewProblemId", props.item.id);
              sessionStorage.setItem("reviewStudyId", studyId);
              sessionStorage.setItem(
                "reviewProblemName",
                props.item.problemName
              );
              sessionStorage.setItem("reviewProblemNum", props.item.problemNum);
              sessionStorage.setItem("reviewStudyName", studyName);
              sessionStorage.setItem("reviewYear", year);
              sessionStorage.setItem("reviewMonth", month);
              navigate("/review");
            }}
          >
            코드 리뷰
          </WhiteButton>
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
