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
        setMessage("????????? ?????????????????????.");
        setAlertRefreshModalShow(true); //????????????
      })
      .catch((err) => {
        setMessage("????????? ?????????????????????. ?????? ??????????????????.");
        setAlertRefreshModalShow(true); //????????????
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
          alt="?????? ?????????"
          width={"350px"}
        ></img>
        <p>
          <span>????????? ???????????? ?????? </span>
          <span className="study-highlight-green"> ????????? </span>
          <span>??????????????? </span>
        </p>
        <h2 className="Mypage-underline-orange">
          <span>????????????</span>
        </h2>
        <div className="Mypage-section1-userinfo">
          <div className="Mypage-tier-nickname">
            <img
              src={`https://d2gd6pc034wcta.cloudfront.net/tier/${tier}.svg`}
              alt="?????? ?????????"
              className="Mypage-tier-img"
            />
            <h2 className="Mypage-section1-userInfo-h2">
              <b>{nickname}</b>??? ???????????????
            </h2>
          </div>
          <div className="mypage-btn-box">
            <Button
              greenLine
              small
              onClick={navigateUserinfo}
              className="study-button"
            >
              ???????????? ??????
            </Button>
            <Button
              greenLine
              small
              onClick={navigateChangePw}
              className="study-button"
            >
              ???????????? ??????
            </Button>
            <Button greenLine small onClick={onRenew} className="study-button">
              ?????? ??????
            </Button>
          </div>
        </div>
        <div>
          <div className="study-list-item">
            <div className="study-list-title">
              <div className="study-id"></div>
              <h5 className="study-name">?????? ????????? ?????? ???</h5>
              <div className="study-number">{answerCodeReviewNumber}???</div>
            </div>
          </div>
        </div>
        <div>
          <div className="study-list-item">
            <div className="study-list-title">
              <div className="study-id"></div>
              <h5 className="study-name">?????? ????????? ?????? ?????? ???</h5>
              <div className="study-number">{replyCodeReviewNumber}???</div>
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
          <span>????????? ?????? ??????</span>
        </h2>
        <p>
          <span>??? ?????? ??? ????????? ???????????? </span>
          <span className="study-highlight-orange">?????? ??????</span>
          <span>??? ???????????????.???</span>
        </p>
      </section>
      <section className="study-list">
        <div className="Mypage-recommend-box">
          <div className="Mypage-recommend-box-title">
            <img
              src={`https://d2gd6pc034wcta.cloudfront.net/tier/${level}.svg`}
              alt="?????? ?????????"
              className="Mypage-tier-img"
            />
            <div className="Mypage-recommend-box-title-titleKo">{titleKo}</div>
          </div>
          <div className="Mypage-recommend-box-content">
            <div className="Mypage-recommend-box-content-text">
              <div>????????????????????? ?????? : {acceptedUserCount}???</div>
              <div>???????????????? ?????? : {algorithm}</div>
              <div>?????????? ?????? : {averageTries}???</div>
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
            ????????? ?????? ??????
            <Lottie
              animationData={reload}
              onClick={newRecommend}
              className="reload-icon"
            ></Lottie>
          </Button>
          <Button greenLine large onClick={mvBoj}>
            ?????? ?????? ??????!
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
          <span>?????? ????????? ????????? ??????</span>
        </h2>
        <p>
          <span>??????????????? ????????? ????????? ?????? </span>
          <span className="study-highlight-orange">????????????</span>
          <span>??? ????????????.???</span>
        </p>
      </section>
      <section className="study-list">
        <div>
          <div className="Mypage-study-list-box">
            <div className="mystudy-search-result-title">??? ????????? ??????</div>
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
