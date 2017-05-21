import java.io.BufferedReader;
import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Interface
{
	private String subjectsFileName, timeIntervalFile;
	ArrayList<Calendar> dates;
	ArrayList<Course> courses;
	private Chromosome lastResult = new Chromosome();

	public Interface(String subjectsFileName, String timeIntervalFile) throws NumberFormatException, IOException
	{
		this.subjectsFileName = subjectsFileName;
		this.timeIntervalFile = timeIntervalFile;
		courses = new ArrayList<Course>();
		dates = new ArrayList<Calendar>();
		readInfoFromFiles();
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
			dates.add(new GregorianCalendar(Integer.parseInt(initDate[0]), Integer.parseInt(initDate[1])-1, Integer.parseInt(initDate[2])));
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

	public void MainMenu() throws IOException
	{
		int option = 0;
		Console console = System.console();

		while (true)
		{
			System.out.println("GENETIC ALGORITHM - EXAM SCHEDULE");
			System.out.println("1. Run Genetic Algorithm");
			System.out.println("2. Show schedule for one student");
			System.out.println("3. Compare both Genetic Algorithm Solutions");
			System.out.println("0 - Exit");

			String line = console.readLine();
			option = Integer.parseInt(line);

			switch (option)
			{
			case 1:
			case 3:
			{
				runAlgorithmMenu(option);
				break;
			}
			case 2:
			{
				ShowStudentScheduleMenu();
				break;
			}
			case 0:
			{
				System.exit(0);
				break;
			}
			default:
				continue;
			}
		}
	}
	
	public void ShowStudentScheduleMenu() throws IOException
	{
		int studentId = 0;
		Console console = System.console();
		
		while (true)
		{
			System.out.println("GENETIC ALGORITHM - EXAM SCHEDULE");
			System.out.println("Please insert the ID of the student you want to show schedule (0  to back to Main Menu)");
			System.out.println(">> ");

			String line = console.readLine();
			studentId = Integer.parseInt(line);

			switch (studentId)
			{
			case 0:
			{
				MainMenu();
				break;
			}
			}
			
			if (studentId != 0)
				break;
		}
		
		if (this.lastResult.getFitness() == 0){
			System.out.println("First run the algoruthm!");
		}
		else {
			this.lastResult.scheduleForStudent(studentId);
		}
	}

	public void runAlgorithmMenu(int compare) throws IOException
	{
		int elitistOrProb = 0, initialPopSize = 0, mutationBit = 0, numIt = 0;
		Console console = System.console();

		while (true)
		{
			System.out.println("GENETIC ALGORITHM - EXAM SCHEDULE");
			System.out.println("How do you want to run algorithm");
			System.out.println("1. Elitist solution");
			System.out.println("2. Elitist + Probabilistic Solutions");
			System.out.println("0 - Back to Main Menu");

			String line = console.readLine();
			elitistOrProb = Integer.parseInt(line);

			switch (elitistOrProb)
			{
			case 0:
			{
				MainMenu();
				break;
			}
			}
			System.out.println("elitistOrProb : " + elitistOrProb);
			if (elitistOrProb == 1 || elitistOrProb == 2)
				break;
		}
		
		while (true)
		{
			System.out.println("GENETIC ALGORITHM - EXAM SCHEDULE");
			System.out.println("What should be the size of the initial population (0 to back to Main Menu)");
			System.out.println(">> ");

			String line = console.readLine();
			initialPopSize = Integer.parseInt(line);

			switch (initialPopSize)
			{
			case 0:
			{
				MainMenu();
				break;
			}
			}
			
			if (initialPopSize > 0)
				break;
		}
		
		while (true)
		{
			System.out.println("GENETIC ALGORITHM - EXAM SCHEDULE");
			System.out.println("What should be the number of iterations (0 to back to Main Menu)");
			System.out.println(">> ");

			String line = console.readLine();
			numIt = Integer.parseInt(line);

			switch (initialPopSize)
			{
			case 0:
			{
				MainMenu();
				break;
			}
			}
			
			if (numIt > 0)
				break;
		}
		
		while (true)
		{
			System.out.println("GENETIC ALGORITHM - EXAM SCHEDULE");
			System.out.println("What should be the bit to mutate? (0 to back to Main Menu)");
			System.out.println(">> ");

			String line = console.readLine();
			mutationBit = Integer.parseInt(line);

			switch (mutationBit)
			{
			case 0:
			{
				MainMenu();
				break;
			}
			}
			
			if (mutationBit != 0)
				break;
		}

		if (compare == 1){
			runGA(elitistOrProb, initialPopSize, mutationBit, numIt);
		}
		else if (compare == 3){
			//TODO compare(initialPopSize, mutationBit, numIt);
		}
	}

	public void runGA(int elitisOrProb, int initPopSize, int mutationBit, int numIt)
	{
		System.out.println(dates.get(0).getTime() + "\n" + dates.get(1).getTime());
		EpocaNormal epn = new EpocaNormal(dates.get(0), dates.get(1));
		
		//System.out.println("Start: " + epn.getWorkDays().get(0).getTime() + "\nEnd:   " + epn.getEnd().getTime());
		University feup = new University();
		feup.setEpNormal(epn);
		for (int i = 0; i < courses.size(); i++)
			feup.newCourse(courses.get(i).getName(), courses.get(i).getYear());
		feup.SetEpocaNormalSubjects(courses);
		GeneticAlgorithm ga = new GeneticAlgorithm();
		Chromosome result = new Chromosome();
		if (elitisOrProb == 1){
			result = ga.runElite(epn, initPopSize, mutationBit, numIt);
		}
		else if (elitisOrProb == 2){
			System.out.println("HERE!");
			result = ga.runProbabilistic(epn, initPopSize, mutationBit, numIt);
		}
		result.printChromosome();
		lastResult = (Chromosome) result.clone();
		ga.printStatistics();
	}
	
	public void compare(int initialPopSize, int mutationBit, int numIt){
		EpocaNormal epn = new EpocaNormal(dates.get(0), dates.get(1));
		System.out.println("Start: " + epn.getWorkDays().get(0).getTime() + "\nEnd:   " + epn.getEnd().getTime());
		University feup = new University();
		feup.setEpNormal(epn);
		for (int i = 0; i < courses.size(); i++)
			feup.newCourse(courses.get(i).getName(), courses.get(i).getYear());
		feup.SetEpocaNormalSubjects(courses);
		GeneticAlgorithm gaE = new GeneticAlgorithm();
		GeneticAlgorithm gaP = new GeneticAlgorithm();
		Chromosome resultE = new Chromosome();
		Chromosome resultP = new Chromosome();
		resultP = gaP.runProbabilistic(epn, initialPopSize, mutationBit, numIt);
		resultE = gaE.runElite(epn, initialPopSize, mutationBit, numIt);
		lastResult = (Chromosome) resultE.clone();
		resultP.printCompare(resultE);
		gaP.printStatComp(gaE);
	}

	public static void main(String[] args) throws IOException
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