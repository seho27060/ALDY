import "./MyStudyListItem.css";
import TierData from "../../src/data/tier";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";

const MyStudyListItem = (props) => {
  const [dropdown, setDropdown] = useState("none");

  const navigate = useNavigate();
  const navigateMyStudyDetail = () => {
    navigate(`/study/detail/${props.item.studyId}`);
  };

  return (
    <div className="study-list-item">
      <div className="study-list-title">
        <div className="study-id">공룡</div>
        <h5
          className={`study-name ${dropdown === "active" ? "big-size" : ""}`}
          onClick={navigateMyStudyDetail}
        >
          {props.item.name}
        </h5>
        <div className="study-number">
          {props.item.countMember}/{props.item.upperLimit}
        </div>
        {dropdown === "none" && (
          <FaChevronCircleDown
            className="down-icon"
            onClick={() => {
              setDropdown("active");
            }}
          />
        )}
        {dropdown === "active" && (
          <FaChevronCircleUp
            className="down-icon"
            onClick={() => {
              setDropdown("none");
            }}
          />
        )}
      </div>

      <div
        className={`my-study-list-content ${
          dropdown === "active" ? "content-active" : ""
        }`}
      >
        <div className="my-study-description1">
          <div className="study-rank">
            <img
              src={`https://d2gd6pc034wcta.cloudfront.net/tier/${props.item.threshold}.svg`}
              alt="티어 이미지"
            ></img>
            <div>{TierData[props.item.threshold]}</div>
          </div>
          <div>{props.item.introduction}</div>
        </div>
        <div className="my-study-description2">
          <div>함께 푼 문제 수 : {props.item.problemNum}</div>
          <div>시작한 날짜 : {props.item.createdDate.substring(0, 10)}</div>
          <div>최근 해결한 문제 티어 : {props.item.recentRank}</div>
        </div>
      </div>
    </div>
  );
};

export default MyStudyListItem;
