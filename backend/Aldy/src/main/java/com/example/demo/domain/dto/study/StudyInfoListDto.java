package com.example.demo.domain.dto.study;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyInfoListDto {

    List<String> memberBaekjoonIdList;

    List<String> algoList;

}
