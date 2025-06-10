package com.lamashkevich.tax_service.service;

import com.lamashkevich.tax_service.dto.TaxRateRequestDto;
import com.lamashkevich.tax_service.dto.TaxRateResponseDto;
import com.lamashkevich.tax_service.entity.TaxRate;
import com.lamashkevich.tax_service.exception.TaxRateNotFoundException;
import com.lamashkevich.tax_service.mapper.TaxRateMapper;
import com.lamashkevich.tax_service.repository.TaxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaxService {

    private final TaxRepository taxRepository;
    private final TaxRateMapper taxRateMapper;

    public List<TaxRateResponseDto> getAll() {
        log.info("Getting all taxes");
        return taxRepository.findAll().stream()
                .map(taxRateMapper::taxRateToTaxRateResponseDto)
                .toList();
    }

    public BigDecimal getTotalRateByRegionAndPosition(String region, String position) {
        log.info("Getting total tax rate by region and position");
        return taxRepository.findTotalRateByRegionAndPosition(region, position)
                .orElseThrow(TaxRateNotFoundException::new);
    }

    @Transactional
    public TaxRateResponseDto createOrUpdate(TaxRateRequestDto requestDto) {
        log.info("Creating or updating tax rate: {}", requestDto);
        TaxRate taxRate = taxRepository.findTaxRateByRegionAndPosition(requestDto.region(), requestDto.position())
                .orElseGet(() -> {
                    log.debug("TaxRate not fond. Creating new TaxRate");
                    return TaxRate.builder()
                            .region(requestDto.region())
                            .position(requestDto.position())
                            .build();
                });

        taxRate.setTotalRate(requestDto.rate());
        taxRepository.save(taxRate);

        return taxRateMapper.taxRateToTaxRateResponseDto(taxRate);
    }

}
