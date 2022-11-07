import "./CodeReview.css";
import { useState, useRef, useEffect } from "react";
import Modal from "react-bootstrap/Modal";
import Select from "react-select";
import Editor from '@monaco-editor/react'
import { useRecoilState } from "recoil";
import { recoilMyCode, recoilStep } from "../../store/states";
import { getEditedCodes, saveCode } from "../../api/code";
import { useNavigate } from "react-router-dom";

const CodeReview = () => {
  // studyInfo
  const studyInfo = sessionStorage.getItem('studyInfo')
  const studyId = studyInfo.id
  const studyName = studyInfo.name
  const problemId = studyInfo.problemId
  const problemNumber = studyInfo.problemNumber
  //
  const [selected, setSelected] = useState(null)
  const [editedCode, setEditedCode] = useState(null)
  const sessionEditCode = sessionStorage.getItem('editedCode')
  const [step, setStep] = useRecoilState(recoilStep);
  const [requestModalShow, setRequestModalShow] = useState(false);
  const [stepModalShow1, setStepModalShow1] = useState(false);
  const [stepModalShow2, setStepModalShow2] = useState(false);
  const [stepModalShow3, setStepModalShow3] = useState(false);
  const [stepModalShow4, setStepModalShow4] = useState(false);
  const [language, setLanguage] = useState('python')
  const [editedCodeList, setEditedCodeList] = useState([])
  const [subimtCode, setSumbitCode] = useState(null)
  const myCode = sessionStorage.getItem('mycode')
  const [yourCode, setYourCode] = useState("")
  const navigate = useNavigate();
  const [codeOneTwoThree, setCodeOneTwoThree] = useState({
    code:"",  
    process: 1,
    studyId: 0,
    problemId: 0
  })

  function handleEditorChange(value, event) {
    setCodeOneTwoThree((prev)=>{
      return {...prev, code: value}
    })
  }
  const modals = {
    1:<StepModal1 show={stepModalShow1} onHide={()=>{setStepModalShow1(false)}}></StepModal1>,
    2:<StepModal2 show={stepModalShow2} onHide={()=>{setStepModalShow2(false)}}></StepModal2>,
    3:<StepModal3 show={stepModalShow3} onHide={()=>{setStepModalShow3(false)}}></StepModal3>,
    4:<StepModal4 show={stepModalShow4} onHide={()=>{setStepModalShow4(false)}}></StepModal4>
  }

  useEffect(()=>{
    const studyId = sessionStorage.getItem('studyId')
    const problemId = sessionStorage.getItem('problemId')
    getEditedCodes(studyId, problemId)
    .then((res)=>{
      console.log(res.data)
      setEditedCodeList(res.data)
    })
  }, [])

  return (
    <main className="review-main">
      <RequestModal
        show={requestModalShow}
        onHide={() => setRequestModalShow(false)}
      />
      {
        modals[step]
      }
      <section className="review-header">
        {step === 1 && 
          <div className="review-header-step">
            <img src='/dinosaur_hello.gif' className="review-header-step-img" style={{'marginLeft':'100px'}}></img>
            <h2 className="review-orange">✨ 코드리뷰 1단계 : 코드 제출하기 ✨</h2>
            <button className="review-header-step-btn" onClick={()=>{
              if (step === 1) {
                setStepModalShow1(true)
              } else if (step === 2) {
                setStepModalShow2(true)
              } else if (step === 3) {
                setStepModalShow3(true)
              } else if (step === 4) {
                setStepModalShow4(true)
              }
            }}>이용방법</button>
            <img src="/dinosaur.png" className="review-header-step-img review-header-special-img" style={{'marginRight':'40px'}}></img>
          </div>
        }
        {step === 2 &&
          <div className="review-header-step">
            <img src='/dinosaur_hello.gif' className="review-header-step-img" style={{'marginLeft':'100px'}}></img>
            <h2 className="review-orange">✨ 코드리뷰 2단계 : 코드 주석달기 ✨</h2>
            <button className="review-header-step-btn" onClick={()=>{
              if (step === 1) {
                setStepModalShow1(true)
              } else if (step === 2) {
                setStepModalShow2(true)
              } else if (step === 3) {
                setStepModalShow3(true)
              } else if (step === 4) {
                setStepModalShow4(true)
              }
            }}>이용방법</button>
            <img src="/dinosaur.png" className="review-header-step-img review-header-special-img" style={{'marginRight':'40px'}}></img>
          </div>
        
        }
        {step === 3 &&
        <div className="review-header-step">
          <img src='/dinosaur_hello.gif' className="review-header-step-img" style={{'marginLeft':'100px'}}></img>
          <h2 className="review-orange">✨ 코드리뷰 3단계 : 코드 하이라이팅 ✨</h2>
          <button className="review-header-step-btn" onClick={()=>{
              if (step === 1) {
                setStepModalShow1(true)
              } else if (step === 2) {
                setStepModalShow2(true)
              } else if (step === 3) {
                setStepModalShow3(true)
              } else if (step === 4) {
                setStepModalShow4(true)
              }
            }}>이용방법</button>
            <img src="/dinosaur.png" className="review-header-step-img review-header-special-img" style={{'marginRight':'40px'}}></img>
        </div>
        }
        {step === 4 &&
          <div className="review-header-step">
            <img src='/dinosaur_hello.gif' className="review-header-step-img" style={{'marginLeft':'100px'}}></img>
            <h2 className="review-orange">✨ 코드리뷰 4단계 : 최종 코드 제출하기 ✨</h2>
            <button className="review-header-step-btn" onClick={()=>{
              if (step === 1) {
                setStepModalShow1(true)
              } else if (step === 2) {
                setStepModalShow2(true)
              } else if (step === 3) {
                setStepModalShow3(true)
              } else if (step === 4) {
                setStepModalShow4(true)
              }
            }}>이용방법</button>
            <img src="/dinosaur.png" className="review-header-step-img review-header-special-img" style={{'marginRight':'40px'}}></img>
          </div>
        }
      </section>
      <section className="review-board">
        <div className="review-title">
          <p style={{ margin: "0 25px" }}>✨ 스터디이름: ssafy</p>
          <p style={{ margin: "0 25px" }}>3017번</p>
          <p style={{ margin: "0 25px" }}>가까운 수 찾기 ✨</p>
        </div>
        <div className="review-language-select">
          <select name='language' id='language-select' onChange={(e)=>{setLanguage(e.target.value)}}>
            <option value=''>--사용할 언어를 선택해주세요--</option>
            <option value='c++'>C++</option>
            <option value='java'>Java</option>
            <option value='python'>Python</option>
            <option value='javascript'>Javascript</option>
            <option value='sql'>SQL</option>
          </select>
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
                // 4단계 클릭하면 axios로 내가 첨삭받은 코드 원래 내코드 불러와서 usestate의 myCode, yourCode에 저장한다.
              }}
            >
              4단계
            </button>
          </div>
          <div className="review-code">
            {
              step === 4 ? 
                <div className="step-four-main">
                  <div className="step-four-your-code">
                    <div className="step-four-type">
                      <span>리뷰 받은 코드 
                        {/* <select className="reviewer-select" onChange={(e)=>{
                          setEditedCode(e.target.value)
                          sessionStorage.setItem('editedCode', e.target.value)
                          navigate('/review')
                          }}>
                          <option>---선택하기</option>
                          {
                            editedCodeList?.map((item)=><option value={item.editedCode}>{item.sender.baekjoonId}</option>)
                          }
                        </select> */}
                      </span>
                    </div>
                    <Editor className='review-code-editor'
                            height='95%'
                            language={language}
                            theme='vs-dark'
                            defaultValue={sessionEditCode}
                            onMount={handleEditorChange} // 바꿔야함
                            options={{
                              fontSize:20,
                              minimap:{ enabled: false},
                              scrollbar:{
                                vertical: 'auto',
                                horizontal: 'auto'
                            }
                          }}></Editor>
                  </div>
                  <div className="step-four-my-code">
                  <div className="step-four-type">내가 작성했던 코드</div>
                    <Editor className='review-code-editor'
                          height='95%'
                          language={language}
                          theme='vs-dark'
                          defaultValue={myCode}
                          onMount={handleEditorChange} // 바꿔야함
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
              :
            <Editor className='review-code-editor'
              height='95%'
              language={language}
              theme='vs-dark'
              defaultValue={null}
              onChange={handleEditorChange}
              options={{
                fontSize:20,
                minimap:{ enabled: false},
                scrollbar:{
                  vertical: 'auto',
                  horizontal: 'auto'
              }
            }}></Editor>
            }
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
                saveCode(codeOneTwoThree)
              }}
            >
              코드 제출하기
            </button>
          )}
        </div>
      </section>
      {/* <section className="review-method">
        <button className="review-method-btn" onClick={()=>{
          if (step === 1) {
            setStepModalShow1(true)
          } else if (step === 2) {
            setStepModalShow2(true)
          } else if (step === 3) {
            setStepModalShow3(true)
          } else if (step === 4) {
            setStepModalShow4(true)
          }
        }}>이용방법</button>
        <img src="/dinosaur.png" className="review-method-img"></img>
      </section> */}
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


