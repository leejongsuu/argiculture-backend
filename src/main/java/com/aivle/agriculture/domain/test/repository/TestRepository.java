package com.aivle.agriculture.domain.test.repository;

import com.aivle.agriculture.domain.test.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}
