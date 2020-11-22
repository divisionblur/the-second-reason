package com.mj;

public class LIS {
	public static void main(String[] args) {
		System.out.println(lengthOfLIS(new int[] {10, 2, 2, 5, 1, 7, 101, 18}));
	}

	/**二分搜索优化
	 * 牌顶
	 */
	static int lengthOfLIS(int[] nums) {
		if (nums == null || nums.length == 0) return 0;
		// 牌堆的数量
		int len = 0;
		// 牌顶数组(存放每一个牌堆的牌顶)
		int[] top = new int[nums.length];
		// 遍历所有的牌
		for (int num : nums) {
			//左闭右开
			int begin = 0;
			int end = len;
			while (begin < end) {
				int mid = (begin + end) >> 1;
				if (num <= top[mid]) {
					end = mid;
				} else {
					begin = mid + 1;
				}
			}
			//begin = end
			//覆盖牌顶(不管返回什么位置都包含了)
			top[begin] = num;
			// 检查是否要新建一个牌堆
			if (begin == len) len++;
		}
		return len;
	}

	/**
	 * 牌顶
	 */
	static int lengthOfLIS2(int[] nums) {
		if (nums == null || nums.length == 0) return 0;
		// 牌堆的数量
		int len = 0;
		// 牌顶数组
		int[] top = new int[nums.length];
		// 遍历所有的牌
		for (int num : nums) {
			int j = 0;
			//当j的值等于len(此时牌堆的数量)
			while (j < len) {
				// 找到一个>=num的牌顶
				if (top[j] >= num) {
					top[j] = num;
					break;
				}
				//牌顶 < num
				//看一下下一个牌堆的牌顶
				j++;
			}
			// 新建一个牌堆
			if (j == len) {
				len++;
				//新建牌堆进行赋值
				top[j] = num;
			}
		}
		return len;
	}

	/**
	 * 动态规划
	 * 最长上升子序列的个数
	 */
	static int lengthOfLIS1(int[] nums) {
		if (nums == null || nums.length == 0) return 0;
		int[] dp = new int[nums.length];
		//以第一个数为结尾的最长上升子序列的个数就是自己为 1
		int max = dp[0] = 1;
		for (int i = 1; i < dp.length; i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++) {
				if (nums[i] <= nums[j]) continue;
				//nums[i] > nums[j]
				dp[i] = Math.max(dp[i],dp[j] + 1);
			}
			max = Math.max(dp[i], max);
		}
		return max;
	}
}
