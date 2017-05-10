package ExamSchedule;
import java.util.*;


public class Chromosome {

	//array of genes
	private ArrayList<Gene> genome = new ArrayList<Gene>();

	//fitness
	private int fitness;
	
	private EpocaNormal epocaNormal;


	//setters and getters
	public int getFitness() {
		return fitness;
	}
	public void setFitness(int fitness) {
		this.fitness = fitness;
	}
	public ArrayList<Gene> getGenome() {
		return genome;
	}
	public void setGenome(ArrayList<Gene> genome) {
		this.genome = genome;
	}
	public void setEpNormal(EpocaNormal epn){
		this.epocaNormal = epn;
	}
	public EpocaNormal getEpNormal(){
		return this.epocaNormal;
	}


	/*
	 * Fills a chromosome with random genes. 
	 * */	
	public void fillChromosomeWithRandomGenes(){
		System.out.println("Filling chromosome with random genes....");

		ArrayList<Gene> tmpGenome = new ArrayList<Gene>();
	
		for (int i = 0; i < this.epocaNormal.getSubjects().size(); i++){
			int r = Utilities.rand(0, this.epocaNormal.getWorkDays().size() - 1);
			Gene g = new Gene(this.epocaNormal.getSubjects().get(i), this.epocaNormal.getWorkDays().get(r));
			Gene gene = (Gene) g.clone();
			tmpGenome.add(gene);
		}
		this.setGenome(tmpGenome);
	}
}