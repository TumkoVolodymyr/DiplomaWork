/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model.DB;

import java.util.Date;

/**
 *
 * @author Volodymyr
 */
public class QuoteDataDB {
    private String name;
    private Date date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double trend;
    private Float herst;

    public QuoteDataDB(String name, Date date, Double open, Double high, Double low, Double close, Double trend, Float herst) {
        this.name = name;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.trend = trend;
        this.herst = herst;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Double getOpen() {
        return open;
    }

    public Double getHigh() {
        return high;
    }

    public Double getLow() {
        return low;
    }

    public Double getClose() {
        return close;
    }

    public Double getTrend() {
        return trend;
    }

    public Float getHerst() {
        return herst;
    }
    
}
