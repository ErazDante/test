
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

public class HumanPlayer extends Player{

    private JPanel setupPanel;
        public JPanel getSetupPanel() {return (this.setupPanel);}
    
    private JButton[] shipButtonArr;
    private JButton[] directionButtonArr;

    private JPanel attackPanel;
        public JPanel getAttackPanel() {return (attackPanel);}

    private GameManager game;

    public HumanPlayer(String name, GameManager game) {
        super(name);

        this.game = game;
        setupPanel = new JPanel();
        System.out.println("setup Panel builded");

        //attackPanel = new JPanel();

        setupPanel.setVisible(false);
        //attackPanel.setVisible(false);
    }

    // ---SetupBoard Builder---//
    @Override
    public void boardBuilder() {

        setupPanel.setBounds(0, 0, game.getGUIWidth(), game.getGUIHeight());
        setupPanel.setBackground(Color.DARK_GRAY);
        setupPanel.setLayout(null);

        setupPanel.add(buildShipBoxSelector());
        setupPanel.add(buildDirectionButton());
        setupPanel.add(buildGrid());

        setupPanel.setVisible(true);
    }

    // ---shipBoxSelector Builder---//
    private JPanel buildShipBoxSelector() {
        JPanel shipBoxPanel = new JPanel();
        shipButtonArr = new JButton[5];
        int pnlWidth = 430;
        int pnlHeight = 420;

        shipBoxPanel.setBounds(750, 30, pnlWidth, pnlHeight);
        shipBoxPanel.setBackground(Color.DARK_GRAY);
        shipBoxPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        shipBoxPanel.setLayout(null);

        for (EnumShipButton actShip : EnumShipButton.values()) {
            shipButtonArr[actShip.ordinal()] = new JButton();
            setShipsButton(shipButtonArr[actShip.ordinal()], pnlWidth, pnlHeight, (actShip.ordinal() + 1), actShip.ordinal(), actShip.getShipLen());

            shipBoxPanel.add(shipButtonArr[actShip.ordinal()]);
        }

        return (shipBoxPanel);
    }

