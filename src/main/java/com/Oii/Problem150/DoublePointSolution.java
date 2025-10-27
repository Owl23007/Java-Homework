package com.Oii.Problem150;

import java.util.Stack;

public class DoublePointSolution {
    // 25.10.27 -1 验证回文串 leetcode - 125
    public boolean isPalindrome(String s) {
        // 大写 to 小写 ，移除空格
        s = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) return false;
        }
        return true;
    }

    // 25.10.27 -2 判断子序列 leetcode - 392
        public boolean isSubsequence(String s, String t) {
            int i = 0;
            for (char c : t.toCharArray()) {
                if (i < s.length() && s.charAt(i) == c) i++;
            }
            return i == s.length();
        }
}
