import java.security.InvalidParameterException;

/**
 * nodemap
 */
public class NodeMap {
    Integer width;
    Integer height;
    public static void main(String[] args) {
    
    }

    public NodeMap (Integer width, Integer height) throws InvalidParameterException{
        if ((width == null || width <=0) || (height == null || height <=0)){
            throw new InvalidParameterException("positive ints for measurement");
        }
        this.height = height;
        this.width = width;


    }
    private void declareContent(){
        
    }

}