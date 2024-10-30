package com.Oii;


import java.util.Scanner;

import static java.util.Arrays.sort;

class Stack {
    public int suffixExpressions(String str) {
            java.util.Stack<Integer> stack = new java.util.Stack<>();

            int i = 0;
            while (i < str.length()) {
                char ch = str.charAt(i);
                if (Character.isDigit(ch)) {
                    int num = 0;
                    while (i < str.length() && Character.isDigit(str.charAt(i))) {
                        num = num * 10 + (str.charAt(i) - '0');
                        i++;
                    }
                    stack.push(num);
                } else if (ch == '.') {
                    i++;
                } else if (ch == '@') {
                    break;
                } else {
                    int b = stack.pop();
                    int a = stack.pop();
                    int result = switch (ch) {
                        case '+' -> a + b;
                        case '-' -> a - b;
                        case '*' -> a * b;
                        case '/' -> a / b;
                        default -> 0;
                    };
                    stack.push(result);
                    i++;
                }
            }
            int res = stack.pop();
            return res;
    }
    public int[] finalPrices(int[] prices) {
        int n = prices.length;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            int discount = 0;
            for (int j = i + 1; j < n; j++) {
                if (prices[j] <= prices[i]) {
                    discount = prices[j];
                    break;
                }
            }
            res[i] = prices[i] - discount;
        }
        return res;
    }
}