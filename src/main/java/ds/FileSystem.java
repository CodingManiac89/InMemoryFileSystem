package ds;

import java.util.HashMap;
import java.util.Map;

import constants.EntryType;
import models.FileSystemEntry;

public class FileSystem {
		Map<String,FileSystem> children = new HashMap<>();
		FileSystemEntry entry;
		EntryType type = EntryType.DIRECTORY;
		FileSystem parent;
		@Override
		public String toString() {
			return "FileSystem [children=" + children + ", entry=" + entry + ", type=" + type + "]";
		}
		public FileSystem getParent() {
			return parent;
		}
		public FileSystemEntry getEntry() {
			return entry;
		}
		
		
}
