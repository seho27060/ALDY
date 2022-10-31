import "./CodeReview.css";
import { useState, useRef } from "react";
import Modal from "react-bootstrap/Modal";
import Select from "react-select";
import Editor from '@monaco-editor/react'

const CodeReview = () => {
  const [step, setStep] = useState(1);
  const [requestModalShow, setRequestModalShow] = useState(false);
  const editorRef = useRef(null)
  function handleEditorChange(editor, monaco) {
    editorRef.current = editor;
    console.log(editorRef.current.getValue())
  }

  return (
    <main className="review-main">
      <RequestModal
        show={requestModalShow}
        onHide={() => setRequestModalShow(false)}
      />
      <section className="review-header">
        {step === 1 && <h2 className="review-orange">✨ 코드리뷰 1단계 : 코드 제출하기 ✨</h2>}
        {step === 2 && <h2 className="review-orange">✨ 코드리뷰 2단계 : 코드 주석달기 ✨</h2>}
        {step === 3 && <h2 className="review-orange">✨ 코드리뷰 3단계 : 코드 하이라이팅 ✨</h2>}
        {step === 4 && <h2 className="review-orange">✨ 코드리뷰 4단계 : 최종 코드 제출하기 ✨</h2>}
      </section>
      <section className="review-board">
        <div className="review-title">
          <p style={{ margin: "0 25px" }}>✨ 스터디이름: ssafy</p>
          <p style={{ margin: "0 25px" }}>3017번</p>
          <p style={{ margin: "0 25px" }}>가까운 수 찾기 ✨</p>
        </div>
        <div className="review-content">
          <div className="review-step">
            <p style={{ fontSize: "20px" }}>✨코드리뷰 단계✨</p>
            <button
              className={`review-step-btn ${step === 1 ? "act" : ""}`}
              onClick={() => {
                setStep(1);
              }}
            >
              1단계
            </button>
            <button
              className={`review-step-btn ${step === 2 ? "act" : ""}`}
              onClick={() => {
                setStep(2);
              }}
            >
              2단계
            </button>
            <button
              className={`review-step-btn ${step === 3 ? "act" : ""}`}
              onClick={() => {
                setStep(3);
              }}
            >
              3단계
            </button>
            <button
              className={`review-step-btn ${step === 4 ? "act" : ""}`}
              onClick={() => {
                setStep(4);
              }}
            >
              4단계
            </button>
          </div>
          <div className="review-code">
            <Editor className='review-code-editor'
              height='100%'
              language='javascript'
              theme='vs-dark'
              defaultValue={null}
              onMount={handleEditorChange}
              options={{
                fontSize:20,
                minimap:{ enabled: false},
                scrollbar:{
                  vertical: 'auto',
                  horizontal: 'auto'
              }
            }}></Editor>
          </div>
        </div>
        <div className="review-btns">
          {step === 1 ? <button className="reviewBtn">백준 연동</button> : null}
          {step === 3 ? (
            <button
              className="reviewBtn"
              onClick={() => {
                setRequestModalShow(true);
              }}
            >
              리뷰 요청하기
            </button>
          ) : (
            <button
              className="reviewBtn"
              onClick={() => {
                // 제출하는 axios 요청 추가
              }}
            >
              코드 제출하기
            </button>
          )}
        </div>
      </section>
      <section className="review-method">
        <button className="review-method-btn">이용방법</button>
        <img src="/dinosaur.png" className="review-method-img"></img>
      </section>
    </main>
  );
};

function RequestModal(props) {
  // collegue에는 user_id, 아이디가 필요
  // 데이터 줄때 value, label 형태로 줄 수 있는지?
  // 스터디원 정보를 useEffect로 받아온다.
  const [collegue, setCollegue] = useState([
    { value: "스터디원1", label: "스터디원1" },
    { value: "스터디원2", label: "스터디원1" },
    { value: "스터디원3", label: "스터디원2" },
    { value: "스터디원4", label: "스터디원3" },
    { value: "스터디원4", label: "스터디원4" },
    { value: "스터디원5", label: "스터디원5" },
    { value: "스터디원6", label: "스터디원6" },
    { value: "스터디원7", label: "스터디원7" },
  ]);
  const [selected, setSelected] = useState(null);

  return (
    <Modal
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
      backdrop="static"
    >
      <Modal.Body className="review-modal-body">
        <div className="review-modal-header">
          <div>
            <p className="review-modal-title">리뷰 요청하기</p>
            <p style={{ fontSize: "12px", color: "#646464" }}>
              스터디원을 선택해주세요
            </p>
          </div>
          <div>
            <button className="review-modal-close-btn" onClick={props.onHide}>
              X
            </button>
          </div>
        </div>
        <div className="review-modal-content">
          {collegue?.map((person) => (
            <div className="review-modal-item">
              {person.label}
              <button className="review-modal-selectBtn">✔</button>
            </div>
          ))}
          {/* <Select isMulti onChange={setSelected} options={collegue}></Select> */}
          <button
            className="review-modal-request-btn"
            onClick={() => {
              props.onHide();
              console.log(selected);
              // 서버로 리뷰 요청하는 axios 추가
            }}
          >
            요청하기
          </button>
        </div>
      </Modal.Body>
    </Modal>
  );
}

export default CodeReview;
