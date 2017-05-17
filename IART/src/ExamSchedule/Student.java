package ExamSchedule;

public class Student
{
	private int studentID;
	private String stName;

	public Student(int id, String name)
	{
		this.studentID = id;
		this.stName = name;
	}

	public int getID()
	{
		return this.studentID;
	}

	public String getName()
	{
		return this.stName;
	}

	public void setName(String name)
	{
		this.stName = name;
	}
}
