/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.gui;

import battleship.Field;
import battleship.Game;
import battleship.GameState;
import battleship.IGameChanged;
import battleship.Playfield;
import battleship.network.Networker;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author Daniel Ouwehand
 */
public class MainFrame extends JFrame implements IGameChanged, ActionListener{

  
    private final JPanel playerField = new JPanel();
    private final JPanel opponendField = new JPanel();
    private JLabel labelOppenent = new JLabel("Gegner");
    private JLabel labelMyPlayfield = new JLabel("Mein Spielfeld");
    private final JLabel label = new JLabel("text for placing ships");
    private int board[][] = new int[10][10];
    private JButton playerButton[][] = new JButton[10][10];
    private JButton opponendButton[][] = new JButton[10][10];
    private Game game;

    public MainFrame(Networker networkplayer) {

        super("playField");
        game = new Game(networkplayer);
        game.registerGameChanged(this);
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLayout(new BorderLayout());
        setResizable(false);
        playerField.setLayout(new GridLayout(10, 10));
        opponendField.setLayout(new GridLayout(10, 10));
        
        labelMyPlayfield.setSize(500, 200);
        labelMyPlayfield.setFont(new Font("Arial", Font.BOLD, 36));
        labelOppenent.setLocation(500, 500);
        labelOppenent.setFont(new Font("Arial", Font.BOLD, 36));
        

        add(labelMyPlayfield, BorderLayout.WEST);
        add(playerField, BorderLayout.WEST);
        add(labelOppenent, BorderLayout.EAST);
        add(opponendField, BorderLayout.EAST);
        add(label, BorderLayout.SOUTH);

        playerField.setBorder(BorderFactory.createEmptyBorder(200, 20, 80, 0));
        opponendField.setBorder(BorderFactory.createEmptyBorder(200, 0, 80, 20));
        label.setBorder(BorderFactory.createEmptyBorder(0, 120, 120, 0));


        for (int y = 0; y < 10; ++y) {
            for (int x = 0; x < 10; ++x) {

                playerButton[y][x] = new JButton();
                opponendButton[y][x] = new JButton();

                playerField.add(playerButton[y][x]);
                opponendField.add(opponendButton[y][x]);

                playerButton[y][x].addActionListener(new ButtonMyPlayfield(y, x));
                opponendButton[y][x].addActionListener(new ButtonOppenent(y,x));

                playerButton[y][x].setPreferredSize(new Dimension(40, 40));
                opponendButton[y][x].setPreferredSize(new Dimension(40, 40));

            }

        }

        game.initialize();
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        
    }

    @Override
    public void onGameChanged(Playfield myPlayfield, Playfield opponentPlayfield, String statusText, boolean isErrorText, GameState gameState) {
                for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++){
                Field field = myPlayfield.fields[y*10+x];
                switch (field.getState()) {
                    case WATER:
                        playerButton[y][x].setBackground(Color.BLUE);
                        break;
                    case WATER_HIT:
                        playerButton[y][x].setBackground(Color.PINK);
                        break;
                    case SHIP:
                        playerButton[y][x].setBackground(Color.LIGHT_GRAY);
                        break;
                    default:
                        playerButton[y][x].setBackground(Color.RED);
                        break;
                }
            }
        }
        
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++){
                Field field = opponentPlayfield.fields[y*10+x];
                switch (field.getState()) {
                    case WATER:
                        opponendButton[y][x].setBackground(Color.BLUE);
                        break;
                    case WATER_HIT:
                        opponendButton[y][x].setBackground(Color.PINK);
                        break;
                    case SHIP:
                        opponendButton[y][x].setBackground(Color.LIGHT_GRAY);
                        break;
                    default:
                        opponendButton[y][x].setBackground(Color.RED);
                        break;
                }
            }
        }
     
        if(isErrorText){
           
        } else{
            
        }
        label.setText(statusText);
    }
     
  

    private class ButtonMyPlayfield implements ActionListener {

        int x;
        int y;

        public ButtonMyPlayfield(int row, int column) {
            y = row;
            x = column;
        }

        public void actionPerformed(ActionEvent evt) {
           game.placeShip(x, y);
        }

    }
    
    private class ButtonOppenent implements ActionListener {

        int x;
        int y;

        public ButtonOppenent(int row, int column) {
            y = row;
            x = column;
        }

        public void actionPerformed(ActionEvent evt) {
           //game.shootAtOpponent(x, y);
        }

    }

}
