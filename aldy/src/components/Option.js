import { useState } from "react";
import styled from "styled-components";

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

const Option = ({ optionData, checkItems, setCheckItems }) => {
  const data = Object.keys(optionData).filter((i) => i !== "0");
  const optionName = data[0];
  // console.log(optionName, checkItems);

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
      data.forEach((el) => idArray.push(el));
      setCheckItems(idArray);
    } else {
      setCheckItems([]);
    }
  };

  return (
    <StyledTable>
      {optionName === "graphs" || optionName === "1" ? null : (
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
      )}
      <tbody>
        {data?.map((item, key) => (
          <tr key={key}>
            <td>
              <input
                type="checkbox"
                name={item}
                id={item}
                onChange={(e) => handleSingleCheck(e.target.checked, item)}
                checked={checkItems.includes(item) ? true : false}
              />
              <label htmlFor={item}></label>
            </td>
            <td className="second-row">
              <label htmlFor={item}>{optionData[item]}</label>
            </td>
          </tr>
        ))}
      </tbody>
    </StyledTable>
  );
};

export default Option;
