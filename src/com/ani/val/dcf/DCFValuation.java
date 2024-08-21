package com.ani.val.dcf;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Anish Poladi
 */

public class DCFValuation {
	private static final Logger log = Logger.getLogger(DCFValuation.class.getName());

	private static final String APPLICATION_ROOT = "C:/ProjectFin/app/dcf/";
	private static final String INPUT_FILE = "valuate.txt";

	private Map<Integer, DCFData> dcfDataAll = new HashMap<Integer, DCFData>(); 
	private ValuationData valuationData;
	

	public static void main( String[] args )
	{		
		
		DCFValuation dcfVal = new DCFValuation();		
		//enter number of shares and amounts in same units
		//file columns
		//CompanyName	DCFYears	TaxRate	WACC	NumberOfShares	TerminalInterestGrowthDiff	
		//Revenue(current)	EBIT	FinanceCost	ShareFunds	Liabilities	Cash
		
		dcfVal.loadData(APPLICATION_ROOT+INPUT_FILE);				
		dcfVal.calculateDCF();
		dcfVal.printResutls();
	}

	public void calculateDCF() {	

		//calculate 11 years free cash flow and data - 10 years plus extra year for terminal value calculations
		for(int i = 1; i <= valuationData.getDcfYears();i++) {
			prepareDCFForFutureYear(i);		
		}

		calculateCurrentValuePerShare();
	}

	public void prepareDCFForFutureYear(int year) {
		DCFData dcfData = new DCFData();

		DCFData prevDcfData = dcfDataAll.get(year - 1);

		//calculate revenue using growth rate and prev year revenue
		double revenue = 0;
		if(year == 1) {
			revenue = valuationData.getCurrentRevenue() * (1 + valuationData.getGrowthRte()/100);
		}else {
			revenue = prevDcfData.getRevenue() * (1 + prevDcfData.getGrowthRate()/100);
		}		
		dcfData.setRevenue(revenue);


		dcfData.setGrowthRate(valuationData.getGrowthRte());
		dcfData.setEbitMargin(valuationData.getEbitMargn());

		double ebitIncome = revenue * valuationData.getEbitMargn()/100;
		dcfData.setEbitIncome(ebitIncome);

		double pat = ebitIncome * (1 - valuationData.getTax()/100);
		dcfData.setPat(pat);

		//calculating amount for investing in the company for future growth
		//for new revenue  (difference of current and previous year revenue) 
		//how much needs to be invested to maintain sale to capital ratio of the company
		double reinvestment;
		if(year == 1) {
			reinvestment = (revenue - valuationData.getCurrentRevenue())/valuationData.getSaleToCapital();
		}else {
			reinvestment = (revenue - prevDcfData.getRevenue())/valuationData.getSaleToCapital();
		}
		dcfData.setReinvestment(reinvestment);

		//multiplier on future cash flow to get present value of it
		double discountFactor;
		if(year == 1) {
			discountFactor = 1/(1+valuationData.getWacc()/100);
		}else {
			discountFactor = prevDcfData.getCummulatedDiscountFactor() * (1/(1+valuationData.getWacc()/100));
		}
		DecimalFormat f = new DecimalFormat("##.0000");
		log.fine("cummulative discount factor "+f.format(discountFactor));
		dcfData.setCummulatedDiscountFactor(discountFactor);
		//weighted average cost of capital
		dcfData.setCostOfCapital(valuationData.getWacc());
		dcfDataAll.put(year, dcfData);


	}

