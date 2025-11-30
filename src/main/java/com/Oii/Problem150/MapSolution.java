package com.Oii.Problem150;

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


    // 25.11.27 - 1 克隆图 leetcode - 133
    class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    public Node cloneGraph(Node node) {
        if (node == null) return null;

        HashMap<Node, Node> map = new HashMap<>();
        Deque<Node> stack = new ArrayDeque<>();

        // 克隆起始节点并放入 map
        map.put(node, new Node(node.val));
        stack.push(node);

        while (!stack.isEmpty()) {
            Node curr = stack.pop();
            Node currClone = map.get(curr); // 获取已存在的克隆

            for (Node neighbor : curr.neighbors) {
                if (!map.containsKey(neighbor)) {
                    map.put(neighbor, new Node(neighbor.val));
                    stack.push(neighbor);
                }
                // 将克隆后的邻居加入当前克隆节点的邻居列表
                currClone.neighbors.add(map.get(neighbor));
            }
        }
        return map.get(node);
    }

    // 25.11.28 - 1 除法求值 leetcode - 399
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        // Step 1: 构建图（邻接表）
        Map<String, Map<String, Double>> graph = new HashMap<>();
        for (int i = 0; i < equations.size(); i++) {
            String u = equations.get(i).get(0);
            String v = equations.get(i).get(1);
            double val = values[i];

            graph.computeIfAbsent(u, k -> new HashMap<>()).put(v, val);
            graph.computeIfAbsent(v, k -> new HashMap<>()).put(u, 1.0 / val);
        }

        // Step 2: 处理每个查询
        double[] result = new double[queries.size()];

        for (int i = 0; i < queries.size(); i++) {
            String start = queries.get(i).get(0);
            String end = queries.get(i).get(1);

            // 如果起点或终点不在图中，无法计算
            if (!graph.containsKey(start) || !graph.containsKey(end)) {
                result[i] = -1.0;
            } else if (start.equals(end)) {
                // 任意存在的变量除以自己 = 1.0
                result[i] = 1.0;
            } else {
                // DFS 搜索路径
                Set<String> visited = new HashSet<>();
                double ans = dfs(start, end, graph, visited);
                result[i] = ans; // ans 要么是正数，要么是 -1.0
            }
        }

        return result;
    }

    private double dfs(String cur, String target, Map<String, Map<String, Double>> graph, Set<String> visited) {
        if (cur.equals(target)) {
            return 1.0;
        }

        visited.add(cur);

        Map<String, Double> neighbors = graph.get(cur);
        if (neighbors != null) {
            for (Map.Entry<String, Double> entry : neighbors.entrySet()) {
                String next = entry.getKey();
                double weight = entry.getValue();

                if (!visited.contains(next)) {
                    double res = dfs(next, target, graph, visited);
                    if (res != -1.0) {
                        return weight * res;
                    }
                }
            }
        }

        return -1.0;
    }

    // 25.11.28 - 2 课程表 leetcode - 207
    // 第一想法: 迭代移除已满足的先修课，直到无法继续
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (prerequisites == null || prerequisites.length == 0) return true;
        int[] graph = new int[numCourses];

        boolean flag = true;

        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int[] pre : prerequisites) {
            int course = pre[0];
            int prereq = pre[1];
            map.computeIfAbsent(course, k -> new HashSet<>()).add(prereq);
            graph[course] = 1;
        }

        while (flag) {
            flag = false;
            for (Map.Entry<Integer, Set<Integer>> entry : map.entrySet()) {
                if (graph[entry.getKey()] == 1) { // 存在未完成的课程
                    entry.getValue().removeIf(j -> graph[j] == 0);
                    if (entry.getValue().isEmpty()) {
                        graph[entry.getKey()] = 0;
                        flag = true;
                    }
                }
            }
        }

        for (int i : graph) {
            if (i == 1) return false;
        }
        return true;
    }

    // 更好的方法: 拓扑排序
    public boolean canFinish_1(int numCourses, int[][] prerequisites) {
        // 1. 构建邻接表和入度数组
        List<List<Integer>> adj = new ArrayList<>(numCourses);
        int[] inDegree = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] pre : prerequisites) {
            int course = pre[0];      // 要学的课
            int prereq = pre[1];      // 先修课
            adj.get(prereq).add(course); // prereq → course
            inDegree[course]++;            // course 的入度 +1
        }

        // 2. BFS：将所有入度为 0 的课程入队（无依赖，可直接学）
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        // 3. 拓扑排序
        int completed = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            completed++; // 完成一门课

            for (int next : adj.get(cur)) {
                inDegree[next]--;       // 移除 cur 对 next 的依赖
                if (inDegree[next] == 0) {
                    queue.offer(next);  // 所有依赖满足，可学
                }
            }
        }

        // 4. 如果完成课程数等于总数，说明无环 → 可完成
        return completed == numCourses;
    }

    // 25.11.28 - 3 课程表 II leetcode - 210
    // 方法一: Kahn 算法（BFS + 入度） 拓扑排序
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }

        // 1. 构建邻接表和入度数组
        int[] inDegree = new int[numCourses];
        for (int[] pre : prerequisites) {
            adj.get(pre[1]).add(pre[0]);
            inDegree[pre[0]]++;
        }

        // 2. 添加入度为 0 的课程入队
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        // 3. 根据当前可达的课程，进行延伸
        List<Integer> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            res.add(cur);
            for (int next : adj.get(cur)) {
                inDegree[next]--;
                if (inDegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }

        if (res.size() != numCourses) {
            return new int[0];
        }
        return res.stream().mapToInt(i -> i).toArray();
    }

    // 方法二: DFS 三色标记法
    private List<List<Integer>> graph;
    private int[] state;          // 0: 未访问 1: 访问中 2: 访问完成
    private int[] topologicalOrder;
    private boolean hasCycle;
    private int orderIndex;

    public int[] findOrder_1(int numCourses, int[][] prerequisites) {
        // 初始化图
        graph = new ArrayList<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }

        // 构建邻接表：prerequisite -> course
        for (int[] edge : prerequisites) {
            graph.get(edge[1]).add(edge[0]);
        }

        // 初始化状态数组和结果数组
        state = new int[numCourses];
        topologicalOrder = new int[numCourses];
        hasCycle = false;
        orderIndex = numCourses - 1;

        // 对每个未访问的节点进行 DFS
        for (int i = 0; i < numCourses && !hasCycle; i++) {
            if (state[i] == 0) {
                dfs(i);
            }
        }

        return hasCycle ? new int[0] : topologicalOrder;
    }

    private void dfs(int node) {
        state[node] = 1; // 标记为正在访问（VISITING）

        for (int neighbor : graph.get(node)) {
            if (state[neighbor] == 0) {
                dfs(neighbor);
                if (hasCycle) return;
            } else if (state[neighbor] == 1) {
                // 遇到正在访问的节点 ⇒ 存在环
                hasCycle = true;
                return;
            }
            // state[neighbor] == 2：已访问完成，跳过
        }

        state[node] = 2; // 标记为已访问完成（VISITED）
        topologicalOrder[orderIndex--] = node; // 逆序入栈
    }

    // 25.11.09 - 1 蛇梯棋 leetcode - 909
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        int[] coordinates = new int[n * n + 1];

        boolean direction = true;
        int index = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (direction) {
                for (int j = 0; j < n; j++) {
                    coordinates[index++] = board[i][j];
                }
            } else {
                for (int j = n - 1; j >= 0; j--) {
                    coordinates[index++] = board[i][j];
                }
            }
            direction = !direction;
        }

        boolean[] visited = new boolean[n * n + 1];
        Deque<Integer> queue = new LinkedList<>();
        queue.add(1);
        visited[1] = true;

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            step++; // 进入新的一层，步数 +1
            for (int s = 0; s < size; s++) {
                int curr = queue.poll();

                for (int i = 1; i <= 6; i++) {
                    int next = curr + i;
                    if (next > n * n) break; // 超出，后续 i 更大，break

                    int dest = coordinates[next] != -1 ? coordinates[next] : next;

                    if (dest == n * n) {
                        return step;
                    }

                    if (!visited[dest]) {
                        visited[dest] = true;
                        queue.add(dest);
                    }
                }
            }
        }
        return -1;
    }

    // 25.11.09 - 2 最小基因变化 leetcode - 433
    public int minMutation(String startGene, String endGene, String[] bank) {
        // 1. 查找 endGene 是否在 bank 中
        boolean tag = true;
        for (String s : bank) {
            if (s.equals(endGene)) {
                tag = false;
                break;
            }
        }
        if (tag) {
            return -1;
        }

        // 2. 层次遍历
        int[] visited = new int[bank.length];
        int step = 0;
        Deque<String> list = new LinkedList<>();
        list.add(startGene);

        while (!list.isEmpty()) {
            int size = list.size();
            step++;
            for (int i = 0; i < size; i++) {
                String cur = list.poll();
                for (int j = 0; j < bank.length; j++) {
                    if (visited[j] == 1) {
                        continue;
                    }
                    if (!isNeighbour(cur, bank[j])) {
                        continue;
                    }
                    if (bank[j].equals(endGene)) {
                        return step;
                    }

                    visited[j] = 1;
                    list.add(bank[j]);
                }
            }
        }
        return -1;
    }

    private static boolean isNeighbour(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        if (a.equals(b)) {
            return false;
        }
        // 判断 a 和 b 是否相邻
        int count = 0;
        int n = a.length();
        for (int i = 0; i < n; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                count++;
            }
        }
        return count == 1;
    }


    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);

        int steps = 0;
        int wordLen = beginWord.length();

        while (!queue.isEmpty()) {
            int size = queue.size();
            steps++;
            for (int i = 0; i < size; i++) {
                String cur = queue.poll();
                if (cur.equals(endWord)) {
                    return steps;
                }

                // 生成所有可能的邻居（变换每个位置为 a-z）
                char[] chars = cur.toCharArray();
                for (int j = 0; j < wordLen; j++) {
                    char original = chars[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) continue;
                        chars[j] = c;
                        String next = new String(chars);
                        if (wordSet.contains(next) && !visited.contains(next)) {
                            visited.add(next);
                            queue.offer(next);
                        }
                    }
                    chars[j] = original; // 恢复
                }
            }
        }
        return 0;
    }

    class Trie {
        private static final int END_INDEX = 26;
        private final Trie[] children; // 长度 27：0-25 是 a-z，26 是结束标记

        public Trie() {
            children = new Trie[27]; // 初始化为 null
        }

        public void insert(String word) {
            insert(word, 0);
        }

        private void insert(String word, int idx) {
            if (idx == word.length()) {
                // 到达单词末尾，设置结束标记
                children[END_INDEX] = new Trie(); // 只需非 null 即可
                return;
            }
            char c = word.charAt(idx);
            int childIndex = c - 'a';
            if (children[childIndex] == null) {
                children[childIndex] = new Trie();
            }
            children[childIndex].insert(word, idx + 1);
        }

        public boolean search(String word) {
            Trie node = traverse(word);
            return node != null && node.children[END_INDEX] != null;
        }

        public boolean startsWith(String prefix) {
            return traverse(prefix) != null;
        }

        private Trie traverse(String s) {
            if (s == null) return null;
            Trie node = this;
            for (char c : s.toCharArray()) {
                int idx = c - 'a';
                if (node == null || node.children[idx] == null) {
                    return null;
                }
                node = node.children[idx];
            }
            return node;
        }
    }

}
