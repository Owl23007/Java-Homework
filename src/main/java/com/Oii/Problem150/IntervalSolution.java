package com.Oii.Problem150;

import java.util.*;

public class IntervalSolution {
    // 25.11.07 - 1 区间列表的交集 leetcode - 986
    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        int start = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1] + 1) {
                if (start == nums[i - 1]) {
                    res.add(String.valueOf(start));
                } else {
                    res.add(start + "->" + nums[i - 1]);
                }

                start = nums[i];
            }
        }
        if (start == nums[nums.length - 1]) {
            res.add(String.valueOf(start));
        } else {
            res.add(start + "->" + nums[nums.length - 1]);
        }
        return res;
    }

    // 25.11.07 - 2 合并区间 leetcode - 56

    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        List<int[]> res = new ArrayList<>();
        for (int i = 0; i < intervals.length;) {
            int start = intervals[i][0];
            int end = intervals[i][1];
            while (i < intervals.length && intervals[i][0] <= end) {
                end = Math.max(end, intervals[i][1]);
                i++;
            }
            res.add(new int[]{start, end});
        }
        return res.toArray(new int[res.size()][]);
    }

    // 25.11.08 -1 插入区间 leetcode - 57
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> res = new ArrayList<>();

        int tag = 0;
        for (int[] interval : intervals) {
            if(interval[0] > newInterval[1]){
                // 当前区间在插入区间的右侧，直接添加
               if(tag == 0){
                   // 插入区间在当前区间的左侧,判断是否已经添加过
                   res.add(newInterval);
                   tag = 1;
               }
                res.add(interval);
            }else if (interval[1]< newInterval[0]){
                // 当前区间在插入区间的左侧，直接添加
                res.add(interval);
            }else {
                // 重叠的区间
                int end = Math.max(interval[1], newInterval[1]);
                int start = Math.min(interval[0], newInterval[0]);
                newInterval = new int[]{start, end};
            }
        }

        if(tag == 0){
            res.add(newInterval);
        }
        return res.toArray(new int[0][]);
    }

    // 25.11.08 - 2 寻找最小的箭头 leetcode - 452
    // 思路一: 按区间起始位置排序，然后处理重叠区间
    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0]));
        int end = points[0][1];  // 当前区间的最小的结束位置，设为插标的位置
        int count = 1;

        for(int[] p: points){
            int start = p[0];
            if (start > end) {
                // 插标位置在当前区间的左侧，无法命中当前
                count++;
                end= p[1];
            }else{
                end = Math.min(end, p[1]);
            }
        }

        return count;
    }

    // 变体，既然我们要插入的标在每个区间的结束位置，那么我们按照区间结束位置排序，然后处理重叠区间
    public int findMinArrowShots_2(int[][] points) {
        if (points.length == 0) {
            return 0;
        }
        Arrays.sort(points, (p1, p2) -> p1[1] < p2[1] ? -1 : 1); // 按区间结束位置排序
        int count = 1;
        int end = points[0][1];
        for (int[] point : points) {
            if (point[0] > end) { // 如果当前区间的起始位置在插标位置的右侧，则无法命中当前区间，需要添加新的箭头
                count++;
                end = point[1];
            }
        }
        return count;
    }

}
