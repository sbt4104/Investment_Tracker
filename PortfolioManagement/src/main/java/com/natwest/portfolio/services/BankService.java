package com.natwest.portfolio.services;

import java.util.List;

import com.natwest.portfolio.model.Bank;
import com.natwest.portfolio.repository.BankRepo;

public interface BankService {
	Bank addbank(Bank bankObj);
	
	List<Bank> viewbanks();

	
	boolean deleteBank(Long accountNo);
	
	Bank updateBank(Bank bankObj);
}
