### Project Euler 题解文档

#### T1 - 006. 和的平方与平方的和之间的差值

**题目描述：**
给定一个整数 `n`，求前 `n` 个自然数的平方和与和的平方之间的差值。

**解决方案：**
```java
public static long squareDifference(int n) {
    long sum = 0;
    long sumOfSquares = 0;
    for (int i = 1; i <= n; i++) {
        sum += i;
        sumOfSquares += (long) i * i;
    }
    return sum * sum - sumOfSquares;
}
```

**解释：**
1. 计算前 `n` 个自然数的和。
2. 计算前 `n` 个自然数的平方和。
3. 返回和的平方与平方和之间的差值。

#### T2 - 008. 序列中的最大乘积

**题目描述：**
给定一个数字字符串，求 `n` 个连续数字的最大乘积。

**解决方案：**
```java
public static long MaxMultiplication(String str, int len, int n) {
    int max = 0;
    for (int i = 0; i <= len - n; i++) {
        int temp = 1;
        for (int j = i; j < i + n; j++) {
            temp *= str.charAt(j) - '0';
        }
        max = Math.max(max, temp);
    }
    return max;
}
```

**解释：**
1. 遍历字符串，考虑每个长度为 `n` 的子串。
2. 计算每个子串中数字的乘积。
3. 记录找到的最大乘积。

#### T3 - 015. 格子路径

**题目描述：**
给定一个 `n x m` 的网格，求从左上角到右下角的唯一路径数。

**解决方案：**
```java
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
```

**解释：**
1. 使用组合数学计算唯一路径数。
2. 路径数由二项式系数 `C(n + m, m)` 给出。

#### T4 - 020. 阶乘各位数的和

**题目描述：**
给定一个整数 `n`，求 `n!` 的各位数字之和。

**解决方案：**
```java
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
```

**解释：**
1. 使用 `BigInteger` 计算 `n` 的阶乘以处理大数。
2. 将阶乘转换为字符串并求其各位数字之和。

#### T5 - 024. 字典排列

**题目描述：**
给定一个整数 `n`，求 0-9 的第 `n` 个字典排列。

**解决方案：**
```java
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
```

**解释：**
1. 预计算 0-9 的阶乘。
2. 使用阶乘数系统确定第 `n` 个排列。

#### T6 - 452. 用最少数量的箭引爆气球

**题目描述：**
给定一个表示气球的区间列表，求引爆所有气球所需的最少箭数。

**解决方案：**
```java
public int findMinArrowShots(int[][] points) {
    if (points.length == 0) {
        return 0;
    }
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
```

**解释：**
1. 按区间的结束点排序。
2. 遍历区间，计算不重叠区间的数量。

#### T7 - 455. 分发饼干

**题目描述：**
给定两个数组，分别表示孩子的贪心指数和饼干的大小，求最多能满足的孩子数量。

**解决方案：**
```java
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
```

**解释：**
1. 对两个数组进行排序。
2. 使用双指针技术贪心地分配饼干给孩子。

#### T8 - 11. 盛最多水的容器

**题目描述：**
给定一个高度数组，求两条线之间能盛最多的水。

**解决方案：**
```java
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
```

**解释：**
1. 使用双指针技术找到最大面积。
2. 移动指针向内，更新找到的最大面积。
