package jump61;

import org.junit.Test;
import ucb.junit.textui;

import static jump61.Side.BLUE;
import static jump61.Side.RED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/** The suite of all JUnit tests for the Jump61 program.
 *  @author
 */
public class UnitTest {

    /** Run the JUnit tests in this package. Add xxxTest.class entries to
     *  the arguments of runClasses to run other JUnit tests. */
    public static void main(String[] ignored) {
        System.exit(textui.runClasses(jump61.BoardTest.class));
    }

    @Test
    public void testNumPieces() {
        Board B = new Board(5);
        assertEquals(25, B.numPieces());
    }

    @Test
    public void testString() {
        Board B = new Board(4);
        String firststring = B.toString();
        B.addSpot(RED, 1, 1);
        String tostring = B.toString();
        String digitalstring = B.toDisplayString();
    }

    @Test
    public void testIsLegal() {
        Board B = new Board(4);
        assertFalse(B.isLegal(RED, 17));
    }

    @Test
    public void testGet() {
        Board B = new Board(4);
        B.get(0);
        B.get(1);
        B.get(2);
        B.get(3);
        B.get(4);
    }

    @Test
    public void testClear() {
        Board B = new Board(4);
        B.addSpot(RED, 1, 1);
        B.addSpot(BLUE, 3, 1);
        B.clear(4);
    }

    @Test
    public void testJump() {
        Board B = new Board(6);
        B.addSpot(RED, 1, 1);
        B.addSpot(BLUE, 2, 1);
        B.addSpot(RED, 1, 1);
        B.addSpot(RED, 2, 1);
        B.addSpot(RED, 2, 2);
        B.addSpot(RED, 2, 2);
        B.addSpot(RED, 2, 2);
    }
}


