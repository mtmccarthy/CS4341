import java.util.Calendar;
import java.util.LinkedList;

import exception.OperatorNotSupportedException;
import exception.SearchTypeNotSupportedException;

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
     * The path that it used by the search (used for post-search report)
     */
    
	public LinkedList<String> path;
	private SearchTask searchTask;
	/**
	 * The amount of nodes expanded (needed for post-search report)
	 */
	private static int nodesExpanded=0;

    /**
     *
     * @param type The type of search used Iterative, or Greedy.
     * @param startV Starting value of the search
     * @param tarV The target value of the search
     * @param ops The operations that the search is allowed to use.
     */
    public AISearch(String type, double startV, double tarV, LinkedList<String> ops) {
        this.type = type;
        this.startingValue = startV;
        this.targetValue = tarV;
        this.operators = ops;

               
        
    }
    

    /**
     *
     * @return List of operators to go from initial to target goal
     * @throws SearchTypeNotSupportedException
     */
    public LinkedList<String> execute() throws SearchTypeNotSupportedException, OperatorNotSupportedException{
    	long now = System.currentTimeMillis();
        LinkedList<String> operationList;

        if(this.type.trim().equals("greedy")){
            operationList = this.greedySearch(this.startingValue, new LinkedList<String>());
        }
        else if(this.type.trim().equals("iterative")){
            operationList = this.iterativeSearch();
        }
        else {
            throw new SearchTypeNotSupportedException("Unsupported Search Type. Please make sure the first line in your file has a supported search type.");
        }
        this.searchTask.cancel();

        
        /*
         * Successful Case: Need to output
         * Error
         * Search require
         * Nodes expanded:
         * Maximum search depth:
         */
        long then = System.currentTimeMillis();
        
        long diff = then - now;
        System.out.println("Worked in time: "+diff / 1000 + " seconds");
        this.displayPath(this.startingValue, operationList);
        System.exit(0);
        return operationList;
    }

    /**
     *
     * @return  List of operators to go from initial to target goal using greedy search
     * @throws OperatorNotSupportedException 
     */
    public LinkedList<String> greedySearch(double h, LinkedList<String> ops) throws OperatorNotSupportedException{
    	// TODO: this throws an StackOverFlowException, we need to solve this.
    	
    	double min = -1;
    	String pathNode = "";

        if(startingValue == targetValue) {
            return new LinkedList<String>();
        }

        for(String operator : operators) {
        	
        	/*
        	 * We will iterate through all operators and check to see which has the most "optimal" heuristic value.
        	 */
        	double branchVal = this.performOperation(h, operator); // Explore branch
        	        	
        	double temp = this.hueristicFunction(branchVal);
        	
        	if (temp == 0)
        	{
        		ops.add(pathNode);
        		
        		return ops;
        	}
        	
        	if (min == -1 || min > temp)
        	{
        		min = temp;
        		pathNode = operator;
        	}
        }

        
        ops.add(pathNode);
        
        return greedySearch(min,ops);
    }

    /**
     * 
     * @param branchVal the branch we are choosing to explore.
     * @return the hueristic value of the branch
     */
    private double hueristicFunction(double branchVal) {
		return Math.abs(this.targetValue - branchVal);	
	}

	/**
     *
     * @return  List of operators to go from initial to target goal using iterative search
     */
    public LinkedList<String> iterativeSearch() throws OperatorNotSupportedException{


        if(startingValue == targetValue) {
            return new LinkedList<String>();
        }

        for(int depth = 1; depth == depth; depth++) {
            LinkedList<String> operatorPath = this.depthLimitedSearch(this.startingValue, depth, new LinkedList<String>());
            if(!operatorPath.isEmpty()) {
                return operatorPath;
            }
        }


        return new LinkedList<String>();
    }

    /**
     * Perform  depth  limited search
     * @param root the starting point for the algorithm to choose
     * @param depth the depth of the actual search
     * @param operations the current "path"
     * @return	the path used by the algorithm
     * @throws OperatorNotSupportedException this happens if the user misused an operation.
     */
    public LinkedList<String> depthLimitedSearch(Double root, int depth, LinkedList<String> operations) throws OperatorNotSupportedException{
    	
    	
        if(depth == 0 && root == this.targetValue) {
            return operations;
        }
        else if(depth == 0) {
            return new LinkedList<String>();
        }
        else if(depth > 0) {
            for(String operator : this.operators) {
                operations.addLast(operator);
                Double child = performOperation(root, operator);
                LinkedList<String> nextLevel = this.depthLimitedSearch(child, depth - 1, operations);
                if(!nextLevel.isEmpty()) {
                    return nextLevel;
                }
                operations.removeLast();
            }
        }

        return new LinkedList<String>();
    }
    
    /**
     * Parse a string and an integer to manipulate the new value
     * @param root The branch we are expanding
     * @param op operator we are using
     * @return the new value of the branch
     * @throws OperatorNotSupportedException misuse of operator
     */
    public static Double performOperation(Double root, String op) throws OperatorNotSupportedException{
    	
    	nodesExpanded++;
    	
    	
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
            throw new OperatorNotSupportedException("Operator not supported. Please make sure all operators are in an acceptable format. Formats include '+', '-', '*', '/', and '^'");
        }
    }


    public static void displayPath(double startingValue, LinkedList<String> operatorPath) throws OperatorNotSupportedException{
        if(operatorPath.size() == 0 ){
            return;
        }

        String operator = operatorPath.removeFirst();
        Double newValue = AISearch.performOperation(startingValue, operator);
        System.out.println(startingValue + " " + operator + " = " + newValue);
        AISearch.displayPath(newValue, operatorPath);
    }


	public void setTimerTask(SearchTask searchTask) {
		this.searchTask = searchTask;
		
	}
}
