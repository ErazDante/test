
public enum EnumShipButton{
    
    SHIP1(5), SHIP2(4), SHIP3(3), SHIP4(3), SHIP5(2);

    private int shipLen;
        public int getShipLen() {return (this.shipLen);}

    EnumShipButton(int shipLen){
        
        this.shipLen = shipLen;
    }

}