package org.hope6537.datastruct.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Describe 使用Java自带的Set 和 Map类进行字典模拟
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-3-27上午08:41:52
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class Dictionary {
	/**
	 * @Descirbe 打印出整个字典序和值
	 * @Author Hope6537(赵鹏)
	 * @Params @param adjWords
	 * @Params @param minWords
	 * @SignDate 2014-3-27上午08:52:13
	 * @Version 0.9
	 * @param adjWords
	 * @param minWords
	 */
	public static void printHighChangeables(Map<String, List<String>> adjWords,
			int minWords) {
		for (Map.Entry<String, List<String>> entry : adjWords.entrySet()) {
			List<String> words = entry.getValue();
			if (words.size() >= minWords) {
				System.out.print(""+entry.getKey() + " (");
				System.out.print(words.size() + ") ");
				for (String w : words) {
					System.out.print(" " + w);
				}
				System.out.println();
			}
		}
	}

	/**
	 * @Descirbe 比较两个单词是否只有一个字母不同
	 * @Author Hope6537(赵鹏)
	 * @Params @param word1
	 * @Params @param word2
	 * @Params @return
	 * @SignDate 2014-3-27上午08:55:17
	 * @Version 0.9
	 * @param word1
	 * @param word2
	 * @return
	 */
	private static boolean oneCharOff(String word1, String word2) {
		if (word1.length() != word2.length()) {// 长度不等 直接死
			return false;
		}
		int diff = 0;// 不同的数量
		for (int i = 0; i < word1.length(); i++) {// 开始挨个字母查找
			if (word1.charAt(i) != word2.charAt(i)) {
				if (++diff > 1) {
					;// 如果出现不等于 第一次回diff+1 diff=1 不会跳出 如果接下来的循环里又出现不同
					return false;// diff=2 > 1 又死了
				}
			}
		}
		return diff == 1;// 最后返回是否有一个不同 （因为有可能都相同 没有经过上面的跳转）
	}

	/**
	 * @Descirbe 将字符相差一个的单词添加进Map集合的方法， 以一个单词为关键字，所以和他一个字母相异组成一个表作为值
	 * @Author Hope6537(赵鹏)
	 * @Params @param <KeyType> 关键字的表示方式
	 * @Params @param m Map集合 即字典
	 * @Params @param key 首单词
	 * @Params @param value 相异的单词
	 * @SignDate 2014-3-27上午09:06:43
	 * @Version 0.9
	 * @param <KeyType>
	 * @param m
	 * @param key
	 * @param value
	 */
	private static <KeyType> void update(Map<KeyType, List<String>> m,
			KeyType key, String value) {
		List<String> list = m.get(key);
		if (list == null) {
			list = new ArrayList<String>();
			m.put(key, list);
		}
		list.add(value);

	}

	/**
	 * @Descirbe 蛮力添加 运行 89000 个单词 需要96秒
	 * @Author Hope6537(赵鹏)
	 * @Params @param theWords
	 * @Params @return
	 * @SignDate 2014-3-27上午09:09:49
	 * @Version 0.9
	 * @param theWords
	 * @return 
	 */
	public static Map<String, List<String>> computeAdjacentWords(
			List<String> theWords) {
		Map<String, List<String>> adjWords = new TreeMap<String, List<String>>();// 这是排序集合
		String[] words = new String[theWords.size()];// 将表化为数组
		theWords.toArray(words);
		for (int i = 0; i < words.length; i++) {
			for (int j = i + 1; j < words.length; j++) {
				// 从头开始 两个两个比对
				if (oneCharOff(words[i], words[j])) {
					// 符合性质添加 暴力添加
					update(adjWords, words[i], words[j]);
					update(adjWords, words[j], words[i]);
				}
			}
		}
		return adjWords;
	}

	/**
	 * @Descirbe 以相同序列长度为关键字的字典添加序 运行89000单词 51秒
	 * @Author Hope6537(赵鹏)
	 * @Params @param theWords
	 * @Params @return
	 * @SignDate 2014-3-27上午09:11:24
	 * @Version 0.9
	 * @param theWords
	 * @return
	 */
	public static Map<String, List<String>> computeAdjacentWords_length(
			List<String> theWords) {
		Map<String, List<String>> adjWords = new TreeMap<String, List<String>>();

		Map<Integer, List<String>> wordsByLength = new TreeMap<Integer, List<String>>();

		for (String w : theWords) {// 将单词的长度进行一一比较排序,然后形成索引
			update(wordsByLength, w.length(), w);
		}

		for (List<String> groupsWords : wordsByLength.values()) {// 这是内含该一个单词长度的所有该长度单词的集合
			// 通过单词的长度 从2开始进行循环 每次都将该长度的单词进行数组化
			String[] words = new String[groupsWords.size()];
			groupsWords.toArray(words);
			// 然后暴力比对
			for (int i = 0; i < words.length; i++) {
				for (int j = i + 1; j < words.length; j++) {
					// 从头开始 两个两个比对
					if (oneCharOff(words[i], words[j])) {
						// 符合性质添加 暴力添加
						update(adjWords, words[i], words[j]);
						update(adjWords, words[j], words[i]);
					}
				}
			}
		}
		return adjWords;
	}

	/**
	 * @Descirbe 以单词作为关键字的字典添加序 运行160000个单词 4791毫秒
	 * @Author Hope6537(赵鹏)
	 * @Params @param theWords
	 * @Params @return
	 * @SignDate 2014-3-27上午09:19:39
	 * @Version 0.9
	 * @param theWords
	 * @return
	 */
	public static Map<String, List<String>> computeAdjacentWords_word(
			List<String> theWords) {
		Map<String, List<String>> adjWords = new TreeMap<String, List<String>>();

		Map<Integer, List<String>> wordsByLength = new TreeMap<Integer, List<String>>();

		for (String w : theWords) {
			update(wordsByLength, w.length(), w);
		}
		// 之前的和上面computeAdjacentWords_length一样
		for (Map.Entry<Integer, List<String>> entry : wordsByLength.entrySet()) {
			//将上面的以长度为关键字的单词集合挨个循环 第一次是以两个单词为长度的字符串（单词）
			List<String> groupWords = entry.getValue();// 这是单词集
			int groupNum = entry.getKey();// 这是这个单词集的单词长度
			
			for (int i = 0; i < groupNum; i++) {
				//从下标0开始 到这一次循环的set集合的单词长度 ， 外部循环每次更换新set groupNum也随之变化
				Map<String, List<String>> repToWord = new TreeMap<String, List<String>>();
				//生成以单词为关键字 相异单词表为值的映射
				for (String str : groupWords) {
					//从这组的单词集开始遍历所有的String单词 
					String rep = str.substring(0, i) + str.substring(i + 1);
					//将所有的单词依次截串 然后再插入repToWord映射里 以i点被截下的剩余单词作为关键字 单词作为集合
					//例如 inat inbt inct indt
					//如果 i=3 时 把第三个字符截掉  则本次循环完毕后 repToWord的数据为 <int,inat>,<int,inbt>.... 
					update(repToWord, rep, str);
				}
				for (List<String> wordClique : repToWord.values()) {
					//将一个单词片段里包含的单词依次循环
					if (wordClique.size() >= 2) {
						//如果这个片段的单词大于2
						for (String s1 : wordClique) {
							for (String s2 : wordClique) {
								//s1，s2 不相同 则以s1为关键字 插入进adjWords字典序里
								if (s1 != s2) {
									update(adjWords, s1, s2);
								}
							}
						}
					}
				}
			}
		}

		return adjWords;
	}
	
	public static void main(String[] args) {
		Dictionary d = new Dictionary();
		List<String> list = new ArrayList<String>();
		for(int i = 65 ; i < 82 ; i++){
			for(int j = 65 ; j < 82 ; j++){
				for(int k = 65 ; k < 82 ; k++){
					for(int n = 65 ; n < 82 ; n++){
					list.add((char)i+"K"+(char)i+(char)j+(char)k+(char)n);
					}
				}
				
			}
			
		}
		Map<String, List<String>> a = Dictionary.computeAdjacentWords_word(list);
		Dictionary.printHighChangeables(a,1);
		/*int count = 0;
		for(String s : list){
			System.out.print(s+" ");
			count++;
			if(count % 20 == 0){
				System.out.println();
			}
		}*/
		
	}

}
