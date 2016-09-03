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
    public LinkedList<String> execute() throws SearchTypeNotSupportedException, OperatorNotSupportedException{
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
    public LinkedList<String> iterativeSearch() throws OperatorNotSupportedException{


        if(startingValue == targetValue) {
            return new LinkedList<String>();
        }

        for(int depth = 0; depth == depth; depth++) {
            LinkedList<String> operatorPath = this.depthLimitedSearch(this.startingValue, depth, new LinkedList<String>());
            if(!operatorPath.isEmpty()) {
                return operatorPath;
            }
        }


        return new LinkedList<String>();
    }

    public LinkedList<String> depthLimitedSearch(Double root, int depth, LinkedList<String> operations) throws OperatorNotSupportedException{

        if(depth == 0 && root == this.targetValue) {
            return operations;
        }
        else if(depth > 0) {
            for(String operator : this.operators) {
                operations.addLast(operator);
                Double child = this.performOperation(root, operator);
                LinkedList<String> nextLevel = this.depthLimitedSearch(child, depth - 1, operations);
                if(nextLevel.size() == operations.size()) {
                    return operations;
                }
            }
        }

        return new LinkedList<String>();
    }

    public Double performOperation(Double root, String op) throws OperatorNotSupportedException{

        //parse the operator
        String[] splitOperator = op.split("\\s+");//Divides operator from operand
        String operator = splitOperator[0];
        Double operand = Double.parseDouble(splitOperator[1]);

        if(operator.equals("+")){
            return root + operand;
        }
        else if(operator.equals("-")){
            return root - operand;
        }
        else if(operator.equals("*")){
            return root * operand;
        }
        else if(operator.equals("/")){
            return root / operand;
        }
        else if(operator.equals("^")){
            return Math.pow(root, operand);
        }
        else {
            throw new OperatorNotSupportedException("Operator not supported. Please make sure all operators are in an acceptable format.");
        }

    }
}
