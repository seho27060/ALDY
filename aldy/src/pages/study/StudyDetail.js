import "./StudyDetail.css";
import { useState, useEffect } from "react";
import Calendar from "react-calendar";
import styled from "styled-components";
import Modal from "react-bootstrap/Modal";
import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";
import { useNavigate, useParams } from "react-router-dom";
import { getStudyDetail } from "../../api/study";
import TierData from "../../data/tier";
import "./Calendar.css";

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

const WhiteButton = styled.button`
  width: 70px;
  border-radius: 8px;
  background-color: white;
  border: 2px solid red;
  outline: none;
  color: red;
  font-weight: bold;
  font-size: 12px;
  transition: transform 30ms ease-in;
`;

const StudyDetail = () => {
  const params = useParams();
  const id = params.id || "";
  const navigate = useNavigate();
  const navigateStudySelect = () => {
    navigate("/study/select", { state: { date: date } });
  };
  const navigateStudyManage = () => {
    navigate(`/study/manage/${id}`, { state: { studyDetail: studyDetail } });
  };
  const navigateReviewList = () => {
    navigate(`/review/list`);
  };

  const [studyDetail, setStudyDetail] = useState({
    id: 0,
    createdDate: "",
    name: "",
    upperLimit: 6,
    introduction: "",
    threshold: 0,
    visibility: 1,
    countMember: 0,
  });

  // 달력 날짜
  const [date, setDate] = useState(new Date());
  const week = ["일", "월", "화", "수", "목", "금", "토"];
  // 모달창
  const [studyJoinModalShow, setStudyJoiModalShow] = useState(false);
  const [problemModalShow, setProblemJoiModalShow] = useState(false);
  const handleStudyJoinModalShow = (e) => {
    e.preventDefault();
    setStudyJoiModalShow((prev) => !prev);
  };
  const handleProblemModalShow = (e) => {
    setProblemJoiModalShow((prev) => !prev);
  };
  // 문제풀이 리스트
  const [problemList, setProblemList] = useState(null);

  useEffect(() => {
    getStudyDetail(id)
      .then((res) => {
        console.log(res.data);
        setStudyDetail(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [id]);

  useEffect(() => {
    handleProblemModalShow();
  }, [date]);

  useEffect(() => {
    setProblemList([
      {
        problemId: "3017",
        problemName: "가까운 수 찾기",
        problemNumber: "3/6",
        study1: "1단계",
        study2: "3단계",
        study3: "2단계",
        study4: "4단계",
        study5: "3단계",
      },
      {
        problemId: "14503",
        problemName: "로봇 청소기",
        problemNumber: "2/6",
        study1: "1단계",
        study2: "2단계",
        study3: "3단계",
        study4: "4단계",
        study5: "3단계",
      },
      {
        problemId: "9205",
        problemName: "맥주 마시면서 걸어가기",
        problemNumber: "4/6",
        study1: "2단계",
        study2: "3단계",
        study3: "2단계",
        study4: "2단계",
        study5: "3단계",
      },
    ]);
  }, []);

  return (
    <main>
      <Modal
        size="lg"
        show={studyJoinModalShow}
        onHide={handleStudyJoinModalShow}
      >
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
              <button
                className="review-modal-close-btn"
                onClick={handleStudyJoinModalShow}
              >
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
      <Modal size="lg" show={problemModalShow} onHide={handleProblemModalShow}>
        <Modal.Body className="review-modal-body">
          <div className="review-modal-header">
            <div>
              <h3 className="study-detail-title">
                <span>
                  {date.getFullYear()}년 {date.getMonth() + 1}월{" "}
                  {date.getDate()}일 {week[date.getDay()]}요일
                </span>
              </h3>
            </div>
            <div>
              <button
                className="review-modal-close-btn"
                onClick={handleProblemModalShow}
              >
                X
              </button>
            </div>
          </div>
          <div className="review-modal-content">
            <div className="problem-list-box">
              {problemList?.map((item, problemId) => (
                <ProblemListItem key={problemId} item={item} />
              ))}
            </div>
          </div>
          <div className="study-join-btn">
            <RedButton onClick={navigateStudySelect}>문제 선정하기</RedButton>
          </div>
        </Modal.Body>
      </Modal>
      <section className="study-detail-top">
        <div className="top">
          <RedButton onClick={handleStudyJoinModalShow}>
            스터디 가입하기
          </RedButton>
        </div>
        <div className="study-detail-description">
          코드 리뷰를 통해 공룡을 키워보세요~
        </div>
        <h1 className="study-title">{studyDetail.name}</h1>
        <div className="study-detail-banner">
          <div className="description-detail">
            <img src="/pencil.png" alt="연필 이미지"></img>
            <div>알고리즘 코드리뷰</div>
            <h4
              className="underline-green"
              onClick={() => {
                setDate(new Date());
              }}
            >
              오늘의 문제 풀어보기
            </h4>
          </div>
          <div className="description-detail">
            <img src="/code_person.png" alt="코딩하는사람"></img>
            <div>함께 푼 문제 수 확인하기</div>
            <h4 className="underline-green" onClick={navigateStudyManage}>
              스터디원 살펴보기
            </h4>
          </div>
          <div className="description-detail">
            <img src="/CodeReviewIcon.png" alt="코드리뷰 이미지"></img>
            <div>다른 사람에게서</div>
            <h4 className="underline-green" onClick={navigateReviewList}>
              내게 요청 온 목록
            </h4>
          </div>
        </div>
      </section>
      <section className="study-detail-middle">
        <img
          className="study-detail-img"
          src="/dinosaur_hello.gif"
          alt="스터디 메인 이미지"
        ></img>
        <h1 className="underline-green">
          안녕하세요 <span style={{ color: "red" }}>반가워요~</span>
        </h1>
        <div className="dinosaur-description">
          지금 우리 스터디{" "}
          <span style={{ color: "rgba(40, 80, 15, 1)", fontWeight: "900" }}>
            공룡의 레벨
          </span>
          은{" "}
          <span style={{ color: "rgba(40, 80, 15, 1)", fontWeight: "900" }}>
            lv.20
          </span>
          입니다.
        </div>
      </section>
      <section className="study-detail-bottom">
        <Calendar
          onChange={setDate}
          date={date}
          onClick={handleProblemModalShow}
        />
        <div className="study-detail-bottom-right">
          <div className="study-detail-info">
            <span className="study-detail-number">
              스터디원 : {studyDetail.countMember}/{studyDetail.upperLimit}
            </span>
            <h3 className="study-detail-title">
              <span>{studyDetail.name}</span>
            </h3>
            <div className="study-detail-rank">
              <img
                src={`https://d2gd6pc034wcta.cloudfront.net/tier/${studyDetail.threshold}.svg`}
                alt="티어 이미지"
                className="tier-image"
              ></img>
              {TierData[studyDetail.threshold]}
            </div>
            <div className="description">
              <div>✨ 스터디 소개 ✨</div>
              {studyDetail.introduction}
            </div>
          </div>
          <div className="study-detail-graph">
            <div>
              <h5 className="study-detail-title">
                <span>카테고리 별 푼 문제</span>
              </h5>
              <div>그래프 뿅뿅</div>
            </div>
            <div>
              <h5 className="study-detail-title">
                <span>난이도 별 푼 문제</span>
              </h5>
              <div>그래프 뿅뿅</div>
            </div>
          </div>
        </div>
      </section>
    </main>
  );
};

const ProblemListItem = (props) => {
  const [dropdown, setDropdown] = useState("none");

  return (
    <div className="problem-list-item">
      <div className="problem-list-title">
        <h5 className="problem-name">{props.item.problemId}번</h5>
        <div className="problem-name">{props.item.problemName}</div>
        <div className="problem-list-right">
          <div className="problem-number">{props.item.problemNumber}</div>
          <WhiteButton>코드 리뷰</WhiteButton>
          {dropdown === "none" && (
            <FaChevronCircleDown
              className="down-icon"
              onClick={() => {
                setDropdown("active");
              }}
            />
          )}
          {dropdown === "active" && (
            <FaChevronCircleUp
              className="down-icon"
              onClick={() => {
                setDropdown("none");
              }}
            />
          )}
        </div>
      </div>

      <div
        className={`problem-list-content ${
          dropdown === "active" ? "content-active" : ""
        }`}
      >
        <div>스터디원 1 : {props.item.study1}</div>
        <div>스터디원 2 : {props.item.study2}</div>
        <div>스터디원 3 : {props.item.study3}</div>
        <div>스터디원 4 : {props.item.study4}</div>
        <div>스터디원 5 : {props.item.study5}</div>
      </div>
    </div>
  );
};

export default StudyDetail;
