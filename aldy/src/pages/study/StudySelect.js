import "./StudySelect.css";
import { useLocation } from "react-router-dom";
import { useState, useEffect } from "react";
import styled from "styled-components";

const RedButton = styled.button`
  width: 60px;
  border-radius: 8px;
  background-color: red;
  border: none;
  outline: none;
  color: white;
  font-weight: bold;
  transition: transform 30ms ease-in;
`;

const WhiteButton = styled.button`
  width: 70px;
  border-radius: 8px;
  background-color: white;
  border: 2px solid red;
  outline: none;
  color: red;
  font-weight: bold;
  transition: transform 30ms ease-in;
`;

const StyledTable = styled.table`
  border-collapse: seperate;
  thead {
    tr {
      th {
        padding: 3px 5px;
        font-weight: 700;
        // border-bottom: 1px solid #eee;
      }
    }
  }
  tbody {
    tr {
      td {
        padding: 3px 5px;
        // border-bottom: 1px solid #eee;
      }
    }
  }
`;

const StudySelect = () => {
  const location = useLocation();

  const date = location.state.date;
  const week = ["일", "월", "화", "수", "목", "금", "토"];

  // 알고리즘 분류
  const algorithm = [
    { id: 0, title: "알고리즘" },
    { id: 1, title: "BFS" },
    { id: 2, title: "DFS" },
    { id: 3, title: "브루트 포스" },
    { id: 4, title: "DP" },
    { id: 5, title: "이진탐색" },
  ];

  const rank = [
    { id: 0, title: "난이도" },
    { id: 1, title: "Gold1" },
    { id: 2, title: "Gold2" },
    { id: 3, title: "Gold3" },
    { id: 4, title: "Gold4" },
    { id: 5, title: "Gold5" },
  ];

  const member = [
    { id: 0, title: "안푼사람" },
    { id: 1, title: "스터디원1" },
    { id: 2, title: "스터디원2" },
    { id: 3, title: "스터디원3" },
    { id: 4, title: "스터디원4" },
    { id: 5, title: "스터디원5" },
  ];

  const result = [
    { id: 0, number: 0, title: "문제 목록" },
    { id: 1, number: 2839, title: "설탕배달" },
    { id: 2, number: 1712, title: "손익분기점" },
    { id: 3, number: 2869, title: "달팽이는 올라가고 싶다" },
    { id: 4, number: 14503, title: "로봇 청소기" },
  ];

  const problem = [
    { id: 0, number: 0 },
    { id: 1, number: 2839 },
    { id: 2, number: 1712 },
    { id: 3, number: 2869 },
    { id: 4, number: 14503 },
  ];

  return (
    <main style={{ textAlign: "start" }}>
      <section className="study-select-title-box">
        <h1 className="study-select-title">
          <span>문제 선정 하기</span>
        </h1>
        <div>
          <div className="study-select-small-title">
            🔥필터 선택 후 검색 버튼을 눌러 문제를 선정해주세요🔥
          </div>
          <div className="study-select-date">
            {date.getFullYear()}년 {date.getMonth() + 1}월 {date.getDate()}일{" "}
            {week[date.getDay()]}요일
          </div>
        </div>
      </section>
      <section className="study-select-search-box">
        <div className="study-select-option">
          <div className="green-line">
            <img src="/code_person.png" alt="코딩하는사람"></img>
            <div>알고리즘 분류</div>
          </div>
          <div className="option-scroll">
            <Option data={algorithm}></Option>
          </div>
        </div>
        <div className="study-select-option">
          <div className="green-line">
            <img src="/code_person.png" alt="코딩하는사람"></img>
            <div>난이도</div>
          </div>
          <div className="option-scroll">
            <Option data={rank}></Option>
          </div>
        </div>
        <div className="study-select-option">
          <div className="green-line">
            <img src="/code_person.png" alt="코딩하는사람"></img>
            <div>안푼사람</div>
          </div>
          <div className="option-scroll">
            <Option data={member}></Option>
          </div>
        </div>
      </section>
      <div style={{ margin: "0px 10%", textAlign: "end" }}>
        <WhiteButton>검색</WhiteButton>
      </div>
      <section className="study-select-problem-box">
        <div className="green-line">
          <div>✨ 담긴 문제 ✨</div>
        </div>
        <div className="problem-number-box">
          {problem?.map((problem, key) => (
            <span key={key} className="problem-number">
              {problem.number}
            </span>
          ))}
        </div>
      </section>
      <section className="study-select-result">
        <div className="green-line">
          <img src="/code_person.png" alt="코딩하는사람"></img>
          <div>문제 목록</div>
          <span className="right">맞힌 사람이 많은 순</span>
        </div>
        <div className="option-scroll">
          <Problem data={result}></Problem>
        </div>
      </section>
    </main>
  );
};

