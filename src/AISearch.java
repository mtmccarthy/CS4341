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

    /**
     *
     * @return List of operators to go from initial to target goal
     * @throws SearchTypeNotSupportedException
     */
    public LinkedList<String> execute() throws SearchTypeNotSupportedException{
        LinkedList<String> operationList;

        if(this.type.equals("greedy")){
            operationList = this.greedySearch();
        }
        else if(this.type.equals("iterative")){
            operationList = this.iterativeSearch();
        }
        else {
            throw new SearchTypeNotSupportedException("Unsupported Search Type. Please make sure the first line in your file has a supported search type.");
        }

        for(String operator : operationList) {
            System.out.println(operator);
        }

        return operationList;
    }

    /**
     *
     * @return  List of operators to go from initial to target goal using greedy search
     */
    public LinkedList<String> greedySearch(){
        return new LinkedList<String>();
    }

    /**
     *
     * @return  List of operators to go from initial to target goal using iterative search
     */
    public LinkedList<String> iterativeSearch(){
        return new LinkedList<String>();
    }
}
