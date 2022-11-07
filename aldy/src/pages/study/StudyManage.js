import { useState, useEffect } from "react";
import "./StudyManage.css";
import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";
import { useNavigate, useParams, useLocation } from "react-router-dom";
import { deleteStudy } from "../../api/study";
import TierData from "../../data/tier";
import styled from "styled-components";

const RedButton = styled.button`
  width: 70px;
  border-radius: 8px;
  background-color: red;
  border: none;
  outline: none;
  color: white;
  font-weight: bold;
  transition: transform 30ms ease-in;
  margin-left: auto;
`;

const StudyManage = () => {
  const params = useParams();
  const id = params.id || "";
  const navigate = useNavigate();
  const location = useLocation();
  const studyDetail = location.state.studyDetail;
  console.log(studyDetail);
  const createdDate = new Date(studyDetail.createdDate);
  const myId = sessionStorage.getItem("userName");

  const deleteMyStudy = () => {
    if (window.confirm("스터디를 정말 삭제하시겠습니까?") === true) {
      deleteStudy(id)
        .then((res) => {
          // console.log(res);
          navigate("/study/list");
        })
        .catch((err) => {
          console.log(err);
        });
    } else {
    }
  };

  return (
    <main>
      <section className="study-manage-top">
        {myId === studyDetail.leaderBaekjoonId && (
          <div className="delete-study">
            <RedButton onClick={deleteMyStudy}>스터디 삭제</RedButton>
          </div>
        )}
        <h5>{studyDetail.name} 스터디원 살펴보기</h5>
        <h1>{studyDetail.name}</h1>
        <div className="study-manage-blackboard">
          <img
            className="study-manage-img"
            src="/dinosaur_hello.gif"
            alt="스터디 메인 이미지"
          ></img>
          <table>
            <tbody>
              <tr>
                <td style={{ color: "#B4E196" }}>공룡레벨</td>
                <td>lv.20</td>
                <td rowSpan="4">
                  <div
                    style={{
                      color: "#B4E196",
                      fontWeight: "700",
                      paddingBottom: "7px",
                    }}
                  >
                    ✨ 스터디 소개 ✨
                  </div>
                  {studyDetail.introduction}
                </td>
              </tr>
              <tr>
                <td>스터디원</td>
                <td>
                  {studyDetail.countMember}/{studyDetail.upperLimit}
                </td>
              </tr>
              <tr>
                <td>스터디 레벨</td>
                <td>
                  {" "}
                  <img
                    src={`https://d2gd6pc034wcta.cloudfront.net/tier/${studyDetail.threshold}.svg`}
                    alt="티어 이미지"
                    className="tier-image"
                  ></img>
                  {TierData[studyDetail.threshold]}
                </td>
              </tr>
              <tr>
                <td>생성 날짜</td>
                <td>
                  {createdDate.getFullYear()}년 {createdDate.getMonth() + 1}월{" "}
                  {createdDate.getDate()}일
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>
      {/* <section className="study-manage-info">스터디 정보</section> */}
      <section className="study-manage-member">
        <h3 className="study-detail-title">
          <span>스터디원</span>
        </h3>
        <StudyMember></StudyMember>
      </section>
      <section className="study-manage-member">
        <h3 className="study-detail-title">
          <span>스터디원 신청목록</span>
        </h3>
        <StudyJoin></StudyJoin>
      </section>
    </main>
  );
};

const StudyMember = () => {
  const [studyMember, setStudyMember] = useState(null);

  useEffect(() => {
    // 서버에서 내게 요청온 목록 가져와서 list에 저장하기
    setStudyMember([
      {
        studyMemberId: "1",
        studyMemberName: "ssafy123",
        problemNum: "32",
        studyJoinDate: "2020.10.18",
        penalty: 0,
      },
      {
        studyMemberId: "2",
        studyMemberName: "세룽룽",
        problemNum: "52",
        studyJoinDate: "2020.10.22",
        penalty: 3,
      },
      {
        studyMemberId: "3",
        studyMemberName: "asdf1234",
        problemNum: "22",
        studyJoinDate: "2020.10.26",
        penalty: 0,
      },
      {
        studyMemberId: "4",
        studyMemberName: "나는야 스터디왕",
        problemNum: "17",
        studyJoinDate: "2020.10.29",
        penalty: 2,
      },
      {
        studyMemberId: "5",
        studyMemberName: "zmmmm111",
        problemNum: "5",
        studyJoinDate: "2020.11.01",
        penalty: 1,
      },
    ]);
  }, []);

  return (
    <div className="study-list-box">
      {studyMember?.map((item, studyMemberId) => (
        <StudyMemberItem key={studyMemberId} item={item} />
      ))}
    </div>
  );
};

const StudyMemberItem = (props) => {
  const [dropdown, setDropdown] = useState("none");
  const [penalty, setPenalty] = useState(props.item.penalty);

  const penaltyColor = () => {
    if (penalty === 1) {
      setPenalty("penalty-green");
    } else if (penalty === 2) {
      setPenalty("penalty-orange");
    } else if (penalty === 3) {
      setPenalty("penalty-red");
    } else {
      setPenalty("penalty-white");
    }
  };

  useEffect(() => {
    penaltyColor();
  }, []);

  return (
    <div className={`${penalty} study-list-item`}>
      <div className="study-list-title">
        <div className="study-id">{props.item.studyMemberId}</div>
        <h5 className="study-name">{props.item.studyMemberName}</h5>
        <div className="study-number"></div>
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
        <div>함께 푼 문제 : {props.item.problemNum}</div>
        <div>들어온 날짜 : {props.item.studyJoinDate}</div>
        <RedButton>강퇴</RedButton>
      </div>
    </div>
  );
};

const StudyJoin = () => {
  const [studyMember, setStudyMember] = useState(null);

  useEffect(() => {
    // 서버에서 내게 요청온 목록 가져와서 list에 저장하기
    setStudyMember([
      {
        studyJoinId: "1",
        studyJoinName: "abcd123",
        description: "스터디 열심히 하겠습니다~",
      },
      {
        studyJoinId: "2",
        studyJoinName: "스터디 함께해요",
        description: "함께 공부하고 싶어요!!",
      },
      {
        studyJoinId: "3",
        studyJoinName: "aldyaldy111",
        description: "ALDY 화이팅!! ><",
      },
    ]);
  }, []);

  return (
    <div className="study-list-box">
      {studyMember?.map((item, studyJoinId) => (
        <StudyJoinItem key={studyJoinId} item={item} />
      ))}
    </div>
  );
};

const StudyJoinItem = (props) => {
  const [dropdown, setDropdown] = useState("none");

  return (
    <div className="study-list-item">
      <div className="study-list-title">
        <div className="study-id">{props.item.studyJoinId}</div>
        <h5 className="study-name">{props.item.studyJoinName}</h5>
        <div className="study-number"></div>
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
        <div>{props.item.description}</div>
        <RedButton>수락</RedButton>
      </div>
    </div>
  );
};

export default StudyManage;
