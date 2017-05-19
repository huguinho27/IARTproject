
import java.util.*;

public class University
{

	private int studentID;
	private int couseID;
	private ArrayList<Course> courses;
	private ArrayList<Student> students;
	private EpocaNormal epocaNormal;

	public University()
	{
		courses = new ArrayList<Course>();
		students = new ArrayList<Student>();
		couseID = 100;
		studentID = 1000;
	};

	public ArrayList<Course> getCourses()
	{
		return this.courses;
	}

	public ArrayList<Student> getStudents()
	{
		return this.students;
	}

	public void newStudent(String name, int year)
	{
		Student s = new Student(this.studentID, name);
		this.studentID++;
		this.students.add(s);
	}

	public void newCourse(String name, int year)
	{
		Course c = new Course(this.couseID, name, year);
		this.couseID++;
		this.courses.add(c);
	}

	public void setEpNormal(EpocaNormal epn)
	{
		this.epocaNormal = epn;
	}

	public EpocaNormal getEpNormal()
	{
		return this.epocaNormal;
	}

	public void SetEpocaNormalSubjects(ArrayList<Course> courses)
	{
		epocaNormal.setSubjects(courses);
	}
}
