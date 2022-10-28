package com.example.demo.service;

import com.example.demo.domain.dto.ProblemDto;
import com.example.demo.domain.entity.Problem;
import com.example.demo.repository.ProblemRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SolvedacServiceImpl implements SolvedacService {

    private final WebClient webClient;

    private final ProblemRedisRepository problemRedisRepository;

    @Override
    public List<ProblemDto> filter(String query) {

        final int[] count = new int[1];
        List<ProblemDto> problemDtoList = new ArrayList<>();
        do {
            // WebClient로 읽어오기
            Flux<String> flux = webClient.get()
                    .uri(uriBuilder ->
                            uriBuilder.path("/search/problem")
                                    .queryParam("query", query)
                                    .queryParam("page", problemDtoList.size() / 50 + 1)
                                    .build())
                    .acceptCharset(Charset.forName("UTF-8"))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToFlux(String.class);

            // parsing
            List<List<String>> stringSplit = new ArrayList<>();

            List<String> fluxToString = flux.collectList().block();
            List<String> strings = Arrays.asList(fluxToString.get(0).split("problemId"));

            strings.forEach(s -> {
                List<String> stringList = Arrays.asList(s.split(",\""));

                stringSplit.add(stringList);
            });

            stringSplit.forEach(s -> {
                if(s.size() > 2) {
                    int num = Integer.parseInt(s.get(0).split(":")[1]);

                    String name = s.get(4).split(":")[1];
                    name = name.replaceAll("\"", "");

                    int acceptedUserCount;
                    try {
                        acceptedUserCount = Integer.parseInt(s.get(8).split(":")[1]);
                    } catch (NumberFormatException e) {
                        acceptedUserCount = Integer.parseInt(s.get(11).split(":")[1]);
                    }

                    problemDtoList.add(new ProblemDto(num, name, acceptedUserCount));
                } else {
                    String tmpCnt = s.get(0).replaceAll("[^0-9]", "");
                    try {
                        count[0] = Integer.parseInt(tmpCnt);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            });

        } while(problemDtoList.size() <= count[0]);

        Collections.sort(problemDtoList);

        problemDtoList.forEach(p -> {
            problemRedisRepository.save(new Problem(p));
        });

        System.out.println(problemRedisRepository.count());

        return problemDtoList;
    }
}
