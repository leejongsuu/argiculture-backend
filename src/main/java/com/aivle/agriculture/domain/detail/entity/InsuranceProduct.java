package com.aivle.agriculture.domain.detail.entity;

import com.aivle.agriculture.domain.detail.enums.InsuredItem;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
@Getter
@Entity
public class InsuranceProduct {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private InsuredItem cropType;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private String pdfUrl;
    private LocalDate startDate;
    private LocalDate endDate;

    // getter, setter, builder 등 Lombok 사용 가능
}
