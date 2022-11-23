import React from "react";
import styled, { css } from "styled-components";

const StyledButton = styled.button`
  border-radius: 8px;
  outline: none;
  font-weight: bold;
  transition: all 200ms ease-in;
  user-select: none;
  white-space: nowrap;

  // 버튼 색깔
  ${(props) =>
    props.redLine &&
    css`
      background-color: white;
      border: 2px solid red;
      color: red;
      &:hover {
        background-color: red;
        color: white;
      }
    `}
  ${(props) =>
    props.red &&
    css`
      background-color: red;
      border: 2px solid red;
      color: white;
      &:hover {
        background-color: white;
        color: red;
      }
    `}
  
  ${(props) =>
    props.greenLine &&
    css`
      background-color: white;
      border: 2px solid rgba(40, 80, 15, 1);
      color: rgba(40, 80, 15, 1);
      &:hover {
        background-color: rgba(40, 80, 15, 1);
        color: white;
      }
    `}
  ${(props) =>
    props.green &&
    css`
      background-color: rgba(40, 80, 15, 1);
      border: 2px solid rgba(40, 80, 15, 1);
      color: white;
      &:hover {
        background-color: white;
        color: rgba(40, 80, 15, 1);
      }
    `}

  ${(props) =>
    props.brown &&
    css`
      background-color: #4b3200;
      border: 2px solid #4b3200;
      color: white;
      &:hover {
        background-color: white;
        color: #4b3200;
      }
    `}


  // 버튼 크기
  ${(props) =>
    props.xsmall &&
    css`
      font-family: "GmarketSansMedium";
      font-size: 13px;
      width: 75px;
      padding: 5px 0px 3px 0px;
    `}
  ${(props) =>
    props.small &&
    css`
      font-family: "GmarketSansMedium";
      font-size: 14px;
      width: 100px;
      padding: 5px 0px 3px 0px;
    `}
  ${(props) =>
    props.medium &&
    css`
      font-family: "KOFIHDrLEEJWTTF-B";
      font-size: 17px;
      width: 170px;
      padding: 8px;
    `}
  ${(props) =>
    props.large &&
    css`
      font-family: "KOFIHDrLEEJWTTF-B";
      font-size: 19px;
      width: 200px;
      padding: 10px;
    `}
`;

const Button = ({ children, ...props }) => {
  return <StyledButton {...props}>{children}</StyledButton>;
};

export default Button;
