import "./StudyDetail.css";
import Modal from "react-bootstrap/Modal";
import { useState, useEffect } from "react";
import Calendar from "react-calendar";
import styled from "styled-components";
import { useNavigate, useParams } from "react-router-dom";
import {
  getStudyDetail,
  getProblem,
  studyWithdrawal,
  getSelectedDay,
} from "../../api/study";
import TierData from "../../data/tier";
import ActivationLevel from "../../data/ActivationLevel";
import "./Calendar.css";
import StudyJoinModal from "../../components/study/StudyJoinModal.js";
import ProblemModal from "../../components/study/ProblemModal";
import StudyMember from "../../components/study/StudyMember";
import moment from "moment";
import StudyChart from "../../components/study/StudyChart";
import StudyChartTier from "../../components/study/StudyChartTier";
import AlertModal from "../../components/AlertModal";
import { FcLock } from "react-icons/fc";
import { useRecoilState } from "recoil";
import { isNav } from "../../store/states";

const RedButton = styled.button`
  width: 90px;
  border-radius: 8px;
  background-color: red;
  border: 2px solid red;
  outline: none;
  color: white;
  font-weight: bold;
  padding: 4px 0px 2px 0px;
  font-size: 12px;
  transition: all 200ms ease-in;
  user-select: none;
  &:hover {
    background-color: white;
    color: red;
    transition: all 200ms ease-in;
    border: 2px solid red;
  }
`;

const WhiteButton = styled.button`
  width: 110px;
  border-radius: 8px;
  background-color: white;
  border: 2px solid red;
  outline: none;
  color: red;
  font-weight: bold;
  font-size: 14px;
  padding: 4px 0px 2px 0px;
  transition: all 200ms ease-in;
  user-select: none;
  &:hover {
    background-color: red;
    color: white;
    transition: all 200ms ease-in;
  }
`;

