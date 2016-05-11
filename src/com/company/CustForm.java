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
public class CustForm extends JFrame implements WindowListener{
    private JTable CustTable;
    private JPanel rootPanel;
    private JTextField CustNamTextField;
    private JTextField StreetTextField;
    private JTextField CityTextField;
    private JTextField StateTextField;
    private JTextField PostalCodeTextField;
    private JTextField BalanceTextField;
    private JButton addCustomerButton;
    private JButton deleteCustomerButton;
    private JButton quitButton;
    private JTextField RepNumTextField;

    CustForm(final CustModel DataTableModel){

        setContentPane(rootPanel);
        pack();
        setTitle(" Rep Table");
        addWindowListener(this);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        //Set up JTable
        CustTable.setGridColor(Color.BLACK);
        CustTable.setModel(DataTableModel);


        //Event handlers for add, delete and quit buttons
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Get Movie title, make sure it's not blank
                String NameData = CustNamTextField.getText();

                if (NameData == null || NameData.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the name of the rep");
                    return;
                }
                String StreetData = StreetTextField.getText();

                if (StreetData == null || StreetData.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the address");
                    return;
                }
                String CityData = CityTextField.getText();

                if (CityData == null || CityData.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the street");
                    return;
                }
                String StateData = StateTextField.getText();

                if (StateData == null || StateData.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the state");
                    return;
                }
                String PostalCodeData = PostalCodeTextField.getText();

                if (PostalCodeData == null || PostalCodeData.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the postal code");
                    return;
                }
                double BalanceData = Double.parseDouble(BalanceTextField.getText());

                if (BalanceData ==0 ) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the Balance");
                    return;
                }
                int RepNum = Integer.parseInt(RepNumTextField.getText());

                if (RepNum == 0 ) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the Rep Num");
                    return;
                }

                System.out.println("Adding " + NameData + " " + StreetData + " " + CityData + " " + StateData + " " + PostalCodeData + " " + BalanceData + " " + RepNum);
                boolean insertedRow = DataTableModel.insertRow(NameData, StreetData, CityData, StateData, PostalCodeData, BalanceData,RepNum);

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

        deleteCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentRow = CustTable.getSelectedRow();

                if (currentRow == -1) {      // -1 means no row is selected. Display error message.
                    JOptionPane.showMessageDialog(rootPane, "Please choose a Rep to delete");
                }
                boolean deleted = DataTableModel.deleteCust(currentRow);
                if (deleted) {
                    StoreData.loadAllRep();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error deleting Customer Record");
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