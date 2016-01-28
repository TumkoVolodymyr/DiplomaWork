/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model;

/**
 *
 * @author Volodymyr
 */
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Row {

    @SerializedName("col0")
    @Expose
    private String Name;
    @SerializedName("col1")
    @Expose
    private String Date;
    @SerializedName("col2")
    @Expose
    private String Time;
    @SerializedName("col3")
    @Expose
    private String Last;
    @SerializedName("col4")
    @Expose
    private String Open;
    @SerializedName("col5")
    @Expose
    private String High;
    @SerializedName("col6")
    @Expose
    private String Low;
    @SerializedName("col7")
    @Expose
    private String Change;
    @SerializedName("col8")
    @Expose
    private String ChangeP;
    @SerializedName("col9")
    @Expose
    private String Range;
    @SerializedName("col10")
    @Expose
    private String Volume;

    /**
     *
     * @return The Name
     */
    public String getName() {
        return Name;
    }

    /**
     *
     * @param Name The Name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     *
     * @return The Date
     */
    public String getDate() {
        return Date;
    }

    /**
     *
     * @param Date The Date
     */
    public void setDate(String Date) {
        this.Date = Date;
    }

    /**
     *
     * @return The Time
     */
    public String getTime() {
        return Time;
    }

    /**
     *
     * @param Time The Time
     */
    public void setTime(String Time) {
        this.Time = Time;
    }

    /**
     *
     * @return The Last
     */
    public String getLast() {
        return Last;
    }

    /**
     *
     * @param Last The Last
     */
    public void setLast(String Last) {
        this.Last = Last;
    }

    /**
     *
     * @return The Open
     */
    public String getOpen() {
        return Open;
    }

    /**
     *
     * @param Open The Open
     */
    public void setOpen(String Open) {
        this.Open = Open;
    }

    /**
     *
     * @return The High
     */
    public String getHigh() {
        return High;
    }

    /**
     *
     * @param High The High
     */
    public void setHigh(String High) {
        this.High = High;
    }

    /**
     *
     * @return The Low
     */
    public String getLow() {
        return Low;
    }

    /**
     *
     * @param Low The Low
     */
    public void setLow(String Low) {
        this.Low = Low;
    }

    /**
     *
     * @return The Change
     */
    public String getChange() {
        return Change;
    }

    /**
     *
     * @param Change The Change
     */
    public void setChange(String Change) {
        this.Change = Change;
    }

    /**
     *
     * @return The ChangeP
     */
    public String getChangeP() {
        return ChangeP;
    }

    /**
     *
     * @param ChangeP The ChangeP
     */
    public void setChangeP(String ChangeP) {
        this.ChangeP = ChangeP;
    }

    /**
     *
     * @return The Range
     */
    public String getRange() {
        return Range;
    }

    /**
     *
     * @param Range The Range
     */
    public void setRange(String Range) {
        this.Range = Range;
    }

    /**
     *
     * @return The Volume
     */
    public String getVolume() {
        return Volume;
    }

    /**
     *
     * @param Volume The Volume
     */
    public void setVolume(String Volume) {
        this.Volume = Volume;
    }

}
