import "./AlertModal.css";
import Modal from "react-bootstrap/Modal";

const AlertRefreshModal = (props) => {
  return (
    <Modal
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      backdrop="static"
    >
      <Modal.Body className="alert-modal-body">
        <div className="alert-board-title">✨ 알림 ✨</div>
        <div className="alert-message">{props.message}</div>
        <div className="alert-close-btn">
          <button
            className="alert-btn"
            onClick={() => {
              props.onHide();
              window.location.reload();
            }}
          >
            확인
          </button>
        </div>
        <div className="alert-footer">
          <img
            src="/dinosaur_left.png"
            alt="공룡이미지"
            className="img-left"
          ></img>
          <img src="/dinosaur.png" alt="공룡이미지" className="img-right"></img>
        </div>
      </Modal.Body>
    </Modal>
  );
};

export default AlertRefreshModal;
