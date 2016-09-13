package genetic;

import java.util.LinkedList;

public class Evolution {

	private LinkedList<Organism> population;

	public LinkedList<Organism> createPopulation(int initialSize){
		return null;
		
	};
	
	public void runTrial()
	{
		return;
	}
	
	public void cull()
	{
		
	}

	public LinkedList<Organism> nextGeneration(LinkedList<Organism> currentGeneration) {

		LinkedList<Organism> nextGen = new LinkedList<Organism>();

		
		//Evaluate population - returns list in order of evaluation
		LinkedList<Organism> evaluated = this.evaluate(currentGeneration);//stubbed
		//Replace worst with best (culling/elitism)
		Organism alpha = evaluated.getFirst(); //The best based on heuristic
		evaluated.removeLast(); //cull
		evaluated.addLast(alpha);//Elitism


		//Reproduce best fitted population
		for(int i = 1; i < this.population.size(); i++) {
			//Randomly select two from the population
			Organism father;
			Organism mother;
			do {
				father = this.randomSelection();
				mother = this.randomSelection();
			}while(!father.equals(mother));
			LinkedList<Organism> children = mother.reproduce(father);
			//Mutation occurs within reproduction
			for(Organism child : children) {
				nextGen.add(child);
			}
		}

		return nextGen;
	}

	public LinkedList<Organism> getPopulation() {
		return this.population;
	}

	public Organism randomSelection() {

		return new Organism();
	}

	public LinkedList<Organism> evaluate(LinkedList<Organism> population) {
		//Returns list sorted by heuristic function

		return population;
	}
	
}