function StepModal1(props) {
  return (
    <Modal
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Body className="step-modal-body">
        <div className="step-modal-header">
          <h2 className="step-modal-title">코드 리뷰 1단계 방법 알아보기</h2>
        </div>
        <div className="step-modal-content">
          <p>
            <span>백준에서</span>
            <span className='highlight'>풀었던 코드</span>
            <span>를 제출해주세요.</span><br></br>
            {/* <span>해당 문제의 풀이코드를</span>
            <span className='highlight'> 공개</span>
            <span>로 설정하였을 경우</span><br></br>
            <span className='highlight'>백준연동</span>
            <span>으로 코드를 가져올 수 있습니다.</span> */}
          </p>
        </div>
      </Modal.Body>
    </Modal>
  );
}

function StepModal2(props) {
  return (
    <Modal
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Body className="step-modal-body">
        <div className="step-modal-header">
            <h2 className="step-modal-title">코드 리뷰 2단계 방법 알아보기</h2>
        </div>
        <div className="step-modal-content">
            <p>
              <span>스터디원들이 이해하기 편하도록</span><br></br>
              <span className='highlight'>코드설명</span>
              <span>이 필요한 부분에</span>
              <span className='highlight'> 주석</span>
              <span>을 달아주세요.</span>
            </p>
        </div>
      </Modal.Body>
    </Modal>
  );
}

