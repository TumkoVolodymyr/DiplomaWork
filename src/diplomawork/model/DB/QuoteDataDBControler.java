/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model.DB;

import ch.qos.logback.core.joran.action.ParamAction;
import diplomawork.model.Quote;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
//import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Volodymyr
 */
public class QuoteDataDBControler {

    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;
    public static PreparedStatement preparedStatement;

    public static void createConnection() {
        try {
            conn = null;
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:DIPLOMA_WORK.sqlite");

            System.out.println("База Підключена!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QuoteDataDBControler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(QuoteDataDBControler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("База відключена");
        } catch (SQLException ex) {
            Logger.getLogger(QuoteDataDBControler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void exexcutStatement(String sql) {
        createConnection();
        if (conn != null) {
            try {
                statmt = conn.createStatement();
                statmt.execute(sql);
                statmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(QuoteDataDBControler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Connection is not availible");
        }
        closeConnection();

    }

    public static ResultSet executeQueryStatement(String sql) {
        ResultSet resultSet = null;
        if (conn != null) {
            try {
                statmt = conn.createStatement();
                resultSet = statmt.executeQuery(sql);
                statmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(QuoteDataDBControler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Connection is not availible");
        }
        return resultSet;

    }
    public static void addQuoteData(Quote quote) {
        String sql = "INSERT INTO QuoteData (Name,Date,Open,High,Low,Close,Trend,Herst) VALUES (?,?,?,?,?,?,?,?)";
        createConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, quote.getName());
            ps.setDate(2, (new Date(quote.getDate().getTime())));
            ps.setDouble(3, quote.getOpen());
            ps.setDouble(4, quote.getHigh());
            ps.setDouble(5, quote.getLow());
            ps.setDouble(6, quote.getLast());
            ps.setDouble(7, quote.getTrend());
            ps.setFloat(8, quote.getHerst());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(QuoteDataDBControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        closeConnection();

    }

    public static ResultSet selectAllByName(String name) {
        String sql = "SELECT * FROM  QuoteData WHERE Name=?";
        ResultSet resultSet = null;
        createConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            resultSet = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(QuoteDataDBControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }

    public static List<Quote> getLastNQuotesByName(int count, String nameQ) {
        ResultSet resultSet = selectAllByName(nameQ);
        try {
            ArrayList<String> columnNames = new ArrayList<String>();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            columnNames.clear();

            int columnCount = rsmd.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
            }
            ArrayList<Quote> quotes = new ArrayList<>();
            while (resultSet.next()) {
                String name=null;
                Date date=null;
                Double last=null;
                Double open=null;
                Double high=null;
                Double low=null;
                Double trend=null;
                Float herst=null;
                for (int i = 0; i < columnCount; i++) {
                    if (columnNames.get(i).equals("Name")) {
                        name=(resultSet.getString(i + 1));
                    } else if (columnNames.get(i).equals("Date")) {
                        date=(resultSet.getDate(i + 1));
                    }else if (columnNames.get(i).equals("Open")) {
                        open=(resultSet.getDouble(i + 1));
                    }else if (columnNames.get(i).equals("High")) {
                        high=(resultSet.getDouble(i + 1));
                    }else if (columnNames.get(i).equals("Low")) {
                        low=(resultSet.getDouble(i + 1));
                    }else if (columnNames.get(i).equals("Close")) {
                        last=(resultSet.getDouble(i + 1));
                    }else if (columnNames.get(i).equals("Trend")) {
                        trend=(resultSet.getDouble(i + 1));
                    }else if (columnNames.get(i).equals("Herst")) {
                        herst=(resultSet.getFloat(i + 1));
                    }
                                       
                }
                quotes.add(new Quote(name, date, last, open, high, low, trend, herst));

            }
            int quotesCount = quotes.size();
            if(quotesCount>=count){
                return quotes.subList(quotesCount-count, quotesCount);
            }
            return quotes;
        } catch (SQLException ex) {
            Logger.getLogger(QuoteDataDBControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

//    public static void main(String[] args) {
//        try {
//            resSet = selectAllByName("EUR");
//            int n = resSet.getFetchSize();
//            System.out.println("edffd=  " + n);
//            while (resSet.next()) {
//                int id = resSet.getInt("id");
//                String name = resSet.getString("Name");
//
//                System.out.println("ID = " + id);
//                System.out.println("name = " + name);
//                System.out.println();
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(QuoteDataDBControler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        List<Quote> al = getLastNQuotesByName(2, "EUR");
//        for (Quote quote : al) {
//            System.out.println(""+quote.toString());
//        }
//        closeConnection();
//    }

}
