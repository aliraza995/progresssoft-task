package com.progresssoft.util;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.progresssoft.dto.DealDto;
import com.progresssoft.entity.DealEntity;
import com.progresssoft.entity.InvalidDealEntity;

@Mapper(uses = { FileMapper.class, BigDecimalStringMapper.class, DateMapper.class })
public interface DealMapper {

	DealMapper INSTANCE = Mappers.getMapper(DealMapper.class);
	
	DealEntity toEntity(DealDto dto);
	
	Set<DealDto> toDto(Set<DealEntity> dto);
	
	DealDto toDto(DealEntity dto);

	InvalidDealEntity toInvalidEntity(DealDto dto);
}
