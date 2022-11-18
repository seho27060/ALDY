import "./CodeReview.css";
import { useState, useRef, useEffect } from "react";
import AlertModal from "../../components/AlertModal"; // alert 정의
import AlertRefreshModal from "../../components/AlertRefreshModal";
import Modal from "react-bootstrap/Modal";
import Select from "react-select";
import Editor from "@monaco-editor/react";
import { useRecoilState } from "recoil";
import { recoilMyCode, recoilStep, isFooter, isNav } from "../../store/states";
import {
  getEditedCodes,
  saveCode,
  getCode,
  reviewRequest,
} from "../../api/code";
import { useNavigate } from "react-router-dom";
import { getStudyMember } from "../../api/study";
import ReviewListAlert from "../../components/ReviewListAlert";

const CodeReview = () => {
  const [footer, setFooter] = useRecoilState(isFooter);
  const [nav, setNav] = useRecoilState(isNav);
  setFooter(false);
  setNav(false);
  // api에서 받아온 코드들의 키값을 firstProcessCode를 1로 바꿔주는 변환
  // const convertCodes = {2:"firstProcessCode"}
  // studyInfo
  const studyId = sessionStorage.getItem("reviewStudyId");
  const studyName = sessionStorage.getItem("reviewStudyName");
  const problemId = sessionStorage.getItem("reviewProblemId");
  const problemNum = sessionStorage.getItem("reviewProblemNum");
  const problemName = sessionStorage.getItem("reviewProblemName");
  // const year = sessionStorage.getItem('reviewYear')
  // const month = sessionStorage.getItem('reviewMonth')
  const isFinal = sessionStorage.getItem("isFinal") || "";
  //
  const [selected, setSelected] = useState(null);
  const sessionEditCode = sessionStorage.getItem("editedCode");
  const [step, setStep] = useState(0);
  // alert를 위한 정의
  const [alertModalShow, setAlertModalShow] = useState(false);
  const [message, setMessage] = useState("");
  // alert를 위한 정의
  // alertRefresh를 위한 정의
  const [reviewAlertShow, setReviewAlertShow] = useState(false);
  const [alertRefreshModalShow, setAlertRefreshModalShow] = useState(false);
  const [requestModalShow, setRequestModalShow] = useState(false);
  const [stepModalShow1, setStepModalShow1] = useState(false);
  const [stepModalShow2, setStepModalShow2] = useState(false);
  const [stepModalShow3, setStepModalShow3] = useState(false);
  const [language, setLanguage] = useState("python");
  const myCode = sessionStorage.getItem("mycode");
  const navigate = useNavigate();
  const [codes, setCodes] = useState({});
  const [defaultCode1, setDefaultCode1] = useState("");
  const [defaultCode2, setDefaultCode2] = useState("");
  const navigateMain = () => {
    navigate("/");
    setNav(true);
    setFooter(true);
  };

  useEffect(() => {
    setSubmitOneTwoThree((prev) => {
      return { ...prev, process: step };
    });
    setSubmitCode((prev) => {
      return { ...prev, process: step };
    });
    if (step === 1) {
      setStepModalShow1(true);
    }
    if (step === 2) {
      setStepModalShow2(true);
    }
    if (step === 3 && !isFinal) {
      setStepModalShow3(true);
    }
  }, [step]);

  const [submitOneTwoThree, setSubmitOneTwoThree] = useState({
    code: "",
    process: step,
    studyId: Number(studyId),
    problemId: Number(problemId),
    // calendarMonth: Number(month),
    // calendarYear: Number(year),
  });

  const [submitCode, setSubmitCode] = useState({
    code: myCode,
    process: step,
    studyId: Number(studyId),
    problemId: Number(problemId),
    // calendarMonth: Number(month),
    // calendarYear: Number(year),
  });

  function handleEditorChange(value, event) {
    setSubmitOneTwoThree((prev) => {
      return { ...prev, code: value };
    });
  }

  function handleEditorChange3(value, event) {
    setSubmitCode((prev) => {
      return { ...prev, code: value };
    });
  }

  const modals = {
    1: (
      <StepModal1
        problemNum={problemNum}
        show={stepModalShow1}
        onHide={() => {
          setStepModalShow1(false);
        }}
      ></StepModal1>
    ),
    2: (
      <StepModal2
        show={stepModalShow2}
        onHide={() => {
          setStepModalShow2(false);
        }}
      ></StepModal2>
    ),
    3: (
      <StepModal3
        show={stepModalShow3}
        onHide={() => {
          setStepModalShow3(false);
        }}
      ></StepModal3>
    ),
    // 4:<StepModal4 show={stepModalShow4} onHide={()=>{setStepModalShow4(false)}}></StepModal4>
  };

  useEffect(() => {
    getCode(studyId, problemId)
      .then((res) => {
        console.log(res.data);
        // setStep(res.data.currentProcess+1)
        setCodes(res.data);
        // 1단계 작성해야한다면 step을 1단계로 설정
        if (res.data.currentProcess === 0) {
          setStep(1);
        }
        // 2단계 작성해야한다면 2단계 코드 초기 값을 1단계 코드로 주고, 1단계 코드 초기값도 1단계 코드로 준다. step은 2단계로 설정
        if (res.data.currentProcess === 1) {
          setStep(2);
          setDefaultCode2(res.data.firstProcessCode.code);
          setDefaultCode1(res.data.firstProcessCode.code);
        }
        // 3단계를 작성해야한다면 step은 2단계로 설정 3단계는 코드리뷰 리스트에서만 들어갈 수 있음
        if (res.data.currentProcess === 2) {
          setStep(3);
          setDefaultCode1(res.data.firstProcessCode.code);
          setDefaultCode2(res.data.secondProcessCode.code);
        }
        if (res.data.currentProcess === 3) {
          setStep(3);
          setDefaultCode1(res.data.firstProcessCode.code);
          setDefaultCode2(res.data.secondProcessCode.code);
        }
      })
      .catch();
  }, []);

  // 최종제출은 세션스토리지 이용! (코드리뷰 4단계 버튼으로만 가능, 달력 모달에서 문제풀기는 3단계(리뷰요청) 제출하면 제출로 바뀌게 해주기(api필요))
  // useEffect(()=>{
  //   const studyId = sessionStorage.getItem('studyId')
  //   const problemId = sessionStorage.getItem('problemId')
  //   getEditedCodes(studyId, problemId)
  //   .then((res)=>{
  //     console.log(res.data)
  //     setEditedCodeList(res.data)
  //   })
  // }, [])

  return (
    <main className="review-main">
      <RequestModal
        studyId={studyId}
        problemId={problemId}
        show={requestModalShow}
        onHide={() => setRequestModalShow(false)}
        setAlertModalShow={setAlertModalShow}
        setMessage={setMessage}
        setReviewAlertShow={setReviewAlertShow}
      />
      <AlertModal
        show={alertModalShow}
        onHide={() => setAlertModalShow(false)}
        message={message}
      />
      <AlertRefreshModal
        show={alertRefreshModalShow}
        onHide={() => setAlertRefreshModalShow(false)}
        message={message}
      />
      <ReviewListAlert
        show={reviewAlertShow}
        onHide={() => setReviewAlertShow(false)}
        message={message}
      />
      {modals[step]}
      <section className="review-header">
        {step === 1 && (
          <div className="review-header-step">
            <div
              onClick={navigateMain}
              className="nav-title"
              style={{ marginLeft: "40px", fontSize: "60px" }}
            >
              ALDY
            </div>
            {/* <img
              src="/dinosaur_hello.gif"
              className="review-header-step-img"
              // style={{ marginLeft: "100px" }}
            ></img> */}
            <h2 className="review-orange">✨ 코드리뷰 1단계 : 코드 ✨</h2>
            <button
              className="review-header-step-btn"
              onClick={() => {
                if (step === 1) {
                  setStepModalShow1(true);
                } else if (step === 2) {
                  setStepModalShow2(true);
                } else if (step === 3) {
                  setStepModalShow3(true);
                }
                // else if (step === 4) {
                //   setStepModalShow4(true)
                // }
              }}
            >
              이용방법
            </button>
            <img
              src="/dinosaur.png"
              className="review-header-step-img review-header-special-img"
              style={{ marginRight: "40px" }}
            ></img>
          </div>
        )}
        {step === 2 && (
          <div className="review-header-step">
            <div
              onClick={navigateMain}
              className="nav-title"
              style={{ marginLeft: "40px", fontSize: "60px" }}
            >
              ALDY
            </div>
            {/* <img
            src="/dinosaur_hello.gif"
            className="review-header-step-img"
            // style={{ marginLeft: "100px" }}
          ></img> */}
            <h2 className="review-orange">
              ✨ 코드리뷰 2단계 : 코드 주석달기 ✨
            </h2>
            <button
              className="review-header-step-btn"
              onClick={() => {
                if (step === 1) {
                  setStepModalShow1(true);
                } else if (step === 2) {
                  setStepModalShow2(true);
                } else if (step === 3) {
                  setStepModalShow3(true);
                }
                // else if (step === 4) {
                //   setStepModalShow4(true)
                // }
              }}
            >
              이용방법
            </button>
            <img
              src="/dinosaur.png"
              className="review-header-step-img review-header-special-img"
              style={{ marginRight: "40px" }}
            ></img>
          </div>
        )}
        {step === 3 && (
          <div className="review-header-step">
            <div
              onClick={navigateMain}
              className="nav-title"
              style={{ marginLeft: "40px", fontSize: "60px" }}
            >
              ALDY
            </div>
            {/* <img
            src="/dinosaur_hello.gif"
            className="review-header-step-img"
            // style={{ marginLeft: "100px" }}
          ></img> */}
            <h2 className="review-orange">
              ✨ 코드리뷰 3단계 : 최종 코드 제출하기 ✨
            </h2>
            <button
              className="review-header-step-btn"
              onClick={() => {
                if (step === 1) {
                  setStepModalShow1(true);
                } else if (step === 2) {
                  setStepModalShow2(true);
                } else if (step === 3) {
                  setStepModalShow3(true);
                }
                // else if (step === 4) {
                //   setStepModalShow4(true)
                // }
              }}
            >
              이용방법
            </button>
            <img
              src="/dinosaur.png"
              className="review-header-step-img review-header-special-img"
              style={{ marginRight: "40px" }}
            ></img>
          </div>
        )}
        {/* {step === 4 &&
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
        } */}
      </section>
      <section className="review-board">
        <div className="review-title">
          <p
            style={{
              margin: "0 25px",
              fontFamily: "uhbeeBold",
              fontSize: "24px",
            }}
          >
            ✨ {studyName}
          </p>
          <p
            style={{
              margin: "0 25px",
              fontFamily: "uhbeeBold",
              fontSize: "24px",
            }}
          >
            {problemNum}번
          </p>
          <p
            style={{
              margin: "0 25px",
              fontFamily: "uhbeeBold",
              fontSize: "24px",
            }}
          >
            {problemName} ✨
          </p>
        </div>
        <div className="review-language-select">
          <select
            name="language"
            id="language-select"
            onChange={(e) => {
              setLanguage(e.target.value);
            }}
            className="language-select-box"
          >
            {/* <option value="">--언어 선택--</option> */}
            <option value="python">Python</option>
            <option value="cpp">C++</option>
            <option value="java">Java</option>
            <option value="javascript">Javascript</option>
            <option value="sql">SQL</option>
          </select>
        </div>
        <div className="review-content">
          <div className="review-step">
            <p style={{ fontSize: "24px", fontFamily: "uhbeeBold" }}>
              ✨코드리뷰 단계✨
            </p>
            <button
              style={{ fontFamily: "uhbeeBold", fontSize: "22px" }}
              className={`review-step-btn ${step === 1 ? "act" : ""}`}
              onClick={() => {
                setStep(1);
              }}
            >
              <img
                src="/star.png"
                className={`${step === 1 ? "star" : "none"}`}
              ></img>
              1단계
            </button>
            <button
              style={{ fontFamily: "uhbeeBold", fontSize: "22px" }}
              className={`review-step-btn ${step === 2 ? "act" : ""}`}
              onClick={() => {
                setStep(2);
              }}
            >
              {" "}
              <img
                src="/star.png"
                className={`${step === 2 ? "star" : "none"}`}
              ></img>
              2단계
            </button>
            {/* <button
              className={`review-step-btn ${step === 3 ? "act" : ""}`}
              onClick={() => {
                setStep(3);
              }}
            >
              3단계
            </button> */}
            <button
              style={{ fontFamily: "uhbeeBold", fontSize: "22px" }}
              className={`review-step-btn ${step === 3 ? "act" : ""}`}
              onClick={() => {
                setStep(3);
                // setStepModalShow3(true)
              }}
            >
              {" "}
              <img
                src="/star.png"
                className={`${step === 3 ? "star" : "none"}`}
              ></img>
              3단계
            </button>
          </div>
          <div className="review-code">
            {step === 3 && (
              <div className="step-four-main">
                <div className="step-four-your-code">
                  <div className="step-four-type">리뷰 받은 코드</div>
                  <Editor
                    className="review-code-editor"
                    height="95%"
                    language={language}
                    theme="vs-dark"
                    defaultValue={sessionEditCode}
                    // 리뷰받은 코드는 변경을 인식할 필요가 없다.
                    // onChange={}
                    options={{
                      fontSize: 20,
                      minimap: { enabled: false },
                      scrollbar: {
                        vertical: "auto",
                        horizontal: "auto",
                      },
                    }}
                  ></Editor>
                </div>
                <div className="step-four-my-code">
                  <div className="step-four-type">
                    내가 작성했던 코드 (여기에 코드를 작성하세요)
                  </div>
                  <Editor
                    className="review-code-editor"
                    height="95%"
                    language={language}
                    theme="vs-dark"
                    defaultValue={myCode}
                    onChange={handleEditorChange3}
                    options={{
                      fontSize: 20,
                      minimap: { enabled: false },
                      scrollbar: {
                        vertical: "auto",
                        horizontal: "auto",
                      },
                    }}
                  ></Editor>
                </div>
              </div>
            )}

            {step === 1 && (
              <Editor
                className="review-code-editor"
                height="95%"
                language={language}
                theme="vs-dark"
                defaultValue={defaultCode1}
                onChange={handleEditorChange}
                options={{
                  fontSize: 20,
                  minimap: { enabled: false },
                  scrollbar: {
                    vertical: "auto",
                    horizontal: "auto",
                  },
                }}
              ></Editor>
            )}

            {step === 2 && (
              <Editor
                className="review-code-editor"
                height="95%"
                language={language}
                theme="vs-dark"
                defaultValue={defaultCode2}
                onChange={handleEditorChange}
                options={{
                  fontSize: 20,
                  minimap: { enabled: false },
                  scrollbar: {
                    vertical: "auto",
                    horizontal: "auto",
                  },
                }}
              ></Editor>
            )}
          </div>
        </div>
        <div className="review-btns">
          {/* {step === 1 ? <button className="reviewBtn">백준 연동</button> : null} */}
          {step === 2 && (
            <button
              className="reviewBtn"
              onClick={() => {
                console.log(submitOneTwoThree);
                if (submitOneTwoThree.code.length === 0) {
                  setMessage(
                    "코드에 변경사항이 없습니다. 수정 후 제출해주세요"
                  );
                  setAlertModalShow(true);
                } else {
                  saveCode(submitOneTwoThree)
                    .then((res) => {
                      setRequestModalShow(true);
                    })
                    .catch((err) => {
                      setMessage(
                        "1단계 코드를 먼저 작성하고 2단계를 작성해주세요."
                      );
                      setAlertModalShow(true);
                    });
                }
              }}
            >
              리뷰 요청하기
            </button>
          )}

          {step === 1 && (
            <button
              className="reviewBtn"
              onClick={() => {
                // 제출하는 axios 요청 추가
                if (submitOneTwoThree.code.length === 0) {
                  setMessage("입력된 내용이 없습니다.");
                  setAlertModalShow(true);
                } else {
                  saveCode(submitOneTwoThree)
                    .then((res) => {
                      setMessage("코드를 제출하였습니다.");
                      setAlertRefreshModalShow(true);
                      // alert('코드를 제출하였습니다.')
                      // window.location.reload()
                    })
                    .catch((err) => {
                      console.log("1단계 제출에러", err);
                    });
                }
              }}
            >
              코드 제출하기
            </button>
          )}

          {step === 3 && (
            <button
              className="reviewBtn"
              onClick={() => {
                // 최종코드 제출하기
                saveCode(submitCode)
                  .then((res) => {
                    setMessage("코드를 최종 제출하였습니다.");
                    setReviewAlertShow(true);
                    // alert('코드를 최종 제출하였습니다.')
                  })
                  .catch((err) => {
                    setMessage("2단계를 먼저 수행하고 제출해주세요.");
                    setAlertModalShow(true);
                    // alert('최종코드 제출에 실패하였습니다.')
                  });
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
  const [collegue, setCollegue] = useState();
  const studyId = props.studyId;
  const problemId = props.problemId;
  const setAlertModalShow = props.setAlertModalShow;
  const setMessage = props.setMessage;
  const setReviewAlertShow = props.setReviewAlertShow;
  const navigate = useNavigate();
  useEffect(() => {
    getStudyMember(sessionStorage.getItem("reviewStudyId")).then((res) => {
      const tmp = [];
      const items = res.data.registeredMember;
      for (let i = 0; i < items.length; i++) {
        if (items[i].nickname !== sessionStorage.getItem("nickname")) {
          const keyValue = {};
          keyValue["value"] = items[i].baekjoonId;
          keyValue["label"] = items[i].nickname;
          tmp.push(keyValue);
        }
      }
      setCollegue(tmp);
    });
  }, []);
  const [selected, setSelected] = useState({
    receiverId: [],
    studyId: Number(studyId),
    problemId: Number(problemId),
  });

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
          <Select
            isMulti
            onChange={(select) => {
              setSelected((prev) => {
                const tmp = prev.receiverId;
                tmp.push(select[select.length - 1].value);
                return { ...prev, receiverId: tmp };
              });
            }}
            options={collegue}
          ></Select>
          <button
            className="review-modal-request-btn"
            onClick={() => {
              if (selected.receiverId.length === 0) {
                setMessage("리뷰어를 한명 이상 선택해주세요");
                setAlertModalShow(true);
              } else {
                props.onHide();
                reviewRequest(selected)
                  .then(() => {
                    setMessage("리뷰요청을 보냈습니다.");
                    setReviewAlertShow(true);
                  })
                  .catch(() => {
                    setMessage("리뷰 요청에 실패했습니다.");
                    setAlertModalShow(true);
                  });
              }
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
  const problemNum = props.problemNum;
  return (
    <Modal
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Body className="step-modal-body">
        <div className="step-modal-header">
          <h2 className="step-modal-title study-underline-orange">
            코드 리뷰 1단계 방법 알아보기
          </h2>
        </div>
        <div className="step-modal-content" style={{ fontSize: "20px" }}>
          <p>
            <span>백준에서 </span>
            <span className="highlight">풀었던 코드</span>
            <span>를 제출해주세요.</span>
            <br></br>
            <button
              className="go-to-baekjoon-btn"
              onClick={() => {
                window.open(
                  `https://www.acmicpc.net/problem/${problemNum}`,
                  "_blank"
                );
              }}
            >
              백준으로 이동하기
            </button>
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
          <h2 className="step-modal-title study-underline-orange">
            코드 리뷰 2단계 방법 알아보기
          </h2>
        </div>
        <div className="step-modal-content" style={{ fontSize: "20px" }}>
          <p>
            <span>스터디원들이 이해하기 편하도록</span>
            <br></br>
            <span className="highlight">코드설명</span>
            <span>이 필요한 부분에</span>
            <span className="highlight"> 주석</span>
            <span>을 달아주세요.</span>
          </p>
        </div>
      </Modal.Body>
    </Modal>
  );
}

// function StepModal3(props) {
//   return (
//     <Modal
//       {...props}
//       size="md"
//       aria-labelledby="contained-modal-title-vcenter"
//       centered
//     >
//       <Modal.Body className="step-modal-body">
//         <div className="step-modal-header">
//           <h2 className="step-modal-title">코드 리뷰 3단계 방법 알아보기</h2>
//         </div>
//         <div className="step-modal-content">
//           <p>
//             <span className='highlight'>코드리뷰</span>
//             <span>를 받고싶은 부분을</span><br></br>
//             <span className='highlight'>하이라이팅</span>
//             <span>을 사용해 선택해주세요!</span><br></br>
//             <span>선택을 완료한 후 리뷰요청 버튼을 누르고</span><br></br>
//             <span>리뷰요청 창에서 스터디원을 선택한 후 요청해주세요.</span>
//           </p>
//         </div>
//       </Modal.Body>
//     </Modal>
//   );
// }

function StepModal3(props) {
  const navigate = useNavigate();
  const [showNav, setShowNav] = useRecoilState(isNav)
  return (
    <Modal
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Body className="step-modal-body">
        <div className="step-modal-header">
          <h2 className="step-modal-title study-underline-orange">
            코드 리뷰 3단계 방법 알아보기
          </h2>
        </div>
        <div className="step-modal-content" style={{ fontSize: "20px" }}>
          <p>
            <span>코드리뷰 리스트의 내가 보낸 요청 탭에서</span>
            <br />
            <span className="highlight">코드리뷰 3단계 </span>
            <span>버튼을 눌러주세요</span>
            <br />
            <span className="highlight">코드리뷰</span>
            <span>를 받은 부분과</span>
            <br></br>
            <span className="highlight">내가 작성한 코드</span>
            <span>를 비교하고</span>
            <br></br>
            <span>최종코드를 완성해서 제출해보세요.</span>
            <br></br>
            <button className='go-to-review-list' onClick={() => {
              setShowNav(true)
              navigate('/review/list')
            }}>코드리뷰 리스트로 이동</button>
          </p>
        </div>
      </Modal.Body>
    </Modal>
  );
}

export default CodeReview;
