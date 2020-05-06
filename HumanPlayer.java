
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

import SetupBoard.ButtonPanel;
import SetupBoard.SetupGridPanel;
import SetupBoard.ShipBoxPanel;

public class HumanPlayer extends Player{

    private JPanel setupPanel;

    public JPanel getSetupPanel() {
        return (this.setupPanel);
    }

    private JPanel attackPanel;

    public JPanel getAttackPanel() {
        return (attackPanel);
    }

    private GameManager game;

    public HumanPlayer(String name) {
        super(name);

        setupPanel = new JPanel();
        gridPanel = new SetupGridPanel(30, 30, board, shipArr);

        attackPanel = new JPanel();
        myBoard = new ShowMyBoard(750, 30, board);
        attackBoard = new HumanAttackBoard(30, 30, board);

        setupPanel.setVisible(false);
        attackPanel.setVisible(false);
    }

    // ---SetupBoard Builder---//
    @Override
    public void boardBuilder() {
        setupPanel.setBounds(0, 0, GameManager.GUIWidth, GameManager.GUIHeight);
        setupPanel.setBackground(Color.DARK_GRAY);
        setupPanel.setLayout(null);

        setupPanel.add(buildShipBoxSelector());
        setupPanel.add(buildDirectionButton());

        setupPanel.setVisible(true);
    }

    // ---shipBoxSelector Builder---//
    private JPanel buildShipBoxSelector() {
        JPanel shipBoxPanel = new JPanel();
        JButton[] shipButtonArr = new JButton[5];
        int pnlWidth = 430;
        int pnlHeight = 420;

        shipBoxPanel.setBounds(750, 30, pnlWidth, pnlHeight);
        shipBoxPanel.setBackground(Color.DARK_GRAY);
        shipBoxPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        shipBoxPanel.setLayout(null);

        for (EnumShipButton actShip : EnumShipButton.values()) {
            shipButtonArr[actShip.ordinal()] = new JButton();
            setShipsButton(shipButtonArr[actShip.ordinal()], pnlWidth, pnlHeight, (actShip.ordinal() + 1),
                    actShip.ordinal(), actShip.getShipLen());

            shipBoxPanel.add(shipButtonArr[actShip.ordinal()]);
        }

        return (shipBoxPanel);
    }

