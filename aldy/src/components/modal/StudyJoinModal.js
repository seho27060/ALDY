import AlertRefreshModal from "./AlertRefreshModal";
import AlertModal from "./AlertModal";
import { useNavigate } from "react-router-dom";
import { useState, useRef } from "react";
import Modal from "react-bootstrap/Modal";
import { studyRegister } from "../../api/study";

import Button from "../styled/Button";
import "../../pages/study/StudyDetail.css";

const StudyJoinModal = ({ studyDetail, modal, handleModal }) => {
  const navigate = useNavigate();

  const navigateStudy = () => {
    navigate("/study/list");
  };
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
        setMessage("가입 신청이 완료되었습니다.");
        setAlertModalShow(true);
      })
      .catch((err) => {
        setMessage("가입 신청을 다시 진행해주세요.");
        setAlertRefreshModalShow(true);
      });
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
          <Button red medium onClick={onSubmit}>
            가입 신청하기
          </Button>
        </div>
      </Modal.Body>
    </Modal>
  );
};

export default StudyJoinModal;
