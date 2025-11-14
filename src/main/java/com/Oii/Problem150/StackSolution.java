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

    // 25.11.12 - 1 逆波兰表达式求值 leetcode - 150
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

    // 25.11.12 - 2 基本计算器 leetcode - 224
    // 原题虽然只是要求 0~9 ，且运算符只有加减、负数与括号
    // 这里提供的是一个更通用的版本，可以处理用于整数加减乘除的表达式
    public int calculate(String s) {
        if (s == null || s.isEmpty()) return 0;
        s = s.replaceAll(" ", ""); // 移除所有空格

        Deque<Integer> values = new LinkedList<>(); // 数字栈
        Deque<Character> ops = new LinkedList<>();  // 操作符栈

        int i = 0;
        boolean tag = true; // 标记上一个字符是否为'('或操作符后（可以出现负号）

        while (i < s.length()) {
            char c = s.charAt(i);

            if (c == '-' && tag) {
                // 负号处理：出现在开头或左括号后或操作符后
                i++;
                if (i < s.length() && Character.isDigit(s.charAt(i))) {
                    // 遇到数字，解析数字并压入取反操作符
                    int num = parseNumber(s, i);
                    i = skipDigits(s, i); // 更新索引到数字末尾
                    values.push(num);
                    ops.push('~');    // 压入取反操作符
                    tag = false;
                } else if (i < s.length() && s.charAt(i) == '(') {
                    // 负号后面是左括号，压入取反操作符和左括号
                    ops.push('~');
                    ops.push('(');
                    tag = true;
                    i++;
                } else {
                    // 负号后不是数字也不是括号，按普通减法处理
                    processOperator(values, ops, c);
                    tag = true;
                }
            } else if (Character.isDigit(c)) {
                // 解析完整数字
                int num = parseNumber(s, i);
                i = skipDigits(s, i); // 更新索引到数字末尾
                values.push(num);
                tag = false;
            } else {
                // 处理操作符和括号
                switch (c) {
                    case '(':
                        ops.push(c);
                        tag = true;
                        i++;
                        break;
                    case ')':
                        // 计算括号内的表达式
                        processUntilLeftParen(values, ops);
                        tag = false;
                        i++;
                        break;
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        processOperator(values, ops, c);
                        tag = true;
                        i++;
                        break;
                    default:
                        // 跳过其他字符
                        i++;
                        break;
                }
            }
        }

        // 处理栈中剩余的所有操作
        while (!ops.isEmpty()) {
            processOperatorLeft(values, ops);
        }

        return values.pop();
    }

    private int parseNumber(String s, int start) {
        int num = 0;
        int i = start;
        while (i < s.length() && Character.isDigit(s.charAt(i))) {
            num = num * 10 + (s.charAt(i) - '0');
            i++;
        }
        return num;
    }

    private int skipDigits(String s, int start) {
        int i = start;
        while (i < s.length() && Character.isDigit(s.charAt(i))) {
            i++;
        }
        return i;
    }

    private void processUntilLeftParen(Deque<Integer> values, Deque<Character> ops) {
        while (!ops.isEmpty() && ops.peek() != '(') {
            processOperatorLeft(values, ops);
        }
        if (!ops.isEmpty()) {
            ops.pop(); // 弹出 '('
        }
    }

    private void processOperator(Deque<Integer> values, Deque<Character> ops, char c) {
        while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(c)) {
            processOperatorLeft(values, ops);
        }
        ops.push(c);
    }

    private void processOperatorLeft(Deque<Integer> values, Deque<Character> ops) {
        char op = ops.pop();
        if (op == '~') {
            values.push(-values.pop());
        } else {
            int val2 = values.pop();
            int val1 = values.pop();
            values.push(applyOp(val1, val2, op));
        }
    }

    // 定义操作符优先级
    private int precedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        if (op == '~') return 3; // 取反操作符优先级最高
        return 0; // '(' 的优先级最低
    }

    // 执行单个运算
    private int applyOp(int a, int b, char op) {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> 0;
        };
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

