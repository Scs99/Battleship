/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.gui;

import battleship.ComputerGame;
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
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class MainFrame extends JFrame implements IGameChanged, ActionListener {

    private static final Color COLOR_WATER = new Color(50, 186, 255);
    private static final Color COLOR_SHIP = new Color(64, 64, 64);
    private static final Color COLOR_WATER_HIT = new Color(214, 127, 255);
    private static final Color COLOR_SHIP_HIT = new Color(255, 0, 0);

    private final JPanel playerField = new JPanel();
    private final JPanel labelpanel = new JPanel();
    private final JPanel opponendField = new JPanel();
    private JLabel labelOppenent = new JLabel("Gegner");
    private JLabel labelMyPlayfield = new JLabel("Mein Spielfeld");
    private final JLabel label = new JLabel("text for placing ships");
    private int board[][] = new int[10][10];
    private JButton playerButton[][] = new JButton[10][10];
    private JButton opponendButton[][] = new JButton[10][10];
    private Game game;
    private ComputerGame computerGame;

    public MainFrame(Networker networkplayer, boolean versusComputer) {

        super("playField");
        game = new Game(networkplayer);
        game.registerGameChanged(this);

        if (versusComputer) {
            Networker computerNetworker = new Networker("ComputerPlayer");
            computerGame = new ComputerGame(computerNetworker);
            computerNetworker.startServer();

            try {
                TimeUnit.MILLISECONDS.sleep(200);
                networkplayer.connect("localhost", computerNetworker.getMyPort());
                TimeUnit.MILLISECONDS.sleep(200);
                computerGame.placeAllShips();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

        }

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLayout(new BorderLayout());
        setResizable(false);
        playerField.setLayout(new GridLayout(10, 10));
        opponendField.setLayout(new GridLayout(10, 10));
        labelpanel.setLayout(new GridLayout(1, 2));

        labelMyPlayfield.setSize(500, 200);
        labelMyPlayfield.setFont(new Font("Arial", Font.BOLD, 36));
        labelOppenent.setLocation(500, 500);
        labelOppenent.setFont(new Font("Arial", Font.BOLD, 36));
        label.setFont(new Font("Arial", Font.BOLD, 20));

        //add(labelMyPlayfield, BorderLayout.WEST);
        add(playerField, BorderLayout.WEST);
        //add(labelOppenent, BorderLayout.EAST);
        add(labelpanel, BorderLayout.NORTH);
        add(opponendField, BorderLayout.EAST);
        add(label, BorderLayout.SOUTH);

        labelpanel.add(labelMyPlayfield);
        labelpanel.add(labelOppenent);

        playerField.setBorder(BorderFactory.createEmptyBorder(60, 20, 80, 0));
        opponendField.setBorder(BorderFactory.createEmptyBorder(60, 0, 80, 20));
        label.setBorder(BorderFactory.createEmptyBorder(0, 80, 120, 0));
        labelpanel.setBorder(BorderFactory.createEmptyBorder(80, 110, 0, 0));
        labelOppenent.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 0));

        for (int y = 0; y < 10; ++y) {
            for (int x = 0; x < 10; ++x) {

                playerButton[y][x] = new JButton();
                opponendButton[y][x] = new JButton();

                playerField.add(playerButton[y][x]);
                opponendField.add(opponendButton[y][x]);

                playerButton[y][x].addActionListener(new ButtonMyPlayfield(y, x));
                opponendButton[y][x].addActionListener(new ButtonOppenent(y, x));

                playerButton[y][x].setPreferredSize(new Dimension(40, 40));
                opponendButton[y][x].setPreferredSize(new Dimension(40, 40));

            }

        }

        game.initialize();
        setVisible(true);

    }

    private void enablePlayerButtons(boolean setEnabled) {
        for (int y = 0; y < 10; ++y) {
            for (int x = 0; x < 10; ++x) {
                playerButton[y][x].setEnabled(setEnabled);
            }
        }
    }

    private void enableOpponentButtons(boolean setEnabled) {
        for (int y = 0; y < 10; ++y) {
            for (int x = 0; x < 10; ++x) {
                opponendButton[y][x].setEnabled(setEnabled);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void onGameChanged(Playfield myPlayfield, Playfield opponentPlayfield, String statusText, boolean isErrorText, GameState gameState) {

        switch (gameState) {
            case IS_PLACING:
                enablePlayerButtons(true);
                enableOpponentButtons(false);
                break;
            case IS_WAITING_FOR_OPPONENT:
                enablePlayerButtons(false);
                enableOpponentButtons(false);
                break;
            case IS_MYTURN:
                enablePlayerButtons(false);
                enableOpponentButtons(true);
                break;
            case IS_NOT_MYTURN:
                enablePlayerButtons(false);
                enableOpponentButtons(false);
                break;
            case IS_OVER:
                enablePlayerButtons(false);
                enableOpponentButtons(false);
                break;
        }

        paintPlayfield(myPlayfield, playerButton);
        paintPlayfield(opponentPlayfield, opponendButton);

        if (isErrorText) {
            label.setForeground(Color.red);
        } else {
            label.setForeground(Color.black);
        }
        label.setText(statusText);
    }

    private void paintPlayfield(Playfield playfield, JButton[][] buttonsToPaint) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Field field = playfield.fields[y * 10 + x];
                switch (field.getState()) {
                    case WATER:
                        buttonsToPaint[y][x].setBackground(COLOR_WATER);
                        break;
                    case WATER_HIT:
                        buttonsToPaint[y][x].setBackground(COLOR_WATER_HIT);
                        break;
                    case SHIP:
                        buttonsToPaint[y][x].setBackground(COLOR_SHIP);
                        break;
                    case SHIP_HIT:
                        buttonsToPaint[y][x].setBackground(COLOR_SHIP_HIT);
                        break;
                    default:
                        break;
                }
            }
        }
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
            game.shootAtOpponent(x, y);
        }

    }

}
