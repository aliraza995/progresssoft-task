package com.progresssoft.dao;

import java.util.List;

import com.progresssoft.entity.FileEntity;

public interface FileDao {

	FileEntity add(FileEntity entity);

	FileEntity getById(String id);

	List<FileEntity> find(String filename);

}
