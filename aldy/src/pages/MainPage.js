const MainPage = () => {
  return (
    <main>
      <section>
        <img src={process.env.PUBLIC_URL + "/MainDinosaur.png"} alt=""></img>
        <div>코드리뷰를 통해 공룡 키우기</div>
        <div> ALDY</div>
        <div>
          <div>✨ 스터디 레벨에 따라 성장하는 공룡 ✨</div>
          <div>스터디원들과 함께 공룡을 키워봐요!</div>
        </div>
        <button>스터디 생성하기</button>
      </section>
      <section>
        <div>ALDY만의 코드리뷰 이용하기</div>
        <div>
          <div>문제풀이 과정을 4단계로 세분화!</div>
          <button>회원가입 하러가기</button>
        </div>
      </section>
    </main>
  );
};

export default MainPage;
