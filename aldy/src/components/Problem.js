import { useState } from "react";
import styled from "styled-components";
import TierData from "../data/tier";

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

const Problem = ({ optionData, checkItems, setCheckItems }) => {
  const optionName = "" && optionData[0].titleKo;

  // 체크박스 단일 선택
  const handleSingleCheck = (checked, item) => {
    if (checked) {
      for (let i = 0; i < checkItems.length; i++) {
        if (checkItems[i].problemId === item.problemId) {
          setCheckItems(
            checkItems.filter((el) => el.problemId !== item.problemId)
          );
          break;
        }
      }
      setCheckItems((prev) => [...prev, item]);
    } else {
      setCheckItems(checkItems.filter((el) => el !== item));
    }
  };

  return (
    <div>
      {optionData.length === 0 ? (
        <div className="problem-search-none">
          알고리즘 분류, 난이도, 안푼사람 체크 후 문제를 검색해주세요.
        </div>
      ) : (
        <StyledTable>
          <tbody>
            {optionData?.map((data, key) => (
              <tr key={key} className="problem-box">
                <td>
                  <input
                    type="checkbox"
                    name={`${optionName}-${data.problemId}`}
                    id={`${optionName}-${data.problemId}`}
                    onChange={(e) => handleSingleCheck(e.target.checked, data)}
                    checked={checkItems.includes(data) ? true : false}
                  />
                  <label htmlFor={`${optionName}-${data.problemId}`}></label>
                </td>
                <td className="second-row">
                  <label
                    htmlFor={`${optionName}-${data.problemId}`}
                    className="problem-item"
                  >
                    <img
                      src={`https://d2gd6pc034wcta.cloudfront.net/tier/${data.level}.svg`}
                      alt="티어 이미지"
                      className="problem-tier-image"
                    ></img>
                    <b>{data.problemId}번</b>
                    <div className="problem-text">{data.titleKo}</div>
                    <div className="problem-small-text problem-text-margin">
                      맞힌 사람 : {data.acceptedUserCount}
                    </div>
                    <div className="problem-text-line">|</div>
                    <div className="problem-small-text">
                      평균 시도 횟수 : {data.averageTries}
                    </div>
                  </label>
                </td>
              </tr>
            ))}
          </tbody>
        </StyledTable>
      )}
    </div>
  );
};

export default Problem;
