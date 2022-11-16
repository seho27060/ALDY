import "./CodeCorrect.css";
import { useState, useEffect, useRef } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Editor from '@monaco-editor/react'
import { useRecoilState } from "recoil";
import { correctCode } from "../../store/states";
import { codeReply } from "../../api/code";
import { useNavigate } from "react-router-dom";
import AlertModal from "../../components/AlertModal";
import ReviewListAlert from "../../components/ReviewListAlert";

const CodeCorrect = () => {
  const [alertModalShow, setAlertModalShow] = useState(false)
  const [reviewAlertShow, setReviewAlertShow] = useState(false)
  const [message, setMessage] = useState('')
  const previousCode = sessionStorage.getItem('previousCode')
  const studyName = sessionStorage.getItem('studyName')
  const sender = sessionStorage.getItem('sender')
  const problemName = sessionStorage.getItem('problemName')
  const problemId = sessionStorage.getItem('problemId')
  const createDate = sessionStorage.getItem('createDate')
  const studyId = sessionStorage.getItem('studyId')
  const [language, setLanguage] = useState('python')
  const [reply, setReply] = useState({
    code:"",
    receiverId: sender,
    problemId: Number(problemId),
    studyId: Number(studyId)
  })
  const navigate = useNavigate()
  function handleEditorChange(value, event) {
    setReply((prev)=>{
      return {...prev, code: value}
    })
  }
  return (
    <main className="correct-main">
      <AlertModal 
      show={alertModalShow}
      onHide={() => setAlertModalShow(false)}
      message={message}
      />
      <ReviewListAlert 
      show={reviewAlertShow}
      onHide={() => setReviewAlertShow(false)}
      message={message}
      />
      <section className="correct-header">
        <p>
          <span>✨ 요청받은 코드에 대한</span>
          <span className="highlight">코드첨삭</span>
          <span>을 진행해주세요 ✨</span>
        </p>
        <h2 className="correct-orange">이번 주 스터디 선정 문제</h2>
      </section>
      <section className="correct-title">
        <Container>
          <Row style={{ color: "#28500F", fontWeight: "bold" }}>
            <Col>스터디 이름</Col>
            <Col>보낸 사람</Col>
            <Col>문제 이름</Col>
            <Col>문제 번호</Col>
            <Col>요청 날짜</Col>
          </Row>
          <hr></hr>
          <Row>
            <Col>{studyName}</Col>
            <Col>{sender}</Col>
            <Col>{problemName}</Col>
            <Col>{problemId}</Col>
            <Col>{createDate}</Col>
          </Row>
        </Container>
      </section>
      <section className="correct-board">
        <div className="correct-board-title">
          <p style={{ margin: "0 25px", fontFamily:'uhbeeBold', fontSize:'28px' }}>✨ {problemId}번</p>
          <p style={{ margin: "0 25px", fontFamily:'uhbeeBold', fontSize:'28px' }}>{problemName} ✨</p>
        </div>
        <div className="correct-language-select">
          <select name='language' id='language-select' onChange={(e)=>{setLanguage(e.target.value)}}>
            <option value=''>--사용할 언어를 선택해주세요--</option>
            <option value='c++'>C++</option>
            <option value='java'>Java</option>
            <option value='python'>Python</option>
            <option value='javascript'>Javascript</option>
            <option value='sql'>SQL</option>
          </select>
        </div>
        <div className="correct-content">
          <div className="correct-your-code-box">
            <div className="correct-code-type">요청 받은 코드</div>
            <div className="correct-your-code">
              <Editor
              className='review-code-editor'
              language={language}
              height='100%'
              theme='vs-dark'
              defaultValue={previousCode}
              options={{
                fontSize:20,
                minimap:{ enabled: false},
                scrollbar:{
                  vertical: 'auto',
                  horizontal: 'auto'
              }
            }}
              ></Editor>
            </div>
          </div>
          <div className="correct-my-code-box">
            <div className="correct-code-type">내가 첨삭한 코드 (여기에 코드를 작성하세요)</div>
            <div className="correct-my-code">
              <Editor
                className='review-code-editor'
                language={language}
                height='100%'
                theme='vs-dark'
                defaultValue={previousCode}
                onChange={handleEditorChange}
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
        </div>
        <div className="correct-btns">
          <button className="correctBtn" onClick={()=>{
            console.log(reply)
            codeReply(reply)
            .then(()=>{
              setMessage('답장을 보냈습니다.')
              setReviewAlertShow(true)
              // alert('답장을 보냈습니다.')
              // navigate('/review/list')
            })
            .catch((err)=>{
              setMessage('답장 보내기에 실패했습니다.')
              setAlertModalShow(true)
              // alert('답장 보내기에 실패했습니다.')
            })
          }}>답장 보내기</button>
        </div>
      </section>
    </main>
  );
};

export default CodeCorrect;
