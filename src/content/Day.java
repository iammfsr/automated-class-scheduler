package content;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day {

	private int id;
	private String name;
	
	public Day(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static Day[] readDays() {
		Day[] day = null;
		try {
		      File myObj = new File("src\\content\\Days.txt");
		      Scanner myReader = new Scanner(myObj);
		      int numberOfDays = Integer.parseInt(myReader.nextLine());
		      day = new Day[numberOfDays];
		      int i = 0;
		      while (myReader.hasNextLine()) {
		        String[] data = myReader.nextLine().split(" ");
		        day[i++] = new Day(Integer.parseInt(data[0]), data[1]);
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    }
		return day;
	}
}
