package ds;

import java.util.*;

public class FileSearchService {
	private Map<String,EntryNamePrefix> findFileMap;
	
	public FileSearchService() {
		findFileMap = new HashMap<>();
	}
	
	private class EntryNamePrefix{
		Map<Character,EntryNamePrefix> children=new HashMap<>();
		boolean isWord=false;
	}
	
	public void addFile(String folder, String file) {
		EntryNamePrefix  prefix=null;
		if(findFileMap.get(folder)!=null) {
			prefix = findFileMap.get(folder);
		}
		else {
			prefix = new EntryNamePrefix();
		}
		EntryNamePrefix temp=prefix;
		for(char c: file.toCharArray()) {
			if(temp.children.get(c)==null) {
				EntryNamePrefix newEntry = new EntryNamePrefix();
				temp.children.put(c, newEntry);
			}
			temp=temp.children.get(c);
		}
		temp.isWord=true;
		findFileMap.put(folder, prefix);
		
	}
	
	public List<String> getAllEntriesByPrefixInFolder(String folder, String prefix){
		if(findFileMap.get(folder)==null) {
			System.err.println("Cannot find folder");
			return null;
		}
		EntryNamePrefix entryNameTree = findFileMap.get(folder);
		List<String> fileNames = new ArrayList<>();
		StringBuilder prefixBuilder = new StringBuilder(prefix);
		return findEntries(getSuffixNode(prefix, entryNameTree), fileNames, prefixBuilder);
	}

	private List<String> findEntries(EntryNamePrefix entryNameTree, List<String> fileNames, StringBuilder prefix) {	
		for(char c:entryNameTree.children.keySet()) {
			EntryNamePrefix node = entryNameTree.children.get(c);
			prefix.append(c);
			if(node.isWord) {
				fileNames.add(prefix.toString());
			}
			findEntries(node,fileNames,prefix);
			prefix.deleteCharAt(prefix.length()-1);
		}
		return fileNames;
		
	}
	

	private EntryNamePrefix getSuffixNode(String prefix, EntryNamePrefix prefixNode) {
		EntryNamePrefix temp = prefixNode;
		for(char c:prefix.toCharArray()) {
			if(temp.children.get(c)==null) {
				System.err.println("No such file or directory found from the prefix");
				return null;
			}
			temp = temp.children.get(c);
		}
		return temp;
	}
	
	public void removeFile(String file) {
		if(findFileMap.get(file)==null) {
			System.out.println("No such file exists");
			return;
		}
		findFileMap.remove(file);
	}
	

}
