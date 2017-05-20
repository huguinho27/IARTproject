import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Interface
{
	private String subjectsFileName, timeIntervalFile;
	ArrayList<Calendar> dates;
	ArrayList<Course> courses;

	public Interface(String subjectsFileName, String timeIntervalFile)
	{
		this.subjectsFileName = subjectsFileName;
		this.timeIntervalFile = timeIntervalFile;
		courses = new ArrayList<Course>();
		dates = new ArrayList<Calendar>();
	}

	public void readInfoFromFiles() throws NumberFormatException, IOException
	{
		String strLine;

		// DATES
		FileInputStream fstream = new FileInputStream(timeIntervalFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		while ((strLine = br.readLine()) != null)
		{
			String[] initDate = strLine.split("-");
			dates.add(new GregorianCalendar(Integer.parseInt(initDate[0]), Integer.parseInt(initDate[1]),
					Integer.parseInt(initDate[2])));
		}
		// DATES END

		// SUBJECTS FILE EXAMPLE: 0;FIS1,1;0-JOAO:1-ALEXANDRE
		fstream = new FileInputStream(subjectsFileName);
		br = new BufferedReader(new InputStreamReader(fstream));
		while ((strLine = br.readLine()) != null)
		{
			if (!strLine.isEmpty())
			{

				String[] initDate = strLine.split(";");
				String[] studentsOnCourse = initDate[3].split(":");

				Course c = new Course(Integer.parseInt(initDate[0]), initDate[1], Integer.parseInt(initDate[2]));

				for (int i = 0; i < studentsOnCourse.length; i++)
				{
					String[] pairStudentId = studentsOnCourse[i].split("-");
					c.addStudent(new Student(Integer.parseInt(pairStudentId[0]), pairStudentId[1]));
				}
				courses.add(c);
			}
		}
		// SUBJECTS FILE END
		br.close();
	}

	public void MainMenu()
	{
		int option = 0;
		while (option != 5)
		{
			System.out.println("GENETIC ALGORITHM - EXAM SCHEDULE");
			System.out.println("1. Run Genetic Algorithm");
			System.out.println("2. Show schedule for one student");
			System.out.println("3. Compare both Genetic Algorithm Solutions");
			System.out.println("0 - Exit");
		}

	}
	
	public void runAlgorithmMenu(int option)
	{
		
	}
	
	public void runGA()
	{
		EpocaNormal epn = new EpocaNormal(dates.get(0), dates.get(1));
		University feup = new University();
		feup.setEpNormal(epn);

		for (int i = 0; i < courses.size(); i++)
			feup.newCourse(courses.get(i).getName(), courses.get(i).getYear());

		feup.SetEpocaNormalSubjects(courses);

		// Chromosome run(EpocaNormal epn, int populationSize, int mutationBit,
		// int numIterations)
		GeneticAlgorithm ga = new GeneticAlgorithm();
		// Chromosome result = ga.runProbabilistic(epn, 4, 8, 2);
		Chromosome result = ga.runElite(epn, 50, 8, 100);
		result.printChromosome();

		/*
		 * Chromosome cr = new Chromosome(); cr.setEpNormal(epn);
		 * cr.fillChromosomeWithRandomGenes(); cr.printChromosome();
		 */
	}

	public static void main(String[] args) throws IOException // java Interface  <subjectsNameFile> // <timeIntervalFile>
	{
		if (args.length != 2)
		{
			System.out.println("USAGE: java Interface <subjectsNameFile> <timeIntervalFileName>");
			System.exit(0);
		}
		Interface interf = new Interface(args[0], args[1]);
		interf.MainMenu();
	}
}
