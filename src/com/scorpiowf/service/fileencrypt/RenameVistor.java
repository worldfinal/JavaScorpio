package com.scorpiowf.service.fileencrypt;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.scorpiowf.filevisit.FileInfo;
import com.scorpiowf.filevisit.IFileVisitor;

public class RenameVistor implements IFileVisitor {

	@Override
	public String visitFile(File file, FileInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visitFolder(File file, FileInfo info) {
		
		RenameFile.renameFile(file);
		RenameFile.renamePath(file);
		return null;
	}

}
