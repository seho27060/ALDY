import "./StudyCreate.css";

import styled from "styled-components";

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
                <input placeholder="스터디 이름을 입력해 주세요."></input>
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
                  name="number"
                  placeholder="2~6명"
                ></input>
              </div>
              <div className="StudyCreate-form-title">
                <div>스터디 공개 범위</div>
                {/* <input placeholder="공개, 비공개"></input> */}
                <label>
                  <input type="radio" name="radio" value="yes" /> 공개
                </label>
                <label>
                  <input type="radio" name="radio" value="no" /> 비공개
                </label>
              </div>
            </div>
            <div className="StudyCreate-form-title">
              <div>스터디 설명</div>
              <textarea placeholder="스터디 설명을 입력해 주세요."></textarea>
              {/* <input placeholder="스터디 설명을 입력해 주세요."></input> */}
            </div>
            <div className="StudyCreate-form-title">
              <div>스터디 가입 요건</div>
              <input placeholder="스터디 가입 요건을 입력해 주세요."></input>
            </div>
            <div className="StudyCreate-submit-btn">
              <RedButton>스터디 생성하기</RedButton>
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