const StudyDetail = () => {
  const [nav, setNav] = useRecoilState(isNav);
  setNav(true);

  const params = useParams();
  const id = params.id || "";
  const myId = sessionStorage.getItem("userName");
  const navigate = useNavigate();
  const navigateStudyManage = () => {
    navigate(`/study/manage/${id}`, { state: { studyDetail: studyDetail } });
  };
  const navigateReviewList = () => {
    navigate(`/review/list`);
  };
  const navigateStudy = () => {
    navigate("/study/list");
  };

  const [studyDetail, setStudyDetail] = useState({
    id: id,
    createdDate: "",
    name: "",
    upperLimit: 6,
    introduction: "",
    threshold: 0,
    visibility: 1,
    countMember: 0,
    leaderBaekjoonId: "",
    leaderEmail: "",
    statsByTier: {},
    statsByTag: {},
    isMember: false,
    isKick: false,
    level: 0,
    activationLevel: 0,
  });
  const keys = Object.keys(studyDetail.statsByTag);
  console.log(studyDetail);

  // 달력 날짜
  const [date, setDate] = useState(new Date());
  const [mark, setMark] = useState([]);
  // 모달창
  const [studyJoinModalShow, setStudyJoiModalShow] = useState(false);
  const handleStudyJoinModalShow = (e) => {
    if (studyDetail.threshold > sessionStorage.getItem("tier")) {
      setMessage(
        `${sessionStorage.getItem(
          "nickname"
        )}님의 티어가 가입요건을 충족하지 않습니다.`
      );
      setAlertModalShow(true);
    } else {
      setStudyJoiModalShow((prev) => !prev);
    }
  };
  const [problemModalShow, setProblemJoiModalShow] = useState(false);
  const handleProblemModalShow = (e) => {
    setProblemJoiModalShow((prev) => !prev);
  };
  const [memberModalShow, setMemberModalShow] = useState(false);
  const handleMemberModalShow = (e) => {
    setMemberModalShow((prev) => !prev);
  };
  // 모달
  const [message, setMessage] = useState("");
  const [alertModalShow, setAlertModalShow] = useState(false);

  // 스터디 탈퇴
  const studyOut = () => {
    studyWithdrawal(Number(id))
      .then((res) => {
        setMessage(`${studyDetail.name}에서 탈퇴되었습니다.`);
        setAlertModalShow(true);
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // 문제 가져오기
  const [problemList, setProblemList] = useState([]);
  useEffect(() => {
    handleProblemModalShow();
    console.log(date, "방금찍음");
    getProblem(id, date.getFullYear(), date.getMonth() + 1, date.getDate())
      .then((res) => {
        // console.log(res.data);
        setProblemList(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [id, date]);

  useEffect(() => {
    getStudyDetail(id)
      .then((res) => {
        setStudyDetail(res.data);
        // setSendLeaderId(res.data.leaderBaekjoonId);
        sessionStorage.setItem("sendLeaderId", res.data.leaderBaekjoonId);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [id]);

  useEffect(() => {
    getSelectedDay(id, date.getFullYear(), date.getMonth() + 1)
      .then((res) => {
        console.log(res.data.dayss);
        setMark(res.data.days);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [date]);

  console.log(studyDetail.statsByTag, "아");

  return (
    <main style={{ userSelect: "none" }}>
      <AlertModal
        show={alertModalShow}
        onHide={() => {
          setAlertModalShow(false);
          navigateStudy();
        }}
        message={message}
      />
      <Modal size="lg" show={memberModalShow} onHide={handleMemberModalShow}>
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
              <button
                className="review-modal-close-btn"
                onClick={handleMemberModalShow}
              >
                X
              </button>
            </div>
          </div>
          <StudyMember id={id} />
        </Modal.Body>
      </Modal>
      <StudyJoinModal
        studyDetail={studyDetail}
        modal={studyJoinModalShow}
        handleModal={handleStudyJoinModalShow}
      />
      <ProblemModal
        studyDetail={studyDetail}
        date={date}
        modal={problemModalShow}
        handleModal={handleProblemModalShow}
        problemList={problemList}
        setProblemList={setProblemList}
      />
      <section className="study-detail-top">
        <div className="top">
          {studyDetail.countMember < studyDetail.upperLimit &&
            !studyDetail.isMember && (
              <WhiteButton onClick={handleStudyJoinModalShow}>
                스터디 가입
              </WhiteButton>
            )}
          {myId === studyDetail.leaderBaekjoonId && (
            <WhiteButton onClick={navigateStudyManage}>스터디 관리</WhiteButton>
          )}
        </div>
        <div className="study-detail-description">
          코드 리뷰를 통해 공룡을 키워보세요~
        </div>
        <div className="study-title">{studyDetail.name}</div>
        <div className="study-detail-banner">
          <div
            className="study-description-detail"
            onClick={() => {
              setDate(new Date());
            }}
          >
            <img src="/pencil.png" alt="연필 이미지"></img>
            <div>알고리즘 코드리뷰</div>
            <div
              className="study-underline-green"
              style={{
                margin: "auto",
                lineHeight: "35px",
                fontSize: "25px",
                marginBottom: "10px",
              }}
            >
              오늘의 문제 풀어보기
            </div>
          </div>
          <div
            className="study-description-detail"
            onClick={handleMemberModalShow}
          >
            <img src="/code_person.png" alt="코딩하는사람"></img>
            <div>함께 푼 문제 수 확인하기</div>
            <div
              className="study-underline-green"
              style={{
                margin: "auto",
                lineHeight: "35px",
                fontSize: "25px",
                marginBottom: "10px",
              }}
            >
              스터디원 살펴보기
            </div>
          </div>
          <div
            className="study-description-detail"
            onClick={navigateReviewList}
          >
            <img src="/codeReviewIcon.png" alt="코드리뷰 이미지"></img>
            <div>다른 사람에게서</div>
            <div
              className="study-underline-green"
              style={{
                margin: "auto",
                lineHeight: "35px",
                fontSize: "25px",
                marginBottom: "10px",
              }}
            >
              내게 요청 온 목록
            </div>
          </div>
        </div>
        {studyDetail.isMember && myId !== studyDetail.leaderBaekjoonId && (
          <div className="study-out">
            <RedButton onClick={studyOut}>스터디 탈퇴</RedButton>
          </div>
        )}
      </section>
      <section className="study-detail-middle">
        <div
          className="study-detail-aldy"
          style={{ width: "50%", display: "flex", flexDirection: "colunm" }}
        >
          <div className="aldy-bg">
            <img
              className="study-detail-img"
              src={ActivationLevel[studyDetail.activationLevel]}
              alt="스터디 메인 이미지"
            ></img>
          </div>
          <div
            className="study-underline-green"
            style={{
              margin: "10px auto",
              lineHeight: "35px",
              fontSize: "35px",
              marginBottom: "10px",
            }}
          >
            안녕하세요 <span style={{ color: "red" }}>반가워요~</span>
          </div>
          <div className="dinosaur-description">
            지금 우리 스터디{" "}
            <span style={{ color: "rgba(40, 80, 15, 1)", fontWeight: "900" }}>
              공룡의 레벨
            </span>
            은{" "}
            <span style={{ color: "rgba(40, 80, 15, 1)", fontWeight: "900" }}>
              lv.{studyDetail.level}
            </span>
            입니다.
          </div>
        </div>
        <div className="study-detail-info">
          <span className="study-detail-number">
            스터디원 : {studyDetail.countMember}/{studyDetail.upperLimit}
          </span>
          <div
            className="study-underline-orange"
            style={{
              alignSelf: "center",
              lineHeight: "35px",
              fontSize: "30px",
              margin: "10px",
            }}
          >
            {studyDetail.name}
          </div>
          <div className="study-detail-rank">
            <img
              src={`https://d2gd6pc034wcta.cloudfront.net/tier/${studyDetail.threshold}.svg`}
              alt="티어 이미지"
              className="tier-image"
            ></img>
            {TierData[studyDetail.threshold]}
          </div>
          <div className="description">
            <div>
              <span style={{ color: "rgb(125,125,125)" }}>
                [스터디장 이메일]{" "}
              </span>
              {studyDetail.leaderEmail}
            </div>
            <hr style={{ margin: "5px 0px 15px 0px" }} />
            <div>
              <div style={{ marginBottom: "5px" }}>
                <b>✨스터디 소개✨</b>
              </div>
              {studyDetail.introduction.split("\n").map((line) => {
                //this.props.data.content: 내용
                return (
                  <span>
                    {line}
                    <br />
                  </span>
                );
              })}
              <hr style={{ margin: "5px 0px 15px 0px" }} />
              <div style={{ marginBottom: "5px" }}>
                <b>🔔스터디 공지🔔</b>
              </div>
              <div style={{ color: "rgb(80,80,80)", fontSize: "15px" }}>
                코드 리뷰 요청 혹은 응답 시 알림 메일이 전송됩니다.
              </div>
              <div style={{ color: "red", marginTop: "15px" }}>
                경고(패널티)🚦
              </div>
              <div style={{ color: "rgb(80,80,80)", fontSize: "15px" }}>
                정해진 날짜까지 문제 1단계를 제출 안했을 시<br></br>
                코드 리뷰 요청을 받고 일주일 안에 응답 안했을 시<br></br>총 경고
                3회 받을 경우 스터디에서 자동으로 강제 퇴장
              </div>
            </div>
          </div>
        </div>
      </section>
      <section className="study-detail-bottom">
        <div className="study-detail-calendar">
          <div className="calendar-description">
            <span className="dot"></span> 빨간색 동그라미가 있는 날짜는 문제가
            등록되어 있는 날짜입니다.
          </div>
          {studyDetail.isMember ? (
            <Calendar
              onChange={setDate}
              date={date}
              tileContent={({ date, view }) => {
                if (mark.find((x) => x === moment(date).format("DD-MM-YYYY"))) {
                  return (
                    <>
                      <div className="dot-box">
                        <div
                          className="dot"
                          style={{ position: "absolute" }}
                        ></div>
                      </div>
                    </>
                  );
                }
              }}
            />
          ) : (
            <div className="calendar-lock">
              <FcLock style={{ fontSize: "120px" }} />
            </div>
          )}
        </div>
        <div className="study-detail-bottom-right">
          {studyDetail.isMember ? (
            <div className="study-detail-graph-box">
              <div
                className="study-underline-orange"
                style={{
                  alignSelf: "center",
                  lineHeight: "35px",
                  fontSize: "25px",
                  marginBottom: "20px",
                }}
              >
                선정된 문제
              </div>
              {keys.length ? (
                <div className="study-detail-graph">
                  <div>
                    <div className="study-title-graph">카테고리 별</div>
                    <StudyChart studyData={studyDetail.statsByTag} />
                  </div>
                  <div>
                    <div className="study-title-graph">난이도 별</div>
                    <StudyChartTier studyData={studyDetail.statsByTier} />
                  </div>
                </div>
              ) : (
                <div style={{ margin: "110px", fontSize: "20px" }}>
                  선정된 문제가 없습니다.
                </div>
              )}
            </div>
          ) : (
            <div className="calendar-lock" style={{ marginTop: "20px" }}>
              <FcLock style={{ fontSize: "120px" }} />
            </div>
          )}
        </div>
      </section>
    </main>
  );
};

export default StudyDetail;
