package com.example.demo.service.solvedac;

import lombok.Getter;

@Getter
public enum AlgoEnum {

    math("수학"),
    implementation("구현"),
    dp("다이나믹 프로그래밍"),
    data_structures("자료 구조"),
    graphs("그래프 이론"),
    string("문자열"),
    greedy("그리디 알고리즘"),
    bruteforcing("브루트포스 알고리즘"),
    graph_traversal("그래프 탐색"),
    sorting("정렬"),
    number_theory("정수론"),
    geometry("기하학"),
    segtree("세그먼트 트리"),
    trees("트리"),
    binary_search("이분 탐색"),
    bfs("너비 우선 탐색"),
    simulation("시뮬레이션"),
    dfs("깊이 우선 탐색"),
    prefix_sum("누적 합"),
    dijkstra("데이크스트라"),
    backtracking("백트래킹"),
    divide_and_conquer("분할 정복"),
    priority_queue("우선순위 큐"),
    stack("스택"),
    dp_tree("트리에서의 다이나믹 프로그래밍"),
    flow("최대 유량"),
    primality_test("소수 판정"),
    game_theory("게임 이론"),
    exponentiation_by_squaring("분할 정복을 이용한 거듭제곱"),
    recursion("재귀"),
    bipartite_matching("이분 매칭"),
    floyd_warshall("플로이드–워셜"),
    hashing("해싱");



    private final String ko;

    AlgoEnum(String ko) {
        this.ko = ko;
    }

    public String getKo() {
        return ko;
    }
}
