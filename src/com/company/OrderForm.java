package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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
        setTitle(" Rep Table");
        addWindowListener(this);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        //Set up JTable
        OrderTable.setGridColor(Color.BLACK);
        OrderTable.setModel(OrderDataTableModel);


        //Event handlers for add, delete and quit buttons
        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Get Movie title, make sure it's not blank
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
                String DateData = DateTextField.getText();

                if (DateData == null || DateData.trim().equals("")) {
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
                    JOptionPane.showMessageDialog(rootPane, "Error adding new movie");
                }
                // If insertedRow is true and the data was added, it should show up in the table, so no need for confirmation message.
            }

        });


        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StoreData.shutdown();
                System.exit(0);   //Should probably be a call back to Main class so all the System.exit(0) calls are in one place.
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
                    StoreData.loadAllRep();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error deleting Rep");
                }
            }
        });
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
