package com.Oii.Problem150;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
public class StackSolution {

    // 25.11.10 - 1 有效的括号 leetcode - 20
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == '[' || s.charAt(i) == '{') {
                stack.push(s.charAt(i));
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                char c = stack.pop();

                if (s.charAt(i) == ')' && c != '(') {
                    return false;
                }
                if (s.charAt(i) == ']' && c != '[') {
                    return false;
                }
                if (s.charAt(i) == '}' && c != '{') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    // 25.11.10 - 2 简化路径 leetcode - 71
    public String simplifyPath(String path) {
        Deque<String> deque = new LinkedList<>();
        String[] parts = path.split("/");

        for (String part : parts) {
            if (part.equals("..")) {
                if (!deque.isEmpty()) {
                    deque.pollLast(); // 移除最后一个元素
                }
            } else if (!part.isEmpty() && !part.equals(".")) {
                deque.offerLast(part); // 添加非空的目录名
            }
        }
        if (deque.isEmpty()) {
            return "/";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append('/');
            // 使用 StringBuilder 构建结果，比 Stream 更高效
            while (!deque.isEmpty()) {
                sb.append(deque.pollFirst());
                if (!deque.isEmpty()) {
                    sb.append('/');
                }
            }
            return sb.toString();
        }
    }
}
