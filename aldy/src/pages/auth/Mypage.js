import "./Mypage.css";
import { useState, useEffect } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";
import { getUserInfo, mypageCode } from "../../api/user";

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
          <div>
            {/* <img src="`https://d2gd6pc034wcta.cloudfront.net/tier/${tier}.svg`" /> */}
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
          <StudyListMy />
        </div>
      </section>
    </main>
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
      {myStudyList?.map((item, studyId) => (
        <MyStudyListItem key={studyId} item={item} />
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

export default Mypage;
