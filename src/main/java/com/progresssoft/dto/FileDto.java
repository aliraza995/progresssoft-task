package com.progresssoft.dto;

import java.util.HashSet;
import java.util.Set;

public class FileDto {

	private String id;
	private String fileName;
	private String contentType;
	private String content;
	private byte[] contentHash;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte[] getContentHash() {
		return contentHash;
	}

	public void setContentHash(byte[] contentHash) {
		this.contentHash = contentHash;
	}

	public String getContentType() {
		return contentType;
	}
	

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
