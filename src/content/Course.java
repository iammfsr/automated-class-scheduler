package content;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Course {

	private String term;
	private String courseNo;
	private String type;
	private String durationOfEachClass;
	private int numberOfClassesInAWeek;
	private String teacher;
	
	public Course(String term, String courseNo, String type, String durationOfEachClass, int numberOfClassesInAWeek,
			String teacher) {
		this.term = term;
		this.courseNo = courseNo;
		this.type = type;
		this.durationOfEachClass = durationOfEachClass;
		this.numberOfClassesInAWeek = numberOfClassesInAWeek;
		this.teacher = teacher;
	}
	
	public String getTerm() {
		return term;
	}
	
	public void setTerm(String term) {
		this.term = term;
	}
	
	public String getCourseNo() {
		return courseNo;
	}
	
	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDurationOfEachClass() {
		return durationOfEachClass;
	}
	
	public void setDurationOfEachClass(String durationOfEachClass) {
		this.durationOfEachClass = durationOfEachClass;
	}
	
	public int getNumberOfClassesInAWeek() {
		return numberOfClassesInAWeek;
	}
	
	public void setNumberOfClassesInAWeek(int numberOfClassesInAWeek) {
		this.numberOfClassesInAWeek = numberOfClassesInAWeek;
	}
	
	public String getTeacher() {
		return teacher;
	}
	
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
	public static Course[] readCourses() {
		Course[] course = null;
		try {
		      File myObj = new File("src\\content\\Courses.txt");
		      Scanner myReader = new Scanner(myObj);
		      int numberOfCourses = Integer.parseInt(myReader.nextLine());
		      course = new Course[numberOfCourses];
		      int i = 0;
		      while (myReader.hasNextLine()) {
		        String[] data = myReader.nextLine().split(" ");
		        course[i++] = new Course(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), data[5]);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }
		return course;
	}
	
}
