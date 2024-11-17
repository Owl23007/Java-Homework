package com.Oii;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ProjectEuler {

    //T1 - 006. 和的平方与平方的和之间的差值 - 洛谷
    //注意：1<=n<=10000
    //题目保证最终结果不超过long long 类型的最大值
    public static void main0() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        long result = squareDifference(n);
        System.out.println(result);
    }

    public static long squareDifference(int n) {
        long sum = 0;
        long sumOfSquares = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
            sumOfSquares += (long) i * i;
        }
        return sum * sum - sumOfSquares;
    }
    //END

    //T2 - 008. 序列中的最大乘积 - 洛谷
    public static void main1() {
        Scanner scanner = new Scanner(System.in);
        int len = scanner.nextInt();
        scanner.nextLine();//若不写这一行，s所读入的长度就会为0，结果会显示运行错误RE
        String str = scanner.nextLine();
        int n = scanner.nextInt();

        long result = MaxMultiplication(str, len,n);

        System.out.println(result);
    }

    public static long MaxMultiplication(String str,int len, int n) {
        int max = 0;
        for (int i = 0; i < len - n; i++) {
            int temp = 1;
            for (int j = i; j < i + n; j++) {
                temp *= str.charAt(j) - '0';
            }
            max = Math.max(max, temp);
        }
        return max;
    }
    //END

    //T3 - 015. 格子路径 - 洛谷
    public static void main2() {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        long result = combination(n + m, m);

        System.out.println(result);
    }

    static long combination(int n, int k) {
        if (k > n - k) {
            k = n - k;
        }
        long result = 1;
        for (int i = 0; i < k; i++) {
            result *= (n - i);
            result /= (i + 1);
        }
        return result;
    }
    //END

    //T4 - 020. 阶乘各位数的和 - 洛谷
    public static void main3(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        BigInteger factorial = calculateFactorial(n);
        int digitSum = calculateDigitSum(factorial);
        System.out.println(digitSum);
    }

    private static BigInteger calculateFactorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 1; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    private static int calculateDigitSum(BigInteger number) {
        String numberStr = number.toString();
        int sum = 0;
        for (char digit : numberStr.toCharArray()) {
            sum += Character.getNumericValue(digit);
        }
        return sum;
    }

    //END

    //T5 - 024. 字典排列 - 洛谷

    public static void main4() {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        for (int t = 0; t < T; t++) {
            int n = scanner.nextInt();
            String result = getSorted(n);
            System.out.println(result);
        }
    }

    public static String getSorted(int n) {
        int[] factorial = new int[10];
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers.add(i);
        }

        factorial[0] = 1;
        for (int i = 1; i < 10; i++) {
            factorial[i] = factorial[i - 1] * i;
        }

        StringBuilder sb = new StringBuilder();
        try {
            n--;
            for (int i = 9; i >= 0; i--) {
                int index = n / factorial[i];
                sb.append(numbers.remove(index));
                n -= index * factorial[i];

            }
        } catch (Exception e) {
            return "WustJavaClub";
        }
        return sb.toString();
    }

    //END

    //T6 - 452. 用最少数量的箭引爆气球 - LeetCode

    public int findMinArrowShots(int[][] points) {
        if (points.length == 0) {
            return 0;
        }
        //Arrays.sort(points, (a, b) -> a[1] - b[1]);
        //存在溢出风险：[[-2147483646,-2147483645],[2147483646,2147483647]]
        //改为：
        Arrays.sort(points, (p1, p2) -> p1[1] < p2[1] ? -1 : 1);
        int count = 1;
        int end = points[0][1];
        for (int[] point : points) {
            if (point[0] > end) {
                count++;
                end = point[1];
            }
        }
        return count;
    }

    //END

    //T7 - 455. 分发饼干 - LeetCode

    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int i = 0;
        int j = 0;
        while (i < g.length && j < s.length) {
            if (g[i] <= s[j]) {
                i++;
            }
            j++;
        }
        return i;
    }

    //END

    //T8 - 11. 盛最多水的容器 - LeetCode

    public int maxArea(int[] height) {
        int max = 0;
        int i = 0;
        int j = height.length - 1;
        while (i < j) {
            int h = Math.min(height[i], height[j]);
            max = Math.max(max, h * (j - i));
            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }
        }
        return max;
    }

    //END
}
