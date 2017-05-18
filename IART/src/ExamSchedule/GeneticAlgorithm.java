package ExamSchedule;

import java.util.*;

import ExamSchedule.Gene;


public class GeneticAlgorithm {
	
	private ArrayList<Chromosome> chromosomesGenerated;
	
	private double crossoverprobability = 0;

	public Chromosome[] crossover(Chromosome parent1, Chromosome parent2){
		Chromosome[] children = new Chromosome[2];
		int crossPoint = Utilities.rand(1, parent1.getGenome().size()-1);
		for (int i = 0; i < crossPoint; i++){
			children[0].getGenome().add(parent1.getGenome().get(i));
			children[1].getGenome().add(parent2.getGenome().get(i));
		}
		for (int j = crossPoint; j < parent1.getGenome().size(); j++){
			children[1].getGenome().add(parent1.getGenome().get(j));
			children[0].getGenome().add(parent2.getGenome().get(j));
		}

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
	
	public ArrayList<Chromosome> selectNextGenerationChromosome(ArrayList<Chromosome> chromosomes)
	{
		int j = 0;
		ArrayList<Chromosome> ordered = new ArrayList<Chromosome>();

		for (int i=0; i<10; i++)
		{
			j = maxFitness(chromosomes);
			ordered.add(chromosomes.get(j));
		}

		return ordered;
	}

	public int maxFitness (ArrayList<Chromosome> chromosomes)
	{
		int temp = 0;
		for (int i=0; i<chromosomes.size(); i++)
		{
			if (chromosomes.get(i).getFitness() > temp)
				temp = i;
		}

		chromosomes.remove(temp);

		return temp;
	}
	
	public void setCrossoverProbability(double value)
	{
		crossoverprobability = value;
	}
	
	public void run()
	{
		
	}

}
