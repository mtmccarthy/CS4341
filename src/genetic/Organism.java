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
		int thisSize = this.getOperators().size();
		int thatSize = o.getOperators().size();
		if(thisSize <= thatSize) {
			minAllelles = thisSize;
		}
		else {
			minAllelles = thatSize;
		}

		Random ran = new Random();
		int crossoverPivot = ran.nextInt() % minAllelles;
		//Crossover contains mutation
		return this.crossover(this, o, crossoverPivot);
	}
	
	private void mutate()
	{
		return;
	}
	
	public double heuristic()
	{
		return 0.0;
	}

	private LinkedList<Organism> crossover(Organism father, Organism mother, int crossoverPivot) {
		//contains mutation

		return null;
	}
	
	
}
