/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model;

import java.lang.reflect.Array;
import static javafx.scene.input.KeyCode.T;

/**
 *
 * @author Volodymyr
 */
public class AdaptivFilter {

    /**
     * function Predicted = adaptiveFilter(X) alpha = 0.3; N = size(X,1); M =
     * 14; Predicted = zeros(N,1); W = zeros(M,1); for i = 1:M; W(i) = alpha *
     * (1 - alpha) ^ i; end for i = M+1 : N; Predicted(i) = 0; for j = 1:M;
     * Predicted(i) = Predicted(i) + W(j) * X(i-j); end e = X(i) - Predicted(i);
     * sum=0; for j = 1:M; sum = sum + X(i-j)^2; end K = alpha/(2*sum); for j =
     * 1:M; W(j) = W(j) + 2 * K * e*X(i-j); end end
     */
    public static void calc(Double[] x) {
        double alpha = 0.3;
        int n = x.length;
        int m = 7;
        Double[] predicted = new Double[n];
        predicted = zeroInit(predicted);
        Double[] w = new Double[m];
        w = zeroInit(w);
        for (int i = 0; i < m; i++) {
            w[i] = alpha * Math.pow(1 - alpha, i);
        }
        for (int i = m + 1; i < n; i++) {
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

        for (int i = 0; i < x.length; i++) {
            System.out.print(x[i] + "\t");
            System.out.println(predicted[i]);
        }

        for (int i = 0; i < predicted.length; i++) {
            System.out.println("predicted = " + predicted[i]);
        }

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
        calc(x);
    }

}
