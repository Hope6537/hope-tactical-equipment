package org.hope6537.datastruct.tree;

/**
 * @Describe AVL平衡树的一般例程
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-3-27上午08:40:15
 * @version 0.9
 * @company Changchun University&SHXT
 * @param <AnyType>
 */
public class AvlTree<AnyType extends Comparable<? super AnyType>> {

	public static class AvlNode<AnyType>{
		
		AnyType element;
		AvlNode<AnyType> left;
		AvlNode<AnyType> right;
		int height;
		
		public AvlNode(AnyType x) {
			this(x,null,null);
		}
		public AvlNode(AnyType element,AvlNode<AnyType> left, AvlNode<AnyType> right){
			this.element = element;
			this.left = left;
			this.right = right;
			this.height = 0;
		}
	}
	
	private int height(AvlNode<AnyType> t){
		return t==null?-1:t.height;
	}
	
	private AvlNode<AnyType> insert(AnyType x,AvlNode<AnyType> t){
		if(t==null){
			return new AvlNode<AnyType>(x);
		}
		int compareResult = compare(x,t.element);
		
		if(compareResult<0){
			t.left = insert(x, t.left);
			if(height(t.left) - height(t.right) == 2){
				if(compare(x, t.left.element)<0){
					t = rotateWithLeftChild(t);
				}
				else{
					t = doubleWithLeftChild(t);
				}
			}
		}
		else if(compareResult>0){
			t.right = insert(x, t.right);
			if(height(t.left) - height(t.right) == -2){
				if(compare(x, t.right.element)<0){
					t = rotateWithRightChild(t);
				}
				else{
					t = doubleWithRightChild(t);
				}
			}
		}
		else{
			;
		}
		t.height = Math.max(height(t.left), height(t.right))+1;
		return t;
	}

	/**
	 * @Descirbe 双旋转和之前的对称
	 * @Author Hope6537(赵鹏)
	 * @Params @param t
	 * @Params @return
	 * @SignDate 2014-3-27上午08:39:57
	 * @Version 0.9
	 * @param t
	 * @return
	 */
	private AvlNode<AnyType> doubleWithRightChild(AvlNode<AnyType> t) {
		t.right = rotateWithRightChild(t.right);
		return rotateWithRightChild(t);
	}

	/**
	 * @Descirbe 单旋转 X在节点的右侧 
	 * @Author Hope6537(赵鹏)
	 * @Params @param k2
	 * @Params @return
	 * @SignDate 2014-3-27上午08:39:06
	 * @Version 0.9
	 * @param k2
	 * @return
	 */
	private AvlNode<AnyType> rotateWithRightChild(AvlNode<AnyType> k2) {
		AvlNode<AnyType> k1 = k2.right;
		k2.right = k1.left;
		k1.left= k2;
		//求出新的高度并返回 
		k2.height = Math.max(height(k2.left), height(k2.right));
		k1.height = Math.max(height(k1.left), height(k1.right));
		return k1;
	}

	/**
	 * @Descirbe 执行双旋转的例程
	 * @Author Hope6537(赵鹏)
	 * @Params @param t
	 * @Params @return
	 * @SignDate 2014-3-27上午08:32:40
	 * @Version 0.9
	 * @param t
	 * @return
	 */
	private AvlNode<AnyType> doubleWithLeftChild(AvlNode<AnyType> t) {
		t.left = rotateWithLeftChild(t.left);
		return rotateWithLeftChild(t);
	}

	/**
	 * @Descirbe 执行单旋转的例程
	 * @Author Hope6537(赵鹏)
	 * @Params @param k2 原来的根节点
	 * @Params @return
	 * @SignDate 2014-3-27上午08:28:38
	 * @Version 0.9
	 * @param k2
	 * @return
	 */
	private AvlNode<AnyType> rotateWithLeftChild(AvlNode<AnyType> k2) {
		//将k2的左子树赋给变量k1 k1为该子树的根节点，同时也作为新树的根节点，即k1的节点深度大于右侧
		AvlNode<AnyType> k1 = k2.left;
		//将k1的右节点付给k2，即将k1的右侧的不影响平衡的Y放在k2的左侧，
		k2.left = k1.right;
		//将k2赋给k1的右侧作为右子树 此时k1的左右两侧的高度相等 k1的左子书高度为2  而右子树为k2+k1.right
		k1.right= k2;
		//求出新的高度并返回 
		k2.height = Math.max(height(k2.left), height(k2.right));
		k1.height = Math.max(height(k1.left), height(k1.right));
		return k1;
	}

	private int compare(AnyType x, AnyType element) {
		
		return x.compareTo(element);
	}
	
}
