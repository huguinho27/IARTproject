package ExamSchedule;

public class Student {

	private int studentID;
	private String stName;
	private int stYear;
	
	public Student (int id, String name, int year){
		this.studentID = id;
		this.stName = name;
		this.stYear = year;
	}
	
	public int getID(){
		return this.studentID;
	}
	
	public String getName(){
		return this.stName;
	}
	
	public int getYear(){
		return this.stYear;
	}
	
	public void setName(String name){
		this.stName = name;
	}
	
	public void setYear(int year){
		this.stYear = year;
	}
	
	public void nextYear(){
		this.stYear++;
	}
}
