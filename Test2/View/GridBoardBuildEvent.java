package View;

import java.awt.Color;
import java.awt.event.MouseEvent;

import Control.Control;
import Enums.Enums.EnumFocusShip;
import Enums.Enums.EnumStatusShip;
import Model.Board;
import Model.Ship;

public class GridBoardBuildEvent extends GridEvent{

    public GridBoardBuildEvent(Control control, int rctSize){

		super(control, rctSize);

    }
    
	@Override
	public void mouseClicked(MouseEvent event) {
		
		Ship actShip = control.shipIsOn();
		Board actBoard = control.getBoard();
		int boxRow = mouseY / rctSize;
		int boxCol = mouseX / rctSize;
		
		if (control.shipIsOn() == null)
		{
			control.warningFrame("WARNING : NO SHIP SELECTED!!!", 450, 170);
		}
		else 
		{
			if (actShip.getStatus() == EnumStatusShip.IN)
			{
				actBoard.removeShip(actShip);
				actBoard.removeShipBox(actShip);
			}
			
			if (boxRow < actBoard.getRowMax() && boxCol < actBoard.getColMax())
			{
				actShip.setCoordShip(control.checkCoordInBounds(actShip, boxRow, boxCol));

				actShip.getShipCoord().printPoint();
				if (control.isAlreadyAShipHere(actBoard, actShip) == false)
				{
					actShip.setDirectionWhenLocked();
					actShip.setCoordShipWhenLocked();
					actBoard.placeShip(actShip);
					actBoard.placeShipBox(actShip);
					actShip.setStatus(EnumStatusShip.IN);

					control.getButtonAccordToShip(actShip).setBackground(Color.BLACK);
					actShip.setFocus(EnumFocusShip.OFF);
				}
			}
		}

		actBoard.printBoard();
	}
}