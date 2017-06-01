package com.progresssoft.manager;

import java.io.InputStream;

import com.progresssoft.dto.FileDto;

public interface DataIngestionManager {

	void importData(FileDto file);

}
