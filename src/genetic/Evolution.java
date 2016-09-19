package genetic;


import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

import exception.OperatorNotSupportedException;

public class Evolution {

	private LinkedList<Organism> population;
	private LinkedList<String> operators;
	private Double goal;
	private Double currentResult;

	public Evolution(LinkedList<String> ops, Double goal) {
		this.goal = goal;
		this.operators = ops;
		this.population = new LinkedList<Organism>();
		this.initializePopulation(250, 30, 15, ops);//Modify these numbers for report
	}
	
	public void runTrial()
	{
		//sort population
		this.evaluate(population);
		//get our best value
		try {
			currentResult =  population.getFirst().trace(0,0);
		} catch (OperatorNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		population = nextGeneration(population);
		
		
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
		int popIndex = Math.abs(ran.nextInt() % population.size());

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

	public void initializePopulation(int size, int maxOps, int maxOperand, LinkedList<String> initialOps) {

		for(int i = 0; i < size; i++) {
			Organism org = Organism.randomlyGenerate(maxOps, initialOps, goal);
			this.population.add(org);
		}

	}
	
}
