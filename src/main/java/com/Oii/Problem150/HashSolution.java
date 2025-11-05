package com.Oii.Problem150;

import java.util.*;

public class HashSolution {

    // 25.11.04 - 1 赎金信 leetcode - 383
    public boolean canConstruct(String ransomNote, String magazine) {
        HashMap<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < ransomNote.length(); i++) {
            char c = ransomNote.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < magazine.length(); i++) {
            char c = magazine.charAt(i);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) - 1);
                if (map.get(c) <= 0) {
                    map.remove(c);
                }
            }
        }

        return map.isEmpty();
    }

    // 方法2 用数组代替，更快
    public boolean canConstruct2(String ransomNote, String magazine) {
        int[] map = new int[26];

        for (int i = 0; i < magazine.length(); i++) {
            map[magazine.charAt(i) - 'a']++;
        }

        for (int i = 0; i < ransomNote.length(); i++) {
            int index = ransomNote.charAt(i) - 'a';
            if (map[index] == 0) {
                return false;
            }
            map[index]--;
        }
        return true;
    }

    // 25.11.04 - 2 同构字符串 leetcode - 205
    public boolean isIsomorphic(String s, String t) {
        HashMap<Character, Character> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);
            if (map.containsKey(c1)) {
                if (map.get(c1) != c2) {
                    return false;
                }
            } else {
                if (map.containsValue(c2)) { // 值重复, a -> b, c -> b,所以这里检查是否值重复
                    return false;
                }
                map.put(c1, c2);
            }
        }
        return true;

    }

    // 25.11.04 - 3 单词规律 leetcode - 290
    public boolean wordPattern(String pattern, String s) {
        HashMap<Character, String> map = new HashMap<>();

        String[] words = s.split(" ");
        if (words.length != pattern.length()) return false;

        int i = 0;
        for (String word : words) {
            if (map.containsKey(pattern.charAt(i))) {
                if (!map.get(pattern.charAt(i)).equals(word)) {
                    return false;
                }
            } else {
                if (map.containsValue(word)) {
                    return false;
                }
                map.put(pattern.charAt(i), word);
            }
            i++;
        }
        return true;
    }

    // 25.11.05  - 1 有效的字母异位词 leetcode - 242
    public boolean isAnagram(String s, String t) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0) {
                    map.remove(c);
                }
            } else {
                return false;
            }
        }

        return map.isEmpty();
    }


    // 25.11.05 - 2 字母异位词分组 leetcode - 49
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }
        return new ArrayList<>(map.values());
    }

    // 25.11.05 - 3 两数之和 leetcode - 1
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> set = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (set.containsKey(complement)) {
                return new int[]{set.get(complement), i};
            }
            set.put(nums[i], i);
        }
        return new int[0];
    }

}
