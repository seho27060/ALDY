import "./Mypage.css";
import { useState, useEffect } from "react";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";
import { getUserInfo, mypageCode } from "../../api/user";
import { getMyStudy } from "../../api/study";
import MyStudyListItem from "../../components/MyStudyListItem";
import Paging from "../../components/Paging";

const RedButton = styled.button`
  width: 170px;
  border-radius: 8px;
  background-color: red;
  border: none;
  outline: none;
  color: white;
  font-weight: bold;
  transition: transform 30ms ease-in;
  margin: 10px;
`;

const Mypage = () => {
  // const [credentials, setCredentials] = useState({
  //   baekjoonId: "",
  //   email: "",
  //   nickname: "",
  //   tear: 0,
  //   answerCodeReviewNumber: 0,
  //   replyCodeReviewNumber: 0,
  // });
  const [baekjoonId, setBaekjoonId] = useState(null);
  const [nickname, setNickname] = useState(null);
  const [email, setEmail] = useState(null);
  const [tier, setTier] = useState(null);
  const [answerCodeReviewNumber, setAnswerCodeReviewNumber] = useState(null);
  const [replyCodeReviewNumber, setReplyCodeReviewNumber] = useState(null);

  const [tab, setTab] = useState("studyListAll");

  const [myStudyList, setMyStudyList] = useState(null);
  // Pagination

  const [myStudyPageNum, setMyStudyPageNum] = useState(1);

  const [myStudyTotal, setMyStudyTotal] = useState(0);

  useEffect(() => {
    getUserInfo()
      .then((res) => {
        console.log(res.data);
        // setCredentials((credentials.baekjoonId = res.data.baekjoonId));
        // setCredentials((credentials.email = res.data.email));
        // setCredentials((credentials.nickname = res.data.nickname));
        // setCredentials((credentials.tear = res.data.tear));
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
      // setCredentials(
      //   (credentials.answerCodeReviewNumber = res.data.answerCodeReviewNumber)
      // );
      // setCredentials(
      //   (credentials.replyCodeReviewNumber = res.data.replyCodeReviewNumber)
      // );
      // console.log(credentials, "ddd");
      setAnswerCodeReviewNumber(res.data.answerCodeReviewNumber);
      setReplyCodeReviewNumber(res.data.replyCodeReviewNumber);
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

  // console.log(credentials, "t");
  console.log(baekjoonId, "t");

  return (
    <main>
      <section className="study-list-banner">
        <img
          className="study-main-img"
          src="/dinosaur.png"
          alt="마이 페이지"
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
            <h2>{nickname}님 안녕하세요</h2>
          </div>
          <div>
            <RedButton onClick={navigateUserinfo} className="study-button">
              회원정보 수정
            </RedButton>
            <RedButton onClick={navigateChangePw} className="study-button">
              비밀번호 수정
            </RedButton>
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
      <section className="study-search">
        <img src={process.env.PUBLIC_URL + "/mypageStudyList.png"} alt=""></img>
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
