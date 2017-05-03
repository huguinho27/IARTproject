package ExamSchedule;

import java.util.*;

public class EpocaNormal {
	ArrayList<Course> subjects;
	ArrayList<Student> studentsToExam;
	Calendar start;
	Calendar end;
	ArrayList<Calendar> workDays = new ArrayList<Calendar>();
	public EpocaNormal(Calendar start, Calendar end){
		this.start = start;
		this.end = end;
		
		while(!this.start.equals(this.end)){
			if (this.start.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || this.start.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
				this.start.add(Calendar.DATE, 1);
			}
			else {
				Calendar wday = (Calendar) this.start.clone();
				this.workDays.add(wday);
				this.start.add(Calendar.DATE, 1);
			}
		}
		if (this.end.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && this.end.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
			this.workDays.add(this.workDays.size(), this.end);
		}
	};
	
	public EpocaNormal(ArrayList<Calendar> wd){
		this.workDays = wd;
	}
	
	public void setSubjects(ArrayList<Course> subjects){
		this.subjects = subjects;
	}
	
	public void setStudents(ArrayList<Student> students){
		this.studentsToExam = students;
	}
	
	public void setStart(Calendar d){
		this.start = d;
	}
	
	public void setEnd(Calendar d){
		this.end = d;
	}
	
	public ArrayList<Course> getSubjects(){
		return this.subjects;
	}
	
	public ArrayList<Student> getStudents(){
		return this.studentsToExam;
	}
	
	public Calendar getStart(){
		return this.start;
	}
	
	public Calendar getEnd(){
		return this.end;
	}
	
}
