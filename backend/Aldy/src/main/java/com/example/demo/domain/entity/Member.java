package com.example.demo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String backjoonId;

    private String password;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<MemberInStudy> studyList;

    @OneToMany(mappedBy = "sender",cascade = CascadeType.ALL)
    private List<EditedCode> sendEditedCodeList;
    @OneToMany(mappedBy = "receiver",cascade = CascadeType.ALL)
    private List<EditedCode> receiveEditedCodeList;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<RequestedCode> seedRequestedCodeList;
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<RequestedCode> receiveRequestedCodeList;
}
