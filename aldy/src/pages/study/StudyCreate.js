import TierData from "../../data/tier";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./StudyCreate.css";
import styled from "styled-components";
import { createStudy } from "../../api/study";

const RedButton = styled.button`
  width: 170px;
  border-radius: 8px;
  background-color: red;
  border: none;
  outline: none;
  color: white;
  font-weight: bold;
  transition: transform 30ms ease-in;
`;

const StudyCreate = () => {
  const navigate = useNavigate();
  const [newStudy, setNewStudy] = useState({
    name: "",
    upperLimit: null,
    introduction: "",
    threshold: null,
    visibility: null,
  });
  const [tierLabel, setTierLabel] = useState([0, "티어를 선택해주세요."]);

  // input 값 변경
  const tierChange = (e) => {
    const { name, value } = e.target;
    setTierLabel([value, TierData[value]]);
    setNewStudy((prev) => {
      return {
        ...prev,
        [name]: value,
      };
    });
  };
  const onChange = (e) => {
    const { name, value } = e.target;
    setNewStudy((prev) => {
      return {
        ...prev,
        [name]: value,
      };
    });
  };
  // 스터디 생성
  const createNewStudy = async (e) => {
    e.preventDefault();
    await createStudy(newStudy)
      .then((res) => {
        console.log(res.data);
        navigate(`/study/detail/${res.data.id}`);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <main className="StudyCreate-page-main">
      <div className="StudyCreate-page-bg">
        <section className="StudyCreate-page-left">
          <div>✨우리만의 스터디를 생성해 보세요.✨</div>
          {/* 여기 수정 */}
          <div className="nnnnnn">스터디 생성</div>
          <form>
            <div className="StudyCreate-form-title">
              <div>스터디 이름</div>
              <div className="form-title-id">
                <input
                  name="name"
                  placeholder="스터디 이름을 입력해 주세요."
                  value={newStudy.name}
                  onChange={onChange}
                  required
                ></input>
              </div>
            </div>
            <div className="StudyCreate-form-second">
              <div className="StudyCreate-form-title">
                <div>스터디 제한 인원</div>
                <input
                  type="number"
                  min="2"
                  max="6"
                  step="1"
                  name="upperLimit"
                  placeholder="2~6명"
                  value={newStudy.upperLimit || ""}
                  onChange={onChange}
                  required
                ></input>
              </div>
              <div className="StudyCreate-form-title">
                <div>스터디 공개 범위</div>
                {/* <input placeholder="공개, 비공개"></input> */}
                <label>
                  <input
                    type="radio"
                    name="visibility"
                    value={1}
                    onChange={onChange}
                  />{" "}
                  공개
                </label>
                <label>
                  <input
                    type="radio"
                    name="visibility"
                    value={0}
                    onChange={onChange}
                  />{" "}
                  비공개
                </label>
              </div>
            </div>
            <div className="StudyCreate-form-title">
              <div>스터디 설명</div>
              <textarea
                placeholder="스터디 설명을 입력해 주세요."
                name="introduction"
                value={newStudy.introduction}
                onChange={onChange}
              ></textarea>
              {/* <input placeholder="스터디 설명을 입력해 주세요."></input> */}
            </div>
            <div className="StudyCreate-form-title">
              <div>스터디 가입 요건</div>
              {/* <input placeholder="스터디 가입 요건을 입력해 주세요."></input> */}
              <input
                type="number"
                min="1"
                max={sessionStorage.getItem("tier")}
                step="1"
                name="threshold"
                placeholder="티어"
                value={newStudy.threshold || ""}
                id="tier"
                onChange={tierChange}
              ></input>
              <label htmlFor="tier">
                {" "}
                <img
                  src={`https://d2gd6pc034wcta.cloudfront.net/tier/${tierLabel[0]}.svg`}
                  alt="티어 이미지"
                  className="tier-image"
                ></img>
                {tierLabel[1]}
              </label>
            </div>
            <div className="StudyCreate-submit-btn">
              <RedButton onClick={createNewStudy}>스터디 생성하기</RedButton>
            </div>
          </form>
        </section>
        <section className="StudyCreate-page-right">
          <div className="StudyCreate-page-right-title">
            ✨Welcome to Aldy✨
          </div>
          <div className="StudyCreate-page-right-text">
            Aldy와 함께 알고리즘 스터디를 키워보세요!
          </div>
          <img
            src={process.env.PUBLIC_URL + "/signup_dinosaur.png"}
            alt=""
          ></img>
        </section>
      </div>
    </main>
  );
};

export default StudyCreate;
