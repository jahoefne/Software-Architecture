package tests;

import controller.MoveValidator;
import org.junit.Before;
import org.junit.Test;

public class MoveValidatorTest {

	MoveValidator sut = new MoveValidator();
	
	@Before
	public void setUp() throws Exception {
        sut = new MoveValidator();
	}

	@Test
	public void test() {
        //o p a b c
        //n - - - d
        //m - x - e
        //l - - - f
        //k j i h g
    }

}
