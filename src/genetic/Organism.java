package genetic;


import java.util.LinkedList;
import java.util.Random;

public class Organism {
	private LinkedList<String> path;
	private LinkedList<String> operators;
	
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
		return;
	}
	
	public double heuristic()
	{
		return 0.0;
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
	
	
}
