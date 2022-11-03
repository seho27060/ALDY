import { api } from './api'

// 회원정보 조회
export const getUserInfo = (baekjoonId) => {
    return api.get(`/member/find/${baekjoonId}`)
}

// 회원정보 수정
export const updateUserInfo = (data) => {
    return api.put('/member/info', data)
}

// 회원 비밀번호 수정
export const changePW = () => {
    return api.put('/member/password')
}

// 회원탈퇴
export const withdraw = (data) => {
    return api.post('/member/withdrawal', data)
}