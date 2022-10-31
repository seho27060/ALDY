import "./ChangePW.css";
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

const Changepw = () => {
  return (
    <main className="Changepw-page-main">
      <div className="Changepw-page-bg">
        <section className="Changepw-page-left">
          <div>✨변경할 비밀번호를 입력해주세요.✨</div>
          {/* 여기 수정 */}
          <div className="nnnnnn">비밀번호 수정</div>
          <form>
            <div className="form-title">
              <div>비밀번호</div>
              <div className="form-title-id">
                <input placeholder="비밀번호를 입력해주세요."></input>
              </div>
            </div>
            <div className="form-title">
              <div>비밀번호 확인</div>
              <input placeholder="비밀번호를 확인해주세요."></input>
            </div>
            <div className="Changepw-submit-btn">
              <RedButton>비밀번호 변경하기</RedButton>
            </div>
          </form>
        </section>
        <section className="Changepw-page-right">
          <div className="Changepw-page-right-title">✨Welcome to Aldy✨</div>
          <div className="Changepw-page-right-text">
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

export default Changepw;
