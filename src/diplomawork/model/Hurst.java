/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 *
 * @author Volodymyr
 */
public class Hurst {

    /**
     *
     * @param x 
     * function H = hurst_my(x)
     */
    
    static void calcHurstAll(Double[] x) {
        int n = x.length;
        Double[] h = new Double[n];
        h = zeroInit(h);
        int k = 20;
        for (int i = k; i < n; i++) {
            Double[] data = Arrays.copyOfRange(x, i - k, i);
            h[i] = calcHurst(data);
            System.out.println(h[i]);
        }     
    }
    /**
     * 
     * @param x
     * @return 
     */
    static Double calcHurst(Double[] x) {
        int n = x.length;
        Double a = Math.PI / 2;
        final Double mn = sum(x) / x.length;
        Double[] xtn = new Double[n];
        xtn = zeroInit(xtn);

        for (int i = 0; i < n; i++) {
            Double[] xsub = Arrays.copyOfRange(x, 0, i);
            List<Double> list;
            list = Arrays.asList(xsub);
            list.replaceAll(new UnaryOperator<Double>() {
                @Override
                public Double apply(Double t) {
                    return t - mn;
                }
            });
            xtn[i]=sum((Double[]) list.toArray());
//            System.out.println("xtn[i]= "+xtn[i]);
        }
        Double maxXtn= xtn[0];
        Double minXtn= xtn[0];
        
        for (int i = 0; i < xtn.length; i++) {
            if (maxXtn<xtn[i]) {
                maxXtn=xtn[i];  
            }
            if (minXtn>xtn[i]) {
                minXtn=xtn[i];
            } 
        }
        Double r = maxXtn - minXtn;
        Double s = std(x);
        Double Ht = Math.log(r/s)/Math.log((n-1)*a);
        return Ht;
    }    
    static Double std (Double [] x){
        final Double mn = sum(x) / x.length;
        Double sum = 0.0;
        for (int i = 0; i < x.length; i++) {
            sum+=Math.pow(x[i]-mn, 2); 
        }
        sum=sum/x.length;
        return Math.sqrt(sum);
    }

    static Double sum(Double[] arr) {
        Double sum = 0.0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum;
    }

    static Double[] zeroInit(Double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0.0;
        }
        return arr;
    }

    public static void main(String[] args) {
        Double[] x = {1.2, 3.0, 1.0, 6.0, 4.0, 9.0, 11.2, 7.0, 9.0, 12.0, 7.0, 9.0, 1.2, 3.0, 1.0, 6.0, 4.0, 9.0, 11.2, 7.0, 9.0, 12.0, 7.0, 9.0
        ,1.2, 3.0, 1.0, 6.0, 4.0, 9.0, 11.2, 7.0, 9.0, 12.0, 7.0, 9.0, 1.2, 3.0, 1.0, 6.0, 4.0, 9.0, 11.2, 7.0, 9.0, 12.0, 7.0, 9.0};
        calcHurstAll(x);
    }
}
