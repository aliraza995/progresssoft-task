package com.progresssoft.util;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.progresssoft.dto.FileDto;
import com.progresssoft.entity.FileEntity;

@Mapper
public interface FileMapper {

	FileMapper INSTANCE = Mappers.getMapper(FileMapper.class);

	FileEntity toEntity(FileDto dto);

	FileDto toDto(FileEntity dto);
}