const Option = (props) => {
  const optionName = props.data[0].title;
  const data = props.data.filter((item) => item.id !== 0);

  const [checkItems, setCheckItems] = useState([]);
  console.log(optionName, checkItems);

  // 체크박스 단일 선택
  const handleSingleCheck = (checked, id) => {
    if (checked) {
      setCheckItems((prev) => [...prev, id]);
    } else {
      setCheckItems(checkItems.filter((el) => el !== id));
    }
  };

  // 체크박스 전체 선택
  const handleAllCheck = (checked) => {
    if (checked) {
      const idArray = [];
      data.forEach((el) => idArray.push(el.id));
      setCheckItems(idArray);
    } else {
      setCheckItems([]);
    }
  };

  return (
    <StyledTable>
      <thead>
        <tr>
          <th>
            <input
              type="checkbox"
              name="select-all"
              id={`${optionName}-select-all`}
              onChange={(e) => handleAllCheck(e.target.checked)}
              checked={checkItems.length === data.length ? true : false}
            />
            <label htmlFor={`${optionName}-select-all`}></label>
          </th>
          <th className="second-row">
            <label htmlFor={`${optionName}-select-all`}>전체 선택</label>
          </th>
        </tr>
      </thead>
      <tbody>
        {data?.map((data, key) => (
          <tr key={key}>
            <td>
              <input
                type="checkbox"
                name={`${optionName}-${data.id}`}
                id={`${optionName}-${data.id}`}
                onChange={(e) => handleSingleCheck(e.target.checked, data.id)}
                checked={checkItems.includes(data.id) ? true : false}
              />
              <label htmlFor={`${optionName}-${data.id}`}></label>
            </td>
            <td className="second-row">
              <label htmlFor={`${optionName}-${data.id}`}>{data.title}</label>
            </td>
          </tr>
        ))}
      </tbody>
    </StyledTable>
  );
};

const Problem = (props) => {
  const optionName = props.data[0].title;
  const data = props.data.filter((item) => item.id !== 0);

  const [checkItems, setCheckItems] = useState([]);
  console.log(optionName, checkItems);

  // 체크박스 단일 선택
  const handleSingleCheck = (checked, id) => {
    if (checked) {
      setCheckItems((prev) => [...prev, id]);
    } else {
      setCheckItems(checkItems.filter((el) => el !== id));
    }
  };

  // 체크박스 전체 선택
  const handleAllCheck = (checked) => {
    if (checked) {
      const idArray = [];
      data.forEach((el) => idArray.push(el.id));
      setCheckItems(idArray);
    } else {
      setCheckItems([]);
    }
  };

  return (
    <StyledTable>
      <thead>
        <tr className="problem-box">
          <th>
            <input
              type="checkbox"
              name="select-all"
              id={`${optionName}-select-all`}
              onChange={(e) => handleAllCheck(e.target.checked)}
              checked={checkItems.length === data.length ? true : false}
            />
            <label htmlFor={`${optionName}-select-all`}></label>
          </th>
          <th className="second-row">
            <label htmlFor={`${optionName}-select-all`}>전체 선택</label>
          </th>
        </tr>
      </thead>
      <tbody>
        {data?.map((data, key) => (
          <tr key={key} className="problem-box">
            <td>
              <input
                type="checkbox"
                name={`${optionName}-${data.id}`}
                id={`${optionName}-${data.id}`}
                onChange={(e) => handleSingleCheck(e.target.checked, data.id)}
                checked={checkItems.includes(data.id) ? true : false}
              />
              <label htmlFor={`${optionName}-${data.id}`}></label>
            </td>
            <td className="second-row">
              <label htmlFor={`${optionName}-${data.id}`}>
                <b>{data.number}번</b> {data.title}
              </label>
            </td>
          </tr>
        ))}
      </tbody>
    </StyledTable>
  );
};

export default StudySelect;
