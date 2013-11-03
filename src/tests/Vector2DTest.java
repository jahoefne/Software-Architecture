package tests;


import controller.Vector2D;

import org.junit.*;

import java.awt.*;

import static org.junit.Assert.assertTrue;


public class Vector2DTest {

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Testing the Vector2D Class..");
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void constructDirections(){
        assertTrue("Error!",new Vector2D(new Point(0,0),new Point(1,0)).equals(new Vector2D(1,0)));
    }



    @Test
    public void initialSetup() {
        Vector2D.sameDirection(new Vector2D(0,2),new Vector2D(0,4));
        Vector2D.sameDirection(new Vector2D(2,1),new Vector2D(4,2));

    }

    @AfterClass
    public static void afterClass() {
        System.out.println("Successful!");
    }

}
