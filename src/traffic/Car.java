package traffic;

import java.util.*;

public class Car {
	
	private long value;
	private char operator;
	private long tempValue;
	private boolean collectingValue; // If the car is currently collecting a number
	private String direction;
	private int x;
	private int y;
	
	public Car(long value, String direction, int x, int y){
		this.value = value;
		this.direction = direction;
		this.operator = 0;
		this.tempValue = 0;
		this.collectingValue = false;
		this.x = x;
		this.y = y;
	}

	public boolean step (RoadMap map){
		// Given the map, advance the car one step.
		// Return false if the car didn't move or something went wrong.
		// Return true if the car moves.
		
		// Check if the way is clear in front of the car
		if (map.spaceAhead(direction, x, y) != '#'){
			if (map.spaceAhead(direction, x, y) == 'R'){
				if (map.spaceRight(direction, x, y) != '#' && map.spaceRight(direction, x, y) != 'R'){
					turnRight();
					moveForward();
					return true;
				} else if (map.spaceLeft(direction, x, y) != '#' && map.spaceLeft(direction, x, y) != 'R'){
					turnLeft();
					moveForward();
					return true;
				} else {
					return false;
				}
			} else {
				moveForward();
				return true;
			}
		}
		
		// Check if the way is clear to the right of the car
		if (map.spaceRight(direction, x, y) != '#'){
			if (map.spaceRight(direction, x, y) == 'R'){
				if (map.spaceLeft(direction, x, y) != '#' && map.spaceLeft(direction, x, y) != 'R'){
					turnLeft();
					moveForward();
					return true;
				} else {
					return false;
				}
			} else {
				turnRight();
				moveForward();
				return true;
			}
		}
		
		// Check if the way is clear to the left of the car
		if (map.spaceLeft(direction, x, y) != '#'){
			if (map.spaceLeft(direction, x, y) == 'R'){
				return false;
			} else {
				turnLeft();
				moveForward();
				return true;
			}
		}
		
		// Check if the way backward is clear
		if (map.spaceBehind(direction, x, y) != '#'){
			if (map.spaceBehind(direction, x, y) == 'R'){
				return false;
			} else {
				turnAround();
				moveForward();
				return true;
			}
		}
		
		return false;
	}
	
	public int handleSpace(RoadMap map, ArrayList<Car> cars){
		// Process the character at the space the car is occupying.
		// Return -1 if something went wrong.
		// Return 0 if everything went OK.
		// Return 1 if the car should be destroyed.
		// Return 2 if the program should end.
		
		char currentSpace = map.spaceAt(x, y);
		Car otherCar = null;
		boolean newValueSet = false;
		
		for (Car c : cars){
			// Find a car on the same position as this, if there is one
			if (c.getX() == x && c.getY() == y && c != this){
				otherCar = c;
				break;
			}
		}
		if (otherCar != null){
			if (operator != 0){
				performOperation(otherCar.getValue(), map.conditionalPath(x, y));
				newValueSet = true;
			}
		}
		
		// Number literals
		if (currentSpace >= '0' && currentSpace <= '9'){
			if (collectingValue){
				tempValue = (tempValue*10) + (long)(currentSpace-48);
			} else {
				collectingValue = true;
				tempValue = (long)(currentSpace-48);
				//System.out.println("Now reading a number: "+tempValue+", operator is "+operator);
			}
			if (map.conditionalPath(x, y) != null && !newValueSet && operator != 0){
				performOperation(tempValue, map.conditionalPath(x, y));
				collectingValue = false;
			}
		} else {
			if (collectingValue){
				//System.out.println("Just stepped off of a number. tempValue="+tempValue+", operator="+(int)operator);
				if (operator != 0 && !newValueSet){
					performOperation(tempValue, null);
					newValueSet = true;
					tempValue = 0;
				}
				
				collectingValue = false;
			}
			operator = 0;
		}
		
		
		// Street exits
		if (currentSpace == '@'){
			output(map);
			return 2;
		}
		if (currentSpace == '$'){
			output(map);
			return 1;
		}
		if (currentSpace == '&'){
			output(map);
			switch(direction){
			case "UP": direction = "DOWN"; break;
			case "DOWN": direction = "UP"; break;
			case "RIGHT": direction = "LEFT"; break;
			case "LEFT": direction = "RIGHT"; break;
			}
			return 0;
		}
		
		// Input
		if (currentSpace == 'I'){
			if (getInput(map.getInputStack()) == false){
				return 1;
			}
		}
		if (currentSpace == 'D'){
			if (getIntegerInput(map.getInputStack()) == false){
				return 1;
			}
		}
		
		// Green light
		if (currentSpace == 'G'){
			map.toggleLights();
			return 0;
		}
		
		// Operators
		if (currentSpace == '+' ||
			currentSpace == '-' ||
			currentSpace == '*' ||
			currentSpace == '/' ||
			currentSpace == '%' ||
			currentSpace == '^' ||
			currentSpace == '|' ||
			currentSpace == ':' ||
			currentSpace == '=' ||
			currentSpace == '~' ||
			currentSpace == '<' ||
			currentSpace == '>'
			){
			operator = currentSpace;
			//System.out.println("Stepped on "+currentSpace+", new operator is "+operator);
		}
		
		return 0;
	}
	
