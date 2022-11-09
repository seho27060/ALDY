import "./StudyList.css";
import { useState, useEffect } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import StudyListItem from "../../components/study/StudyListItem";
import MyStudyListItem from "../../components/study/MyStudyListItem";
import Paging from "../../components/Paging";
import { getStudyList, getMyStudy } from "../../api/study";

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
  const navigate = useNavigate();

  const [tab, setTab] = useState("studyListAll");
  const [studyList, setStudyList] = useState(null);
  const [myStudyList, setMyStudyList] = useState(null);
  // Pagination
  const [studyPageNum, setStudyPageNum] = useState(1);
  const [myStudyPageNum, setMyStudyPageNum] = useState(1);
  const [studyTotal, setStudyTotal] = useState(0);
  const [myStudyTotal, setMyStudyTotal] = useState(0);

  const navigateStudyCreate = () => {
    navigate("/study/create");
  };

  useEffect(() => {
    getStudyList(studyPageNum)
      .then((res) => {
        const data = res.data.studyDtoPage;
        // console.log(data);
        setStudyList(data.content);
        setStudyTotal(data.totalElements);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [studyPageNum]);

  useEffect(() => {
    getMyStudy(myStudyPageNum)
      .then((res) => {
        const data = res.data.myStudyDtoPage;
        // console.log(res);
        setMyStudyList(data.content);
        setMyStudyTotal(data.totalElements);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [myStudyPageNum]);

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
          <span>지금 바로 스터디를 만들어보세요!</span>
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
          <span>원하는 스터디 페이지로 들어가 가입신청을 해주세요!</span>
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
          {tab === "studyListAll" && (
            <div className="study-list-box">
              {studyList?.map((item, i) => (
                <StudyListItem key={i} item={item} />
              ))}
              <Paging
                page={studyPageNum}
                setPage={setStudyPageNum}
                totalElements={studyTotal}
              />
            </div>
          )}
          {tab === "studyListMy" && (
            <div className="study-list-box">
              {myStudyList?.map((item, i) => (
                <MyStudyListItem key={i} item={item} />
              ))}
              <Paging
                page={myStudyPageNum}
                setPage={setMyStudyPageNum}
                totalElements={myStudyTotal}
              />
            </div>
          )}
        </div>
      </section>
    </main>
  );
};

export default StudyList;