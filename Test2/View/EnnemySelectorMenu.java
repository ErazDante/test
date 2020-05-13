package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Enums.Enums.OpponentType;

public class EnnemySelectorMenu extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JButton human;
    private JButton computer;
    private JLabel text;

    private final int buttonWidth = 200;
    private final int buttonHeight = 100;

    private OpponentType ennemy;

    public OpponentType getEnnemyType() {return (this.ennemy);}

    public EnnemySelectorMenu(int GUIWidth, int GUIHeight) 
    {
        this.setBounds(0, 0, GUIWidth, GUIHeight);
        this.setBackground(Color.DARK_GRAY);
        this.setLayout(null);

        human = new JButton("HUMAN");
        setButton(human, 350, 450);

        computer = new JButton("CPU");
        setButton(computer, ((GUIWidth - 350) - buttonWidth), 450);

        text = new JLabel("CHOOSE YOUR ENNEMY", SwingConstants.CENTER);
        text.setBounds(0, 150, GUIWidth, 300);
        text.setFont(new Font("Arial", Font.BOLD, 48));
        text.setForeground(Color.GRAY);

        add(human);
        add(computer);
        add(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "HUMAN")
            ennemy = EnumPlayerType.HUMAN;
        else if (e.getActionCommand() == "CPU")
            ennemy = EnumPlayerType.COMPUTER;

        System.out.println("the ennemy is " + ennemy);
        game.setGameState(EnumGameStates.ENNEMYBUIDING);
    }

    private void setButton(JButton nameButton, int posX, int posY)
    {
        nameButton.setBounds(posX, posY, buttonWidth, buttonHeight);
        nameButton.setBackground(Color.GRAY);
        nameButton.setBorderPainted(false);
        nameButton.setFocusable(false);
        nameButton.addActionListener(this);
    }
}