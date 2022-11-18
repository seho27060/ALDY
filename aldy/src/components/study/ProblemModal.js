import "../../pages/study/StudyDetail.css";
import React, { useState } from "react";
import Modal from "react-bootstrap/Modal";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import ProblemListItem from "./ProblemListItem";
import { recoilLeaderBaekjoonId } from "../../store/states";
import { useRecoilState } from "recoil";
import AlertModal from "../AlertModal";

const RedButton = styled.button`
  width: 170px;
  border-radius: 8px;
  background-color: red;
  border: 2px solid red;
  outline: none;
  color: white;
  font-weight: bold;
  padding: 4px 0px 2px 0px;
  &:hover {
    background-color: white;
    color: red;
    transition: all 200ms ease-in;
    border: 2px solid red;
  }
`;

const ProblemModal = ({
  studyDetail,
  date,
  modal,
  handleModal,
  problemList,
  setProblemList,
}) => {
  const navigate = useNavigate();
  const navigateStudySelect = () => {
    if (studyDetail.countMember < 2) {
      setMessage("스터디원이 2명 이상일 때 문제선정을 할 수 있습니다.");
      setAlertModalShow(true);
    } else {
      navigate("/study/select", { state: { date: date, studyId: studyId } });
    }
  };
  const sendLeaderId = sessionStorage.getItem("sendLeaderId");
  const myId = sessionStorage.getItem("userName");

  const week = ["일", "월", "화", "수", "목", "금", "토"];
  const studyId = studyDetail.id;
  const studyName = studyDetail.name;
  const year = date.getFullYear();
  const month = date.getMonth() + 1;

  // alert Modal
  const [message, setMessage] = useState("");
  const [alertModalShow, setAlertModalShow] = useState(false);

  return (
    <main>
      <AlertModal
        show={alertModalShow}
        onHide={() => {
          setAlertModalShow(false);
        }}
        message={message}
      />
      <Modal
        size="lg"
        show={modal}
        onHide={() => {
          handleModal();
          setProblemList([]);
        }}
        centered
      >
        <Modal.Body className="review-modal-body">
          <div className="review-modal-header">
            <div>
              <p
                className="study-underline-orange"
                style={{
                  lineHeight: "30px",
                  fontSize: "30px",
                  fontFamily: "KOFIHDrLEEJWTTF-B",
                }}
              >
                {date.getFullYear()}년 {date.getMonth() + 1}월 {date.getDate()}
                일 {week[date.getDay()]}요일
              </p>
            </div>
            <div>
              <button
                className="review-modal-close-btn"
                onClick={() => {
                  handleModal();
                  setProblemList([]);
                }}
              >
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
                    leader={studyDetail.leaderBaekjoonId}
                    year={year}
                    month={month}
                    handleModal={handleModal}
                  />
                ))
              ) : (
                <div className="problem-none">선정된 문제가 없습니다.</div>
              )}
            </div>
          </div>
          <div className="study-join-btn">
            {myId === sendLeaderId && (
              <RedButton onClick={navigateStudySelect}>문제 선정하기</RedButton>
            )}
          </div>
        </Modal.Body>
      </Modal>
    </main>
  );
};

export default ProblemModal;
