package test;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import org.junit.*;
import org.junit.rules.TestName;

import traffic.*;

public class MapTests {

	@Rule public TestName name = new TestName();
	private static Logger logger = Logger.getLogger(MapTests.class.getName());
	private static RoadMap testMap;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		testMap = new RoadMap();
	}
	
	@Before
	public void setUp() throws Exception {
		logger.info("Starting " + name.getMethodName() + "...");
	}

	@Test
	public void testSpaceAhead() {
		testMap.setMap( "$#%\n@ ?\n^!&" );
		
		/*
		 * $#%
		 * @ ?
		 * ^!&
		 */
		
		// Make sure it sees the correct character
		assertEquals(testMap.spaceAhead("LEFT", 1, 1), '@');
		assertEquals(testMap.spaceAhead("RIGHT", 1, 1), '?');
		assertEquals(testMap.spaceAhead("UP", 1, 1), '#');
		assertEquals(testMap.spaceAhead("DOWN", 1, 1), '!');
		
		// Make sure it detects the edge of the map
		assertEquals(testMap.spaceAhead("LEFT", 0, 1), 0);
		assertEquals(testMap.spaceAhead("RIGHT", 2, 1), 0);
		assertEquals(testMap.spaceAhead("UP", 1, 0), 0);
		assertEquals(testMap.spaceAhead("DOWN", 1, 2), 0);
		
		logger.info("Finished " + name.getMethodName() + " successfully!");	
	}

	@Test
	public void testSpaceRight() {
		testMap.setMap( "$#%\n@ ?\n^!&" );
		
		/*
		 * $#%
		 * @ ?
		 * ^!&
		 */
		
		// Make sure it sees the correct character
		assertEquals(testMap.spaceRight("LEFT", 1, 1), '#');
		assertEquals(testMap.spaceRight("RIGHT", 1, 1), '!');
		assertEquals(testMap.spaceRight("UP", 1, 1), '?');
		assertEquals(testMap.spaceRight("DOWN", 1, 1), '@');
		
		// Make sure it detects the edge of the map
		assertEquals(testMap.spaceRight("LEFT", 1, 0), 0);
		assertEquals(testMap.spaceRight("RIGHT", 1, 2), 0);
		assertEquals(testMap.spaceRight("UP", 2, 1), 0);
		assertEquals(testMap.spaceRight("DOWN", 0, 1), 0);
		
		logger.info("Finished " + name.getMethodName() + " successfully!");	
	}
	
	@Test
	public void testSpaceLeft() {
		testMap.setMap( "$#%\n@ ?\n^!&" );
		
		/*
		 * $#%
		 * @ ?
		 * ^!&
		 */
		
		// Make sure it sees the correct character
		assertEquals(testMap.spaceLeft("LEFT", 1, 1), '!');
		assertEquals(testMap.spaceLeft("RIGHT", 1, 1), '#');
		assertEquals(testMap.spaceLeft("UP", 1, 1), '@');
		assertEquals(testMap.spaceLeft("DOWN", 1, 1), '?');
		
		// Make sure it detects the edge of the map
		assertEquals(testMap.spaceLeft("LEFT", 1, 2), 0);
		assertEquals(testMap.spaceLeft("RIGHT", 1, 0), 0);
		assertEquals(testMap.spaceLeft("UP", 0, 1), 0);
		assertEquals(testMap.spaceLeft("DOWN", 2, 1), 0);
		
		logger.info("Finished " + name.getMethodName() + " successfully!");	
	}
	
	@Test
	public void testSpaceBehind() {
		testMap.setMap( "$#%\n@ ?\n^!&" );
		
		/*
		 * $#%
		 * @ ?
		 * ^!&
		 */
		
		// Make sure it sees the correct character
		assertEquals(testMap.spaceBehind("LEFT", 1, 1), '?');
		assertEquals(testMap.spaceBehind("RIGHT", 1, 1), '@');
		assertEquals(testMap.spaceBehind("UP", 1, 1), '!');
		assertEquals(testMap.spaceBehind("DOWN", 1, 1), '#');
		
		// Make sure it detects the edge of the map
		assertEquals(testMap.spaceBehind("LEFT", 2, 1), 0);
		assertEquals(testMap.spaceBehind("RIGHT", 0, 1), 0);
		assertEquals(testMap.spaceBehind("UP", 1, 2), 0);
		assertEquals(testMap.spaceBehind("DOWN", 1, 0), 0);
		
		logger.info("Finished " + name.getMethodName() + " successfully!");	
	}
	
	@Test
	public void testConditionalPath(){
		// Cases with an invalid conditional
		testMap.setMap( "###\n###\n###" );
		assertTrue(testMap.conditionalPath(1, 1) == null);

		testMap.setMap( "#?#\n###\n###" );
		assertTrue(testMap.conditionalPath(1, 1) == null);

		testMap.setMap( "#!#\n###\n###" );
		assertTrue(testMap.conditionalPath(1, 1) == null);
		
		
		// Cases with a valid conditional
		testMap.setMap( "#?#\n##!\n###" );
		assertTrue(testMap.conditionalPath(1, 1)[0] == "UP");
		assertTrue(testMap.conditionalPath(1, 1)[1] == "RIGHT");

		testMap.setMap( "###\n##?\n#!#" );
		assertTrue(testMap.conditionalPath(1, 1)[0] == "RIGHT");
		assertTrue(testMap.conditionalPath(1, 1)[1] == "DOWN");

		testMap.setMap( "###\n!##\n#?#" );
		assertTrue(testMap.conditionalPath(1, 1)[0] == "DOWN");
		assertTrue(testMap.conditionalPath(1, 1)[1] == "LEFT");

		testMap.setMap( "#!#\n?##\n###" );
		assertTrue(testMap.conditionalPath(1, 1)[0] == "LEFT");
		assertTrue(testMap.conditionalPath(1, 1)[1] == "UP");
		
	}
	
	@Test
	public void testToggleLights(){
		testMap.setMap( "G##\n#R#\n##G" );
		testMap.toggleLights();
		assertEquals(testMap.spaceAt(0, 0), 'R');
		assertEquals(testMap.spaceAt(1, 1), 'G');
		assertEquals(testMap.spaceAt(2, 2), 'R');

		testMap.toggleLights();
		assertEquals(testMap.spaceAt(0, 0), 'G');
		assertEquals(testMap.spaceAt(1, 1), 'R');
		assertEquals(testMap.spaceAt(2, 2), 'G');
	}
	
	@Test
	public void testOutputCharAt(){
		// Cases with invalid output construction: (returns null character)
		// Non-space in between exit and output
		testMap.setMap("#C#\n###\n#@#");
		assertEquals(testMap.outputCharAt("UP", 1, 2), 0);
		// Non-space beyond the output
		testMap.setMap("###\n#C#\n# #\n#@#");
		assertEquals(testMap.outputCharAt("UP", 1, 3), 0);
		// Non-space far beyond the output
		testMap.setMap("###\n# #\n# #\n# #\n# #\n#C#\n# #\n#@#");
		assertEquals(testMap.outputCharAt("UP", 1, 7), 0);
		// Exit isn't surrounded by #
		testMap.setMap("#C#\n###\n @#");
		assertEquals(testMap.outputCharAt("UP", 1, 2), 0);
		testMap.setMap(" ##\n@ C\n###");
		assertEquals(testMap.outputCharAt("RIGHT", 0, 1), 0);
		
		// Cases with valid output construction:
		// Close to edge of the map (returns space)
		testMap.setMap("# #\n#@#");
		assertEquals(testMap.outputCharAt("UP", 1, 1), ' ');
		// UP
		testMap.setMap("#C#\n# #\n#@#");
		assertEquals(testMap.outputCharAt("UP", 1, 2), 'C');
		// RIGHT
		testMap.setMap("###\n@ C\n###");
		assertEquals(testMap.outputCharAt("RIGHT", 0, 1), 'C');
		// LEFT
		testMap.setMap("###\nC @\n###");
		assertEquals(testMap.outputCharAt("LEFT", 2, 1), 'C');
		// DOWN
		testMap.setMap("#@#\n# #\n#C#");
		assertEquals(testMap.outputCharAt("DOWN", 1, 0), 'C');
		
	}
	
}
