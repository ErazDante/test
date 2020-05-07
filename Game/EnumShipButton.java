import javax.swing.JButton;

public enum EnumShipButton{
    
    SHIP1(5, new JButton()), 
    SHIP2(4, new JButton()), 
    SHIP3(3, new JButton()), 
    SHIP4(3, new JButton()), 
    SHIP5(2, new JButton()),
    WAITING(0);

    private int shipLen;
        public int getShipLen() {return (this.shipLen);}

    private JButton btn;
        public JButton getShipBtn() {return (this.btn);}

    EnumShipButton(int shipLen, JButton btn){
        
        this.shipLen = shipLen;
        this.btn = btn;
    }

    EnumShipButton(int shipLen){
        
        this.shipLen = shipLen;
    }
}