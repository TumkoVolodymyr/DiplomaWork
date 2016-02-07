/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model.util;

import diplomawork.model.JPEGSaver;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.timer.Timer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author Volodymyr
 */
public class GanerateDataForTrainNN extends ApplicationFrame {

    static int n = 0, trainCount = 0;
    static int width_hight = 200;
    static String nameOfJPGFile;

    public GanerateDataForTrainNN(String title) {
        super(title);
    }

    /**
     * *
     * Save chart to .jpg file
     *
     * @param chart Chart that saved
     * @param trainFlag flag for saving train or usual name file
     */
    static public void saveChartToFile(JFreeChart chart, String name) {
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.getDomainAxis().setVisible(false);
        plot.getRangeAxis().setVisible(false);
        plot.setBackgroundAlpha(0);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setOutlinePaint(null);
        XYItemRenderer r = plot.getRenderer();

        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(false);
            renderer.setSeriesPaint(0, Color.BLACK);
        }
        plot.getRenderer().setSeriesStroke(0, new java.awt.BasicStroke(6f));
        File f = null;

        f = new File("src/resorce/RecogData/" + name + ".jpg");

        try {
            ChartUtilities.saveChartAsJPEG(f, chart, width_hight, width_hight - 100);
        } catch (IOException ex) {
            Logger.getLogger(JPEGSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {

        for (ChartType chartType : ChartType.values()) {
            TimeSeries timeSeries = new TimeSeries("data for training");
            Double[] price = {1.0, 6.1, 3.0, 10.0, 2.1, 6.0, 1.0};
            Double[] priceDoubleTop = {3.0, 13.0, 6.0, 13.0, 1.1, 5.0, 1.0};
            Double[] priceTripleTop = {3.0, 13.0, 6.0, 13.0, 6.1, 13.0, 1.0};
            String figureName = chartType.toString();
            switch (chartType) {
                case HeadAndSoulders:
                    break;
                case ReversHeadAndSoulders:
                    int i = 0;
                    for (Double val : price) {
                        price[i] = 12 - val;
                        i++;
                    }
                    break;
                case DoubleTop:
                    price = priceDoubleTop;
                    break;
                case ReversDoubleTop:
                    int j = 0;
                    for (Double val : priceDoubleTop) {
                        priceDoubleTop[j] = 15 - val;
                        j++;
                    }
                    price = priceDoubleTop;
                    break;
                case TripleTop:
                    price = priceTripleTop;
                    break;
                case ReversTripleTop:
                    int n = 0;
                    for (Double val : priceTripleTop) {
                        priceTripleTop[n] = 15 - val;
                        n++;
                    }
                    price = priceTripleTop;
                    break;
                default:
                    throw new AssertionError();
            }
            long time = System.currentTimeMillis();
            for (int i = 0; i < price.length; i++) {
                timeSeries.add(new Second(new Date(time)), price[i]);
                time = time + Timer.ONE_SECOND * 5;

            }
            Random random = new Random();
            for (int i = 0; i < 2; i++) {
                TimeSeries newTimeSeries = new TimeSeries("New");
                TimeSeriesCollection dataset = new TimeSeriesCollection();
                for (int j = 0; j < timeSeries.getItemCount(); j++) {
                    TimeSeriesDataItem dataItem = timeSeries.getDataItem(j);
                    Double tmpval = (Double) dataItem.getValue();
                    tmpval = tmpval + (random.nextBoolean() ? tmpval * 0.15 * Math.random() : -tmpval * 0.15 * Math.random());
                    long l = dataItem.getPeriod().getFirstMillisecond();
                    l = l + ((Timer.ONE_SECOND / 2) * ((random.nextBoolean()) ? random.nextInt(2) : -random.nextInt(2)));
                    RegularTimePeriod tmpRegularTimePeriod = new Second(new Date(l));
                    newTimeSeries.add(tmpRegularTimePeriod, tmpval);
                }

                dataset.addSeries(newTimeSeries);
                JFreeChart chart = ChartFactory.createTimeSeriesChart(null, null, null,
                        dataset, // data
                        false, // create legend?
                        true, // generate tooltips?
                        false // generate URLs?
                );
                String name = figureName + "_" + i;
                saveChartToFile(chart, name);
            }

        }

    }

}

enum ChartType {
    HeadAndSoulders, ReversHeadAndSoulders, DoubleTop, ReversDoubleTop, TripleTop, ReversTripleTop
}
