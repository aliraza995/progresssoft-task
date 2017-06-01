package com.progresssoft.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.type.ClobType;

@Entity
@Table(name = "file")
public class FileEntity {

	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "content_type")
	private String contentType;
	
	@Lob
	@Column(name = "content")
	@Fetch(FetchMode.SELECT)
	private String content;

	@Column(name = "content_hash")
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
