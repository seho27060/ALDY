package com.example.demo.domain.entity.Code;

import com.example.demo.domain.entity.Member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @OneToOne
    @JoinColumn(name = "code_id")
    private Code code;

    @CreatedDate
    private LocalDateTime editedDate;

    @Column(length = 10000)
    private String text;

    public void changeCode(String text){
        this.text = text;
    }
}
