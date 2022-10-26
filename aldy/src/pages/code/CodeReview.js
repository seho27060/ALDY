import './CodeReview.css'
import { useState } from 'react';

const CodeReview = () => {
  const [step, setStep] = useState(1)
  return (
    <main className='review-main'>
      <section className="review-header">
        <p>
          <span>다른 사람들과 함꼐</span>
          <span className='highlight'>코드리뷰</span>
          <span>를 하며</span>
          <span className='highlight'>공룡</span>
          <span>을 키워볼 기회</span>
        </p>
        <h2 className='review-orange'>이번 주 스터디 선정 문제</h2>
      </section>
      <section className="review-board">
        <div className="review-title">
          <p style={{'margin': '0 25px'}}>✨ 스터디이름: ssafy</p>
          <p style={{'margin': '0 25px'}}>3017번</p>
          <p style={{'margin': '0 25px'}}>가까운 수 찾기 ✨</p>
        </div>
        <div className="review-content">
          <div className="review-step">
            <p style={{'fontSize':'25px'}}>✨코드리뷰 단계✨</p>
            <button className={`review-step-btn ${step === 1 ? 'act':''}`} onClick={()=>{
              setStep(1)
            }}>1단계</button>
            <button className={`review-step-btn ${step === 2 ? 'act':''}`} onClick={()=>{
              setStep(2)
            }}>2단계</button>
            <button className={`review-step-btn ${step === 3 ? 'act':''}`} onClick={()=>{
              setStep(3)
            }}>3단계</button>
            <button className={`review-step-btn ${step === 4 ? 'act':''}`} onClick={()=>{
              setStep(4)
            }}>4단계</button>
          </div>
          <div className="review-code">
            코드영역
          </div>
        </div>
        <div className='review-btns'>
          <button className="reviewBtn">백준 연동</button>
          <button className="reviewBtn">코드 제출하기</button>
        </div>
      </section>
    </main>
  )
};

export default CodeReview;
