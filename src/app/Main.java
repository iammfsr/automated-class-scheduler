package app;

import algorithm.RoutineGenerator;
import content.Course;
import content.Day;
import content.Time;

public class Main {

	public static void main(String[] args) {
		Course[] courses = Course.readCourses();
		Day[] days = Day.readDays();
		Time[] times = Time.readTimes();
		RoutineGenerator routine = new RoutineGenerator(courses, days, times, 8, "1.0");
		routine.generateRoutine();
	}

}
