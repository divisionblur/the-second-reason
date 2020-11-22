package com.mj;

public class Knapsack {
	public static void main(String[] args) {
		int[] values = {6, 3, 5, 4, 6};
		int[] weights = {2, 2, 6, 5, 4};
		int capacity = 10;
		System.out.println(maxValueExactly(values, weights, capacity));
	}
	
	/**
	 * @return 如果返回-1，代表没法刚好凑到capacity这个容量
	 */
	static int maxValueExactly(int[] values, int[] weights, int capacity) {
		if (values == null || values.length == 0) return 0;
		if (weights == null || weights.length == 0) return 0;
		if (values.length != weights.length || capacity <= 0) return 0;
		int[] dp = new int[capacity + 1];
		for (int j = 1; j <= capacity; j++) {
			dp[j] = Integer.MIN_VALUE;
		}
		for (int i = 1; i <= values.length; i++) {
			for (int j = capacity; j >= weights[i - 1]; j--) {
				dp[j] = Math.max(dp[j], values[i - 1] + dp[j - weights[i - 1]]);
			}
		}
		return dp[capacity] < 0 ? -1 : dp[capacity];
	}



	static int maxValue(int[] values, int[] weights, int capacity) {
		if (values == null || values.length == 0) return 0;
		if (weights == null || weights.length == 0) return 0;
		if (values.length != weights.length || capacity <= 0) return 0;

		int[] dp = new int[capacity + 1];
		for (int i = 1; i <= values.length; i++) {
			for (int j = capacity; j >= weights[i - 1]; j--) {
				dp[j] = Math.max(dp[j], values[i - 1] + dp[j - weights[i - 1]]);
			}
		}
		return dp[capacity];
	}
	//一维数组优化
	static int maxValue2(int[] values, int[] weights, int capacity) {
		if (values == null || values.length == 0) return 0;
		if (weights == null || weights.length == 0) return 0;
		if (values.length != weights.length || capacity <= 0) return 0;

		int[] dp = new int[capacity + 1];
		//更倾向于次数？一行一次！
		for (int i = 1; i <= values.length; i++) {
			for (int j = capacity; j >= 1; j--) {
				if (j < weights[i - 1]) continue;
				dp[j] = Math.max(dp[j], values[i - 1] + dp[j - weights[i - 1]]);
			}
		}
		return dp[capacity];
	}
	//传物品的价值，物品的重量，背包的容量！
	static int maxValue1(int[] values, int[] weights, int capacity) {
		//各种需要满足的条件
		if (values == null || values.length == 0) return 0;
		if (weights == null || weights.length == 0) return 0;
		if (values.length != weights.length || capacity <= 0) return 0;

		int[][] dp = new int[values.length + 1][capacity + 1];
		for (int i = 1; i <= values.length; i++) {
			for (int j = 1; j <= capacity; j++) {
				//背包的最大承重是小于最后一件物品的重量的,最后一件物品没法选
				if (j < weights[i - 1]) {
					dp[i][j] = dp[i - 1][j];
				} else {
					//取两种情况中最大的
					dp[i][j] = Math.max(dp[i - 1][j], values[i - 1] + dp[i - 1][j - weights[i - 1]]);
				}
			}
		}
		//有这么多物品可以选,最大容量是capacity
		return dp[values.length][capacity];
	}
}
