package com.Oii.Problem150;

import java.util.*;

public class BackTraceProblem {
    // 25.12.05 1 - 电话号码的字母组合 leetcode - 17
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits == null || digits.isEmpty()) {
            return res; // 空列表
        }
        Map<Character, List<Character>> map = Map.of(
                '2', List.of('a', 'b', 'c'),
                '3', List.of('d', 'e', 'f'),
                '4', List.of('g', 'h', 'i'),
                '5', List.of('j', 'k', 'l'),
                '6', List.of('m', 'n', 'o'),
                '7', List.of('p', 'q', 'r', 's'),
                '8', List.of('t', 'u', 'v'),
                '9', List.of('w', 'x', 'y', 'z')
        );
        backTrace(digits, 0, map, new StringBuilder(), res);

        return res;
    }

    void backTrace(String digits, int index, Map<Character, List<Character>> map, StringBuilder path, List<String> res) {
        if (index == digits.length()) {
            res.add(path.toString());
            return;
        }
        List<Character> list = map.get(digits.charAt(index));
        for (Character c : list) {
            path.append(c);
            backTrace(digits, index + 1, map, path, res);
            path.deleteCharAt(path.length() - 1);
        }
    }

    // 25.12.05 2 - 组合 leetcode - 77

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();

        if (k == 1) {
            for (int i = 1; i <= n; i++) {
                res.add(List.of(i));
            }
            return res;
        }

        backtrack(n, k, 1, new ArrayList<>(), res);
        return res;
    }

    private void backtrack(int n, int k, int start, List<Integer> path, List<List<Integer>> res) {
        if (path.size() == k) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i <= n; i++) {
            path.add(i);
            backtrack(n, k, i + 1, path, res);
            path.removeLast();
        }
    }
}
