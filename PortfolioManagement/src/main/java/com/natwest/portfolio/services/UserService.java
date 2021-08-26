package com.natwest.portfolio.services;

import java.util.List;
import java.util.Map;

import com.natwest.portfolio.exceptions.BankAccountAlreadyPresentException;
import com.natwest.portfolio.exceptions.BankNotPresentException;
import com.natwest.portfolio.exceptions.NotEnoughStocksToSellException;
import com.natwest.portfolio.exceptions.PortFolioAlreadyPresentException;
import com.natwest.portfolio.exceptions.PortFolioNotPresentException;
import com.natwest.portfolio.exceptions.TradeOperationNotAllowedException;
import com.natwest.portfolio.exceptions.UserAlreadyPresentException;
import com.natwest.portfolio.exceptions.UserNotPresentException;
import com.natwest.portfolio.model.Bank;
import com.natwest.portfolio.model.PortFolio;
import com.natwest.portfolio.model.Trade;
import com.natwest.portfolio.model.User;

public interface UserService {
	
	// User Operations
	User addUser(User usrObj) throws BankAccountAlreadyPresentException, UserAlreadyPresentException;	
	List<User> viewUsers();
	boolean deleteBus(String userid);
	User updateUser(User userObj);

	// Bank related operations
	public Bank addBank(User userObj) throws BankAccountAlreadyPresentException;
	public Bank updateBank(Bank bankObj, String userId) throws UserNotPresentException;
	public void deleteBank(String userId) throws UserNotPresentException, BankNotPresentException;
	
	// PortFolio related operations
	public PortFolio createPortfolio(PortFolio portfolio, String userId) throws PortFolioAlreadyPresentException, UserNotPresentException;
	public List<PortFolio> viewAllPortFolios(String userid) throws UserNotPresentException;
	public void deletePortfolio(String userid, String portName) throws UserNotPresentException, PortFolioNotPresentException;
	public Map<String,Integer> getstocksOfPortfolio(String userId, String portName) throws UserNotPresentException, PortFolioNotPresentException;
	public PortFolio viewPortFolio(String userId, String portName) throws UserNotPresentException, PortFolioNotPresentException;
	// Trade related operations
	public List<Trade> getTrades(String userId, String portName) throws UserNotPresentException, PortFolioNotPresentException;
	public Trade tradeStocks(String userId, String portName, Trade trade) throws UserNotPresentException, PortFolioNotPresentException, NotEnoughStocksToSellException, TradeOperationNotAllowedException;
}
