package com.cheatsheet.repository;

import com.cheatsheet.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rate, Integer> {
    Rate findByCount(int count);
}
