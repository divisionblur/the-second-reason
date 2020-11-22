package com.mj;
import java.util.Comparator;
@SuppressWarnings("unchecked")
public class SkipList<K, V> {
	private static final int MAX_LEVEL = 32;
	private static final double P = 0.25;
	private int size;
	private Comparator<K> comparator;
	/**
	 * 有效层数
	 */
	private int level;
	/**
	 * 首节点不存放任何K-V的一个虚拟节点。
	 */
	private Node<K, V> first;
	
	public SkipList(Comparator<K> comparator) {
		this.comparator = comparator;
		first = new Node<>(null, null, MAX_LEVEL);
	}
	//没有传比较器的构造器
	public SkipList() {
		this(null);
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public V get(K key) {
		keyCheck(key);
		// first.nexts[3] == 21节点
		// first.nexts[2] == 9节点
		// first.nexts[1] == 6节点
		// first.nexts[0] == 3节点
		
		// key = 30
		// level = 4
		Node<K, V> node = first;
		for (int i = level - 1; i >= 0; i--) {
			int cmp = -1;
			while (node.nexts[i] != null && (cmp = compare(key, node.nexts[i].key)) > 0) {
				node = node.nexts[i];
			}
			// node.nexts[i].key >= key  等于的话cmp = 0
			// 把node.nexts[i] = null 的情况看作是比
			if (cmp == 0) {
				return node.nexts[i].value;
			}
		}
		return null;
	}
	
	public V put(K key, V value) {
		keyCheck(key);
		Node<K, V> node = first;
		//prevs数组用来存储前驱节点们，要把每一层的前驱都存起来！
		Node<K, V>[] prevs = new Node[level];
		for (int i = level - 1; i >= 0; i--) {
			int cmp = -1;
			while (node.nexts[i] != null && (cmp = compare(key, node.nexts[i].key)) > 0) {
				node = node.nexts[i];
			}
			if (cmp == 0) { // 节点是存在的
				//先把原来的value保存一下
				V oldV = node.nexts[i].value;
				//覆盖value
				node.nexts[i].value = value;
				//返回原来的value
				return oldV;
			}
			//来到这里说明即将执行下一次for循环，就说明要往下挪一层了。
			//而往下挪一层又说明这个节点就是要添加节点的一个前驱节点
			prevs[i] = node;
		}
		
		// 新节点的层数
		int newLevel = randomLevel();
		// 添加新节点
		Node<K, V> newNode = new Node<>(key, value, newLevel);
		// 设置前驱和后继
		for (int i = 0; i < newLevel; i++) {
			if (i >= level) {
				first.nexts[i] = newNode;
			} else {
				//这行代码就把新节点指向了这个新节点的前驱节点们以前指向的节点。
				newNode.nexts[i] = prevs[i].nexts[i];
				prevs[i].nexts[i] = newNode;
			}
		}
		// 节点数量增加
		size++;
		// 计算跳表的最终层数
		level = Math.max(level, newLevel);
		return null;
	}
	
	public V remove(K key) {
		keyCheck(key);
		Node<K, V> node = first;
		Node<K, V>[] prevs = new Node[level];
		boolean exist = false;
		for (int i = level - 1; i >= 0; i--) {
			int cmp = -1;
			while (node.nexts[i] != null 
					&& (cmp = compare(key, node.nexts[i].key)) > 0) {
				node = node.nexts[i];
			}
			prevs[i] = node;
			if (cmp == 0) exist = true;
		}
		//如果不存在的话就没有删除操作的必要！
		if (!exist) {
			return null;
		}
		
		// 需要被删除的节点
		Node<K, V> removedNode = node.nexts[0];
		
		// 数量减少
		size--;
		
		// 设置后继
		for (int i = 0; i < removedNode.nexts.length; i++) {
			//设置被删除节点的前驱的后继为被删除节点的后继。
			prevs[i].nexts[i] = removedNode.nexts[i];
		}
		
		// 更新跳表的层数
		int newLevel = level;
		//直接减一层，看这一层是否为空
		while (--newLevel >= 0 && first.nexts[newLevel] == null) {
			//这一层没有东西
			level = newLevel;
		}
		
		return removedNode.value;
	}
	
	private int randomLevel() {
		int level = 1;
		while (Math.random() < P && level < MAX_LEVEL) {
			level++;
		}
		return level;
	}
	
	private void keyCheck(K key) {
		if (key == null) {
			throw new IllegalArgumentException("key must not be null.");
		}
	}
	
	private int compare(K k1, K k2) {
		//如果传了比较器，就按照比较器的规则比
		return comparator != null ? comparator.compare(k1, k2) : ((Comparable<K>)k1).compareTo(k2);
	}
	
	private static class Node<K, V> {
		K key;
		V value;
		Node<K, V>[] nexts;
//		Node<K, V> right;
//		Node<K, V> down;
//		Node<K, V> top;
//		Node<K, V> left;
		public Node(K key, V value, int level) {
			this.key = key;
			this.value = value;
			nexts = new Node[level];
		}
		@Override
		public String toString() {
			return key + ":" + value + "_" + nexts.length;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("一共" + level + "层").append("\n");
		for (int i = level - 1; i >= 0; i--) {
			Node<K, V> node = first;
			while (node.nexts[i] != null) {
				sb.append(node.nexts[i]);
				sb.append(" ");
				node = node.nexts[i];
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
