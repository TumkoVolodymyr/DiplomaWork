/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model.util;

/**
 *
 * @author Volodymyr
 */
import java.util.Arrays;
import org.neuroph.nnet.Hopfield;
import java.util.Vector;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

/**
* This sample shows how to create and train Hopfield neural network
*/
public class HopfieldSample {

    public static void main(String args[]) {

    // create training set (H and T letter in 3x3 grid)
    DataSet trainingSet = new DataSet(9);
    trainingSet.addRow(new DataSetRow(new double[]{1, 0, 1, 1, 1, 1, 1, 0, 1})); // H letter
    trainingSet.addRow(new DataSetRow(new double[]{1, 1, 1, 0, 1, 0, 0, 1, 0})); // T letter

    // create hopfield network
    Hopfield myHopfield = new Hopfield(9);
    // learn the training set
    myHopfield.learn(trainingSet);

    // test hopfield network
    System.out.println("Testing network");

    // add one more 'incomplete' H pattern for testing - it will be recognized as H
    trainingSet.addRow(new DataSetRow(new double[]{1, 1, 0, 1, 0, 1, 1, 0, 1})); // incomplete H letter

        for(DataSetRow dataRow : trainingSet.getRows()) {

            myHopfield.setInput(dataRow.getInput());
            myHopfield.calculate();
            myHopfield.calculate();
            double[ ] networkOutput = myHopfield.getOutput();

            System.out.print("Input: " + Arrays.toString(dataRow.getInput()) );
            System.out.println(" Output: " + Arrays.toString(networkOutput) );

        }

    }

}