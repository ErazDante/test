package View;

import java.awt.Color;

import Control.Control;
import Enums.Enums.EnumBoxStates;
import Enums.Enums.EnumShipDirection;
import Model.Board;
import Model.Point;
import Model.Ship;

public class GridBoardBuildPanel extends GridPanel {

    private static final long serialVersionUID = 1L;

    public GridBoardBuildPanel(int posX, int posY, int pnlSize, Control control) {
        super(posX, posY, pnlSize, control);

        event = new GridBoardBuildEvent(control, rctSize);
        this.addMouseListener(event);
        this.addMouseMotionListener(event);
    }

    @Override
    protected Color setColorForBoxGrid(Board board, int row, int col) {

        if (board.getBoard()[row][col] == EnumBoxStates.SHIP)
            return (Color.RED);
        else if (board.getBoard()[row][col] == EnumBoxStates.BOX)
            return (Color.PINK);
        else if (shipDrawInGrid(rctSize, row, col))
            return (Color.RED);
            
        return (Color.GRAY);
    }

    private boolean shipDrawInGrid(int rctSize, int row, int col){
        
        Ship actShip = control.shipIsOn();
        int boxRow = (event.getMouseY() / rctSize);
        int boxCol = (event.getMouseX() / rctSize);
        Point firstLimit = new Point();
        Point secondLimit = new Point();

        if (actShip == null || event.getMouseIn() == false || boxRow == control.getBoard().getRowMax() || boxCol == control.getBoard().getColMax())
            return (false);   
        else if (actShip.getDirection() == EnumShipDirection.RIGHT)
        {
            firstLimit.cpyPoint(control.checkCoordInBounds(actShip, boxRow, boxCol));
            secondLimit.setPoint(boxRow, (boxCol + (actShip.getLen() - 1)) );   
        }
        else if (actShip.getDirection() == EnumShipDirection.LEFT)
        {
            secondLimit.cpyPoint(control.checkCoordInBounds(actShip, boxRow, boxCol));
            firstLimit.setPoint(boxRow, (boxCol - (actShip.getLen() - 1)) );  
        }
        else if (actShip.getDirection() == EnumShipDirection.TOP)
        {
            secondLimit.cpyPoint(control.checkCoordInBounds(actShip, boxRow, boxCol)); 
            firstLimit.setPoint(boxRow - (actShip.getLen() - 1), boxCol);
        }
        else if (actShip.getDirection() == EnumShipDirection.BOTTOM)
        {
            firstLimit.cpyPoint(control.checkCoordInBounds(actShip, boxRow, boxCol));
            secondLimit.setPoint(boxRow + (actShip.getLen() - 1), boxCol); 
        }

        if (row >= firstLimit.getRow() && row <= secondLimit.getRow() && col >= firstLimit.getCol() && col <= secondLimit.getCol())
            return (true);

        return (false);
    }
}