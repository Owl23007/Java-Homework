package com.Oii.Problem150;

import com.sun.source.tree.Tree;

import java.util.*;

public class BinaryTreeSolution {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // 25.11.18 - 1 二叉树的最大深度 leetcode - 104
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    // 25.11.18 - 2 相同的树 leetcode - 100
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        boolean left = isSameTree(p.left, q.left);
        boolean right = isSameTree(p.right, q.right);
        return left && right && p.val == q.val;
    }

    // 25.11.18 - 3 翻转二叉树 leetcode - 226
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode temp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(temp);
        return root;
    }

    // 25.11.18 - 4 对称二叉树 `leetcode - 101`
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;

        Deque<TreeNode> queue = new ArrayDeque<>();
        if (root.left == null && root.right == null) return true;
        if (root.left == null || root.right == null) return false;
        queue.add(root.left);
        queue.add(root.right);

        while (!queue.isEmpty()) {
            TreeNode leftNode = queue.poll();
            TreeNode rightNode = queue.poll();

            // 两个都为 null，继续
            if (leftNode == null && rightNode == null) continue;

            // 一个为 null 一个不为 null，或值不相等，则不对称
            if (leftNode == null || rightNode == null || leftNode.val != rightNode.val) {
                return false;
            }

            // 在添加到队列前检查是否为 null
            if (leftNode.left != null && rightNode.right != null) {
                queue.add(leftNode.left);
                queue.add(rightNode.right);
            } else if (leftNode.left != null || rightNode.right != null) {
                // 一个为 null，一个不为 null，不对称
                return false;
            }

            if (leftNode.right != null && rightNode.left != null) {
                queue.add(leftNode.right);
                queue.add(rightNode.left);
            } else if (leftNode.right != null || rightNode.left != null) {
                // 一个为 null，一个不为 null，不对称
                return false;
            }
        }

        return true;
    }

    public boolean isSymmetric_2(TreeNode root) {
        if (root == null) return true;
        return isSymmetric(root.left, root.right);
    }

    public boolean isSymmetric(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        if (left.val != right.val) return false;
        boolean leftTag = isSymmetric(left.left, right.right);
        boolean rightTag = isSymmetric(left.right, right.left);
        return leftTag && rightTag;
    }

    // 25.11.19 - 1 从前序与中序遍历序列构造二叉树 leetcode - 105

    // 将 inorderMap 作为成员变量，避免在递归中反复传递
    private Map<Integer, Integer> inorderMap;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // 创建一个哈希表存储中序遍历的值和索引
        this.inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        // 调用辅助函数，初始时处理整个数组
        return buildTree(preorder, 0, preorder.length - 1,
                inorder, 0, inorder.length - 1);
    }

    private TreeNode buildTree(int[] preorder, int preStart, int preEnd,
                               int[] inorder, int inStart, int inEnd) {
        // 如果范围无效，则返回 null
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }

        // 前序遍历的第一个元素就是当前子树的根节点
        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);

        // 使用哈希表 O(1) 时间找到根节点在中序遍历中的索引
        // 不需要再传入 inorder 数组，因为我们不再遍历它
        int index = inorderMap.get(rootVal);

        // 计算左子树的节点数量
        int leftSubtreeSize = index - inStart;

        // 递归构建左子树
        // 左子树的前序范围: [preStart + 1, preStart + leftSubtreeSize]
        // 左子树的中序范围: [inStart, index - 1]
        root.left = buildTree(preorder, preStart + 1, preStart + leftSubtreeSize,
                inorder, inStart, index - 1);

        // 递归构建右子树
        // 右子树的前序范围: [preStart + leftSubtreeSize + 1, preEnd]
        // 右子树的中序范围: [index + 1, inEnd]
        root.right = buildTree(preorder, preStart + leftSubtreeSize + 1, preEnd,
                inorder, index + 1, inEnd);

        return root;
    }

    // 25.11.19 - 2 从中序与后序遍历序列构造二叉树 leetcode - 106
    private Map<Integer, Integer> inorderMap_2;

    public TreeNode buildTree_2(int[] inorder, int[] postorder) {
        // 创建一个哈希表存储中序遍历的值和索引
        this.inorderMap_2 = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap_2.put(inorder[i], i);
        }
        // 调用辅助函数，初始时处理整个数组
        return buildTree_2(postorder, 0, postorder.length - 1,
                inorder, 0, inorder.length - 1);
    }

    private TreeNode buildTree_2(int[] postorder, int postStart, int postEnd,
                                 int[] inorder, int inStart, int inEnd) {
        if (postStart > postEnd || inStart > inEnd) return null;

        int rootVal = postorder[postEnd];

        TreeNode root = new TreeNode(rootVal);

        int index = inorderMap_2.get(rootVal);

        int leftSubtreeSize = index - inStart;
        root.left = buildTree_2(postorder, postStart, postStart + leftSubtreeSize - 1,
                inorder, inStart, index - 1);
        root.right = buildTree_2(postorder, postStart + leftSubtreeSize, postEnd - 1,
                inorder, index + 1, inEnd);

        return root;
    }

    // 25.11.19 - 3 填充每个节点的下一个右侧节点指针 II leetcode - 117
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    ;

    public Node connect(Node root) {
        if (root == null) return null;
        Deque<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                Node node = queue.poll();
                if (node == null) continue;
                node.next = size > 1 ? queue.peek() : null;
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
                size--;
            }
        }
        return root;
    }

    // 25.11.20 - 1 二叉树的展开 leetcode - 114
    public void flatten(TreeNode root) {
        if (root == null) return;
        sort(root);
    }

    public TreeNode sort(TreeNode root) {
        if (root == null) return null;
        TreeNode leftEnd = sort(root.left);
        sort(root.right);
        if (leftEnd != null) {
            leftEnd.right = root.right;
            root.right = root.left;
            root.left = null;
        }


        while (root.right != null) {
            root = root.right;
        }
        return root;
    }

    // 25.11.20 - 2 路径总和 leetcode - 112
    public boolean hasPathSum(TreeNode root, int targetSum) {
        return hasPathSum(root, targetSum, 0);
    }

    public boolean hasPathSum(TreeNode root, int targetSum, int sum) {
        if (root == null) return false;
        if (sum + root.val == targetSum && root.left == null && root.right == null) return true;
        boolean left = hasPathSum(root.left, targetSum, sum + root.val);
        boolean right = hasPathSum(root.right, targetSum, sum + root.val);
        return left || right;
    }

    // 25.11.20 - 3 求根节点到叶节点数字之和 leetcode - 129
    public int sumNumbers(TreeNode root) {
        return sumNumbers(root, 0);
    }

    public int sumNumbers(TreeNode root, int sum) {
        if (root == null) return 0;
        sum = sum * 10 + root.val;
        if (root.left == null && root.right == null) return sum;
        return sumNumbers(root.left, sum) + sumNumbers(root.right, sum);
    }

    // 25.11.21 - 1 二叉树的最大路径和 leetcode - 124
    private int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxPathSumHelper(root);
        return maxSum;
    }

    private int maxPathSumHelper(TreeNode root) {
        if (root == null) return 0;

        // 递归计算左右子树的最大贡献值，若为负则取0表示不选该路径
        int left = Math.max(0, maxPathSumHelper(root.left));
        int right = Math.max(0, maxPathSumHelper(root.right));

        // 当前节点作为顶点的路径最大值
        int currentMax = root.val + left + right;

        // 更新全局最大值
        maxSum = Math.max(maxSum, currentMax);

        // 返回当前节点能向上提供的最大路径和
        return root.val + Math.max(left, right);
    }

    // 25.11.21 - 2 二叉搜索树迭代器 leetcode - 173
    class BSTIterator {
        List<TreeNode> nodeList;
        int index;

        public BSTIterator(TreeNode root) {
            nodeList = new ArrayList<>();
            index = 0;
            inorderTraversal(root);
        }

        private void inorderTraversal(TreeNode node) {
            if (node != null) {
                inorderTraversal(node.left);
                nodeList.add(node);
                inorderTraversal(node.right);
            }
        }

        public int next() {
            return nodeList.get(index++).val;
        }

        public boolean hasNext() {
            return index < nodeList.size();
        }
    }

    // 25.11.21 - 3 完全二叉树的节点个数 leetcode - 222
    public int countNodes(TreeNode root) {
        if (root == null) return 0;
        TreeNode point = root;
        int countR = 1;
        while (point.right != null) {
            countR++;
            point = point.right;
        }

        point = root;
        int countL = 1;
        while (point.left != null) {
            countL++;
            point = point.left;
        }

        if (countL == countR) return (1 << countL) - 1;

        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    // 25.11.21 - 4 二叉树的最近公共祖先 leetcode - 236
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (root == p || root == q) return root; // 递归终止条件
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) {
            return root;
        }
        return left != null ? left : right;
    }

    //
    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) return new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();

        stack.add(root);

        List<Integer> res = new ArrayList<>();
        res.add(root.val);


        int size = 1;
        while (!stack.isEmpty()) {
            TreeNode node = stack.poll();
            size--;

            if (node.right != null) {
                stack.add(node.right);
            }
            if (node.left != null) {
                stack.add(node.left);
            }
            if (size == 0 && stack.peek() != null) {
                res.add(stack.peek().val);
                size = stack.size();
            }
        }
        return res;
    }

    public List<Double> averageOfLevels(TreeNode root) {
        if (root == null) return new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();

        stack.add(root);

        List<Double> res = new ArrayList<>();

        int size = 1;
        int count = 0;
        double sum = 0.00;
        while (!stack.isEmpty()) {
            TreeNode node = stack.poll();
            count++;
            sum += node.val;

            if (node.right != null) {
                stack.add(node.right);
            }
            if (node.left != null) {
                stack.add(node.left);
            }
            if (size == count) {
                double avg  = sum / size;
                sum = 0.00;
                res.add(avg);
                size = stack.size();
                count = 0;
            }
        }
        return res;
    }
}


