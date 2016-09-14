package genetic;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class Evolution {

	private LinkedList<Organism> population;

	public LinkedList<Organism> createPopulation(int initialSize){
		return null;
		
	};
	
	public void runTrial()
	{
		return;
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
		for(int i = 1; i < evaluated.size(); i++) {
			//Randomly select two from the population
			Organism father;
			Organism mother;
			do {
				father = this.randomSelection(evaluated);
				mother = this.randomSelection(evaluated);
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

	public Organism randomSelection(LinkedList<Organism> population) {

		Random ran = new Random();
		int popIndex = ran.nextInt() % population.size();

		return population.get(popIndex);
	}

	public LinkedList<Organism> evaluate(LinkedList<Organism> population) {
		//Returns list sorted by heuristic function

		population.sort(new Comparator<Organism>() {
			@Override
			public int compare(Organism o1, Organism o2) {
				int comparision = (int) Math.floor(o1.heuristic() - o2.heuristic());
				return comparision;
			}
		});

		return population;
	}
	
}
