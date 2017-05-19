package ComponentsTests;

import gamecomponents.Square;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Test for Square class
 */
public class SquareTest  {

    /**
     * check if the toString method and the constructor are
     * working righ
     */
    @Test
    public void testToStringMethode() {
        String expected = "a4";
        Square square = new Square(expected);
        Assert.assertEquals(expected, square.toString());
    }

    /**
     * check if the construcotr is working well
     */
    @Test
    public void testConstrutorWithRightValues(){
        String expected = "a4";
        Square square = new Square(0,3);
        Assert.assertEquals(expected, square.toString());
    }

}
