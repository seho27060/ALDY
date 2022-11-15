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

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class RequestedCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isDone;

    private Boolean isChecked;

    @CreatedDate
    private LocalDateTime requestDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @OneToOne
    @JoinColumn(name = "code_id")
    private Code code;

    @PrePersist
    public void prePersist() {
        this.isChecked = false;
        this.isDone = false;
    }

    public void replyCheck(){
        this.isDone = true;
    }

    public void batchCheck() {
        this.isChecked = true;
    }
}
