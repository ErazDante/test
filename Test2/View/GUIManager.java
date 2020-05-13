package View;

import java.awt.Color;

import javax.swing.JPanel;

import Control.Control;

public class GUIManager extends JPanel {

    private static final long serialVersionUID = 1L;

    private ShipDirectionPanel shipDirectionPanel;
    private ShipSelectorPanel shipSelectorPanel;
    private GridPanel gridBuildBoard;

    public GUIManager (int GUIWidth, int GUIHeight, Control control){

        this.setBounds(0, 0, GUIWidth, GUIHeight);
        this.setBackground(Color.DARK_GRAY);
        this.setLayout(null);

        shipDirectionPanel = new ShipDirectionPanel(430, 250, control);
        shipDirectionPanel.buildDirectionButton();

        shipSelectorPanel = new ShipSelectorPanel(430, 420, control);
        shipSelectorPanel.buildSelectorButton();

        gridBuildBoard = new GridBoardBuildPanel(30, 30, 700, control);
        
        this.add(shipSelectorPanel);
        this.add(shipDirectionPanel);
        this.add(gridBuildBoard);
        this.setVisible(true);
    }

    public void addShipDirectionPanel() {
        shipDirectionPanel.setVisible(true);
    }

    public void addShipSelectorPanel() {
        shipSelectorPanel.setVisible(true);
    }
}