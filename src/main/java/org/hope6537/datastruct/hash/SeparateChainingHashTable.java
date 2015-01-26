package org.hope6537.datastruct.hash;

import java.util.LinkedList;
import java.util.List;

/**
 * @Describe 分离链接法散列表
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-4-3下午07:29:27
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class SeparateChainingHashTable<AnyType> {
	/**
	 * @Describe 表的初始长度
	 */
	private static final int DEFAULT_TABLE_SIZE = 100;

	/**
	 * @Describe 散列表是作为元素存在的链表的数组集合
	 */
	private List<AnyType>[] theLists;

	/**
	 * @Describe 当前散列表内元素的个数
	 */
	private int currentSize;

	/**
	 * @Descirbe 生成该单元元素的Hash值，用于存储
	 * @Author Hope6537(赵鹏)
	 * @Params @return
	 * @SignDate 2014-4-3下午07:31:59
	 * @Version 0.9
	 * @return
	 */
	private int myHash(AnyType x) {
		int hashVal = x.hashCode();
		hashVal %= theLists.length;
		if (hashVal < 0) {
			hashVal += theLists.length;
		}
		return hashVal;
	}

	/**
	 * @Descirbe 清空该散列表的算法
	 * @Author Hope6537(赵鹏)
	 * @Params
	 * @SignDate 2014-4-3下午07:35:41
	 * @Version 0.9
	 */
	public void makeEmpty() {
		for (int i = 0; i < theLists.length; i++) {
			theLists[i].clear();
		}
		currentSize = 0;
	}

	public SeparateChainingHashTable() {
		this(DEFAULT_TABLE_SIZE);
	}

	@SuppressWarnings("unchecked")
	public SeparateChainingHashTable(int size) {
		theLists = new LinkedList[nextPrime(size)];//生成一个链表数组
		for (int i = 0; i < theLists.length; i++) {
			theLists[i] = new LinkedList<AnyType>();//对每个链表进行初始化 
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
		if (n % 2 == 0) {//首先检测是否能被2整除，如果是的话就直接干+1
			n++;
		}

		for (; !isPrime(n); n += 2)//然后无限循环到 不是素数 否则的话就n+2递增
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
		if (n == 2 || n == 3){
			return true;
		}
		if (n == 1 || n % 2 == 0){
			return false;
		}
		//上两行太弱智

		for (int i = 3; i * i <= n; i += 2){//折半循环查找,即在公因子在sqrt n 之前如果存在的话，就可以节省一半时间
			if (n % i == 0){
				return false;
			}	
		}

		return true;
	}
	
	/**
	 * @Descirbe 检查该x元素在散列表中是否存在
	 * @Author Hope6537(赵鹏)
	 * @Params @param x
	 * @Params @return
	 * @SignDate 2014-4-3下午07:46:29
	 * @Version 0.9
	 * @param x
	 * @return
	 */
	public boolean contains(AnyType x){
		List<AnyType>whichList = theLists[myHash(x)];
		return whichList.contains(x);
	}
	/**
	 * @Descirbe 将x添加进散列表
	 * @Author Hope6537(赵鹏)
	 * @Params @param x
	 * @SignDate 2014-4-3下午07:48:48
	 * @Version 0.9
	 * @param x
	 */
	public void insert(AnyType x){
		List<AnyType> whichList = theLists[myHash(x)];
		if(!whichList.contains(x)){
			whichList.add(x);
		}
		if(++currentSize>theLists.length){
			rehash();
		}
		
	}
	
	/**
	 * @Descirbe 散列表在空间不够时重新构造并hash化
	 * @Author Hope6537(赵鹏)
	 * @Params 
	 * @SignDate 2014-4-3下午07:50:55
	 * @Version 0.9
	 */
	@SuppressWarnings("unchecked")
	private void rehash() {
		List<AnyType> [] oldLists = theLists;
		theLists = new List[nextPrime(2*theLists.length)];
		for(int j = 0 ; j < theLists.length ; j++ ){
			theLists[j] = new LinkedList<AnyType>();
		}
		currentSize = 0;
		
		for(int i = 0 ; i < oldLists.length ; i++){
			for(AnyType x : oldLists[i]){
				insert(x);
			}
		}
	}
	
	/**
	 * @Descirbe 在散列表中删除掉x
	 * @Author Hope6537(赵鹏)
	 * @Params @param x
	 * @SignDate 2014-4-3下午07:51:41
	 * @Version 0.9
	 * @param x
	 */
	public void remove(AnyType x){
		List<AnyType> whichList = theLists[myHash(x)];
		if(!whichList.contains(x)){
			whichList.remove(x);
			currentSize--;
		}
	}
}
