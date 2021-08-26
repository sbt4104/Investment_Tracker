package com.natwest.portfolio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.natwest.portfolio.repository.BankRepo;
import com.natwest.portfolio.repository.PortFolioRepo;
import com.natwest.portfolio.repository.TradeRepo;
import com.natwest.portfolio.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepo userrepo;
	
	@Autowired
	BankRepo bankrepo;

	@Autowired
	PortFolioRepo portfoliorepo;

	@Autowired
	TradeRepo traderepo;
	
	@Override
	public User addUser(User usrObj) throws BankAccountAlreadyPresentException, UserAlreadyPresentException {
		Optional<User> useropt= userrepo.findById(usrObj.getUserid());
		
		if(useropt.isPresent())
		{		    	
			throw new UserAlreadyPresentException("User Already present");
		}
		else {
			if(usrObj.getBank() != null) {
				try {
					addBank(usrObj);
				} catch (BankAccountAlreadyPresentException e) {
					throw new BankAccountAlreadyPresentException("Bank Acount number already belongs to someone else");
				}
			}
			userrepo.save(usrObj);
			return usrObj;
		}
	}


	@Override
	public List<User> viewUsers() {
		return userrepo.findAll();
	}

	@Override
	public boolean deleteBus(String userid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User updateUser(User userObj) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Bank addBank(User userObj) throws BankAccountAlreadyPresentException {
		Bank currBank = userObj.getBank();
		Optional<Bank> bankopt= bankrepo.findById(currBank.getAccountNumber());
		
		if(bankopt.isPresent())
		{		    	
			throw new BankAccountAlreadyPresentException("Bank Acount number already belongs to someone else");
		}
		else {
			currBank.setUser(userObj.getUserid());
			bankrepo.save(currBank);
			return currBank;
		}
	}


	@Override
	public Bank updateBank(Bank bankObj, String userId) throws UserNotPresentException {
		Optional<User> useropt= userrepo.findById(userId);

		if(useropt.isPresent())
		{
			User currUser = useropt.get();
			
			// remove the already present present account
			if(currUser.getBank() != null && currUser.getBank().getAccountNumber() != bankObj.getAccountNumber()) {
				bankrepo.deleteById(currUser.getBank().getAccountNumber());
			}

			currUser.setBank(bankObj);
			bankObj.setUser(userId);
			bankrepo.save(bankObj);
		
			currUser.setBank(bankObj);
			userrepo.save(currUser);
			return bankObj;
		} else {
			throw new UserNotPresentException("User not present");
		}
	}

	@Override
	public void deleteBank(String userId) throws UserNotPresentException, BankNotPresentException {
		Optional<User> useropt= userrepo.findById(userId);

		if(useropt.isPresent())
		{
			User currUser = useropt.get();
			Bank currBank = currUser.getBank();
			if(currBank == null) {
				throw new BankNotPresentException("Bank is already deleted");
			}
			// delete bank from Bank Table
			bankrepo.deleteById(currBank.getAccountNumber());

			// set Bank to null in details
			currUser.setBank(null);
			userrepo.save(currUser);
		} else {
			throw new UserNotPresentException("User not present");
		}
	}


	@Override
	public PortFolio createPortfolio(PortFolio portfolio, String userId) throws PortFolioAlreadyPresentException, UserNotPresentException{
			Optional<User> useropt= userrepo.findById(userId);

			if(useropt.isPresent())
			{
				User currUser = useropt.get();

				// check if PortFolio category is already present
				List<PortFolio> currPortfolios = currUser.getPortfolios();
				if(currPortfolios == null) {
					currPortfolios = new ArrayList<PortFolio>();
				}
				for (PortFolio port : currPortfolios) {
					if( port.getName().equals(portfolio.getName())) {
						throw new PortFolioAlreadyPresentException("Porfolio with given name is Already Present");
					}
				}

				portfolio.setUserId(userId);
				portfoliorepo.save(portfolio);

				currPortfolios.add(portfolio);
				currUser.setPortfolios(currPortfolios);
				
				userrepo.save(currUser);
				return portfolio;
			} else {
				throw new UserNotPresentException("User not present");
			}
	}


	@Override
	public List<PortFolio> viewAllPortFolios(String userid) throws UserNotPresentException {
		// TODO Auto-generated method stub
		Optional<User> useropt= userrepo.findById(userid);
		if(useropt.isPresent())
		{
			User currUser = useropt.get();

			// check if portfolio category is already present
			List<PortFolio> currPortfolios = currUser.getPortfolios();
			return currPortfolios;
		} else {
			throw new UserNotPresentException("User not present");
		}
	}


	@Override
	public void deletePortfolio(String userid, String portName)
			throws UserNotPresentException, PortFolioNotPresentException {
		Optional<User> useropt= userrepo.findById(userid);
		if(useropt.isPresent())
		{
			User currUser = useropt.get();

			List<PortFolio> currPortfolios = currUser.getPortfolios();
			if(currPortfolios == null) {
				throw new PortFolioNotPresentException("Portfolio Not Present");
			}
			for (PortFolio port : currPortfolios) {
				if( port.getName().equals(portName)) {
					portfoliorepo.deleteById(port.getId());

					currPortfolios.remove(port);
					currUser.setPortfolios(currPortfolios);
					userrepo.save(currUser);
					return;
				}
			}
			
			throw new PortFolioNotPresentException("PortFolio with given category name not found");
		} else {
			throw new UserNotPresentException("User not present");
		}
	}


	@Override
	public Map<String, Integer> getstocksOfPortfolio(String userId, String portName)
			throws UserNotPresentException, PortFolioNotPresentException {
		Optional<User> useropt= userrepo.findById(userId);
		if(useropt.isPresent())
		{
			User currUser = useropt.get();

			List<PortFolio> currPortfolios = currUser.getPortfolios();
			if(currPortfolios == null) {
				throw new PortFolioNotPresentException("Portfolio Not Present");
			}
			for (PortFolio port : currPortfolios) {
				if( port.getName().equals(portName)) {
					return port.getStockMap();
				}
			}
			
			throw new PortFolioNotPresentException("PortFolio with given category name not found");
		} else {
			throw new UserNotPresentException("User not present");
		}
	}


	@Override
	public Trade tradeStocks(String userId, String portName, Trade trade) throws UserNotPresentException, PortFolioNotPresentException 
	, NotEnoughStocksToSellException, TradeOperationNotAllowedException {
		Optional<User> useropt= userrepo.findById(userId);
		if(useropt.isPresent())
		{
			User currUser = useropt.get();
			List<PortFolio> currPortfolios = currUser.getPortfolios();
			if(currPortfolios == null) {
				throw new PortFolioNotPresentException("Portfolio Not Present");
			} else if(trade.getBuySell() == null || trade.getBuySell() == null) {
				throw new TradeOperationNotAllowedException("Trade operation should either be BUY/SELL");
			} else if(trade.getQuantity() == null) {
				throw new TradeOperationNotAllowedException("quantity is not defined");
			} else if(trade.getPrice() == null) {
				throw new TradeOperationNotAllowedException("stock price cannot be null");
			}
			for (PortFolio port : currPortfolios) {
				if( port.getName().equals(portName)) {
					currPortfolios.remove(port);
					Map<String, Integer> stockMap = port.getStockMap();
					String currStock = trade.getStock();

					if(trade.getBuySell().toLowerCase().equals("buy")) {
						if(stockMap.containsKey(currStock)) {
							Integer qnty = stockMap.get(currStock);
							stockMap.put(currStock, qnty + trade.getQuantity());
						} else {
							stockMap.put(currStock, trade.getQuantity());
						}
						port.setTotalPrice(port.getTotalPrice() + trade.getQuantity()*trade.getPrice());
					} else {
						if(stockMap.containsKey(currStock)) {
							Integer qnty = stockMap.get(currStock);
							Integer postQnty = qnty - trade.getQuantity();
							if(postQnty < 0) {
								// not enough stocks to sell
								throw new NotEnoughStocksToSellException("not Enough Stocks to sell");
							}else if(postQnty == 0) {
								stockMap.remove(currStock);
							}
							port.setTotalPrice(port.getTotalPrice() - trade.getQuantity()*trade.getPrice());
							stockMap.put(currStock, postQnty);
						} else {
							// not enough stocks to sell
							throw new NotEnoughStocksToSellException("not Enough Stocks to sell");
						}
					}
					// update Trade List
					List<Trade> currTrades = port.getTrades();
					trade.setPortfolioId(userId);
					currTrades.add(trade);
					traderepo.save(trade);
					
					// update Map List
					port.setStockMap(stockMap);
					port.setTrades(currTrades);
					currPortfolios.add(port);
					currUser.setPortfolios(currPortfolios);
					userrepo.save(currUser);

					return trade;
				}
			}
			throw new PortFolioNotPresentException("Portfolio Not Present");
		} else {
			throw new UserNotPresentException("User not present");
		}
	}


	@Override
	public List<Trade> getTrades(String userId, String portName)
			throws UserNotPresentException, PortFolioNotPresentException {
		Optional<User> useropt= userrepo.findById(userId);
		if(useropt.isPresent())
		{
			User currUser = useropt.get();

			List<PortFolio> currPortfolios = currUser.getPortfolios();
			if(currPortfolios == null) {
				throw new PortFolioNotPresentException("Portfolio Not Present");
			}
			for (PortFolio port : currPortfolios) {
				if( port.getName().equals(portName)) {
					return port.getTrades();
				}
			}
			throw new PortFolioNotPresentException("PortFolio with given category name not found");
		} else {
			throw new UserNotPresentException("User not present");
		}
	}


	@Override
	public PortFolio viewPortFolio(String userId, String portName)
			throws UserNotPresentException, PortFolioNotPresentException {
		Optional<User> useropt= userrepo.findById(userId);
		if(useropt.isPresent())
		{
			User currUser = useropt.get();

			List<PortFolio> currPortfolios = currUser.getPortfolios();
			if(currPortfolios == null) {
				throw new PortFolioNotPresentException("Portfolio Not Present");
			}
			for (PortFolio port : currPortfolios) {
				if( port.getName().equals(portName)) {
					return port;
				}
			}
			throw new PortFolioNotPresentException("PortFolio with given category name not found");
		} else {
			throw new UserNotPresentException("User not present");
		}
	}
}
