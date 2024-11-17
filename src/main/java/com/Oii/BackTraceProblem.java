package com.Oii;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackTraceProblem {

    //leetcode-39 组合总和
    List<List<Integer>> res39 = new ArrayList<>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        backTrace39(target,new ArrayList<>(),candidates,0);
        return res39;
    }
    public void backTrace39(int target, List<Integer> temp, int[] candidates, int flag) {
        int sum = 0;
        for (int it : temp) {
            sum += it;
        }

        if (sum == target) {
            res39.add(new ArrayList<>(temp));
            return;
        }
        if (sum > target) {
            return;
        }
        for (int i = flag; i < candidates.length; i++) {
            temp.add(candidates[i]);
            backTrace39(target, temp, candidates, i);
            temp.remove(temp.size() - 1); // backtrack
        }
    }

    //leetcode-40 组合总和 II
    List<List<Integer>> res40 = new ArrayList<>();
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        backTrace40(target,new ArrayList<>(),candidates,0);
        return res40;
    }
    public void backTrace40(int target, List<Integer> temp, int[] candidates, int flag) {
        int sum = 0;
        for (int it : temp) {
            sum += it;
        }

        if (sum == target) {
            res40.add(new ArrayList<>(temp));
            return;
        }
        if (sum > target) {
            return;
        }
        for (int i = flag; i < candidates.length; i++) {
            if (i > flag && candidates[i] == candidates[i - 1]) {
                continue;
            }
            temp.add(candidates[i]);
            backTrace40(target, temp, candidates, i + 1);
            temp.remove(temp.size() - 1); // backtrack
        }
    }
    //leetcode-93 复原 IP 地址
    List<String> res93 = new ArrayList<>();
    public List<String> restoreIpAddresses(String s) {
        backTrace93(s,new ArrayList<>(),0);
        return res93;
    }

    public void backTrace93(String s, List<String> temp, int flag) {
        if (temp.size() == 4) {
            if (flag == s.length()) {
                res93.add(String.join(".", temp));
            }
            return;
        }
        for (int i = 1; i <= 3; i++) {
            if (flag + i > s.length()) {
                break;
            }
            String str = s.substring(flag, flag + i);
            if (str.length() > 1 && str.charAt(0) == '0') {
                continue;
            }
            if (Integer.parseInt(str) > 255) {
                continue;
            }
            temp.add(str);
            backTrace93(s, temp, flag + i);
            temp.remove(temp.size() - 1);
        }
    }
    //leetcode-90 子集 II
    List<List<Integer>> res90 = new ArrayList<>();
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        backTrace90(nums,new ArrayList<>(),0);
        return res90;
    }

    public void backTrace90(int[] nums, List<Integer> temp, int flag) {
        res90.add(new ArrayList<>(temp));
        for (int i = flag; i < nums.length; i++) {
            if (i > flag && nums[i] == nums[i - 1]) {
                continue;
            }
            temp.add(nums[i]);
            backTrace90(nums, temp, i + 1);
            temp.remove(temp.size() - 1);
        }
    }

    //leetcode-47 全排列 II
    List<List<Integer>> res47 = new ArrayList<>();
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        backTrace47(nums,new ArrayList<>(),new boolean[nums.length]);
        return res47;
    }

    public void backTrace47(int[] nums, List<Integer> temp, boolean[] flag) {
        if (temp.size() == nums.length) {
            res47.add(new ArrayList<>(temp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (flag[i] || (i > 0 && nums[i] == nums[i - 1] && !flag[i - 1])) {
                continue;
            }
            temp.add(nums[i]);
            flag[i] = true;
            backTrace47(nums, temp, flag);
            temp.remove(temp.size() - 1);
            flag[i] = false;
        }
    }
}
