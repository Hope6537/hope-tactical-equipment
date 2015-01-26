package org.hope6537.datastruct.hash;

public class HashFunction {

	/**
	 * @Descirbe 一种好的Hash生成方式
	 * @Author Hope6537(赵鹏)
	 * @Params @param key
	 * @Params @param tableSize
	 * @Params @return
	 * @SignDate 2014-4-3下午07:28:14
	 * @Version 0.9
	 * @param key
	 * @param tableSize
	 * @return
	 */
	public static int hash(String key,int tableSize){
		int hashVal = 0;
		for(int i = 0 ; i<key.length();i++){
			hashVal = 37*hashVal + key.charAt(i);
		}
		hashVal %= tableSize;
		if(hashVal<0){
			hashVal += tableSize;
		}
		return hashVal;
	}
	
}
