package main;

import java.io.*;

import traffic.*;

import java.util.*;

public class Main {
	
	public static void main (String[] args) throws Exception{
		
		String mapString = "";
		String[] mapArray;
		RoadMap map;
		ArrayList<Car> cars = new ArrayList<Car>();
		
		FileInputStream fin = null;
		
		// Open the file and read its contents
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
		
		// Split up the code into lines
		mapArray = mapString.split(""+(char)10);
		int maxLength = 0;
		for (int s=0; s<mapArray.length; s++){
			mapArray[s] = mapArray[s].replace((char)13, ' ');
			maxLength = Math.max(maxLength, mapArray[s].length());
		}
		// Pad each line with spaces to make it a rectangular grid
		for (int s=0; s<mapArray.length; s++){
			int initialLength = mapArray[s].length();
			for (int i=maxLength; i>initialLength; i--){
				mapArray[s] += " ";
			}
		}
		
		// Search for cars
		for (int y=0; y<mapArray.length; y++){
			for (int x=0; x<mapArray[y].length(); x++){
				String testString = "@$&"; // contains the street exit characters
				char c = mapArray[y].charAt(x);
				if (c >= '0' && c <= '9'){
					if (y > 0 && testString.indexOf(mapArray[y-1].charAt(x)) != -1){
						mapArray[y] = mapArray[y].substring(0, x)+" "+mapArray[y].substring(x+1);
						cars.add(new Car((int)c-48, "DOWN", x, y));
					} else if (y < mapArray.length-1 && testString.indexOf(mapArray[y+1].charAt(x)) != -1){
						mapArray[y] = mapArray[y].substring(0, x)+" "+mapArray[y].substring(x+1);
						cars.add(new Car((int)c-48, "UP", x, y));
					} else if (x > 0 && testString.indexOf(mapArray[y].charAt(x-1)) != -1){
						mapArray[y] = mapArray[y].substring(0, x)+" "+mapArray[y].substring(x+1);
						cars.add(new Car((int)c-48, "RIGHT", x, y));
					} else if (x < mapArray[0].length() && testString.indexOf(mapArray[y].charAt(x+1)) != -1){
						mapArray[y] = mapArray[y].substring(0, x)+" "+mapArray[y].substring(x+1);
						cars.add(new Car((int)c-48, "LEFT", x, y));
					}
				}
			}
		}
		
		// Take input from the user
		System.out.println("Program input: ");
		Scanner scanner = new Scanner(System.in); 
		String input = scanner.nextLine();
		
		/*// Put the input into the input stack
		Stack<Character> inputStack = new Stack<Character>();
		for (int i=input.length()-1; i>=0 ; i--){
			inputStack.push(input.charAt(i));
		}*/
		
		// Initialize the map object
		map = new RoadMap(mapArray, input);
		
		// Read file
		// Split into lines
		// Pad lines
		// Read input
		
		// TODO: Main execution code goes here...
		
		// Make every car step (exit program if every car returned false)
		// Make every car handle space (exit program if it returns 2)
		// Repeat unless there are no cars on the map
		
		boolean programFinished = false;
		while (!programFinished){
			
			// Keeps track of whether a car moved on this step. 
			// If no cars move in a step, the program terminates.
			boolean carsMoved = false;
			
			// Make every car step
			for (Car car : cars){
				carsMoved = car.step(map) || carsMoved; // Become true if the car moved. Never becomes false again.
			}
			// Handle the space every car is on
			for (Car car : cars){
				//System.out.println(car.getX()+", "+car.getY());
				int result = car.handleSpace(map, cars);
				if (result == -1){
					throw new Exception("Something went wrong");
				} else if (result == 1){
					cars.remove(car);
					if (cars.size() == 0){
						programFinished = true;
					}
				} else if (result == 2){
					programFinished = true;
				}
			}
			//System.out.println("");
		}
		
	}
	
}
