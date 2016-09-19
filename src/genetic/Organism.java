package genetic;


import java.util.LinkedList;
import java.util.Random;

import exception.OperatorNotSupportedException;

public class Organism {
	private LinkedList<String> path;
	private LinkedList<String> operators;
	private double finalValue;
	
	public Organism()
	{
		path = new LinkedList<String>();
		operators = new LinkedList<String>();
	}

	public Organism(LinkedList<String> operators) {
		this.path = new LinkedList<String>();
		this.operators = operators;
	}

	public Organism(LinkedList<String> path, LinkedList<String> operators)
	{
		this.path=path;
		this.operators=operators;
	}
	
	public LinkedList<String> getPath() {
		return path;
	}
	
	public void setPath(LinkedList<String> path) {
		this.path = path;
	}
	
	public LinkedList<String> getOperators() {
		return operators;
	}
	
	public void setOperators(LinkedList<String> operators) {
		this.operators = operators;
	}
	
	public LinkedList<Organism> reproduce(Organism o)
	{
		//Determine which organism has more operators
		int minAllelles;
		int maxAllelles;
		int thisSize = this.getOperators().size();
		int thatSize = o.getOperators().size();
		if(thisSize <= thatSize) {
			minAllelles = thisSize;
			maxAllelles = thatSize;
		}
		else {
			minAllelles = thatSize;
			maxAllelles = thisSize;
		}

		Random ran = new Random();
		int crossoverPivot = ran.nextInt() % minAllelles;
		//Crossover contains mutation
		return this.crossover(this, o, crossoverPivot, maxAllelles);
	}
	
	private void mutate()
	{
		Random r =  new Random();
		Integer pathIndex = r.nextInt(this.path.size()-1);
		Integer replaceIndex = r.nextInt(this.operators.size()-1);
		
		path.set(pathIndex,operators.get(replaceIndex));
	}
	
	public double heuristic()
	{
		//Heuristic will be calculated though the error (whether the search is successful)
		//I plan to use a recursive function to trace through the route.
		
		try {
			return this.finalValue - trace(0,0);
		} catch (OperatorNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public double trace(double root, Integer index) throws OperatorNotSupportedException
	{
		//We are just going to trace through the path recursively
		if (path.size() > index)
		{
			root += performOperation(root, path.get(index++));
			return trace(root,index++);
		}
		else
		{
			
			return root;
		}
	}

	private LinkedList<Organism> crossover(Organism father, Organism mother, int crossoverPivot, int maxsize) {
		//contains mutation

		LinkedList<String> childAOps = new LinkedList<String>();
		LinkedList<String> childBOps = new LinkedList<String>();

		//crossover
		for(int i = 0; i < crossoverPivot; i++) {//make sure to come back and check corner cases here
			childAOps.add(i, father.getOperators().get(i));
			childBOps.add(i, mother.getOperators().get(i));
		}
		for(int j = crossoverPivot; j < maxsize; j++) {
			childAOps.add(j, mother.getOperators().get(j));
			childBOps.add(j, father.getOperators().get(j));
		}
		Organism childA = new Organism(childAOps);
		Organism childB = new Organism(childBOps);

		//mutate - small percentage chance of mutating
		childA.mutate();
		childB.mutate();

		LinkedList<Organism> children = new LinkedList<Organism>();
		children.add(childA);
		children.add(childB);

		return children;
	}
	
	public static Organism randomlyGenerate(int maxOps, LinkedList<String> ops, double finalInt){
		Organism org = new Organism();
		Random ran = new Random();
		int numOps = ran.nextInt() % maxOps;

		for(int i = 0; i < numOps; i++) {
			int opIndex = ran.nextInt() % ops.size();
			String op = ops.get(opIndex);
			if(!org.getOperators().contains(op)) {
				org.getOperators().add(op);
			}
		}
		
		org.finalValue = finalInt;
		
		return org;
	}
	
	 /**
     * Parse a string and an integer to manipulate the new value
     * @param root The branch we are expanding
     * @param op operator we are using
     * @return the new value of the branch
     * @throws OperatorNotSupportedException misuse of operator
     */
    public static Double performOperation(Double root, String op) throws OperatorNotSupportedException{
    	
    	
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
    

}
