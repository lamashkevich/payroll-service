package com.lamashkevich.tax_service.repository;

import com.lamashkevich.tax_service.entity.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface TaxRepository extends JpaRepository<TaxRate, Long> {

    @Query("SELECT t.totalRate FROM TaxRate t " +
            "WHERE LOWER(t.region) = LOWER(:region) " +
            "AND LOWER(t.position) = LOWER(:position)")
    Optional<BigDecimal> findTotalRateByRegionAndPosition(String region, String position);


    @Query("SELECT t FROM TaxRate t " +
            "WHERE LOWER(t.region) = LOWER(:region) " +
            "AND LOWER(t.position) = LOWER(:position)")
    Optional<TaxRate> findTaxRateByRegionAndPosition(String region, String position);

}
