import "../../pages/study/StudyDetail.css";
import Modal from "react-bootstrap/Modal";
import styled from "styled-components";
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

const StudyJoin = ({ studyDetail, date, modal, handleModal, problemList }) => {
  const navigate = useNavigate();
  const navigateStudySelect = () => {
    navigate("/study/select", { state: { date: date, studyId: studyId } });
  };

  const week = ["일", "월", "화", "수", "목", "금", "토"];
  const studyId = studyDetail.id;
  const studyName = studyDetail.name;
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
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
            {problemList.length !== 0 ? (
              problemList?.map((item, i) => (
                <ProblemListItem
                  key={i}
                  item={item}
                  studyId={studyId}
                  studyName={studyName}
                  year={year}
                  month={month}
                />
              ))
            ) : (
              <div className="problem-none">문제를 선정해 주세요!</div>
            )}
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
