package genetic;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

import exception.OperatorNotSupportedException;

public class Evolution {

	public Integer genCount = 10;
	
	private LinkedList<Organism> population;
	private LinkedList<String> operators;
	private Double goal;
	private static double finalVal = 0;

	public Evolution(LinkedList<String> ops, Double goal) {
		long now = System.currentTimeMillis();
		population = new LinkedList<Organism>();
		this.goal = goal;
		this.operators = ops;
		Organism.operators = ops;
		this.initializePopulation(250, 30, ops);//Modify these numbers for report
		for	(int i = 0; i < genCount; i++)
		{
			population = runTrial();
		}
		
		Organism alphaMale= population.getFirst();
		
		long then = System.currentTimeMillis();
        long diff = then - now;
        
        Integer orgSize = alphaMale.getPath().size();
		
		try {
			displayPath(Organism.startValue,alphaMale.getPath());
		} catch (OperatorNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
        
        
        //Output

        //finalVal is set by displayPath
        double errorAmt = java.lang.Math.abs(alphaMale.finalValue - goal);
        
        System.out.println("Error: " + errorAmt);
        System.out.println("Size of Organism: " + orgSize);
        System.out.println("Search required: "+diff / 1000 + " seconds");
        System.out.println("Population: " + this.population.size());
        System.out.println("Number of Generations " + genCount);
        
	
		
	}
	
    public static void displayPath(double startingValue, LinkedList<String> operatorPath) throws OperatorNotSupportedException{
        if(operatorPath.size() == 0 ){
            return;
        }

        String operator = operatorPath.removeFirst();
        if (!operator.equals(""))
        {
        	Double newValue = Organism.performOperation(startingValue, operator);
        	finalVal = newValue;
        	System.out.println(startingValue + " " + operator + " = " + newValue);
            displayPath(newValue, operatorPath);
        }
        
    }
	
	public LinkedList<Organism> runTrial()
	{
		
		LinkedList<Organism> pop = nextGeneration(this.population);
		
		this.population = pop;
		return evaluate(pop);
	}


	public LinkedList<Organism> nextGeneration(LinkedList<Organism> currentGeneration) {

		LinkedList<Organism> nextGen = new LinkedList<Organism>();

		//Evaluate population - returns list in order of evaluation
		LinkedList<Organism> evaluated = this.evaluate(currentGeneration);//stubbed
		//Replace worst with best (culling/elitism)
		Organism alpha = evaluated.getFirst(); //The best based on heuristic
		evaluated.addLast(alpha);//Elitism


		//Reproduce best fitted population
		for(int i = 1; i < evaluated.size(); i++) {
			//Randomly select two from the population
			Organism father;
			Organism mother;
			do {
				father = this.randomSelection(evaluated);
				mother = this.randomSelection(evaluated);
				nextGen.remove(father);
				nextGen.remove(mother);
			}while(!father.equals(mother));
			LinkedList<Organism> children = mother.reproduce(father);
			//Mutation occurs within reproduction
			for(Organism child : children) {
				nextGen.add(child);
			}
			evaluated.removeLast();//cull
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
				int comparision = (int) Math.ceil(o1.heuristic() - o2.heuristic());
				return comparision;
			}
		});

		return population;
	}

	public void initializePopulation(int size, int maxOps, LinkedList<String> initialOps) {

		for(int i = 0; i < size; i++) {
			Organism org = Organism.randomlyGenerate(maxOps, initialOps, goal);
			this.population.add(org);
		}

	}
	
}
