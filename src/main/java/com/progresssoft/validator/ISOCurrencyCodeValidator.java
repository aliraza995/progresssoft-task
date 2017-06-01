package com.progresssoft.validator;

import java.util.Currency;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ISOCurrencyCodeValidator implements ConstraintValidator<ISOCurrencyCode, String> {

    private static final Logger LOG = LogManager.getLogger();
    
    private String message;

    @Override
    public void initialize(final ISOCurrencyCode constraintAnno) {
        this.message = constraintAnno.message();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(final String code, final ConstraintValidatorContext context) {
        if (code == null) {
            return true;
        }
        
        for(Currency currency : Currency.getAvailableCurrencies()) {
        	if (currency.getCurrencyCode().equals(code)) {
        		return true;
        	}
        }
        
        return false;
    }

}