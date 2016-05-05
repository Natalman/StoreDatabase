package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;

/**
 * Created by TheKingNat on 5/4/16.
 */
public class RepForm extends JFrame implements WindowListener{
    private JTable Reptable;
    private JTextField NameTextField;
    private JTextField StreetTextField;
    private JTextField CityTextField;
    private JTextField StateTextField;
    private JTextField PostalCodeTextField;
    private JTextField CommissionTextField;
    private JTextField RateTextField;
    private JButton addNewRepButton;
    private JButton deleteRepButton;
    private JButton quitButton;
    private JPanel rootPanel;

    RepForm(final StoreModel DataTableModel){

        setContentPane(rootPanel);
        pack();
        setTitle(" Rep Table");
        addWindowListener(this);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        //Set up JTable
        Reptable.setGridColor(Color.BLACK);
        Reptable.setModel(DataTableModel);


        //Event handlers for add, delete and quit buttons
        addNewRepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Get Movie title, make sure it's not blank
                String NameData = NameTextField.getText();

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
                double CommissionData = Double.parseDouble(CommissionTextField.getText());

                if (CommissionData ==0 ) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the commission");
                    return;
                }
                double RateData = Double.parseDouble(RateTextField.getText());

                if (RateData ==0 ) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the Rate");
                    return;
                }

                System.out.println("Adding " + NameData + " " + StreetData + " " + CityData + " " + StateData + " " + PostalCodeData + " " + CommissionData + " " + RateData);
                boolean insertedRow = DataTableModel.insertRow(NameData, StreetData, CityData, StateData, PostalCodeData, CommissionData, RateData);

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

        deleteRepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentRow = Reptable.getSelectedRow();

                if (currentRow == -1) {      // -1 means no row is selected. Display error message.
                    JOptionPane.showMessageDialog(rootPane, "Please choose a Rep to delete");
                }
                boolean deleted = DataTableModel.deleteRow(currentRow);
                if (deleted) {
                    StoreData.loadAllRep();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error deleting movie");
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

