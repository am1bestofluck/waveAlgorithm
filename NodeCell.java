/**
 * NodeCell
 */
public class NodeCell {
    public Integer value;
    public NodeCell L_NC;
    public NodeCell R_NC;
    public NodeCell B_NC;
    public NodeCell U_NC;
    public EColor color;


    public static void main(String[] args) {
        
        NodeCell a = new NodeCell(1);
        a.color = EColor.WHITE;
        System.out.println(a);
        a.color = EColor.BLUE;
        System.out.println(a);
        a.color = EColor.GREEN;
        System.out.println(a);
        a.color = EColor.RED;
        System.out.println(a);
    }
    


    public NodeCell(Integer value){
        this.value = value;
        this.L_NC = null;
        this.R_NC = null;
        this.U_NC = null;
        this.B_NC = null;
        this.color = EColor.WHITE;
    }


    @Override
    public String toString() {
        String flavor = null;
        switch (this.color.name()) {
            case "RED":
                flavor = "\u001B[31m";
                break;
            case "BLUE":
                flavor = "\u001B[34m";
                break;
            case "WHITE":
                flavor = "\u001B[37m";
                break;
            case "GREEN":
                flavor = "\u001B[32m";
                break;
        }
        if (flavor == null){
            throw new IllegalArgumentException("Ты где, заюша, нашел такой цвет?");
        } 
        return flavor +"NC:" + value;
    }
    

    
}