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
public class ItemForm extends JFrame implements WindowListener{
    private JPanel rootPanel;
    private JTable ItemTable;
    private JLabel Price;
    private JTextField DescTextField;
    private JTextField OnHandTextField;
    private JTextField CategoryTextField;
    private JTextField PriceTextField;
    private JButton addItemButton;
    private JButton deleteItemButton;
    private JButton quitButton;

    ItemForm(final ItemModel ItemDataTableModel){

        setContentPane(rootPanel);
        pack();
        setTitle(" Item Table");
        addWindowListener(this);
        setVisible(true);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        //Set up JTable
        ItemTable.setGridColor(Color.BLACK);
        ItemTable.setModel(ItemDataTableModel);


        //Event handlers for add, delete and quit buttons
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Get Movie title, make sure it's not blank
                String DescData = DescTextField.getText();

                if (DescData == null || DescData.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the name of the item");
                    return;
                }
                String CategoryData = CategoryTextField.getText();

                if (CategoryData == null || CategoryData.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the Category");
                    return;
                }
                double PriceData = Double.parseDouble(PriceTextField.getText());

                if (PriceData ==0 ) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the Price");
                    return;
                }
                int OnHandData = Integer.parseInt(OnHandTextField.getText());

                if (OnHandData ==0 ) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter How many we have");
                    return;
                }

                System.out.println("Adding " + DescData + " " + CategoryData + " " + PriceData + " " + OnHandData);
                boolean insertedRow = ItemDataTableModel.insertRow(DescData, OnHandData, CategoryData, PriceData);

                if (!insertedRow) {
                    JOptionPane.showMessageDialog(rootPane, "Error adding new item");
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

        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentRow = ItemTable.getSelectedRow();

                if (currentRow == -1) {      // -1 means no row is selected. Display error message.
                    JOptionPane.showMessageDialog(rootPane, "Please choose a Rep to delete");
                }
                boolean deleted = ItemDataTableModel.deleteItem(currentRow);
                if (deleted) {
                    StoreData.loadItem();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error deleting movie");
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
