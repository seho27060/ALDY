import TierSelect from "../../data/tierSelect";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./StudyCreate.css";
import styled from "styled-components";
import { createStudy } from "../../api/study";
import Select from "react-select";

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
  const myTier = sessionStorage.getItem("tier");
  const [newStudy, setNewStudy] = useState({
    name: "",
    upperLimit: null,
    introduction: "",
    threshold: null,
    visibility: null,
  });
  const studyNumber = [
    { value: 2, label: 2 },
    { value: 3, label: 3 },
    { value: 4, label: 4 },
    { value: 5, label: 5 },
    { value: 6, label: 6 },
  ];
  const tierSelectOption = TierSelect.filter((item) => item.value <= myTier);

  const onChange = (e) => {
    const { name, value } = e.target;
    setNewStudy((prev) => {
      return {
        ...prev,
        [name]: value,
      };
    });
  };
  const onSelectChange = (e, name) => {
    const value = e.value;
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
                <Select
                  onChange={(e) => {
                    onSelectChange(e, "upperLimit");
                  }}
                  options={studyNumber}
                ></Select>
              </div>
              <div className="StudyCreate-form-title">
                <div>스터디 공개 범위</div>
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
            </div>
            <div className="StudyCreate-form-title">
              <div>스터디 가입 요건</div>
              <Select
                onChange={(e) => {
                  onSelectChange(e, "threshold");
                }}
                options={tierSelectOption}
              ></Select>
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
