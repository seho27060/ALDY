import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";
import styled from "styled-components";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { deleteProblem, getProblemStage } from "../../api/study";
import "../../pages/study/StudyDetail.css";
import AlertRefreshModal from "../AlertRefreshModal";

const WhiteButton = styled.button`
  width: 75px;
  border-radius: 8px;
  background-color: white;
  border: 2px solid rgb(40, 80, 15);
  outline: none;
  color: rgb(40, 80, 15);
  font-size: 13px;
  transition: transform 30ms ease-in;
  padding-top: 3px;
  font-weight: bold;
  &:hover {
    background-color: rgb(40, 80, 15);
    color: white;
    transition: all 200ms ease-in;
  }
`;

const RedButton = styled.button`
  width: 75px;
  border-radius: 8px;
  background-color: white;
  border: 2px solid red;
  outline: none;
  color: red;
  transition: transform 30ms ease-in;
  margin-left: auto;
  font-size: 13px;
  padding-top: 3px;
  font-weight: bold;
  &:hover {
    background-color: red;
    color: white;
    transition: all 200ms ease-in;
  }
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
  const myId = sessionStorage.getItem("userName");
  const leader = props.leader;
  const studyId = props.studyId;
  const studyName = props.studyName;
  const year = props.year;
  const month = props.month;
  const navigate = useNavigate();

  // alert modal
  const [message, setMessage] = useState("");
  const [alertRefreshModalShow, setAlertRefreshModalShow] = useState(false);

  const delProblem = () => {
    console.log(props.item);
    deleteProblem(props.item.id)
      .then((res) => {
        setMessage(
          `${props.item.problemNum}번 ${props.item.problemName} 문제가 삭제되었습니다.`
        );
        setAlertRefreshModalShow(true);
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
      <AlertRefreshModal
        show={alertRefreshModalShow}
        onHide={() => setAlertRefreshModalShow(false)}
        message={message}
      />
      <div className="problem-list-title">
        <b className="problem-name">{props.item.problemNum}번</b>
        <div className="problem-name" style={{ fontSize: "18px" }}>
          {props.item.problemName}
        </div>
        <div className="problem-list-right">
          <WhiteButton
            onClick={() => {
              window.open(
                `https://www.acmicpc.net/problem/${props.item.problemNum}`,
                "_blank"
              );
            }}
          >
            문제 풀기
          </WhiteButton>
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
            {problemStage.length >= 1 ? (
              problemStage.map((item, i) => (
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
                  </td>
                  <td className={item.process === 3 ? "black" : "gray"}>
                    3단계
                  </td>
                </tr>
              ))
            ) : (
              <div style={{ color: "rgb(150, 150, 150)", marginTop: "10px" }}>
                문제를 푼 팀원이 아직 없습니다.
              </div>
            )}
          </tbody>
        </table>

        {myId === leader && (
          <RedButton onClick={delProblem}>문제 삭제</RedButton>
        )}
      </div>
    </div>
  );
};

export default ProblemListItem;
