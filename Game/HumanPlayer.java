
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

public class HumanPlayer extends Player{

    private JPanel setupPanel;
        public JPanel getSetupPanel() {return (this.setupPanel);}
    
    private JPanel attackPanel;
        public JPanel getAttackPanel() {return (attackPanel);}

    private final Ship waitingState = shipArr[5];
    
    private int mouseX;
        public int getMouseX() {return (this.mouseX);}
        public void setMouseX(int newMouseX) {this.mouseX = newMouseX;}

    private int mouseY;
        public int getMouseY() {return (this.mouseY);}
        public void setMouseY(int newMouseY) {this.mouseY = newMouseY;}

    private GameManager game;

    public HumanPlayer(String name, GameManager game) {
        super(name);

        this.game = game;
        setupPanel = new JPanel();
        System.out.println("setup Panel builded");

        setupPanel.setVisible(false);
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
        int pnlWidth = 430;
        int pnlHeight = 420;

        JPanel shipBoxPanel = new JPanel();
        shipBoxPanel.setBounds(750, 30, pnlWidth, pnlHeight);
        shipBoxPanel.setBackground(Color.DARK_GRAY);
        shipBoxPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        shipBoxPanel.setLayout(null);
        for (EnumShipButton actShip : EnumShipButton.values()) 
        {
            if (actShip != EnumShipButton.WAITING)
            {
                setShipsButton(actShip, pnlWidth, pnlHeight);
                shipBoxPanel.add(actShip.getShipBtn()); 
            }
        }

        return (shipBoxPanel);
    }

