package com.handleJSON.quiz3.hugeJSON;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.handleJSON.quiz2.util.JSONProcessUtil;
/**
 * a class have method to split huge JSON file to plenty of samll files
 * @author wangchao
 *
 */


public class SplitHugeJSON {
	   private static final String WRAP_LINE = "\n";
	   private static final String QUOTO= "\"";
	
	private SplitHugeJSON() {

	}	
	/**
	 * split huge file to plenty of small file by given child-node's name.
	 * @param filePath A pathname string
	 * @param nodeName the name of child-node
	 * @param objectNum the number of objects new-created small file(splitting granularity)
	 */
	public static void split(String filePath, String nodeName, int objectNum) {
		//the flag of starting split
		boolean startSplit = false;
		StringBuilder sb = new StringBuilder("{"+WRAP_LINE);
		int count = 0;
        int fileNum = 1;
        try {
        	BufferedReader reader = new BufferedReader(new FileReader(
        			new File(filePath).getAbsoluteFile()));
	        try {
	        	String line;
		        while((line = reader.readLine()) != null){
		        	//check if its' the target node
					if (line.indexOf(nodeName) != -1) {
						if (line.indexOf("[ ]") == -1) {
							startSplit = true;
						}else {
							//when target node don't have element, don't split it
							break;
						}
					}
					//start split
					if (startSplit) {
						if (line.indexOf("}") != -1) {
							count++;
							//when reach the number of target object number
							if (count >= objectNum) {
								sb.append(line.split("}")[0]+"} ]"+WRAP_LINE+"}");
								JSONProcessUtil.writeFile(sb.toString(), filePath.split("/")[0]+"/"+nodeName+"/"+nodeName+"_"+fileNum+".json");
								sb.setLength(0);
								sb.trimToSize();
								String tmp = "{"+WRAP_LINE+QUOTO +nodeName+QUOTO+" : [{"+WRAP_LINE;
								sb.append(tmp);
								count = 0;
								fileNum++;
								
							}else {
								if (line.indexOf("} ]") != -1) {
									sb.append(line.split("} ]")[0]+"} ]"+WRAP_LINE+"}");
									JSONProcessUtil.writeFile(sb.toString(), filePath.split("/")[0]+"/"+nodeName+"/"+nodeName+"_"+fileNum+".json");
									break;
								}else {
									sb.append(line+WRAP_LINE);
								}
							}
						}else {
							sb.append(line+WRAP_LINE);
						}
					}
		        }
	        }finally{
	        	reader.close();
	        }
        } catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("split function finished.");
	}

}
