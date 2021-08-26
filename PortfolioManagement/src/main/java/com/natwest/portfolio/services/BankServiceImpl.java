package com.natwest.portfolio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.natwest.portfolio.model.Bank;
import com.natwest.portfolio.repository.BankRepo;

public class BankServiceImpl implements BankService{

	@Autowired
	BankRepo bankrepo;

	@Override
	public Bank addbank(Bank bankObj) {
		Optional<Bank> bankopt= bankrepo.findById(bankObj.getAccountNumber());
		
		if(bankopt.isPresent())
		{		    	
			System.out.println("Bank Acount number already belongs to someone else");
			return null;
		}
		else {
			bankrepo.save(bankObj);
			return bankObj;
		}
	}

	@Override
	public List<Bank> viewbanks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteBank(Long accountNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Bank updateBank(Bank bankObj) {
		// TODO Auto-generated method stub
		return null;
	}

}
