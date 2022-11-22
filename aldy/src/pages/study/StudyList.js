import StudyListItem from "../../components/study/StudyListItem";
import MyStudyListItem from "../../components/study/MyStudyListItem";
import AlertModal from "../../components/modal/AlertModal";
import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import Form from "react-bootstrap/Form";
import Paging from "../../components/Paging";
import { getStudyList, getMyStudy } from "../../api/study";
import { isNav } from "../../store/states";

import "./StudyList.css";
import Button from "../../components/styled/Button";

const StudyList = () => {
  const [nav, setNav] = useRecoilState(isNav);
  setNav(true);

  const navigate = useNavigate();
  const searchInput = useRef("");

  const [tab, setTab] = useState("studyListAll");
  const [studyList, setStudyList] = useState(null);
  const [myStudyList, setMyStudyList] = useState(null);
  const [searchList, setSearchList] = useState([]);
  // Pagination
  const [studyPageNum, setStudyPageNum] = useState(1);
  const [myStudyPageNum, setMyStudyPageNum] = useState(1);
  const [searchPageNum, setSearchPageNum] = useState(1);
  const [studyTotal, setStudyTotal] = useState(0);
  const [myStudyTotal, setMyStudyTotal] = useState(0);
  const [searchTotal, setSearchTotal] = useState(0);
  const [searchShow, setSearchShow] = useState(false);
  // alert Modal
  const [message, setMessage] = useState("");
  const [alertModalShow, setAlertModalShow] = useState(false);
  const navigateStudyCreate = () => {
    navigate("/study/create");
  };

  const onKeypress = (e) => {
    if (e.key === "Enter") {
      studySearch();
    }
  };

  const studySearch = () => {
    if (searchInput.current.value) {
      getStudyList(searchPageNum, 10, searchInput.current.value)
        .then((res) => {
          const data = res.data.studyDtoPage;
          if (data.content.length > 0) {
            setSearchShow(true);
            setSearchList(data.content);
            setSearchTotal(data.totalElements);
          } else {
            setMessage("검색결과가 없습니다.");
            setAlertModalShow(true);
          }
        })
        .catch((err) => {
          setMessage("검색결과가 없습니다.");
          setAlertModalShow(true);
          setSearchShow(false);
          // console.log(err);
        });
    } else {
      setMessage("검색어를 입력해주세요.");
      setAlertModalShow(true);
    }
  };

  useEffect(() => {
    getStudyList(studyPageNum)
      .then((res) => {
        const data = res.data.studyDtoPage;
        setStudyList(data.content);
        setStudyTotal(data.totalElements);
      })
      .catch((err) => {
        // console.log(err);
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
        // console.log(err);
      });
  }, [myStudyPageNum]);

  useEffect(() => {
    if (searchInput.current.value !== "") {
      studySearch();
    }
  }, [searchPageNum]);

  return (
    <main style={{ userSelect: "none" }}>
      <AlertModal
        show={alertModalShow}
        onHide={() => {
          setAlertModalShow(false);
        }}
        message={message}
      />
      <section className="study-list-banner">
        <img
          className="study-main-img"
          src="/ALDY/dinosaur.png"
          alt="스터디 메인 이미지"
        ></img>
        <p>
          <span>다른 사람들과 함께 </span>
          <span className="study-highlight-green">코드리뷰</span>
          <span>를 하며 </span>
          <span className="study-highlight-green">공룡</span>
          <span>을 키워볼 기회</span>
        </p>
        <div className="study-underline-green study-list-create">
          지금 바로 스터디를 만들어보세요!
        </div>
        <Button redLine medium onClick={navigateStudyCreate}>
          스터디 생성하기
        </Button>
      </section>
      <section className="study-search">
        <p>
          <span>다른 사람들과 함께 </span>
          <span className="study-highlight-orange">코드리뷰</span>
          <span>를 하며 </span>
          <span className="study-highlight-orange">공룡</span>
          <span>을 키워볼 기회</span>
        </p>
        <div className="study-underline-orange study-list-subtitle">
          원하는 스터디 페이지로 들어가 가입신청을 해주세요!
        </div>
        <div className="search-box">
          <div className="d-flex search-bar">
            <Form.Control
              type="search"
              placeholder="검색어를 입력해주세요."
              className="me-2"
              aria-label="Search"
              ref={searchInput}
              onKeyPress={onKeypress}
            />
            <Button brown medium onClick={studySearch}>
              Search
            </Button>
          </div>
        </div>
        {searchShow && (
          <section className="study-list-search">
            <div>
              <div className="Mypage-study-list-box">
                <div className="study-search-top">
                  <div className="study-search-result-title">검색결과</div>
                  <button
                    className="study-search-result-close-btn"
                    onClick={() => {
                      setSearchShow(false);
                    }}
                  >
                    X
                  </button>
                </div>
                {searchList?.map((item, i) => (
                  <StudyListItem key={i} item={item} num={i} />
                ))}
                <Paging
                  page={searchPageNum}
                  setPage={setSearchPageNum}
                  totalElements={searchTotal}
                />
              </div>
            </div>
          </section>
        )}
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
                <StudyListItem key={i} item={item} num={i} />
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
                <MyStudyListItem key={i} item={item} num={i} />
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
