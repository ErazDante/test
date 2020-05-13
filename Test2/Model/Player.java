package Model;

import Enums.Enums.EnumFocusShip;
import Enums.Enums.EnumShipButton;
import Enums.Enums.EnumStatusShip;

public class Player {

    protected Ship[] shipArr;
        public Ship[] getShipArr() {return (this.shipArr);}

    protected Board board;
        public Board getBoard() {return (this.board);}

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
        this.playerName = name;

        board = new Board();
        board.initBoard(); 

        shipArr = new Ship[5];
        for (EnumShipButton shipList : EnumShipButton.values())
        {
            shipArr[shipList.ordinal()] = new Ship(shipList.getShipLen(), board);
            shipArr[shipList.ordinal()].setNameShip(shipList);
        }
    }

    public boolean allShipIsIn()
    {
        for (Ship elem : shipArr)
            if (elem.getStatus().equals(EnumStatusShip.OUT))
                return (false);
        return (true);
    }
}