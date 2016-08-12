package main;

import java.io.*;

import traffic.*;

import java.util.*;

public class Main {
	
	public static void main (String[] args) throws IOException{
		
		String mapString = "";
		String[] mapArray;
		RoadMap map;
		ArrayList<Car> cars = new ArrayList<Car>();
		
		FileInputStream fin = null;
		
		try {
			fin = new FileInputStream("prime.tfc");
			int c;
			while ((c = fin.read()) != -1) {
				mapString += (char)c;
			}
		} finally {
			if (fin != null) {
				fin.close();
			}
		}
		
		mapArray = mapString.split(""+(char)10);
		int maxLength = 0;
		for (int s=0; s<mapArray.length; s++){
			mapArray[s] = mapArray[s].replace((char)13, ' ');
			maxLength = Math.max(maxLength, mapArray[s].length());
		}
		for (int s=0; s<mapArray.length; s++){
			int initialLength = mapArray[s].length();
			for (int i=maxLength; i>initialLength; i--){
				mapArray[s] += " ";
			}
			System.out.println(mapArray[s]);
		}
		
		
		map = new RoadMap(mapArray);
		
		// Read file
		// Split into lines
		// Pad lines
		// Read input
		
		// TODO: Main execution code goes here...
		
		// Make every car step (exit program if every car returned false)
		// Make every car handle space (exit program if it returns 2)
		// Repeat unless there are no cars on the map
		
		
		
	}
	
}
