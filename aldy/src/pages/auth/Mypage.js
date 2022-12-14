import AlertRefreshModal from "../../components/modal/AlertRefreshModal";
import MyStudyListItem from "../../components/study/MyStudyListItem";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import {
  getUserInfo,
  mypageCode,
  tierRenewApi,
  recommendation,
} from "../../api/user";
import { getMyStudy } from "../../api/study";
import Paging from "../../components/Paging";
import Lottie from "lottie-react";
import reload from "../../lotties/reload.json";
import { useRecoilState } from "recoil";
import { isNav } from "../../store/states";

import "./Mypage.css";
import Button from "../../components/styled/Button";

const Mypage = () => {
  const [nav, setNav] = useRecoilState(isNav);
  setNav(true);

  const [baekjoonId, setBaekjoonId] = useState(null);
  const [nickname, setNickname] = useState(null);
  const [email, setEmail] = useState(null);
  const [tier, setTier] = useState(null);
  const [answerCodeReviewNumber, setAnswerCodeReviewNumber] = useState(null);
  const [replyCodeReviewNumber, setReplyCodeReviewNumber] = useState(null);
  const [acceptedUserCount, setAcceptedUserCount] = useState(null);
  const [algorithm, setAlgorithm] = useState(null);
  const [averageTries, setAverageTries] = useState(null);
  const [level, setLevel] = useState(null);
  const [problemId, setProblemId] = useState(null);
  const [titleKo, setTitleKo] = useState(null);
  const [myStudyList, setMyStudyList] = useState(null);
  // Pagination
  const [myStudyPageNum, setMyStudyPageNum] = useState(1);
  const [myStudyTotal, setMyStudyTotal] = useState(0);
  const [message, setMessage] = useState("");
  const [alertRefreshModalShow, setAlertRefreshModalShow] = useState(false);

  useEffect(() => {
    getUserInfo()
      .then((res) => {
        setBaekjoonId(res.data.baekjoonId);
        setNickname(res.data.nickname);
        setEmail(res.data.email);
        setTier(res.data.tier);
      })
      .catch((err) => {
        // console.log(err);
      });
    mypageCode().then((res) => {
      setAnswerCodeReviewNumber(res.data.answerCodeReviewNumber);
      setReplyCodeReviewNumber(res.data.replyCodeReviewNumber);
    });
    recommendation().then((res) => {
      setAcceptedUserCount(res.data.acceptedUserCount);
      setAlgorithm(res.data.algorithm);
      setAverageTries(res.data.averageTries);
      setLevel(res.data.level);
      setProblemId(res.data.problemId);
      setTitleKo(res.data.titleKo);
    });
  }, []);

  useEffect(() => {
    getMyStudy(myStudyPageNum)
      .then((res) => {
        const data = res.data.myStudyDtoPage;
        setMyStudyList(data.content);
        setMyStudyTotal(data.totalElements);
      })
      .catch((err) => {
        // console.log(err);
      });
  }, [myStudyPageNum]);

  const navigate = useNavigate();

  const navigateUserinfo = () => {
    navigate("/userinfo");
  };
  const navigateChangePw = () => {
    navigate("/changepw");
  };

  const mvBoj = () => {
    window.open(`https://www.acmicpc.net/problem/${problemId}`, "_blank");
  };

  const onRenew = () => {
    tierRenewApi()
      .then((res) => {
        sessionStorage.setItem("tier", res.data.tier);
        setMessage("티어가 갱신되었습니다.");
        setAlertRefreshModalShow(true); //새로고침
      })
      .catch((err) => {
        setMessage("갱신에 실패하였습니다. 다시 시도해주세요.");
        setAlertRefreshModalShow(true); //새로고침
      });
  };

  const newRecommend = () => {
    recommendation().then((res) => {
      setAcceptedUserCount(res.data.acceptedUserCount);
      setAlgorithm(res.data.algorithm);
      setAverageTries(res.data.averageTries);
      setLevel(res.data.level);
      setProblemId(res.data.problemId);
      setTitleKo(res.data.titleKo);
    });
  };

  return (
    <main style={{ userSelect: "none" }}>
      <AlertRefreshModal
        show={alertRefreshModalShow}
        onHide={() => setAlertRefreshModalShow(false)}
        message={message}
      />
      <section className="myPage-list-banner">
        <img
          className="mb-2"
          src="/ALDY/pinkAldy.gif"
          alt="마이 페이지"
          width={"350px"}
        ></img>
        <p>
          <span>✨내가 활동하고 있는 </span>
          <span className="study-highlight-green"> 스터디 </span>
          <span>모아보기✨ </span>
        </p>
        <h2 className="Mypage-underline-orange">
          <span>회원정보</span>
        </h2>
        <div className="Mypage-section1-userinfo">
          <div className="Mypage-tier-nickname">
            <img
              src={`https://d2gd6pc034wcta.cloudfront.net/tier/${tier}.svg`}
              alt="티어 이미지"
              className="Mypage-tier-img"
            />
            <h2 className="Mypage-section1-userInfo-h2">
              <b>{nickname}</b>님 안녕하세요
            </h2>
          </div>
          <div className="mypage-btn-box">
            <Button
              greenLine
              small
              onClick={navigateUserinfo}
              className="study-button"
            >
              회원정보 수정
            </Button>
            <Button
              greenLine
              small
              onClick={navigateChangePw}
              className="study-button"
            >
              비밀번호 수정
            </Button>
            <Button greenLine small onClick={onRenew} className="study-button">
              티어 갱신
            </Button>
          </div>
        </div>
        <div>
          <div className="study-list-item">
            <div className="study-list-title">
              <div className="study-id"></div>
              <h5 className="study-name">내가 리뷰한 코드 수</h5>
              <div className="study-number">{answerCodeReviewNumber}개</div>
            </div>
          </div>
        </div>
        <div>
          <div className="study-list-item">
            <div className="study-list-title">
              <div className="study-id"></div>
              <h5 className="study-name">내가 리뷰한 받은 코드 수</h5>
              <div className="study-number">{replyCodeReviewNumber}개</div>
            </div>
          </div>
        </div>
      </section>
      <section style={{ margin: "30px" }}>
        <img
          className="Mypage-icon"
          src={process.env.PUBLIC_URL + "/icon/mypageRecommend.png"}
          alt=""
        ></img>
        <h2 className="Mypage-underline-orange">
          <span>오늘의 문제 추천</span>
        </h2>
        <p>
          <span>✨ 최근 푼 문제를 기준으로 </span>
          <span className="study-highlight-orange">문제 추천</span>
          <span>을 해드립니다.✨</span>
        </p>
      </section>
      <section className="study-list">
        <div className="Mypage-recommend-box">
          <div className="Mypage-recommend-box-title">
            <img
              src={`https://d2gd6pc034wcta.cloudfront.net/tier/${level}.svg`}
              alt="티어 이미지"
              className="Mypage-tier-img"
            />
            <div className="Mypage-recommend-box-title-titleKo">{titleKo}</div>
          </div>
          <div className="Mypage-recommend-box-content">
            <div className="Mypage-recommend-box-content-text">
              <div>👩🏻‍💻맞힌 사람 : {acceptedUserCount}명</div>
              <div>🧮알고리즘 종류 : {algorithm}</div>
              <div>📊평균 시도 : {averageTries}회</div>
            </div>
          </div>
        </div>
        <div className="mypage-reload-Btn">
          <Button
            greenLine
            large
            onClick={newRecommend}
            style={{ display: "flex", justifyContent: "center" }}
          >
            새로운 문제 추천
            <Lottie
              animationData={reload}
              onClick={newRecommend}
              className="reload-icon"
            ></Lottie>
          </Button>
          <Button greenLine large onClick={mvBoj}>
            문제 풀러 가기!
          </Button>
        </div>
      </section>
      <section className="study-search">
        <img
          className="Mypage-icon"
          src={process.env.PUBLIC_URL + "/icon/mypageStudyList.png"}
          alt=""
        ></img>
        <h2 className="Mypage-underline-orange">
          <span>내가 가입한 스터디 목록</span>
        </h2>
        <p>
          <span>✨스터디에 참여해 문제를 풀고 </span>
          <span className="study-highlight-orange">코드리뷰</span>
          <span>를 해주세요.✨</span>
        </p>
      </section>
      <section className="study-list">
        <div>
          <div className="Mypage-study-list-box">
            <div className="mystudy-search-result-title">내 스터디 목록</div>
            {myStudyList?.map((item, i) => (
              <MyStudyListItem key={i} item={item} num={i} />
            ))}
            <Paging
              page={myStudyPageNum}
              setPage={setMyStudyPageNum}
              totalElements={myStudyTotal}
            />
          </div>
        </div>
      </section>
    </main>
  );
};

export default Mypage;
