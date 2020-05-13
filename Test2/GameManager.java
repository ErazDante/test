
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import Control.Control;
import Enums.Enums.GamePhases;
import Enums.Enums.Opponent;
import Model.Player;
import View.GUIManager;

public class GameManager extends JFrame {

    private static final long serialVersionUID = 1L;

    private Timer gameTime;

    private GamePhases actualPhase;
    private Opponent actualPlayer;
    
    Player p1;
    Player p2;

    private Control control;
    private GUIManager GUI;

    private final int GUIWidth = 1200;
    private final int GUIHeight = 800;

    public GameManager() {
        super("BattleShip");

        this.setPreferredSize(new java.awt.Dimension(GUIWidth, GUIHeight));
        this.setMinimumSize(new Dimension(GUIWidth, GUIHeight));
        this.setMaximumSize(new Dimension(GUIWidth, GUIHeight));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        this.actualPhase = GamePhases.ENEMY_CHOICE;
        this.actualPlayer = Opponent.PLAYER1;

        p1 = new Player("Player1");

        control = new Control(p1);

        GUI = new GUIManager(GUIWidth, GUIHeight, control);
        this.add(GUI);

        this.setVisible(true);
    }

    public void gameRunning(){

        gameTime = new Timer(15, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

                if (actualPhase = GamePhases.ENEMY_CHOICE)
                {
                    
                }

                GUI.addShipDirectionPanel();
                GUI.addShipSelectorPanel();

                repaint();
            }
		});

        gameTime.start();
    }
}