import "./CodeCorrect.css";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

const CodeCorrect = () => {
  let { params } = useParams();
  const [code, setCode] = useState("");
  useEffect(() => {
    // params를 활용해 서버로 부터 코드를 가져온다.
    // 가져온 코드를 state에 저장한다.
  }, []);
  return (
    <main className="correct-main">
      <section className="correct-header">
        <p>
          <span>✨ 요청받은 코드에 대한</span>
          <span className="highlight">코드첨삭</span>
          <span>을 진행해주세요 ✨</span>
        </p>
        <h2 className="correct-orange">이번 주 스터디 선정 문제</h2>
      </section>
      <section className="correct-title">
        <Container>
          <Row style={{ color: "#28500F", fontWeight: "bold" }}>
            <Col>스터디 이름</Col>
            <Col>보낸 사람</Col>
            <Col>문제 이름</Col>
            <Col>문제 번호</Col>
            <Col>요청 날짜</Col>
          </Row>
          <hr></hr>
          <Row>
            {/* 데이터 바인딩 해줘야 함 */}
            <Col>알스알스</Col>
            <Col>BOJ</Col>
            <Col>설탕배달</Col>
            <Col>1567</Col>
            <Col>2022.5.15</Col>
          </Row>
        </Container>
      </section>
      <section className="correct-board">
        <div className="correct-board-title">
          <p style={{ margin: "0 25px" }}>✨ 3017번</p>
          <p style={{ margin: "0 25px" }}>가까운 수 찾기 ✨</p>
        </div>
        <div className="correct-content">
          <div className="correct-your-code">
            요청한 코드영역 요청한 코드영역 요청한 코드영역 요청한 코드영역
            요청한 코드영역 요청한 코드영역 요청한 코드영역 요청한 코드영역
            요청한 코드영역 요청한 코드영역 요청한 코드영역
          </div>
          <div className="correct-my-code">
            첨삭한 코드영역 첨삭한 코드영역 첨삭한 코드영역 첨삭한 코드영역
            첨삭한 코드영역 첨삭한 코드영역 첨삭한 코드영역 첨삭한 코드영역
            첨삭한 코드영역 첨삭한 코드영역 첨삭한 코드영역 첨삭한 코드영역
          </div>
        </div>
        <div className="correct-btns">
          <button className="correctBtn">답장 보내기</button>
        </div>
      </section>
    </main>
  );
  return <div>CodeCorrect</div>;
};

export default CodeCorrect;
