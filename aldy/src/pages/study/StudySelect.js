import AlertModal from "../../components/modal/AlertModal";
import Option from "../../components/study/Option";
import Problem from "../../components/study/Problem";
import { useLocation, useNavigate } from "react-router-dom";
import { useState, useEffect, useRef } from "react";
import { useRecoilState } from "recoil";
import TierData from "../../data/tier";
import {
  getStudyProblem,
  getOptionList,
  addProblem,
  getSearchProblem,
} from "../../api/study";
import Form from "react-bootstrap/Form";
import { isNav } from "../../store/states";

import Button from "../../components/styled/Button";
import "./StudySelect.css";

const StudySelect = () => {
  const [nav, setNav] = useRecoilState(isNav);
  setNav(true);

  const navigate = useNavigate();
  const location = useLocation();
  const date = location.state.date;
  const studyId = location.state.studyId;
  const week = ["일", "월", "화", "수", "목", "금", "토"];

  // 체크박스 옵션 리스트
  const [algoOption, setAlgoOption] = useState({});
  const tierOption = TierData;
  const [baekjoonIdOption, setBaekjoonIdOption] = useState({});
  // 체크된 옵션
  const [algoList, setAlgoList] = useState([]);
  const [tierList, setTierList] = useState([]);
  const [baekjoonIdList, setBaekjoonIdList] = useState([]);
  // 검색 결과
  const [result, setResult] = useState([]);
  // 선정된 문제
  const [problem, setProblem] = useState([]);
  // 문제 검색
  const searchInput = useRef("");
  // 모달
  const [message, setMessage] = useState("");
  const [alertModalShow, setAlertModalShow] = useState(false);
  const [searchAlertModalShow, setSearchAlertModalShow] = useState(false);

  const onKeypress = (e) => {
    if (e.key === "Enter") {
      problemSearch();
    }
  };

  const problemSearch = () => {
    if (searchInput.current.value) {
      getSearchProblem(searchInput.current.value)
        .then((res) => {
          const data = res.data;
          if (data.items.length > 0) {
            setResult(data.items);
          } else {
            setMessage("검색결과가 없습니다.");
            setSearchAlertModalShow(true);
          }
        })
        .catch((err) => {
          setMessage("검색결과가 없습니다.");
          setSearchAlertModalShow(true);
        });
    } else {
      setMessage("검색어를 입력해주세요.");
      setSearchAlertModalShow(true);
    }
  };

  useEffect(() => {
    getOptionList(studyId)
      .then((res) => {
        const data = res.data;
        setAlgoOption(data.algoHash);
        setBaekjoonIdOption(data.memberHash);
      })
      .catch((err) => {
        // console.log(err);
      });
  }, [studyId]);

  const searchProblem = () => {
    const tierNumberList = [];
    tierList.forEach((id) => tierNumberList.push(Number(id)));
    getStudyProblem(
      algoList.join(","),
      tierNumberList.join(","),
      baekjoonIdList.join(",")
    )
      .then((res) => {
        const data = res.data;
        setResult(data.items);
      })
      .catch((err) => {
        // console.log(err);
      });
  };

  const deleteProblem = (item) => {
    setProblem(problem.filter((el) => el !== item));
  };

  const choiceProblem = () => {
    const data = {
      studyId: studyId,
      problemList: problem,
      year: date.getFullYear(),
      month: date.getMonth() + 1,
      day: date.getDate(),
    };
    addProblem(data)
      .then((res) => {
        setMessage("문제선정이 완료되었습니다!");
        setAlertModalShow(true);
      })
      .catch((err) => {
        // console.log(err);
      });
  };

  return (
    <main className="study-select-main">
      <AlertModal
        show={alertModalShow}
        onHide={() => {
          setAlertModalShow(false);
          navigate(`/study/detail/${studyId}`);
        }}
        message={message}
      />
      <AlertModal
        show={searchAlertModalShow}
        onHide={() => {
          setSearchAlertModalShow(false);
        }}
        message={message}
      />
      <section className="study-select-title-box">
        <h1 className="study-underline-green">문제 선정 하기</h1>
        <div>
          <div className="study-select-small-title">
            🔥필터 선택 후 검색 버튼을 눌러 문제를 선정해주세요🔥
          </div>
          <div className="study-select-date">
            {date.getFullYear()}년 {date.getMonth() + 1}월 {date.getDate()}일{" "}
            {week[date.getDay()]}요일
          </div>
        </div>
      </section>
      <section className="study-select-search-box">
        <div className="study-select-option">
          <div className="green-line">
            <img
              src="/icon/code_person.png"
              alt="코딩하는사람"
              className="codeImg"
            ></img>
            <div className="study-select-info">알고리즘 분류</div>
          </div>
          <div className="option-scroll">
            <Option
              optionData={algoOption}
              checkItems={algoList}
              setCheckItems={setAlgoList}
            ></Option>
          </div>
        </div>
        <div className="study-select-option">
          <div className="green-line">
            <span className="imgbox">
              <img
                src={`https://d2gd6pc034wcta.cloudfront.net/tier/0.svg`}
                alt="티어 이미지"
                className="tierImg"
              ></img>
            </span>
            <div className="study-select-info">난이도</div>
          </div>
          <div className="option-scroll">
            <Option
              optionData={tierOption}
              checkItems={tierList}
              setCheckItems={setTierList}
            ></Option>
          </div>
        </div>
        <div className="study-select-option">
          <div className="green-line">
            <span className="imgbox">❓</span>
            <div className="study-select-info">안푼사람</div>
          </div>
          <div className="option-scroll">
            <Option
              optionData={baekjoonIdOption}
              checkItems={baekjoonIdList}
              setCheckItems={setBaekjoonIdList}
            ></Option>
          </div>
        </div>
      </section>
      <div className="filter-search-box">
        <Button greenLine small onClick={searchProblem}>
          필터 검색
        </Button>
      </div>
      <div className="select-search-box">
        <div className="d-flex search-bar">
          <Form.Control
            type="search"
            placeholder="검색어를 입력해주세요."
            className="me-2 slect-search-form"
            aria-label="Search"
            ref={searchInput}
            onKeyPress={onKeypress}
          />
          <Button greenLine small onClick={problemSearch}>
            Search
          </Button>
        </div>
      </div>
      <section className="study-select-problem-box">
        <div className="green-line">
          <div className="study-select-info">✨ 담긴 문제 ✨</div>
        </div>
        <div className="problem-number-box">
          {problem?.map((item, key) => (
            <div key={key} className="problem-number">
              {item.problemId}
              <span
                className="delete-problem"
                onClick={() => {
                  deleteProblem(item);
                }}
              >
                X
              </span>
            </div>
          ))}
        </div>
      </section>
      <section className="study-select-result">
        <div className="green-line">
          <span className="imgbox">📃</span>
          <div className="study-select-info">문제 목록</div>
          <span className="right">맞힌 사람이 많은 순</span>
        </div>
        <div className="option-scroll">
          <Problem
            optionData={result}
            checkItems={problem}
            setCheckItems={setProblem}
          ></Problem>
        </div>
      </section>
      <section className="study-problem-button study-select-info">
        <Button green large onClick={choiceProblem}>
          문제 선정하기
        </Button>
      </section>
    </main>
  );
};

export default StudySelect;
