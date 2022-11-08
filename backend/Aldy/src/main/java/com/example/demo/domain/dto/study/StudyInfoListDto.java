package com.example.demo.domain.dto.study;

import com.example.demo.service.solvedac.AlgoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyInfoListDto {

    HashMap<String, String> memberHash;

    HashMap<AlgoEnum, String> algoHash;

}
