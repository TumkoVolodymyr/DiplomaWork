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
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.PlotChangeListener;
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
    int n = 0, trainCount=0;
    JPGFileChangeListener fileChangeListener;
    String nameOfJPGFile;

    public JPEGSaver(TimeSeries timeSeries) {
        this.timeSeries = timeSeries;
    }

    public JPEGSaver(TimeSeries timeSeries, JPGFileChangeListener fileChangeListener) {
        this.timeSeries = timeSeries;
        this.fileChangeListener = fileChangeListener;
    }
    
    

    /**
     * Save plot to file
     */
    public void saveDiagramToJPeG() {
        int startIndexForTimeSeries = (timeSeries.getItemCount() >= 5) ? timeSeries.getItemCount() - 5 : 0;
        TimeSeries timeSeries = this.timeSeries;
        try {
            timeSeries = this.timeSeries.createCopy(startIndexForTimeSeries, this.timeSeries.getItemCount() - 1);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(JPEGSaver.class.getName()).log(Level.SEVERE, null, ex);
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
    public void saveChartToFile(JFreeChart chart, boolean trainFlag){
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
        if(trainFlag){
            nameOfJPGFile = "src/resorce/TranePlot"+trainCount+++".jpg";
            f = new File(nameOfJPGFile);
        }else{
//            f = new File("src/resorce/Plot" + n++ + ".jpg");
            f = new File("src/resorce/Plot" + ".jpg");
        }
        
        try {
            ChartUtilities.saveChartAsJPEG(f, chart, 80, 80);
        } catch (IOException ex) {
            Logger.getLogger(JPEGSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   

    @Override
    public void seriesChanged(SeriesChangeEvent event) {
        saveDiagramToJPeG();
        fileChangeListener.fileChanged();
    }

}
