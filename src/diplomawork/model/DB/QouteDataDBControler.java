/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model.DB;

import ch.qos.logback.core.joran.action.ParamAction;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Volodymyr
 */
public class QouteDataDBControler {

    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;
    public static PreparedStatement preparedStatement;
    
    public static void createConnection(){
        try {
            conn = null;
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:DIPLOMA_WORK.sqlite");
            
            System.out.println("База Підключена!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QouteDataDBControler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(QouteDataDBControler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void closeConnection(){
        try {
            conn.close();
            System.out.println("База відключена");
        } catch (SQLException ex) {
            Logger.getLogger(QouteDataDBControler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void exexcutStatement(String sql){
        createConnection();
        if(conn!=null){
            try {
                statmt  = conn.createStatement();
                statmt.execute(sql);
                statmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(QouteDataDBControler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("Connection is not availible");
        } 
        closeConnection();
        
    }
    public static ResultSet executeQueryStatement(String sql){
        ResultSet resultSet = null;
        if(conn!=null){
            try {
                statmt  = conn.createStatement();
                resultSet = statmt.executeQuery(sql);
                statmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(QouteDataDBControler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("Connection is not availible");
        } 
        return resultSet;
        
    }
    public static void addQuoteData(String name, Date date,Double open,Double high,Double low,Double close,Double trend,Float herst){
        String sql = "INSERT INTO QuoteData (Name,Date,Open,High,Low,Close,Trend,Herst) VALUES (?,?,?,?,?,?,?,?)";
        createConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setDate(2, (java.sql.Date) date);
            ps.setDouble(3, open);
            ps.setDouble(4, high);
            ps.setDouble(5, low);
            ps.setDouble(6, close);
            ps.setDouble(7, trend);
            ps.setFloat(8, herst);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(QouteDataDBControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        closeConnection();
        
    }
    public static ResultSet select (String name){
        String sql = "SELECT * FROM  QuoteData WHERE Name=?";
        ResultSet resultSet = null;
        createConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            resultSet = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(QouteDataDBControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultSet;
    }
    public static void main(String[] args) {
        try {
            resSet = select("EUR");
            while (resSet.next()) {
                int id = resSet.getInt("id");
                String name = resSet.getString("Name");
                
                System.out.println("ID = " + id);
                System.out.println("name = " + name);
                System.out.println();
            }
        } catch (SQLException ex) {
            Logger.getLogger(QouteDataDBControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        closeConnection();
    }
    

}
