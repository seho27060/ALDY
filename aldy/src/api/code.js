import { api } from './api'

// 코드 반환
export const getCode = (studyId, problemId) => {
    return api.get(`/code/${studyId}/${problemId}`)
}

// 코드 저장
export const saveCode = (data) => {
    return api.post('/code/process', data)
}

// 코드 리뷰 첨삭답장 보내기
export const codeReply = (data) => {
    return api.post('/code/reply', data)
}

//코드 리뷰 요청
export const reviewRequest = (data) => {
    return api.post('/code/request', data)
}

// 코드 리뷰 요청 리스트 조회
export const getReviewList = () => {
    return api.get('/code/review-page')
}