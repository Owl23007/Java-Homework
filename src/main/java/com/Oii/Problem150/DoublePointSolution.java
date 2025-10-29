package com.Oii.Problem150;

import java.util.*;

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

    //25.10.28 - 1 两数之和 leetcode - 167
    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum > target)
                right--;
            else if (sum < target)
                left++;
            else
                return new int[]{left + 1, right + 1};
        }

        return new int[]{};
    }


    // 25.10.28 - 2 盛最多水的容器 leetcode - 11
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int max = 0;
        while (left < right) {
            int h = Math.min(height[left], height[right]);
            max = Math.max(max, h * (right - left));

            if (height[left] < height[right]) {
                int cur = height[left];
                while (left < right && height[left] <= cur) left++; // 跳过所有 ≤ 当前左高的
            } else {
                int cur = height[right];
                while (left < right && height[right] <= cur) right--; // 跳过所有 ≤ 当前右高的
            }
        }
        return max;
    }

    // 25.10.28 - 3 三数之和 leetcode - 15
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            // 跳过重复的 i
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            // 剪枝 1：最小可能和 > 0 → 后面都更大，无解
            if (nums[i] + nums[i + 1] + nums[i + 2] > 0) break;

            // 剪枝 2：最大可能和 < 0 → 当前 i 太小，跳过
            if (nums[i] + nums[n - 2] + nums[n - 1] < 0) continue;

            int left = i + 1, right = n - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum < 0) {
                    left++;
                } else if (sum > 0) {
                    right--;
                } else {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // 跳过重复的 left 和 right
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;

                    // 移动到下一个不同值
                    left++;
                    right--;
                }
            }
        }
        return res;
    }


    // 在leetcode上发现的小 trick
    public List<List<Integer>> threeSum_2(int[] nums) {
        return new java.util.AbstractList<List<Integer>>() {
            private List<List<Integer>> res;

            @Override
            public List<Integer> get(int index) {
                init();
                return res.get(index);
            }

            @Override
            public int size() {
                init();
                return res.size();
            }

            private void init() {
                if (res != null) return; // 只计算一次
                res = new ArrayList<>();
                Arrays.sort(nums);
                int n = nums.length;

                for (int i = 0; i < n - 2; i++) {
                    if (i > 0 && nums[i] == nums[i - 1]) continue;
                    // 剪枝：最小和 > 0，直接 break
                    if (nums[i] + nums[i + 1] + nums[i + 2] > 0) break;
                    // 剪枝：最大和 < 0，跳过
                    if (nums[i] + nums[n - 1] + nums[n - 2] < 0) continue;

                    int left = i + 1, right = n - 1;
                    while (left < right) {
                        int sum = nums[i] + nums[left] + nums[right];
                        if (sum < 0) {
                            left++;
                        } else if (sum > 0) {
                            right--;
                        } else {
                            res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                            while (left < right && nums[left] == nums[left + 1]) left++;
                            while (left < right && nums[right] == nums[right - 1]) right--;
                            left++;
                            right--;
                        }
                    }
                }
            }
        };
    }

}
