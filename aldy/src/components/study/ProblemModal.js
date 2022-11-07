import "../../pages/study/StudyDetail.css";
import Modal from "react-bootstrap/Modal";
import styled from "styled-components";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import ProblemListItem from "./ProblemListItem";

const RedButton = styled.button`
  width: 170px;
  border-radius: 8px;
  background-color: red;
  border: none;
  outline: none;
  color: white;
  font-weight: bold;
  transition: transform 30ms ease-in;
`;

const StudyJoin = ({ studyDetail, date, modal, handleModal }) => {
  const navigate = useNavigate();
  const navigateStudySelect = () => {
    navigate("/study/select", { state: { date: date } });
  };

  const week = ["일", "월", "화", "수", "목", "금", "토"];
  const [problemList, setProblemList] = useState(null);
  const studyId = studyDetail.id;
  const studyName = studyDetail.name;

  useEffect(() => {
    setProblemList([
      {
        problemId: "3017",
        problemName: "가까운 수 찾기",
        problemNumber: "3/6",
        study1: "1단계",
        study2: "3단계",
        study3: "2단계",
        study4: "4단계",
        study5: "3단계",
      },
      {
        problemId: "14503",
        problemName: "로봇 청소기",
        problemNumber: "2/6",
        study1: "1단계",
        study2: "2단계",
        study3: "3단계",
        study4: "4단계",
        study5: "3단계",
      },
      {
        problemId: "9205",
        problemName: "맥주 마시면서 걸어가기",
        problemNumber: "4/6",
        study1: "2단계",
        study2: "3단계",
        study3: "2단계",
        study4: "2단계",
        study5: "3단계",
      },
    ]);
  }, []);

  return (
    <Modal size="lg" show={modal} onHide={handleModal}>
      <Modal.Body className="review-modal-body">
        <div className="review-modal-header">
          <div>
            <h3 className="study-detail-title">
              <span>
                {date.getFullYear()}년 {date.getMonth() + 1}월 {date.getDate()}
                일 {week[date.getDay()]}요일
              </span>
            </h3>
          </div>
          <div>
            <button className="review-modal-close-btn" onClick={handleModal}>
              X
            </button>
          </div>
        </div>
        <div className="review-modal-content">
          <div className="problem-list-box">
            {problemList?.map((item, problemId) => (
              <ProblemListItem key={problemId} item={item} />
            ))}
          </div>
        </div>
        <div className="study-join-btn">
          <RedButton onClick={navigateStudySelect}>문제 선정하기</RedButton>
        </div>
      </Modal.Body>
    </Modal>
  );
};

export default StudyJoin;
