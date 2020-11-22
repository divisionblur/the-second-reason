package com.mj;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		BloomFilter<Integer> bf = new BloomFilter<>(1_00_0000, 0.01);
//		for (int i = 1; i <= 1_00_0000; i++) {
//			bf.put(i);
//		}
//		
//		int count = 0;
//		for (int i = 1_00_0001; i <= 2_00_0000; i++) {
			//一旦返回true就说明产生了误判,count++
//			if (bf.contains(i)) {
//				count++;
//			}
//		}
//		System.out.println(count);
		
		// 数组
		String[] urls = {};
		BloomFilter<String> bf = new BloomFilter<String>(10_0000_0000, 0.01);
		/*
		//有些网址会漏掉,但是绝不会爬重复的网站
		for (String url : urls) {
			//说明已经爬过这个网站了
			if (bf.contains(url)) continue;
			// 爬这个url
			// ......
			
			// 放进BloomFilter中
			// 一旦有一个元素放进了布隆过滤器，对应的二进制位会被设置为1
			bf.put(url);
		}*/


		for (String url : urls) {
			if (bf.put(url) == false) continue;
			// 爬这个url
			// ......
		}
	}

}
