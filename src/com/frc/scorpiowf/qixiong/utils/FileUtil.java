package com.frc.scorpiowf.qixiong.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileUtil {
	public static void writeFile(String qqNum, String logs, String fileName) {
		File dir = new File(String.format("C:\\7xlog\\%s", qqNum));
		dir.mkdir();
		File file = new File(String.format("C:\\7xlog\\%s\\%s", qqNum, fileName));
		try {
			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter os = new OutputStreamWriter(fos, "UTF-8");
			os.write(logs); 
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
