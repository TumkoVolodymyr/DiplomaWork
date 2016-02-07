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
import diplomawork.model.yahoodata.Results;

@Generated("org.jsonschema2pojo")
public class Query {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("results")
    @Expose
    private Results results;

    /**
     *
     * @return The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @param count The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     *
     * @return The created
     */
    public String getCreated() {
        return created;
    }

    /**
     *
     * @param created The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     *
     * @return The lang
     */
    public String getLang() {
        return lang;
    }

    /**
     *
     * @param lang The lang
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     *
     * @return The results
     */
    public Results getResults() {
        return results;
    }

    /**
     *
     * @param results The results
     */
    public void setResults(Results results) {
        this.results = results;
    }

}
