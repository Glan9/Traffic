package test;

import static org.junit.Assert.*;

import java.util.*;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import traffic.*;

public class CarTests {

	@Rule public TestName name = new TestName();
	private static Logger logger = Logger.getLogger(MapTests.class.getName());
	private static Car testCar;
	private static RoadMap testMap;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		logger.info("Starting " + name.getMethodName() + "...");
	}

	@Test
	public void testTurnRight() {
		testCar = new Car(0, "", 0, 0);
		
		testCar.setDirection("LEFT");
		testCar.turnRight();
		assertEquals(testCar.getDirection(), "UP");
		
		testCar.setDirection("RIGHT");
		testCar.turnRight();
		assertEquals(testCar.getDirection(), "DOWN");
		
		testCar.setDirection("UP");
		testCar.turnRight();
		assertEquals(testCar.getDirection(), "RIGHT");
		
		testCar.setDirection("DOWN");
		testCar.turnRight();
		assertEquals(testCar.getDirection(), "LEFT");

		logger.info("Finished " + name.getMethodName() + " successfully!");	
	}
	
	@Test
	public void testTurnLeft() {
		testCar = new Car(0, "", 0, 0);
		
		testCar.setDirection("LEFT");
		testCar.turnLeft();
		assertEquals(testCar.getDirection(), "DOWN");
		
		testCar.setDirection("RIGHT");
		testCar.turnLeft();
		assertEquals(testCar.getDirection(), "UP");
		
		testCar.setDirection("UP");
		testCar.turnLeft();
		assertEquals(testCar.getDirection(), "LEFT");
		
		testCar.setDirection("DOWN");
		testCar.turnLeft();
		assertEquals(testCar.getDirection(), "RIGHT");

		logger.info("Finished " + name.getMethodName() + " successfully!");	
	}
	
	@Test
	public void testTurnAround() {
		testCar = new Car(0, "", 0, 0);
		
		testCar.setDirection("LEFT");
		testCar.turnAround();
		assertEquals(testCar.getDirection(), "RIGHT");
		
		testCar.setDirection("RIGHT");
		testCar.turnAround();
		assertEquals(testCar.getDirection(), "LEFT");
		
		testCar.setDirection("UP");
		testCar.turnAround();
		assertEquals(testCar.getDirection(), "DOWN");
		
		testCar.setDirection("DOWN");
		testCar.turnAround();
		assertEquals(testCar.getDirection(), "UP");

		logger.info("Finished " + name.getMethodName() + " successfully!");	
	}

	@Test
	public void testMoveForward(){
		testCar = new Car(0, "", 1, 1);
		
		testCar.setDirection("LEFT");
		testCar.moveForward();
		assertEquals(testCar.getX(), 0);
		
		testCar.setDirection("RIGHT");
		testCar.moveForward();
		assertEquals(testCar.getX(), 1);
		
		testCar.setDirection("UP");
		testCar.moveForward();
		assertEquals(testCar.getY(), 0);
		
		testCar.setDirection("DOWN");
		testCar.moveForward();
		assertEquals(testCar.getX(), 1);

		logger.info("Finished " + name.getMethodName() + " successfully!");	
	}
	
	@Test
	public void testGetInput(){
		Stack<Character> inputStack = new Stack<Character>();
		testCar = new Car(0, "", 0, 0);
		
		// Attempt with an empty stack.
		// Should return false.
		assertFalse(testCar.getInput(inputStack));
		assertEquals(testCar.getValue(), 0);
		assertTrue(inputStack.isEmpty());
		
		// Attempt with a stack one letter.
		// Should gain the value 72, clear the stack, and return true.
		inputStack.push('H');
		assertTrue(testCar.getInput(inputStack));
		assertEquals(testCar.getValue(), 72);
		assertTrue(inputStack.isEmpty());
		
		// Attempt with a stack two letters.
		// Should gain the value 72, leave 'G' as the top stack element, and return true.
		inputStack.push('G');
		inputStack.push('H');
		assertTrue(testCar.getInput(inputStack));
		assertEquals(testCar.getValue(), 72);
		assertTrue(inputStack.peek() == 'G');
		
	}
	
	@Test
	public void testGetIntegerInput(){
		Stack<Character> inputStack = new Stack<Character>();
		testCar = new Car(0, "", 0, 0);
		
		// Attempt with an empty stack.
		// Should return false.
		inputStack.clear();
		assertFalse(testCar.getIntegerInput(inputStack));
		assertEquals(testCar.getValue(), 0);
		assertTrue(inputStack.isEmpty());
		
		// Attempt with a stack only containing letters.
		// Should clear the stack and return false.
		inputStack.clear();
		inputStack.push('F');
		assertFalse(testCar.getIntegerInput(inputStack));
		assertEquals(testCar.getValue(), 0);
		assertTrue(inputStack.isEmpty());
		
		// Attempt with a stack containing only numbers.
		// Should gain the value 941, clear the stack, and return true.
		inputStack.clear();
		inputStack.push('1');
		inputStack.push('4');
		inputStack.push('9');
		assertTrue(testCar.getIntegerInput(inputStack));
		assertEquals(testCar.getValue(), 941);
		assertTrue(inputStack.isEmpty());
		
		// Attempt with a stack containing a number following a letter.
		// Should gain the value 941, clear the stack, and return true.
		inputStack.clear();
		inputStack.push('1');
		inputStack.push('4');
		inputStack.push('9');
		inputStack.push('F');
		assertTrue(testCar.getIntegerInput(inputStack));
		assertEquals(testCar.getValue(), 941);
		assertTrue(inputStack.isEmpty());
		
		// Attempt with a stack containing a letter following a number following a letter.
		// Should gain the value 941, leave 'G' as the top stack element, and return true.
		inputStack.clear();
		inputStack.push('G');
		inputStack.push('1');
		inputStack.push('4');
		inputStack.push('9');
		inputStack.push('F');
		assertTrue(testCar.getIntegerInput(inputStack));
		assertEquals(testCar.getValue(), 941);
		assertTrue(inputStack.peek() == 'G');
		
		// Attempt with a stack containing 2 numbers separated by letters.
		// Should gain the value 941, leave 'G' as the top stack element, and return true.
		inputStack.clear();
		inputStack.push('H');
		inputStack.push('5');
		inputStack.push('6');
		inputStack.push('7');
		inputStack.push('G');
		inputStack.push('1');
		inputStack.push('4');
		inputStack.push('9');
		inputStack.push('F');
		assertTrue(testCar.getIntegerInput(inputStack));
		assertEquals(testCar.getValue(), 941);
		assertTrue(inputStack.peek() == 'G');
		
	}
	
	@Test
	public void testStep(){
		RoadMap testMap = new RoadMap();
		testCar = new Car(0, "UP", 1, 1);
		
		// Maps where the car should move forward
		testCar.setDirection("UP");
		String[] forwardTestCases = {
				"# #\n# #\n# #",
				"# #\n   \n# #",
				"# #\nR R\n# #"
		};
		for (String testCase : forwardTestCases){
			testCar.setX(1);
			testCar.setY(1);
			testMap.setMap(testCase);
			System.out.println("Testing forward case: \n"+testMap);
			assertTrue(testCar.step(testMap));
			System.out.println("Car moved to "+testCar.getX()+","+testCar.getY()+" and has direction "+testCar.getDirection()+"\n");
			assertEquals(testCar.getX(), 1);
			assertEquals(testCar.getY(), 0);
		}
		
		// Maps where the car should move right
		testCar.setDirection("UP");
		String[] rightTestCases = {
				"###\n#  \n# #",
				"###\n   \n# #",
				"#R#\n   \n# #",
				"###\nR  \n# #",
				"#R#\nR  \n# #"
		};
		for (String testCase : rightTestCases){
			testCar.setX(1);
			testCar.setY(1);
			testMap.setMap(testCase);
			System.out.println("Testing right case: \n"+testMap);
			assertTrue(testCar.step(testMap));
			System.out.println("Car moved to "+testCar.getX()+","+testCar.getY()+" and has direction "+testCar.getDirection()+"\n");
			assertEquals(testCar.getX(), 2);
			assertEquals(testCar.getY(), 1);
		}
		
		// Maps where the car should move left
		testCar.setDirection("UP");
		String[] leftTestCases = {
				"###\n  #\n# #",
				"###\n  R\n# #",
				"#R#\n  R\n# #"
		};
		for (String testCase : leftTestCases){
			testCar.setX(1);
			testCar.setY(1);
			testMap.setMap(testCase);
			System.out.println("Testing left case: \n"+testMap);
			assertTrue(testCar.step(testMap));
			System.out.println("Car moved to "+testCar.getX()+","+testCar.getY()+" and has direction "+testCar.getDirection()+"\n");
			assertEquals(testCar.getX(), 0);
			assertEquals(testCar.getY(), 1);
		}
		
		// Maps where the car should move left
		testCar.setDirection("UP");
		String[] backTestCases = {
				"###\n# #\n# #"
		};
		for (String testCase : backTestCases){
			testCar.setX(1);
			testCar.setY(1);
			testMap.setMap(testCase);
			System.out.println("Testing back case: \n"+testMap);
			assertTrue(testCar.step(testMap));
			System.out.println("Car moved to "+testCar.getX()+","+testCar.getY()+" and has direction "+testCar.getDirection()+"\n");
			assertEquals(testCar.getX(), 1);
			assertEquals(testCar.getY(), 2);
		}
		
		// Maps where the car should move left
		testCar.setDirection("UP");
		String[] stayTestCases = {
				"###\n# #\n###",
				"###\n# #\n#R#",
				"#R#\n# #\n# #",
				"###\n# R\n# #",
				"###\nR #\n# #",
				"#R#\nR R\n# #"
		};
		for (String testCase : stayTestCases){
			testCar.setX(1);
			testCar.setY(1);
			testMap.setMap(testCase);
			System.out.println("Testing stay case: \n"+testMap);
			assertFalse(testCar.step(testMap));
			System.out.println("Car remained at "+testCar.getX()+","+testCar.getY()+" and has direction "+testCar.getDirection()+"\n");
			assertEquals(testCar.getX(), 1);
			assertEquals(testCar.getY(), 1);
		}
		
		logger.info("Finished " + name.getMethodName() + " successfully!");	
	}
	
	@Test
	public void testPerformOperation(){
		// Cases with a conditional
		String[] directionArray = {"UP", "DOWN"};
		testCar = new Car(1, "", 0, 0);
		testCar.setOperator('+');
		testCar.performOperation(1, directionArray);
		assertEquals(testCar.getDirection(), "UP");
		assertEquals(testCar.getValue(), 1);
		
		testCar = new Car(1, "", 0, 0);
		testCar.setOperator('*');
		testCar.performOperation(0, directionArray);
		assertEquals(testCar.getDirection(), "DOWN");
		assertEquals(testCar.getValue(), 1);
		
		// Cases without a conditional
		// Addition
		testCar = new Car(1, "", 0, 0);
		testCar.setOperator('+');
		testCar.performOperation(1, null);
		assertEquals(testCar.getValue(), 2);
		
		// Subtraction
		testCar = new Car(1, "", 0, 0);
		testCar.setOperator('-');
		testCar.performOperation(2, null);
		assertEquals(testCar.getValue(), -1);

		// Multiplication
		testCar = new Car(1, "", 0, 0);
		testCar.setOperator('*');
		testCar.performOperation(3, null);
		assertEquals(testCar.getValue(), 3);

		// Division
		testCar = new Car(4, "", 0, 0);
		testCar.setOperator('/');
		testCar.performOperation(2, null);
		assertEquals(testCar.getValue(), 2);

		// Exponentiation
		testCar = new Car(2, "", 0, 0);
		testCar.setOperator('^');
		testCar.performOperation(3, null);
		assertEquals(testCar.getValue(), 8);

		// Base-n Logarithm
		testCar = new Car(9, "", 0, 0);
		testCar.setOperator(':');
		testCar.performOperation(3, null);
		assertEquals(testCar.getValue(), 2);
		
		// Equal
		testCar = new Car(3, "", 0, 0);
		testCar.setOperator('=');
		testCar.performOperation(3, null);
		assertEquals(testCar.getValue(), 1);

		// Not Equal
		testCar = new Car(9, "", 0, 0);
		testCar.setOperator('~');
		testCar.performOperation(3, null);
		assertEquals(testCar.getValue(), 1);

		// Less Than
		testCar = new Car(9, "", 0, 0);
		testCar.setOperator('<');
		testCar.performOperation(3, null);
		assertEquals(testCar.getValue(), 0);

		// Greater Than
		testCar = new Car(9, "", 0, 0);
		testCar.setOperator('>');
		testCar.performOperation(3, null);
		assertEquals(testCar.getValue(), 1);
	}

	@Test
	public void testHandleSpace(){
		testMap = new RoadMap();
		testMap.setMap("###\n# #\n###");
				
		Car otherCar = new Car(2, "", 1, 1);
		testCar = new Car(1, "", 1, 1);
		
		ArrayList<Car> cars = new ArrayList<Car>();
		cars.add(otherCar);
		cars.add(testCar);
		
		// Case with another car on the same space but no operator
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getValue(), 1);
		
		// Case with another car on the same space and an operator
		testCar.setOperator('+');
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getValue(), 3);
		assertEquals(testCar.getOperator(), 0);
		
		cars.remove(otherCar); // Remove the other car so as not to interfere with other space handling
		
		// Case on a number
		testMap.setMap("###\n#3#\n###");
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getTempValue(), 3);
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getTempValue(), 33);

		// Case not on a number just after stepping on a number
		testMap.setMap("###\n# #\n###");
		testCar.setOperator('+');
		testCar.setValue(3);
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getValue(), 36); // because it was 3 before, plus the 33 tempValue from before
		assertEquals(testCar.getOperator(), 0);
		assertEquals(testCar.getTempValue(), 0);
		
		// Case on a number with a conditional
		testMap.setMap("#?#\n#3#\n#!#");
		testCar.setOperator('=');
		testCar.setTempValue(3);
		testCar.setValue(3);
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getTempValue(), 0);
		assertEquals(testCar.getDirection(), "UP");
		testCar.setOperator('=');
		testCar.setTempValue(0);
		testCar.setValue(4);
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getTempValue(), 0);
		assertEquals(testCar.getDirection(), "DOWN");
		
		// Case not on a number
		testCar.setOperator('+');
		testCar.setValue(3);
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getValue(), 3);
		assertEquals(testCar.getOperator(), 0);
		assertEquals(testCar.getTempValue(), 0);
		
		// Case on a green light
		testMap.setMap("#R#\n#G#\n###");
		testCar.handleSpace(testMap, cars);
		assertEquals(testMap.getMap()[0], "#G#");
		assertEquals(testMap.getMap()[1], "#R#");
		
		// Cases on an I
		testMap.getInputStack().push('F');
		testMap.setMap("###\n#I#\n###");
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getValue(), (long)'F');
		assertEquals(testCar.handleSpace(testMap, cars), 1);
		
		// Cases on a D
		testMap.getInputStack().push('T');
		testMap.getInputStack().push('1');
		testMap.getInputStack().push('4');
		testMap.getInputStack().push('9');
		testMap.getInputStack().push('F');
		testMap.setMap("###\n#D#\n###");
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getValue(), 941);
		assertEquals(testCar.handleSpace(testMap, cars), 1);
		
		// Case on a + operator
		testMap.setMap("###\n#+#\n###");
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getOperator(), '+');
		
		// Case on a - operator
		testMap.setMap("###\n#-#\n###");
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getOperator(), '-');
		
		// Case on a * operator
		testMap.setMap("###\n#*#\n###");
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getOperator(), '*');
		
		// Case on a / operator
		testMap.setMap("###\n#/#\n###");
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getOperator(), '/');
		
		// Case on a ^ operator
		testMap.setMap("###\n#^#\n###");
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getOperator(), '^');
		
		// Case on a : operator
		testMap.setMap("###\n#:#\n###");
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getOperator(), ':');
		
		// Case on a = operator
		testMap.setMap("###\n#=#\n###");
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getOperator(), '=');
		
		// Case on a ~ operator
		testMap.setMap("###\n#~#\n###");
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getOperator(), '~');
		
		// Case on a < operator
		testMap.setMap("###\n#<#\n###");
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getOperator(), '<');
		
		// Case on a > operator
		testMap.setMap("###\n#>#\n###");
		testCar.handleSpace(testMap, cars);
		assertEquals(testCar.getOperator(), '>');

		
		testCar.setY(2); // Move the car down to allow for output in the map
		
		// Case on a @ street exit
		testMap.setMap(" N \n   \n#@#");
		assertEquals(testCar.handleSpace(testMap, cars), 2);
		
		// Case on a $ street exit
		testMap.setMap(" N \n   \n#$#");
		assertEquals(testCar.handleSpace(testMap, cars), 1);
		
		// Cases on a & street exit
		testCar.setDirection("UP");
		testMap.setMap(" N \n   \n#&#");
		assertEquals(testCar.handleSpace(testMap, cars), 0);
		assertEquals(testCar.getDirection(), "DOWN");
		
		testCar.setDirection("DOWN");
		testMap.setMap(" N \n   \n#&#");
		assertEquals(testCar.handleSpace(testMap, cars), 0);
		assertEquals(testCar.getDirection(), "UP");
		
		testCar.setDirection("RIGHT");
		testMap.setMap(" N \n   \n#&#");
		assertEquals(testCar.handleSpace(testMap, cars), 0);
		assertEquals(testCar.getDirection(), "LEFT");
		
		testCar.setDirection("LEFT");
		testMap.setMap(" N \n   \n#&#");
		assertEquals(testCar.handleSpace(testMap, cars), 0);
		assertEquals(testCar.getDirection(), "RIGHT");
	}
	
}
