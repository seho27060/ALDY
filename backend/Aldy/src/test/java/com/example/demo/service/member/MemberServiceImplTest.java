package com.example.demo.service.member;

import com.example.demo.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doReturn;

// 가짜 객체 도입
// 단위 테스트시에 연결된 repository 등의 연결을 끊어야 함. 안그럼 통합테스트가 되버림
@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @InjectMocks
    private MemberServiceImpl memberService;

    @Mock
    private MemberRepository memberRepository;


    // 테스트 과정
    // given
    // 사전에 설정한 상황에서
    // when
    // 실제 테스트가 진행될때
    // then
    // 특정 결과를 기대한다.
    @Test
    void withdrawal() {
    }

    @Test
    void findMember() {
//        doReturn();
    }

    @Test
    void modifyInfo() {
    }

    @Test
    void modifyPassword() {
    }
}