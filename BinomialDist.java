/*****************************************
* Writing a class that computes methods 
* related with the Binomial Distribution.
*****************************************/

import java.math.BigInteger;

public class BinomialDist {

  private final int trials;
  private final doube param;
  private final double[] weights;

  /************************************************/
  private static BigInteger[][] pascal(int n) {

    BigInteger[][] triangle = new BigInteger[n + 1][n + 2];
    
    for (int i = 0; i < n + 1; i++)
      for (int j = 0; j < n+ 2; j++)
        triangle[i][j] = BigInteger.ZERO;
    
    triangle[0][1] = BigInteger.ONE;
    
    for (int i = 1; i < n + 1; i++) {
      for (int j = 1; j < i + 2; j++) {
        triangle[i][j] = triangle[i - 1][j - 1].add(triangle[i - 1][j]);
      }
    }
    
    return triangle;
  }

  /************************************************/
  public BinomialDist(int n; double p) {
    trials = n;
    param = p;
    weights = new double[n + 1];
  }

  /************************************************/
  private static double[] weights(int n, double p) {

    double[] w = new double[n + 1];
    BigInteger[][] triangle = pascal(n);
    
    for (int x = 0; x < n + 1; x++) {
      
      int i = 0;
      double _p = 1;
      while (i < x) {
        _p = _p * p;
        i++;
      }

      int y = n - x;
      int j = 0;
      double q = 1 - p;
      double _q = 1;
      while (j < y) {
        _q  = _q * q;
        j++;
      }
      double comb = triangle[n][x + 1].doubleValue();
      w[x] = _p * _q * comb;
      // w[x] = _p * _q * triangle[n][x + 1];
    }

    return w;
  } 

  /************************************************/
  public static double getProbability(int n, int x, double p) {
    
    // x: 'number of endpoints' 0 <= x <= n
    // n: 'number of trials'    0 < n
    // p: 'success probability' 0 < p < 1
    
    BigInteger[][] triangle = pascal(n);
    
    int i = 0;
    double _p = 1;
    while (i < x) {
      _p = _p * p;
      i++;
    }

    int y = n - x;
    int j = 0;
    double q = 1 - p;
    double _q = 1;
    while (j < y) {
      _q  = _q * q;
      j++;
    }
    
    double comb = triangle[n][x + 1].doubleValue();
    double w = _p * _q * comb;
    return w;
  }

  /************************************************/
  public static void main(String[] args) {
    
    int n = Integer.parseInt(args[0]);
    int x =  Integer.parseInt(args[1]);
    double p = Double.parseDouble(args[2]);
    
    double[] w = weights(n, p);
    double sProb1 = getProbability(n, x, p);
    int y = n - x;
    double sProb2 = getProbability(n, y, p);
    Boolean match1 = (sProb1 == w[x]);
    double cumm = 0.0;
      
    for (int i = 0; i < n + 1; i++)
      cumm += w[i];

    System.out.println("The cummulative value of all weights is: " + cumm);
    System.out.println("Is correct P(X = " + x + ") -> " + match1);
    System.out.println("P_X(" + n + " - " + x + ") = " + sProb2);
  }
}
