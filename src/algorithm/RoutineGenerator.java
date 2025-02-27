package algorithm;

import java.time.LocalTime;
import java.util.ArrayList;

import content.Course;
import content.Day;
import content.Slot;
import content.Time;

public class RoutineGenerator {
	
	Course[] courses; 
	Day[] days; 
	Time[] times;
	int breakTimeStartsAtIndex;
	String breakTimeDuration;
	
	public RoutineGenerator(Course[] courses, Day[] days, Time[] times, int breakTimeStartsAtIndex, String breakTimeDuration) {
		this.courses = courses;
		this.days = days;
		this.times = times;
		this.breakTimeStartsAtIndex = breakTimeStartsAtIndex;
		this.breakTimeDuration = breakTimeDuration;
	}

	public ArrayList<String> findDistinctElements(ArrayList<String> arr) {
        ArrayList<String> res = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            int j;
            for (j = 0; j < i; j++) {
            	if (arr.get(i).equals(arr.get(j)))
                    break;
            }      
            if (i == j)
                res.add(arr.get(i));
        }
        return res;
    }
	
	// currentTimeString formant: "09:00", hours format: "1.15", return format: "10.15"
	public String addHoursToATime(String currentTimeString, String hours) {
	    LocalTime time = LocalTime.parse(currentTimeString);
	    String regex = "[.]";
	    String[] arr = hours.split(regex);
	    int minutes = ((Integer.parseInt(arr[0]) * 60) + Integer.parseInt(arr[1]));
	    LocalTime newTime = time.plusMinutes(minutes);
	    String result = newTime.toString();
	    return result;
	}
	
	// time1 format: "09:00", return format: 9.0
	public double changeStringTimeFormatIntoDouble(String time) {
	   	String regex = "[:]";
		String[] arr = time.split(regex);
	    String arr2 = arr[0] + arr[1];
	    double time1DiffFormat = Double.parseDouble(arr2) / 100;
	    return time1DiffFormat;
	}
	
	// Checking if a day and time are conflict free for placing a course there.
	public boolean isConflict(ArrayList<Slot> slots, Course course, String term, int indexOfDay, int indexOfTime) {
		boolean conflict1 = false;
		boolean conflict2 = false;
		boolean conflict3 = false;
		for(int i = 0; i < slots.size(); i++) {
			// If terms and days are equal &&
			// (If new class start time falls inside another class (Slot 2 and 3 of Figure 1) ||
			// If new time class ends inside another class (Slot 1 and 4 of Figure 1) )
			if(slots.get(i).getCourse().getTerm().equals(term) && slots.get(i).getIndexOfDay() == indexOfDay 
					&& ( ((this.changeStringTimeFormatIntoDouble(this.addHoursToATime(times[slots.get(i).getIndexOfTime()].getStartTime(), slots.get(i).getCourse().getDurationOfEachClass())) > this.changeStringTimeFormatIntoDouble(times[indexOfTime].getStartTime())) && (this.changeStringTimeFormatIntoDouble(times[indexOfTime].getStartTime()) >= this.changeStringTimeFormatIntoDouble(times[slots.get(i).getIndexOfTime()].getStartTime())))
					|| ((this.changeStringTimeFormatIntoDouble(this.addHoursToATime(times[indexOfTime].getStartTime(), course.getDurationOfEachClass())) > this.changeStringTimeFormatIntoDouble(times[slots.get(i).getIndexOfTime()].getStartTime())) && (this.changeStringTimeFormatIntoDouble(times[indexOfTime].getStartTime()) < this.changeStringTimeFormatIntoDouble(times[slots.get(i).getIndexOfTime()].getStartTime()))) ) ) {
				conflict1 = true;
			}
			// Checking if the teacher of the course has another class on same day and time regardless of term.
			// If same day and same teacher &&
			// (If new class start time falls inside another class (Slot 2 and 3 of Figure 2) ||
			// If new time class ends inside another class (Slot 1 and 4 of Figure 2) ) 
			if(slots.get(i).getIndexOfDay() == indexOfDay && slots.get(i).getCourse().getTeacher().equals(course.getTeacher())
					&& ( ((this.changeStringTimeFormatIntoDouble(this.addHoursToATime(times[slots.get(i).getIndexOfTime()].getStartTime(), slots.get(i).getCourse().getDurationOfEachClass())) > this.changeStringTimeFormatIntoDouble(times[indexOfTime].getStartTime())) && (this.changeStringTimeFormatIntoDouble(times[indexOfTime].getStartTime()) >= this.changeStringTimeFormatIntoDouble(times[slots.get(i).getIndexOfTime()].getStartTime())))
							|| ((this.changeStringTimeFormatIntoDouble(this.addHoursToATime(times[indexOfTime].getStartTime(), course.getDurationOfEachClass())) > this.changeStringTimeFormatIntoDouble(times[slots.get(i).getIndexOfTime()].getStartTime())) && (this.changeStringTimeFormatIntoDouble(times[indexOfTime].getStartTime()) < this.changeStringTimeFormatIntoDouble(times[slots.get(i).getIndexOfTime()].getStartTime()))) ) ) {
				conflict2 = true;
			}
			// If new class starts inside break time (Slot 2 and 3 of Figure 3) ||
			// If new class ends inside break time (Slot 1 and 4 of Figure 3) ||
			// If new class ends after 5 pm (Slot 1 of Figure 4). 
			if( ((this.changeStringTimeFormatIntoDouble(times[indexOfTime].getStartTime()) >= this.changeStringTimeFormatIntoDouble(times[this.breakTimeStartsAtIndex].getStartTime())) && (this.changeStringTimeFormatIntoDouble(times[indexOfTime].getStartTime()) < this.changeStringTimeFormatIntoDouble(this.addHoursToATime(times[this.breakTimeStartsAtIndex].getStartTime(), this.breakTimeDuration))))
					|| ((this.changeStringTimeFormatIntoDouble(this.addHoursToATime(times[indexOfTime].getStartTime(), course.getDurationOfEachClass())) > this.changeStringTimeFormatIntoDouble(times[this.breakTimeStartsAtIndex].getStartTime())) && ((this.changeStringTimeFormatIntoDouble(times[indexOfTime].getStartTime()) < this.changeStringTimeFormatIntoDouble(times[this.breakTimeStartsAtIndex].getStartTime()))))
					|| changeStringTimeFormatIntoDouble(this.addHoursToATime(times[indexOfTime].getStartTime(), course.getDurationOfEachClass())) > changeStringTimeFormatIntoDouble(this.addHoursToATime(times[times.length - 1].getStartTime(), "0.30"))) {
				conflict3 = true;
			}
		}
		return (conflict1 || conflict2 || conflict3);
	}

	public void generateRoutine() {
		ArrayList<Slot> slots = new ArrayList<Slot>();
		// Finding unique terms
		ArrayList<String> terms = new ArrayList<String>();
		for(int i = 0; i < courses.length; i++) {
			terms.add(courses[i].getTerm());
		}
		ArrayList<String> uniqueTerms = this.findDistinctElements(terms);
		for(int i = 0; i < uniqueTerms.size(); i++) {
			// Courses of current(i) term.
			ArrayList<Course> coursesOfATerm = new ArrayList<Course>();
			for(int j = 0; j < courses.length; j++) {
				if(uniqueTerms.get(i).equals(courses[j].getTerm())) {
					coursesOfATerm.add(courses[j]);
				}
			}
			// Loop for each course of the term (i)
			for(int k = 0; k < coursesOfATerm.size(); k++) {
				//System.out.println(coursesOfATerm.get(k).getCourseNo());
				int numberOfDaysBetweenClasses = 0;
				if(coursesOfATerm.get(k).getNumberOfClassesInAWeek() == 5 || coursesOfATerm.get(k).getNumberOfClassesInAWeek() == 4) {
					numberOfDaysBetweenClasses = 1;
				}
				else if(coursesOfATerm.get(k).getNumberOfClassesInAWeek() == 3) {
					numberOfDaysBetweenClasses = 2;
				}
				else if(coursesOfATerm.get(k).getNumberOfClassesInAWeek() == 2) {
					numberOfDaysBetweenClasses = 4;
				}
//				else if(coursesOfATerm.get(k).getNumberOfClassesInAWeek() == 1) {
//					numberOfDaysBetweenClasses = 0;
//				}
				int indexOfDay = 0;
				int indexOfTime = 0;
				// Loop for each setting up each class of a course.
				for(int l = 0; l < coursesOfATerm.get(k).getNumberOfClassesInAWeek(); l++) {
					while(isConflict(slots, coursesOfATerm.get(k), coursesOfATerm.get(k).getTerm(), indexOfDay, indexOfTime)) {
						indexOfTime++;
						if(indexOfTime == times.length) {
							indexOfDay = (indexOfDay + 1) % days.length;
							indexOfTime = 0;
						}
					}
					slots.add(new Slot(coursesOfATerm.get(k), indexOfDay, indexOfTime));
					if(coursesOfATerm.get(k).getNumberOfClassesInAWeek() != 1) {
						indexOfDay = (indexOfDay + numberOfDaysBetweenClasses) % days.length;
						indexOfTime = 0;
					}
				}
			}
		}
					
//		for(int z = 0; z < slots.size(); z++) {
//			System.out.println(slots.get(z).getCourse().getTerm() + ", " +slots.get(z).getCourse().getCourseNo() + ", " + slots.get(z).getCourse().getType() + ", " + days[slots.get(z).getIndexOfDay()].getName() + ", " + times[slots.get(z).getIndexOfTime()].getStartTime() + " - " + this.addHoursToATime(times[slots.get(z).getIndexOfTime()].getStartTime(), slots.get(z).getCourse().getDurationOfEachClass()) + ", " + slots.get(z).getCourse().getTeacher());
//		}
		for(int z = 0; z < slots.size(); z++) {
			System.out.println(slots.get(z).getCourse().getTerm() + ", " +slots.get(z).getCourse().getCourseNo() + ", " + slots.get(z).getCourse().getTeacher() + ", " + times[slots.get(z).getIndexOfTime()].getStartTime() + " - " + this.addHoursToATime(times[slots.get(z).getIndexOfTime()].getStartTime(), slots.get(z).getCourse().getDurationOfEachClass()) + ", " + slots.get(z).getCourse().getType() + ", " + days[slots.get(z).getIndexOfDay()].getName());
		}
		System.out.println();
		
	}
}
