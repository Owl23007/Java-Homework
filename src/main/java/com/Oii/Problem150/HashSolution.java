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

    //25.11.06 - 1 快乐数 leetcode - 202
    public boolean isHappy(int n) {
        int slow = n;
        int fast = n;
        while (true) {
            slow = findSquare(slow);
            fast = findSquare(findSquare(fast));
            if (slow == fast) {
                return slow == 1;
            }
            if (slow == 1 || fast == 1) {
                return true;
            }
            if (slow == 4 || fast == 4) {
                return false;
            }
        }

    }
    public int findSquare(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }

    // 25.11.06 - 2 存在重复元素 II leetcode - 219
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i]) && i - map.get(nums[i]) <= k) {
                return true;
            }
            map.put(nums[i], i);
        }
        return false;
    }

    public boolean containsNearbyDuplicate2(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (set.size()>= k){
                set.remove(nums[i - k - 1]);
            }
            if (set.contains(nums[i])) {
                return true;
            }
            set.add(nums[i]);
        }
        return false;
    }

    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;

        Set<Integer> set = new TreeSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int res = 1; // 至少有一个数时，最小长度为1
        int count = 1; // 当前序列长度从1开始

        Integer last = null;
        for (int num : set) {
            if (last == null) {
                last = num;
            } else if (num == last + 1) {
                count++;
            } else {
                res = Math.max(res, count);
                count = 1; // 重新开始计数
            }
            last = num;
        }
        res = Math.max(res, count);
        return res;
    }
}
