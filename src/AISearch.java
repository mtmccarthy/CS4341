import java.util.Calendar;
import java.util.LinkedList;

import exception.OperatorNotSupportedException;
import exception.SearchTypeNotSupportedException;
import genetic.Evolution;

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
    
	public static LinkedList<String> path;
	private SearchTask searchTask;
	/**
	 * The amount of nodes expanded (needed for post-search report)
	 */
	static int nodesExpanded=0;
	static int maxDepth=0;
	static Double finalVal=(double) 0;

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
        this.path = new LinkedList<String>();
               
        
    }
    

    /**
     *
     * @return List of operators to go from initial to target goal
     * @throws SearchTypeNotSupportedException
     */
    public LinkedList<String> execute() throws SearchTypeNotSupportedException, OperatorNotSupportedException{

        nodesExpanded=0;
        maxDepth=0;
        finalVal=(double) 0;
        path = new LinkedList<String>();
    	
        LinkedList<String> operationList;
        
        
        if(this.type.trim().equals("greedy")){
        	maxDepth++;
            operationList = this.greedySearch(this.startingValue, new LinkedList<String>());
            path = operationList;
            System.out.println("Path is " + path.size());
        }
        else if(this.type.trim().equals("iterative")){
            operationList = this.iterativeSearch();
            path = operationList;
            System.out.println("Path is " + path.size());
        }
        else if(this.type.trim().equals("genetic")) {
            operationList = this.geneticSearch();
        }
        else {
            throw new SearchTypeNotSupportedException("Unsupported Search Type. Please make sure the first line in your file has a supported search type.");
        }
       
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
    	double branchVal = 0;
    	String pathNode = "";

        if(startingValue == targetValue) {
            return new LinkedList<String>();
        }

        for(String operator : operators) {
        	/*
        	 * We will iterate through all operators and check to see which has the most "optimal" heuristic value.
        	 */
        	branchVal = this.performOperation(h, operator); // Explore branch
        	        	
        	double temp = this.heuristicFunction(branchVal);
        	

        	
        	if (min == -1 || min > temp)
        	{
        		min = temp;
        		pathNode = operator;
        	}
        	
        	if (temp == 0)
        	{
        		ops.add(pathNode);
        		return ops;
        	}
        }

        maxDepth++;
        ops.add(pathNode);
        this.searchTask.path = ops;

        return greedySearch(this.performOperation(h, pathNode),ops);
    }

    /**
     * 
     * @param branchVal the branch we are choosing to explore.
     * @return the hueristic value of the branch
     */
    private double heuristicFunction(double branchVal) {
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
        	maxDepth = depth;
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
        this.searchTask.path = operations;
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

            return Math.floor(root + operand);
        }
        else if(operator.equals("-")){
            return Math.floor(root - operand);
        }
        else if(operator.equals("*")){
            return Math.floor(root * operand);
        }
        else if(operator.equals("/")){
            return Math.floor(root / operand);
        }
        else if(operator.equals("^")){
            return Math.floor(Math.pow(root, operand));
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
        if (!operator.equals(""))
        {
        	Double newValue = AISearch.performOperation(startingValue, operator);
        	finalVal = newValue;
        	System.out.println(startingValue + " " + operator + " = " + newValue);
            AISearch.displayPath(newValue, operatorPath);
        }
        
    }


	public void setTimerTask(SearchTask searchTask) {
		this.searchTask = searchTask;
	}



	public LinkedList<String> geneticSearch() {
		Evolution e  = new Evolution(operators, this.targetValue);

	    return new LinkedList<>();
    }
}
