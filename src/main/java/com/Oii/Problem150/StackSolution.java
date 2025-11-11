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


    public int evalRPN(String[] tokens) {
        Deque<Integer> numsStack = new LinkedList<>();

        int res;
        for (String token : tokens) {
            switch (token) {
                case "+":
                    res = numsStack.pop() + numsStack.pop();
                    numsStack.push(res);
                    break;
                case "-":
                    res = -numsStack.pop() + numsStack.pop();
                    numsStack.push(res);
                    break;
                case "*":
                    res = numsStack.pop() * numsStack.pop();
                    numsStack.push(res);
                    break;
                case "/":
                    int a = numsStack.pop();
                    int b = numsStack.pop();
                    res = b / a;
                    numsStack.push(res);
                    break;
                default:
                    numsStack.push(Integer.parseInt(token));
            }
        }
        return numsStack.pop();
    }

    public int calculate(String s) {
        s = s.replace(" ", "");
        Deque<Integer> stack = new LinkedList<>();
        Deque<Character> opStack = new LinkedList<>();

        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '+':
                case '-':
                case '*':
                case '/':
                case '(':
                    opStack.push(c);
                    break;
                case ')':
                    while (opStack.peekFirst() != '(') {
                        Character op = opStack.pollFirst();
                        switch (op) {
                            case '+':
                                res = stack.pop() + stack.pop();
                                stack.push(res);
                                break;
                            case '-':
                                res = -stack.pop() + stack.pop();
                                stack.push(res);
                                break;
                            case '*':
                                res = stack.pop() * stack.pop();
                                stack.push(res);
                                break;
                            case '/':
                                break;
                            case null:
                                break;
                        }
                    }
                    opStack.pop();
                default:
                    stack.push(Integer.parseInt(String.valueOf(c)));
            }
            while (!opStack.isEmpty() && opStack.peekFirst() != '(') {
                char op = opStack.pollFirst();
                switch (op) {
                    case '+':
                        res = stack.pop() + stack.pop();
                        stack.push(res);
                        break;
                    case '-':
                        res = -stack.pop() + stack.pop();
                        stack.push(res);
                        break;
                    case '*':
                        res = stack.pop() * stack.pop();
                        stack.push(res);
                        break;
                    case '/':
                        int a = stack.pop();
                        int b = stack.pop();
                        res = b / a;
                        stack.push(res);
                        break;
                }
            }
            return stack.pop();
        }
    }
}

class MinStack {
    Deque<Integer> stack;
    Deque<Integer> minStack;

    public MinStack() {
        stack = new LinkedList<>();
        minStack = new LinkedList<>();
    }

    public void push(int val) {
        stack.push(val);
        if (minStack.isEmpty()) {
            minStack.push(val);
        } else {
            if (val <= minStack.peekFirst()) {
                minStack.push(val);
            }
        }

    }

    public void pop() {
        if (minStack.peekFirst() != null && minStack.peekFirst().equals(stack.peekFirst())) {
            minStack.pop();
        }
        stack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}

