/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model.yahoodata;

/**
 *
 * @author Volodymyr
 */
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Results {

    @SerializedName("row")
    @Expose
    private Row row;

    /**
     *
     * @return The row
     */
    public Row getRow() {
        return row;
    }

    /**
     *
     * @param row The row
     */
    public void setRow(Row row) {
        this.row = row;
    }

}
