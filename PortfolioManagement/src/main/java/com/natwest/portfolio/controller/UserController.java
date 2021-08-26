package com.natwest.portfolio.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import com.natwest.portfolio.services.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	UserService userservice;
	
	@PostMapping("/adduser")
	public ResponseEntity saveuser(@RequestBody User usernew)
	{
		User userObj;
		try {
			userObj = userservice.addUser(usernew);
		} catch (BankAccountAlreadyPresentException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		} catch (UserAlreadyPresentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		}
		return new ResponseEntity<User>(usernew,HttpStatus.CREATED);	
	}
	
	@GetMapping("/viewallusers")
	public ResponseEntity viewusers()
	{
		 List<User> users = userservice.viewUsers();
		 return new ResponseEntity<List>(users,HttpStatus.OK);
	}

	@PostMapping("/createportfolio/{userid}")
	public ResponseEntity createPortfolio(@RequestBody PortFolio portfolio, @PathVariable("userid") String userId)
	{
		try {
			userservice.createPortfolio(portfolio, userId);
		} catch (PortFolioAlreadyPresentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);	
		} catch (UserNotPresentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		}
		return new ResponseEntity<PortFolio>(portfolio,HttpStatus.CREATED);
	}

	@GetMapping("/viewallportfolios/{userid}")
	public ResponseEntity viewPortfolios(@PathVariable("userid") String userId)
	{
		List<PortFolio> portfolioList;
		try {
			portfolioList = userservice.viewAllPortFolios(userId);
		} catch (UserNotPresentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		}
		 return new ResponseEntity<List>(portfolioList,HttpStatus.OK);
	}

	@GetMapping("/getTrades/{userid}/{portname}")
	public ResponseEntity getTrades(@PathVariable("userid") String userId, @PathVariable("portname") String portName)
	{
		List<Trade> trades;
		try {
			trades = userservice.getTrades(userId, portName);
		} catch (UserNotPresentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		} catch (PortFolioNotPresentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		}
		return new ResponseEntity<List>(trades,HttpStatus.OK);
	}

	@GetMapping("/getportfolio/{userid}/{portname}")
	public ResponseEntity getPortfolio(@PathVariable("userid") String userId, @PathVariable("portname") String portName)
	{
		PortFolio currPortFolio = new PortFolio();
		try {
			currPortFolio = userservice.viewPortFolio(userId, portName);
		} catch (UserNotPresentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		} catch (PortFolioNotPresentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		}
		return new ResponseEntity<PortFolio>(currPortFolio,HttpStatus.OK);
	}

	@GetMapping("/getStocks/{userid}/{portname}")
	public ResponseEntity getStocksOfPortfolios(@PathVariable("userid") String userId,  @PathVariable("portname") String portName)
	{
		Map<String, Integer> stockMap = new TreeMap<String, Integer>();
		try {
			stockMap = userservice.getstocksOfPortfolio(userId, portName);
		} catch (UserNotPresentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		} catch (PortFolioNotPresentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		}
		 return new ResponseEntity<Map>(stockMap,HttpStatus.OK);
	}

	@PostMapping("/deleteportfolio/{userid}/{portname}")
	public ResponseEntity deletePortfolio(@PathVariable("userid") String userId, @PathVariable("portname") String portName)
	{
		try {
			userservice.deletePortfolio(userId, portName);
		} catch (UserNotPresentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		} catch (PortFolioNotPresentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>("Deleted successfully",HttpStatus.OK);
	}
	
	@PostMapping("/tradeStocks/{userid}/{portname}")
	public ResponseEntity deletePortfolio(@PathVariable("userid") String userId, @PathVariable("portname") String portName, @RequestBody Trade trade)
	{
		Trade currTrade = new Trade();
		try {
			currTrade = userservice.tradeStocks(userId, portName, trade);
		} catch (UserNotPresentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		} catch (PortFolioNotPresentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		} catch (NotEnoughStocksToSellException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		} catch (TradeOperationNotAllowedException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Trade>(currTrade,HttpStatus.OK);
	}

	@PostMapping("/updateBank/{userid}")
	public ResponseEntity updateBank(@RequestBody Bank bankupd, @PathVariable("userid") String userId)
	{
		try {
			userservice.updateBank(bankupd, userId);
		} catch (UserNotPresentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		}
	
		return new ResponseEntity<Bank>(bankupd,HttpStatus.CREATED);
	}

	@PostMapping("/deleteBank/{userid}")
	public ResponseEntity deleteBank(@PathVariable("userid") String userId)
	{
		
		try {
			userservice.deleteBank(userId);
		} catch (UserNotPresentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		} catch (BankNotPresentException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
		}
	
		return new ResponseEntity<String>("Deleted successfully",HttpStatus.OK);
	}
}
