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

// 첨삭받은 코드 리스트 조회
export const getEditedCodes = (studyId, problemId) => {
    return api.get(`/code/getEditedCodes/${studyId}/${problemId}`)
}

// 내게 온 요청 조회
export const getRequestedCode = (page, size) => {
    return api.get('/code/my-requested-codes', {
        params: {
            page: page,
            size: size,
        }
    })
}

// 내가 보낸 요청 조회
export const getRequestingCode = (page, size) => {
    return api.get('/code/my-requesting-codes', {
        params: {
            page:page,
            size:size,
        }
    })
}

// 첨삭 받은 코드 리스트 조회
export const getEditedCode = (page, size) => {
    return api.get('/code/my-edited-codes', {
        params: {
            page: page,
            size:size,
        }
    })
}

// 최종코드 리스트 조회
export const getFinalCode = (page, size) => {
    return api.get('/code/my-final-codes', {
        params: {
            page:page,
            size:size,
        }
    })
}
