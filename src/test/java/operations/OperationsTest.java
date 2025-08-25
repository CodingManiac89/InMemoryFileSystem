package operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ds.FileSystem;
import ds.FileSystemDS;
import models.Directory;
import models.File;
import models.FileSystemEntry;

public class OperationsTest {
	private FileSystemDS ds;
	
	@BeforeEach
	public void setup() {
		ds = new FileSystemDS();
		FileSystemEntry folder1 = new Directory();
		folder1.setName("folder1");
		ds.createFile("root", folder1);
		FileSystemEntry file = new File();
		file.setName("f.txt");
		ds.createFile("root/folder1", file);
		FileSystemEntry folder2 = new Directory();
		folder2.setName("folder2");
		ds.createFile("root", folder2);
	}
	
	@Test
	public void testDeletion() {
		ds.deleteEntry("root/folder1");
		assertNull(ds.changeDirectory("root/folder1"));
	}
	
	@Test
	public void testMove() {
		ds.move("root/folder2", "root/folder1");
		FileSystem node = ds.changeDirectory("root/folder2/folder1");
		assertEquals("root/folder2/folder1", node.getEntry().getPath());
	}
}
