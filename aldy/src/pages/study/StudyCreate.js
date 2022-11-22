import AlertModal from "../../components/modal/AlertModal";
import React, { useState } from "react";
import { useRecoilState } from "recoil";
import { useNavigate } from "react-router-dom";
import TierSelect from "../../data/tierSelect";
import { createStudy } from "../../api/study";
import Select from "react-select";
import { isNav } from "../../store/states";

import "./StudyCreate.css";
import Button from "../../components/styled/Button";

const StudyCreate = () => {
  const navigate = useNavigate();
  const [nav, setNav] = useRecoilState(isNav);
  setNav(true);
  const myTier = sessionStorage.getItem("tier");
  const [newStudy, setNewStudy] = useState({
    name: "",
    upperLimit: null,
    introduction: "",
    threshold: null,
    visibility: null,
  });
  const studyNumber = [
    { value: 2, label: 2 },
    { value: 3, label: 3 },
    { value: 4, label: 4 },
    { value: 5, label: 5 },
    { value: 6, label: 6 },
  ];
  const tierSelectOption = TierSelect.filter((item) => item.value <= myTier);
  // alert Modal
  const [message, setMessage] = useState("");
  const [alertModalShow, setAlertModalShow] = useState(false);

  const onChange = (e) => {
    const { name, value } = e.target;
    setNewStudy((prev) => {
      return {
        ...prev,
        [name]: value,
      };
    });
  };
  const onSelectChange = (e, name) => {
    const value = e.value;
    setNewStudy((prev) => {
      return {
        ...prev,
        [name]: value,
      };
    });
  };
  // 스터디 생성
  const createNewStudy = async (e) => {
    e.preventDefault();
    if (
      newStudy.name &&
      newStudy.introduction &&
      newStudy.threshold &&
      newStudy.upperLimit &&
      newStudy.visibility
    ) {
      await createStudy(newStudy)
        .then((res) => {
          // console.log(res.data);
          navigate(`/study/detail/${res.data.id}`);
        })
        .catch((err) => {
          // console.log(err);
        });
    } else {
      setMessage("스터디 정보를 모두 입력해주세요.");
      setAlertModalShow(true);
    }
  };

  return (
    <main className="study-create-page-main">
      <AlertModal
        show={alertModalShow}
        onHide={() => {
          setAlertModalShow(false);
        }}
        message={message}
      />
      <div className="study-create-page-bg">
        <section className="study-create-page-left">
          <div className="study-create-info">
            ✨우리만의 스터디를 생성해 보세요.✨
          </div>
          <div className="study-create-title study-underline-orange">
            스터디 생성
          </div>
          <form>
            <div className="study-create-form-title">
              <div className="study-create-form-info">스터디 이름</div>
              <input
                name="name"
                placeholder="스터디 이름을 입력해 주세요."
                value={newStudy.name}
                onChange={onChange}
                className="study-create-input"
                maxLength={12}
                required
              ></input>
            </div>
            <div className="study-create-form-second">
              <div className="study-create-form-title">
                <div className="study-create-form-info">스터디 제한 인원</div>
                <Select
                  onChange={(e) => {
                    onSelectChange(e, "upperLimit");
                  }}
                  options={studyNumber}
                  theme={(theme) => ({
                    ...theme,
                    colors: {
                      ...theme.colors,
                      primary25: "rgb(235, 235, 235)",
                      primary: "rgb(150, 150, 150)",
                    },
                  })}
                ></Select>
              </div>
              <div className="study-create-form-title">
                <div className="study-create-form-info">스터디 공개 범위</div>
                <input
                  type="radio"
                  name="visibility"
                  className="study-create-radio"
                  value={1}
                  id="public"
                  onChange={onChange}
                />
                <label htmlFor="public">공개</label>
                <input
                  type="radio"
                  name="visibility"
                  className="study-create-radio"
                  value={0}
                  id="private"
                  onChange={onChange}
                />
                <label htmlFor="private">비공개</label>
              </div>
            </div>
            <div className="study-create-form-title">
              <div className="study-create-form-info">스터디 가입 요건</div>
              <Select
                onChange={(e) => {
                  onSelectChange(e, "threshold");
                }}
                options={tierSelectOption}
                theme={(theme) => ({
                  ...theme,
                  colors: {
                    ...theme.colors,
                    primary25: "rgb(235, 235, 235)",
                    primary: "rgb(150, 150, 150)",
                  },
                })}
              ></Select>
            </div>
            <div className="study-create-form-title">
              <div className="study-create-form-info">스터디 설명</div>
              <textarea
                placeholder="스터디 설명과 카카오톡 오픈채팅방을 입력해주세요!"
                name="introduction"
                value={newStudy.introduction}
                onChange={onChange}
                className="study-create-textarea"
                wrap="hard"
                cols={20}
                rows={2}
              ></textarea>
            </div>
            <div className="study-create-submit-btn">
              <Button greenLine large onClick={createNewStudy}>
                스터디 생성하기
              </Button>
            </div>
          </form>
        </section>
        <section className="study-create-page-right">
          <div className="study-create-page-right-title">
            ✨Welcome to Aldy✨
            <div className="study-create-page-right-text">
              Aldy와 함께 알고리즘 스터디를 키워보세요!
            </div>
          </div>

          <img
            src={process.env.PUBLIC_URL + "/signup_dinosaur.png"}
            alt=""
          ></img>
        </section>
      </div>
    </main>
  );
};

export default StudyCreate;
