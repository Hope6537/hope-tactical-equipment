package org.hope6537.utils;

/**
 * @Describe 键值对模型
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-4-30下午05:45:47
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class NameValuePair {

	private String name;
	private Object value;

	public NameValuePair() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "NameValuePair [name=" + name + ", value=" + value + "]";
	}

	public NameValuePair(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
