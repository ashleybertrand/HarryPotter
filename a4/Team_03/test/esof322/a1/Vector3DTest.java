package esof322.a1;

import esof322.a1.Vector3D;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Megan Weller, Ashley Bertrand, Sheila Tupker
 */
public class Vector3DTest {

    public Vector3DTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    //Ashley
    @Test
    public void testAdd() {
        Vector3D expected = new Vector3D(5, 7, 9);
        Vector3D v1 = new Vector3D(1, 2, 3);
        Vector3D v2 = new Vector3D(4, 5, 6);
        //calling add() on v1
        Vector3D actual = v1.add(v2);
        //assert statement
        assertTrue(actual.equals(expected));
    }

    @Test
    public void testDot() {
        double expected = 32.0;
        Vector3D v1 = new Vector3D(1, 2, 3);
        Vector3D v2 = new Vector3D(4, 5, 6);
        //calling dot() on v1
        double actual = v1.dot(v2);
        //assert statement
        assertEquals(actual, expected, 32.0);
    }

    @Test
    public void testToString() {
        String expected = "{1.0, 2.0, 3.0}";
        Vector3D vector = new Vector3D(1, 2, 3);
        //calling toString() on vector
        String actual = vector.toString();
        //assert statement
        assertEquals(actual, expected);
    }

    //Megan
    //test for equals
    @Test
    public void testEquals() {
        //making an expected Vector
        Vector3D expVector = new Vector3D(2, 4, 6);
        //making vector to test
        Vector3D vector = new Vector3D(1, 2, 3);
        vector = vector.scale(2);
        boolean result = expVector.equals(vector);
        //assert that the test will be true
        assertTrue(result);

    }

    @Test
    public void testScale() {
        Vector3D expVector = new Vector3D(0, 0, 0);
        Vector3D vector = new Vector3D(0, 0, 0);
        vector = vector.scale(2);

        //Thanks!
        assertTrue(expVector.equals(vector));

    }

    //Sheila
    @Test
    //will test the actual output to the expected output
    //of subtracting vector1 from vector2
    public void testSubtract() {
        Vector3D vector1 = new Vector3D(1, 2, 3);
        Vector3D vector2 = new Vector3D(4, 5, 6);
        Vector3D output = vector2.subtract(vector1);
        Vector3D compare = new Vector3D(3, 3, 3);

        //testing expected values to program values
        assertTrue(output.equals(compare));
    }

    @Test
    public void testMagnitude() {
        Vector3D vector1 = new Vector3D(0, 0, 5);
        double output = vector1.magnitude();
        double compare = 5.0;
        assertTrue(output == compare);
    }

    @Test
    public void testNegate() {
        Vector3D vector1 = new Vector3D(1, 2, 3);
        Vector3D output = vector1.negate();
        Vector3D compare = new Vector3D(-1, -2, -3);
        assertTrue(output.equals(compare));
    }
}
