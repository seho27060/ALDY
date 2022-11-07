import { useState, useEffect } from "react";
import "./StudyManage.css";
import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";
import { useNavigate, useParams, useLocation } from "react-router-dom";
import { deleteStudy } from "../../api/study";
import TierData from "../../data/tier";
import styled from "styled-components";
import StudyMember from "../../components/study/StudyMember";
import StudyJoin from "../../components/study/StudyJoin";

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
                <td rowSpan="5">
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
                <td>스터디장</td>
                <td>{studyDetail.leaderBaekjoonId}</td>
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
        <StudyMember id={id}></StudyMember>
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

export default StudyManage;
