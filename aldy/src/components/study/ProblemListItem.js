import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";
import styled from "styled-components";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { deleteProblem } from "../../api/study";

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
  font-size: 12px;
  padding: 2.5px 0px;
`;

const ProblemListItem = (props) => {
  const [dropdown, setDropdown] = useState("none");
  const studyId = props.studyId;
  const studyName = props.studyName;
  const year = props.year;
  const month = props.month;
  const handleModal = props.handleModal;
  const navigate = useNavigate();

  const delProblem = () => {
    console.log(props.item);
    deleteProblem(props.item.id)
      .then((res) => {
        alert(
          `${props.item.problemNum}번 ${props.item.problemName} 문제가 삭제되었습니다.`
        );
        handleModal();
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  };

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
        <RedButton onClick={delProblem}>문제 삭제</RedButton>
      </div>
    </div>
  );
};

export default ProblemListItem;
