/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.gui;

import battleship.network.Networker;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author Daniel Ouwehand
 */
public class StartFrame extends JFrame {

    private final JTextField display = new JTextField();

    private final JButton pcPlayer = new JButton("Play against AI");
    private final JButton select = new JButton("Select");
    private final JPanel panel = new JPanel();
    private final JPanel dummy = new JPanel();
    private final JPanel panelSelect = new JPanel();
    private final JPanel panelPc = new JPanel();
    private final JPanel panelDisplay = new JPanel();

    public StartFrame() {
        super("Battleship - Select Play Mode");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 200);
        setLayout(new BorderLayout());
        setResizable(false);
        display.setBackground(Color.white);
        panel.setLayout(new GridLayout(2, 2));
        panelSelect.setLayout(new GridLayout(2, 2));
        panelPc.setLayout(new GridLayout(2, 2));
        panelDisplay.setLayout(new GridLayout(2, 2));
        add(panel, BorderLayout.CENTER);
      
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        pcPlayer.setPreferredSize(new Dimension(50,20));

        panel.add(panelPc);
        panel.add(panelDisplay);
        panel.add(dummy);
        panel.add(panelSelect);        
        
        panelPc.add(pcPlayer);
        panelSelect.add(select);
        panelDisplay.add(display);
        
        panelSelect.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        panelDisplay.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        panelPc.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        
        pcPlayer.addActionListener((ActionEvent e) -> {
            pcPlayerOnButtonPressed();
        });
        
        select.addActionListener((ActionEvent e) -> {
            networkPlayerOnButtonPressed();
        });
 
        setVisible(true);

    }

    public void pcPlayerOnButtonPressed() {
        Networker networkplayer = new Networker("");
        MainFrame frame = new MainFrame(networkplayer);
        frame.setVisible(true);
        this.setVisible(false);

    }

    public void networkPlayerOnButtonPressed() {
        Networker networkplayer = new Networker("");
        MainFrame frame = new MainFrame(networkplayer);
        frame.setVisible(true);
        this.setVisible(false);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new StartFrame());
    }

   

}
