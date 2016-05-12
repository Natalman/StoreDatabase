package com.company;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by TheKingNat on 5/8/16.
 */
public class ItemModel extends AbstractTableModel{

    private int rowCountItem = 0;
    private int colCountItem = 0;
    ResultSet resultSetItem;

    public ItemModel(ResultSet rs) {
        this.resultSetItem = rs;
        setup();
    }

    private void setup(){

        countRows();

        try{
            colCountItem = resultSetItem.getMetaData().getColumnCount();

        } catch (SQLException se) {
            System.out.println("Error counting columns" + se);
        }

    }


    public void updateResultSetItem(ResultSet newRS){
        resultSetItem = newRS;
        setup();
    }


    private void countRows() {
        rowCountItem = 0;
        try {
            //Moving cursor to the start
            resultSetItem.beforeFirst();
            //then move it forward
            while (resultSetItem.next()){
                rowCountItem++;
            }
            resultSetItem.beforeFirst();

        } catch (SQLException dd){
            System.out.println("error counting rows");
        }

    }
    @Override
    public int getRowCount() {
        countRows();
        return rowCountItem;
    }

    @Override
    public int getColumnCount(){
        return colCountItem;
    }

    @Override
    public Object getValueAt(int row, int col){
        try{
            resultSetItem.absolute(row+1);
            Object n = resultSetItem.getObject(col+1);
            return n.toString();
        }catch (SQLException se){
            System.out.println(se);
            se.printStackTrace();
            return se.toString();
        }
    }

    //Setting up editable column in the item table
    @Override
    public boolean isCellEditable(int row, int col){
        if (col == 2 || col == 4) {
            return true;
        }
        return false;
    }

    //Delete row, return true if successful, false otherwise
    public boolean deleteItem(int row){
        try {
            resultSetItem.absolute(row +1);
            resultSetItem.deleteRow();
            // redraw table
            fireTableDataChanged();
            return true;
        }catch (SQLException nn){
            System.out.println("Delete row error" + nn);
            return false;
        }
    }

    //returns true if successful, false if error occurs
    public boolean insertRow(String Desc, int OnHand, String Category, double price) {

        try {
            //Move to insert row, insert the appropriate data in each column, insert the row, move cursor back to where it was before we started
            resultSetItem.moveToInsertRow();
            resultSetItem.updateString(StoreData.DESC_COLUMN, Desc);
            resultSetItem.updateInt(StoreData.ONHAND_COLUMN, OnHand);
            resultSetItem.updateString(StoreData.CATEGORY_COLUMN, Category);
            resultSetItem.updateDouble(StoreData.PRICE_COLUMN, price);
            resultSetItem.insertRow();
            resultSetItem.moveToCurrentRow();
            fireTableDataChanged();
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding row");
            System.out.println(e);
            return false;
        }

    }

    @Override
    public String getColumnName(int col){

        try {
            return resultSetItem.getMetaData().getColumnName(col + 1);
        } catch (SQLException se) {
            System.out.println("Error fetching column names" + se);
            return "?";
        }
    }


}

