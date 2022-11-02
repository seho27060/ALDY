import "./StudyDetail.css";
import { useState, useEffect } from "react";
import Calendar from "react-calendar";
import styled from "styled-components";
import Modal from "react-bootstrap/Modal";
import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";
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
  // 달력 날짜
  const [value, onChange] = useState(new Date());
  const [problemDay, setProblemDay] = useState(null);

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
    const year = value.getFullYear();
    const month = value.getMonth();
    const date = value.getDate();
    const day = value.getDay();
    const week = ["일", "월", "화", "수", "목", "금", "토"];

    setProblemDay(`${year}년 ${month + 1}월 ${date}일 ${week[day]}요일`);

    handleProblemModalShow();
  }, [value]);

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
                SSAFY ALDY에 가입하기 위해 가입 신청 메세지를 작성해주세요!!
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
                <span>{problemDay}</span>
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
              {problemList?.map((item) => (
                <ProblemListItem item={item} />
              ))}
            </div>
          </div>
          <div className="study-join-btn">
            <RedButton>문제 선정하기</RedButton>
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
        <h1 className="study-title">SSAFY ALDY</h1>
        <div className="study-detail-banner">
          <div className="description-detail">
            <img src="/pencil.png" alt="연필 이미지"></img>
            <div>알고리즘 코드리뷰</div>
            <h4 className="underline-green">오늘의 문제 풀어보기</h4>
          </div>
          <div className="description-detail">
            <img src="/code_person.png" alt="코딩하는사람"></img>
            <div>함께 푼 문제 수 확인하기</div>
            <h4 className="underline-green">스터디원 살펴보기</h4>
          </div>
          <div className="description-detail">
            <img src="/CodeReviewIcon.png" alt="코드리뷰 이미지"></img>
            <div>다른 사람에게서</div>
            <h4 className="underline-green">내게 요청 온 목록</h4>
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
          은 ssafy aldy{" "}
          <span style={{ color: "rgba(40, 80, 15, 1)", fontWeight: "900" }}>
            lv.20
          </span>
          입니다.
        </div>
      </section>
      <section className="study-detail-bottom">
        <Calendar
          onChange={onChange}
          value={value}
          onClick={handleProblemModalShow}
        />
        <div className="study-detail-bottom-right">
          <div className="study-detail-info">
            <span className="study-detail-number">멤버 수 : 5/6</span>
            <h3 className="study-detail-title">
              <span>SSAFY ALDY</span>
            </h3>
            <div className="study-detail-rank">GOLD 4</div>
            <div className="description">
              ✨ 다들 열심히 달려봅시다!! ✨ <br></br> 저희 스터디는 알고리즘
              스터디입니다. <br></br> 매주 월 수 금 문제를 풀어 올려야 합니다~~
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
