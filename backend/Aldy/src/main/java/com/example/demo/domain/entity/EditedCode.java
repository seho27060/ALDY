package com.example.demo.domain.entity;

import com.example.demo.domain.entity.Member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

// orm, object relation mapping
// mapping 1:1 로 연관을 짓는걸
// object라는 것은 객체지향에서 객체를 말하는 것
// relation 데이터베이스에서의

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)

public class EditedCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    // @Column(name="sender")
    // @JoinColumn(name="sender")
    // 데이터베이스 조건에서 외래키의 조건은 그 해당키가 유니크하기만 하면 됨. 주키가 아니어도 됨.
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @OneToOne
    @JoinColumn(name = "code_id")
    private Code code;

}
