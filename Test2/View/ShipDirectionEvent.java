package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Control.Control;
import Enums.Enums.EnumShipDirection;
import Model.Ship;

import java.awt.Color;

public class ShipDirectionEvent implements ActionListener {

    private Control control;

    public ShipDirectionEvent(Control control) {

        this.control = control;

    }

    @Override
    public void actionPerformed(ActionEvent event) {

        Ship actualShip = control.shipIsOn();

        if (control.allShipFocusOff() == false)
        {
            actualShip.setDirection(control.getValueOfDirectionButton((JButton)event.getSource()));
            for (EnumShipDirection direction : EnumShipDirection.values()) 
            {
                direction.getButton().setBackground(Color.GRAY);
                direction.getButton().setForeground(Color.WHITE);
            }
            ((JButton) event.getSource()).setBackground(Color.LIGHT_GRAY);
            ((JButton) event.getSource()).setForeground(Color.DARK_GRAY);
        }
        else 
        {
            control.warningFrame("WARNING : NO SHIP SELECTED!!!", 450, 170);
        }
    }
}