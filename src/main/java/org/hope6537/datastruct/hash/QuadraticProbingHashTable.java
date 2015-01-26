package org.hope6537.datastruct.hash;

/**
 * @Describe 平方探测散列表
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-4-5下午12:07:37
 * @version 0.9
 * @company Changchun University&SHXT
 * @param <AnyType>
 */
public class QuadraticProbingHashTable<AnyType> {

	/**
	 * @Describe 散列表的节点元素
	 * @Author Hope6537(赵鹏)
	 * @Signdate 2014-4-5下午12:44:09
	 * @version 0.9
	 * @company Changchun University&SHXT
	 * @param <AnyType>
	 */
	private static class HashEntry<AnyType> {
		// 数据
		public AnyType element;
		// 是否可用
		public boolean isActive;

		// 构造方法
		public HashEntry(AnyType x) {
			this(x, true);
		}

		public HashEntry(AnyType x, boolean isActive) {
			this.element = x;
			this.isActive = isActive;
		}
	}

	/**
	 * @Describe 默认长度
	 */
	private static final int DEFAULT_TABLE_SIZE = 11;

	/**
	 * @Describe 装载节点的容器集合
	 */
	private HashEntry<AnyType>[] array;

	/**
	 * @Describe 当前节点的数量
	 */
	private int currentSize;

	/**
	 * @Describe 默认构造方法
	 * @Author Hope6537(赵鹏)
	 * @param
	 */
	public QuadraticProbingHashTable() {
		this(DEFAULT_TABLE_SIZE);
	}

	/**
	 * @Describe 有数组大小参数的构造方法
	 * @Author Hope6537(赵鹏)
	 * @param @param size
	 */
	public QuadraticProbingHashTable(int size) {
		// 创建HashEntry节点数组并依次初始化
		alocateArray(size);
		// 同时清空数据
		makeEmpty();
	}

	/**
	 * @Descirbe 清空散列表的数据
	 * @Author Hope6537(赵鹏)
	 * @Params
	 * @SignDate 2014-4-5下午12:45:57
	 * @Version 0.9
	 */
	public void makeEmpty() {
		for (int i = 0; i < array.length; i++) {
			array[i] = null;
		}
		currentSize = 0;
	}

	/**
	 * @Descirbe 初始化容器数组
	 * @Author Hope6537(赵鹏)
	 * @Params @param arraySize
	 * @SignDate 2014-4-5下午12:46:09
	 * @Version 0.9
	 * @param arraySize
	 */
	@SuppressWarnings("unchecked")
	private void alocateArray(int arraySize) {
		array = new HashEntry[arraySize];
	}

	/**
	 * @Descirbe 返回当前pos索引下元素的可用性
	 * @Author Hope6537(赵鹏)
	 * @Params @param pos
	 * @Params @return
	 * @SignDate 2014-4-5下午12:46:19
	 * @Version 0.9
	 * @param pos
	 * @return
	 */
	private boolean isActive(int pos) {
		// 不为空同时属性值为true 说明可用
		return array[pos] != null && array[pos].isActive;

	}

	/**
	 * @Descirbe 找到x元素所平方探测法分配的位置
	 * @Author Hope6537(赵鹏)
	 * @Params @param x
	 * @Params @return
	 * @SignDate 2014-4-5下午12:46:49
	 * @Version 0.9
	 * @param x
	 * @return
	 */
	private int findPos(AnyType x) {
		int offset = 1; // 默认初始位置
		int pos = myHash(x); // 首先进行散列化
		while (array[pos] != null && !array[pos].element.equals(x)) {// 如果当前pos索引存在可用元素
																		// 同时数值和x不同
			pos = pos + offset; // 那么依次将pos后移
			offset += 2; // 同时将参数逐渐+2 以相加 直到寻找到空点再跳出循环
			if (pos >= array.length) {// 如果越界 那么做MOD运算
				pos -= array.length;
			}
		}
		return pos;// 返回最终散列位置
	}

	/**
	 * @Descirbe Hash函数
	 * @Author Hope6537(赵鹏)
	 * @Params @param x
	 * @Params @return
	 * @SignDate 2014-4-5下午12:47:13
	 * @Version 0.9
	 * @param x
	 * @return
	 */
	private int myHash(AnyType x) {
		int hashVal = x.hashCode();

		hashVal %= array.length;
		if (hashVal < 0) {
			hashVal += array.length;
		}
		return hashVal;
	}

	/**
	 * @Descirbe 寻找x元素是否在散列表中存在
	 * @Author Hope6537(赵鹏)
	 * @Params @param x
	 * @Params @return
	 * @SignDate 2014-4-5下午12:47:22
	 * @Version 0.9
	 * @param x
	 * @return
	 */
	public boolean contains(AnyType x) {
		int pos = findPos(x);
		return isActive(pos);
	}

	/**
	 * @Descirbe 插入元素
	 * @Author Hope6537(赵鹏)
	 * @Params @param x
	 * @SignDate 2014-4-5下午12:54:13
	 * @Version 0.9
	 * @param x
	 */
	public void insert(AnyType x) {
		int pos = findPos(x); // 找到索引pos位置
		if (isActive(pos)) {// 如果存在相同元素 直接跳转
			return;
		}
		array[pos] = new HashEntry<AnyType>(x); // 插入
		if (++currentSize > array.length / 2) {// 如果 λ>0.5 则重排
			rehash();
		}
	}

	/**
	 * @Descirbe 在添加因子系数大于0.5时 重排
	 * @Author Hope6537(赵鹏)
	 * @Params
	 * @SignDate 2014-4-5下午12:59:13
	 * @Version 0.9
	 */
	private void rehash() {
		HashEntry<AnyType>[] oldLists = array;
		alocateArray(nextPrime(2 * oldLists.length));
		currentSize = 0;
		for (int i = 0; i < oldLists.length; i++) {
			if (oldLists[i] != null && oldLists[i].isActive) {
				insert(oldLists[i].element);
			}
		}
	}

	/**
	 * @Descirbe 得到n之后的下一个素数
	 * @Author Hope6537(赵鹏)
	 * @Params @param n
	 * @Params @return
	 * @SignDate 2014-4-3下午07:42:07
	 * @Version 0.9
	 * @param n
	 * @return
	 */
	private static int nextPrime(int n) {
		if (n % 2 == 0) {// 首先检测是否能被2整除，如果是的话就直接干+1
			n++;
		}

		for (; !isPrime(n); n += 2)
			// 然后无限循环到 不是素数 否则的话就n+2递增
			;

		return n;
	}

	/**
	 * @Descirbe 检查是否是素数
	 * @Author Hope6537(赵鹏)
	 * @Params @param n
	 * @Params @return
	 * @SignDate 2014-4-3下午07:42:18
	 * @Version 0.9
	 * @param n
	 * @return
	 */
	private static boolean isPrime(int n) {
		if (n == 2 || n == 3) {
			return true;
		}
		if (n == 1 || n % 2 == 0) {
			return false;
		}
		// 上两行太弱智

		for (int i = 3; i * i <= n; i += 2) {// 折半循环查找,即在公因子在sqrt n
												// 之前如果存在的话，就可以节省一半时间
			if (n % i == 0) {
				return false;
			}
		}

		return true;
	}

}
