package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Date;

/**
 * Created by TheKingNat on 5/10/16.
 */
public class OrderForm extends JFrame implements WindowListener{
    private JPanel rootPanel;
    private JTable OrderTable;
    private JTextField ItemNumberTextField;
    private JTextField DateTextField;
    private JTextField NumberOrderTextField;
    private JTextField CustomerNumberTextField;
    private JTextField TotaltextField;
    private JButton addOrderButton;
    private JButton deleteButton;
    private JButton quitButton;

    OrderForm(final OrderModel OrderDataTableModel){

        setContentPane(rootPanel);
        pack();
        setTitle(" Order Table");
        addWindowListener(this);
        setVisible(true);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        //Set up JTable
        OrderTable.setGridColor(Color.BLACK);
        OrderTable.setModel(OrderDataTableModel);


        //Event handlers for add, delete and quit buttons
        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Get columns , make sure they are not blank
                int CustNum = Integer.parseInt(CustomerNumberTextField.getText());

                if (CustNum == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the Customer Number");
                    return;
                }
                int ItemNum = Integer.parseInt(ItemNumberTextField.getText());

                if (ItemNum == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the Item Number");
                    return;
                }
                SpinnerDateModel OrdDate = new SpinnerDateModel();
                Date DateData = Date.valueOf(DateTextField.getText());

                if (DateData == null) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the street");
                    return;
                }
                int NumOrderData = Integer.parseInt(NumberOrderTextField.getText());

                if (NumOrderData == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the number ordered");
                    return;
                }
                double TotalData = Double.parseDouble(TotaltextField.getText());

                if (TotalData ==0 ) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter Total");
                    return;
                }

                System.out.println("Adding " + ItemNum + " " + DateData + " " + NumOrderData + " " + CustNum + " " + TotalData);
                boolean insertedRow = OrderDataTableModel.insertRow(ItemNum, DateData, NumOrderData, CustNum, TotalData);

                if (!insertedRow) {
                    JOptionPane.showMessageDialog(rootPane, "Error adding new Order");
                }
                // If insertedRow is true and the data was added, it should show up in the table, so no need for confirmation message.
            }

        });


        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StoreData.shutdown();
                //closeWindow();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentRow = OrderTable.getSelectedRow();

                if (currentRow == -1) {      // -1 means no row is selected. Display error message.
                    JOptionPane.showMessageDialog(rootPane, "Please choose a Rep to delete");
                }
                boolean deleted = OrderDataTableModel.deleteOrder(currentRow);
                if (deleted) {
                    StoreData.loadOrder();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error deleting Rep");
                }
            }
        });
    }
    //This is use so that when we close the window it does not close the whole application but only this window
    public void closeWindow() {
        //this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    //windowListener methods. Only need one of them, but are required to implement the others anyway
    //WindowClosing will call DB shutdown code, which is important, so the DB is in a consistent state however the application is closed.

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("closing");
        StoreData.shutdown();}

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}

}
