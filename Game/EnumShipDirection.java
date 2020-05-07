import javax.swing.JButton;

public enum EnumShipDirection {
    
    RIGHT(new JButton("RIGHT")),
    LEFT(new JButton("LEFT")),
    TOP(new JButton("TOP")),
    BOTTOM(new JButton("BOTTOM"));

    private JButton btn;
        public JButton getDirectionBtn() {return (this.btn);}

    EnumShipDirection(JButton btn){

        this.btn = btn;
    }
}