package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    //Singleton Design Pattern
    private static Db instance = null;
    Connection connection = null;
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/tourismagency";
    public static final String DB_USERNAME = "postgres";
    public static final String DB_PASS = "postgres";

    private Db(){
        try {
            this.connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASS);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public Connection getConnection() {
        return connection;
    }

    public static Connection getInstance() { // Singleton Design Pattern i uyguladık
        try{
            if (instance == null || instance.getConnection().isClosed()){
                instance = new Db(); //yeni connection oluşturuyor
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());

        }
        return instance.getConnection();
    }
}
