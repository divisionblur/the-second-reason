package com.mj;

public class CoinChange {

	public static void main(String[] args) {
		System.out.println(coins5(41, new int[] {1, 5, 25, 20}));
		// fib(40)
		
		// dp(i) 第i项斐波那契数
		// dp(i) = dp(i - 1) + dp(i - 2)
		
		// dp(41) = 凑够41需要的最少硬币数量 = min { dp(40), dp(36), dp(16), dp(21) } + 1 这些值还是子问题的最优解
		// dp(41 - 1) = dp(40) = 凑够40需要的最少硬币数量
		// dp(41 - 5) = dp(36) = 凑够36需要的最少硬币数量
		// dp(41 - 25) = dp(16) = 凑够16需要的最少硬币数量
		// dp(41 - 20) = dp(21) = 凑够21需要的最少硬币数量
		// min { dp(40), dp(36), dp(16), dp(21) } + 1
	}
	//别人传硬币面值进来
	static int coins5(int n, int[] faces) {
		if (n < 1 || faces == null || faces.length == 0) return -1;
		int[] dp = new int[n + 1];
		for (int i = 1; i <= n; i++) {		//5,20,25
			int min = Integer.MAX_VALUE;
			for (int face : faces) {
				//比持有的钱的面值还小,这张钱是没法凑的。
				if (i < face) continue;
				int v = dp[i - face];
				if (v < 0 || v >= min) continue;
				min = v;
			}
			//进入for循环一直都是continue,比所有的面值都要小。
			if (min == Integer.MAX_VALUE) {
				dp[i] = -1;
			} else {
				dp[i] = min + 1;
			}
		}
		return dp[n];
	}

	static int coins4(int n) {
		if (n < 1) return -1;
		int[] dp = new int[n + 1];
		// faces[i]是凑够i分时最后那枚硬币的面值
		int[] faces = new int[dp.length];
		for (int i = 1; i <= n; i++) {
			int min = dp[i - 1];
			faces[i] = 1;

			if (i >= 5 && dp[i - 5] < min) {
				min = dp[i - 5];
				faces[i] = 5;
			}
			if (i >= 20 && dp[i - 20] < min) {
				min = dp[i - 20];
				faces[i] = 20;
			}
			if (i >= 25 && dp[i - 25] < min) {
				min = dp[i - 25];
				faces[i] = 25;
			}
			dp[i] = min + 1;
			print(faces, i);
		}
//		print(faces, n);
		return dp[n];
	}
	
	static void print(int[] faces, int n) {
		System.out.print("[" + n + "] = ");
		while (n > 0) {
			System.out.print(faces[n] + " ");
			n -= faces[n];
		}
		System.out.println();
	}
	
	/**
	 * 递推（自底向上）
	 */
	static int coins3(int n) {
		if (n < 1) return -1;
		//数组是n+1的话才能将钱与数组下标一一对应
		int[] dp = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			int min = dp[i - 1];
			if (i >= 5) min = Math.min(dp[i - 5], min);
			if (i >= 20) min = Math.min(dp[i - 20], min);
			if (i >= 25) min = Math.min(dp[i - 25], min);
			//凑够i分钱最少需要多少个硬币
			dp[i] = min + 1;
		}
		return dp[n];
	}
	
	/**
	 * 记忆化搜索（自顶向下的调用）(把曾经求解过的值保存起来)
	 */
	static int coins2(int n) {
		if (n < 1) return -1;
		//为了要让求解的钱和数组下标一一对应
		//dp这个数组的下标就是要求解的值,而里面的值是需要的最少个数。
		int[] dp = new int[n + 1];
		//所拥有的钱的面额
		int[] faces = {1, 5, 20, 25};
		for (int face : faces) {
			//意思是当n小于某一个面值的话就根本用不上这张面值,就不用初始化它。
			if (n < face) break;
			dp[face] = 1;
		}
		return coins2(n, dp);
	}

	//dp数组是记忆集
	static int coins2(int n, int[] dp) {
		if (n < 1) return Integer.MAX_VALUE;
		if (dp[n] == 0) {
			int min1 = Math.min(coins2(n - 25, dp), coins2(n - 20, dp));
			int min2 = Math.min(coins2(n - 5, dp), coins2(n - 1, dp));
			//把搜索过的值存起来
			dp[n] = Math.min(min1, min2) + 1;
		}
		return dp[n];
	}
	
	/**
	 * 暴力递归（自顶向下的调用，出现了重叠子问题）
	 */
	static int coins1(int n) {
		if (n < 1) return Integer.MAX_VALUE;
		if (n == 25 || n == 20 || n == 5 || n == 1) return 1;
		int min1 = Math.min(coins1(n - 25), coins1(n - 20));
		int min2 = Math.min(coins1(n - 5), coins1(n - 1));
		return Math.min(min1, min2) + 1;
	}
}
