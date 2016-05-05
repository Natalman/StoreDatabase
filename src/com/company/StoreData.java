package com.company;

import java.sql.*;

public class StoreData {

    private static String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "Store";
    private static final String USER = "root";
    private static final String PASS = "itecitec";

    static Statement statement = null;
    static Connection conn = null;
    static ResultSet rs = null;

    public final static String REP_TABLE_NAME = "REP";
    public final static String PK_COLUMN = "RepNum";                   //Primary key column. Each movie will have a unique ID.


    public final static String REPNAME_COLUMN = "RepName";
    public final static String STREET_COLUMN = "Street";
    public final static String CITY_COLUMN = "City";
    public final static String STATE_COLUMN = "State";
    public final static String POSTALCODE_COLUMN = "PostalCode";
    public final static String COMMISSION_COLUMN = "Commission";
    public final static String RATE_COLUMN = "Rate";


    public final static double MIN_RATING = 0.01;
    public final static double MAX_RATING = 0.09;

    private static StoreModel storeModel;

    public static void main(String args[]) {

        //setup creates database (if it doesn't exist), opens connection, and adds sample data

        if (!setup()) {
            System.exit(-1);
        }

        if (!loadAllRep()) {
            System.exit(-1);
        }

        //If no errors, then start GUI
        RepForm tableGUI = new RepForm(storeModel);

    }

    //Create or recreate a ResultSet containing the whole database, and give it to storemodel
    public static boolean loadAllRep(){

        try{

            if (rs!=null) {
                rs.close();
            }

            String getAllData = "SELECT * FROM " + REP_TABLE_NAME;
            rs = statement.executeQuery(getAllData);

            if (storeModel == null) {
                //If no current movieDataModel, then make one
                storeModel = new StoreModel(rs);
            } else {
                //Or, if one already exists, update its ResultSet
                storeModel.updateResultSet(rs);
            }

            return true;

        } catch (Exception e) {
            System.out.println("Error loading or reloading the data");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }

    }

    public static boolean setup(){
        try {

            //Load driver class
            try {
                String Driver = "com.mysql.jdbc.Driver";
                Class.forName(Driver);
            } catch (ClassNotFoundException cnfe) {
                System.out.println("No database drivers found. Quitting");
                return false;
            }

            conn = DriverManager.getConnection(DB_CONNECTION_URL + DB_NAME, USER, PASS);


            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            //Does the table exist? If not, create it.
            if (!movieTableExists()) {

                //Create a table in the database with 3 columns: Movie title, year and rating
                String createTableSQL = "CREATE TABLE " + REP_TABLE_NAME + " (" + PK_COLUMN + " int NOT NULL AUTO_INCREMENT, " + REPNAME_COLUMN + " varchar(50), " + STREET_COLUMN + " varchar(50), " + CITY_COLUMN + " varchar(10)," + STATE_COLUMN + " varchar(10)," + POSTALCODE_COLUMN + " varchar(10)," + COMMISSION_COLUMN + " double, " + RATE_COLUMN + " double, PRIMARY KEY(" + PK_COLUMN + "))";
                System.out.println(createTableSQL);
                statement.executeUpdate(createTableSQL);

                System.out.println("Created movie_reviews table");

                String addDataSQL = "INSERT INTO " + REP_TABLE_NAME + "(" + REPNAME_COLUMN + ", " + STREET_COLUMN + ", " + CITY_COLUMN + ", " + STATE_COLUMN + ", " + POSTALCODE_COLUMN + ", " + COMMISSION_COLUMN + ", " + RATE_COLUMN + ")" + " VALUES ('Campos Rafael', '724 Vinca Dr', 'Grove', 'CA','90092', 300.00, 0.06)";
                statement.executeUpdate(addDataSQL);
                addDataSQL = "INSERT INTO " + REP_TABLE_NAME + "(" + REPNAME_COLUMN + ", " + STREET_COLUMN + ", " + CITY_COLUMN + ", " + STATE_COLUMN + ", " + POSTALCODE_COLUMN + ", " + COMMISSION_COLUMN + ", " + RATE_COLUMN + ")" + " VALUES ('Gradey Megan', '632 Liatris st', 'Fullton', 'CA','90085', 500.00, 0.08)";
                statement.executeUpdate(addDataSQL);
                addDataSQL = "INSERT INTO " + REP_TABLE_NAME + "(" + REPNAME_COLUMN + ", " + STREET_COLUMN + ", " + CITY_COLUMN + ", " + STATE_COLUMN + ", " + POSTALCODE_COLUMN + ", " + COMMISSION_COLUMN + ", " + RATE_COLUMN + ")" + " VALUES ('Tian Hui', '1785 Tyler Ave', 'Northfield', 'CA','90098', 100.00, 0.06)";
                statement.executeUpdate(addDataSQL);
                addDataSQL = "INSERT INTO " + REP_TABLE_NAME + "(" + REPNAME_COLUMN + ", " + STREET_COLUMN + ", " + CITY_COLUMN + ", " + STATE_COLUMN + ", " + POSTALCODE_COLUMN + ", " + COMMISSION_COLUMN + ", " + RATE_COLUMN + ")" + " VALUES ('Sefton Janet', '267 Oakley st', 'Congaree', 'CA','90097', 0.00, 0.06)";
                statement.executeUpdate(addDataSQL);
            }
            return true;

        } catch (SQLException se) {
            System.out.println(se);
            se.printStackTrace();
            return false;
        }
    }

    private static boolean movieTableExists() throws SQLException {

        String checkTablePresentQuery = "SHOW TABLES LIKE '" + REP_TABLE_NAME + "'";   //Can query the database schema
        ResultSet tablesRS = statement.executeQuery(checkTablePresentQuery);
        if (tablesRS.next()) {    //If ResultSet has a next row, it has at least one row... that must be our table
            return true;
        }
        return false;

    }

    //Close the ResultSet, statement and connection, in that order.
    public static void shutdown(){
        try {
            if (rs != null) {
                rs.close();
                System.out.println("Result set closed");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        try {
            if (statement != null) {
                statement.close();
                System.out.println("Statement closed");
            }
        } catch (SQLException se){
            //Closing the connection could throw an exception too
            se.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
                System.out.println("Database connection closed");
            }
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
    }
}