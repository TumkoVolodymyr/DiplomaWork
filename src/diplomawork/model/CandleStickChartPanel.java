/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.timer.Timer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Minute;
import org.jfree.data.time.ohlc.OHLCItem;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;

/**
 *
 * @author Volodymyr
 */
public class CandleStickChartPanel extends Thread {

    private OHLCSeries oHLCSeries;
    private String stock = "EURUSD=X";
    private final String url;
    private final ChartPanel chartPanel;

    public ChartPanel getChartPanel() {
        return chartPanel;
    }

    public CandleStickChartPanel(String name, String url) {
        this.url = url;
        stock = name;
        chartPanel = createChartPanel();
        this.start();
    }

    /**
     * Creates a chart.
     *
     * @param dataset a dataset.
     *
     * @return A chart.
     */
    private JFreeChart createChart(OHLCSeriesCollection dataset) {
        final JFreeChart chart = ChartFactory.createCandlestickChart(
                stock, "Time", "Price", dataset, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("dd HH:mm"));

        return chart;
    }

    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return The dataset.
     */
    private OHLCSeriesCollection createDataset() {
        oHLCSeries = new OHLCSeries(stock);
        OHLCSeriesCollection dataset = new OHLCSeriesCollection();
        dataset.addSeries(oHLCSeries);
        return dataset;
    }

    /**
     * Creates a panel.
     *
     * @return A panel.
     */
    private ChartPanel createChartPanel() {
        JFreeChart chart = createChart(createDataset());
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);

        return panel;
    }

    /**
     * Periodly conecting for server and check a data if it was change than
     * update a candelstick panel
     */
    @Override
    public void run() {
        Date lastDate = new Date();
        Quote quote = GetDataFormYahoo.getQouteFromYahoo(url);
        stock = quote.getName();
        while (this != null) {
            quote = GetDataFormYahoo.getQouteFromYahoo(url);
            if (lastDate.compareTo(quote.getDate()) != 0) {
                lastDate = quote.getDate();
                OHLCItem oHLCItem = new OHLCItem(new Minute(quote.getDate()), quote.getOpen(), quote.getHigh(), quote.getLow(), quote.getLast());
                oHLCSeries.add(oHLCItem);
                oHLCSeries.fireSeriesChanged();
            }

            synchronized (this) {
                try {
                    this.wait(Timer.ONE_SECOND * 5 * 4);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ViewForDiagram.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
