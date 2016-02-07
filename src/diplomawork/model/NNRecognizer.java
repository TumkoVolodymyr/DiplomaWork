/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.exceptions.VectorSizeMismatchException;
import org.neuroph.imgrec.ImageRecognitionPlugin;

/**
 *
 * @author Volodymyr
 */
public class NNRecognizer extends Thread implements JPGFileChangeListener {

    /**
     * Create object and run the thread
     */
    public NNRecognizer() {
        this.start();
    }

    /**
     * Waiting for notifycation about changing JPEG file
     *
     */
    @Override
    public void run() {
        while (this != null) {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(NNRecognizer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            recognizeImage();
        }
    }

    /**
     *
     * Recognized static jpeg file
     */
    public void recognizeImage() {
        // load trained neural network saved with NeurophStudio (specify existing neural network file here)
        //NeuralNetwork nnet = NeuralNetwork.createFromFile("F:\\Users\\Volodymyr\\Documents\\NetBeansProjects\\Neuroph project\\Neural Networks\\newNN.nnet");
        NeuralNetwork nnet = NeuralNetwork.createFromFile("src/resorce/BW60on30hlnc15last.nnet");

        // get the image recognition plugin from neural network
        ImageRecognitionPlugin imageRecognition = (ImageRecognitionPlugin) nnet.getPlugin(ImageRecognitionPlugin.class);
//        System.out.println(imageRecognition.getSamplingResolution().getHeight()+" ");
//        System.out.println(imageRecognition.getSamplingResolution().getWidth()+" ");
//        System.out.println(imageRecognition.getColorMode().toString()+" ");
        NumberFormat numberFormat = DecimalFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(3);

        try {
            // image recognition is done here
//            HashMap<String, Double> output = imageRecognition.recognizeImage(new File("src/resorce/RecogData/DoubleTop_1.jpg")); // specify some existing image file here
            HashMap<String, Double> output = imageRecognition.recognizeImage(new File("src/resorce/Plot.jpg")); // specify some existing image file here
            String outputString = "";
            for (String key : output.keySet()) {
                outputString += key + " : " + numberFormat.format(output.get(key)) + "\n";
            }
            System.out.println(outputString);
//            Neuron[] neurophs = imageRecognition.getParentNetwork().getInputNeurons();
//            for (int l = 0; l < neurophs.length; l++) {
//                if (l % 20 == 0) {
//                    System.out.println();
//                }
//                System.out.print(numberFormat.format(neurophs[l].getNetInput()) + "\t");
//
//            }
//            System.out.println();

            // System.out.println(output.toString());
        } catch (IOException ioe) {
            System.out.println("Error: could not read file!");
        } catch (VectorSizeMismatchException vsme) {
            System.out.println("Error: Image dimensions dont !");
        }
    }

    public static void main(String[] args) {
        NNRecognizer nNRecognizer = new NNRecognizer();
        nNRecognizer.recognizeImage();
        nNRecognizer.stop();
    }

    @Override
    /**
     * *
     *
     * Run when new jpeg file was saved
     */
    public void fileChanged() {
        synchronized (this) {
            this.notify();

        }
    }
}
