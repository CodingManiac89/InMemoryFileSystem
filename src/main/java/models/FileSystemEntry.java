package models;

import java.time.DateTimeException;
import java.util.Date;

import constants.EntryType;

public abstract class FileSystemEntry {
	
	private EntryType type;
	private String name;
	private Date createdOn;
	private Date modifiedOn;
	private String path;
	
	public FileSystemEntry() {
		this.createdOn = new Date();
		this.modifiedOn = new Date();
		this.setType(EntryType.DIRECTORY);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return name.hashCode();
	}

	public EntryType getType() {
		return type;
	}

	public void setType(EntryType type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	

}
