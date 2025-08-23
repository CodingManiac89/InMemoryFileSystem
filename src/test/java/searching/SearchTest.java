package searching;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ds.FileSystemDS;
import models.Directory;
import models.FileSystemEntry;

public class SearchTest {
	private FileSystemDS ds;
	
	@BeforeEach
	public void setup() {
		ds = new FileSystemDS();
		FileSystemEntry folder = new Directory();
		folder.setName("sac");
		ds.createFile("root", folder);
		FileSystemEntry folder2 = new Directory();
		folder2.setName("sub1");
		ds.createFile("root", folder2);
		FileSystemEntry folder3 = new Directory();
		folder3.setName("sub2");
		ds.createFile("root", folder3);
		FileSystemEntry folder4 = new Directory();
		folder4.setName("sub3");
		ds.createFile("root", folder4);
	}
	
	@Test
	public void testSearchUsingPrefix() {
		List<String> results = ds.searchFilesByPrefix("root", "sub");
		assertEquals(3,results.size());
	}
	
	@Test
	public void testSearchUsingPrefix2() {
		List<String> results = ds.searchFilesByPrefix("root", "s");
		assertEquals(4,results.size());
	}
}
