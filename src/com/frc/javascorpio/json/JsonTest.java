package com.frc.javascorpio.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import net.sf.json.JSONObject;

public class JsonTest {
	public static void main(String[] args) {
		String fileName = "D:\\My Documents\\CandyCrush\\20160521\\levels";
		File files = new File(fileName);
		String file[] = files.list();
		for (int i = 0; i < file.length; i++) {
			String path = fileName + "\\" + file[i];
			modifyCandyCrush(path);
//			System.out.println(path);
		}
	}
	@Test
	public void test() {
		int k = 100;
		System.out.println("sss");
		for (int i = 4; i <= 128; i++) {
			String str = String.format("	<Lock lockedLevel=\"%d\"/><!-- episode %d -->", k++, i);
			System.out.println(str);
		}
	}
	public static void writeTxtFile(String filePath, String content) {
		FileOutputStream fos = null;
		PrintWriter pw = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		File file = new File(filePath);
		try {
			fos = new FileOutputStream(file);
			fis = new FileInputStream(file);
			pw = new PrintWriter(fos);
			pw.write(content.toCharArray());
			pw.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void modifyCandyCrushFromString(String str) {
		com.alibaba.fastjson.JSONObject obj = JSON.parseObject(str);
		
	}
	public static void modifyCandyCrush(String fileName) {
	//	String fileName = "G:\\CandyCrush\\myLevel\\episode1level1.txt";
		String json = readTxtFile(fileName);
		
	//	String json = "{name=\"json\",bool:true,int:1,double:2.2,func:function(a){ return a; },array:[1,2]}";       
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map map = (Map)jsonObject;
		Set<Map.Entry<String, Object>> set = map.entrySet();
		for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it
				.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it
					.next();
			System.out.println(entry.getKey() + "--->" + entry.getValue());
			if ("numberOfColours".equals(entry.getKey()) && "6".equals((String)entry.getValue().toString()) ) {
				//			System.out.println("==========>" + entry.getValue());
				entry.setValue(5);
			} else if ("moveLimit".equals(entry.getKey())) {
	//			int moveLimit = Integer.parseInt(entry.getKey().toString());
				String val = (String)entry.getValue().toString();
				int v = Integer.parseInt(val);
				int x = (int)(1.5 * v);
				entry.setValue(x);
			} 
			if ("timeLimit".equals(entry.getKey())) {
				System.out.println("========> timelimit: " + entry.getValue());
				int timeLimit = Integer.parseInt(entry.getValue().toString());
				if (timeLimit < 80) {
					entry.setValue("80");
				}
			}
		}
		System.out.println(map);
		writeTxtFile(fileName, map.toString());
	//	System.out.println(jsonObject);
	}
	public static String readTxtFile(String filePath){ 
        try { 
        	String str = "";
                String encoding="UTF-8"; 
                File file=new File(filePath); 
                if(file.isFile() && file.exists()){ //判断文件是否存在 
                    InputStreamReader read = new InputStreamReader( 
                    new FileInputStream(file),encoding);//考虑到编码格式 
                    BufferedReader bufferedReader = new BufferedReader(read); 
                    String lineTxt = null; 
                    while((lineTxt = bufferedReader.readLine()) != null){ 
                  //      System.out.println(lineTxt); 
                        str += lineTxt;
                    } 
                    read.close();
                    System.out.println(str);
                    return str;
        }else{ 
            System.out.println("找不到指定的文件"); 
        } 
        } catch (Exception e) { 
            System.out.println("读取文件内容出错"); 
            e.printStackTrace(); 
        } 
        return "";
    } 
      

}
