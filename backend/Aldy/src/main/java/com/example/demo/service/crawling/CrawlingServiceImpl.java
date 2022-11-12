package com.example.demo.service.crawling;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class CrawlingServiceImpl {

    
    public ArrayList<Integer> getRecentProblems(String backjoonId) throws IOException {
        ArrayList<Integer> problemList = new ArrayList<>();
        String url = "https://www.acmicpc.net/status?problem_id=&user_id=%s&language_id=-1&result_id=4";
        url = String.format(url, backjoonId);
        Connection.Response rs = Jsoup.connect(url)
                .method(Connection.Method.GET)
                .execute();
        Document document = rs.parse();
        Elements table = document.selectXpath("//*[@id=\"status-table\"]/tbody");
        Element list = table.get(0);
        for(Element data : list.children()){
            problemList.add(Integer.parseInt((data.child(2).wholeText())));
        }
        return problemList;
    }

}
