package Control;

import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Enums.Enums.EnumBoxStates;
import Enums.Enums.EnumFocusShip;
import Enums.Enums.EnumShipButton;
import Enums.Enums.EnumShipDirection;
import Model.Board;
import Model.Player;
import Model.Point;
import Model.Ship;

public class Control {
    
    private Ship[] shipArr;
        public Ship[] getShipArr() {return (this.shipArr);}
        public void setShipArr(Ship[] newShipArr) {this.shipArr = newShipArr;}
    
    private Board board;
        public Board getBoard() {return (this.board);}
        public void setBoard(Board newBoard) {this.board = newBoard;}

    private String source;
        public void getSource(){System.out.println("controle source : " + this.source);}

    public Control(Player player){

        setControl(player);
    }
    
    public void setControl(Player player){

        setShipArr(player.getShipArr());
        setBoard(player.getBoard());
        source = player.getPlayerName();
        
    }

    //---Status of ShipArr---//
    public boolean allShipFocusOff() {

        for (Ship elem : shipArr) 
        {
            if (elem.getFocus().equals(EnumFocusShip.ON))
                return (false);
        }
        return (true);
    }

    public Ship shipIsOn() {
        for (Ship elem : shipArr) {
            if (elem.getFocus().equals(EnumFocusShip.ON))
                return (elem);
        }
        return (null);
    }

    //---Convertor Button to Ship---//
    public Ship getShipAccordToButton(JButton btn) {

        EnumShipButton selectedShip = getValueOfShipButton(btn);
        for (int index = 0; index < 5; ++index)
            if (shipArr[index].getNameShip() == selectedShip)
                return (shipArr[index]);
        
        return (null);
    }

    public EnumShipButton getValueOfShipButton(JButton btn){
        
        for(EnumShipButton actBtn : EnumShipButton.values())
            if (actBtn.getButton() == btn) 
                return (actBtn);
        
        return (null);
    }

    //---Convertor Ship to Button---//
    public JButton getButtonAccordToShip(Ship ship) {

        for (EnumShipButton actShip : EnumShipButton.values())
            if (actShip == ship.getNameShip())
                return (actShip.getButton());
        
        return (null);
    }

    //---Convertor Button to Direction---//
    public EnumShipDirection getValueOfDirectionButton(JButton btn){
        
        for(EnumShipDirection actBtn : EnumShipDirection.values())
            if (actBtn.getButton() == btn) 
                return (actBtn);
        
        return (null);
    }

    // ----Check validity placement----// 
    //Go from the Point Up/Left to tho Point Down/Right (without exceeding the limits of the box) of the shipBoxArea checked
    public boolean isAlreadyAShipHere(Board bd, Ship ship) 
    {
        ship.findBoxArea(ship.getDirection(), ship.getShipCoord());

        for (int iRow = ship.getShipBox()[0].getRow(); iRow <= ship.getShipBox()[1].getRow(); ++iRow)
            for (int iCol = ship.getShipBox()[0].getCol(); iCol <= ship.getShipBox()[1].getCol(); ++iCol)
                if (bd.getBoard()[iRow][iCol] != EnumBoxStates.EMPTY)
                    return (true);
        return (false);
    }

    //Check if the coordonate with the direction don't make the Ship go out of Board bounds, if yes assign a new value (the closest 
    // from the original one) to the coordinate Point
    public Point checkCoordInBounds(Ship actShip, int y, int x)
    {
        Point checked = new Point();

        if (x < actShip.getLen() && actShip.getDirection() == EnumShipDirection.LEFT)
            setNewCoordValues(checked, y, (actShip.getLen() - 1));
        else if (x > (board.getColMax() - (actShip.getLen() + 1)) && actShip.getDirection() == EnumShipDirection.RIGHT)
            setNewCoordValues(checked, y, (board.getColMax() - (actShip.getLen())));
        else if (y > (board.getRowMax() - (actShip.getLen() + 1)) && actShip.getDirection() == EnumShipDirection.BOTTOM)
            setNewCoordValues(checked, (board.getRowMax() - (actShip.getLen())), x);
        else if (y < actShip.getLen() && actShip.getDirection() == EnumShipDirection.TOP)
            setNewCoordValues(checked, (actShip.getLen() - 1), x);
        else 
            setNewCoordValues(checked, y, x);
            
        return (checked);
    }

    //Change coordonate to stay in the Board bounds
    private void setNewCoordValues(Point newPoint, int valueForRow, int valueForCol)
    {
        newPoint.setRow(valueForRow);
        newPoint.setCol(valueForCol);
    }

    public void warningFrame(String text, int width, int height) {

        JFrame warningPanel = new JFrame();
        JLabel warningText = new JLabel(text, SwingConstants.CENTER);
        warningPanel.setSize(width, height);
        warningPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        warningPanel.getContentPane().setBackground(Color.DARK_GRAY);
        warningPanel.setLocationRelativeTo(null);

        warningText.setFont(new Font("Arial", Font.BOLD, 20));
        warningText.setForeground(Color.GRAY);
        warningPanel.add(warningText, BorderLayout.CENTER);
    
        warningPanel.setVisible(true);
    }
}