package com.zodiac.homehealthdevicedatalogger.Data;

import java.sql.*;

public class DBConnect {
    public static void main(String[] args) {
        Connection con = DBConnect.getConnection();
        if (con != null) {
            System.out.println("Connected to database");
            try{
                con.close();
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
            String url = "";
            String user = "";
            String password = "";
            connection = DriverManager.getConnection(url, user , password);
            return connection;
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
