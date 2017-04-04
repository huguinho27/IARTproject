package ExamSchedule;

import java.util.*;

public class EpocaNormal {
	ArrayList<Course> subjects;
	ArrayList<Student> studentsToExam;
	Calendar start;
	Calendar end;
	ArrayList<Calendar> workDays;
	
	public EpocaNormal(Calendar start, Calendar end){
		this.start = start;
		this.end = end;
		
		while(this.start != this.end){
			if (this.start.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || this.start.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
				this.start.add(Calendar.DATE, 1);
			}
			else {
				this.workDays.add(this.start);
				this.start.add(Calendar.DATE, 1);
			}
		}
		if (this.end.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && this.end.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
			this.workDays.add(this.end);
		}
	};
	
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
