import "./StudyDetail.css";
import { useState, useEffect } from "react";
import Calendar from "react-calendar";
import styled from "styled-components";
import { useNavigate, useParams } from "react-router-dom";
import { getStudyDetail } from "../../api/study";
import TierData from "../../data/tier";
import "./Calendar.css";
import StudyJoinModal from "../../components/study/StudyJoinModal.js";
import ProblemModal from "../../components/study/ProblemModal";

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

const StudyDetail = () => {
  const params = useParams();
  const id = params.id || "";
  const navigate = useNavigate();
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
  // 모달창
  const [studyJoinModalShow, setStudyJoiModalShow] = useState(false);
  const handleStudyJoinModalShow = (e) => {
    setStudyJoiModalShow((prev) => !prev);
  };
  const [problemModalShow, setProblemJoiModalShow] = useState(false);
  const handleProblemModalShow = (e) => {
    setProblemJoiModalShow((prev) => !prev);
  };

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

  return (
    <main>
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
      />

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

export default StudyDetail;
