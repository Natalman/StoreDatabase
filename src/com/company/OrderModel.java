package com.company;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by TheKingNat on 5/8/16.
 */
public class OrderModel extends AbstractTableModel {

    private int rowCountOrder = 0;
    private int colCountOrder = 0;
    ResultSet resultSetOrder;

    public OrderModel(ResultSet rs) {
        this.resultSetOrder = rs;
        setup();
    }

    private void setup(){

        countRows();

        try{
            colCountOrder = resultSetOrder.getMetaData().getColumnCount();

        } catch (SQLException se) {
            System.out.println("Error counting columns" + se);
        }

    }


    public void updateResultSetOrder(ResultSet newRS){
        resultSetOrder = newRS;
        setup();
    }


    private void countRows() {
        rowCountOrder = 0;
        try {
            //moving to the start
            resultSetOrder.beforeFirst();
            //moving to the next row down
            while (resultSetOrder.next()){
                rowCountOrder++;
            }
            resultSetOrder.beforeFirst();
        }catch (SQLException bm){
            System.out.println("Can't count rows" + bm);
        }

    }
    @Override
    public int getRowCount() {
        countRows();
        return rowCountOrder;
    }

    @Override
    public int getColumnCount(){
        return colCountOrder;
    }

    @Override
    public Object getValueAt(int row, int col){
        try{
            resultSetOrder.absolute(row + 1);
            Object q = resultSetOrder.getObject(col + 1);
            return q.toString();
        }catch (SQLException mn){
            mn.printStackTrace();
            return mn.toString();
        }
    }

    //Setting up editable column in the order table
    @Override
    public boolean isCellEditable(int row, int col){
        if (col == 2 || col == 4) {
            return true;
        }
        return false;
    }

    //Delete row, return true if successful, false otherwise
    public boolean deleteOrder(int row){
        try {
            resultSetOrder.absolute(row + 1);
            resultSetOrder.deleteRow();
            //redraw the table
            fireTableDataChanged();
            return true;
        }catch (SQLException ty){
            System.out.println("can't delete " + ty);
            return false;
        }
    }

    //returns true if successful, false if error occurs
    public boolean insertRow(int ItemNum, String Date, int NumOrder, int CustNum, double total) {

        try {
            //Move to insert row, insert the appropriate data in each column, insert the row, move cursor back to where it was before we started
            resultSetOrder.moveToInsertRow();
            resultSetOrder.updateInt(StoreData.ITEM_PK, ItemNum);
            resultSetOrder.updateString(StoreData.DATE_COLUMN, Date);
            resultSetOrder.updateInt(StoreData.NUM_ORDERED_COLUMN, NumOrder);
            resultSetOrder.updateInt(StoreData.CU_PK, CustNum);
            resultSetOrder.updateDouble(StoreData.TOTAL_COLUMN, total);
            resultSetOrder.insertRow();
            resultSetOrder.moveToCurrentRow();
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
            return resultSetOrder.getMetaData().getColumnName(col + 1);
        } catch (SQLException se) {
            System.out.println("Error fetching column names" + se);
            return "?";
        }
    }


}
