package com.ani.val.dcf;

public class ValuationData {	
	//latest earnings data
	private double currentRevenue = 0;
	private double currentFinanceCost = 0;
	private double growthRte = 0;
	private double currentEbit = 0;
	private double ebitMargn = 0;	

	//balance sheet data
	private double shareFundsBS = 0;
	//non-current liabilities
	private double liabilitiesBS = 0;	
	//cash in the bank
	private double cashBS = 0;	

	//cost of capital, tax rate, number of shares
	private float wacc;
	private float tax;
	private double shares;
	
	//terminal year
	private int dcfYears;
	private double saleToCapital = 0;
	private float terminalInterestAndGrowthDifference = 1;
	private float overrideGrowthRate = 0;
	
	private String companyName;
	
	
	//calculated values
	private double totalFCFTillTerminalYear;
	private double terminalValue;
	private double presentValueOfTotalFreeCashflows;
	private double earningPerShare;
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public double getCurrentRevenue() {
		return currentRevenue;
	}

	public void setCurrentRevenue(double currentRevenue) {
		this.currentRevenue = currentRevenue;
	}

	public double getGrowthRte() {
		return growthRte;
	}

	public void setGrowthRte(double growthRte) {
		this.growthRte = growthRte;
	}

	public double getCurrentEbit() {
		return currentEbit;
	}

	public void setCurrentEbit(double currentEbit) {
		this.currentEbit = currentEbit;
	}

	public double getEbitMargn() {
		return ebitMargn;
	}

	public void setEbitMargn(double ebitMargn) {
		this.ebitMargn = ebitMargn;
	}

	public int getDcfYears() {
		return dcfYears;
	}

	public void setDcfYears(int dcfYears) {
		this.dcfYears = dcfYears;
	}

	public float getWacc() {
		return wacc;
	}

	public void setWacc(float wacc) {
		this.wacc = wacc;
	}

	public float getTax() {
		return tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	public double getShares() {
		return shares;
	}

	public void setShares(double shares) {
		this.shares = shares;
	}

	public double getSaleToCapital() {
		return saleToCapital;
	}

	public void setSaleToCapital(double saleToCapital) {
		this.saleToCapital = saleToCapital;
	}

	public float getTerminalInterestAndGrowthDifference() {
		return terminalInterestAndGrowthDifference;
	}

	public void setTerminalInterestAndGrowthDifference(float terminalInterestAndGrowthDifference) {
		this.terminalInterestAndGrowthDifference = terminalInterestAndGrowthDifference;
	}

	public float getOverrideGrowthRate() {
		return overrideGrowthRate;
	}

	public void setOverrideGrowthRate(float overrideGrowthRate) {
		this.overrideGrowthRate = overrideGrowthRate;
	}

	public double getCurrentFinanceCost() {
		return currentFinanceCost;
	}

	public void setCurrentFinanceCost(double currentFinanceCost) {
		this.currentFinanceCost = currentFinanceCost;
	}

	public double getLiabilitiesBS() {
		return liabilitiesBS;
	}

	public void setLiabilitiesBS(double liabilitiesBS) {
		this.liabilitiesBS = liabilitiesBS;
	}

	public double getCashBS() {
		return cashBS;
	}

	public void setCashBS(double cashBS) {
		this.cashBS = cashBS;
	}

	public double getShareFundsBS() {
		return shareFundsBS;
	}

	public void setShareFundsBS(double shareFundsBS) {
		this.shareFundsBS = shareFundsBS;
	}

	public double getTotalFCFTillTerminalYear() {
		return totalFCFTillTerminalYear;
	}

	public void setTotalFCFTillTerminalYear(double totalFCFTillTerminalYear) {
		this.totalFCFTillTerminalYear = totalFCFTillTerminalYear;
	}

	public double getTerminalValue() {
		return terminalValue;
	}

	public void setTerminalValue(double terminalValue) {
		this.terminalValue = terminalValue;
	}

	public double getPresentValueOfTotalFreeCashflows() {
		return presentValueOfTotalFreeCashflows;
	}

	public void setPresentValueOfTotalFreeCashflows(double presentValueOfTotalFreeCashflows) {
		this.presentValueOfTotalFreeCashflows = presentValueOfTotalFreeCashflows;
	}

	public double getEarningPerShare() {
		return earningPerShare;
	}

	public void setEarningPerShare(double earningPerShare) {
		this.earningPerShare = earningPerShare;
	} 
    
}
