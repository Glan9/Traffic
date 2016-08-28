package traffic;

import java.util.*;

public class RoadMap {
	
	private int mapWidth;
	private int mapHeight;
	private String[] roadMap;
	private String input;
	
	public RoadMap(String[] roadMap, String input){
		this.input = input;
		this.roadMap = roadMap;
		this.mapWidth = roadMap[0].length();
		this.mapHeight = roadMap.length;
	}
	
	public RoadMap(){
		//inputStack = new Stack<Character>();
	}
	
	public void toggleLights(){
		// Toggle all lights between red and green
		for (int i=0;i<roadMap.length;i++){
			roadMap[i] = roadMap[i].replace('G', (char)0);
			roadMap[i] = roadMap[i].replace('R', 'G');
			roadMap[i] = roadMap[i].replace((char)0, 'R');
		}
	}
	
	public char spaceAt(int x, int y){
		// Returns the character at the specified co-ordinates
		if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) return 0;
		return roadMap[y].charAt(x);
	}
	
	public char spaceAhead(String direction, int x, int y){
		// Returns the character at the space straight ahead of the current space
		int new_x = x;
		int new_y = y;
		
		switch (direction){
		case "LEFT":
			if (x == 0) return 0;
			new_x--;
			break;
		case "RIGHT":
			if (x == mapWidth-1) return 0;
			new_x++;
			break;
		case "UP":
			if (y == 0) return 0;
			new_y--;
			break;
		case "DOWN":
			if (y == mapHeight-1) return 0;
			new_y++;
			break;
		default:
			return 0;
		}
		
		return spaceAt(new_x, new_y);
	}
	
	public char spaceRight(String direction, int x, int y){
		// Returns the character at the space to the right of the current space
		int new_x = x;
		int new_y = y;
		
		switch (direction){
		case "LEFT":
			if (y == 0) return 0;
			new_y--;
			break;
		case "RIGHT":
			if (y == mapHeight-1) return 0;
			new_y++;
			break;
		case "UP":
			if (x == mapWidth-1) return 0;
			new_x++;
			break;
		case "DOWN":
			if (x == 0) return 0;
			new_x--;
			break;
		default:
			return 0;
		}
		
		return spaceAt(new_x, new_y);
	}
	
	public char spaceLeft(String direction, int x, int y){
		// Returns the character at the space to the left of the current space
		int new_x = x;
		int new_y = y;
		
		switch (direction){
		case "LEFT":
			if (y == mapHeight-1) return 0;
			new_y++;
			break;
		case "RIGHT":
			if (y == 0) return 0;
			new_y--;
			break;
		case "UP":
			if (x == 0) return 0;
			new_x--;
			break;
		case "DOWN":
			if (x == mapWidth-1) return 0;
			new_x++;
			break;
		default:
			return 0;
		}
		
		return spaceAt(new_x, new_y);
	}
	
	public char spaceBehind(String direction, int x, int y){
		// Returns the character at the space behind of the current space
		int new_x = x;
		int new_y = y;
		
		switch (direction){
		case "LEFT":
			if (x == mapWidth-1) return 0;
			new_x++;
			break;
		case "RIGHT":
			if (x == 0) return 0;
			new_x--;
			break;
		case "UP":
			if (y == mapHeight-1) return 0;
			new_y++;
			break;
		case "DOWN":
			if (y == 0) return 0;
			new_y--;
			break;
		default:
			return 0;
		}
		
		return spaceAt(new_x, new_y);
	}
	
	public String[] conditionalPath(int x, int y){
		// Determines if the space is a valid conditional intersection
		// Returns the array {pathOnTruthy, pathOnFalsy} if it is a conditional
		// Returns null if it is not a valid conditional
		String truePath = null;
		String falsePath = null;
		
		if (spaceAhead ("UP", x, y) == '?') truePath = "UP";
		if (spaceRight ("UP", x, y) == '?') truePath = "RIGHT";
		if (spaceLeft  ("UP", x, y) == '?') truePath = "LEFT";
		if (spaceBehind("UP", x, y) == '?') truePath = "DOWN";
		
		if (spaceAhead ("UP", x, y) == '!') falsePath = "UP";
		if (spaceRight ("UP", x, y) == '!') falsePath = "RIGHT";
		if (spaceLeft  ("UP", x, y) == '!') falsePath = "LEFT";
		if (spaceBehind("UP", x, y) == '!') falsePath = "DOWN";

		String[] paths = {truePath, falsePath};
		if (truePath != null && falsePath != null) return paths;
		return null;
	}
	
	public char outputCharAt(String direction, int x, int y){
		// Find the output character for a street exit at a specific position
		// If the exit is too close to the edge, return a space (to indicate no output)
		
		// TODO: Make sure the street exit has #'s on either side of it or it is invalid (add test case for this as well)
		
		if (direction == "UP"){
			if (y >= 2){
				if (spaceAt(x-1, y) != '#' || spaceAt(x+1, y) != '#') return 0;
				for (int i=y-1; i>=0; i--){
					if (spaceAt(x, i) != ' ' && i != y-2) return 0;
				}
				return spaceAt(x, y-2);
			} else {
				return ' ';
			}
		}
		if (direction == "DOWN"){
			if (y <= roadMap.length-3){
				if (spaceAt(x-1, y) != '#' || spaceAt(x+1, y) != '#') return 0;
				for (int i=y+1; i<roadMap.length; i++){
					if (spaceAt(x, i) != ' ' && i != y+2) return 0;
				}
				return spaceAt(x, y+2);
			} else {
				return ' ';
			}
		}
		if (direction == "LEFT"){
			if (x >= 2){
				if (spaceAt(x, y-1) != '#' || spaceAt(x, y+1) != '#') return 0;
				for (int i=x-1; i>=0; i--){
					if (spaceAt(i, y) != ' ' && i != x-2) return 0;
				}
				return spaceAt(x-2, y);
			} else {
				return ' ';
			}
		}
		if (direction == "RIGHT"){
			if (x <= roadMap[0].length()-3){
				if (spaceAt(x, y-1) != '#' || spaceAt(x, y+1) != '#') return 0;
				for (int i=x+1; i<roadMap[0].length(); i++){
					if (spaceAt(i, y) != ' ' && i != x+2) return 0;
				}
				return spaceAt(x+2, y);
			} else {
				return ' ';
			}
		}
		
		return 0;
	}
	
	
	public int width() { return mapWidth; }
	public int height() { return mapHeight; }
	

	public String getInput() { return input; }
	public void setInput(String input) { this.input = input; }

	public String toString(){
		String result = "";
		for (int i=0; i<roadMap.length; i++){
			result += roadMap[i]+(i==roadMap.length-1?"":"\n");
		}
		return result;
	}
	
	public void setMap(String mapString){
		// For testing only
		//String newMap[] = mapString.split("\n");
		
		this.roadMap = mapString.split("\n");;
		this.mapWidth = this.roadMap[0].length();
		this.mapHeight = this.roadMap.length;
	}
	public String[] getMap() { return this.roadMap; }
	
}
