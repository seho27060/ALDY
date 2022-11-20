import "./MyStudyListItem.css";
import TierData from "../../data/tier";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { FaChevronCircleDown, FaChevronCircleUp } from "react-icons/fa";

const MyStudyListItem = (props) => {
  const [dropdown, setDropdown] = useState("none");

  const navigate = useNavigate();
  const navigateMyStudyDetail = () => {
    navigate(`/study/detail/${props.item.id}`);
  };

  console.log(props);

  return (
    <div className="study-list-item">
      <div className="study-list-title">
        <div className="study-id">
          <img
            src={
              process.env.PUBLIC_URL +
              `/aldyhead${((props.num + 3) % 10) + 1}.png`
            }
            alt=""
            width="100%"
          ></img>
        </div>
        {`${props.item.visibility}` === "1" ? (
          <div className="not-secret-study">공개</div>
        ) : (
          <div className="secret-study">비공개</div>
        )}
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
              className="tier-image"
            ></img>
            <div>{TierData[props.item.threshold]}</div>
          </div>
          <div>{props.item.introduction}</div>
        </div>
        <div className="my-study-description2">
          <div>함께 푼 문제 수 : {props.item.numberOfSolvedProblem}</div>
          <div>시작한 날짜 : {props.item.createdDate.substring(0, 10)}</div>
          <div>
            최근 해결한 문제 티어 :{" "}
            <img
              src={`https://d2gd6pc034wcta.cloudfront.net/tier/${props.item.tierOfRecentSolvedProblem}.svg`}
              alt="티어 이미지"
              className="tier-image"
            ></img>
            {TierData[props.item.tierOfRecentSolvedProblem]}
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyStudyListItem;
