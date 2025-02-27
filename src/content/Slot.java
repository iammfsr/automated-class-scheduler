package content;

public class Slot {

	private Course course;
	private int indexOfDay;
	private int indexOfTime;
	
	public Slot(Course course, int indexOfDay, int indexOfTime) {
		this.course = course;
		this.indexOfDay = indexOfDay;
		this.indexOfTime = indexOfTime;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getIndexOfDay() {
		return indexOfDay;
	}

	public void setIndexOfDay(int indexOfDay) {
		this.indexOfDay = indexOfDay;
	}

	public int getIndexOfTime() {
		return indexOfTime;
	}

	public void setIndexOfTime(int indexOfTime) {
		this.indexOfTime = indexOfTime;
	}
	
}
