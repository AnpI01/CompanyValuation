package com.ani.val.dcf;

public class DCFData {

	private double growthRate;
	private double revenue;
	private double ebitMargin;
	private double ebitIncome;
	private double pat;
	private double reinvestment;
	
	private double costOfCapital;
	private double cummulatedDiscountFactor;
	
	
	private double roic;

	public double getGrowthRate() {
		return growthRate;
	}

	public void setGrowthRate(double growthRate) {
		this.growthRate = growthRate;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

	public double getEbitMargin() {
		return ebitMargin;
	}

	public void setEbitMargin(double ebitMargin) {
		this.ebitMargin = ebitMargin;
	}

	public double getEbitIncome() {
		return ebitIncome;
	}

	public void setEbitIncome(double ebitIncome) {
		this.ebitIncome = ebitIncome;
	}

	public double getPat() {
		return pat;
	}

	public void setPat(double pat) {
		this.pat = pat;
	}

	public double getReinvestment() {
		return reinvestment;
	}

	public void setReinvestment(double reinvestment) {
		this.reinvestment = reinvestment;
	}

	public double getCostOfCapital() {
		return costOfCapital;
	}

	public void setCostOfCapital(double costOfCapital) {
		this.costOfCapital = costOfCapital;
	}

	public double getCummulatedDiscountFactor() {
		return cummulatedDiscountFactor;
	}

	public void setCummulatedDiscountFactor(double cummulatedDiscountFactor) {
		this.cummulatedDiscountFactor = cummulatedDiscountFactor;
	}
	
	public double getRoic() {
		return roic;
	}

	public void setRoic(double roic) {
		this.roic = roic;
	}
	
	public double getPresentValueOfFCF() {
		double pvFCF = 0;
		pvFCF = getFreeCashFlow() * cummulatedDiscountFactor;
		
		return pvFCF;
	}
	public double getFreeCashFlow() {
		double fCF = 0;
		fCF = pat - reinvestment;
		return fCF;
	}
	
}
