package ExamSchedule;

import java.util.*;

public class Course {
	
	private int courseID;
	private String courseName;
	private int year;
	private ArrayList<Student> students;
	
	public Course(int ID, String name, int year){
		this.courseID = ID;
		this.courseName = name;
		this.year = year;
	}
	
	public int getID(){
		return this.courseID;
	}
	
	public String getName(){
		return this.courseName;
	}
	
	public int getYear(){
		return this.year;
	}
	
	public ArrayList<Student> getStudents(){
		return this.students;
	}
	
	public void setName(String name){
		this.courseName = name;
	}
	
	public void setYear(int year){
		this.year = year;
	}
	
	public void setStudents(ArrayList<Student> students){
		this.students = students;
	}
	
	public void addStudent(Student s){
		this.students.add(s);
	}
}
