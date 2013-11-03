package tests;

import static org.junit.Assert.*;
import model.Field;

import org.junit.*;

public class FieldTest {

	Field sut;

	@BeforeClass
	public static void beforeClass() {
		System.out.println("Testing the Field Class..");
	}

	@Before
	public void setUp() throws Exception {
		sut = new Field();
	}

	@Test
	public void initialSetup() {
		for (int i = 0; i < 8; i++) {
			assertTrue(
					"Black Pawn at [0," + i + "] should be -1! Is"
							+ sut.getCell(1, i), sut.getCell(1, i) == -1);
		}
		for (int i = 0; i < 8; i++) {
			assertTrue(
					"White Pawn at [0," + i + "] should be 1! Is"
							+ sut.getCell(6, i), sut.getCell(6, i) == 1);
		}
	}

	@AfterClass
	public static void afterClass() {
		System.out.println("Successful!");
	}

}
