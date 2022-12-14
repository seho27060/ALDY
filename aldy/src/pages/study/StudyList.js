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
            setMessage("??????????????? ????????????.");
            setAlertModalShow(true);
          }
        })
        .catch((err) => {
          setMessage("??????????????? ????????????.");
          setAlertModalShow(true);
          setSearchShow(false);
          // console.log(err);
        });
    } else {
      setMessage("???????????? ??????????????????.");
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
          alt="????????? ?????? ?????????"
        ></img>
        <p>
          <span>?????? ???????????? ?????? </span>
          <span className="study-highlight-green">????????????</span>
          <span>??? ?????? </span>
          <span className="study-highlight-green">??????</span>
          <span>??? ????????? ??????</span>
        </p>
        <div className="study-underline-green study-list-create">
          ?????? ?????? ???????????? ??????????????????!
        </div>
        <Button redLine medium onClick={navigateStudyCreate}>
          ????????? ????????????
        </Button>
      </section>
      <section className="study-search">
        <p>
          <span>?????? ???????????? ?????? </span>
          <span className="study-highlight-orange">????????????</span>
          <span>??? ?????? </span>
          <span className="study-highlight-orange">??????</span>
          <span>??? ????????? ??????</span>
        </p>
        <div className="study-underline-orange study-list-subtitle">
          ????????? ????????? ???????????? ????????? ??????????????? ????????????!
        </div>
        <div className="search-box">
          <div className="d-flex search-bar">
            <Form.Control
              type="search"
              placeholder="???????????? ??????????????????."
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
                  <div className="study-search-result-title">????????????</div>
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
            ?????? ????????? ??????
          </button>
          <button
            onClick={() => {
              setTab("studyListMy");
            }}
            className={`study-tab ${
              tab === "studyListMy" ? "study-tab-active" : ""
            }`}
          >
            ??? ????????? ??????
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
