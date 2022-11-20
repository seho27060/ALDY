import "../../pages/study/StudyDetail.css";
import Modal from "react-bootstrap/Modal";
import styled from "styled-components";
import { useState, useEffect, useRef } from "react";
import { studyRegister } from "../../api/study";
import { useNavigate } from "react-router-dom";
import AlertModal from "../../components/AlertModal";
import AlertRefreshModal from "../../components/AlertRefreshModal";

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

const StudyJoinModal = ({ studyDetail, modal, handleModal }) => {
  const navigate = useNavigate();

  const navigateStudy = () => {
    navigate("/study/list");
  };
  // console.log(studyDetail, "안녕하세요, 저는 모달창입니다.");
  const messageInput = useRef(null);
  const [sendJoin, setSendJoin] = useState({
    studyId: studyDetail.id,
    message: "",
  });

  const [message, setMessage] = useState("");
  const [alertModalShow, setAlertModalShow] = useState(false);
  const [alertRefreshModalShow, setAlertRefreshModalShow] = useState(false);

  const onSubmit = () => {
    setSendJoin((sendJoin.message = messageInput.current.value));
    studyRegister(sendJoin)
      .then((res) => {
        // alert("가입 신청이 완료되었습니다.");
        // navigateStudy();
        setMessage("가입 신청이 완료되었습니다.");
        setAlertModalShow(true);
      })
      .catch((err) => {
        // alert("가입 신청을 다시 진행해주세요.");
        setMessage("가입 신청을 다시 진행해주세요.");
        setAlertRefreshModalShow(true);
      });
    // console.log(messageInput.current.value, "제출");
    // console.log(studyDetail.id, "I'm study id number.");
  };
  return (
    <Modal size="lg" show={modal} onHide={handleModal}>
      <AlertModal
        show={alertModalShow}
        onHide={() => {
          setAlertModalShow(false);
          navigateStudy();
        }}
        message={message}
      />
      <AlertRefreshModal
        show={alertRefreshModalShow}
        onHide={() => setAlertRefreshModalShow(false)}
        message={message}
      />
      <Modal.Body className="review-modal-body">
        <div className="review-modal-header">
          <div>
            <div
              className="study-underline-orange"
              style={{ fontSize: "32px", marginBottom: "10px" }}
            >
              스터디 가입 신청
            </div>
            <div>
              {studyDetail.name}
              <span style={{ color: "rgb(120, 120, 120)" }}>
                에 가입하기 위해 가입 신청 메세지를 작성해주세요!!
              </span>
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
            placeholder="가입신청 메세지를 작성해주세요."
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

export default StudyJoinModal;
