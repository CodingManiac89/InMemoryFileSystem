package engine;

import java.util.List;
import java.util.stream.Collectors;

import ds.FileSystemDS;
import ds.FileSystem;
import models.*;

public class FileSystemService {
	public static void main(String[] args) {
		FileSystemDS ds = new FileSystemDS();
		FileSystemEntry subfolder = new Directory();
		subfolder.setName("subfolder");
		ds.createFile("root", subfolder);
		System.out.println(ds.currentDirectory().getEntry().getName());
		
		FileSystemEntry file = new File();
		file.setName("f1");
		((File)file).setContent("This is a text file");
		ds.createFile("root/subfolder", file);
		System.out.println(ds.currentDirectory().getEntry().getName());
		
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
		
		FileSystemEntry folder3 = new Directory();
		folder3.setName("subfolder3");
		ds.createFile("root/subfolder", folder3);
		
		FileSystemEntry folder4 = new Directory();
		folder4.setName("subfolder4");
		ds.createFile("root/subfolder", folder4);
		
		FileSystemEntry folder5 = new Directory();
		folder5.setName("subfolder5");
		ds.createFile("root/subfolder", folder5);
		
		FileSystemEntry nestedFile = new File();
		nestedFile.setName("f4");
		((File)nestedFile).setContent("This is internal file");
		ds.createFile("root/subfolder/subfolder2", nestedFile);
		
		System.out.println(ds.getAllFilesInFolder("root/subfolder").stream().map(File::getName).collect(Collectors.toList()));
		System.out.println(ds.getAllFilesInFolderRecursively("root/subfolder").stream().map(File::getName).collect(Collectors.toList()));
		
		System.out.println(ds.searchFilesByPrefix("root/subfolder", "sub"));
		
		System.out.println(ds.changeDirectory("root/subfolder/subfolder5"));
		
		System.out.println(ds.getAllEntriesRecursively("//root").stream().map(FileSystemEntry::getPath).collect(Collectors.toList()));
		
	}
}
