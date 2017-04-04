package ExamSchedule;

import java.util.*;

public class University {

	private int studentID = 1000;
	private int couseID = 100;
	ArrayList<Course> courses;
	ArrayList<Student> students;
	
	public University(){};
	
	public ArrayList<Course> getCourses(){
		return this.courses;
	}
	
	public ArrayList<Student> getStudents(){
		return this.students;
	}
	
	public void newStudent(String name, int year){
		Student s = new Student(this.studentID, name, year);
		this.studentID++;
		this.students.add(s);
	}
	
	public void newCourse(String name, int year){
		Course c = new Course(this.couseID, name, year);
		this.couseID++;
		this.courses.add(c);
	}
	
}
