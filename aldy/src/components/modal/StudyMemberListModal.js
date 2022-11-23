import StudyMember from "../study/StudyMember";
import Modal from "react-bootstrap/Modal";

const StudyMemberListModal = ({ studyDetail, modal, handleModal }) => {
  return (
    <Modal size="lg" show={modal} onHide={handleModal}>
      <Modal.Body className="review-modal-body">
        <div className="review-modal-header">
          <div>
            <div
              className="study-underline-orange"
              style={{
                lineHeight: "35px",
                fontSize: "25px",
                marginBottom: "10px",
              }}
            >
              <span>✨{studyDetail.name}✨</span>
            </div>
          </div>
          <div>
            <button className="review-modal-close-btn" onClick={handleModal}>
              X
            </button>
          </div>
        </div>
        <StudyMember id={studyDetail.id} />
      </Modal.Body>
    </Modal>
  );
};

export default StudyMemberListModal;
