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
public class DataFromYahoo {

    @SerializedName("query")
    @Expose
    private Query query;

    /**
     *
     * @return The query
     */
    public Query getQuery() {
        return query;
    }

    /**
     *
     * @param query The query
     */
    public void setQuery(Query query) {
        this.query = query;
    }

}
