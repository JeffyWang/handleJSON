package com.handleJSON.quiz2.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * a Utility class to handle JSON Object  
 * @author wangchao
 *
 */
public final class JSONProcessUtil{
	/**
	 * private constructor to avoid unnecessary instantiation of the class
	 */
	private JSONProcessUtil() {

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

	/**
	 * get the child node JSONObject by given conditions
	 * 
	 * <p> Note:You can put more than one condition to filter the child node
	 * 
	 * @param json the JSONObject to process
	 * @param nodeName the name of child node
	 * @param conditions the key/value of the conditions; 
	 * 			key is sub-node's field name like:SD_DOC , 
	 * 			value is sub-node's value like:0000000151
	 * @return the child node JSONObject object
	 */
	public static JSONObject getChildNode(JSONObject json, 
			String nodeName, Map<String, Object> conditions) {
			JSONArray jsonArray = (JSONArray) json.get(nodeName);
		       JSONObject jsonObject = null;
		       boolean flag;
		       for(int i=0; i<jsonArray.size(); i++) {
		            jsonObject = (JSONObject) jsonArray.get(i);
		            flag = mapsAreEqual(jsonObject, conditions);
		            if (flag) {
						return jsonObject;
					}
		        }
		return null;
	}
	
	/**
	 * get the number of given conditions field/value appears
	 * <p> Note:You can put more than one condition to filter
	 * @param json the JSONObject to process
	 * @param conditions the key/value of the conditions;
	 * 			key is sub-node's field like:SD_DOC , 
	 * 			value is sub-node's value like:0000000151
	 * @return the number of statistical result
	 */
	public static int getCount(JSONObject json, Map<String, Object> conditions) {
        Iterator keys = json.keys();
        int count = 0;
        JSONArray valueArray = null;
        JSONObject jsonObject = null;
        while(keys.hasNext()){
            String key = keys.next().toString();
            valueArray = (JSONArray) json.get(key);
            for(int i=0; i<valueArray.size(); i++) {
                jsonObject = (JSONObject) valueArray.get(i);
                String value;
                for (String k : conditions.keySet()){
                	value = (String) jsonObject.get(k);
                	 if (value !=null && value.equals(conditions.get(k))) {
                         count ++;
                     }
    	        } 
            }
        }
        return count;
	}
	
	/**
	 * get the child node JSONObject by given conditions.
	 *  And update the field's value by given conditons.
	 *   Finally, save it to given path
	 * <p> Note:You can put more than one condition to filter the child node
	 * @param json the JSONObject to process
	 * @param nodeName the name of child node
	 * @param conditions the key/value of the conditions; 
	 * 			key is sub-node's field like:SD_DOC , 
	 * 			value is sub-node's value like:0000000151
	 * @param updateConditions the key/value of update conditions; 
	 * 			key is sub-node's field like:LEVEL_NR , 
	 * 			value is sub-node's value like:100
	 * @param outPath A output pathname
	 */
	public static void upDateAndSaveJson(JSONObject json, String nodeName,
			Map<String, Object> conditions, Map<String, Object> updateConditions,
			String outPath) {
		JSONObject jsonObject = getChildNode(json, nodeName, conditions);
		if (jsonObject!=null) {
		  for (String k : updateConditions.keySet()){
	        	jsonObject.put(k, updateConditions.get(k));
	        }
		  writeFile(jsonObject.toString(), outPath);	
			
		}else {
			System.out.println("No such a child node.");
		}
	}
	
	/**
	 * save the String to given path
	 * @param json the String to save
	 * @param path A output pathname
	 */
    public static void writeFile(String json, String path) {
    	File file = new File(path);
    	File parentFile = file.getParentFile();
    	if (!parentFile.exists()) {
			parentFile.mkdir();
		}
    	
//    	String[] paths = path.split("/");
//    	File file = new File(paths[0] + "/" + paths[1]);
//
//    	if (!file.exists()) {
//			file.mkdir();
//		}
    	
        try{
        	PrintWriter out = new PrintWriter(file.getAbsoluteFile());
        	try {
				out.print(new StringBuilder().append(json).toString());
			} finally{
				out.close();
			}
        } catch(IOException ex){
        		ex.printStackTrace();
	      }
    }
	
	/**
	 * compare if the is child node meet the given conditions
	 * @param mapA  the JSONObject of the JSONArray in child node
	 * @param mapB the key/value of the conditions; 
	 * 				key is sub-node's field like:SD_DOC , 
	 * 				value is sub-node's value like:0000000151
	 * @return true, if the sub-node meet the conditions ;
	 * 				<p>false, the sub-node cannot meet the conditions
	 */
	public static boolean mapsAreEqual(Map<String, Object> mapA, Map<String, Object> mapB) {
	    try{
	        for (String k : mapB.keySet()){
	        	if (mapA.containsKey(k)) {
	        		 if (!mapA.get(k).equals(mapB.get(k))) {
			                return false;
			            }
				}else {
					return false;
				}
	        } 
	    } catch (NullPointerException np) {
	    	np.printStackTrace();
	        return false;
	    }
	    return true;
	}
}
