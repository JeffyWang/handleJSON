package com.handleJSON.quiz4.multiThread;

import java.util.HashMap;
import java.util.Map;



/**
 * 改进已有的代码，考虑如果需要处理的json文件数目很多的情况，如何能够高效的处理。
 * @author wangchao
 *
 */
public class Quiz4Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Task1： 多线程下，提取出SD_DOC=0000000151, PARTN_ROLE=AG的ORDER_PARTNERS_OUT子节点
		task1();

		//Task2： 多线程下，统计一下SD_DOC=0000000151一共出现了几次
		task2();

		//Task3： 多线程下，将SD_DOC=0000000151, PARTN_ROLE=AG的ORDER_PARTNERS_OUT
		//子节点的LEVEL_NR设置为100, 并将结果保存在另一个json文件中
		task3();
	}

	public static void task3() {
		String path = "resource";
		String nodeName = "ORDER_PARTNERS_OUT";
		Map<String,Object> conditions = new HashMap<String,Object>();
		conditions.put("SD_DOC", "0000000151");
		conditions.put("PARTN_ROLE", "AG");
		Map<String,Object> updateConditions = new HashMap<String,Object>();
		updateConditions.put("LEVEL_NR", 100);
		String outPath = "resource/Quiz4_new_childNode";
		MultiThreadUtility.upDateAndSave(path, nodeName, conditions,updateConditions,outPath);
	}

	public static void task2() {
		String path = "resource";
		Map<String,Object> conditions = new HashMap<String,Object>();
		conditions.put("SD_DOC", "0000000151");
		int count = MultiThreadUtility.getCount(path, conditions);
		System.out.println(count);
	}

	public static void task1() {
		String path = "resource";
		String nodeName = "ORDER_PARTNERS_OUT";
		Map<String,Object> conditions = new HashMap<String,Object>();
		conditions.put("SD_DOC", "0000000151");
		conditions.put("PARTN_ROLE", "AG");
		String childNode = MultiThreadUtility.getChildNode(path, nodeName, conditions);
		System.out.println(childNode);
	}
}
