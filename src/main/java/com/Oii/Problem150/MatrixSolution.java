package com.Oii.Problem150;

import java.util.ArrayList;
import java.util.List;

public class MatrixSolution {
    public boolean isValidSudoku(char[][] board) {
        int[][][] flag = new int[10][10][3];

        for (int i = 0; i < 9; i++){ // 遍历行
            for (int j = 0; j < 9; j++){ // 遍历列
                if (board[i][j] == '.'){
                    continue;
                }
                int num = board[i][j] - '0';
                int k =j/3*3+i/3; // 获取当前数字在第几块

                if (flag[num][i][0] == 1 || flag[num][j][1] == 1 || flag[num][k][2] == 1){
                    return false;
                }
                flag[num][i][0] = flag[num][j][1] = flag[num][k][2] = 1;
            }
        }
        return true;
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        int[][] tag = new int[matrix.length][matrix[0].length];

        List<Integer> res = new ArrayList<>();
        res.add(matrix[0][0]);
        tag[0][0] = 1;
        int x = 0, y = 0;
        boolean flag = true;
        while (flag){
            flag = false;

            while (y+1 < matrix[0].length && tag[x][y+1] == 0){
                y++;
                res.add(matrix[x][y]);
                tag[x][y] = 1;
                flag = true;
            }

            while (x+1 < matrix.length && tag[x+1][y] == 0){
                x++;
                res.add(matrix[x][y]);
                tag[x][y] = 1;
                flag = true;
            }
            while (y-1 >= 0 && tag[x][y-1] == 0){
                y--;
                res.add(matrix[x][y]);
                tag[x][y] = 1;
                flag = true;
            }
            while (x-1 >= 0 && tag[x-1][y] == 0){
                x--;
                res.add(matrix[x][y]);
                tag[x][y] = 1;
                flag = true;
            }

        }
        return res;
    }

    // 25.11.03 - 1 旋转图像 leetcode - 48
    public void rotate(int[][] matrix) {
        int total = matrix.length;
        int n = total;

        while (n > 1) {
            int deep = (total - n) / 2; // 当前层的起始行/列
            int last = deep + n - 1;    // 当前层的结束行/列

            // 只需遍历当前层顶部边的前 (n - 1) 个元素
            for (int i = deep; i < last; i++) {
                int offset = i - deep; // 相对于当前层的偏移量

                // 保存 top
                int temp = matrix[deep][i];

                // left → top
                matrix[deep][i] = matrix[last - offset][deep];

                // bottom → left
                matrix[last - offset][deep] = matrix[last][last - offset];

                // right → bottom
                matrix[last][last - offset] = matrix[i][last];

                // top → right
                matrix[i][last] = temp;
            }

            n -= 2; // 进入内层
        }
    }

    // 25.11.03 - 2 矩阵置零 leetcode - 73

    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        boolean[] zeroRows = new boolean[m];
        boolean[] zeroCols = new boolean[n];

        // 第一遍：记录哪些行/列有 0
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    zeroRows[i] = true;
                    zeroCols[j] = true;
                }
            }
        }

        // 第二遍：根据记录清零
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (zeroRows[i] || zeroCols[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    // 25.11.03 - 3 生命游戏 leetcode - 289
    public void gameOfLife(int[][] board) {
        // 0 - 死亡，1 - 活跃，2 - 下一帧死亡，3 - 下一帧活跃
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int count =  find(board, i, j);
                if (board[i][j] == 1) {
                    if (count < 2 || count > 3) {
                        board[i][j] = 2;
                    }
                } else {
                    if (count == 3) {
                        board[i][j] = 3;
                    }
                }
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 2) {
                    board[i][j] = 0;
                }
                if (board[i][j] == 3) {
                    board[i][j] = 1;
                }
            }
        }

    }

    public int find(int[][] board, int i, int j) {
        int count = 0;
        for (int x =i - 1; x < i + 2; x++) {
            for(int y = j - 1; y < j + 2; y++){
                if (x == i && y == j) continue;
                if (x < 0 || x >= board.length || y < 0 || y >= board[0].length){
                    continue;
                }
                if (board[x][y] == 1 || board[x][y] == 2){ // 活细胞
                    count++;
                }
            }
        }
        return count;
    }


}
