package edu.yu.var;
import java.util.*;
public interface Company {

public List<Double> getPrices(Stock stock);

public double getVariance(Stock stock);

public double getStandardDeviation(Stock stock);

public double getAveragePrice(Stock stock);

public double getAveragePeriodicChange(Stock stock);
}
