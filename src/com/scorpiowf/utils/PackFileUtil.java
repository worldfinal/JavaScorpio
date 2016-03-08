package com.scorpiowf.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PackFileUtil {
	final static public long MAX_FILE_LENGTH = 1024*1024*50;
	public static void main(String args[]) {
		if (args.length < 3) {
			System.out.println("format as below");
			System.out.println("java -jar packer.jar pack psw \"c:\\danny\"");
			System.out.println("java -jar packer.jar unpack psw \"c:\\danny\"");
		} else if ("pack".equalsIgnoreCase(args[0])) {
			String encryptString = args[1];
			for (int i = 2; i < args.length; i++) {
				String filepath = args[i];
				File file = new File(filepath);

				processPackFile(file, encryptString);
				deleteDataFile(file);
			}
		} else if ("unpack".equalsIgnoreCase(args[0])) {
			String encryptString = args[1];
			for (int i = 2; i < args.length; i++) {
				String filepath = args[i];
				File file = new File(filepath);

				processUnPackFile(file, encryptString);
			}
		} else {
			System.out.println("format as below");
			System.out.println("java -jar packer.jar pack psw \"c:\\danny\"");
			System.out.println("java -jar packer.jar unpack psw \"c:\\danny\"");
		}
	}
	public static void deleteDataFile(File file) {
		if (file == null) {
			return;
		}
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteDataFile(files[i]);
			}
		} else {
			String name = file.getName();
			if (!name.contains("data.wf")) {
				file.delete();
			}
			
		}
	}
	public static void processPackFile(File file, String encryptString) {
		if (file == null) {
			return;
		}
		
		if (file.isDirectory()) {
			String path = file.getPath();
			String name = "";
			File wfile = null;
			
			File[] files = file.listFiles();
			int sizeArr[] = new int[files.length];
			int fileSize = 0;
			int fileNum = 1;
			try {
	//			DataOutputStream dos=new DataOutputStream(new FileOutputStream(wfile));
				
				int numOfFile = 0;
				for (int i = 0; i < files.length; i++) {
					String fileName = files[i].getName();
					if (!files[i].isDirectory() && !fileName.contains("data.wf")) {
						numOfFile++;
					}
					if (files[i].isDirectory() || fileName.contains("data.wf")) {
						sizeArr[i] = 0;
					} else {
						FileInputStream in = new FileInputStream(files[i]);
						sizeArr[i] = in.available();
					}
				}
				int count = 0;
				List<File> list = new ArrayList<File>();
				for (int i = 0; i < files.length; i++) {
					String fileName = files[i].getName();
					if (files[i].isDirectory() || fileName.contains("data.wf")) {
						continue;
					}
					if (count > MAX_FILE_LENGTH) {
						processPackFile(list, path, fileNum++, encryptString);
						list = new ArrayList<File>();
						list.add(files[i]);
						count = sizeArr[i];
					} else {
						list.add(files[i]);
						count += sizeArr[i];
					}
				}
				
				if (list.size() > 0) {
					processPackFile(list, path, fileNum++, encryptString);
				}
								
				
				for (int i = 0; i < files.length; i++) {
					String filename = files[i].getName();
					if (files[i].isDirectory() ) {
						processPackFile(files[i], encryptString);
					} else if (!filename.contains("data.wf")) {
						files[i].delete();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
	private static void processPackFile(List<File>list, String path, int fileNum, String encryptString) throws IOException {
		String name = String.format("%s/data.wf.%d", path, fileNum);
		File wfile = new File(name);
		DataOutputStream dos=new DataOutputStream(new FileOutputStream(wfile));
		
		dos.writeInt(list.size());
		
		for (int i = 0; i < list.size(); i++) {
			packFile(dos, list.get(i), encryptString);
		}
		
		dos.flush();
		dos.close();
	}
	
	public static void processUnPackFile(File file, String encryptString) {
		if (file == null) {
			return;
		}
		
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				String name = files[i].getName();
				if (!files[i].isDirectory() && !name.contains("data.wf")) {
					files[i].delete();
				}
			}
			
			for (int i = 0; i < files.length; i++) {
				String name = files[i].getName();
				if (!files[i].isDirectory() && name.contains("data.wf")) {
					unpackFile(files[i], encryptString);
				} else if (files[i].isDirectory()) {
					processUnPackFile(files[i], encryptString);
				}
			}
		}
	}
	
	
	
	private static int packFile(DataOutputStream dos, File file,
			String encryptString) {
		FileInputStream in;
		byte[] encryBytes = encryptString.getBytes();
		int m = encryBytes.length;
		int rt = 0;
		try {
			in = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(in);

			rt = in.available();
			int len = 65536;
			byte[] itemBuf = new byte[len];

			String fileName = file.getName();
			int fileSize = in.available();

			byte[] fileNameBytes = fileName.getBytes();
			int lenghOfFileName = fileNameBytes.length;

			// length of file name
			dos.writeInt(lenghOfFileName);

			// file name
			dos.write(fileNameBytes);

			// length of file
			dos.writeInt(fileSize);

			int ur = dis.available();
			int done = 0;
			while (ur > 0) {
				int r = dis.read(itemBuf, 0, len);

				for (int i = 0; i < r; i++) {
					itemBuf[i] ^= encryBytes[(done + i) % m];
				}

				done += r;
				ur = dis.available();

				dos.write(itemBuf, 0, r);
			}
		
			in.close();
			dis.close();
			
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rt;
	}

	public static void unpackFile(File wfFile, String encryptString) {
		FileInputStream in;
		byte[] encryBytes = encryptString.getBytes();
		int m = encryBytes.length;
		int len = 65536;
		try {
			in = new FileInputStream(wfFile);
			DataInputStream dis = new DataInputStream(in);
			
			int numOfFile = dis.readInt();
			byte[] data = new byte[len];
			
			for (int i = 0; i < numOfFile; i++) {
				int lengthOfFileName = dis.readInt();
				dis.read(data, 0, lengthOfFileName);
				data[lengthOfFileName] = 0;
				data[lengthOfFileName+1] = 0;
				
				String fileName = new String(data, 0, lengthOfFileName);
				int idx = wfFile.getAbsolutePath().lastIndexOf("data.wf");
				String fullName = wfFile.getAbsolutePath().substring(0, idx) + fileName;
				File file = new File(fullName);
				
				int fileSize = dis.readInt();
								
				DataOutputStream dos=new DataOutputStream(new FileOutputStream(file)); 
				int done = 0;
				while (done < fileSize) {
					int pending = fileSize-done > len? len : fileSize - done;
					int r = dis.read(data, 0, pending);
					
					for (int j = 0; j < r; j++) {
						data[j] ^= encryBytes[(done + j) % m];
					}
					
					dos.write(data, 0, r);
					done += r;
				}
				dos.flush();
				dos.close();
			}
			
			dis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testPack() {
		String filepath = "D:\\My Documents\\java\\acm\\matlab\\txt";
		File file = new File(filepath);

		String encryptString = "scorpiowf";
		processPackFile(file, encryptString);
		deleteDataFile(file);
	}
	
	@Test
	public void testUnpack() {
		String filepath = "D:\\My Documents\\java\\acm\\matlab\\txt";
		File file = new File(filepath);

		String encryptString = "scorpiowf";
		processUnPackFile(file, encryptString);
	}
}
