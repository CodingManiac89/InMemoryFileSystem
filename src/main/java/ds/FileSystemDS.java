package ds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constants.EntryType;
import models.Directory;
import models.File;
import models.FileSystemEntry;

public class FileSystemDS {
	
	FileSystem fileSystem;
	ThreadLocal<String> currentEntryId = ThreadLocal.withInitial(()->"");
	Map<String,FileSystem> entryDSMap = new HashMap<>();
	FileSearchService searchService = new FileSearchService();
	
	public FileSystemDS() {
		this.fileSystem = new FileSystem();
		FileSystemEntry entry = new Directory();
		entry.setName("root");
		createFile("/",entry);
		currentEntryId.set("//"+entry.getName());
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
		node.parent=temp;
		entry.setPath(path+"/"+entry.getName());
		node.entry=entry;
		if(entry instanceof File) {
			node.type=EntryType.FILE;
		}
		temp.children.put(entry.getName(), node);
		if(node.type==EntryType.DIRECTORY) {
			entryDSMap.put(path+"/"+entry.getName(), node);
		}
		searchService.addFile(path, entry.getName());
	}
	
	public FileSystem changeDirectory(String name) {
		if(entryDSMap.get(name)==null) {
			System.out.println("No such file or directory exisits");
			return null;
		}
		else {
			currentEntryId.set(name);
			return entryDSMap.get(name);
		}
		
	}
	
	public FileSystem currentDirectory() {
		return entryDSMap.get(currentEntryId.get());
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
	
	public List<FileSystemEntry> getAllEntriesRecursively(String folderName){
		FileSystem node = entryDSMap.get(folderName);
		return getAllEntries(node,new ArrayList<FileSystemEntry>());
	}
	
	private List<FileSystemEntry> getAllEntries(FileSystem node, ArrayList<FileSystemEntry> entries) {
		for(FileSystem child:node.children.values()) {
			entries.add(child.entry);
			getAllEntries(child,entries);
		}
		return entries;
	}

	public List<String> searchFilesByPrefix(String folderName, String prefix){
		return searchService.getAllEntriesByPrefixInFolder(folderName, prefix);
	}
	
	public void deleteEntry(String entryName) {
		if(entryDSMap.get(entryName)==null) {
			System.out.println("No such entry exists");
			return;
		}
		else {
			FileSystem node = entryDSMap.get(entryName);
			FileSystem parent = node.parent;
			parent.children.remove(entryName);
			removeAllChildrenFromMap(node);
			entryDSMap.remove(entryName);
		}
	}
	
	private void removeAllChildrenFromMap(FileSystem node) {
		for(FileSystem child:node.children.values()) {
			entryDSMap.remove(child.entry.getPath());
			searchService.removeFile(child.entry.getPath());
			removeAllChildrenFromMap(child);
		}	
	}

	public void move(String dest, String source) {
		if(entryDSMap.get(source)==null) {
			System.out.println("No such entry exists");
			return;
		}
		if(entryDSMap.get(dest)==null) {
			System.out.println("No such destination exists");
			return;
		}
		
		FileSystem node = entryDSMap.get(source);
		node.parent.children.remove(source);
		
		FileSystem destNode = entryDSMap.get(dest);
		
		node.parent=destNode;
		entryDSMap.remove(node.entry.getPath());
		node.entry.setPath(destNode.entry.getPath()+"/"+source.split("/")[source.split("/").length-1]);
		updatePaths(node);
		destNode.children.put(source, node);
		entryDSMap.put(node.entry.getPath(), node);
	}
	
	private void updatePaths(FileSystem node) {
		for(FileSystem n:node.children.values()) {
			n.entry.setPath(n.parent.entry.getPath()+"/"+n.entry.getName());
			updatePaths(n);
		}
	}
}




