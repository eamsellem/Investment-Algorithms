package edu.yu.var;

import java.util.*;

import edu.yu.var.Stock;

import javax.sound.sampled.Port;
import javax.swing.text.html.HTMLDocument;

public class Portfolio {

    Double weight;
    double investment;
    double value;
    double [][] covarianceMatrix;
    Hashtable<Stock,Double> portfolio;

    public Portfolio(Hashtable<Stock,Double> portfolio){
        this.portfolio = portfolio;
        //this.investment = investment;
        this.value = getPortfolioValue();
    }


    public double [][] getCovarianceMatrix(Portfolio portfolio) {
        Set<Stock> stocks = portfolio.portfolio.keySet();
        Iterator<Stock> iter = stocks.iterator();
        Stock peak = iter.next();
        Stock[] arr = new Stock[portfolio.portfolio.size()];
        // double [][] T = new double [portfolio.portfolio.size()] [arr.length];
        double[][] T = new double[arr.length][peak.closing.size()];
        //double [][] temp = new double [portfolio.portfolio.size()] [arr.length];
        double[][] temp = new double[peak.closing.size()][arr.length];
        //double[][] covarianceMatrix = new double[arr.length][portfolio.portfolio.size()];
        double[][] covarianceMatrix = new double[arr.length][arr.length];
        int i = 1;
        arr[0] = peak;
        while (iter.hasNext()) {
            arr[i] = iter.next();
            i++;
        }
        for (int k = 0; k < arr.length; k++) {
            for (int j = 0; j < peak.closing.size(); j++) {
                temp[j][k] = arr[k].getPeriodicChange(arr[k])[j] - arr[k].getAveragePeriodicChange(arr[k]);
            }
        }
        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < peak.closing.size(); y++) {
                T[x][y] = temp[y][x];
            }
        }
        for (int z = 0; z < arr.length; z++) {
            for (int j = 0; j < arr.length; j++) {
                for (int k = 0; k < arr[0].closing.size(); k++) {
                    covarianceMatrix[z][j] += T[z][k] * temp[k][j] ;
                }
            }
        }
        return covarianceMatrix;
    }/**
        for(int s = 0; s< temp.length;s++){
            for(int t = 0; t< T[s].length; t++){
                covarianceMatrix[s][t] = covarianceMatrix[s][t]/arr.length;
            }
        }
        return covarianceMatrix;
        }**/


    //taken from material published by Adrian-Ng
    public double[] sampleCorrelatedVariables(double[][] choleskyDecomposition) {
        // Generate a vector of random variables, sampling from random Gaussian of mean 0 and sd 1
        Random epsilon = new Random();
        double[] correlatedRandomVariables = new double[choleskyDecomposition[0].length];
        for (int i = 0; i < choleskyDecomposition[0].length; i++)
            for (int j = 0; j < choleskyDecomposition[0].length; j++)
                //multiply the Cholesky Decomposition by a random variable sampled from the standard gaussian
                correlatedRandomVariables[i] += choleskyDecomposition[i][j] * epsilon.nextGaussian();
        // our random variables are now correlated
        return correlatedRandomVariables;
    }

    //taken from material published by Adrian-Ng
    public double[][] getCholeskyDecomposition(Portfolio portfolio) {
        double[][] covarianceMatrix = getCovarianceMatrix(portfolio);
        int n = portfolio.portfolio.size();
        double[][] choleskyMatrix = new double[n][n];

        for (int i = 0; i < covarianceMatrix.length; i++) {
            for (int j = 0; j <= i; j++) {
                Double sum = 0.0;
                for (int k = 0; k < j; k++)
                    sum += choleskyMatrix[i][k] * choleskyMatrix[j][k];
                if (i == j)
                    choleskyMatrix[i][j] = Math.sqrt(covarianceMatrix[i][j] - sum);
                else
                    choleskyMatrix[i][j] = (covarianceMatrix[i][j] - sum) / choleskyMatrix[j][j];
            }
        }
        return choleskyMatrix;
    }

    public double getStake(Stock stock){
        Double n = portfolio.get(stock);
        return n/weight;
    }

    public double getPortfolioValue(){
        double n = 0;
        Set<Stock> stocks = portfolio.keySet();
        for(Stock stock: stocks){
            n += portfolio.get(stock) *stock.currentPrice;
        }
        return n;
    }


}
