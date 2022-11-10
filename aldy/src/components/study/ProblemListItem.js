import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";
import styled from "styled-components";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { deleteProblem, getProblemStage } from "../../api/study";
import "../../pages/study/StudyDetail.css";

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
  const [problemStage, setProblemStage] = useState([
    {
      memberId: 0,
      baekjoonId: "",
      nickname: "",
      process: 0,
    },
  ]);
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

  const codeStage = () => {
    getProblemStage(studyId, props.item.id)
      .then((res) => {
        console.log(res.data.studyMemberDtoList);
        setProblemStage(res.data.studyMemberDtoList);
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
                codeStage();
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
        <table className="stage-table">
          <tbody>
            {problemStage.length >= 1
              ? problemStage.map((item, i) => (
                  <tr key={i}>
                    <td>
                      <b>{item.nickname}</b>
                      <div>백준ID : {item.baekjoonId}</div>
                    </td>
                    <td className={item.process === 1 ? "black" : "gray"}>
                      1단계
                    </td>
                    <td className={item.process === 2 ? "black" : "gray"}>
                      2단계
                    </td>{" "}
                    <td className={item.process === 3 ? "black" : "gray"}>
                      3단계
                    </td>{" "}
                    <td className={item.process === 4 ? "black" : "gray"}>
                      4단계
                    </td>
                  </tr>
                ))
              : null}
          </tbody>
        </table>

        <RedButton onClick={delProblem}>문제 삭제</RedButton>
      </div>
    </div>
  );
};

export default ProblemListItem;
