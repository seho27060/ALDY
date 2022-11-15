import "./AlertModal.css";
import Modal from "react-bootstrap/Modal";
import { useNavigate } from "react-router-dom";
import { deleteStudy } from "../api/study";
import AlertModal from "./AlertModal";
import { useState } from "react";

const StudyDeleteAlert = (props) => {
  const navigate = useNavigate();
  // 모달
  const [message, setMessage] = useState("");
  const [alertModalShow, setAlertModalShow] = useState(false);

  const deleteMyStudy = () => {
    deleteStudy(props.id)
      .then((res) => {
        setMessage(`스터디(${props.name})가 삭제되었습니다.`);
        setAlertModalShow(true);
        // console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <main>
      <AlertModal
        show={alertModalShow}
        onHide={() => {
          setAlertModalShow(false);
          navigate("/study/list");
        }}
        message={message}
      />
      <Modal
        {...props}
        size="md"
        aria-labelledby="contained-modal-title-vcenter"
        backdrop="static"
      >
        <Modal.Body className="alert-modal-body">
          <div className="alert-board-title">✨ 알림 ✨</div>
          <p className="alert-message">
            스터디 ({props.name})를 정말 삭제하시겠습니까?
          </p>
          <div className="alert-close-btn">
            <button
              className="alert-btn"
              onClick={() => {
                props.onHide();
                deleteMyStudy();
                setAlertModalShow(true);
              }}
            >
              확인
            </button>
            <button
              className="alert-btn"
              style={{ marginLeft: "10px" }}
              onClick={() => {
                props.onHide();
              }}
            >
              취소
            </button>
          </div>
          <div className="alert-footer">
            <img
              src="/dinosaur_left.png"
              alt="공룡이미지"
              className="img-left"
            ></img>
            <img
              src="/dinosaur.png"
              alt="공룡이미지"
              className="img-right"
            ></img>
          </div>
        </Modal.Body>
      </Modal>
    </main>
  );
};

export default StudyDeleteAlert;
