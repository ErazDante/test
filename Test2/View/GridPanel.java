package View;

import java.awt.Graphics;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Control.Control;
import Model.Board;

abstract public class GridPanel extends JPanel{
    
    private static final long serialVersionUID = 1L;

    protected Control control;

    protected GridEvent event;

    protected int pnlSize;
    protected int posX, posY;
    protected int rctSize = 69, spaceBtnElm = 4;
    protected int boxRow, boxCol;

    public GridPanel(int posX, int posY, int pnlSize, Control control){
        
        this.control = control;
        
        this.pnlSize = pnlSize;
        this.posX = posX;
        this.posY = posY;

        this.setBounds(posX, posY, pnlSize, pnlSize);
        this.setOpaque(true);
        this.setBackground(Color.DARK_GRAY);
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.setLayout(null);

        this.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g){

        boxRow = (event.getMouseY() / rctSize);
        boxCol = (event.getMouseX() / rctSize);

        super.paintComponent(g);
        for (int row = control.getBoard().getRowMin(); row < control.getBoard().getRowMax(); ++row)
        {
            for (int col = control.getBoard().getColMin(); col < control.getBoard().getColMax(); ++col)
            {
                g.setColor(setColorForBoxGrid(control.getBoard(), row, col));

                if (row == 0 || col == 0)
                    g.fillRect(((col * rctSize) + (spaceBtnElm + 3)), ((row * rctSize) + (spaceBtnElm + 3)), rctSize - spaceBtnElm, rctSize - spaceBtnElm);
                else
                    g.fillRect((((col * rctSize) + 3) + spaceBtnElm), (((row * rctSize) + 3) + spaceBtnElm), rctSize - spaceBtnElm, rctSize - spaceBtnElm);
            }
        }
    }

    abstract protected Color setColorForBoxGrid(Board board, int row, int col);
}