    private void setShipsButton(EnumShipButton ship, int pnlWidth, int pnlHeight) {
        JButton actButton = ship.getShipBtn();
        Ship actShip = shipArr[ship.ordinal()];
        int spaceBtwnElm = 38;
        int space = 60;
        int shipWidth = 40;

        actButton.setLocation((spaceBtwnElm * (ship.ordinal() + 1)) + (shipWidth * ship.ordinal()), (pnlHeight - ((50 * ship.getShipLen()) + space)));
        actButton.setSize(shipWidth, (50 * ship.getShipLen()));
        actButton.setBorderPainted(false);
        actButton.setBackground(Color.LIGHT_GRAY);

        actButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((JButton) e.getSource()).getBackground() != Color.GRAY && (allShipFocusOff() == true))
                {
                    if (((JButton) e.getSource()).getBackground().equals(Color.BLACK))
                    {
                        actShip.setStatus(EnumStatusShip.OUT);
                        board.removeShip(actShip);
                        board.removeShipBox(actShip);
                    }
                    ((JButton) e.getSource()).setBackground(Color.GRAY);
                    actShip.setFocus(EnumFocusShip.ON);
                    waitingState.setFocus(EnumFocusShip.OFF);
                } 
                else if (((JButton) e.getSource()).getBackground().equals(Color.GRAY)) 
                {
                    ((JButton) e.getSource()).setBackground(Color.LIGHT_GRAY);
                    actShip.setFocus(EnumFocusShip.OFF);
                    waitingState.setFocus(EnumFocusShip.ON);
                }
            }
        });
    }

    // ---DirectionButton Builder---//
    private JPanel buildDirectionButton() {
        JPanel directionButtonPanel = new JPanel();
        int pnlWidth = 430;
        int pnlHeight = 250;
        int btnWidth = pnlWidth / 3;
        int btnHeight = pnlHeight / 4;

        directionButtonPanel.setBounds(750, 470, pnlWidth, pnlHeight);
        directionButtonPanel.setBackground(Color.DARK_GRAY);
        directionButtonPanel.setLayout(null);

        for (EnumShipDirection direction : EnumShipDirection.values()) 
        {
            if (direction == EnumShipDirection.RIGHT)
                setDirectionButton(direction, (pnlWidth - btnWidth), ((pnlHeight / 2) - (btnHeight / 2)), btnWidth, btnHeight);
            else if (direction == EnumShipDirection.LEFT)
                setDirectionButton(direction, 0, ((pnlHeight / 2) - (btnHeight / 2)), btnWidth, btnHeight);
            else if (direction == EnumShipDirection.TOP)
                setDirectionButton(direction, ((pnlWidth / 2) - (btnWidth / 2)), 0, btnWidth, btnHeight);
            else if (direction == EnumShipDirection.BOTTOM)
                setDirectionButton(direction, ((pnlWidth / 2) - (btnWidth / 2)), (pnlHeight - btnHeight), btnWidth, btnHeight);

            directionButtonPanel.add(direction.getDirectionBtn());
        }

        return (directionButtonPanel);
    }

    private void setDirectionButton(EnumShipDirection direction, int posX, int posY, int btnWidth, int btnHeight) {

        JButton actButton = direction.getDirectionBtn();

        actButton.setBounds(posX, posY, btnWidth, btnHeight);
        actButton.setBorderPainted(false);
        actButton.setBackground(Color.GRAY);
        actButton.setForeground(Color.WHITE);

        actButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (shipIsOn() != waitingState)
                {
                    shipIsOn().setDirection(direction);
                    for (EnumShipDirection shipDirection : EnumShipDirection.values())
                    {
                        shipDirection.getDirectionBtn().setBackground(Color.GRAY);
                        shipDirection.getDirectionBtn().setForeground(Color.WHITE);
                    }
                    actButton.setBackground(Color.LIGHT_GRAY);
                    actButton.setForeground(Color.DARK_GRAY);
                }
                else 
                    warningFrame("Warning : No ship selected !!!", 450, 170);
            }
        });
    }
    

    // ---Grid Builder---//
    private JPanel buildGrid() {

        int pnlSize = 700;
        JPanel gridPanel = new JPanel() {
            private static final long serialVersionUID = 1L;
           
            @Override
            public void paintComponent(Graphics g) {
                
                int rctSize = 69;
                int spaceBtnElm = 4;
                int boxRow = (mouseY / rctSize);
                int boxCol = (mouseX / rctSize);

                super.paintComponent(g);
                for (int row = board.getRowMin(); row < board.getRowMax(); ++row)
                {
                    for (int col = board.getColMin(); col < board.getColMax(); ++col)
                    {
                        g.setColor(setColorForBoxGrid(row, col));
                        if ((boxCol >= board.getColMax()) || boxRow >= board.getRowMax())
                            if (shipDrawInGrid(rctSize, row, col))
                                g.setColor(Color.RED);
                        
                        if (row == 0 || col == 0)
                            g.fillRect(((col * rctSize) + (spaceBtnElm + 3)), ((row * rctSize) + (spaceBtnElm + 3)), rctSize - spaceBtnElm, rctSize - spaceBtnElm);
                        else
                            g.fillRect((((col * rctSize) + 3) + spaceBtnElm), (((row * rctSize) + 3) + spaceBtnElm), rctSize - spaceBtnElm, rctSize - spaceBtnElm);
                    }
                }
            }
        };

        gridPanel.setBounds(30, 30, pnlSize, pnlSize);
        gridPanel.setBackground(Color.DARK_GRAY);
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        gridPanel.setLayout(null);

        gridMouseEvent(gridPanel);

        return (gridPanel);
    }

    private void gridMouseEvent(JPanel context){

        context.addMouseMotionListener(new MouseInputAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);

                setMouseX(e.getX());
                setMouseY(e.getY());

                System.out.println(mouseY + " " + mouseX);
            }
        });
        context.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);


            }
        });
    }

        //---Coloring grid---//
    private Color setColorForBoxGrid(int row , int col){

        if (board.getBoard()[row][col] == EnumBoxStates.SHIP)
            return (Color.RED);
        else if (board.getBoard()[row][col] == EnumBoxStates.BOX)
            return (Color.PINK);
        return (Color.GRAY);
    }

    private boolean shipDrawInGrid(int rctSize, int row, int col){
        
        Ship actShip = shipIsOn();
        int boxRow = (mouseY / rctSize);
        int boxCol = (mouseX / rctSize);
        Point firstLimit = new Point();
        Point secondLimit = new Point();

        firstLimit.setPoint(boxRow , boxCol);

        if (actShip.getDirection() == EnumShipDirection.RIGHT){
            secondLimit.setPoint(boxRow, (boxCol + (actShip.getLen() - 1)));
        }
        else if (actShip.getDirection() == EnumShipDirection.LEFT){

        }
        else if (actShip.getDirection() == EnumShipDirection.TOP){

        }
        else if (actShip.getDirection() == EnumShipDirection.BOTTOM){

        }

        if (row >= firstLimit.getRow() && row <= secondLimit.getRow() && col >= firstLimit.getCol() && col <= secondLimit.getCol())
            return (true);

        return (false);
    }

    //---Help Functions---//
    private boolean allShipFocusOff() {
        for (Ship elem : shipArr) 
        {
            if (elem.getFocus().equals(EnumFocusShip.ON) && elem.getNameShip() != EnumShipButton.WAITING)
                return (false);
        }
        return (true);
    }

    private Ship shipIsOn() {
        for (Ship elem : shipArr) 
        {
            if (elem.getFocus().equals(EnumFocusShip.ON))
                return (elem);
        }
        return (waitingState);
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

    @Override
    public void attackEnnemy() {
        // TODO Auto-generated method stub

    }
}