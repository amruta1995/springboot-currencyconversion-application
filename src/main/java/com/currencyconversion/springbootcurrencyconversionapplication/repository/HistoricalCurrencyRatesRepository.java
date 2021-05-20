package com.currencyconversion.springbootcurrencyconversionapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.currencyconversion.springbootcurrencyconversionapplication.entity.HistoricalCurrencyRates;

public interface HistoricalCurrencyRatesRepository extends JpaRepository<HistoricalCurrencyRates, Long> {
	
	@Query(value = "SELECT * FROM HISTORICAL_CURRENCY_RATES ORDER BY id DESC LIMIT 10", nativeQuery = true)
	public List<HistoricalCurrencyRates> findHistoricalCurrencyRatesWithLimit();

}
