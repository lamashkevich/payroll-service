package com.lamashkevich.tax_service.controller;

import com.lamashkevich.tax_service.dto.TaxRateRequestDto;
import com.lamashkevich.tax_service.dto.TaxRateResponseDto;
import com.lamashkevich.tax_service.service.TaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/taxes")
@RequiredArgsConstructor
public class TaxController {

    private final TaxService taxService;

    @GetMapping
    public List<TaxRateResponseDto> getAll() {
        return taxService.getAll();
    }

    @GetMapping("/rate")
    public BigDecimal getRateByRegionAndPosition(@RequestParam String region,
                                                 @RequestParam String position) {
        return taxService.getTotalRateByRegionAndPosition(region, position);
    }

    @PostMapping
    public TaxRateResponseDto createOrUpdate(@RequestBody TaxRateRequestDto requestDto) {
        return taxService.createOrUpdate(requestDto);
    }
}
