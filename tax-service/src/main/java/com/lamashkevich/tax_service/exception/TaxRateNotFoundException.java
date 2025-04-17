package com.lamashkevich.tax_service.exception;

public class TaxRateNotFoundException extends RuntimeException {

    public TaxRateNotFoundException() {
        super("Tax rate not found");
    }

}
