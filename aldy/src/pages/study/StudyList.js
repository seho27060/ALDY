import "./StudyList.css";
import { useState, useEffect } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
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

const StudyList = () => {
  const [tab, setTab] = useState("requestToMe");
  return (
    <main>
      <section className="study-list-banner">
        <img
          className="study-main-img"
          src="/dinosaur.png"
          alt="코드리뷰 메인 이미지"
        ></img>
        <p>
          <span>다른 사람들과 함께 </span>
          <span className="study-highlight-green">코드리뷰</span>
          <span>를 하며 </span>
          <span className="study-highlight-green">공룡</span>
          <span>을 키워볼 기회</span>
        </p>
        <h2 className="study-underline-green">
          지금 바로 스터디를 만들어보세요!
        </h2>
        <RedButton className="study-button">스터디 생성하기</RedButton>
      </section>
      <section className="study-search">
        <p>
          <span>다른 사람들과 함께 </span>
          <span className="study-highlight-orange">코드리뷰</span>
          <span>를 하며 </span>
          <span className="study-highlight-orange">공룡</span>
          <span>을 키워볼 기회</span>
        </p>
        <h2 className="study-underline-orange">
          원하는 스터디 페이지로 들어가 가입신청을 해주세요!
        </h2>
        <div className="search-box">
          <Form className="d-flex search-bar">
            <Form.Control
              type="search"
              placeholder="Search"
              className="me-2"
              aria-label="Search"
            />
            <Button variant="outline-success" className="search-button">
              Search
            </Button>
          </Form>
        </div>
      </section>
    </main>
  );
};

export default StudyList;
