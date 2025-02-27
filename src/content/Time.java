package content;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Time {
	
	private int id;
	private String startTime;
	
	public Time(int id, String startTime) {
		this.id = id;
		this.startTime = startTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public static Time[] readTimes() {
		Time[] time = null;
		try {
		      File myObj = new File("src\\content\\Times.txt");
		      Scanner myReader = new Scanner(myObj);
		      int numberOfStartTimes = Integer.parseInt(myReader.nextLine());
		      time = new Time[numberOfStartTimes];
		      int i = 0;
		      while (myReader.hasNextLine()) {
		        String[] data = myReader.nextLine().split(" ");
		        time[i++] = new Time(Integer.parseInt(data[0]), data[1]);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }
		return time;
	}
}
