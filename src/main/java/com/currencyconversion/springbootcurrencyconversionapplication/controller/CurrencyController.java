package com.currencyconversion.springbootcurrencyconversionapplication.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.currencyconversion.springbootcurrencyconversionapplication.entity.HistoricalCurrencyRates;
import com.currencyconversion.springbootcurrencyconversionapplication.model.HistoricalCurrencyRatesQuery;
import com.currencyconversion.springbootcurrencyconversionapplication.repository.HistoricalCurrencyRatesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CurrencyController {
	
	@Autowired
	HistoricalCurrencyRatesRepository historicalCurrencyRatesRepository;
	
	@GetMapping(path = "/dashboard")
	public String getDashboard(Model model) {
		
		model.addAttribute("historicalCurrencyRatesQuery", new HistoricalCurrencyRatesQuery());
		
		HashMap<String, Double> rates = new HashMap<>();
		model.addAttribute("rates", rates);
		
		List<HistoricalCurrencyRates> historicalRates = 
				new ArrayList<>();
		model.addAttribute("historicalRates", historicalRates);
		
		return "dashboard";
	}
	
	@PostMapping(path = "/dashboard")
	public String doCurrencyConversion(HistoricalCurrencyRatesQuery historicalCurrencyRatesQuery, Model model) {
		RestTemplate restTemplate = new RestTemplate();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String fooResourceUrl
		  = "http://api.currencylayer.com/historical?access_key=<enter-your-access-key-here>" +
				  "&source=" + historicalCurrencyRatesQuery.getCurrency() + 
				  "&date=" + dateFormat.format(historicalCurrencyRatesQuery.getDate());
		ResponseEntity<String> response
		  = restTemplate.getForEntity(fooResourceUrl, String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		JsonNode conversionRates = null;
		HashMap<String, Double> rates = new HashMap<>();
		List<HistoricalCurrencyRates> historicalRates = 
				new ArrayList<>();
		try {
			System.out.println(response.getBody());
			root = mapper.readTree(response.getBody());
			conversionRates = root.path("quotes");
			if(null != conversionRates) {
				System.out.println("result: " + conversionRates);
				Iterator<Entry<String, JsonNode>> iterator = conversionRates.fields();
				while(iterator.hasNext()) {
					Entry<String, JsonNode> conversionRate = iterator.next();
					rates.put(conversionRate.getKey(), conversionRate.getValue().asDouble());
				}
				HistoricalCurrencyRates historicalCurrencyRates = 
						new HistoricalCurrencyRates(historicalCurrencyRatesQuery.getCurrency(), 
								historicalCurrencyRatesQuery.getDate(), 
								conversionRates.toPrettyString());
				historicalCurrencyRatesRepository.save(historicalCurrencyRates);
			}
			historicalRates = historicalCurrencyRatesRepository.findHistoricalCurrencyRatesWithLimit();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
        model.addAttribute("rates", rates);
        model.addAttribute("historicalRates", historicalRates);
        
        return "dashboard";
    }

}
