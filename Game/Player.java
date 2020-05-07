
abstract public class Player {

    protected Ship[] shipArr;
    protected Board board;

    protected Player ennemy;
        public void setEnnemy(Player newEnnemy) {this.ennemy = newEnnemy;}

    protected int HP = 17;
        public int getHp() {return(this.HP);}
        public void setHP(int newHP) {this.HP = newHP;}

    protected String playerName;
        public String getPlayerName() {return(this.playerName);}
        public void setPlayerName(String newName) {this.playerName = newName;}

    /*protected IsPlaying player;
        public IsPlaying getPlayer() {return (player);}

    protected EnnemyType playerType;
        public EnnemyType getPlayerType() {return (playerType);}*/

    public Player(String name)
    {
        board = new Board();
        board.initBoard(); 

        shipArr = new Ship[6];
        for (EnumShipButton shipList : EnumShipButton.values())
        {
            shipArr[shipList.ordinal()] = new Ship(shipList.getShipLen(), board);
            shipArr[shipList.ordinal()].setNameShip(shipList);
        }
        shipArr[5].setFocus(EnumFocusShip.ON);
        shipArr[5].setStatus(EnumStatusShip.IN);
    }

    public boolean allShipIsIn()
    {
        for (Ship elem : shipArr)
            if (elem.getStatus().equals(EnumStatusShip.OUT))
                return (false);
        return (true);
    }

    abstract public void boardBuilder();
    abstract public void attackEnnemy();
    
}