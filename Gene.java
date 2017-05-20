import java.util.*;

public class Gene implements Cloneable
{

	private Course exam;
	private Calendar dayOfExam;

	public Gene(Course subject, Calendar data)
	{
		this.exam = subject;
		this.dayOfExam = data;
	}

	@Override
	protected Gene clone()
	{
		return new Gene(exam, dayOfExam);
	}

	public void printGene(){
		System.out.println(exam.getName() + " - " + dayOfExam.getTime());
	}
	
	public Course getSubject()
	{
		return this.exam;
	}

	public Calendar getDayOfExam()
	{
		return this.dayOfExam;
	}

	public void setExam(Course subject)
	{
		this.exam = subject;
	}

	public void setDayOfExam(Calendar newDay)
	{
		this.dayOfExam = newDay;
	}

}
