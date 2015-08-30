package com.handleJSON.quiz1;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;


/**
 *
 * @author wangchao
 *
 */
public class Quiz1Test {
	public static void main(String[] args) {
		//a.提取出SD_DOC=0000000151, PARTN_ROLE=AG的ORDER_PARTNERS_OUT子节点
		task1();
		//b.统计一下SD_DOC=0000000151一共出现了几次
		task2();
		//c.将SD_DOC=0000000151, PARTN_ROLE=AG的ORDER_PARTNERS_OUT子节点的
		//LEVEL_NR设置为100, 并将结果保存在另一个json文件中
		task3();
	}

	private static void task3(){
		String path = "resource/responseDetailsOfSalesorderItem.json";
		//新文件保存路径
		String outputPath = "resource/Quiz1_new_childNode/Quiz1_new_childNode.json";
		String nodeName = "ORDER_PARTNERS_OUT";
		JSONObject json = getJson(path);
		if (json!=null) {
			upDateAndSaveJson(json,nodeName,"0000000151","AG",100,outputPath);
		}else {
			System.out.println("Wriong file path!");
		}
	}

	private static void task2(){
		String path = "resource/responseDetailsOfSalesorderItem.json";
		JSONObject json = getJson(path);
		if (json!=null) {
			int count = getCount(json,"0000000151");
			System.out.println(count);
		}else {
			System.out.println("Wriong file path!");
		}
	}

	private static void task1(){
		String path = "resource/responseDetailsOfSalesorderItem.json";
		JSONObject json = getJson(path);
		String nodeName = "ORDER_PARTNERS_OUT";
		if (json!=null) {
			JSONObject childNode = getChildNode(json,nodeName,"0000000151","AG");
			if (childNode!=null) {
				System.out.println(childNode.toString());
			}else {
				System.out.println("No such a child node.");
			}

		}else {
			System.out.println("Wriong file path!");
		}
	}

	/**
	 *  get the child node JSONObject by given conditions And update the field's value
	 *   by given conditons. Finally, save it to given path
	 * @param json json the JSONObject to process
	 * @param childNode childNode the name of childNode
	 * @param SD_DOC_Val the value of SD_DOC
	 * @param PARTN_ROLE_Val the value of PARTN_ROLE
	 * @param LEVEL_NR_Val the value of LEVEL_NR
	 * @param path A output pathname
	 */
	public static void upDateAndSaveJson(JSONObject json, String childNode,
										 String SD_DOC_Val, String PARTN_ROLE_Val, int LEVEL_NR_Val, String path) {
		JSONObject jsonObject = getChildNode(json, childNode, SD_DOC_Val, PARTN_ROLE_Val);
		if (jsonObject!=null) {
			jsonObject.put("LEVEL_NR", LEVEL_NR_Val);
			writeFile(jsonObject.toString(), path);
		}else {
			System.out.println("No such a child node.");
		}

	}

	/**
	 * save updated data to new file
	 * @param json the json string to save
	 * @param path A output pathname
	 */
	public static void writeFile(String json, String path) {
		File file = new File(path);
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdir();
		}
		try{
			PrintWriter out = new PrintWriter(new File(path).getAbsoluteFile());
			try {
				out.print(new StringBuilder().append(json).toString());
			} finally{
				out.close();
			}
		} catch(IOException ex){
			System.out.println(ex.getStackTrace());
		}
	}

	/**
	 * get the number of given conditions field/value appears
	 * @param json the JSONObject to process
	 * @param SD_DOC_Val SD_DOC_Val the value of SD_DOC
	 * @return the number of given field
	 */
	public static int getCount(JSONObject json, String SD_DOC_Val) {
		Iterator keys = json.keys();
		int count = 0;
		JSONArray valueArray = null;
		JSONObject jsonObject = null;
		while(keys.hasNext()){
			String key = keys.next().toString();
			valueArray = (JSONArray) json.get(key);
			for(int i=0; i<valueArray.size(); i++) {
				jsonObject = (JSONObject) valueArray.get(i);
				String value = (String) jsonObject.get("SD_DOC");
				if (value !=null && value.equals(SD_DOC_Val)) {
					count ++;
				}
			}
		}
		return count;
	}

	/**
	 * get the child node JSONObject by given conditions
	 * @param json the JSONObject to process
	 * @param childNode the name of childNode
	 * @param SD_DOC_Val the value of SD_DOC
	 * @param PARTN_ROLE_Val the value of PARTN_ROLE
	 * @return target JSONObject of child node
	 */
	public static JSONObject getChildNode(JSONObject json, String childNode,
										  String SD_DOC_Val, String PARTN_ROLE_Val) {
		JSONArray jsonArray = (JSONArray) json.get(childNode);
		JSONObject jsonObject = null;
		for(int i=0; i<jsonArray.size(); i++) {
			jsonObject = (JSONObject) jsonArray.get(i);
			String value1 = (String) jsonObject.get("SD_DOC");
			String value2 = (String) jsonObject.get("PARTN_ROLE");
			if(value1.equals(SD_DOC_Val) && value2.equals(PARTN_ROLE_Val)) {
				return jsonObject;
			}
		}
		return null;
	}

	/**
	 * get JSONObject from a local file
	 * @param filePath	A pathname string
	 * @return a JSONObject object
	 */
	public static JSONObject getJson(String filePath) {
		File file = new File(filePath);

		if (!file.exists()) {
			System.out.println("Wriong file path!");
			return null;
		}
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					new File(filePath).getAbsoluteFile()));
			try{
				String line;
				while((line = reader.readLine()) != null){
					sb.append(line);
				}
			}finally{
				reader.close();
			}
		} catch(IOException e){
			e.printStackTrace();
		}
		JSONObject json = (JSONObject) JSONSerializer.toJSON(sb.toString());
		return json;
	}
}
