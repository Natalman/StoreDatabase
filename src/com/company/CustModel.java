package com.company;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by TheKingNat on 5/8/16.
 */
public class CustModel extends AbstractTableModel {

    private int rowCountCust = 0;
    private int colCountCust = 0;
    ResultSet resultSetCust;

    public CustModel(ResultSet rs) {
        this.resultSetCust = rs;
        setup();
    }

    private void setup(){

        countRows();

        try{
            colCountCust = resultSetCust.getMetaData().getColumnCount();

        } catch (SQLException se) {
            System.out.println("Error counting columns" + se);
        }

    }


    public void updateResultSetCust(ResultSet newRS){
        resultSetCust = newRS;
        setup();
    }


    private void countRows() {
        rowCountCust = 0;
        try {
            //Moving cursor to the beginning
            resultSetCust.beforeFirst();
            //then will be moved one row up
            while (resultSetCust.next()){
                rowCountCust++;
            }
            resultSetCust.beforeFirst();
        }catch (SQLException dr){
            System.out.println("Error counting rows" + dr);
        }

    }
    @Override
    public int getRowCount() {
        countRows();
        return rowCountCust;
    }

    @Override
    public int getColumnCount(){
        return colCountCust;
    }

    @Override
    public Object getValueAt(int row, int col){
        try{
            resultSetCust.absolute(row+1);
            Object n = resultSetCust.getObject(col+1);
            return n.toString();
        }catch (SQLException bu){
            return bu.toString();
        }
    }

    //Setting up editable column in the customer table
    @Override
    public boolean isCellEditable(int row, int col){
        if (col >= 2) {
            return true;
        }
        return false;
    }

    //Delete row, return true if successful, false otherwise
    public boolean deleteCust(int row){
        try {
            resultSetCust.absolute(row +1);
            resultSetCust.deleteRow();
            //send a redraw to the table
            fireTableDataChanged();
            return true;
        }catch (SQLException ma){
            System.out.println("Error deleting row" + ma);
            return false;
        }
    }

    //returns true if successful, false if error occurs
    public boolean insertRow(String CustName, String Street, String City, String State, String PostalCode, double Balance, int RepNum) {

        try {
            //Move to insert row, insert the appropriate data in each column, insert the row, move cursor back to where it was before we started
            resultSetCust.moveToInsertRow();
            resultSetCust.updateString(StoreData.CUSTNAME_COLUMN, CustName);
            resultSetCust.updateString(StoreData.STREET_COLUMN, Street);
            resultSetCust.updateString(StoreData.CITY_COLUMN, City);
            resultSetCust.updateString(StoreData.STATE_COLUMN, State);
            resultSetCust.updateString(StoreData.POSTALCODE_COLUMN, PostalCode);
            resultSetCust.updateDouble(StoreData.BALANCE_COLUMN, Balance);
            resultSetCust.updateInt(StoreData.PK_COLUMN, RepNum);
            resultSetCust.insertRow();
            resultSetCust.moveToCurrentRow();
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
            return resultSetCust.getMetaData().getColumnName(col + 1);
        } catch (SQLException se) {
            System.out.println("Error fetching column names" + se);
            return "?";
        }
    }


}

