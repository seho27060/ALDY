package com.example.demo.domain.entity;

import com.example.demo.domain.dto.ProblemDto;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@RedisHash(value = "problem", timeToLive = 30)
public class Problem {

    @Id
    private int id;

    private int num;

    private String name;

    private int acceptedUserCount;

    public Problem(ProblemDto problemDto) {
        this.num = problemDto.getNum();
        this.name = problemDto.getName();
        this.acceptedUserCount = problemDto.getAcceptedUserCount();
    }
}
