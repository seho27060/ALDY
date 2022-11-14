import "./AlertModal.css";
import Modal from "react-bootstrap/Modal";
import { useNavigate } from "react-router-dom";

const LoginAlert = (props) => {
  const navigate = useNavigate();
  return (
    <Modal
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      backdrop="static"
    >
      <Modal.Body className="alert-modal-body">
        <div className="alert-board-title">✨ 알림 ✨</div>
        <p className="alert-message">
          로그인이 필요합니다. 로그인 페이지로 이동할까요?
        </p>
        <div className="alert-close-btn">
          <button
            className="alert-btn"
            onClick={() => {
              props.onHide();
              navigate("/login");
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
          <img src="/dinosaur.png" alt="공룡이미지" className="img-right"></img>
        </div>
      </Modal.Body>
    </Modal>
  );
};

export default LoginAlert;
