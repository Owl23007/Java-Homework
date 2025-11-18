package com.Oii.Problem150;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

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


}