    private void setShipsButton(JButton actButton, int pnlWidth, int pnlHeight, int numOfSpaceBetweenElem, int numOfShip, int shipLen) {
        int spaceBetweenElem = 38;
        int space = 60;
        int shipWidth = 40;

        actButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((((JButton) e.getSource()).getBackground().equals(Color.LIGHT_GRAY) || ((JButton) e.getSource()).getBackground().equals(Color.BLACK)) && (allShipOff() == true)) {
                    if (((JButton) e.getSource()).getBackground().equals(Color.BLACK))
                    {
                        shipArr[numOfShip].setStatus(EnumStatusShip.OUT);
                        board.removeShip(shipArr[numOfShip]);
                        board.removeShipBox(shipArr[numOfShip]);
                    }
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
        actButton.setLocation((spaceBetweenElem * numOfSpaceBetweenElem) + (shipWidth * numOfShip), (pnlHeight - ((50 * shipLen) + space)));
        actButton.setSize(shipWidth, (50 * shipLen));
        actButton.setBorderPainted(false);
    }

    public void shipSelectorColorManager(){

        for (int index = 0; index < 5; ++index){
            if (shipArr[index].getStatus() == EnumStatusShip.IN && shipArr[0].getFocus() == EnumFocusShip.OFF)
                shipButtonArr[index].setBackground(Color.BLACK);
            else if (shipArr[index].getFocus() == EnumFocusShip.OFF)
                shipButtonArr[index].setBackground(Color.LIGHT_GRAY); 
            else if (shipArr[index].getFocus() == EnumFocusShip.ON)
                shipButtonArr[index].setBackground(Color.GRAY);
        }
    }

    // ---DirectionButton Builder---//
    private JPanel buildDirectionButton() {
        JPanel directionButtonPanel = new JPanel();
        directionButtonArr = new JButton[4];
        int pnlWidth = 430;
        int pnlHeight = 250;
        int btnWidth = pnlWidth / 3;
        int btnHeight = pnlHeight / 4;

        directionButtonPanel.setBounds(750, 470, pnlWidth, pnlHeight);
        directionButtonPanel.setBackground(Color.DARK_GRAY);
        directionButtonPanel.setLayout(null);

        for (EnumShipDirection directionButton : EnumShipDirection.values()) {
            directionButtonArr[directionButton.ordinal()] = new JButton(directionButton.getName());
            if (directionButton == EnumShipDirection.RIGHT)
                setDirectionButton(directionButtonArr[directionButton.ordinal()], EnumShipDirection.RIGHT, (pnlWidth - btnWidth), ((pnlHeight / 2) - (btnHeight / 2)), btnWidth, btnHeight);
            else if (directionButton == EnumShipDirection.LEFT)
                setDirectionButton(directionButtonArr[directionButton.ordinal()], EnumShipDirection.LEFT, 0, ((pnlHeight / 2) - (btnHeight / 2)), btnWidth, btnHeight);
            else if (directionButton == EnumShipDirection.TOP)
                setDirectionButton(directionButtonArr[directionButton.ordinal()], EnumShipDirection.TOP, ((pnlWidth / 2) - (btnWidth / 2)), 0, btnWidth, btnHeight);
            else if (directionButton == EnumShipDirection.BOTTOM)
                setDirectionButton(directionButtonArr[directionButton.ordinal()], EnumShipDirection.BOTTOM, ((pnlWidth / 2) - (btnWidth / 2)), (pnlHeight - btnHeight), btnWidth, btnHeight);

            directionButtonPanel.add(directionButtonArr[directionButton.ordinal()]);
        }

        return (directionButtonPanel);
    }

    private void setDirectionButton(JButton actButton, EnumShipDirection direction, int posX, int posY, int btnWidth, int btnHeight) {

        actButton.setBounds(posX, posY, btnWidth, btnHeight);
        actButton.setBorderPainted(false);
        actButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (shipIsOn() != shipArr[5])
                    shipIsOn().setDirection(direction);
                else 
                    warningFrame("Warning : No ship selected !!!", 450, 170);
            }
        });
    }

    public void shipDirectionColorManager(){
        
        Ship actShip = shipIsOn();
        for (int index = 0; index < 4; ++index){
            if (actShip.getLen() != 0 && actShip.getDirection() == EnumShipDirection.values()[index])
            {
                directionButtonArr[index].setBackground(Color.LIGHT_GRAY);
                directionButtonArr[index].setForeground(Color.DARK_GRAY);
            }else{
                directionButtonArr[index].setBackground(Color.GRAY);
                directionButtonArr[index].setForeground(Color.WHITE);
            }
        }
    }

    // ---Grid Builder---//
    private JPanel buildGrid() {

        int pnlSize = 700;
        int rectSize = 69;
        int spaceBtnElem = 4;

        JPanel gridPanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int row = board.getRowMin(); row < board.getRowMax(); ++row){
                    for (int col = board.getColMin(); col < board.getColMax(); ++col){
                        if (board.getBoard()[row][col] == EnumBoxStates.EMPTY)
                            g.setColor(Color.GRAY);
                        else if (board.getBoard()[row][col] == EnumBoxStates.SHIP)
                            g.setColor(Color.RED);
                        else if (board.getBoard()[row][col] == EnumBoxStates.BOX)
                            g.setColor(Color.PINK);
            
                        if (row == 0 || col == 0)
                            g.fillRect((col * rectSize) + (spaceBtnElem + 3), row * rectSize + (spaceBtnElem + 3), rectSize - spaceBtnElem, rectSize - spaceBtnElem);
                        else
                            g.fillRect(((col * rectSize) + 3) + spaceBtnElem, ((row * rectSize) + 3) + spaceBtnElem, rectSize - spaceBtnElem, rectSize - spaceBtnElem);
                    }
                }
            }
        };

        GridEvent gridEvent = new GridEvent(rectSize);
        gridPanel.setBounds(30, 30, pnlSize, pnlSize);
        gridPanel.setBackground(Color.DARK_GRAY);
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        gridPanel.setLayout(null);

        gridPanel.addMouseMotionListener(gridEvent);
        gridPanel.addMouseListener(gridEvent);

        return (gridPanel);
    }


    // ---AttackBoard Builder---//
    @Override
    public void attackEnnemy() {
    }

    //---Help Functions---//
    private boolean allShipOff() {
        for (Ship elem : shipArr) {
            if (elem.getFocus().equals(EnumFocusShip.ON))
                if (elem.getLen() != 0)
                    return (false);
        }
        return (true);
    }

    private Ship shipIsOn() {
        for (Ship elem : shipArr) {
            if (elem.getFocus().equals(EnumFocusShip.ON))
                return (elem);
        }
        return (shipArr[5]);
    }

    private void warningFrame(String text, int width, int height){

        JFrame warningPanel = new JFrame();
        JLabel warningText = new JLabel(text, SwingConstants.CENTER );
        warningPanel.setSize(width, height);
        warningPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        warningPanel.getContentPane().setBackground(Color.DARK_GRAY);
        warningPanel.setLocationRelativeTo(null);
    
        warningText.setFont(new Font("Arial", Font.BOLD, 20));
        warningText.setForeground(Color.GRAY);
        warningPanel.add(warningText, BorderLayout.CENTER);
    
        warningPanel.setVisible(true);
    }


    //---Class GridEvent---//
    private class GridEvent extends MouseInputAdapter{

        private int mouseX,mouseY;
            public int getMouseX() {return (this.mouseX);}
            public int getMouseY() {return (this.mouseY);}
        
        private int rectSize;

        public GridEvent(int rectSize){

            this.rectSize = rectSize;
        }

        @Override
        public void mouseClicked(MouseEvent e){
            Ship actShip = shipIsOn();
            
            super.mouseClicked(e);
            if (actShip.getStatus().equals(EnumStatusShip.IN) && actShip.getLen() != 0)
            {
                board.removeShip(actShip);
                board.removeShipBox(actShip);
            }
            else if (actShip.getLen() == 0)
            {
                warningFrame("Warning : No ship selected !!!", 450, 170);
            }
            else if (((mouseX/rectSize) < board.getColMax()) && ((mouseY/rectSize) < board.getRowMax()))
            {
                actShip.setCoordShip(actShip.checkCoordInBounds(mouseY/rectSize, mouseX/rectSize));
                if (actShip.isAlreadyAShipHere(board))
                {
                    actShip.setDirectionWhenLocked();
                    actShip.setCoordShipWhenLocked();
                    board.placeShip(actShip);
                    board.placeShipBox(actShip);
                    actShip.setStatus(EnumStatusShip.IN);
                    actShip.setFocus(EnumFocusShip.OFF);
                }
            }

            System.out.println((mouseX/rectSize) + " " + (mouseY/rectSize));
            board.printBoard();

            e.consume();
        }

        @Override
        public void mouseMoved(MouseEvent e){
            super.mouseMoved(e);

            mouseX = e.getX();
            mouseY = e.getY();

            e.consume();
        }
    }
}