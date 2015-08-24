package com.handleJSON.quiz3.hugeJSON;

import java.util.HashMap;
import java.util.Map;

import com.handleJSON.quiz4.multiThread.MultiThreadUtility;

/**
 * 改进已有的代码，考虑如果需求1中需要处理的单个json文件容量过大的情况，如何能够高效的处理。
 * @author wangchao
 *
 */
public class Quiz3Test {

	
	public static void main(String[] args) {
		//Task1:大文件下，提取出SD_DOC=0000000151, PARTN_ROLE=AG的ORDER_PARTNERS_OUT子节点
		task1();
		//Task2： 大文件下，统计一下SD_DOC=0000000151一共出现了几次
		task2();
		//Task3： 大文件下，将SD_DOC=0000000151, PARTN_ROLE=AG的ORDER_PARTNERS_OUT子节点的
		//LEVEL_NR设置为100, 并将结果保存在另一个json文件中
		task3();
	}
	
	public static void task3() {
		String filePath = "resource/responseDetailsOfSalesorderItem.json";
		String nodeName = "ORDER_PARTNERS_OUT";
		int objectNum = 500;
		//先将目标直接掉分割成小文件
		SplitHugeJSON.split(filePath, nodeName, objectNum);
		//然后在用多线程处理多个小文件
		String path = "resource/"+nodeName;
		Map<String,Object> conditions = new HashMap<String,Object>();
		conditions.put("SD_DOC", "0000000151");
		conditions.put("PARTN_ROLE", "AG");
		Map<String,Object> updateConditions = new HashMap<String,Object>();
		updateConditions.put("LEVEL_NR", 100);
		String outPath = "resource/updatedData";
		String childNode = MultiThreadUtility.upDateAndSave(path, nodeName, 
				conditions, updateConditions, outPath);
		System.out.println(childNode);
	}
	
	
	public static void task2() {
		String filePath = "resource/responseDetailsOfSalesorderItem.json";
		String nodeName = "ORDER_PARTNERS_OUT";
		int objectNum = 500;
		//先将目标直接掉分割成小文件
		SplitHugeJSON.split(filePath, nodeName, objectNum);
		//然后在用多线程处理多个小文件
		String path = "resource/"+nodeName;
		Map<String,Object> conditions = new HashMap<String,Object>();
		conditions.put("SD_DOC", "0000000151");
		int num = MultiThreadUtility.getCount(path, conditions);
		System.out.println(num);
	}
	
	public static void task1() {
		String filePath = "resource/responseDetailsOfSalesorderItem.json";
		String nodeName = "ORDER_PARTNERS_OUT";
		int objectNum = 500;
		//先将目标直接掉分割成小文件
		SplitHugeJSON.split(filePath, nodeName, objectNum);
		//然后在用多线程处理多个小文件
		String path = "resource/"+nodeName;
		Map<String,Object> conditions = new HashMap<String,Object>();
		conditions.put("SD_DOC", "0000000151");
		conditions.put("PARTN_ROLE", "AG");
		String childNode = MultiThreadUtility.getChildNode(path, nodeName, conditions);
		System.out.println(childNode);
	}

}
