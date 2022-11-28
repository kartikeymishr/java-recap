package com.kartikey.databases;

import java.sql.*;

public class Main {

    public static final String DB_NAME = "testjava.db";
    public static final String TEST_JAVA_DB_URL = "jdbc:sqlite:E:\\Workspaces\\IdeaProjects\\java-recap\\" + DB_NAME;
    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";


    public static void main(String[] args) {
        try {
            // Connection object for creating a connection to the DB
            Connection connection = DriverManager.getConnection(TEST_JAVA_DB_URL);
            connection.setAutoCommit(false);

            // Statement object for creating executable SQL statements
            Statement statement = connection.createStatement();

            statement.execute("drop table if exists " + TABLE_CONTACTS);

            statement.execute("create table if not exists " + TABLE_CONTACTS
                    + " (" + COLUMN_NAME + " text, " + COLUMN_PHONE + " integer, " + COLUMN_EMAIL + " text)");

            insertContact(statement, "Kartikey", 95570691, "kartikey@email.com");
            insertContact(statement, "Joe", 349375459, "joe@anywhere.com");
            insertContact(statement, "Jane", 976583454, "jane@somewhere.com");
            insertContact(statement, "Fido", 849540954, "fido@nowhere.com");

            statement.execute("update " + TABLE_CONTACTS + " set " + COLUMN_PHONE + " = 556677889 where " + COLUMN_NAME + " = 'Jane'");
            statement.execute("delete from " + TABLE_CONTACTS + " where " + COLUMN_NAME + " = 'Joe'");

            ResultSet results = statement.executeQuery("select * from " + TABLE_CONTACTS);
            while (results.next()) {
                System.out.println(results.getString(COLUMN_NAME) + " "
                        + results.getInt(COLUMN_PHONE) + " "
                        + results.getString(COLUMN_EMAIL));
            }

            results.close();

            statement.close();
            connection.commit();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Something went wrong " + ex.getMessage());
        }
    }

    public static void insertContact(Statement statement, String name, int phone, String email) throws SQLException {
        statement.execute("insert into " + TABLE_CONTACTS
                + " (" + COLUMN_NAME + ", " + COLUMN_PHONE + ", " + COLUMN_EMAIL + ") values ( '"
                + name + "', " + phone + ", '" + email + "' )");
    }
}
