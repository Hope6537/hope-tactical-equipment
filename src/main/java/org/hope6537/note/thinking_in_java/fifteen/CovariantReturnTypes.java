package org.hope6537.note.thinking_in_java.fifteen;

class Base{}

class Derived extends Base{}

interface OrdinaryGetter{
	//父类接口
	Base get();
}

interface DerivedGetter extends OrdinaryGetter{
	//子类接口
	//在继承父类接口方法的同时,覆盖了Get方法，同时返回类型也进行了覆盖，而不是重载
	//因为Base是Derived的父类？
	Derived get();
}

/**
 * @Describe 协变返回类型
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-20下午12:26:34
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class CovariantReturnTypes {
	void test(DerivedGetter d){
		Derived d2 = d.get();
		
	}
}
