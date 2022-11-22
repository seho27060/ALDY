import Container from "react-bootstrap/Container";
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

import LoginAlert from "./modal/LoginAlert";
import AlertModal from "../components/modal/AlertModal";

import "./AldyNav.css";
import Button from "./styled/Button";

const AldyNav = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [logged, setLogged] = useRecoilState(isLoggedIn);
  const [username] = useRecoilState(userName);
  const [loginAlertShow, setLoginAlertShow] = useState(false);

  // 모달
  const [message, setMessage] = useState("");
  const [alertModalShow, setAlertModalShow] = useState(false);

  const navigateMain = () => {
    navigate("/");
  };
  const navigateStudy = () => {
    if (logged) {
      navigate("/study/list");
    } else {
      setLoginAlertShow(true);
    }
  };
  const navigateReview = () => {
    if (logged) {
      navigate("/review/list");
    } else {
      setLoginAlertShow(true);
    }
  };
  const navigateMypage = () => {
    navigate("/mypage");
  };
  const navigateLogin = () => {
    navigate("/login");
  };

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
      <AlertModal
        show={alertModalShow}
        onHide={() => {
          setAlertModalShow(false);
        }}
        message={message}
      />
      <LoginAlert
        show={loginAlertShow}
        onHide={() => setLoginAlertShow(false)}
      />
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
                    src={process.env.PUBLIC_URL + "/icon/navzookeeper.png"}
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
                        setMessage("로그아웃 되었습니다.");
                        setAlertModalShow(true);
                        navigateMain();
                      }}
                    >
                      <BsPower />
                      로그아웃
                    </NavDropdown.Item>
                  </NavDropdown>
                </div>
              ) : (
                <Button
                  redLine
                  medium
                  onClick={() => {
                    navigateLogin();
                  }}
                  style={{ marginLeft: "15px" }}
                >
                  로그인
                </Button>
              )}
            </Nav>
          </Offcanvas.Body>
        </Navbar.Offcanvas>
      </Container>
    </Navbar>
  );
};

export default AldyNav;
