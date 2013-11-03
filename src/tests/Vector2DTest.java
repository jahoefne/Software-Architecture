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
        //o p a b c
        //n - - - d
        //m - x - e
        //l - - - f
        //k j i h g



    //    Point orig= new Point(2,2);
    //    Point dir = new Point(2,4);
    //
    //    Vector2D up = new Vector2D(0,5);
    //    Vector2D test = new Vector2D(orig,dir);

      //  Vector2D.sameDirection(up,test);

        Vector2D.sameDirection(new Vector2D(0,2),new Vector2D(0,4));
        Vector2D.sameDirection(new Vector2D(2,1),new Vector2D(4,2));

    }

    @AfterClass
    public static void afterClass() {
        System.out.println("Successful!");
    }

}
