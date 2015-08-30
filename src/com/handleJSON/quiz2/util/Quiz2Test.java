package com.handleJSON.quiz2.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 *
 * @author wangchao
 *
 */
public class Quiz2Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//a.提取出SD_DOC=0000000151, PARTN_ROLE=AG的ORDER_PARTNERS_OUT子节点
		task1();
		//b.统计一下SD_DOC=0000000151一共出现了几次
		task2();
		//c.将SD_DOC=0000000151, PARTN_ROLE=AG的ORDER_PARTNERS_OUT子节点的
		//LEVEL_NR设置为100, 并将结果保存在另一个json文件中
		task3();
	}

	private static void task3() {
		String path = "resource/responseDetailsOfSalesorderItem.json";
		JSONObject json = JSONProcessUtil.getJson(path);
		if (json!=null) {
			String nodeName = "ORDER_PARTNERS_OUT";
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("SD_DOC", "0000000151");
			conditions.put("PARTN_ROLE", "AG");
			Map<String, Object> updateConditions = new HashMap<String, Object>();
			updateConditions.put("LEVEL_NR", 100);
			String outputPath = "resource/Quiz2_new_childNode/Quiz2_new_childNode.json";
			JSONProcessUtil.upDateAndSaveJson(json, nodeName, conditions, updateConditions, outputPath);

		}else {
			System.out.println("Wriong file path!");
		}


	}

	private static void task2() {
		String path = "resource/responseDetailsOfSalesorderItem.json";
		JSONObject json = JSONProcessUtil.getJson(path);
		if (json!=null) {
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("SD_DOC", "0000000151");
			int count = JSONProcessUtil.getCount(json, conditions);
			System.out.println(count);
		}else {
			System.out.println("Wriong file path!");
		}
	}

	private static void task1() {
		String path = "resource/responseDetailsOfSalesorderItem.json";
		JSONObject json = JSONProcessUtil.getJson(path);
		if (json!=null) {
			String nodeName = "ORDER_PARTNERS_OUT";
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("SD_DOC", "0000000151");
			conditions.put("PARTN_ROLE", "AG");
			JSONObject childNode = JSONProcessUtil.getChildNode(json, nodeName, conditions);
			if (childNode!=null) {
				System.out.println(childNode.toString());
			}else {
				System.out.println("No such a child node.");
			}
		}else {
			System.out.println("Wriong file path!");
		}
	}

}
