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

    //Table coordination
    public final static String REP_TABLE_NAME = "Rep_Table";
    public final static String CUSTOMER_TABLE = "Customer_Table";
    public final static String ORDER_TABLE = "Order_Table";
    public final static String ITEM_TABLE = "Item_Table";

    //Primary keys' columns.
    public final static String PK_COLUMN = "RepNum";
    public final static String CU_PK = "CustNum";
    public final static String OD_PK = "OrderNum";
    public final static String ITEM_PK = "ItemNum";

    //Rep table columns
    public final static String REPNAME_COLUMN = "RepName";
    public final static String STREET_COLUMN = "Street";
    public final static String CITY_COLUMN = "City";
    public final static String STATE_COLUMN = "State";
    public final static String POSTALCODE_COLUMN = "PostalCode";
    public final static String COMMISSION_COLUMN = "Commission";
    public final static String RATE_COLUMN = "Rate";

    //Customer table columns
    public final static String CUSTNAME_COLUMN = "CustName";
    public final static String BALANCE_COLUMN = "balance";

    //Item table Colums
    public final static String DESC_COLUMN = "Description";
    public final static String ONHAND_COLUMN = "onHand";
    public final static String CATEGORY_COLUMN = "Category";
    public final static String PRICE_COLUMN = "Price";

    //Order Table Colums
    public final static String DATE_COLUMN = "OrderDate";
    public final static String NUM_ORDERED_COLUMN = "Num_Ordered";
    public final static String TOTAL_COLUMN = "Total";

    public final static double MIN_RATING = 0.01;
    public final static double MAX_RATING = 0.09;

    public static StoreModel getStoreModel() {
        return storeModel;
    }

    public static void setStoreModel(StoreModel storeModel) {
        StoreData.storeModel = storeModel;
    }



    public static CustModel getCustmodel() {
        return custmodel;
    }

    public static void setCustmodel(CustModel custmodel) {
        StoreData.custmodel = custmodel;
    }



    public static ItemModel getItemModel() {
        return itemModel;
    }

    public static void setItemModel(ItemModel itemModel) {
        StoreData.itemModel = itemModel;
    }



    public static OrderModel getOrderModel() {
        return orderModel;
    }

    public static void setOrderModel(OrderModel orderModel) {
        StoreData.orderModel = orderModel;
    }

    private static StoreModel storeModel;
    private static CustModel custmodel;
    private static ItemModel itemModel;
    private static OrderModel orderModel;

    public static void main(String args[]) {

        //setup creates database (if it doesn't exist), opens connection, and adds sample data

        if (!setup()) {
            System.exit(-1);
        }

        if (!loadAllRep() && !loadCust() && !loadItem() && !loadOrder()) {
            System.exit(-1);
        }

        //If no errors, then start GUI
        ControlTable tableGUI = new ControlTable();

    }

    //Create or recreate a ResultSet containing the whole database, and give it to storemodel, custModel, itemModel, and orderModel

    public static boolean loadCust(){
        try {

            if (rs!=null) {
                rs.close();
            }
            String getCust = "SELECT * FROM " + CUSTOMER_TABLE;

            rs = statement.executeQuery(getCust);

            if (custmodel == null) {
                custmodel = new CustModel(rs);
            } else {
                custmodel.updateResultSetCust(rs);
            }
            return true;
        }catch (Exception m) {
            System.out.println("Error loading or reloading the data");
            System.out.println(m);
            m.printStackTrace();
            return false;
        }
    }
    public static boolean loadAllRep(){

        try{

            if (rs!=null) {
                rs.close();
            }

            //Geting data from tables
            String getAllData = "SELECT * FROM " + REP_TABLE_NAME;
            //Creating a statement for them
            rs = statement.executeQuery(getAllData);

            if (storeModel == null ) {
                //If no current dataSet, It then creates them
                storeModel = new StoreModel(rs);
            } else {
                //Or, if they already exist, update their ResultSets.
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
    public static boolean loadItem(){
        try {

            if (rs != null) {
                rs.close();
            }
            String getItem = "SELECT * FROM " + ITEM_TABLE;

            rs = statement.executeQuery(getItem);

            if (itemModel == null) {
                itemModel = new ItemModel(rs);
            } else {
                itemModel.updateResultSetItem(rs);
            }
            return true;
        }catch (Exception e) {
            System.out.println("Error loading or reloading the data");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }
    public static boolean loadOrder(){
        try {

            if (rs != null) {
                rs.close();
            }
            String getOrder = "SELECT * FROM " + ORDER_TABLE;

            rs = statement.executeQuery(getOrder);

            if (orderModel == null) {
                orderModel = new OrderModel(rs);
            } else {
                orderModel.updateResultSetOrder(rs);
            }
            return true;
        }catch (Exception e) {
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
            if (!TableExists()) {

                //Creating a rep table
                String createTableSQL = "CREATE TABLE " + REP_TABLE_NAME + " (" + PK_COLUMN + " int NOT NULL AUTO_INCREMENT, " + REPNAME_COLUMN + " varchar(50), " + STREET_COLUMN + " varchar(50), " + CITY_COLUMN + " varchar(10)," + STATE_COLUMN + " varchar(10)," + POSTALCODE_COLUMN + " varchar(10)," + COMMISSION_COLUMN + " double, " + RATE_COLUMN + " double, PRIMARY KEY(" + PK_COLUMN + "))";
                System.out.println(createTableSQL);
                statement.executeUpdate(createTableSQL);

                System.out.println("Created Rep table");

                String addDataSQL = "INSERT INTO " + REP_TABLE_NAME + "(" + REPNAME_COLUMN + ", " + STREET_COLUMN + ", " + CITY_COLUMN + ", " + STATE_COLUMN + ", " + POSTALCODE_COLUMN + ", " + COMMISSION_COLUMN + ", " + RATE_COLUMN + ")" + " VALUES ('Campos Rafael', '724 Vinca Dr', 'Grove', 'CA','90092', 300.00, 0.06)";
                statement.executeUpdate(addDataSQL);
                addDataSQL = "INSERT INTO " + REP_TABLE_NAME + "(" + REPNAME_COLUMN + ", " + STREET_COLUMN + ", " + CITY_COLUMN + ", " + STATE_COLUMN + ", " + POSTALCODE_COLUMN + ", " + COMMISSION_COLUMN + ", " + RATE_COLUMN + ")" + " VALUES ('Gradey Megan', '632 Liatris st', 'Fullton', 'CA','90085', 500.00, 0.08)";
                statement.executeUpdate(addDataSQL);
                addDataSQL = "INSERT INTO " + REP_TABLE_NAME + "(" + REPNAME_COLUMN + ", " + STREET_COLUMN + ", " + CITY_COLUMN + ", " + STATE_COLUMN + ", " + POSTALCODE_COLUMN + ", " + COMMISSION_COLUMN + ", " + RATE_COLUMN + ")" + " VALUES ('Tian Hui', '1785 Tyler Ave', 'Northfield', 'CA','90098', 100.00, 0.06)";
                statement.executeUpdate(addDataSQL);
                addDataSQL = "INSERT INTO " + REP_TABLE_NAME + "(" + REPNAME_COLUMN + ", " + STREET_COLUMN + ", " + CITY_COLUMN + ", " + STATE_COLUMN + ", " + POSTALCODE_COLUMN + ", " + COMMISSION_COLUMN + ", " + RATE_COLUMN + ")" + " VALUES ('Sefton Janet', '267 Oakley st', 'Congaree', 'CA','90097', 0.00, 0.06)";
                statement.executeUpdate(addDataSQL);

                //Creating a customer table
                String createCustTable = "CREATE TABLE " + CUSTOMER_TABLE + " (" + CU_PK + " int NOT NULL AUTO_INCREMENT, " + CUSTNAME_COLUMN + " varchar(50), " + STREET_COLUMN + " varchar(50), " + CITY_COLUMN + " varchar(10)," + STATE_COLUMN + " varchar(10)," + POSTALCODE_COLUMN + " varchar(10)," + BALANCE_COLUMN + " double," + PK_COLUMN + " int, PRIMARY KEY(" + CU_PK + "))";
                System.out.println(createCustTable);
                statement.executeUpdate(createCustTable);

                String addToCust = "INSERT INTO " + CUSTOMER_TABLE + "(" + CUSTNAME_COLUMN + ", " + STREET_COLUMN + ", " + CITY_COLUMN + ", " + STATE_COLUMN + ", " + POSTALCODE_COLUMN + ", " + BALANCE_COLUMN + ", " + PK_COLUMN + ")" + " VALUES ('Joel Wilson', '28 lakeson St.', 'Fullton', 'CA','90085', 500.00,1)";
                statement.executeUpdate(addToCust);
                addToCust = "INSERT INTO " + CUSTOMER_TABLE + "(" + CUSTNAME_COLUMN + ", " + STREET_COLUMN + ", " + CITY_COLUMN + ", " + STATE_COLUMN + ", " + POSTALCODE_COLUMN + ", " + BALANCE_COLUMN + ", " + PK_COLUMN + ")" + " VALUES ('Ron King', '452 Columbus Dr.', 'Grove', 'CA','90092', 700.00,2)";
                statement.executeUpdate(addToCust);
                addToCust = "INSERT INTO " + CUSTOMER_TABLE + "(" + CUSTNAME_COLUMN + ", " + STREET_COLUMN + ", " + CITY_COLUMN + ", " + STATE_COLUMN + ", " + POSTALCODE_COLUMN + ", " + BALANCE_COLUMN + ", " + PK_COLUMN + ")" + " VALUES ('John willbow', '342 Magee St.', 'Congaree', 'CA','90097', 800.00,3)";
                statement.executeUpdate(addToCust);
                addToCust = "INSERT INTO " + CUSTOMER_TABLE + "(" + CUSTNAME_COLUMN + ", " + STREET_COLUMN + ", " + CITY_COLUMN + ", " + STATE_COLUMN + ", " + POSTALCODE_COLUMN + ", " + BALANCE_COLUMN + ", " + PK_COLUMN + ")" + " VALUES ('Gerry Coldwell', '124 Main St.', 'Mesa', 'CA','900104', 200.00,3)";
                statement.executeUpdate(addToCust);
                addToCust = "INSERT INTO " + CUSTOMER_TABLE + "(" + CUSTNAME_COLUMN + ", " + STREET_COLUMN + ", " + CITY_COLUMN + ", " + STATE_COLUMN + ", " + POSTALCODE_COLUMN + ", " + BALANCE_COLUMN + ", " + PK_COLUMN + ")" + " VALUES ('Anita Blink', '3456 Central Ave.', 'Fullton', 'CA','90125', 900.00,2)";
                statement.executeUpdate(addToCust);
                addToCust = "INSERT INTO " + CUSTOMER_TABLE + "(" + CUSTNAME_COLUMN + ", " + STREET_COLUMN + ", " + CITY_COLUMN + ", " + STATE_COLUMN + ", " + POSTALCODE_COLUMN + ", " + BALANCE_COLUMN + ", " + PK_COLUMN + ")" + " VALUES ('Steven Young', '12 Rising Sun Ave', 'Almondon', 'CA','90104', 500.00,2)";
                statement.executeUpdate(addToCust);
                addToCust = "INSERT INTO " + CUSTOMER_TABLE + "(" + CUSTNAME_COLUMN + ", " + STREET_COLUMN + ", " + CITY_COLUMN + ", " + STATE_COLUMN + ", " + POSTALCODE_COLUMN + ", " + BALANCE_COLUMN + ", " + PK_COLUMN + ")" + " VALUES ('Andrea Karldrim', '382 Wildwood Ave', 'Northfield', 'CA','90078', 1000.00,1)";
                statement.executeUpdate(addToCust);
                addToCust = "INSERT INTO " + CUSTOMER_TABLE + "(" + CUSTNAME_COLUMN + ", " + STREET_COLUMN + ", " + CITY_COLUMN + ", " + STATE_COLUMN + ", " + POSTALCODE_COLUMN + ", " + BALANCE_COLUMN + ", " + PK_COLUMN + ")" + " VALUES ('Gloria VenKkolm', '945 Gilham St.', 'Mesa', 'CA','90045', 10.00,1)";
                statement.executeUpdate(addToCust);


                //Creating an Item Table
                String createItemTable = "CREATE TABLE " + ITEM_TABLE + " (" + ITEM_PK + " int NOT NULL AUTO_INCREMENT, " + DESC_COLUMN + " varchar(50), " + ONHAND_COLUMN + " int, " + CATEGORY_COLUMN + " varchar(10)," + PRICE_COLUMN + " double, PRIMARY KEY(" + ITEM_PK + "))";
                System.out.println(createItemTable);
                statement.executeUpdate(createItemTable);

                String addToItem = "INSERT INTO " + ITEM_TABLE + "(" + DESC_COLUMN+ ", " + ONHAND_COLUMN + ", " + CATEGORY_COLUMN + ", " + PRICE_COLUMN + ")" + " VALUES ('XBOX ONE', 20, 'Console', 350.00)";
                statement.executeUpdate(addToItem);
                addToItem = "INSERT INTO " + ITEM_TABLE + "(" + DESC_COLUMN+ ", " + ONHAND_COLUMN + ", " + CATEGORY_COLUMN + ", " + PRICE_COLUMN + ")" + " VALUES ('XBOX 360', 5, 'Console', 150.00)";
                statement.executeUpdate(addToItem);
                addToItem = "INSERT INTO " + ITEM_TABLE + "(" + DESC_COLUMN+ ", " + ONHAND_COLUMN + ", " + CATEGORY_COLUMN + ", " + PRICE_COLUMN + ")" + " VALUES ('PS4', 10, 'Console', 300.00)";
                statement.executeUpdate(addToItem);
                addToItem = "INSERT INTO " + ITEM_TABLE + "(" + DESC_COLUMN+ ", " + ONHAND_COLUMN + ", " + CATEGORY_COLUMN + ", " + PRICE_COLUMN + ")" + " VALUES ('PS3', 3, 'Console', 99.00)";
                statement.executeUpdate(addToItem);
                addToItem = "INSERT INTO " + ITEM_TABLE + "(" + DESC_COLUMN+ ", " + ONHAND_COLUMN + ", " + CATEGORY_COLUMN + ", " + PRICE_COLUMN + ")" + " VALUES ('Wii', 15, 'Console', 200.00)";
                statement.executeUpdate(addToItem);
                addToItem = "INSERT INTO " + ITEM_TABLE + "(" + DESC_COLUMN+ ", " + ONHAND_COLUMN + ", " + CATEGORY_COLUMN + ", " + PRICE_COLUMN + ")" + " VALUES ('FIFA 16', 30, 'CD Game', 59.00)";
                statement.executeUpdate(addToItem);
                addToItem = "INSERT INTO " + ITEM_TABLE + "(" + DESC_COLUMN+ ", " + ONHAND_COLUMN + ", " + CATEGORY_COLUMN + ", " + PRICE_COLUMN + ")" + " VALUES ('FORZA 5', 10, 'CD Game', 59.00)";
                statement.executeUpdate(addToItem);
                addToItem = "INSERT INTO " + ITEM_TABLE + "(" + DESC_COLUMN+ ", " + ONHAND_COLUMN + ", " + CATEGORY_COLUMN + ", " + PRICE_COLUMN + ")" + " VALUES ('DESTINY', 10, 'CD Game', 59.00)";
                statement.executeUpdate(addToItem);
                addToItem = "INSERT INTO " + ITEM_TABLE + "(" + DESC_COLUMN+ ", " + ONHAND_COLUMN + ", " + CATEGORY_COLUMN + ", " + PRICE_COLUMN + ")" + " VALUES ('GTA V', 5, 'CD Game', 59.00)";
                statement.executeUpdate(addToItem);
                addToItem = "INSERT INTO " + ITEM_TABLE + "(" + DESC_COLUMN+ ", " + ONHAND_COLUMN + ", " + CATEGORY_COLUMN + ", " + PRICE_COLUMN + ")" + " VALUES ('DIVISION', 35, 'CD Game', 59.00)";
                statement.executeUpdate(addToItem);
                addToItem = "INSERT INTO " + ITEM_TABLE + "(" + DESC_COLUMN+ ", " + ONHAND_COLUMN + ", " + CATEGORY_COLUMN + ", " + PRICE_COLUMN + ")" + " VALUES ('RAINBOW SIEGE', 10, 'CD Game', 59.00)";
                statement.executeUpdate(addToItem);
                addToItem = "INSERT INTO " + ITEM_TABLE + "(" + DESC_COLUMN+ ", " + ONHAND_COLUMN + ", " + CATEGORY_COLUMN + ", " + PRICE_COLUMN + ")" + " VALUES ('ZELDA', 3, 'CD Game', 59.00)";
                statement.executeUpdate(addToItem);
                addToItem = "INSERT INTO " + ITEM_TABLE + "(" + DESC_COLUMN+ ", " + ONHAND_COLUMN + ", " + CATEGORY_COLUMN + ", " + PRICE_COLUMN + ")" + " VALUES ('HITMAN', 6, 'CD Game', 59.00)";
                statement.executeUpdate(addToItem);
                addToItem = "INSERT INTO " + ITEM_TABLE + "(" + DESC_COLUMN+ ", " + ONHAND_COLUMN + ", " + CATEGORY_COLUMN + ", " + PRICE_COLUMN + ")" + " VALUES ('HALO', 8, 'CD Game', 59.00)";
                statement.executeUpdate(addToItem);
                addToItem = "INSERT INTO " + ITEM_TABLE + "(" + DESC_COLUMN+ ", " + ONHAND_COLUMN + ", " + CATEGORY_COLUMN + ", " + PRICE_COLUMN + ")" + " VALUES ('MORTAL KOMBAT X', 3, 'CD Game', 59.00)";
                statement.executeUpdate(addToItem);


                //Creating Order table
                String createOrderTable = "CREATE TABLE " + ORDER_TABLE + " (" + OD_PK + " int NOT NULL AUTO_INCREMENT, " + ITEM_PK + " int," + DATE_COLUMN + " DATE , " + NUM_ORDERED_COLUMN + " int, " + CU_PK + " int," + TOTAL_COLUMN + " double, PRIMARY KEY(" + OD_PK + "))";
                System.out.println(createOrderTable);
                statement.executeUpdate(createOrderTable);

            }
            return true;

        } catch (SQLException se) {
            System.out.println(se);
            se.printStackTrace();
            return false;
        }
    }

    private static boolean TableExists() throws SQLException {

        String checkTablePresentQuery = "SHOW TABLES LIKE '" + REP_TABLE_NAME + CUSTOMER_TABLE + ITEM_TABLE + ORDER_TABLE +"'"; //Can query the database schema

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