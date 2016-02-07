/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model;

import diplomawork.model.util.GanerateDataForTrainNN;
import diplomawork.model.yahoodata.DataFromYahoo;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public static Double getPriceValue(String url) {
        return getQouteFromYahoo(url).getLast();
    }
    /***
     * Get DataFromYahoo from YahoooFinance URL
     * @param url URL adres off server
     * @return object from json string
     */
    private static DataFromYahoo getData(String url) {
        Gson gson = new Gson();
        String stringObj = getContenxtWeb(url);
        DataFromYahoo dataFromYahoo = gson.fromJson(stringObj, DataFromYahoo.class);
        return dataFromYahoo;
    }
    /***
     * Get Quote from YahoooFinance URL
     * @param url URL adres off server
     * @return Quote with all prices
     */
    public static Quote getQouteFromYahoo(String url) {
        DataFromYahoo dataFromYahoo = getData(url);
        String name = dataFromYahoo.getQuery().getResults().getRow().getName();
        String timeStr = dataFromYahoo.getQuery().getResults().getRow().getTime().toUpperCase();
        String stringDate = dataFromYahoo.getQuery().getResults().getRow().getDate() + " " + timeStr;
        Double Last = Double.valueOf(dataFromYahoo.getQuery().getResults().getRow().getLast());
        Double Open = Double.valueOf(dataFromYahoo.getQuery().getResults().getRow().getOpen());
        Double High = Double.valueOf(dataFromYahoo.getQuery().getResults().getRow().getHigh());
        Double Low = Double.valueOf(dataFromYahoo.getQuery().getResults().getRow().getLow());
        Double Volume = Double.valueOf(dataFromYahoo.getQuery().getResults().getRow().getVolume());
        DateFormat format = new SimpleDateFormat("M/dd/yyyy h:mma");
        format.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));
        Date dateD;
        try {
            dateD = format.parse(stringDate);
//            Locale local = new Locale("uk", "ukr");
//            System.out.println("Date 2= " + dateD);
//            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, local);
//            System.out.println("currentDate = " + df.format(dateD));
            Quote quote = new Quote(name, dateD, Last, Open, High, Low, Volume);
            return quote;
        } catch (ParseException ex) {
            Logger.getLogger(GanerateDataForTrainNN.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    /***
     * Connect to server and get JSONG stiring
     * @param urlS URL adres off server
     * @return  jsong string
     */
    public static String getContenxtWeb(String urlS) {
        String result = "";
        URL url;
        try {
            url = new URL(urlS);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String linea = reader.readLine();
                while (linea != null) {
                    result += linea;
                    linea = reader.readLine();
                }
                reader.close();
            } else {
                connection.disconnect();
                return null;
            }
            connection.disconnect();
            return result;
        } catch (Exception ex) {
            return result;
        }
    }

}
