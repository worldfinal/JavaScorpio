package com.scorpiowf.service.fileencrypt;

import java.io.File;

import com.scorpiowf.filevisit.FileInfo;
import com.scorpiowf.filevisit.IFileVisitor;

public class EncryptFileVisitor implements IFileVisitor {

	@Override
	public String visitFile(File file, FileInfo info) {
		Encrypter.encryptFile(file.getAbsolutePath(), "scorpiowf");
		return null;
	}

	@Override
	public String visitFolder(File file, FileInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

}
