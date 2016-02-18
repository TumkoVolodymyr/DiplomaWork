/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model;

import java.lang.reflect.Array;
import java.util.Arrays;
import static javafx.scene.input.KeyCode.T;

/**
 *
 * @author Volodymyr
 */
public class AdaptivFilter {
    
//    static int m = 7;
//    Double[] w;

//    public AdaptivFilter() {
//        w = new Double[m];
//        w = zeroInit(w);
//        
//    }
//
//    
//    public static void calcTrendAll(Double[] x) {
//        double alpha = 0.3;
//        int n = x.length;
//        m = 7;
//        Double[] predicted = new Double[n];
//        predicted = zeroInit(predicted);
//        Double[] w = new Double[m];
//        w = zeroInit(w);
//        for (int i = 0; i < m; i++) {
//            w[i] = alpha * Math.pow(1 - alpha, i);
//        }
//        for (int i = m; i < n; i++) {
//            predicted[i] = 0.0;
//            for (int j = 0; j < m; j++) {
//                predicted[i] = predicted[i] + w[j] * x[i - j];
//            }
//            Double e = x[i] - predicted[i];
//            Double sum = 0.0;
//            for (int j = 0; j < m; j++) {
//                sum = sum + Math.pow(x[i - j], 2);
//            }
//            Double k = alpha / (2 * sum);
//            for (int j = 0; j < m; j++) {
//                w[j] = w[j] + 2 * k * e * x[i - j];
//            }
//        }
//    }
    public static double calcTrendMany(Double[] x) {
        double alpha = 0.3;
        int n = x.length;
        int m = 7;
        Double[] w;
        m = 7;
        if (n<m) {
            m=n-1;
        }
        Double[] predicted = new Double[n];
        predicted = zeroInit(predicted);
        w = new Double[m];
        w = zeroInit(w);
        for (int i = 0; i < m; i++) {
            w[i] = alpha * Math.pow(1 - alpha, i);
        }
        for (int i = m; i < n; i++) {
            predicted[i] = 0.0;
            for (int j = 0; j < m; j++) {
                predicted[i] = predicted[i] + w[j] * x[i - j];
            }
            Double e = x[i] - predicted[i];
            Double sum = 0.0;
            for (int j = 0; j < m; j++) {
                sum = sum + Math.pow(x[i - j], 2);
            }
            Double k = alpha / (2 * sum);
            for (int j = 0; j < m; j++) {
                w[j] = w[j] + 2 * k * e * x[i - j];
            }
        }
        return predicted[n-1];
    }
//    public double calcTrend(Double[] xData) {
//        Double[] x;
//        double alpha = 0.3;
//        int n = xData.length;
//        m = 7;
//        if (n<m) {
//            x=xData;
//            m=n;
//        }else{
//            x = Arrays.copyOfRange(xData, n - m-1, n);
//        }
//        n = x.length;
//        Double predicted;
//        
//        for (int i = 0; i < m; i++) {
//            w[i] = alpha * Math.pow(1 - alpha, i);
//        }
//        for (int i = m; i < n; i++) {
//            predicted = 0.0;
//            for (int j = 0; j < m; j++) {
//                predicted = predicted + w[j] * x[i - j];
//            }
//            System.out.println(""+predicted);
//            Double e = x[i] - predicted;
//            Double sum = 0.0;
//            for (int j = 0; j < m; j++) {
//                sum = sum + Math.pow(x[i - j], 2);
//            }
//            Double k = alpha / (2 * sum);
//            for (int j = 0; j < m; j++) {
//                w[j] = w[j] + 2 * k * e * x[i - j];
//            }
//        }
//
//        return 0;
//    }
//    public double calcTrendNew(Double[] xData) {
//        Double[] x;
//        double alpha = 0.3;
//        int n = xData.length;
//        m = 7;
//        if (n<m) {
//            x=xData;
//            m=n;
//        }else{
//            x = Arrays.copyOfRange(xData, n - m, n);
//        }
//        n = x.length-1;
//        Double predicted;
//        
//        for (int i = 0; i < m; i++) {
//            w[i] = alpha * Math.pow(1 - alpha, i);
//        }
// 
//            predicted = 0.0;
//            for (int j = 0; j < m; j++) {
//                predicted = predicted + w[j] * x[n - j];
//            }
//            System.out.println(""+predicted);
//            Double e = x[n] - predicted;
//            Double sum = 0.0;
//            for (int j = 0; j < m; j++) {
//                sum = sum + Math.pow(x[n - j], 2);
//            }
//            Double k = alpha / (2 * sum);
//            for (int j = 0; j < m; j++) {
//                w[j] = w[j] + 2 * k * e * x[n - j];
//            }
//        
//
//        return 0;
//    }

    private static Double[] zeroInit(Double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0.0;
        }
        return arr;
    }

//    public static void main(String[] args) {
//        Double[] x = {1.2, 3.0, 1.0, 6.0, 4.0, 9.0, 11.2, 7.0, 9.0, 12.0, 7.0, 9.0, 1.2, 3.0, 1.0, 6.0, 4.0, 9.0, 11.2, 7.0, 9.0, 12.0, 7.0, 9.0
//        ,1.2, 3.0, 1.0, 6.0, 4.0, 9.0, 11.2, 7.0, 9.0, 12.0, 7.0, 9.0, 1.2, 3.0, 1.0, 6.0, 4.0, 9.0, 11.2, 7.0, 9.0, 12.0, 7.0, 9.0};
//        calcTrendAll(x);
//        System.out.println("new");
//        int n = x.length;
//        int m = 7;
//        AdaptivFilter adaptivFilter = new  AdaptivFilter();
//        for (int i = 0; i < n-m; i++) {
//            Double[] xx = Arrays.copyOfRange(x, i, i+m);
//            adaptivFilter.calcTrendNew(xx);
//            
//        }
//        m=30;
//        System.out.println("new 2");
//        for (int i = 0; i < n-m; i++) {
//            Double[] xx = Arrays.copyOfRange(x, i, i+m);
//            System.out.println(""+calcTrendMany(xx));
//            
//        }
//    }

}
