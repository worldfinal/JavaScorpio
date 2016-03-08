package com.scorpiowf.service.fileencrypt;

import org.junit.Test;

import com.scorpiowf.filevisit.FileVisitProcessor;
import com.scorpiowf.filevisit.IFileVisitor;

public class FileEncryptController {
	private String PATH = "D:\\My Documents\\java\\acm\\matlab\\2015v2\\ECHQ1"; 
	@Test
	public void doRenamePaths() {
		IFileVisitor renameVistor = new RenameVistor();
		FileVisitProcessor processor = new FileVisitProcessor(renameVistor);
		String path = PATH;
		processor.visit(path);
	}
	
	@Test
	public void doEncryptPaths() {
		IFileVisitor encryptVistor = new EncryptFileVisitor();
		FileVisitProcessor processor = new FileVisitProcessor(encryptVistor);
		String path = PATH;
		processor.visit(path);
	}
	
	@Test
	public void decriptRenameFile() {
		String path = PATH;
		RenameFile.decriptRenameFile(path);
	}
	
	@Test
	public void encrypteFile() {
		String fileName = "D:\\My Documents\\java\\acm\\matlab\\2015\\index.sys.jpg"; 
	//	fileName = "d:\\test\\a.png";
		Encrypter.encryptFile(fileName, "scorpiowf");
	}
	
	@Test
	public void encryptFolder() {
		String fileName = PATH;
		Encrypter.encryptFolder(fileName, "scorpiowf");
	}
}