	public boolean getIntegerInput(Stack<Character> inputStack){
		// Get the next input
		// Return false if there was no integer left in the input to retrieve

		char topElement;
		boolean inInt = false;
		boolean isNegative = false;
		int newValue = 0;
		
		while (!inputStack.isEmpty()){			
			topElement = inputStack.pop();
			
			if (topElement >= '0' && topElement <= '9'){
				// If a digit was taken, append it to the new value
				if (!inInt) inInt = true;
				newValue = (newValue*10) + (int)(topElement-48);
			} else if (topElement == '-' && !isNegative){
				isNegative = true;
			} else {
				// If a non-digit was taken
				if (inInt){
					// If it was already taking a number, then put it back because it's done
					inputStack.push(topElement);
					this.value = (long)newValue;
					return true;
				}
			}
		}
		
		if (isNegative) newValue *= -1;
		
		if (inInt){
			this.value = (long)newValue;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean getInput(Stack<Character> inputStack){
		// Get the next character input
		// Return false if there was no input left
		
		if (inputStack.empty()) return false;
		
		value = (long)inputStack.pop();
		
		return true;
	}
	
	public void output(RoadMap map){
		// Handle doing output
		
		if (map.outputCharAt(direction, x, y) == 'C'){
			if (value > 31 && value < 127) System.out.print((char)value);
		} else if (map.outputCharAt(direction, x, y) == 'N'){
			System.out.print(value);
		} else if (map.outputCharAt(direction, x, y) >= 48 && map.outputCharAt(direction, x, y) <= 58){
			System.out.print((int)(map.outputCharAt(direction, x, y)-48));
		}
	}
	
	public int performOperation(long operationValue, String[] conditional){
		// Perform the operation with the temporary value
		// conditional: if the operation is being performed in a conditional, don't set the value
		// Returns the result of the operation
		//System.out.println(value+" "+operator+" "+operationValue);
		long newValue = 0;
		
		switch (operator){
		case '+': newValue = value+operationValue; break;
		case '-': newValue = value-operationValue; break;
		case '*': newValue = value*operationValue; break;
		case '/': newValue = value/operationValue; break;
		case '%': newValue = value%operationValue; break;
		case '^': newValue = (long)Math.pow(value, operationValue); break;
		case ':': newValue = (long)Math.floor(Math.log(value)/Math.log(operationValue)); break;
		case '=': newValue = value==operationValue?1:0; break;
		case '~': newValue = value!=operationValue?1:0; break;
		case '<': newValue = value< operationValue?1:0; break;
		case '>': newValue = value> operationValue?1:0; break;
		default: // This case should never happen
		}
		operator = 0;
		
		
		if (conditional != null){
			if (newValue != 0){
				direction = conditional[0];
			} else {
				direction = conditional[1];
			}
		} else {
			value = newValue;
		}
		tempValue = 0;
		
		return 0;
	}
	
	public void turnRight(){
		switch(direction){
		case "LEFT": direction = "UP"; break;
		case "RIGHT": direction = "DOWN"; break;
		case "UP": direction = "RIGHT"; break;
		case "DOWN": direction = "LEFT"; break;
		}
	}
	
	public void turnLeft(){
		switch(direction){
		case "LEFT": direction = "DOWN"; break;
		case "RIGHT": direction = "UP"; break;
		case "UP": direction = "LEFT"; break;
		case "DOWN": direction = "RIGHT"; break;
		}
	}
	
	public void turnAround(){
		switch(direction){
		case "LEFT": direction = "RIGHT"; break;
		case "RIGHT": direction = "LEFT"; break;
		case "UP": direction = "DOWN"; break;
		case "DOWN": direction = "UP"; break;
		}
	}
	
	public void moveForward(){
		switch (direction){
		case "LEFT": x--; break;
		case "RIGHT": x++; break;
		case "UP": y--; break;
		case "DOWN": y++; break;
		}
	}
	
	public long getValue() { return value; }
	public void setValue(long value) { this.value = value; }

	public long getTempValue() { return tempValue; }
	public void setTempValue(long tempValue) { this.tempValue = tempValue; }
	
	public char getOperator() {	return operator; }
	public void setOperator(char operator) { this.operator = operator; }

	public String getDirection() { return direction; }
	public void setDirection(String direction) { this.direction = direction; }

	public int getX() { return x; }
	public void setX(int x) { this.x = x; }

	public int getY() { return y; }
	public void setY(int y) { this.y = y; }
	
	
	
}
