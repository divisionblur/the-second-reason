package com.mj;

public class BloomFilter<T> {
	/**
	 * 二进制向量的长度(一共有多少个二进制位)
	 */
	private int bitSize;
	/**
	 * 二进制向量
	 * 一个数组位置64个二进制位
	 */
	private long[] bits;
	/**
	 * 哈希函数的个数
	 */
	private int hashSize;
	
	/**
	 * @param n 数据规模
	 * @param p 误判率, 取值范围(0, 1)
	 */
	public BloomFilter(int n, double p) {
		//条件判断
		if (n <= 0 || p <= 0 || p >= 1) {
			throw new IllegalArgumentException("wrong n or p");
		}
		
		double ln2 = Math.log(2);
		// 求出二进制向量的长度
		bitSize = (int) (- (n * Math.log(p)) / (ln2 * ln2));
		// 求出哈希函数的个数
		hashSize = (int) (bitSize * ln2 / n);
		// bits数组的长度
		bits = new long[(bitSize + Long.SIZE - 1) / Long.SIZE];
		// 每一页显示100条数据, pageSize
		// 一共有999999条数据, 数据规模n
		// 请问有多少页 pageCount = (n + pageSize - 1) / pageSize
	}
	
	/**
	 * 添加元素1
	 */
	public boolean put(T value) {
		nullCheck(value);

		// 利用value生成2个整数
		int hash1 = value.hashCode();
		//无符号右移16位
		int hash2 = hash1 >>> 16;
	
		boolean result = false;
		//hash函数有多少个就遍历多少遍
		for (int i = 1; i <= hashSize; i++) {
			int combinedHash = hash1 + (i * hash2);
			if (combinedHash < 0) {
				combinedHash = ~combinedHash;
			} 
			// 生成一个二进位的索引
			// 能得到某一个二进制位的索引
			int index = combinedHash % bitSize;
			// 判断是否设置了index位置的二进位为1
			if (set(index)) result = true;
			//   101010101010010101
			// | 000000000000000100   1 << index
			//   101010111010010101
		}
		return result;
	}
	
	/**
	 * 判断一个元素是否存在
	 */
	public boolean contains(T value) {
		nullCheck(value);
		// 利用value生成2个整数
		int hash1 = value.hashCode();
		int hash2 = hash1 >>> 16;
	
		for (int i = 1; i <= hashSize; i++) {
			int combinedHash = hash1 + (i * hash2);
			if (combinedHash < 0) {
				combinedHash = ~combinedHash;
			} 
			// 生成一个二进位的索引
			int index = combinedHash % bitSize;
			// 查询index位置的二进位是否为0
			// 一旦发现有一个二进制位0则要查询的元素必然不存在就返回false
			if (!get(index)) return false;
		}
		return true;
	}
	
	/**
	 * 设置index位置的二进位为1
	 */
	private boolean set(int index) {
		//index / Long.SIZE找到在哪个第几个long里面(从左边开始)
		long value = bits[index / Long.SIZE];
		//内部从右边过来
		int bitValue = 1 << (index % Long.SIZE);
		bits[index / Long.SIZE] = value | bitValue;//按位或
		//如果是返回true的话说明此次操作修改了二进制位(0>>>>1)
		return (value & bitValue) == 0;
	}
	
	/**
	 * 查看index位置的二进位的值
	 * @return true代表1, false代表0
	 */
	private boolean get(int index) {
		long value = bits[index / Long.SIZE];
		return (value & (1 << (index % Long.SIZE))) != 0;
	}
	
	private void nullCheck(T value) {
		if (value == null) {
			throw new IllegalArgumentException("Value must not be null.");
		}
	}
}
