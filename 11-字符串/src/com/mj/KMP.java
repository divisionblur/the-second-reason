package com.mj;
public class KMP {
	public static int indexOf(String text, String pattern) {
		// 检测数据合法性
		if (text == null || pattern == null) return -1;
		char[] textChars = text.toCharArray();
		int tlen = textChars.length;
		char[] patternChars = pattern.toCharArray();
		int plen = patternChars.length;
		if (tlen == 0 || plen == 0 || tlen < plen) return -1;
		
		// next表
		int[] next = next(pattern);
		//在第一版蛮力算法的基础上考虑上next数组
		int pi = 0, ti = 0;
		while (pi < plen && ti < tlen) {
			// next表置-1的精妙之处, pi = -1 则 pi = 0, ti++ 相当于模式串后移一位。
			// 当pi < 0 时或者匹配成功
			if (pi < 0 || textChars[ti] == patternChars[pi]) {
				ti++;
				pi++;
			} else {
				pi = next[pi];
			}
		} 
		return pi == plen ? ti - pi : -1;
	}
	
	/**
	 * next 表构造 - 优化
	 */
	private static int[] next(String pattern) {
		char[] chars = pattern.toCharArray();
		int[] next = new int [chars.length];
		next[0] = -1;
		int i = 0;
		int n = -1;
		int iMax = chars.length - 1;
		while (i < iMax) {
			if (n < 0 || chars[i] == chars[n]) {
				++i;
				++n;
				if (chars[i] == chars[n]) {
					//直接把你的next赋值给我，因为char[i]匹配失败那么char[n]也必然匹配失败那么不如直接把你匹配失败
					//要移动到的位置给我，那么我就直接移动过去，而不用做过多的比较。
					next[i] = next[n];
				} else {//如果不相等就照旧
					next[i] = n;
				}
			} else {
				n = next[n];
			}
		}
		return next;
	}
	
	/**
	 * next表构造
	 */
	private static int[] next1(String pattern) {
		char[] chars = pattern.toCharArray();
		//next数组的长度其实就是pattern模式串的长度！
		int[] next = new int [chars.length];
		next[0] = -1;
		int i = 0;
		int n = -1;
		int iMax = chars.length - 1;
		while (i < iMax) {
			if (n < 0 || chars[i] == chars[n]) {
				next[++i] = ++n;
			} else {
				//以前时i和n进行比较,现在n和next[n]进行比较。
				n = next[n];
			}
		}
		return next;
	}
}