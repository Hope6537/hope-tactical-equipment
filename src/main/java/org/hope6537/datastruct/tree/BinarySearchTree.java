package org.hope6537.datastruct.tree;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * @Describe 二叉搜索树
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-3-6下午07:55:36
 * @version 0.9
 * @company Changchun University&SHXT
 * @param <T>
 */
public class BinarySearchTree<T extends Comparable<? super T>>{

	/**
	 * @Describe 树的节点的嵌入类
	 * @Author Hope6537(赵鹏)
	 * @Signdate 2014-3-6下午07:55:50
	 * @version 0.9
	 * @company Changchun University&SHXT
	 * @param <T>
	 */
	private static class BinaryNode<T>{
		
		private T element;
		private BinaryNode<T> left;
		private BinaryNode<T> right;
		
		
		public BinaryNode(T theElement , BinaryNode<T> l , BinaryNode<T> r) {
			this.element = theElement;
			this.left = l;
			this.right = r;
		}
		
		public BinaryNode(T theElement) {
			this(theElement , null , null);
		}
	}
	
	/**
	 * @Describe 根节点
	 */
	private BinaryNode<T> root;
	
	
	/**
	 * @Describe 构造方法 形成一个空的树
	 * @Author Hope6537(赵鹏)
	 * @param 
	 */
	public BinarySearchTree() {
		root = null;
	}
	
	/**
	 * @Descirbe 清空整个树
	 * @Author Hope6537(赵鹏)
	 * @Params 
	 * @SignDate 2014-3-6下午07:56:32
	 * @Version 0.9
	 */
	public void makeEmpty(){
		root = null;
	}
	
	/**
	 * @Descirbe 检查书是否为空
	 * @Author Hope6537(赵鹏)
	 * @Params @return
	 * @SignDate 2014-3-6下午07:56:59
	 * @Version 0.9
	 * @return
	 */
	public boolean isEmpty(){
		return root == null;
	}
	
	/**
	 * @Descirbe 检查自t后树中是否存在x元素
	 * @Author Hope6537(赵鹏)
	 * @Params @param x
	 * @Params @param t
	 * @Params @return
	 * @SignDate 2014-3-6下午07:59:28
	 * @Version 0.9
	 * @param x
	 * @param t
	 * @return
	 */
	private boolean contain(T x , BinaryNode<T> t){
		if(t==null){//如果树是空的，或者该树是树叶
			return false;//不匹配 找不到
		}
		int compareResult = x.compareTo(t.element);//这是x和该树元素字典对比的结果
		if(compareResult<0){//如果x在t的左边 比较大小小于零
			return contain(x, t.left);//递归查找从t树的左儿子
		}else if(compareResult>0){//反之从右
			return contain(x, t.right);
		}
		else{//如果相等 输出true 这是找到了
			return true;
		}
	}
	
	/**
	 * @Descirbe 找到自t下最小的元素
	 * @Author Hope6537(赵鹏)
	 * @Params @param t
	 * @Params @return
	 * @SignDate 2014-3-6下午08:41:32
	 * @Version 0.9
	 * @param t
	 * @return
	 */
	private BinaryNode<T> findMin(BinaryNode<T> t){
		if(t==null){
			return null;
		}
		else if(t.left==null){//如果它没有左儿子了 那么根据结构 他是最小的
			return t;
		}
		return findMin(t.left);//否则递归
	}
	
	/**
	 * @Descirbe 找到自t下最大的元素
	 * @Author Hope6537(赵鹏)
	 * @Params @param t
	 * @Params @return
	 * @SignDate 2014-3-6下午08:42:23
	 * @Version 0.9
	 * @param t
	 * @return
	 */
	private BinaryNode<T> findMax(BinaryNode<T> t){
		if(t!=null){
			while(t.right!=null){
				t = t.right;//循环找到没有右儿子的那个
			}
		}
		return t;
	}
	