    private void setShipsButton(JButton actButton, int pnlWidth, int pnlHeight, int numOfSpaceBetweenElem,
            int numOfShip, int shipLen) {
        int spaceBetweenElem = 38;
        int space = 60;
        int shipWidth = 40;

        actButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((((JButton) e.getSource()).getBackground().equals(Color.LIGHT_GRAY)
                        || ((JButton) e.getSource()).getBackground().equals(Color.BLACK)) && (allShipOff() == true)) {
                    ((JButton) e.getSource()).setBackground(Color.GRAY);
                    shipArr[numOfShip].setFocus(EnumFocusShip.ON);
                    shipArr[5].setFocus(EnumFocusShip.OFF);
                } else if (((JButton) e.getSource()).getBackground().equals(Color.GRAY)) {
                    ((JButton) e.getSource()).setBackground(Color.LIGHT_GRAY);
                    shipArr[numOfShip].setFocus(EnumFocusShip.OFF);
                    shipArr[5].setFocus(EnumFocusShip.ON);
                }
            }
        });
        actButton.setLocation((spaceBetweenElem * numOfSpaceBetweenElem) + (shipWidth * numOfShip),
                (pnlHeight - ((50 * shipLen) + space)));
        actButton.setSize(shipWidth, (50 * shipLen));
        actButton.setBackground(Color.LIGHT_GRAY);
        actButton.setBorderPainted(false);
    }

    // ---DirectionButton Builder---//
    private JPanel buildDirectionButton() {
        JPanel directionButtonPanel = new JPanel();
        JButton[] directionButtonArr = new JButton[4];
        int pnlWidth = 430;
        int pnlHeight = 250;
        int btnWidth = pnlWidth / 3;
        int btnHeight = pnlHeight / 4;

        directionButtonPanel.setBounds(750, 470, pnlWidth, pnlHeight);
        directionButtonPanel.setBackground(Color.DARK_GRAY);
        directionButtonPanel.setLayout(null);

        for (EnumShipDirection directionButton : EnumShipDirection.values()) {
            directionButtonArr[directionButton.ordinal()] = new JButton(directionButton.name());
            if (directionButton.name().equals(EnumShipDirection.RIGHT))
                setDirectionButton(directionButtonArr[directionButton.ordinal()], EnumShipDirection.RIGHT,
                        (pnlWidth - btnWidth), ((pnlHeight / 2) - (btnHeight / 2)), btnWidth, btnHeight);
            else if (directionButton.name().equals(EnumShipDirection.LEFT))
                setDirectionButton(directionButtonArr[directionButton.ordinal()], EnumShipDirection.LEFT, 0,
                        ((pnlHeight / 2) - (btnHeight / 2)), btnWidth, btnHeight);
            else if (directionButton.name().equals(EnumShipDirection.TOP))
                setDirectionButton(directionButtonArr[directionButton.ordinal()], EnumShipDirection.TOP,
                        ((pnlWidth / 2) - (btnWidth / 2)), 0, btnWidth, btnHeight);
            else if (directionButton.name().equals(EnumShipDirection.BOTTOM))
                setDirectionButton(directionButtonArr[directionButton.ordinal()], EnumShipDirection.BOTTOM,
                        ((pnlWidth / 2) - (btnWidth / 2)), (pnlHeight - btnHeight), btnWidth, btnHeight);

            directionButtonPanel.add(directionButtonArr[directionButton.ordinal()]);
        }

        return (directionButtonPanel);
    }

    private void setDirectionButton(JButton actButton, EnumShipDirection direction, int posX, int posY, int btnWidth,
            int btnHeight) {

        actButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (shipIsOn() != shipArr[5])
                    shipIsOn().setDirection(direction);
                else {
                    JFrame noShipSelectedPanel = new JFrame();
                    JLabel warningText = new JLabel("Warning : No ship selected !!", SwingConstants.CENTER);
                    noShipSelectedPanel.setSize(450, 170);
                    noShipSelectedPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    noShipSelectedPanel.getContentPane().setBackground(Color.DARK_GRAY);
                    noShipSelectedPanel.setLocationRelativeTo(null);

                    warningText.setFont(new Font("Arial", Font.BOLD, 20));
                    warningText.setForeground(Color.GRAY);
                    noShipSelectedPanel.add(warningText, BorderLayout.CENTER);

                    noShipSelectedPanel.setVisible(true);
                }
            }
        });
        actButton.setBounds(posX, posY, btnWidth, btnHeight);
        if (shipIsOn() != shipArr[5] && shipIsOn().getDirection().equals(actButton.getName())) {
            actButton.setBackground(Color.LIGHT_GRAY);
            actButton.setForeground(Color.DARK_GRAY);
        } else {
            actButton.setBackground(Color.GRAY);
            actButton.setForeground(Color.WHITE);
        }
        actButton.setBorderPainted(false);
    }

    // ---Grid Builder---//
    private JPanel buildGrid() {
        JPanel gridPanel = new JPanel();
        int pnlSize = 700;

        gridPanel.setBounds(30, 30, pnlSize, pnlSize);
        gridPanel.setBackground(Color.DARK_GRAY);
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        gridPanel.setLayout(null);

        gridPanel.addMouseListener();
        gridPanel.addMouseMotionListener();
    }

    private void setGrid() {

    }

    // ---AttackBoard Builder---//
    @Override
    public void attackEnnemy() {

        attackPanel.setBounds(0, 0, GameManager.GUIWidth, GameManager.GUIHeight);
        attackPanel.setBackground(Color.DARK_GRAY);
        attackPanel.setLayout(null);

        attackPanel.add(myBoard);

        attackBoard.setBoardToAttack(ennemy.board);
        attackBoard.event.updateEnnemyBoard(ennemy.board);
        attackBoard.event.setEnnemy(ennemy);
        attackBoard.event.setActPlayer(this);
        attackPanel.add(attackBoard);

        attackPanel.setVisible(true);
    }

    // ---Help Functions---//
    public boolean allShipOff() {
        for (Ship elem : shipArr) {
            if (elem.getFocus().equals(EnumFocusShip.ON))
                if (elem.getLen() != 0)
                    return (false);
        }
        return (true);
    }

    public Ship shipIsOn() {
        for (Ship elem : shipArr) {
            if (elem.getFocus().equals(EnumFocusShip.ON))
                return (elem);
        }
        return (shipArr[5]);
    }

    public class gridEvent(){

    }
}