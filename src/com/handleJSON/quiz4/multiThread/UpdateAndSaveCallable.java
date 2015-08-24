package com.handleJSON.quiz4.multiThread;
/**
 * 
 * @author wangchao
 *
 */
import java.util.Map;
import java.util.concurrent.Callable;

import net.sf.json.JSONObject;

import com.handleJSON.quiz2.util.JSONProcessUtil;

public class UpdateAndSaveCallable implements Callable<String> {
	private String filePath;
	private String nodeName;
	private Map<String, Object> conditions;
	private Map<String, Object> updateConditions;
	private String outPath;
	
	public UpdateAndSaveCallable(String filePath, String nodeName, 
			Map<String, Object> conditions,Map<String, Object> updateConditions,String outPath) {
		this.filePath = filePath;
		this.nodeName = nodeName;
		this.conditions = conditions;
		this.updateConditions = updateConditions;
		this.outPath = outPath;
	}
	
	@Override
	public String call() throws Exception {
		JSONObject jsonObject = JSONProcessUtil.getJson(filePath);
		JSONProcessUtil.upDateAndSaveJson(jsonObject, nodeName, conditions, updateConditions, outPath);
		return "success";
	}

}
