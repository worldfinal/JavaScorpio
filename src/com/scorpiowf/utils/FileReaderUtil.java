package com.scorpiowf.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

import org.junit.Test;

public class FileReaderUtil {
	@Test
	public void testEncrypteV2() {
		String fileName="d:\\a.rar";
		encryptV2(fileName);
	}
	public static void encryptV2(String fileName) {
		try {
			RandomAccessFile f = new RandomAccessFile(fileName, "rw");
			byte[] data = new byte[20];
			int rt = f.read(data);
			for (int i = 0; i < rt; i++) {
				data[i] ^= 0xe3;
			}
			f.seek(0);
			f.write(data);
			f.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void encrypt(String fileName, String encryptString) {
		File file = new File(fileName);   
        if(file.exists()){  
            try {  
            	//read
                FileInputStream in = new FileInputStream(file);  
                DataInputStream dis=new DataInputStream(in);  
                
                //write
                String outfileName = fileName + ".wf";
                DataOutputStream dos=new DataOutputStream(new FileOutputStream(new File(outfileName)));  
                  
                int len = 65536;
                byte[] itemBuf = new byte[len];
                byte[] encryBytes = encryptString.getBytes();
                int m = encryBytes.length;
                
                int x = dis.available();
                int y = 0;
                while (x > 0) {
                	int rt = dis.read(itemBuf, 0, len);
                	
                	for (int i = 0; i < rt; i++) {
                		itemBuf[i] ^= encryBytes[(y + i) % m];
                	}
                	
                	y += rt;
                	x = dis.available();
                	
                	dos.write(itemBuf);
                }  
                
                dos.flush();
                dos.close();
                dis.close();
      //          System.out.println("read: " + y + " bytes");
                
                File f1 = new File(fileName);
                File f2 = new File(outfileName);
                f1.delete();
                f2.renameTo(f1);
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }finally{  
                //close  
            }  
        }  
	}
	
	public static void encrypt(String fileName) {
		File file = new File(fileName);   
        if(file.exists()){  
            try {  
            	//read
                FileInputStream in = new FileInputStream(file);  
                DataInputStream dis=new DataInputStream(in);  
                
                //write
                String outfileName = fileName + ".wf";
                DataOutputStream dos=new DataOutputStream(new FileOutputStream(new File(outfileName)));  
                  
                int len = 65536;
                byte[] itemBuf = new byte[len];  
                
                int x = dis.available();
       //         System.out.println("dis.available(): " + x);
                int y = 0;
                while (x > 0) {
                	int rt = dis.read(itemBuf, 0, len);
                	
                	int m = Constants.FILE_ENCRYPTED_CODE.length;
                	for (int i = 0; i < rt; i++) {
                		itemBuf[i] ^= Constants.FILE_ENCRYPTED_CODE[(y + i) % m];
                	}
                	
                	y += rt;
                	x = dis.available();
                	
                	dos.write(itemBuf);
                }  
                
                dos.flush();
                dos.close();
                dis.close();
      //          System.out.println("read: " + y + " bytes");
                
                File f1 = new File(fileName);
                File f2 = new File(outfileName);
                f1.delete();
                f2.renameTo(f1);
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }finally{  
                //close  
            }  
        }  
	}
	public static void processEncrypt(File file, String encryptString) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				processEncrypt(files[i], encryptString);
			}
		} else {
			encrypt(file.getAbsolutePath(), encryptString);
		}
	}
	public static void processEncrypt(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				processEncrypt(files[i]);
			}
		} else {
			encrypt(file.getAbsolutePath());
		}
	}
	
	@Test
	public void testEncrypt() {
		Date from = new Date();
		String filepath = "D:\\QMDownload\\test\\";
		File file = new File(filepath);
		String s1 = "this is acmicpc";
		String s2 = "wfwfwf";
		processEncrypt(file, s2);
		Date to = new Date();
		long use = to.getTime() - from.getTime();
		System.out.println("done, use:" + use + " (ms)");
	}
}
