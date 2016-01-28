/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model;

import com.google.gson.Gson;

/**
 *
 * @author Volodymyr
 */
public class НовыйClass {
    public static void main(String[] args) {
        Gson gson = new Gson();
        String stringObj = "{\"query\":{\"count\":1,\"created\":\"2016-01-28T18:14:59Z\",\"lang\":\"uk-UA\",\"results\":{\"row\":{\"col0\":\"EUR/USD\",\"col1\":\"1/28/2016\",\"col2\":\"6:14pm\",\"col3\":\"1.0947\",\"col4\":\"1.0899\",\"col5\":\"1.0957\",\"col6\":\"1.0865\",\"col7\":\"+0.0045\",\"col8\":\"+0.4174%\",\"col9\":\"1.0458 - 1.1707\",\"col10\":\"0\"}}}}";
        DataFromYahoo dataFromYahoo = gson.fromJson(stringObj, DataFromYahoo.class);
        System.out.println("data "+dataFromYahoo.getQuery().getResults().getRow().getName()+" = "+dataFromYahoo.getQuery().getResults().getRow().getLast());
    }
    
}
    






