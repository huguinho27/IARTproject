import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class GeneticAlgorithm {
	
	public GeneticAlgorithm ()
	{
		
	}
	
	private ArrayList<Chromosome> chromosomesGenerated = new ArrayList<Chromosome>();
	private ArrayList<Chromosome> initialPopulation;
	
	public Chromosome[] crossover(Chromosome parent1, Chromosome parent2){
		Chromosome[] children = new Chromosome[2];
		int crossPoint = Utilities.rand(1, parent1.getGenome().size()-1);
		Chromosome child1 = new Chromosome();
		Chromosome child2 = new Chromosome();
		for (int i = 0; i < crossPoint; i++){
			child1.addGene(parent1.getGenome().get(i));
			child2.addGene(parent2.getGenome().get(i));
		}
		for (int j = crossPoint; j < parent1.getGenome().size(); j++){
			child2.addGene(parent1.getGenome().get(j));
			child1.addGene(parent2.getGenome().get(j));
		}
		children[0] = (Chromosome) child1.clone();
		children[1] = (Chromosome) child2.clone();

		return children;
	}
	
	public void mutations(int bit)
	{
		int ind = chromosomesGenerated.size();
		int size = chromosomesGenerated.get(0).getGenome().size();
		int totalBits = ind * size;
		int count;
		
		if (bit > totalBits){
			while (bit > totalBits) bit = bit - totalBits;
			count = 1;
		}
		else{
			count = totalBits / bit;
		}

		int chr = (bit-1) / size;
		int g = (bit-1) % size;
		chromosomesGenerated.get(chr).changeGene(g);
		count--;
		while (count > 0){
			g = g + bit;
			while (g >= size){
				chr++;
				g = g - size;
			}
			chromosomesGenerated.get(chr).changeGene(g);
			count--;
		}	
	}
	
	public ArrayList<Chromosome> selectNextGenerationElite(ArrayList<Chromosome> chromosomes, int num)
	{
		int j = 0;
		ArrayList<Chromosome> ordered = new ArrayList<Chromosome>();

		for (int i=0; i<num; i++)
		{
			j = maxFitness(chromosomes);
			ordered.add(chromosomes.get(j));
			chromosomes.remove(j);
		}

		return ordered;
	}
	
	public ArrayList<Chromosome> selectNextGenerationProbabilistic(ArrayList<Chromosome> chromosomes)
	{
		//Calculate Fa/Ftotal of each chromosome
		int countTotalFitness = 0;
		ArrayList<Double> chromosomesProbability = new ArrayList<Double>();
		
		for (int i=0; i< chromosomes.size(); i++)
		{
			countTotalFitness += chromosomes.get(i).getFitness();
		}
		
		for (int i=0; i < chromosomes.size(); i++)
		{
			//i think we have to use clone() here
			double prob = chromosomes.get(i).getFitness();
			chromosomesProbability.add(prob/countTotalFitness);
		}
		
		//Roulette Generated
		Random r = new Random();
		double start = r.nextDouble();
		double end = r.nextDouble();
		
		if (start > end)
		{
			double temp = end;
			end = start;
			start = temp;
		}
		
		//Selection
		double intervalo = 0.0;
		
		Collections.sort(chromosomesProbability);
		ArrayList<Double> sequenceProbability = new ArrayList<Double>();
		
		double count = chromosomesProbability.get(0);
		
		for (int i=0; i<(chromosomesProbability.size()-1); i++)
		{
			count += chromosomesProbability.get(i);
			sequenceProbability.add(count);
		}
		
		ArrayList<Chromosome> selectedChromosomes = new ArrayList<Chromosome>();
		
		for (int i=0; i<sequenceProbability.size();i++)
		{
			if (sequenceProbability.get(i)>start && sequenceProbability.get(i)<end)
				selectedChromosomes.add(chromosomes.get(i));
		}
		
		return selectedChromosomes;
		
	}

	public int maxFitness (ArrayList<Chromosome> chromosomes)
	{
		int temp = 0;
		for (int i=1; i<chromosomes.size(); i++)
		{
			if (chromosomes.get(i).getFitness() > chromosomes.get(temp).getFitness())
				temp = i;
		}

		return temp;
	}
	
	public int minFitness (ArrayList<Chromosome> chromosomes)
	{
		int temp = 0;
		for (int i=0; i<chromosomes.size(); i++)
		{
			if (chromosomes.get(i).getFitness() < chromosomes.get(temp).getFitness())
				temp = i;
		}

		return temp;
	}

	public Chromosome runProbabilistic(EpocaNormal epn, int populationSize, int mutationBit, int numIterations)
	{
		//create initial population
		//System.out.println("create initial population");
		
		this.initialPopulation = new ArrayList<Chromosome>();
		for (int i = 0; i < populationSize; i++){
			Chromosome tmp = new Chromosome();
			tmp.setEpNormal(epn);
			tmp.fillChromosomeWithRandomGenes();
			initialPopulation.add(tmp);
		}
		//calculate fitness
		//System.out.println("calculate fitness");
		
		for (Chromosome individual: initialPopulation){
			individual.calculateFitness();
			//System.out.println(individual.getFitness());
		}
		int iter = 0;
		int parentSize = populationSize / 2;
		while (iter < numIterations){
			chromosomesGenerated = new ArrayList<Chromosome>();
			//save best individual
			Chromosome best = (Chromosome) initialPopulation.get(maxFitness(initialPopulation)).clone();
			//System.out.println("best - " + best.getFitness());
			//select parents by probabilistic + elite
			//System.out.println("select parents by probabilistic + elite");
			
			ArrayList<Chromosome> parents = selectNextGenerationProbabilistic(initialPopulation);
			if (parents.size() > parentSize){
				parents = selectNextGenerationElite(parents, parentSize);
			}
			//select parents by elite
			else{
				//System.out.println("select parents by elite");
				
				int rest = parentSize - parents.size();
				if (rest > 0){
					ArrayList<Chromosome> restParents = selectNextGenerationElite(initialPopulation, rest);
					parents.addAll(restParents);
				}
			}
			
			//create pairs
			//System.out.println("create pairs");
			
			int[][] pairs = new int[parentSize][2];
			for (int i = 0; i < parentSize; i++){
				int firstParent = Utilities.rand(0, parentSize - 1);
				int secondParent;
				do{
					secondParent = Utilities.rand(0, parentSize - 1);

				}while (firstParent == secondParent);
				pairs[i][0] = firstParent;
				pairs[i][1] = secondParent;
			}
			
			//crossover
			//System.out.println("crossover");
			
			for (int i = 0; i < parentSize; i++){
				Chromosome[] children = crossover(parents.get(pairs[i][0]), parents.get(pairs[i][1]));
				this.chromosomesGenerated.add(children[0]);
				this.chromosomesGenerated.add(children[1]);
			}
			
			for (int i = 0; i < chromosomesGenerated.size(); i++){
				chromosomesGenerated.get(i).setEpNormal(epn);
			}
			//mutation
			//System.out.println("mutation");
			
			mutations(mutationBit);
			//set new initial population
			//System.out.println("set new initial population");
			
			this.initialPopulation = new ArrayList<Chromosome>();
			for (int i = 0; i < chromosomesGenerated.size(); i++){
				Chromosome tmp = (Chromosome) chromosomesGenerated.get(i).clone();
				this.initialPopulation.add(tmp);
			}
			for (Chromosome individual: initialPopulation){
				individual.calculateFitness();
				//System.out.println(individual.getFitness());
			}
			Chromosome worst = initialPopulation.get(minFitness(initialPopulation));
			if (best.getFitness() > worst.getFitness()){
				initialPopulation.remove(minFitness(initialPopulation));
				initialPopulation.add(best);
			}
		}
		return initialPopulation.get(maxFitness(initialPopulation));
	}
	
	public Chromosome runElite(EpocaNormal epn, int populationSize, int mutationBit, int numIterations)
	{
		//create initial population
		//System.out.println("create initial population");
		
		this.initialPopulation = new ArrayList<Chromosome>();
		for (int i = 0; i < populationSize; i++){
			Chromosome tmp = new Chromosome();
			tmp.setEpNormal(epn);
			tmp.fillChromosomeWithRandomGenes();
			initialPopulation.add(tmp);
		}
		//calculate fitness
		//System.out.println("calculate fitness");
		
		for (Chromosome individual: initialPopulation){
			individual.calculateFitness();
			//System.out.println(individual.getFitness());
		}
		int iter = 0;
		int parentSize = populationSize / 2;
		while (iter < numIterations){
			chromosomesGenerated = new ArrayList<Chromosome>();
			
			//save best individual
			Chromosome best = (Chromosome) initialPopulation.get(maxFitness(initialPopulation)).clone();
			//System.out.println("best - " + best.getFitness());
			
			//System.out.println("population: " + initialPopulation.size());
			//select parents by elite
			//System.out.println("select parents by elite");
			ArrayList<Chromosome> parents = selectNextGenerationElite(initialPopulation, parentSize);
			
			
			//create pairs
			//System.out.println("create pairs");
			
			int[][] pairs = new int[parentSize][2];
			for (int i = 0; i < parentSize; i++){
				int firstParent = Utilities.rand(0, parentSize - 1);
				int secondParent;
				do{
					secondParent = Utilities.rand(0, parentSize - 1);

				}while (firstParent == secondParent);
				pairs[i][0] = firstParent;
				pairs[i][1] = secondParent;
			}
			
			//crossover
			//System.out.println("crossover");
			
			for (int i = 0; i < parentSize; i++){
				Chromosome[] children = crossover(parents.get(pairs[i][0]), parents.get(pairs[i][1]));
				this.chromosomesGenerated.add(children[0]);
				this.chromosomesGenerated.add(children[1]);
			}
			
			for (int i = 0; i < chromosomesGenerated.size(); i++){
				chromosomesGenerated.get(i).setEpNormal(epn);
			}
			//mutation
			//System.out.println("mutation");
			
			mutations(mutationBit);
			//set new initial population
			//System.out.println("set new initial population");
			
			this.initialPopulation = new ArrayList<Chromosome>();
			for (int i = 0; i < chromosomesGenerated.size(); i++){
				Chromosome tmp = (Chromosome) chromosomesGenerated.get(i).clone();
				this.initialPopulation.add(tmp);
			}
			for (Chromosome individual: initialPopulation){
				individual.calculateFitness();
			}
			Chromosome worst = initialPopulation.get(minFitness(initialPopulation));
			if (best.getFitness() > worst.getFitness()){
				initialPopulation.remove(minFitness(initialPopulation));
				initialPopulation.add(best);
			}
			iter++;
		}
		return initialPopulation.get(maxFitness(initialPopulation));
	}
}
