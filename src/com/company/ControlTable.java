package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by TheKingNat on 5/11/16.
 */
public class ControlTable extends JFrame implements WindowListener{
    private JPanel rootPanel;
    private JButton customerTableButton;
    private JButton itemTableButton;
    private JButton repTableButton;
    private JButton orderTableButton;
    private JButton quitButton;

    public ControlTable() {
        setContentPane(rootPanel);
        pack();
        setTitle(" Option Table");
        addWindowListener(this);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        repTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RepForm RepGUI = new RepForm (StoreData.getStoreModel());
            }
        });
        customerTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustForm CustGUI = new CustForm(StoreData.getCustmodel());
            }
        });
        itemTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ItemForm ItemGUI = new ItemForm(StoreData.getItemModel());
            }
        });
        orderTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderForm OrderGUI = new OrderForm(StoreData.getOrderModel());
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StoreData.shutdown();  // Closing the database and the whole application
                System.exit(0);
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
