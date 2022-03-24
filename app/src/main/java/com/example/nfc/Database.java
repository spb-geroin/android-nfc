package com.example.nfc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {
    private Connection connection;
    private PreparedStatement st;
    static ResultSet rs;


    // For Local PostgreSQL
    private final String host = "192.168.1.6";

    private final String database = "nfc";
    private final int port = 6432;
    private final String user = "nfc";
    private final String pass = "nfc";
    private String url = "jdbc:postgresql://178.250.242.76:5432/nfc";
    private boolean status;
    private String tag;

    public static String result = "";

    public Database(String tag) {
//        this.url = String.format(this.url, this.host, this.port, this.database);
        this.tag = tag;
        connect();
        //this.disconnect();
        System.out.println("connection status:" + status);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    private void connect() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(url, user, pass);
                    status = true;
                    System.out.println("connected:" + status);
                    st = connection.prepareStatement("SELECT * FROM nfc_tag WHERE id = " + tag);
                    rs = st.executeQuery();
                    if (rs.next()) {
//                        System.out.println("vivod1: "+rs.getRow());
                        result = rs.getString(2);
                        int id = rs.getInt("id");
                        String country = rs.getString("country");
                        String region = rs.getString("region");
                        String date = rs.getString("registration_date");
                        String street = rs.getString("street");
                        result = id + " " + country + " " + region + " " + date + " " + street;
                    }
                } catch (Exception e) {
                    status = false;
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
    }

    public String Query() {
        return "sd";
    }

    public Connection getExtraConnection() {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;
    }
}
