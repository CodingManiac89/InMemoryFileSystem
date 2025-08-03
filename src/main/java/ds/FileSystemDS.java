package ds;

import java.util.*;

import constants.EntryType;
import models.Directory;
import models.File;
import models.FileSystemEntry;

public class FileSystemDS {
	
	FileSystem fileSystem;
	Map<Integer,FileSystemEntry> pathObjMap = new HashMap<>();
	ThreadLocal<Integer> currentEntryId = ThreadLocal.withInitial(()->0);
	Map<String,FileSystem> entryDSMap = new HashMap<>();
	
	public FileSystemDS() {
		this.fileSystem = new FileSystem();
		FileSystemEntry entry = new Directory();
		entry.setName("root");
		createFile("/root",entry);
	}
	
	private class FileSystem{
		private Map<String,FileSystem> children = new HashMap<>();
		private FileSystemEntry entry;
		private EntryType type = EntryType.DIRECTORY;
		@Override
		public String toString() {
			return "FileSystem [children=" + children + ", entry=" + entry + ", type=" + type + "]";
		}
		
		
	}
	
	
	public void createFile(String path, FileSystemEntry entry) {
		FileSystem temp = this.fileSystem;
		String[] pathTokens = path.split("/");
		for(String token:pathTokens) {
			if(temp.children.get(token)!=null) {
				if(temp.children.get(token).type==EntryType.FILE) {
					System.err.println("Cannot create the object in this path");
					return;
				}
				temp = temp.children.get(token);
			}
		}
		
		FileSystem node = new FileSystem();
		node.entry=entry;
		if(entry instanceof File) {
			node.type=EntryType.FILE;
		}
		temp.children.put(entry.getName(), node);
		if(node.type==EntryType.DIRECTORY) {
			pathObjMap.put(entry.hashCode(), entry);
			changeDirectory(entry.getName());
			entryDSMap.put(entry.getName(), node);
		}
	}
	
	public FileSystemEntry changeDirectory(String name) {
		if(pathObjMap.get(name.hashCode())==null) {
			System.out.println("No such file or directory exisits");
			return null;
		}
		else {
			currentEntryId.set(name.hashCode());
			return pathObjMap.get(name.hashCode());
		}
		
	}
	
	public FileSystemEntry currentDirectory() {
		return pathObjMap.get(currentEntryId.get());
	}
	
   
	public List<File> getAllFilesInFolder(String folderName){
		List<File> files = new ArrayList<>();
		FileSystem folderNode = entryDSMap.get(folderName);
		for(FileSystem val:folderNode.children.values()) {
			if(val.type==EntryType.FILE) {
				files.add((File)val.entry);
			}
		}
		return files;
		
	}
	
	public List<File> getAllFilesInFolderRecursively(String folderName){
		List<File> files = new ArrayList<>();
		FileSystem folderNode = entryDSMap.get(folderName);
		return findFilesRecursively(folderNode,files);
	}

	private List<File> findFilesRecursively(FileSystem folderNode, List<File> files) {
		for(FileSystem fsNode:folderNode.children.values()) {
			if(fsNode.type==EntryType.FILE) {
				files.add((File)fsNode.entry);
			}
			else {
				findFilesRecursively(fsNode, files);
			}
		}
		return files;
		
	}
		
	
	
	
	
	
}



