import "../../pages/study/StudyDetail.css";
import Modal from "react-bootstrap/Modal";
import styled from "styled-components";

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
          ></textarea>
        </div>
        <div className="study-join-btn">
          <RedButton>가입 신청하기</RedButton>
        </div>
      </Modal.Body>
    </Modal>
  );
};

export default StudyJoin;
