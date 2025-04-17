package com.lamashkevich.tax_service.mapper;

import com.lamashkevich.tax_service.dto.TaxRateResponseDto;
import com.lamashkevich.tax_service.entity.TaxRate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaxRateMapper {

    TaxRateResponseDto taxRateToTaxRateResponseDto(TaxRate taxRate);

}
