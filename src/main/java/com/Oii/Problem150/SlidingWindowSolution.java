package com.Oii.Problem150;

import java.util.HashMap;
import java.util.HashSet;

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
                    ans = Math.min(ans, j-i+1);
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
}
