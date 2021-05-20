package com.currencyconversion.springbootcurrencyconversionapplication.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.currencyconversion.springbootcurrencyconversionapplication.entity.HistoricalCurrencyRates;
import com.currencyconversion.springbootcurrencyconversionapplication.entity.User;
import com.currencyconversion.springbootcurrencyconversionapplication.model.HistoricalCurrencyRatesQuery;
import com.currencyconversion.springbootcurrencyconversionapplication.model.UserLoginDetails;
import com.currencyconversion.springbootcurrencyconversionapplication.model.UserRegistrationDetails;
import com.currencyconversion.springbootcurrencyconversionapplication.repository.UserRepository;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping(path = "/registration")
	public String getRegistrationForm() {
		return "registration-form";
	}

	@PostMapping(path = "/registration")
	public String submitRegistrationForm(UserRegistrationDetails userRegistrationDetails) {
		User user = null;
		if(null != userRegistrationDetails) {
			user = new User(userRegistrationDetails.getFirstName(), 
					userRegistrationDetails.getLastName(), 
					userRegistrationDetails.getEmailId(), 
					userRegistrationDetails.getPassword());
		}
		userRepository.save(user);
		return "registration-successful";
	}

	@GetMapping(path = "/login")
	public String getLoginForm() {
		return "login-form";
	}

	@PostMapping(path = "/login")
	public String submitLoginForm(UserLoginDetails userLoginDetails, Model model) {
		User user = null;
		if(null != userLoginDetails) {
			user = userRepository.findUserByEmailId(userLoginDetails.getEmailId());
			if(null != user) {
				if(user.getPassword().equals(userLoginDetails.getPassword())) {
					model.addAttribute("historicalCurrencyRatesQuery", new HistoricalCurrencyRatesQuery());
					
					HashMap<String, Double> rates = new HashMap<>();
					model.addAttribute("rates", rates);
					
					List<HistoricalCurrencyRates> historicalRates = 
							new ArrayList<>();
					model.addAttribute("historicalRates", historicalRates);
					return "dashboard";
				}
			}

		}
		return "login-form";
	}

}
