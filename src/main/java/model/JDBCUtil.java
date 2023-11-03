/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author DELL
 */
public class JDBCUtil {

    public static Connection getConnection() {
        Connection c = null;
        try {
            DriverManager.registerDriver(new Driver());
            String url = "jdbc:mySQL://localhost:3306/Polypro";
            String username = "root";
            String password = "tqthuan1734";
            c = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
        }
        return c;

    }

    public static void closeConnection(Connection c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
