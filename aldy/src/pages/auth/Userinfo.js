import "./Userinfo.css";
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

const Userinfo = () => {
  return (
    <main className="userinfo-page-main">
      <div className="userinfo-page-bg">
        <section className="userinfo-page-left">
          <div>✨닉네임과 이메일을 변경할 수 있습니다.✨</div>
          <div className="nnnnnn">회원 정보</div>
          <form>
            <div className="form-title">
              <div>이메일</div>
              <div className="form-title-id">
                <input placeholder="zmmmm111@gmail.com"></input>
                <RedButton>수정하기</RedButton>
              </div>
            </div>
            <div className="form-title">
              <div>닉네임</div>
              <div className="form-title-id">
                <input placeholder="세룽룽"></input>
                <RedButton>수정하기</RedButton>
              </div>
            </div>
          </form>
        </section>
        <section className="userinfo-page-right">
          <div className="userinfo-page-right-title">✨Welcome to Aldy✨</div>
          <div className="userinfo-page-right-text">
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

export default Userinfo;
