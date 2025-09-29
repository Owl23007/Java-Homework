package com.Oii;

public class Problem150 {

    // 25.09.29-1 删除重复元素 - leetcode-26
    public int removeDuplicates(int[] nums) {
        int left = 0, right = 0;
        while (right < nums.length - 1) {
            right++;
            if (nums[left] != nums[right]) {
                left++;
                nums[left] = nums[right];
            }
        }
        return left + 1;
    }

    // 25.09.29-2 删除排序数组中的重复项2 - leetcode-80
    public int removeDuplicates_1(int[] nums) {
        int slow = 2; // 前两个元素直接保留
        for (int fast = 2; fast < nums.length; fast++) {
            // 如果当前元素与 slow-2 位置的元素不同，说明可以保留
            if (nums[fast] != nums[slow - 2]) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        return slow;
    }

    // 25.09.29-3 众数 - leetcode-169
    public int majorityElement(int[] nums) {
        int count = 0, candidate = 0;
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
                count = 1;
            } else if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }
        return candidate;
    }
}
