package com.Oii.Problem150;

import com.sun.jdi.Value;

import java.util.*;

public class MapSolution {
    // 25.11.26 - 1 岛屿数量 leetcode - 200
    // 方法1: 并查集
    private final Map<Integer, Integer> parent = new HashMap<>();
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        int m = grid.length, n = grid[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    int id = i * n + j; // 创建id
                    parent.put(id, id); // 初始化父节点为自身
                    if (i > 0 && grid[i - 1][j] == '1') merge(id, (i - 1) * n + j); // 上为陆地，合并
                    if (j > 0 && grid[i][j - 1] == '1') merge(id, i * n + j - 1); // 左
                }
            }
        }

        int islands = 0;
        for (int id : parent.keySet()) {
            if (find(id) == id) islands++;
        }
        return islands;
    }

    // 查找父节点, 同时更新路径上的父节点
    private int find(int x) {
        if (parent.get(x) != x) {
            // 路径压缩
            parent.put(x, find(parent.get(x))); // 递归，对前面的节点进行更新
        }
        return parent.get(x);
    }

    // 合并
    private void merge(int a, int b) {
        int ra = find(a), rb = find(b); // 找到两个节点的根节点
        if (ra != rb) parent.put(rb, ra); // 合并
    }

    //  方法2: 深度优先搜索
    int[] tag;
    public int numIslands_1(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        int m = grid.length, n = grid[0].length;
        tag = new int[m * n];

        int islands = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                   dfs(grid, i, j);
                   islands++;
                }
            }
        }

        return islands;
    }

    private void dfs(char[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != '1') return;
        grid[i][j] = '0';
        tag[i * grid[0].length + j] = 1;
        dfs(grid, i + 1, j); // 下
        dfs(grid, i - 1, j); // 上
        dfs(grid, i, j + 1); // 右
        dfs(grid, i, j - 1); // 左
    }

    // 25.11.26 - 2 被围绕的区域 leetcode - 130
    public void solve(char[][] board) {
        if (board == null || board.length == 0) return;
        int m = board.length, n = board[0].length;

        // 从四个边界出发，标记所有与边界连通的 'O' 为 'A'
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') dfs_1(board, i, 0);
            if (board[i][n - 1] == 'O') dfs_1(board, i, n - 1);
        }
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') dfs_1(board, 0, j);
            if (board[m - 1][j] == 'O') dfs_1(board, m - 1, j);
        }

        // 遍历整个 board：
        // 'A' -> 'O'（保留）
        // 'O' -> 'X'（被包围）
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'A') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }

    private void dfs_1(char[][] board, int i, int j) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != 'O') {
            return;
        }
        board[i][j] = 'A'; // 标记为安全
        dfs_1(board, i + 1, j);
        dfs_1(board, i - 1, j);
        dfs_1(board, i, j + 1);
        dfs_1(board, i, j - 1);
    }

}
