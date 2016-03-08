package com.scorpiowf.service.fileencrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class Encrypter {
	public static void encryptFolder(String folderPath, String psw) {
		File file = new File(folderPath);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				if (f.isFile()) {
					encryptFile(f.getAbsolutePath(), psw);
				}
			}
		}
	}
	public void decryptFolder(String folderPath, String psw) {
		
	}
	public static void encryptFile(String fileName, String psw) {
		try {
			byte p[] = psw.getBytes();
			int n = p.length;
			RandomAccessFile f = new RandomAccessFile(fileName, "rw");
			byte[] data = new byte[100];
			int rt = f.read(data);
			for (int i = 0; i < rt; i += 2) {
				data[i] ^= p[i%n];
			}
			f.seek(0);
			f.write(data);
			/*
			f.seek(200);
			rt = f.read(data);
			for (int i = 0; i < rt; i += 2) {
				data[i] ^= p[i%n];
			}
			f.seek(200);
			f.write(data);
			*/
			f.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void decryptFile(String fileName, String psw) {
		
	}
}
