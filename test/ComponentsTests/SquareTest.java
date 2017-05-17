package ComponentsTests;

import gamecomponents.Square;
import junit.framework.Assert;
import org.junit.Test;

public class SquareTest  {

    @Test
    public void testToStringMethode() {
        String expected = "a4";
        Square square = new Square(expected);
        Assert.assertEquals(expected, square.toString());
    }

    @Test
    public void testConstrutorWithRightValues(){
        String expected = "a4";
        Square square = new Square(0,3);
        Assert.assertEquals(expected, square.toString());
    }

}
