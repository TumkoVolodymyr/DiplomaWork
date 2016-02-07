/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diplomawork.model.DB;

/**
 *
 * @author Владимир
 */
import com.sun.org.apache.bcel.internal.generic.SWITCH;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

public class DB_table extends AbstractTableModel {

    public Connection con;
    private ArrayList<String> columnNames = new ArrayList<String>();
    private ArrayList<Class> columnTypes = new ArrayList<Class>();
    private ArrayList data = new ArrayList();

    public DB_table(String query, Connection con2) {
        try {
            this.con = con2;
            Statement st;
            ResultSet rs;
            st = this.con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(query);
            this.setDataSource(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DB_table.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public DB_table( ResultSet rs) {    
            this.setDataSource(rs);   
    }

    public int getRowCount() {
        synchronized (data) {
            return data.size();
        }
    }

    public int getColumnCount() {
        return columnNames.size();
    }

    public Object getValueAt(int row, int col) {
        synchronized (data) {
            return ((ArrayList) data.get(row)).get(col);
        }
    }

    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    public Class getColumnClass(int col) {
        return columnTypes.get(col);
    }

    public boolean isEditable() {
        return true;
    }

    public boolean isCellEditable(int row, int col) {
        if (col == 0) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public void setValueAt(Object obj, int row, int col) {
        synchronized (data) {
            ((ArrayList) data.get(row)).set(col, obj);
        }
    }

    /**
     * Core of the model. Initializes column names, types, data from ResultSet.
     *
     * @param rs ResultSet from which all information for model is token.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void setDataSource(ResultSet rs) {
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            columnNames.clear();
            columnTypes.clear();
            data.clear();

            int columnCount = rsmd.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                columnNames.add(rowZag(rsmd.getColumnName(i + 1)));
                Class type = Class.forName(rsmd.getColumnClassName(i + 1));
                columnTypes.add(type);
            }
            fireTableStructureChanged();
            while (rs.next()) {
                ArrayList rowData = new ArrayList();
                for (int i = 0; i < columnCount; i++) {
                    if (columnTypes.get(i) == String.class) {
                        rowData.add(rs.getString(i + 1));
                    } else {
                        rowData.add(rs.getObject(i + 1));
                    }
                }
                synchronized (data) {
                    data.add(rowData);
                    this.fireTableRowsInserted(data.size() - 1, data.size() - 1);
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DB_table.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DB_table.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    String rowZag(String name) {
        String pover;
        switch (name) {
            case "idkoryst":
                pover = "Ідентифікатор";
                break;
            case "login":
                pover = "Логін";
                break;
            case "parol":
                pover = "Пароль";
                break;
            case "name":
                pover = "Ім'я";
                break;
            case "adres":
                pover = "Адреса";
                break;
            case "kontakty":
                pover = "Ел. пошта";
                break;
            case "rol_koryst":
                pover = "Роль користувача";
                break;
            case "reestr":
                pover = "Зар'еєстрований";
                break;

            default:
                pover = name;
        }
        return pover;
    }

}
