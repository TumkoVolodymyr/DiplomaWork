/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model;

import java.math.BigDecimal;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 *
 * @author Volodymyr
 */
public class GetDataFormYahoo {
    /**
     * Connect to YahoooFinance and get data for entered type
     * 
     * @return {@code BigDecimal} Price at curent time
     */
    public static BigDecimal getPriceValue(){
        BigDecimal pricevalue = null;
        try {
            Stock stock = YahooFinance.get("EURUSD=X");
            if(null!=stock.getQuote().getPrice()){
                pricevalue = stock.getQuote().getPrice();
            }
        } catch (Exception e) {             
            System.out.println("java.lang.StringIndexOutOfBoundsException");
        }
        return pricevalue;
    };
    
}
