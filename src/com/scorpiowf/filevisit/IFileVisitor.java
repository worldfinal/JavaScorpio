package com.scorpiowf.filevisit;

import java.io.File;

public interface IFileVisitor {
	public String visitFile(File file, FileInfo info);
	public String visitFolder(File file, FileInfo info);
}
