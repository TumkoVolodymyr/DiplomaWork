/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author Volodymyr
 */
public class JPEGSaver implements SeriesChangeListener {

    private TimeSeries timeSeries;
//    int n = 0, trainCount = 0;
    private int width = 200, hight = 100;
    private JPGFileChangeListener fileChangeListener;
    private String nameOfJPGFile;

    /**
     *
     * Creat new object
     *
     * @param timeSeries data for chart
     */
    public JPEGSaver(TimeSeries timeSeries) {
        this.timeSeries = timeSeries;
    }

    /**
     * *
     * Create new object
     *
     * @param timeSeries data for chart
     * @param fileChangeListener listner of file changes
     */
    public JPEGSaver(TimeSeries timeSeries, JPGFileChangeListener fileChangeListener) {
        this.timeSeries = timeSeries;
        this.fileChangeListener = fileChangeListener;
    }

    /**
     * Save plot to file
     */
    public void saveDiagramToJPeG() {
        int n = 7;
        int startIndexForTimeSeries = (timeSeries.getItemCount() >= n) ? timeSeries.getItemCount() - n : 0;
        TimeSeries timeSeries = this.timeSeries;
        if (this.timeSeries.getItemCount() > 7) {
            try {
                timeSeries = this.timeSeries.createCopy(startIndexForTimeSeries, this.timeSeries.getItemCount() - 1);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(JPEGSaver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(timeSeries);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(null, null, null,
                dataset, // data
                false, // create legend?
                true, // generate tooltips?
                false // generate URLs?
        );
        saveChartToFile(chart, false);
    }

    /**
     * *
     * Save chart to .jpg file
     *
     * @param chart Chart that saved
     * @param trainFlag flag for saving train or usual name file
     */
    private void saveChartToFile(JFreeChart chart, boolean trainFlag) {
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
        plot.getRenderer().setSeriesStroke(0, new java.awt.BasicStroke(4f));
        File f = null;
        if (trainFlag) {
            nameOfJPGFile = "src/resorce/TranePlot" +  ".jpg";
            f = new File(nameOfJPGFile);
        } else {
//            f = new File("src/resorce/Plot" + n++ + ".jpg");
            f = new File("src/resorce/Plot" + ".jpg");
        }

        try {
            ChartUtilities.saveChartAsJPEG(f, chart, width, hight);
        } catch (IOException ex) {
            Logger.getLogger(JPEGSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *
     * Save chart to file when series changed and send notifycation to
     * JPGFileChangeListener about changing file
     *
     * @param event SeriesChangeEvent
     */
    @Override
    public void seriesChanged(SeriesChangeEvent event) {
        saveDiagramToJPeG();
        fileChangeListener.fileChanged();
    }

}
