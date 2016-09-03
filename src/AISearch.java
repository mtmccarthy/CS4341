import java.util.LinkedList;

/**
 * Created by Matt McCarthy on 9/3/16.
 */
public class AISearch {
    /**
     * The type of the search
     */
    String type;
    /**
     * The starting value for the algorithm
     */
    double startingValue;
    /**
     * The target value for the algorithm to reach
     */
    double targetValue;
    /**
     * The amount of time the algorithm has to complete execution
     */
    double time;
    /**
     * An array of operators that the algorithm has to choose from
     */
    LinkedList<String> operators;

    /**
     *
     * @param type
     * @param startV
     * @param tarV
     * @param time
     * @param ops
     */
    public AISearch(String type, double startV, double tarV, double time, LinkedList<String> ops) {
        this.type = type;
        this.startingValue = startV;
        this.targetValue = tarV;
        this.time = time;
        this.operators = ops;
    }

    public LinkedList<String> execute() throws SearchTypeNotSupportedException{

        if(this.type.equals("greedy")){
            return this.greedySearch();
        }
        else if(this.type.equals("iterative")){
            return this.iterativeSearch();
        }
        else {
            throw new SearchTypeNotSupportedException("Unsupported Search Type. Please make sure the first line in your file has a supported search type.");
        }

    }

    public LinkedList<String> greedySearch(){
        return new LinkedList<String>();
    }
    public LinkedList<String> iterativeSearch(){
        return new LinkedList<String>();
    }
}
