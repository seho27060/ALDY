import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import Offcanvas from "react-bootstrap/Offcanvas";

import { useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

import { BsPerson, BsPower } from "react-icons/bs";
import { useRecoilState } from "recoil";
import { isLoggedIn, userName } from "../store/states";

import "./AldyNav.css";
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
  margin-left: 20px;
`;

const AldyNav = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [logged, setLogged] = useRecoilState(isLoggedIn);
  const [username] = useRecoilState(userName);

  const navigateMain = () => {
    navigate("/");
  };
  const navigateStudy = () => {
    navigate("/study/list");
  };
  const navigateReview = () => {
    navigate("/review/list");
  };
  const navigateMypage = () => {
    navigate("/mypage");
  };
  const navigateLogin = () => {
    navigate("/login");
  };

  const [userObject, setUserObject] = useState(true);

  const [activeMain, setActiveMain] = useState(false);
  const [activeStudy, setActiveStudy] = useState(false);
  const [activeReview, setActiveReview] = useState(false);

  useEffect(() => {
    setActiveMain(false);
    setActiveStudy(false);
    setActiveReview(false);
    if (location.pathname === "/") {
      setActiveMain(true);
    } else if (location.pathname.includes("study")) {
      setActiveStudy(true);
    } else if (
      location.pathname.includes("review") ||
      location.pathname.includes("correct")
    ) {
      setActiveReview(true);
    }
  }, [location]);

  return (
    <Navbar expand="md">
      <Container fluid>
        <Navbar.Brand onClick={navigateMain} className="nav-title">
          ALDY
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="offcanvasNavbar-expand-md" />
        <Navbar.Offcanvas
          id="offcanvasNavbar-expand-md"
          aria-labelledby="offcanvasNavbarLabel-expand-md"
          placement="end"
        >
          <Offcanvas.Header closeButton>
            <Offcanvas.Title
              id="offcanvasNavbarLabel-expand-md"
              className="nav-title"
            >
              ALDY
            </Offcanvas.Title>
          </Offcanvas.Header>
          <Offcanvas.Body>
            <Nav className="justify-content-end flex-grow-1 pe-3">
              <Nav.Link
                onClick={navigateMain}
                style={
                  activeMain
                    ? { color: "rgb(230, 50, 70)" }
                    : { color: "rgb(100, 100, 100)" }
                }
              >
                소개
              </Nav.Link>
              <Nav.Link
                onClick={navigateStudy}
                style={
                  activeStudy
                    ? { color: "rgb(230, 50, 70)" }
                    : { color: "rgb(100, 100, 100)" }
                }
              >
                스터디
              </Nav.Link>
              <Nav.Link
                onClick={navigateReview}
                style={
                  activeReview
                    ? { color: "rgb(230, 50, 70)" }
                    : { color: "rgb(100, 100, 100)" }
                }
              >
                코드리뷰
              </Nav.Link>
              {logged ? (
                <div className="nav_info">
                  <img
                    src={process.env.PUBLIC_URL + "/navzookeeper.png"}
                    alt=""
                    width="37px"
                    height="40px"
                  ></img>
                  <NavDropdown
                    title={username}
                    id="offcanvasNavbarDropdown-expand-md"
                  >
                    <NavDropdown.Item onClick={navigateMypage}>
                      <BsPerson />
                      마이페이지
                    </NavDropdown.Item>
                    <NavDropdown.Item
                      href="#action4"
                      onClick={() => {
                        sessionStorage.clear();
                        setLogged(false);
                        alert("로그아웃 되었습니다.");
                      }}
                    >
                      <BsPower />
                      로그아웃
                    </NavDropdown.Item>
                  </NavDropdown>
                </div>
              ) : (
                <RedButton
                  onClick={() => {
                    navigateLogin();
                  }}
                >
                  로그인
                </RedButton>
              )}
            </Nav>
            {!userObject ? <RedButton>로그인</RedButton> : ""}
          </Offcanvas.Body>
        </Navbar.Offcanvas>
      </Container>
    </Navbar>
  );
};

export default AldyNav;
