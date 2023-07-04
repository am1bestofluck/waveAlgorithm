import java.security.InvalidParameterException;

/**
 * nodemap
 */
public class NodeMap {
    Integer width;
    Integer height;
    private boolean wallsAreSet; 
    public static void main(String[] args) {
    
    }

    public NodeMap (Integer width, Integer height) throws InvalidParameterException{
        if ((width == null || width <=0) || (height == null || height <=0)){
            throw new InvalidParameterException("positive ints for measurement");
        }
        this.height = height;
        this.width = width;
        this.wallsAreSet = false;


    }

    public void set_walls(){

    }
    private void contentDeclare()
    {

    }

    private void contentColorize()
    {

    }

    private void contentBind()
    {

    }
}