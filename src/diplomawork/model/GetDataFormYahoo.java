/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
       
    private static DataFromYahoo getData(String url) {
        Gson gson = new Gson();
        String stringObj = getContenxtWeb(url);
        DataFromYahoo dataFromYahoo = gson.fromJson(stringObj, DataFromYahoo.class);
        return dataFromYahoo;
    }

    public static Quote getQouteFromYahoo(String url) {
        DataFromYahoo dataFromYahoo = getData(url);
        String name = dataFromYahoo.getQuery().getResults().getRow().getName();
        String stringDate = dataFromYahoo.getQuery().getResults().getRow().getDate() + " " + dataFromYahoo.getQuery().getResults().getRow().getTime().replace("pm", " PM");
        Double Last = Double.valueOf(dataFromYahoo.getQuery().getResults().getRow().getLast());
        Double Open = Double.valueOf(dataFromYahoo.getQuery().getResults().getRow().getOpen());
        Double High = Double.valueOf(dataFromYahoo.getQuery().getResults().getRow().getHigh());
        Double Low = Double.valueOf(dataFromYahoo.getQuery().getResults().getRow().getLow());
        Double Volume = Double.valueOf(dataFromYahoo.getQuery().getResults().getRow().getVolume());
        DateFormat format = new SimpleDateFormat("M/dd/yyyy h:mm a", Locale.US);
        Date dateD;
        try {
            dateD = format.parse(stringDate);
            System.out.println("Date 2= "+ dateD);
            Quote quote = new Quote(name, dateD, Last, Open, High, Low, Volume);
            return quote;
        } catch (ParseException ex) {
            Logger.getLogger(НовыйClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

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
