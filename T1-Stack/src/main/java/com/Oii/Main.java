package com.Oii;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        Stack<Integer> stack = new Stack<>();

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
        System.out.println(res);
    }
}