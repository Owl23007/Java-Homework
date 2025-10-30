package com.Oii.Problem150;

import java.util.*;

public class SlidingWindowSolution {
    // 25.10.29 -1 长度最小的子数组 - leetcode-209
    //  方法一 : 暴力解法 O(n^2) 超时
    public int minSubArrayLen(int target, int[] nums) {
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            int j = i;
            while (j < nums.length) {
                sum += nums[j];
                if (sum >= target) {
                    ans = Math.min(ans, j - i + 1);
                    break;
                }
                j++;
            }
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }

    // 方法二 滑动窗口 O(n)
    public int minSubArrayLen_2(int target, int[] nums) {
        int left = 0, sum = 0, ans = Integer.MAX_VALUE;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= target) {
                ans = Math.min(ans, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }

    // 25.10.29 -2 最长无重复子串 - leetcode-3
    public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> map = new HashMap<>();

        int left = 0, right = 0, ans = 0;
        for (; right < s.length(); right++) {
            char c = s.charAt(right);
            if (map.containsKey(c)) {
                left = Math.max(left, map.get(c) + 1); // 更新 left 到上一个c的右边， 注意如果left > map.get(c)，则说明当前c之前没有重复的，则left不需要更新
            }
            map.put(c, right);
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }

    // 25.10.30 -1 串联所有单词的子串 - leetcode - 30
    public List<Integer> findSubstring(String s, String[] words) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        List<Integer> ans = new ArrayList<>(); // 存放结果

        int len = words[0].length(); // 单词长度
        int count = words.length; // 单词数量

        for (int i = 0; i < len; i++) {
            HashMap<String, Integer> temp = new HashMap<>();

            int left = i, right = i;
            for (; right < s.length(); right += len) {
                if (right + len > s.length()) { // 剩余的字符串长度不足一个单词
                    break;
                }
                String word = s.substring(right, right + len); // 下一个单词
                if (!map.containsKey(word)) { // 当前单词不在words中
                    left = right + len; // 跳过当前单词
                    temp.clear();
                    continue;
                }

                temp.put(word, temp.getOrDefault(word, 0) + 1); // 统计单词出现的次数
                if (temp.get(word) > map.getOrDefault(word, 0)) {// 当前单词出现的次数超过words中单词出现的次数

                    while (temp.get(word) > map.getOrDefault(word, 0)) { // 移除左边单词

                        String leftWord = s.substring(left, left + len);
                        temp.put(leftWord, temp.get(leftWord) - 1);
                        left += len;
                    }
                }
                if ((right - left) / len + 1 == count) {
                    ans.add(left);
                }
            }
            temp.clear();
        }

        return ans;
    }

    // 25.10.30 -2 最小覆盖子串 - leetcode - 76

    public String minWindow(String s, String t) {
        String ans = "";
        int left = 0, right = 0;
        HashMap<Character, Integer> map = new HashMap<>(); // 存放t中单词出现的次数
        int count = 0; // 单词数量

        for (char c : t.toCharArray()) { // 统计t中单词出现的次数
            map.put(c, map.getOrDefault(c, 0) + 1);
            count++;

        }

        HashMap<Character, Integer> temp = new HashMap<>();
        int tempCount = 0;
        for (; right < s.length(); right++) {

            char c = s.charAt(right);

            if (map.containsKey(c)) {
                temp.put(c, temp.getOrDefault(c, 0) + 1);
                if (temp.get(c) <= map.get(c)) {
                    tempCount++;
                }
            }

            while (tempCount == count) {
                if (ans.isEmpty() || right - left + 1 < ans.length()) { // 更新结果
                    ans = s.substring(left, right + 1);
                }
                char leftChar = s.charAt(left);
                left++;
                if (map.containsKey(leftChar)) { // 当前字符在t中
                    temp.put(leftChar, temp.get(leftChar) - 1); // 移除左边单词
                    if (temp.get(leftChar) < map.get(leftChar)) { // 当前单词出现的次数小于t中单词出现的次数
                        tempCount--;
                    }
                }
            }

        }
        return ans;
    }

    /* 同样的思路
     * 优化点 :
     * 1. 用 int[128] 数组代替 HashMap：直接通过字符 ASCII 值索引，避免哈希计算和装箱开销
     * 2. 只记录最优窗口的起始位置和长度，最后再 substring 一次，避免循环中反复创建子串
     * 3. 用“满足条件的字符种类数”判断窗口是否有效（而非总字符数），减少冗余操作
     */

    public String minWindow_2(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) return "";

        int[] need = new int[128]; // t 中每个字符需要的次数
        int[] window = new int[128]; // 当前窗口中各字符的出现次数

        int needCount = 0;
        for (char c : t.toCharArray()) {
            if (need[c] == 0) needCount++; // 统计 t 中有多少种不同字符
            need[c]++;
        }

        int left = 0, right = 0;
        int valid = 0; // 窗口中满足 need 要求的字符种类数

        int start = 0, minLen = Integer.MAX_VALUE;

        while (right < s.length()) {
            char c = s.charAt(right);
            right++;

            if (need[c] > 0) {
                window[c]++;
                if (window[c] == need[c]) {
                    valid++;
                }
            }

            // 尝试收缩窗口
            while (valid == needCount) {
                if (right - left < minLen) {
                    minLen = right - left;
                    start = left;
                }

                char d = s.charAt(left);
                left++;

                if (need[d] > 0) {
                    if (window[d] == need[d]) {
                        valid--;
                    }
                    window[d]--;
                }
            }
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }

}
