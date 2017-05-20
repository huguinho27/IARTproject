import java.util.*;

public class Chromosome implements Cloneable
{
	private ArrayList<Gene> genome = new ArrayList<Gene>();
	private float fitness;
	private EpocaNormal epocaNormal;

	public Chromosome()
	{
		genome = new ArrayList<Gene>();
		fitness = 0;
	}
	
	public void addGene(Gene g){
		this.genome.add(g);
	}
	
	@Override
	protected Chromosome clone()
	{
		Chromosome tmp = new Chromosome();
		tmp.setGenome(genome);
		tmp.setFitness(fitness);
		tmp.setEpNormal(epocaNormal);
		return tmp;
	}

	// setters and getters
	public float getFitness()
	{
		return fitness;
	}

	public void setFitness(float fitness)
	{
		this.fitness = fitness;
	}

	public ArrayList<Gene> getGenome()
	{
		return genome;
	}

	public void setGenome(ArrayList<Gene> genome)
	{
		this.genome = genome;
	}

	public void setEpNormal(EpocaNormal epn)
	{
		epocaNormal = epn;
	}

	public EpocaNormal getEpNormal()
	{
		return epocaNormal;
	}

	public void changeGene(int bit)
	{
		int r = Utilities.rand(0, this.epocaNormal.getWorkDays().size() - 1);
		genome.get(bit).setDayOfExam(this.epocaNormal.getWorkDays().get(r));
	}

	/*
	 * Fills a chromosome with random genes.
	 */
	public void fillChromosomeWithRandomGenes()
	{
		ArrayList<Gene> tmpGenome = new ArrayList<Gene>();
		
		for (int i = 0; i < epocaNormal.getSubjects().size(); i++)
		{
			int r = Utilities.rand(0, this.epocaNormal.getWorkDays().size() - 1);
			Gene g = new Gene(this.epocaNormal.getSubjects().get(i), this.epocaNormal.getWorkDays().get(r));
			Gene gene = (Gene) g.clone();
			tmpGenome.add(gene);
		}
		this.setGenome(tmpGenome);
	}

	public void calculateFitness()
	{
		float fitness = 0;
		int numOfYears = 0;

		for (int i = 0; i < genome.size() - 1; i++) {
			for (int j = i++; j < genome.size(); j++) {
				if (genome.get(i).getDayOfExam().equals(genome.get(j).getDayOfExam())) {
					for (Student student: genome.get(i).getSubject().getStudents()) {
						if (genome.get(j).getSubject().getStudents().contains(student)) {
							this.setFitness(0); 
							return;
							} 
						} 
					} 
				} 
			}

		ArrayList<ArrayList<Gene>> examsByYears = devideGenomeByYears();
		for (ArrayList<Gene> year : examsByYears)
		{
			if (!year.isEmpty())
			{
				year = this.sortByDate(year);
				fitness += this.fitnessForYear(year);
				numOfYears++;
			}
		}
		fitness = fitness / numOfYears;
		this.setFitness(fitness);
	}

	public ArrayList<ArrayList<Gene>> devideGenomeByYears()
	{
		ArrayList<Gene> firstYear = new ArrayList<Gene>();
		ArrayList<Gene> secondYear = new ArrayList<Gene>();
		ArrayList<Gene> thirdYear = new ArrayList<Gene>();
		ArrayList<Gene> fourthYear = new ArrayList<Gene>();
		ArrayList<Gene> fifthYear = new ArrayList<Gene>();

		ArrayList<ArrayList<Gene>> allYears = new ArrayList<ArrayList<Gene>>();

		for (Gene exam : this.genome)
		{
			if (exam.getSubject().getYear() == 1)
				firstYear.add(exam);
			else if (exam.getSubject().getYear() == 2)
				secondYear.add(exam);
			else if (exam.getSubject().getYear() == 3)
				thirdYear.add(exam);
			else if (exam.getSubject().getYear() == 4)
				fourthYear.add(exam);
			else if (exam.getSubject().getYear() == 5)
				fifthYear.add(exam);
		}
		allYears.add(firstYear);
		allYears.add(secondYear);
		allYears.add(thirdYear);
		allYears.add(fourthYear);
		allYears.add(fifthYear);

		return allYears;
	}

	public ArrayList<Gene> sortByDate(ArrayList<Gene> year)
	{
		ArrayList<Gene> newList = new ArrayList<Gene>();
		while (!year.isEmpty())
		{
			Gene min = year.get(0);
			for (int i = 1; i < year.size(); i++)
			{
				if (year.get(i).getDayOfExam().before(min.getDayOfExam()))
				{
					min = year.get(i);
				}
			}
			Gene g = (Gene) min.clone();
			newList.add(g);
			year.remove(min);
		}
		return newList;
	}

	public int fitnessForYear(ArrayList<Gene> year)
	{
		int result = 1;
		for (int i = 0; i < year.size() - 1; i++)
		{
			long dif = (year.get(i + 1).getDayOfExam().getTimeInMillis() - year.get(i).getDayOfExam().getTimeInMillis())
					/ 86400000;
			int diff = (int) (long) dif;
			if (diff != 0)
				result *= diff;
		}
		return result;
	}

	public void printChromosome()
	{
		System.out.println("Schedule:\n");
		for (int i = 0; i < this.genome.size(); i++)
		{
			System.out.println(
					this.genome.get(i).getDayOfExam().getTime() + " - " + this.genome.get(i).getSubject().getName());
		}
		System.out.println("Fitness = " + this.getFitness());
	}

}
