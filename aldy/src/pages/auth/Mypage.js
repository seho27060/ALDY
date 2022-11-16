import "./Mypage.css";
import { useState, useEffect } from "react";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { getUserInfo, mypageCode, tierRenewApi } from "../../api/user";
import { getMyStudy } from "../../api/study";
import { recommendation } from "../../api/user";
import MyStudyListItem from "../../components/study/MyStudyListItem";
import Paging from "../../components/Paging";
import AlertRefreshModal from "../../components/AlertRefreshModal";

const WhiteButton = styled.button`
  width: 110px;
  border-radius: 8px;
  background-color: white;
  border: 2px solid rgb(40, 80, 15);
  outline: none;
  color: rgb(40, 80, 15);
  font-weight: bold;
  transition: transform 30ms ease-in;
  margin: 4px;
`;

const WhiteButtonL = styled.button`
  width: 200px;
  border-radius: 8px;
  background-color: white;
  border: 2px solid rgb(40, 80, 15);
  outline: none;
  color: rgb(40, 80, 15);
  font-weight: bold;
  transition: transform 30ms ease-in;
  font-size: 20px;
  margin: 10px;
`;

const Mypage = () => {
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
        console.log(res.data);
        setBaekjoonId(res.data.baekjoonId);
        setNickname(res.data.nickname);
        setEmail(res.data.email);
        setTier(res.data.tier);
        console.log(baekjoonId);
      })
      .catch((err) => {
        console.log(err);
      });
    mypageCode().then((res) => {
      console.log(res.data);
      setAnswerCodeReviewNumber(res.data.answerCodeReviewNumber);
      setReplyCodeReviewNumber(res.data.replyCodeReviewNumber);
    });
    recommendation().then((res) => {
      console.log(res.data);
      setAcceptedUserCount(res.data.acceptedUserCount);
      setAlgorithm(res.data.algorithm);
      setAverageTries(res.data.averageTries);
      setLevel(res.data.level);
      setProblemId(res.data.problemId);
      setTitleKo(res.data.titleKo);
      console.log("문제추천 잘 뜨는지 확인");
    });
  }, []);

  useEffect(() => {
    getMyStudy(myStudyPageNum)
      .then((res) => {
        const data = res.data.myStudyDtoPage;
        console.log(data);
        setMyStudyList(data.content);
        setMyStudyTotal(data.totalElements);
      })
      .catch((err) => {
        console.log(err);
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
    console.log("티어 갱신");
    tierRenewApi()
      .then((res) => {
        sessionStorage.setItem("tier", res.data.tier);
        console.log(res.data);
        // alert("티어가 갱신되었습니다.");
        // window.location.reload(); //새로고침
        setMessage("티어가 갱신되었습니다.");
        setAlertRefreshModalShow(true); //새로고침
      })
      .catch((err) => {
        console.log(err);
        // alert("갱신에 실패하였습니다. 다시 시도해주세요.");
        // window.location.reload(); //새로고침
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
      console.log("새로운 문제 갱신 잘 뜨는지 확인");
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
          src="/pinkAldy.gif"
          alt="마이 페이지"
          style={{ width: "350px" }}
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
              {nickname}님 안녕하세요
            </h2>
          </div>
          <div>
            <WhiteButton onClick={navigateUserinfo} className="study-button">
              회원정보 수정
            </WhiteButton>
            <WhiteButton onClick={navigateChangePw} className="study-button">
              비밀번호 수정
            </WhiteButton>
            <WhiteButton onClick={onRenew} className="study-button">
              티어 갱신
            </WhiteButton>
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
      <section>
        <img
          className="Mypage-icon"
          src={process.env.PUBLIC_URL + "/mypageRecommend.png"}
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
              <div>맞힌 사람 : {acceptedUserCount}명</div>
              <div>알고리즘 종류 : {algorithm}</div>
              <div>평균 시도 : {averageTries}회</div>
              {/* <div>티어 : {level}</div> */}
              {/* <div>문제 번호 : {problemId}번</div> */}
            </div>
          </div>
        </div>
        <div>
          <WhiteButtonL onClick={newRecommend}>새로운 문제 추천</WhiteButtonL>
          <WhiteButtonL onClick={mvBoj}>문제 풀러 가기!</WhiteButtonL>
        </div>
      </section>
      <section className="study-search">
        <img
          className="Mypage-icon"
          src={process.env.PUBLIC_URL + "/mypageStudyList.png"}
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
          {/* <StudyListMy /> */}
          <div className="Mypage-study-list-box">
            {myStudyList?.map((item, i) => (
              <MyStudyListItem key={i} item={item} />
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
