/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model;

import java.util.Date;

/**
 *
 * @author Volodymyr
 */
public class Quote {

    private String name;
    private Date Date;
    private Double Last;
    private Double Open;
    private Double High;
    private Double Low;
    private Double Volume;
    public Quote(String name, Date Date, Double Last, Double Open, Double High, Double Low, Double Volume) {
        this.name = name;
        this.Date = Date;
        this.Last = Last;
        this.Open = Open;
        this.High = High;
        this.Low = Low;
        this.Volume = Volume;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return Date;
    }

    public Double getLast() {
        return Last;
    }

    public Double getOpen() {
        return Open;
    }

    public Double getHigh() {
        return High;
    }

    public Double getLow() {
        return Low;
    }

    public Double getVolume() {
        return Volume;
    }

    

}
