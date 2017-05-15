package ExamSchedule;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		Calendar c1 = new GregorianCalendar(2017, 4, 29);
		Calendar c2 = new GregorianCalendar(2017, 5, 30);
		EpocaNormal epn = new EpocaNormal(c1, c2);
		/*System.out.println("\n\n");
		for (Calendar object: epn.workDays){
			System.out.println(object.getTime());
		}*/
		
		University feup = new University();
		feup.setEpNormal(epn);
		
		feup.newCourse("FIS1", 1);
		feup.newCourse("MEST", 1);
		feup.newCourse("MPCP", 1);
		feup.newCourse("PROG", 1);
		feup.newCourse("BDAD", 2);
		feup.newCourse("CGRA", 2);
		feup.newCourse("CAL", 2);
		feup.newCourse("SOPE", 2);
		feup.newCourse("IART", 3);
		feup.newCourse("SDIS", 3);
		feup.newCourse("CPAR", 4);
		feup.newCourse("AGRS", 4);
		feup.newCourse("CMO", 4);
		feup.newCourse("MARK", 4);
		feup.newCourse("MNSE", 4);
		feup.newCourse("SSIN", 4);
		feup.newCourse("TBDA", 4);
		feup.newCourse("TDIN", 4);
		
		feup.getEpNormal().setSubjects(feup.getCourses());
		
		
		//System.out.println(feup.getEpNormal().getWorkDays().size());
		//System.out.println(feup.getCourses().size());
		Chromosome cr = new Chromosome();
		cr.setEpNormal(epn);
		cr.fillChromosomeWithRandomGenes();
		cr.calculateFitness();
		cr.printChromosome();
	
	}

}
