import "../../pages/study/StudyDetail.css";
import Modal from "react-bootstrap/Modal";
import styled from "styled-components";
import { useState, useEffect, useRef } from "react";
import { studyRegister } from "../../api/study";
import { useNavigate } from "react-router-dom";

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

const StudyJoin = ({ studyDetail, modal, handleModal }) => {
  const navigate = useNavigate();

  const navigateStudy = () => {
    navigate("/study/list");
  };
  console.log(studyDetail, "안녕하세요, 저는 모달창입니다.");
  const messageInput = useRef(null);
  const [sendJoin, setSendJoin] = useState({
    studyId: studyDetail.id,
    message: "",
  });
  const onSubmit = () => {
    setSendJoin((sendJoin.message = messageInput.current.value));
    studyRegister(sendJoin)
      .then((res) => {
        alert("가입 신청이 완료되었습니다.");
        navigateStudy();
      })
      .catch((err) => {
        alert("가입 신청을 다시 진행해주세요.");
      });
    // console.log(messageInput.current.value, "제출");
    // console.log(studyDetail.id, "I'm study id number.");
  };
  return (
    <Modal size="lg" show={modal} onHide={handleModal}>
      <Modal.Body className="review-modal-body">
        <div className="review-modal-header">
          <div>
            <h3 className="study-detail-title">
              <span>스터디 가입 신청</span>
            </h3>
            <div>
              {studyDetail.name}에 가입하기 위해 가입 신청 메세지를
              작성해주세요!!
            </div>
          </div>
          <div>
            <button className="review-modal-close-btn" onClick={handleModal}>
              X
            </button>
          </div>
        </div>
        <div className="review-modal-content">
          <textarea
            className="join-message"
            placeholder=" 가입신청 메세지를 작성해주세요."
            ref={messageInput}
          ></textarea>
        </div>
        <div className="study-join-btn">
          <RedButton onClick={onSubmit}>가입 신청하기</RedButton>
        </div>
      </Modal.Body>
    </Modal>
  );
};

export default StudyJoin;
