/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.gui;

import battleship.Field;
import battleship.Game;
import battleship.network.Networker;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
public class MainFrame extends JFrame implements ActionListener{

  
    private final JPanel playerField = new JPanel();
    private final JPanel opponendField = new JPanel();
    private final JLabel label = new JLabel("text for placing ships");
    private int board[][] = new int[10][10];
    private JButton playerButton[][] = new JButton[10][10];
    private JButton opponendButton[][] = new JButton[10][10];
    private Game game;

    public MainFrame(Networker networkplayer) {

        super("playField");
        game = new Game(networkplayer);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLayout(new BorderLayout());
        setResizable(false);
        playerField.setLayout(new GridLayout(10, 10));
        opponendField.setLayout(new GridLayout(10, 10));

       
        add(playerField, BorderLayout.WEST);
        add(opponendField, BorderLayout.EAST);
        add(label, BorderLayout.SOUTH);

        playerField.setBorder(BorderFactory.createEmptyBorder(200, 20, 80, 100));
        opponendField.setBorder(BorderFactory.createEmptyBorder(200, 0, 80, 20));
        label.setBorder(BorderFactory.createEmptyBorder(0, 120, 120, 0));


        for (int y = 0; y < 10; ++y) {
            for (int x = 0; x < 10; ++x) {

                playerButton[y][x] = new JButton();
                opponendButton[y][x] = new JButton();

                playerField.add(playerButton[y][x]);
                opponendField.add(opponendButton[y][x]);

                playerButton[y][x].addActionListener(new ButtonPressed(y, x));
                //opponendButton[y][x].addActionListener(new ButtonPressed(y,x));

                playerButton[y][x].setPreferredSize(new Dimension(40, 40));
                opponendButton[y][x].setPreferredSize(new Dimension(40, 40));

            }

        }

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++){
                Field field = game.myPlayfield.fields[y*10+x];
                switch (field.getState()) {
                    case WATER:
                        playerButton[y][x].setBackground(Color.blue);
                        break;
                    case WATER_HIT:
                        playerButton[y][x].setBackground(Color.black);
                        break;
                    case SHIP:
                        playerButton[y][x].setBackground(Color.red);
                        break;
                    default:
                        playerButton[y][x].setBackground(Color.yellow);
                        break;
                }
            }
        }
        
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++){
                Field field = game.opponentPlayfield.fields[y*10+x];
                switch (field.getState()) {
                    case WATER:
                        opponendButton[y][x].setBackground(Color.blue);
                        break;
                    case WATER_HIT:
                        opponendButton[y][x].setBackground(Color.black);
                        break;
                    case SHIP:
                        opponendButton[y][x].setBackground(Color.red);
                        break;
                    default:
                        opponendButton[y][x].setBackground(Color.yellow);
                        break;
                }
            }
        }
    }

  

    private class ButtonPressed implements ActionListener {

        int x;
        int y;

        public ButtonPressed(int row, int column) {
            y = row;
            x = column;
        }

        public void actionPerformed(ActionEvent evt) {
           game.shootAtOpponent(x, y);
        }

    }

}
