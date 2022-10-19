package com.example.demo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class RequestedCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "member_id",name = "sender_id", nullable = false)
    private Member sender;

    @ManyToOne
    @JoinColumn(referencedColumnName = "member_id",name = "receiver_id", nullable = false)
    private Member receiver;

    @OneToOne
    @JoinColumn(name = "code_id")
    private Code code;
}
