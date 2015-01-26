package org.hope6537.datastruct.list;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @Describe 自己的ArrayList类
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-3-3下午09:09:08
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class MyArrayList implements Iterable<Serializable> {

	
	private static final int DEFAULT_CAPACITY = 10; //默认容量为10
	
	private int theSize; // 现在的大小
	
	private Serializable [] theItems; // 数据 即 data
	
	public MyArrayList() {
		clear();	//构造方法直接清空当前被调用对象,返回出来的时候是使用的无元素体
	}
	
	
	public void clear(){
		theSize = 0; //现在的大小数值清零
		ensureCapacity(DEFAULT_CAPACITY);//因为循环是从0循环到0 所以不存在数据了
	}
	
	public int size(){
		return theSize; //返回现在的大小的数值
	}
	
	public boolean isEmpty(){
		return size() == 0;//现在的大小的数值是否为零
	}
	
	public void trimToSize(){
		//将数据结构格式化到参数大小
		ensureCapacity(size());
	}

	
	public Serializable get(int index){
		if(index<0 || index>= size()){
			throw new ArrayIndexOutOfBoundsException();
		}
		return theItems[index];
	}
	
	public Serializable set(int index , Serializable newValue){//替换改下标下的数据data
		if(index<0 || index>= size()){
			throw new ArrayIndexOutOfBoundsException();
		}
		Serializable old = theItems[index];
		theItems[index] = newValue;
		return old;
	} 
	
	public void ensureCapacity(int newCapacity){ //这是调整大小并复制MyArrayList的内容
		if(newCapacity < theSize){
			return; //如果现在的数据大小大于参数给的预备数值 那么我们就不受理
		}
		Serializable [] old = theItems; //将原来的数据备份
		theItems = (Serializable [])new Object[newCapacity]; //生命数组
		for(int i = 0 ; i<size();i++){
			//然后循环迭代这里面的数据data
			theItems[i] = old[i];
		}
	}
	
	public void add(int index , Serializable x){// 从索引下表处插入一个元素x
		if(theItems.length == size()){
			//如果数组的长度已经等于size大小 那就是说不够用了
			ensureCapacity(size()*2+1);
			//所以我们要调用上面的方法来扩容
			//注意 我们扩大的大小是数组的大小，不是数据结构的。
		}
		
		for(int i = theSize ; i> index ; i--){
		//接下来就是讲从索引处到最后的元素依次后串一位，用来装新数据
			theItems[i] = theItems[i-1];
		}
		//新数据装入
		theItems[index] = x;
		//同时size的长度+1
		theSize++;
	}
	
	public boolean add(Serializable x){//直接在队尾插入。
		add(size(),x);
		return true;
	}
	
	public Serializable remove(int index){
		//删除索引指定下标的元素
		Serializable removedItem = theItems[index];
		for(int i = index ; i < size() -1 ; i++){
			//将元素前移添补空白
			theItems[i] = theItems[i+1];
		}
		theSize--;
		return removedItem;
	}
	
	public Iterator<Serializable> iterator(){//获取自己的迭代器
		return new ArrayListIterator(this);
	}
	
	public Iterator<Serializable> iterator_finish(){//获取自己的迭代器2
		return new ArrayListIterator_Finish();
	}
	
	/**
	 * @Describe 迭代器嵌套类 有static 第三修改版
	 * @Author Hope6537(赵鹏)
	 * @Signdate 2014-3-3下午10:24:38
	 * @version 0.9
	 * @company Changchun University&SHXT
	 */
	private static class ArrayListIterator implements Iterator<Serializable>{
		//自编迭代器内部类
		
		private int current = 0;//迭代器当前索引
		
		private MyArrayList theList;
		
		public ArrayListIterator(MyArrayList list){
			theList = list;
		}
		
		public boolean hasNext(){//是否有下一个 通过当前索引和size的大小进行比较判断
			return current<theList.size();
		}
		@Override
		public Serializable next() {
			//输出下一个 
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			return theList.theItems[current++];
		}
		@Override
		public void remove() {
			//将ArrayList的remove方法调用,加上this是为了确保使用的是当前对象
			theList.remove(--current);
		}
		
		
	}
	
	/**
	 * @Describe 将它更改为一个隐式的泛型类，他现在依赖于外部类，而外部类是泛型的。
	 * @Author Hope6537(赵鹏)
	 * @Signdate 2014-3-3下午10:31:06
	 * @version 0.9
	 * @company Changchun University&SHXT
	 */
	private class ArrayListIterator_Finish implements Iterator<Serializable>{
		
		
		private int current = 0;//迭代器当前索引
		
		
		public boolean hasNext(){//是否有下一个 通过当前索引和size的大小进行比较判断
			return current<size();
		}
		@Override
		public Serializable next() {
			//输出下一个 
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			return theItems[current++];
		}
		@Override
		public void remove() {
			//将ArrayList的remove方法调用,加上this是为了确保使用的是当前对象
			MyArrayList.this.remove(--current);
		}
		
		
	}
	
	
}
