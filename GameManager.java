
import java.awt.*;
import javax.swing.Timer;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameManager extends JFrame {

    private static final long serialVersionUID = 1L;

    private int GUIWidth = 1200;

    public int getGUIWidth() {
        return (this.GUIWidth);
    }

    private int GUIHeight = 800;

    public int getGUIHeight() {
        return (this.GUIHeight);
    }

    private HumanPlayer p1;
    private Player p2;
    private Timer gameTime;

    private EnumGameStates gameState;
        public EnumGameStates getGameState() {return (this.gameState);}
        public void setGameState(EnumGameStates newState) {this.gameState = newState;}

    private EnumWhoIsPlaying actPlayer;
        public EnumWhoIsPlaying getActPLayer() {return (this.actPlayer);}

    public GameManager() {
        super("BattleShip");

        this.gameState = EnumGameStates.MENU;
        this.actPlayer = EnumWhoIsPlaying.PLAYER1;

        this.setPreferredSize(new Dimension(GUIWidth, GUIHeight));
        this.setMinimumSize(new Dimension(GUIWidth, GUIHeight));
        this.setMaximumSize(new Dimension(GUIWidth, GUIHeight));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        this.setVisible(true);
    }

    public void gameRunning()
    {
        JFrame gameContext = this;

        p1 = new HumanPlayer("Player1", this);
        EnnemySelectorMenu menu = new EnnemySelectorMenu(this);

        gameTime = new Timer(15, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                
                /*if (gameState == EnumGameStates.MENU){
                    gameContext.add(menu);
                }*/


                gameContext.add(p1.getSetupPanel());
                p1.boardBuilder();
                p1.shipSelectorColorManager();
                p1.shipDirectionColorManager();

                repaint();
            }
        });

        gameTime.start();
    }
}