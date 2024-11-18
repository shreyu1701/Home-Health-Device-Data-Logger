package com.zodiac.homehealthdevicedatalogger.Data;

import com.zodiac.homehealthdevicedatalogger.Models.User;

import java.sql.*;

public class DBConnect {

    User user = new User();
    public static void main(String[] args) {
        Connection con = DBConnect.getConnection();
        if (con != null) {
            System.out.println("Connected to database");
            try{
                Statement stm = con.createStatement();
                ResultSet rs = stm.executeQuery("select * from author");
                while (rs.next()) {
                    System.out.println(rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3));
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Failed to connect to database");
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try{
            String url = "jdbc:oracle:thin:@calvin.humber.ca:1521:grok";
            String user = "n01660845";
            String password = "oracle";
            connection = DriverManager.getConnection(url, user , password);
            return connection;
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
