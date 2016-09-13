package genetic;

import java.util.LinkedList;

public class Organism {
	private LinkedList<String> path;
	private LinkedList<String> operators;
	
	public Organism()
	{
		path = new LinkedList<String>();
		operators = new LinkedList<String>();
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

		//Must include mutation
		return null; // TODO
	}
	
	private void mutate()
	{
		return;
	}
	
	public double heuristic()
	{
		return 0.0;
	}
	
	
}
