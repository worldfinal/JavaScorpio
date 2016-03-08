package com.scorpiowf.filevisit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileVisitProcessor {
	private IFileVisitor vistor = null;
	private List<String> list = null;
	
	public FileVisitProcessor(IFileVisitor vistor) {
		this.vistor = vistor;
	}
	
	public List<String> visit(String path) {
		list = new ArrayList<String>();
		File file = new File(path);
		if (file.exists()) {
			visit(file, 1);
		}
		return list;
	}
	
	private void visit(File file, int layer) {
		FileInfo info = new FileInfo();
		info.setFilePath(file.getAbsolutePath());
		info.setFileName(file.getName());
		info.setFile(file.isFile());
		info.setLayer(layer);
		
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			info.setNumOfChildren(files.length);
			for (File f: files) {
				visit(f, layer+1);
			}
			try {
				vistor.visitFolder(file, info);
			} catch (Exception e) {}
			
		} else {
			list.add(file.getAbsolutePath());
			try {
				vistor.visitFile(file, info);
			} catch (Exception e) {}
		}
	}
}
