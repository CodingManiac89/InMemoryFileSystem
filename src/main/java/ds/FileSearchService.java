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
		return findEntries(getSuffixNode(prefix, entryNameTree), fileNames, prefix, prefix);
	}

	private List<String> findEntries(EntryNamePrefix entryNameTree, List<String> fileNames, String prefix, String entryName) {	
		for(char c:entryNameTree.children.keySet()) {
			if(entryNameTree.children.get(c).isWord) {
				entryName=entryName+c;
				fileNames.add(entryName);
				entryName=prefix;
			}
			else {
				prefix = entryName+c;
				entryNameTree = entryNameTree.children.get(c);
				findEntries(entryNameTree,fileNames,prefix,prefix);
			}
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
	

}
