package com.Oii;

import java.util.*;

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

    //25.10.15-1 旋转数组 - leetcode-189
    public void rotate(int[] nums, int k) {
        k = k % nums.length;
        int[] clone = nums.clone();
        for (int i = 0; i < nums.length; i++) {
            nums[(i + k) % nums.length] = clone[i];
        }
    }

    // 25.10.16-1 买卖股票的最佳时机 - leetcode-121
    public int maxProfit(int[] prices) {
        int[] list = new int[prices.length]; // 存储当前位置卖出股票能得到的最大利润
        int max = 0;
        int min = prices[0];
        for (int i = 1; i < prices.length; i++) {
            list[i] = Math.max(list[i - 1], prices[i] - min); // 当前位置卖出股票能得到的利润
            min = Math.min(min, prices[i]);
            max = Math.max(max, list[i]);
        }
        return max;

    }

    // 25.10.16-2 买卖股票的最佳时机 II - leetcode-122

    // 解法1
    public int maxProfit2(int[] prices) {
        int n = prices.length;

        int[][] dp = new int[n][2];
        dp[0][0] = 0;              // 第 i 天不持有股票
        dp[0][1] = -prices[0];     // 第 i 天持有股票

        for (int i = 1; i < n; i++) {
            // 第 i 天不持有股票 = [前一天不持有股票] 和 [前一天持有股票 + (卖出)当前价格]  的最大值
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            // 第 i 天持有股票 = [前一天持有股票] 和 [前一天不持有股票 -(买入)当前价格] 的最大值
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }

        return dp[n - 1][0];
    }

    // 解法2
    public int maxProfit3(int[] prices) {
        int[] list = new int[prices.length];
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            list[i] = prices[i] - prices[i - 1]; // 当前位置卖出股票能得到的利润
            if (list[i] > 0) {
                // 如果当前位置卖出股票能得到的利润大于0，则加上
                max += list[i];
            }
        }
        return max;
    }

    // 25.10.17-1 跳跃游戏 - leetcode-55
    public boolean canJump(int[] nums) {
        int step = nums.length - 1;

        int max = nums[0]; // 当前位置能跳的最大距离
        for (int i = 0; i <= max; i++) { // 遍历当前位置能跳的最大距离
            if (max >= step) return true; // 当前位置能跳的最大距离 >= 最后一个位置，则返回true
            int newMax = i + nums[i];
            if (newMax > max) {
                max = newMax;
            }
        }
        return max >= step;
    }

    // 25.10.17-2 跳跃游戏 II - leetcode-45
    public int jump(int[] nums) {
        if (nums.length <= 1) return 0;
        int len = nums.length - 1;
        int step = 0; // 当前位置跳的步数
        int end = 0; // 当前位置能跳的最大距离

        int nextEnd = 0;
        for (int i = 0; i < len; i++) {
            nextEnd = Math.max(nextEnd, i + nums[i]); // 更新当前位置能跳的最大距离
            if (i == end) {
                // 到达当前位置能跳的最大距离，则说明当前位置跳的步数加1
                // 当前位置跳的步数加1，并更新当前位置能跳的最大距离
                step++;
                end = nextEnd;
            }
        }
        return step;

    }

    // 25.10.18-1 h指数 - leetcode-274
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        int n = citations.length;
        for (int i = 0; i < n; i++) {
            int h = n - i; // 大于h次数的引用的篇数
            if (citations[i] >= h) { // 如果当前位置的引用次数数大于篇数
                return h;
            }
        }
        return 0;
    }

    // 25.10.20-1 除自己的乘积 - leetcode-238
    public int[] productExceptSelf(int[] nums) {
        int[]ans = new int[nums.length];
        Arrays.fill(ans, 1);

        int left = 1;
        int right = 1;
        for (int i = 0; i < nums.length; i++) {
            ans[i] = ans[i] * left;
            ans[nums.length - 1 - i] = ans[nums.length - 1 - i] * right;
            left *= nums[i];   // 当前位置的左边的乘积
            right *= nums[nums.length - 1 - i]; // 当前位置的右边的乘积
        }

        return ans;
    }

    // 25.10.20-2 加油站 - leetcode-134
    // 关键点在于 如果总油量大于总消耗，则一定存在一个位置，使得从该位置开始，可以完成环路
    // 所以只用遍历一遍，找到一个位置，使得从该位置开始，可以到达最后一个位置
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int totalGas = 0;
        int totalCost = 0;
        
        // 计算总油量和总消耗
        for (int i = 0; i < gas.length; i++) {
            totalGas += gas[i];
            totalCost += cost[i];
        }
        
        // 如果总油量小于总消耗，无法完成环路
        if (totalGas < totalCost) {
            return -1;
        }
        
        int currentGas = 0;
        int start = 0;
        
        // 寻找合适的起点
        for (int i = 0; i < gas.length; i++) {
            currentGas += gas[i] - cost[i];
            
            // 如果当前油量为负，说明无法从当前起点到达下一个站点
            if (currentGas < 0) {
                // 将起点设为下一个站点
                start = i + 1;
                currentGas = 0;
            }
        }
        
        return start;
    }
}


// 25.10.18-2 随机数集合 - leetcode-380
class RandomizedSet {
    private final List<Integer> list;
    private final Map<Integer, Integer> map;
    private final Random random;

    public RandomizedSet() {
        list = new ArrayList<>();
        map = new HashMap<>();
        random = new Random();
    }

    public boolean insert(int val) {
        if (map.containsKey(val)) return false;
        map.put(val, list.size());
        list.add(val);
        return true;
    }

    public boolean remove(int val) {
        if (!map.containsKey(val)) return false;

        int index = map.get(val);
        int lastElement = list.getLast();

        // 将最后一个元素移到被删除的位置
        list.set(index, lastElement);
        map.put(lastElement, index);

        // 删除最后一个元素
        list.removeLast();
        map.remove(val);
        return true;
    }

    public int getRandom() {
        return list.get(random.nextInt(list.size()));
    }
}
