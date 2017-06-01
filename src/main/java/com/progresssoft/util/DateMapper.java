package com.progresssoft.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class DateMapper {

	public static final DateMapper INSTANCE = Mappers.getMapper(DateMapper.class);

    public LocalDateTime asDate(final Long timestamp) {
        try {
            return timestamp == null ? null: LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.of("UTC"));
        } 
        catch (final Exception e) {
            throw new RuntimeException( e );
        }
    }
    
    public Long asTimestamp(final LocalDateTime time) {
        try {
            return time == null ? null: time.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
        } 
        catch (final Exception e) {
            throw new RuntimeException( e );
        }
    }
}
