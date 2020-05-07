
public enum EnumShipDirection {
    
    RIGHT("RIGHT"), LEFT("LEFT"), TOP("TOP"), BOTTOM("BOTTOM");

    private String name;
        public String getName() {return (this.name);}

    EnumShipDirection(String name){

        this.name = name;
    }
}