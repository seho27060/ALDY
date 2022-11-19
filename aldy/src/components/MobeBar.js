import "./MoveBar.css";

const MoveBar = () => {
  const moveService = () => {
    // 서비스 설명 위치로 이동
    const top = document.querySelector(".main-page-description").offsetTop;
    window.scrollTo({ top: top, behavior: "smooth" });
  };
  const moveCharacter = () => {
    // 캐릭터 설명 위치로 이동
    const top = document.querySelector(".aldy_step_info").offsetTop;
    window.scrollTo({ top: top, behavior: "smooth" });
  };

  const moveStudy = () => {
    // 스터디 설명 위치로 이동
    const top = document.querySelector(
      ".main-page-study-description"
    ).offsetTop;
    window.scrollTo({ top: top, behavior: "smooth" });
  };

  const moveCodeReview = () => {
    // 코드리뷰 설명 위치로 이동
    const top = document.querySelector(
      ".main-page-description-codereview"
    ).offsetTop;
    window.scrollTo({ top: top, behavior: "smooth" });
  };

  const moveTutorial = () => {
    // 이용 방법 설명 위치로 이동
    const top = document.querySelector(
      ".main-page-tutorial-description"
    ).offsetTop;
    window.scrollTo({ top: top, behavior: "smooth" });
  };

  return (
    <div className="move-bar">
      <button className="move-bar-btn" onClick={moveService}>
        서비스 설명
      </button>
      <button className="move-bar-btn" onClick={moveCharacter}>
        캐릭터 설명
      </button>
      <button className="move-bar-btn" onClick={moveStudy}>
        스터디 설명
      </button>
      <button className="move-bar-btn" onClick={moveCodeReview}>
        코드리뷰 설명
      </button>
      <button className="move-bar-btn" onClick={moveTutorial}>
        이용 방법
      </button>
    </div>
  );
};

export default MoveBar;
