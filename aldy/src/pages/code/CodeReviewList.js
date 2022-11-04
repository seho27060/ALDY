import './CodeReviewList.css';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { getReviewList } from '../../api/code'
import { useRecoilState } from "recoil";
import { correctCode, recoilMyCode, recoilStep } from "../../store/states";

const CodeReviewList = () => {
  const [tab, setTab] = useState('requestToMe')
  return (
    <main>
      <section className='main-banner'>
        <img className='main-img' src="/studyIcon.png" alt="코드리뷰 메인 이미지"></img>
        <p>
          <span>다른 사람들과 함께</span>
          <span className='highlight'>코드리뷰</span>
          <span>를 하며</span>
          <span className='highlight'>공룡</span>
          <span>을 키워볼 기회</span>
        </p>
        <h2 className='underline-orange'>
          지금 바로 코드리뷰를 해보세요!
        </h2>
      </section>
      <section className='description'>
        <div className='description-detail'>
          <img src="/CodeReviewIcon.png" alt="코드리뷰 이미지"></img>
          <p>다른 사람에게서</p>
          <h2 className='underline-green'>내게 요청 온 목록</h2>
        </div>
        <div className='description-detail'>
          <img src="/book.png" alt="공책 이미지"></img>
          <p>스터디원들에게</p>
          <h2 className='underline-green'>리뷰 받은 코드</h2>
        </div>
        <div className='description-detail'>
          <img src="/pencil.png" alt="연필 이미지"></img>
          <p>스터디 문제를 푼 후</p>
          <h2 className='underline-green'>내가 보낸 요청</h2>
        </div>
      </section>
      <section className='request-list'>
        <div>
          <button onClick={()=>{
            setTab("requestToMe")
          }}
          className={`tabBtn ${tab === 'requestToMe' ? 'active': ""}`}>내게 온 요청 목록</button>
          <button onClick={()=>{
            setTab("reviewedCode")
          }}
          className={`tabBtn ${tab === 'reviewedCode' ? 'active': ""}`}>리뷰 받은 코드</button>
          <button onClick={()=>{
            setTab("requestByMe")
          }}
          className={`tabBtn ${tab === 'requestByMe' ? 'active': ""}`}>내가 보낸 요청</button>
        </div>
        <div>
        <Container className='review-list-header'>
          <Row>
            <Col>스터디 이름</Col>
            <Col>{ tab === 'requestToMe' ? "요청한 사람" : "리뷰한 사람" }</Col>
            <Col>문제 번호</Col>
            <Col>문제 이름</Col>
            <Col>날짜</Col>
            <Col>진행 상황</Col>
          </Row>
        </Container>

        {
          tab === 'requestToMe' && <RequestToMe />
        }
        {
          tab === 'requestByMe' && <RequestByMe />
        }
        {
          tab === 'reviewedCode' && <ReviewedCode />
        }
        </div>
      </section>
    </main>
  )
};

// 내가 요청 받은 코드
const RequestToMe = () => {
  const [list, setList] = useState(null);
  useEffect(()=>{
    // 서버에서 내게 요청온 목록 가져와서 list에 저장하기
    getReviewList()
    .then((res)=>{
      setList(res.data.requestedCodeList)
      console.log(res)
    }).catch((err)=>{
      console.log(err)
      alert('코드 리스트를 불러올 수 없습니다.')
    })
  }, [])
  return (
    <div>
      {
        list?.map((item) => <CardRequestToMe item={item}/>)
      }
    </div>
  )
}
// 내가 요청한 코드
const RequestByMe = () => {
  const [list, setList] = useState(null)
  useEffect(()=>{
    getReviewList()
    .then((res)=>{
      setList(res.data.requestingCodeList)
    }).catch((err)=>{
      console.log(err)
      alert('코드 리스트를 불러올 수 없습니다.')
    })
  }, [])
  return (
    <div>
      {
        list?.map((item) => <CardRequestByMe item={item}/>)
      }
    </div>
  )
}
// 내가 리뷰 받은 코드
const ReviewedCode = () => {
  const [list, setList] = useState(null)
  useEffect(()=>{
    getReviewList()
    .then((res)=>{
      setList(res.data.editedCodeList)
      console.log('리뷰받은', res.data.editedCodeList)
    }).catch((err)=>{
      console.log(err)
      alert('코드 리스트를 불러올 수 없습니다.')
    })
  }, [])
  return (
    <div>
      {
        list?.map((item) => <CardReviewdCode item={item}/>)
      }
    </div>
  )
}
// 리뷰 요청받은 코드 카드 컴포넌트
const CardRequestToMe = (props) => {
  const item = props.item;
  const [code, setCode] = useRecoilState(correctCode)
  console.log(item)
  const navigate = useNavigate();
  return (
    <Container className='review-list-item'>
      <Row>
        <Col>{item.codeDto.studyDto.name}</Col>
        <Col>{item.sender.baekjoonId}</Col>
        <Col>{item.codeDto.problemId}</Col>
        <Col>{item.codeDto.problemName}</Col>
        <Col>{item.codeDto.createdDate.substring(0,10)}</Col>
        <Col>{item.done ? "완료" : <button className='correctBtn' onClick={()=>{
            // 코드 첨삭 페이지로 이동
            sessionStorage.setItem('correctCode', item.codeDto.code)
            setCode(item.codeDto.code)
            navigate('/correct')
        }}>코드 첨삭하기</button>}</Col>
      </Row>
    </Container>
  )
}
// 요청 한 코드 카드 컴포넌트
const CardRequestByMe = (props) => {
  const item = props.item;
  const navigate = useNavigate();
  return (
    <Container className='review-list-item'>
      <Row>
        <Col>{item.codeDto.studyDto.name}</Col>
        <Col>{item.receiver.baekjoonId}</Col>
        <Col>{item.codeDto.problemId}</Col>
        <Col>{item.codeDto.problemName}</Col>
        <Col>{item.codeDto.createdDate.substring(0,10)}</Col>
        <Col>{item.done ? "완료" : "미완료"}</Col>
      </Row>
    </Container>
  )
}
// 리뷰받은 코드 카드 컴포넌트
const CardReviewdCode = (props) => {
  const item = props.item;
  const [process, setProcess] = useRecoilState(recoilStep)
  const [myCode, setMyCode] = useRecoilState(recoilMyCode)
  const navigate = useNavigate();
  return (
    <Container className='review-list-item'>
      <Row>
        <Col>{item.codeDto.studyDto.name}</Col>
        <Col>{item.sender.baekjoonId}</Col>
        <Col>{item.codeDto.problemId}</Col>
        <Col>{item.codeDto.problemName}</Col>
        <Col>{item.codeDto.createdDate.substring(0,10)}</Col>
        <Col><button className='correctBtn' onClick={()=>{
          navigate('/review')
          setProcess(4)
          setMyCode(item.codeDto.code)
          sessionStorage.setItem('problemId', item.codeDto.problemId)
          sessionStorage.setItem('studyId', item.codeDto.studyDto.id)
          sessionStorage.setItem('editedCode', item.editedCode)
        }}>코드리뷰 4단계</button></Col>
      </Row>
    </Container>
  ) 
}

export default CodeReviewList;
