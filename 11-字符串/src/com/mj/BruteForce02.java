package com.mj;import com.mj.tool.Times;

public class BruteForce02 {
	/**
	 * 蛮力匹配2
	 */
	public static int indexOf(String text, String pattern) {
		if (text == null || pattern == null) return -1;
		char[] textChars = text.toCharArray();
		int tlen = textChars.length;
		char[] patternChars = pattern.toCharArray();
		int plen = patternChars.length;
		if (tlen == 0 || plen == 0 || tlen < plen) return -1;
		// tiMax就是临界值
		int tiMax = tlen - plen;
		for (int ti = 0; ti <= tiMax; ti++) {
			// 把pi放在内层for循环外面，这样的话就能直接使用它。
			int pi = 0;
			for (; pi < plen; pi++) {
				if (textChars[ti + pi] != patternChars[pi]) break;
			}
			//能够来到这里说明for循环退出了，也许是break，一种是自然退出。
			if (pi == plen) return ti;
		}
		return -1;
	}
}
