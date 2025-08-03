package engine;

import java.util.List;
import java.util.stream.Collectors;

import ds.FileSystemDS;
import models.*;

public class FileSystemService {
	public static void main(String[] args) {
		FileSystemDS ds = new FileSystemDS();
		FileSystemEntry subfolder = new Directory();
		subfolder.setName("subfolder");
		ds.createFile("/root/subfolder", subfolder);
		System.out.println(ds.currentDirectory().getName());
		
		FileSystemEntry file = new File();
		file.setName("f1");
		((File)file).setContent("This is a text file");
		ds.createFile("root/subfolder", file);
		System.out.println(ds.currentDirectory().getName());
		
		FileSystemEntry file2 = new File();
		file2.setName("f2");
		((File)file2).setContent("This is a image file");
		ds.createFile("root/subfolder", file2);
		
		FileSystemEntry file3 = new File();
		file3.setName("f3");
		((File)file3).setContent("This is a video file");
		ds.createFile("root/subfolder", file3);
		
		FileSystemEntry folder2 = new Directory();
		folder2.setName("subfolder2");
		ds.createFile("root/subfolder", folder2);
		
		FileSystemEntry nestedFile = new File();
		nestedFile.setName("f4");
		((File)nestedFile).setContent("This is internal file");
		ds.createFile("root/subfolder/subfolder2", nestedFile);
		
		System.out.println(ds.getAllFilesInFolder("subfolder").stream().map(File::getName).collect(Collectors.toList()));
		System.out.println(ds.getAllFilesInFolderRecursively("subfolder").stream().map(File::getName).collect(Collectors.toList()));
		
	}
}
