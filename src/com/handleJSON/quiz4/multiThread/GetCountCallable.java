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
public class GetCountCallable implements Callable<Integer> {
	private String filePath;
	private Map<String, Object> conditions;
	
	public GetCountCallable(String filePath, Map<String, Object> conditions) {
		this.filePath = filePath;
		this.conditions = conditions;
	}
	
	@Override
	public Integer call() throws Exception {
		JSONObject jsonObject = JSONProcessUtil.getJson(filePath);
		int count = JSONProcessUtil.getCount(jsonObject, conditions);
		return count;
	}

}
