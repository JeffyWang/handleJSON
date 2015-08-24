package com.handleJSON.quiz4.multiThread;

import java.util.Map;
import java.util.concurrent.Callable;

import net.sf.json.JSONObject;

import com.handleJSON.quiz2.util.JSONProcessUtil;
/**
 * 
 * @author wangchao
 *
 */
public class GetChildCallable implements Callable<String>{
	private String filePath;
	private String nodeName;
	private Map<String, Object> conditions;
	
	public GetChildCallable(String filePath, String nodeName, Map<String, Object> conditions) {
		this.filePath = filePath;
		this.nodeName = nodeName;
		this.conditions = conditions;
	}
	
	@Override
	public String call() throws Exception {
		JSONObject jsonObject = JSONProcessUtil.getJson(filePath);
		JSONObject childNode = null;
		if (jsonObject!=null){
			childNode = JSONProcessUtil.getChildNode(jsonObject, nodeName, conditions);
			if (childNode!=null) {
				System.out.println(childNode.toString());
			}else {
				System.out.println("No such a child node.");
				return "";
			}
		}else {
			System.out.println("Wriong file path!");
			return "";
		}
		return childNode.toString();
	}
}
