package com.handleJSON.quiz4.multiThread;

import java.io.File;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * 
 * @author wangchao
 *
 */
public final class MultiThreadUtility {
	
	private MultiThreadUtility() {
		
	}

	/**
	 * get the child node string by given conditions.
	 * And update the field's value by given conditons. 
	 * Finally, save it to given path
	 * @param path path A path name string
	 * @param nodeName nodeName the name of child node
	 * @param conditions the key/value of the conditions; 
	 * 			key is sub-node's field like:SD_DOC , 
	 * 			value is sub-node's value like:0000000151
	 * @param updateConditions the key/value of update conditions; 
	 * 			key is sub-node's field like:LEVEL_NR , 
	 * 			value is sub-node's value like:100
	 * @param outPath A output pathname
	 * @return if finished, return success
	 */
	public static String upDateAndSave(String path, String nodeName, 
			Map<String,Object> conditions,Map<String,Object> updateConditions,String outPath) {
		//create thread pool
		ExecutorService exec = Executors.newCachedThreadPool();
		CompletionService<String> compService = new ExecutorCompletionService<>(exec);

		File dirctory = new File(path);
		File[] files = dirctory.listFiles();
		
		if (files != null && files.length != 0) {
			for (int i = 0; i < files.length; i++) {
				if (!files[i].isDirectory()) {
					//Task3: get update and save
					compService.submit(new UpdateAndSaveCallable(
							files[i].getAbsolutePath(),nodeName,conditions,
								updateConditions,outPath+"/new_"+nodeName+"_"+i+".json"));
				}
			}
			
			//check results
			for (int i = 0; i < files.length; i++) {
				try {
					compService.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			exec.shutdown();
			
		}else {
			System.out.println("path not exsist or have no file");
		}
		return "success";
	}
	
	/**
	 * get the number of given conditions field/value appears
	 * @param path A path name string
	 * @param conditions  the key/value of the conditions; 
	 * key is sub-node's field like:SD_DOC , 
	 * value is sub-node's value like:0000000151
	 * @return the number of statistical result
	 */
	public static int getCount(String path, Map<String, Object> conditions) {
		//create thread pool
		ExecutorService exec = Executors.newCachedThreadPool();
		CompletionService<Integer> compService = 
				new ExecutorCompletionService<>(exec);

		File dirctory = new File(path);
		File[] files = dirctory.listFiles();
		int total = 0;
		
		if (files != null && files.length != 0) {
			for(File file : files){
				//Task2: get count
				compService.submit(new GetCountCallable(
						file.getAbsolutePath(),conditions));
			}
			
			//check results
			for (int i = 0; i < files.length; i++) {
				try {
					Future<Integer> future = compService.take();
					total = total+future.get();
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			
			exec.shutdown();
			
		}else {
			System.out.println("path not exsist or have no file");
		}
		return total;
	}
	
	/**
	 * 
	 * @param path A path name string
	 * @param nodeName the name of child node
	 * @param conditions conditions the key/value of the conditions;
	 *  key is sub-node's field name like:SD_DOC ,
	 *  value is sub-node's value like:0000000151
	 * @return the string of plenty of child node 
	 */
	public static String getChildNode(String path,
			String nodeName,Map<String,Object> conditions) {
		//create thread pool
		ExecutorService exec = Executors.newCachedThreadPool();
		CompletionService<String> compService = 
				new ExecutorCompletionService<>(exec);

		File dirctory = new File(path);
		File[] files = dirctory.listFiles();
		StringBuilder sb = new StringBuilder();
		
		if (files != null && files.length != 0) {
			for(File file : files){
				//Task1: get child-node
				compService.submit(new GetChildCallable(
						file.getAbsolutePath(),nodeName,conditions));
			}
			
			//check results
			for (int i = 0; i < files.length; i++) {
				try {
					Future<String> future = compService.take();
					sb.append(future.get()+"\n");
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			
			exec.shutdown();
			
		}else {
			System.out.println("path not exsist or have no file");
		}
		return sb.toString();
	}
	
}