	public void calculateCurrentValuePerShare() {
		//get the terminal cash flow prepared in the last step
		DCFData dcfDataT = dcfDataAll.get(valuationData.getDcfYears());

		DCFData PrvDcfData = dcfDataAll.get(valuationData.getDcfYears() - 1);

		dcfDataT.setCummulatedDiscountFactor(PrvDcfData.getCummulatedDiscountFactor());

		//for terminal value calculation growth rate should be less than cost of capital
		//make sense company can't grow and beat interest rate forever.

		//Terminal Interest And Growth Difference is an estimated value for each company - one of the inputs		
		//set terminal growth rate which should be less than the WACC
		float diff = valuationData.getTerminalInterestAndGrowthDifference();
		double terminalGrate = dcfDataT.getCostOfCapital() - diff;
		dcfDataT.setGrowthRate(terminalGrate);
		log.info("Terminal growth rate "+terminalGrate);
		log.info("Terminal wacc "+dcfDataT.getCostOfCapital());

		//calculate terminal revenue and pat
		double revenue = PrvDcfData.getRevenue() * (1 + terminalGrate/100);
		dcfDataT.setRevenue(revenue);

		double ebitIncome = revenue * valuationData.getEbitMargn()/100;
		dcfDataT.setEbitIncome(ebitIncome);

		double pat = ebitIncome * (1 - valuationData.getTax()/100);
		dcfDataT.setPat(pat);


		//terminal reinvestment
		double trein = (terminalGrate /dcfDataT.getCostOfCapital())*dcfDataT.getPat();
		dcfDataT.setReinvestment(trein);

		log.info("Terminal pat "+(long)dcfDataT.getPat());
		log.info("Terminal reinvestment "+(long)trein);

		//terminal value - convergent power series 
		//growth rate is less than cost of capital - will converge
		double terminalVal = dcfDataT.getFreeCashFlow()*100/(dcfDataT.getCostOfCapital() - dcfDataT.getGrowthRate());

		log.info("Terminal value "+(long)terminalVal);
		DecimalFormat f = new DecimalFormat("##.0000");
		log.info("cummulative discount factor "+f.format(dcfDataT.getCummulatedDiscountFactor()));

		double pvOfTerminalVal = terminalVal * dcfDataT.getCummulatedDiscountFactor();
		log.info("Present Value of Terminal value "+(long)pvOfTerminalVal);

		double totalPVofFCFAllYears = 0;
		double totalFCF = 0;
		//calculate total pv of fcf of 10 years
		//take only 10 years, 11th one is for terminal value
		for(int i = 1; i < valuationData.getDcfYears();i++) {
			DCFData dcfdata = dcfDataAll.get(i);
			totalFCF = totalFCF + dcfdata.getFreeCashFlow();
			totalPVofFCFAllYears = totalPVofFCFAllYears + dcfdata.getPresentValueOfFCF();
		}


		log.info("total Present Value of all DCF year's FCF "+(long)totalPVofFCFAllYears);

		double totalPVOfFCF = totalPVofFCFAllYears + pvOfTerminalVal;

		log.info("total Present Value of company "+(long)totalPVOfFCF);

		//remove debt
		totalPVOfFCF = totalPVOfFCF - valuationData.getLiabilitiesBS();

		//add cash
		totalPVOfFCF = totalPVOfFCF + valuationData.getCashBS();

		log.info("total value of company after removing debt and adding cash "+(long)totalPVOfFCF);

		double perShareValue = totalPVOfFCF /valuationData.getShares();
		
		valuationData.setTerminalValue(terminalVal);
		valuationData.setTotalFCFTillTerminalYear(totalFCF);
		valuationData.setPresentValueOfTotalFreeCashflows(totalPVOfFCF);
		valuationData.setEarningPerShare(perShareValue);

		log.info("Per share value "+(long)perShareValue);

	}


	public ValuationData loadData(String file) {
		
		valuationData = new ValuationData();
		//file columns
		//CompanyName	DCFYears	TaxRate	WACC	NumberOfShares	TerminalInterestGrowthDiff	
		//Revenue(current)	EBIT	FinanceCost	ShareFunds	Liabilities	Cash
		String finData = FileManager.readOneRecFromFile(file);
		log.info("data line "+finData);
		String[] finDataCols = finData.split("\t");

		valuationData.setCompanyName(finDataCols[0]);

		int dcfYears = Integer.valueOf(finDataCols[1]);
		valuationData.setDcfYears(dcfYears);

		float tax = Float.valueOf(finDataCols[2]);
		valuationData.setTax(tax);

		//Weighted average cost of capital
		float wacc = Float.valueOf(finDataCols[3]);
		valuationData.setWacc(wacc);

		//number of shares
		double shares = Double.valueOf(finDataCols[4]);
		valuationData.setShares(shares);

		float terminalInterestAndGrowthDifference = Float.valueOf(finDataCols[5]);
		valuationData.setTerminalInterestAndGrowthDifference(terminalInterestAndGrowthDifference);

		float revenue = Float.valueOf(finDataCols[6]);
		valuationData.setCurrentRevenue(revenue); 
		float ebit = Float.valueOf(finDataCols[7]);
		valuationData.setCurrentEbit(ebit);
		float financeCost = Float.valueOf(finDataCols[8]);
		valuationData.setCurrentFinanceCost(financeCost);

		//book value of equity
		float shareFunds = Float.valueOf(finDataCols[9]);
		valuationData.setShareFundsBS(shareFunds);
		float liabilities = Float.valueOf(finDataCols[10]);
		valuationData.setLiabilitiesBS(liabilities);
		float cash = Float.valueOf(finDataCols[11]);
		valuationData.setCashBS(cash);

		//calculate
		double ebitMgn = 100 * ebit / revenue;
		valuationData.setEbitMargn(ebitMgn);
		double saleToCapital = revenue/(shareFunds + liabilities - cash);
		valuationData.setSaleToCapital(saleToCapital);

		return valuationData;

	}
	public void printResutls() {
		StringBuffer outPut = new StringBuffer();
		outPut.append("###################RESULTS############################");
		outPut.append("\n");
		outPut.append("Valuation of "+valuationData.getCompanyName());
		outPut.append("\n");
		outPut.append("\n");
		outPut.append("Total Free Cash Flows till Terminal Year "+valuationData.getTotalFCFTillTerminalYear());
		outPut.append("\n");
		outPut.append("\n");
		outPut.append("Terminal Value "+valuationData.getTerminalValue());
		outPut.append("\n");
		outPut.append("\n");
		outPut.append("Present Value of Total Free Cash Flows "+valuationData.getPresentValueOfTotalFreeCashflows());
		outPut.append("\n");
		outPut.append("\n");
		outPut.append("Current Value of Each Share "+valuationData.getEarningPerShare());

		System.out.println(outPut.toString());
	}
}
