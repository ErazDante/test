package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JButton;

import Control.Control;
import Enums.Enums.EnumFocusShip;
import Enums.Enums.EnumShipDirection;
import Enums.Enums.EnumStatusShip;
import Model.Ship;

public class ShipSelectorEvent implements ActionListener {

    private Control control;

    public ShipSelectorEvent(Control control){

        this.control = control;

    }

    @Override
    public void actionPerformed(ActionEvent event) {

        Ship actShip = control.getShipAccordToButton((JButton)event.getSource());
        JButton actButton = ((JButton) event.getSource());

        if (((actButton.getBackground() == Color.LIGHT_GRAY) || (actButton.getBackground() == Color.BLACK)) && (control.allShipFocusOff() == true))
        {
            if (((JButton) event.getSource()).getBackground().equals(Color.BLACK))
            {
                actShip.setStatus(EnumStatusShip.OUT);
                control.getBoard().removeShip(actShip);
                control.getBoard().removeShipBox(actShip);
            }
            actButton.setBackground(Color.GRAY);
            actShip.setFocus(EnumFocusShip.ON);
            resetDirectionButton();

            EnumShipDirection.RIGHT.getButton().setBackground(Color.LIGHT_GRAY);
            EnumShipDirection.RIGHT.getButton().setForeground(Color.DARK_GRAY);
        } 
        else if (((JButton) event.getSource()).getBackground().equals(Color.GRAY)) 
        {
            ((JButton) event.getSource()).setBackground(Color.LIGHT_GRAY);
            actShip.setFocus(EnumFocusShip.OFF);
            resetDirectionButton();
        }
    }

    private void resetDirectionButton(){

        for (EnumShipDirection direction : EnumShipDirection.values()) 
        {
            direction.getButton().setBackground(Color.GRAY);
            direction.getButton().setForeground(Color.WHITE);
        }

    }
}