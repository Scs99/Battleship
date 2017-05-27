package battleship.gui;

import battleship.ComputerGame;
import battleship.Field;
import battleship.Game;
import battleship.GameState;
import battleship.IGameChanged;
import battleship.Playfield;
import battleship.StatusMessage;
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
import java.util.Random;

/**
 *
 * @author Daniel Ouwehand
 */
public class MainFrame extends JFrame implements IGameChanged {

    private static final Color COLOR_WATER = new Color(50, 186, 255);
    private static final int COLOR_WATER_VARIANCE = 20;
    private static final Color COLOR_SHIP = new Color(64, 64, 64);
    private static final Color COLOR_WATER_HIT = new Color(214, 127, 255);
    private static final Color COLOR_SHIP_HIT = new Color(255, 0, 0);

    private final JPanel playerField = new JPanel();
    private final JPanel opponentField = new JPanel();
    private final JPanel labelpanel = new JPanel();
    private JLabel labelOpponentTitle = new JLabel("Gegner");
    private JLabel labelMyPlayfieldTitle = new JLabel("Mein Spielfeld");
    private final JLabel labelStatusText = new JLabel();
    private JButton[][] playerButtons = new JButton[10][10];
    private JButton[][] opponendButtons = new JButton[10][10];

    private Game game;
    private ComputerGame computerGame;
    private final Random rand;

    public MainFrame(Networker networkplayer, boolean versusComputer) {

        super("Battleship - Lernteam 6");
        rand = new Random();
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
        opponentField.setLayout(new GridLayout(10, 10));
        labelpanel.setLayout(new GridLayout(1, 2));

        labelMyPlayfieldTitle.setSize(500, 200);
        labelMyPlayfieldTitle.setFont(new Font("Arial", Font.BOLD, 36));
        labelOpponentTitle.setLocation(500, 500);
        labelOpponentTitle.setFont(new Font("Arial", Font.BOLD, 36));
        labelStatusText.setFont(new Font("Arial", Font.BOLD, 20));

        add(playerField, BorderLayout.WEST);
        add(labelpanel, BorderLayout.NORTH);
        add(opponentField, BorderLayout.EAST);
        add(labelStatusText, BorderLayout.SOUTH);

        labelpanel.add(labelMyPlayfieldTitle);
        labelpanel.add(labelOpponentTitle);

        playerField.setBorder(BorderFactory.createEmptyBorder(60, 20, 80, 0));
        opponentField.setBorder(BorderFactory.createEmptyBorder(60, 0, 80, 20));
        labelStatusText.setBorder(BorderFactory.createEmptyBorder(0, 80, 120, 0));
        labelpanel.setBorder(BorderFactory.createEmptyBorder(80, 110, 0, 0));
        labelOpponentTitle.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 0));

        for (int y = 0; y < 10; ++y) {
            for (int x = 0; x < 10; ++x) {

                playerButtons[y][x] = new JButton();
                opponendButtons[y][x] = new JButton();

                playerField.add(playerButtons[y][x]);
                opponentField.add(opponendButtons[y][x]);

                playerButtons[y][x].addActionListener(new ButtonMyPlayfield(y, x));
                opponendButtons[y][x].addActionListener(new ButtonOppenent(y, x));

                playerButtons[y][x].setPreferredSize(new Dimension(40, 40));
                opponendButtons[y][x].setPreferredSize(new Dimension(40, 40));

            }
        }

        paintPlayfieldInitial(playerButtons);
        paintPlayfieldInitial(opponendButtons);

        game.initialize();
        setVisible(true);
    }

    private void enablePlayerButtons(boolean setEnabled) {
        for (int y = 0; y < 10; ++y) {
            for (int x = 0; x < 10; ++x) {
                playerButtons[y][x].setEnabled(setEnabled);
            }
        }
    }

    private void enableOpponentButtons(boolean setEnabled) {
        for (int y = 0; y < 10; ++y) {
            for (int x = 0; x < 10; ++x) {
                opponendButtons[y][x].setEnabled(setEnabled);
            }
        }
    }

    @Override
    public void onGameChanged(Playfield myPlayfield, Playfield opponentPlayfield, StatusMessage message, boolean isErrorText, GameState gameState) {

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

        repaintPlayfield(myPlayfield, playerButtons);
        repaintPlayfield(opponentPlayfield, opponendButtons);

        switch (message.type) {
            case INFO:
                labelStatusText.setForeground(Color.BLACK);
                break;
            case ERROR:
                labelStatusText.setForeground(Color.RED);
                break;
        }

        labelStatusText.setText(message.message);
    }

    private void paintPlayfieldInitial(JButton[][] buttonsToPaint) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Color waterColor = new Color(COLOR_WATER.getRed(), (COLOR_WATER.getGreen() - COLOR_WATER_VARIANCE) + rand.nextInt(COLOR_WATER_VARIANCE), COLOR_WATER.getBlue());
                buttonsToPaint[y][x].setBackground(waterColor);
            }
        }
    }

    private void repaintPlayfield(Playfield playfield, JButton[][] buttonsToPaint) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Field field = playfield.fields[y * 10 + x];
                switch (field.getState()) {
                    case WATER:
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
