package ExamSchedule;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main
{

	public static void main(String[] args) throws IOException // java <progName> <subjectsNameFile> <timeIntervalFile>
	{
		if (args.length != 3)
		{
			System.out.println("USAGE: java Main <subjectsNameFile> <timeIntervalFileName>");
			System.exit(0);
		}
		ArrayList<Calendar> dates = new ArrayList<Calendar>();
		ArrayList<Course> courses = new ArrayList<Course>();
		String strLine;

		// DATES
		FileInputStream fstream = new FileInputStream(args[1]);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		while ((strLine = br.readLine()) != null)
		{
			String[] initDate = strLine.split("-");
			dates.add(new GregorianCalendar(Integer.parseInt(initDate[0]), Integer.parseInt(initDate[1]),
					Integer.parseInt(initDate[2])));
		}
		// DATES END

		// SUBJECTS FILE EXAMPLE: 0;FIS1,1;0-JOAO:1-ALEXANDRE
		fstream = new FileInputStream(args[0]);
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
			}

		}
		// SUBJECTS FILE END
		br.close();
		
		EpocaNormal epn = new EpocaNormal(dates.get(0), dates.get(1));
		University feup = new University();
		feup.setEpNormal(epn);
		
		for (int i = 0; i < courses.size();i++)
			feup.newCourse(courses.get(i).getName(), courses.get(i).getYear());

		feup.getEpNormal().setSubjects(feup.getCourses());

		Chromosome cr = new Chromosome();
		cr.setEpNormal(epn);
		cr.fillChromosomeWithRandomGenes();
		cr.calculateFitness();
		cr.printChromosome();

	}

}
