import "./StudyList.css";
import { useState, useEffect } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";

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

const StudyList = () => {
  const [tab, setTab] = useState("studyListAll");
  const navigate = useNavigate();

  const navigateStudyCreate = () => {
    navigate("/study/create");
  };

  return (
    <main>
      <section className="study-list-banner">
        <img
          className="study-main-img"
          src="/dinosaur.png"
          alt="스터디 메인 이미지"
        ></img>
        <p>
          <span>다른 사람들과 함께 </span>
          <span className="study-highlight-green">코드리뷰</span>
          <span>를 하며 </span>
          <span className="study-highlight-green">공룡</span>
          <span>을 키워볼 기회</span>
        </p>
        <h2 className="study-underline-green">
          지금 바로 스터디를 만들어보세요!
        </h2>
        <RedButton onClick={navigateStudyCreate} className="study-button">
          스터디 생성하기
        </RedButton>
      </section>
      <section className="study-search">
        <p>
          <span>다른 사람들과 함께 </span>
          <span className="study-highlight-orange">코드리뷰</span>
          <span>를 하며 </span>
          <span className="study-highlight-orange">공룡</span>
          <span>을 키워볼 기회</span>
        </p>
        <h2 className="study-underline-orange">
          원하는 스터디 페이지로 들어가 가입신청을 해주세요!
        </h2>
        <div className="search-box">
          <Form className="d-flex search-bar">
            <Form.Control
              type="search"
              placeholder="Search"
              className="me-2"
              aria-label="Search"
            />
            <Button variant="outline-success" className="search-button">
              Search
            </Button>
          </Form>
        </div>
      </section>
      <section className="study-list">
        <div className="study-tab-list">
          <button
            onClick={() => {
              setTab("studyListAll");
            }}
            className={`study-tab ${
              tab === "studyListAll" ? "study-tab-active" : ""
            }`}
          >
            전체 스터디 목록
          </button>
          <button
            onClick={() => {
              setTab("studyListMy");
            }}
            className={`study-tab ${
              tab === "studyListMy" ? "study-tab-active" : ""
            }`}
          >
            내 스터디 목록
          </button>
        </div>
        <div>
          {tab === "studyListAll" && <StudyListAll />}
          {tab === "studyListMy" && <StudyListMy />}
        </div>
      </section>
    </main>
  );
};

const StudyListAll = () => {
  const [studyList, setStudyList] = useState(null);

  useEffect(() => {
    // 서버에서 내게 요청온 목록 가져와서 list에 저장하기
    setStudyList([
      {
        studyId: "1",
        studyName: "SSAFY ALDY",
        studyNumber: "5/6",
        studyRank: "Gold4",
        studyDescription:
          "✨ 다들 열심히 달려봅시다!! ✨ 저희 스터디는 알고리즘 스터디입니다. 매주 월 수 금 문제를 풀어 올려야 합니다~~",
      },
      {
        studyId: "2",
        studyName: "알고리즘 홧팅!",
        studyNumber: "4/5",
        studyRank: "Gold3",
        studyDescription: "알고리즘 화이팅~~",
      },
      {
        studyId: "3",
        studyName: "다들 열심히 스터디",
        studyNumber: "3/6",
        studyRank: "Gold2",
        studyDescription: "아무말이나 우선 적어보기",
      },
      {
        studyId: "4",
        studyName: "공룡 키우기",
        studyNumber: "6/6",
        studyRank: "Gold1",
        studyDescription: "우리 스터디는 마감이요",
      },
    ]);
  }, []);

  return (
    <div className="study-list-box">
      {studyList?.map((item) => (
        <StudyListItem item={item} />
      ))}
    </div>
  );
};

const StudyListItem = (props) => {
  const [dropdown, setDropdown] = useState("none");

  return (
    <div className="study-list-item">
      <div className="study-list-title">
        <div className="study-id">{props.item.studyId}</div>
        <h5 className="study-name">{props.item.studyName}</h5>
        <div className="study-number">{props.item.studyNumber}</div>
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

      <div
        className={`study-list-content ${
          dropdown === "active" ? "content-active" : ""
        }`}
      >
        <div className="study-rank">{props.item.studyRank}</div>
        <div>{props.item.studyDescription}</div>
      </div>
    </div>
  );
};

const StudyListMy = () => {
  const [myStudyList, setMyStudyList] = useState(null);

  useEffect(() => {
    // 서버에서 내게 요청온 목록 가져와서 list에 저장하기
    setMyStudyList([
      {
        studyId: "1",
        studyName: "SSAFY ALDY",
        studyNumber: "5/6",
        studyRank: "Gold4",
        studyDescription:
          "✨ 다들 열심히 달려봅시다!! ✨ 저희 스터디는 알고리즘 스터디입니다. 매주 월 수 금 문제를 풀어 올려야 합니다~~",
        problemNum: 32,
        startDate: "2022 - 10 - 21",
        recentRank: "Gold5",
      },
      {
        studyId: "2",
        studyName: "알고리즘 홧팅!",
        studyNumber: "4/5",
        studyRank: "Gold3",
        studyDescription: "알고리즘 화이팅~~",
        problemNum: 23,
        startDate: "2022 - 10 - 17",
        recentRank: "Gold4",
      },
      {
        studyId: "3",
        studyName: "다들 열심히 스터디",
        studyNumber: "3/6",
        studyRank: "Gold2",
        studyDescription: "아무말이나 우선 적어보기",
        problemNum: 12,
        startDate: "2022 - 09 - 15",
        recentRank: "Gold2",
      },
      {
        studyId: "4",
        studyName: "공룡 키우기",
        studyNumber: "6/6",
        studyRank: "Gold1",
        studyDescription: "우리 스터디는 마감이요",
        problemNum: 40,
        startDate: "2022 - 09 - 09",
        recentRank: "Gold1",
      },
    ]);
  }, []);

  return (
    <div className="study-list-box">
      {myStudyList?.map((item) => (
        <MyStudyListItem item={item} />
      ))}
    </div>
  );
};

const MyStudyListItem = (props) => {
  const [dropdown, setDropdown] = useState("none");

  return (
    <div className="study-list-item">
      <div className="study-list-title">
        <div className="study-id">{props.item.studyId}</div>
        <h5 className="study-name">{props.item.studyName}</h5>
        <div className="study-number">{props.item.studyNumber}</div>
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

      <div
        className={`my-study-list-content ${
          dropdown === "active" ? "content-active" : ""
        }`}
      >
        <div className="my-study-description1">
          <div className="study-rank">{props.item.studyRank}</div>
          <div>{props.item.studyDescription}</div>
        </div>
        <div className="my-study-description2">
          <div>함께 푼 문제 수 : {props.item.problemNum}</div>
          <div>시작한 날짜 : {props.item.startDate}</div>
          <div>최근 해결한 문제 티어 : {props.item.recentRank}</div>
        </div>
      </div>
    </div>
  );
};

export default StudyList;
