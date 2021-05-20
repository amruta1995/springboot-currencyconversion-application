package com.currencyconversion.springbootcurrencyconversionapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.currencyconversion.springbootcurrencyconversionapplication.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE u.emailId LIKE ?1%")
	public User findUserByEmailId(String emailId);

}
