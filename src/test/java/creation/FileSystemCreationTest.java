package creation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ds.FileSystemDS;
import models.Directory;
import models.File;
import models.FileSystemEntry;

public class FileSystemCreationTest {
	private FileSystemDS ds;
	
	@BeforeEach
	public void setup() {
		ds = new FileSystemDS();
	}
	
	@Test
	public void testSubfolderCreation() {
		FileSystemEntry folder = new Directory();
		folder.setName("folder1");
		ds.createFile("root", folder);
		ds.changeDirectory(folder.getName());
		assertEquals("folder1", ds.currentDirectory().getName());
	}
	
	@Test
	public void testFileCreation() {
		FileSystemEntry file = new File();
		file.setName("f.txt");
		ds.createFile("root", file);
		assertEquals(1,ds.getAllFilesInFolder("root").size());
	}
	
	
	@Test
	public void testFilesInSubfolderCreation() {
		FileSystemEntry folder = new Directory();
		folder.setName("subfolder");
		ds.createFile("root", folder);
		ds.changeDirectory(folder.getName());
		assertEquals("subfolder", ds.currentDirectory().getName());
		
		FileSystemEntry file = new File();
		file.setName("f.txt");
		ds.createFile("root/subfolder", file);
		assertEquals(1,ds.getAllFilesInFolder("subfolder").size());	
	}
	
	@Test
	public void testChangeDirectory() {
		FileSystemEntry folder = new Directory();
		folder.setName("f1");
		ds.createFile("root", folder);
		assertEquals("root",ds.currentDirectory().getName());
		
		ds.changeDirectory("f1");
		assertEquals("f1",ds.currentDirectory().getName());
	}
}
