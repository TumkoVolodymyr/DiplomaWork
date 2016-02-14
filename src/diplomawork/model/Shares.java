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
public enum Shares {
    EURdUSD("EURUSD=X", "EUR/USD"), AAPL("AAPL", "Apple Inc.");
    private String yahooShareName;
    private String fullName;

    private Shares(String yahooShareName, String fullName) {
        this.yahooShareName = yahooShareName;
        this.fullName = fullName;
    }

    public String getYahooShareName() {
        return yahooShareName;
    }

   public String getFullName() {
        return fullName;
    }
   public static Shares getByFullName (String fullName){
       for (Shares share : values()) {
           if (share.getFullName().equals(fullName)){
               return share;
           }
       }
       return EURdUSD;
   }
   public static Shares getByStockName (String stockName){
       for (Shares share : values()) {
           if (share.getYahooShareName().equals(stockName)){
               return share;
           }
       }
       return EURdUSD;
   }
    

}