	/**
	 * @Descirbe 在该t树下添加新节点 
	 * @Author Hope6537(赵鹏)
	 * @Params @param x
	 * @Params @param t
	 * @Params @return
	 * @SignDate 2014-3-6下午08:42:55
	 * @Version 0.9
	 * @param x
	 * @param t
	 * @return
	 */
	private BinaryNode<T> insert (T x , BinaryNode<T> t){
		
		if(t == null){ // 如果树是空的 那么这个就是根
			return new BinaryNode<T>(x);
		}
		int compareResult = x.compareTo(t.element);
		
		if(compareResult<0){
			t.left = insert(x, t.left);//因为比t的数据小 所以在左边
		}else if(compareResult>0){
			t.right = insert(x, t.right);//反正 大 在右边
		}
		else{
			;//两者重复 树中已经有相同数据
		}
		return t;
		
	}
	/**
	 * @Descirbe 从t树中删除x元素
	 * @Author Hope6537(赵鹏)
	 * @Params @param x
	 * @Params @param t
	 * @Params @return
	 * @SignDate 2014-3-6下午08:44:20
	 * @Version 0.9
	 * @param x
	 * @param t
	 * @return
	 */
	private BinaryNode<T> remove(T x , BinaryNode<T> t){
		if( t == null){
			//如果树是空的 那么直接回去
			return t;
		}
		int compareResult = x.compareTo(t.element);
		
		if(compareResult<0){
			t.left = remove(x, t.left);//说明要删除的在t的左儿子里
		}else if(compareResult>0){
			t.right = remove(x, t.right);//在右儿子里
		}else if(t.left!=null && t.right != null){
			//这里是最复杂的情况 如果它有左儿子 又有右儿子
			//那么 我们需要找出t的右儿子的最小的哪一个用于代替当前被删除的节点来担当原来的t的孩子们的父亲
			//因为他是仅仅大于t的数据的 所以他比t的其他右儿子都小
			//比其他左儿子们都大 所以先从t的右侧寻找
			t.element = findMin(t.right).element;
			//找到后 将下一任父亲的值带入进去 寻找他的原址并删除 最终我们会走到最后一个else
			//同时t的右侧会绕过新任父亲原来的位置 而直接找向t的父亲的单子节点
			//因为新任父亲的最多只会有一个右节点 所以我们就像是链表一样绕过新任父亲的原址就好了。
			t.right = remove(t.element, t.right);
		}
		else{
			//在这里是简单状况 如果t有左儿子 那么t = t的左儿子  如果没有 t = t的右儿子
			t = t.left!=null ? t.left : t.right;//重复
		}
		return t;
	}
	
	/**
	 * @Descirbe 打印二叉搜索树
	 * @Author Hope6537(赵鹏)
	 * @Params @param t
	 * @SignDate 2014-3-6下午08:53:15
	 * @Version 0.9
	 * @param t
	 */
	private void printTree(BinaryNode<T> t){
		if(t != null){
			printTree(t.left);
			System.out.print(" "+t.element+" ");
			printTree(t.right);
		}
	}
	
	/**
	 * @Descirbe 再t树下插入一个新的节点
	 * @Author Hope6537(赵鹏)
	 * @Params @param x
	 * @Params @return
	 * @SignDate 2014-3-6下午08:54:21
	 * @Version 0.9
	 * @param x
	 * @return
	 */
	public boolean contains(T x){
		return contain(x, root);
	}

	/**
	 * @Descirbe 找到二叉树中的最小项
	 * @Author Hope6537(赵鹏)
	 * @Params @return
	 * @SignDate 2014-3-6下午08:56:52
	 * @Version 0.9
	 * @return
	 */
	public T findMin(){
		if(isEmpty()){
			throw new UndeclaredThrowableException(null);
		}
		return findMin(root).element;
	}
	/**
	 * @Descirbe 找到二叉树中的最大项
	 * @Author Hope6537(赵鹏)
	 * @Params @return
	 * @SignDate 2014-3-6下午08:56:51
	 * @Version 0.9
	 * @return
	 */
	public T findMax(){
		if(isEmpty()){
			throw new UndeclaredThrowableException(null);
		}
		return findMax(root).element;
	}
	
	/**
	 * @Descirbe 从树根添加数据
	 * @Author Hope6537(赵鹏)
	 * @Params @param x
	 * @SignDate 2014-3-6下午08:58:47
	 * @Version 0.9
	 * @param x
	 */
	public void insert (T x){
		root = insert(x, root);
	}
	/**
	 * @Descirbe 从树根删除数据
	 * @Author Hope6537(赵鹏)
	 * @Params @param x
	 * @SignDate 2014-3-6下午08:58:46
	 * @Version 0.9
	 * @param x
	 */
	public void remove (T x){
		root = remove(x, root);
	}
	
	public void printTree(){
		if(isEmpty()){
			System.out.println("This Tree is Empty : 这是个空树 无数据 ");
		}
		else{
			printTree(root);
		}
	}
	
	public static void main(String[] args) {
		BinarySearchTree<Integer> t = new BinarySearchTree<Integer>();
		t.insert(0);
		for(int i = -99 ; i <= 99 ; i = i+1){
			t.insert(i);
		}
	
		t.printTree();
	}
}
