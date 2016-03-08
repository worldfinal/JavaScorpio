package com.scorpiowf.service.fileencrypt;

import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class RenameFile {
	private final static String picext[] = new String[]{"jpg", "jpeg", "gif", "png"};
	private final static String movext[] = new String[]{"avi", "mp4", "rmvb"};
	private final static String picextHide[] = new String[]{".tmp", ".sys", ".qcp"};
	private final static String movextHide[] = new String[]{".TPY", ".SST", ".LHF"};
	
	private final static String folderName[] = new String[]{"安装路径", "安装文件", "项目文件", "二进制文件", "程序管理"};
	private final static String filePattern[] = new String[]{
		"data%d", "java_%02d_tmp", "struts2013_%03d", "TmpFile%d" 
	};
	@Test
	public void testRenameFile() {
		String file = "D:\\test\\upan\\安装路径";
		RenameFile.renameFileBase64(new File(file));
	}
	@Test
	public void testRevertFile() {
		String file = "D:\\test\\upan\\安装路径";
		RenameFile.revertFileBase64(new File(file));
	}
	@Test
	public void test() {
		String fromname = "d:\\test\\wf";
		String toname = "d:\\test\\www1";
		rename(fromname, toname);
	}
	@Test
	public void testRenamePath() {
		String path = "D:\\test\\www\\3";
		renamePath(new File(path));
	}
	@Test
	public void testDescript() {
		String path = "D:\\My Documents\\java\\acm\\matlab\\2015\\test";
		RenameFile.decriptRenameFile(path);
	}
	public static void decriptRenameFile(String folderPath) {
		File file = new File(folderPath);
		if (file == null || file.isFile() || !file.exists()) {
			System.out.println("打开路径失败!");
			return;
		}
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			String fileName = f.getAbsolutePath();
	//		System.out.println(fileName);
			if (isEncriptJpg(fileName)) {
				String toFileName = changeExt(fileName, ".jpg");
				System.out.println(toFileName);
				rename(fileName, toFileName);
			} else if (isEncriptMov(fileName)) {
				String toFileName = changeExt(fileName, ".mp4");
				System.out.println(toFileName);
				rename(fileName, toFileName);
			}
		}
	}
	public static void rename(String fromName, String toName) {
		File file1 = new File(fromName);
		File file2 = new File(toName);
		if (file1.exists() && !file2.exists()) {
			file1.renameTo(file2);
			System.out.println(String.format("[%s]->[%s]", fromName, toName));
		}
	}
	public static void revertFileBase64(File file) {
		File[] files = file.listFiles();
		List<File> list = new ArrayList<File>();
		for (File f : files) {
			if (f.isFile()) {
				list.add(f);
			}
		}
		Collections.sort(list, new Comparator<File>(){

			@Override
			public int compare(File arg0, File arg1) {
				return (int)(arg0.lastModified() - arg1.lastModified());
			}
			
		});
		for (int i = 0; i < list.size(); i++) {
			File f = list.get(i);
			String orginName = f.getName();
			String name1 = f.getParent() + "\\" + orginName;
			
			String base64 = new String(Base64.getDecoder().decode(orginName));
			String name2 = f.getParent() + "\\" + base64;
			rename(name1, name2);
		}
	}
	public static void renameFileBase64(File file) {
		File[] files = file.listFiles();
		List<File> list = new ArrayList<File>();
		for (File f : files) {
			if (f.isFile()) {
				list.add(f);
			}
		}
		Collections.sort(list, new Comparator<File>(){

			@Override
			public int compare(File arg0, File arg1) {
				return (int)(arg0.lastModified() - arg1.lastModified());
			}
			
		});
		for (int i = 0; i < list.size(); i++) {
			File f = list.get(i);
			String orginName = f.getName();
			String name1 = f.getParent() + "\\" + orginName;
			String base64 = Base64.getEncoder().encodeToString(orginName.getBytes());
			String name2 = f.getParent() + "\\" + base64;
			rename(name1, name2);
		}
	}
	public static void renameFile(File file) {
		File[] files = file.listFiles();
		List<File> list = new ArrayList<File>();
		for (File f : files) {
			if (f.isFile()) {
				list.add(f);
			}
		}
		Collections.sort(list, new Comparator<File>(){

			@Override
			public int compare(File arg0, File arg1) {
				return (int)(arg0.lastModified() - arg1.lastModified());
			}
			
		});
		String pattern = getFileNamePattern();
		String picExt = getImgExt();
		String movExt = getMovieExt();
		for (int i = 0; i < list.size(); i++) {
			File f = list.get(i);
			String orginName = f.getName();
			String name = f.getParent() + "\\" + String.format(pattern, i);
			if (isJpg(orginName)) {
				name += picExt;
			} else if (isMov(orginName)){
				name += movExt;
			} else {
				System.out.println("[ERROR]unknowext:" + f.getAbsolutePath());
			}
			rename(f.getAbsolutePath(), name);
			System.out.println(String.format("  From[%s]to[%s]", orginName, name));
		}
	}
	
	public static void renamePath(File file) {
		String rootPath = file.getParent();
		String folderName = getFolderName();
		String toName = rootPath + "\\" + folderName;
		File f = new File(toName);
		int idx = 2;
		while (f.exists()) {
			String s = String.format("%s\\%s%d", rootPath, folderName, idx);
			idx++;
			f = new File(s);
		}
		rename(file.getAbsolutePath(), f.getAbsolutePath());
		String str = String.format("from[%s] to [%s]", file.getAbsolutePath(), f.getAbsolutePath());
		System.out.println(str);
		
	}
	private static String getMovieExt() {
		String rs = getRandomValue(movextHide);
		return rs;
	}
	private static String getImgExt() {
		String rs = getRandomValue(picextHide);
		return rs;
	}
	private static String getFolderName() {
		String rs = getRandomValue(folderName);
		return rs;
	}
	private static String getFileNamePattern() {
		String rs = "";
		rs = getRandomValue(filePattern);
		return rs;
	}
	private static String getRandomValue(String[] str) {
		String rs = "";
		int rt = (int)(Math.random() * 77117.0);
		rt %= str.length;
		rs = str[rt];
		return rs;
	}
	private static boolean isJpg(String name) {
		String fileName = name.toLowerCase();
		for (String ext: picext) {
			if (fileName.indexOf(ext) >= 0) {
				return true;
			}
		}
		return false;
	}
	private static boolean isEncriptJpg(String name) {
		String fileName = name;
		for (String ext: picextHide) {
			if (fileName.indexOf(ext) >= 0) {
				return true;
			}
		}
		return false;
	}
	private static boolean isEncriptMov(String name) {
		String fileName = name;
		for (String ext: movextHide) {
			if (fileName.indexOf(ext) >= 0) {
				return true;
			}
		}
		return false;
	}
	private static boolean isMov(String name) {
		String fileName = name.toLowerCase();
		for (String ext: movext) {
			if (fileName.indexOf(ext) >= 0) {
				return true;
			}
		}
		return false;
	}
	private static String changeExt(String fileName, String ext) {
		if (fileName == null || fileName.length() < 4) {
			return "";
		}
		if (fileName.charAt(fileName.length()-4) == '.') {
			return fileName.substring(0, fileName.length() - 4) + ext;
		}
		return fileName;
	}
}
