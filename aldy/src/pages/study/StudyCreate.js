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
            <div className="form-title">
              <div>스터디 이름</div>
              <div className="form-title-id">
                <input placeholder="스터디 이름을 입력해 주세요."></input>
              </div>
            </div>
            <div className="form-title">
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
