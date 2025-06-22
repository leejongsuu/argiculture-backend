package com.aivle.agriculture.domain.detail.repository;

import com.aivle.agriculture.domain.detail.entity.InsuranceProduct;
import com.aivle.agriculture.domain.detail.enums.InsuredItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InsuranceProductRepository extends JpaRepository<InsuranceProduct, Long> {
    Optional<InsuranceProduct> findByCropType(InsuredItem cropType);
}