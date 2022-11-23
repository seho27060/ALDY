import AlertRefreshModal from "../modal/AlertRefreshModal";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";
import { deleteProblem, getProblemStage } from "../../api/study";

import "../../pages/study/StudyDetail.css";
import Button from "../styled/Button";

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
    deleteProblem(props.item.id)
      .then((res) => {
        setMessage(
          `${props.item.problemNum}번 ${props.item.problemName} 문제가 삭제되었습니다.`
        );
        setAlertRefreshModalShow(true);
      })
      .catch((err) => {
        // console.log(err);
      });
  };

  const codeStage = () => {
    getProblemStage(studyId, props.item.id)
      .then((res) => {
        setProblemStage(res.data.studyMemberDtoList);
      })
      .catch((err) => {
        // console.log(err);
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
          <Button
            greenLine
            xsmall
            onClick={() => {
              window.open(
                `https://www.acmicpc.net/problem/${props.item.problemNum}`,
                "_blank"
              );
            }}
          >
            문제 풀기
          </Button>
          <Button
            greenLine
            xsmall
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
          </Button>
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
        <div className="problem-delete-box">
          {myId === leader && (
            <Button redLine xsmall onClick={delProblem}>
              문제 삭제
            </Button>
          )}
        </div>
      </div>
    </div>
  );
};

export default ProblemListItem;
