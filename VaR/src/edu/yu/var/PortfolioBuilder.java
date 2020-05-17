package edu.yu.var;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class PortfolioBuilder {
    Double weight;
    double investment;
    double value;
    double [][] covarianceMatrix;
    Hashtable<Stock,Double> portfolio;

    public PortfolioBuilder(Stock [] stocks, Double [] weights){
        this.portfolio = new Hashtable<Stock, Double>();
        for(int i = 0; i < stocks.length; i++){
            portfolio.put(stocks[i], weights[i]);
        }
        this.portfolio = portfolio;
    }
}
