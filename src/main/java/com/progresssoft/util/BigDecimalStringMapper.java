package com.progresssoft.util;

import java.math.BigDecimal;

import org.mapstruct.Mapper;

/**
 * Map BigDecimal to String and vice versa.
 */
@Mapper
public abstract class BigDecimalStringMapper {

    /**
     * Converts {@link BigDecimal} to String.
     * @param bigDecimal BigDecimal instance.
     * @return String value of status
     */
    public String toStatusString(final BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        
        return bigDecimal.toString();
    }
    
    /**
     * Converts String to {@link BigDecimal}.
     * @param number String number value
     * @return BigDecimal instance
     */
    public BigDecimal toBigDecimal(final String number) {
        if (number == null) {
            return null;
        }
        return new BigDecimal(number);
    }

}