function StepModal3(props) {
  return (
    <Modal
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Body className="step-modal-body">
        <div className="step-modal-header">
          <h2 className="step-modal-title">코드 리뷰 3단계 방법 알아보기</h2>
        </div>
        <div className="step-modal-content">
          <p>
            <span className='highlight'>코드리뷰</span>
            <span>를 받고싶은 부분을</span><br></br>
            <span className='highlight'>하이라이팅</span>
            <span>을 사용해 선택해주세요!</span><br></br>
            <span>선택을 완료한 후 리뷰요청 버튼을 누르고</span><br></br>
            <span>리뷰요청 창에서 스터디원을 선택한 후 요청해주세요.</span>
          </p>
        </div>
      </Modal.Body>
    </Modal>
  );
}

function StepModal4(props) {
  return (
    <Modal
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Body className="step-modal-body">
        <div className="step-modal-header">
          <h2 className="step-modal-title">코드 리뷰 4단계 방법 알아보기</h2>
        </div>
        <div className="step-modal-content">
          <p>
            <span className='highlight'>코드리뷰</span>
            <span>를 받은 부분과</span><br></br>
            <span className='highlight'>내가 작성한 코드</span>
            <span>를 비교해보세요!</span><br></br>
            <span>코드리뷰를 받은 내용을 바탕으로</span><br></br>
            <span>최종코드를 완성해서 제출해보세요.</span>
          </p>
        </div>
      </Modal.Body>
    </Modal>
  );
}


export default CodeReview;
