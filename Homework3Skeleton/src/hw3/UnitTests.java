package hw3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

/*
  import com.spertus.jacquard.common.Visibility;
import com.spertus.jacquard.junittester.GradedTest;
*/

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

/*
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
*/

import java.util.LinkedList;
import java.util.List;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;


@TestMethodOrder(MethodOrderer.MethodName.class)
public class UnitTests {

    // This is just for testing, this picture should not be displayed
    public Picture makePicture(int picSide, int tileSize, int offset) {
        int[][] pic = new int[picSide][picSide];
        for (int i = 0; i < picSide; ++i) {
            for (int j = 0; j < picSide; ++j) {
                pic[i][j] = i * picSide + j + offset;
            }
        }
	return new Picture(pic, tileSize);
    }

    public static void checkPictureState(Picture p, int topleft, int topright, int bottomright, int bottomleft, String operation) {
        int width = p.getWidth();
        int height = p.getHeight();

        if (false) { // change to true to turn on the print statements, but
                     // normally we should not print stuff out unless there
                     // has been a failure.
            System.out.println("After: " + operation);
            System.out.println("Corners should be: " +
                               topleft + " " +
                               topright + " " +
                               bottomright + " " +
                               bottomleft);
            System.out.println(p);
            System.out.println();
        }
        
        assertEquals(topleft, p.getPixel(0, 0),
                     "Top left value is wrong.");
        assertEquals(topright, p.getPixel(0, width-1),
                     "Top right value is wrong.");
        assertEquals(bottomright, p.getPixel(height-1, width-1),
                     "Bottom right value is wrong.");
        assertEquals(bottomleft, p.getPixel(height-1, 0),
                     "Bottom left value is wrong.");
    }

    public static void checkPictureEqual(Picture p, Picture q, String msg) {
        int width = p.getWidth();
        int height = p.getHeight();
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                assertEquals(p.getPixel(i,j), q.getPixel(i,j),
                             msg + " but\n"+
                             "Pixel at (" + i + ", " + j + ") differs in p=\n" + p + "and q=\n" + q);
            }
        }
    }
    
    @Test
    public void test1() {
        Picture p = makePicture(8, 4, 0);
        checkPictureState(p, 0, 7, 63, 56, "s8 t4 no operation");

        p = p.hflip();
        checkPictureState(p, 7, 0, 56, 63, "s8 t4 hflip");
    }
    
    @Test
    public void test2() {
        Picture p = makePicture(8, 4, 0);
        p = p.vflip();
        checkPictureState(p, 56, 63, 7, 0, "s8 t4 vflip");
    }
    
    @Test
    public void test3() {
        Picture p = makePicture(8, 4, 0);
        p = p.clockwise();
        checkPictureState(p, 56, 0, 7, 63, "s8 t4 clockwise");
    }
    
    @Test
    public void test4() {
        Picture p = makePicture(8, 4, 0);
        p = p.anticlockwise();
        checkPictureState(p, 7, 63, 56, 0, "s8 t4 anticlockwise");
    }
    
    @Test
    public void test5() {
        Picture p = makePicture(8, 4, 0);
        p = p.transpose();
        checkPictureState(p, 0, 56, 63, 7, "s8 t4 transpose");
    }

    @Test
    public void test6() {
        Picture p = makePicture(8, 4, 0);
        p = p.getTile(0,0);
        checkPictureState(p, 0, 3, 27, 24, "s8 t4 get tile 0, 0");
    }

    @Test
    public void test7() {
        Picture p = makePicture(8, 4, 0);
        p = p.getTile(0,1);
        checkPictureState(p, 4, 7, 31, 28, "s8 t4 get tile 0, 1");
    }

    @Test
    public void test8() {
        Picture p = makePicture(8, 4, 0);
        p = p.getTile(1,0);
        checkPictureState(p, 32, 35, 59, 56, "s8 t4 get tile 1, 0");
    }

    @Test
    public void test9() {
        Picture p = makePicture(8, 4, 0);
        p = p.getTile(1,1);
        checkPictureState(p, 36, 39, 63, 60, "s8 t4 get tile 1, 1");
    }

    @Test
    public void test10() {
        Picture p = makePicture(8, 4, 0);
        Picture q = p.getTile(1,1);
	p.setTile(0, 0, q);
	Picture r = p.getTile(0,0);
	checkPictureState(r, 36, 39, 63, 60, "s8 t4 get tile 1, 1 set tile 0, 0");
    }

    @Test
    public void test11() {
        Picture p = makePicture(8, 4, 0);
        Picture q = p.getTile(0,1);
	p.setTile(1, 0, q);
	Picture r = p.getTile(1,0);
        checkPictureState(r, 4, 7, 31, 28, "s8 t4 get tile 0, 1 set tile 1, 0");
    }

    @Test
    public void test12() {
        Picture p = makePicture(80, 40, 0);
        Picture q = new Picture(p.drawCursor(0, 0), p.getTileSize());
	q = q.getTile(0, 0);
        checkPictureState(q, p.RED, p.RED, p.RED, p.RED,
			  "s8 t4 drawCursor 0, 0");
    }

    @Test
    public void test13() {
        Picture p = makePicture(9, 3, 0);
        checkPictureState(p, 0, 8, 80, 72, "s9 t3 no operation");

        p = p.hflip();
        checkPictureState(p, 8, 0, 72, 80, "s9 t3 hflip");
    }
    
    @Test
    public void test14() {
        Picture p = makePicture(9, 3, 0);
        p = p.vflip();
        checkPictureState(p, 72, 80, 8, 0, "s9 t3 vflip");
    }
    
    @Test
    public void test15() {
        Picture p = makePicture(9, 3, 0);
        p = p.clockwise();
        checkPictureState(p, 72, 0, 8, 80, "s9 t3 clockwise");
    }
    
    @Test
    public void test16() {
        Picture p = makePicture(9, 3, 0);
        p = p.anticlockwise();
        checkPictureState(p, 8, 80, 72, 0, "s9 t3 anticlockwise");
    }
    
    @Test
    public void test17() {
        Picture p = makePicture(9, 3, 0);
        p = p.transpose();
        checkPictureState(p, 0, 72, 80, 8, "s9 t3 transpose");
    }

    @Test
    public void test18() {
        Picture p = makePicture(9, 3, 0);
        p = p.getTile(0,0);
        checkPictureState(p, 0, 2, 20, 18, "s9 t3 get tile 0, 0");
    }

    @Test
    public void test19() {
        Picture p = makePicture(9, 3, 0);
        p = p.getTile(0,1);
        checkPictureState(p, 3, 5, 23, 21, "s9 t3 get tile 0, 1");
    }

    @Test
    public void test20() {
        Picture p = makePicture(9, 3, 0);
        p = p.getTile(1,0);
        checkPictureState(p, 27, 29, 47, 45, "s9 t3 get tile 1, 0");
    }

    @Test
    public void test21() {
        Picture p = makePicture(9, 3, 0);
        p = p.getTile(1,1);
        checkPictureState(p, 30, 32, 50, 48, "s9 t3 get tile 1, 1");
    }

    // Testing some null op sequences
    
    @Test
    public void test22() {
        Picture p = makePicture(8, 4, 0);
        p = p.clockwise();
        p = p.clockwise();
        p = p.clockwise();
        p = p.clockwise();
        checkPictureState(p, 0, 7, 63, 56, "s8 t4 four clockwise turns");
    }

    @Test
    public void test23() {
        Picture p = makePicture(8, 4, 0);
        p = p.anticlockwise();
        p = p.anticlockwise();
        p = p.anticlockwise();
        p = p.anticlockwise();
        checkPictureState(p, 0, 7, 63, 56, "s8 t4 four anticlockwise turns");
    }

    @Test
    public void test24() {
        Picture p = makePicture(8, 4, 0);
        p = p.hflip();
        p = p.hflip();
        checkPictureState(p, 0, 7, 63, 56, "s8 t4 two hflips");
    }

    @Test
    public void test25() {
        Picture p = makePicture(8, 4, 0);
        p = p.vflip();
        p = p.vflip();
        checkPictureState(p, 0, 7, 63, 56, "s8 t4 two vflips");
    }

    @Test
    public void test26() {
        Picture p = makePicture(8, 4, 0);
        p = p.transpose();
        p = p.transpose();
        checkPictureState(p, 0, 7, 63, 56, "s8 t4 two transposes");
    }

    @Test
    public void test27() {
        Picture p = makePicture(8, 4, 0);
        Picture q = makePicture(8, 4, 0);
        p = p.clockwise();
        p = p.clockwise();
        p = p.clockwise();
        q = q.anticlockwise();
        checkPictureEqual(p, q, "s8 t4 three clockwise should equal one anticlockwise");
    }        

    @Test
    public void test28() {
        Picture p = makePicture(8, 4, 0);
        Picture q = makePicture(8, 4, 0);
        p = p.clockwise();
        p = p.clockwise();
        q = q.anticlockwise();
        q = q.anticlockwise();
        checkPictureEqual(p, q, "s8 t4 two clockwise should equal two anticlockwise");
    }        

    @Test
    public void test29() {
        Picture p = makePicture(8, 4, 0);
        Picture q = makePicture(8, 4, 0);
        p = p.clockwise();
        q = q.anticlockwise();
        q = q.anticlockwise();
        q = q.anticlockwise();
        checkPictureEqual(p, q, "s8 t4 one clockwise should equal three anticlockwise");
    }

    @Test
    public void test30() {
        Picture p = makePicture(8, 4, 0);
        Picture q = makePicture(8, 4, 0);
        p = p.clockwise();
        p = p.clockwise();
        p = p.hflip();
        q = q.vflip();
        checkPictureEqual(p, q, "s8 t4 one vflip should equal two clockwise turns and an hflip");
    }

    @Test
    public void test31() {
        Picture p = makePicture(8, 4, 0);
        Picture q = makePicture(8, 4, 0);
        p = p.clockwise();
        p = p.hflip();
        q = q.transpose();
        checkPictureEqual(p, q, "s8 t4 one transpose should equal one clockwise turn and an hflip");
    }

    @Test
    public void test32() {
        Picture p = makePicture(8, 4, 0);
        Picture q = makePicture(8, 4, 0);
        p = p.hflip();
        p = p.anticlockwise();
        q = q.transpose();
        checkPictureEqual(p, q, "s8 t4 one transpose should equal one hflip and an anti");
    }

    @Test
    public void test33() {
        Picture p = makePicture(8, 4, 0);
        Picture q = makePicture(8, 4, 0);
        p = p.hflip();
        p = p.anticlockwise();
        p = p.anticlockwise();
        q = q.clockwise();
        q = q.clockwise();
        q = q.hflip();
        checkPictureEqual(p, q, "s8 t4 hflip anti anti should equal clock clock hflip");
    }

    @Test
    public void test34() {
        Picture p = makePicture(8, 4, 0);
        Picture q = makePicture(8, 4, 0);
        p = p.hflip();
        p = p.anticlockwise();
        p = p.anticlockwise();
        q = q.clockwise();
        q = q.clockwise();
        q = q.hflip();
        checkPictureEqual(p, q, "s8 t4 hflip anti anti should equal clock clock hflip");
    }

    @Test
    public void test35() {
        Picture p = makePicture(8, 4, 0);
        Picture q = makePicture(8, 4, 0);
        p = p.hflip();
        p = p.vflip();
        q = q.vflip();
        q = q.hflip();
        checkPictureEqual(p, q, "s8 t4 hflip vflip should equal vflip hflip");
    }

    @Test
    public void test36() {
        Picture p = makePicture(8, 4, 0);
        Picture q = makePicture(8, 4, 0);
        p = p.vflip();
        p = p.hflip();
        q = q.clockwise();
        q = q.clockwise();
        checkPictureEqual(p, q, "s8 t4 hflip vflip should equal clockwise clockwise");
    }


    ////////////////////////////////////////////////////////////////////////

    private static void checkTrackerState(Tracker s,
                                       int expectedMixedI,
                                       int expectedMixedJ,
                                       int expectedCorrectI,
                                       int expectedCorrectJ,
                                       int expectedRotations,
                                       boolean expectedIsFlipped,
                                       String message) {
        assertEquals(expectedMixedI, s.getMixedI(),
                     "checkTrackerState " + message +
                     "MixedI do not match");
        assertEquals(expectedMixedJ, s.getMixedJ(),
                     "checkTrackerState " + message +
                     "MixedJ do not match");
        assertEquals(expectedCorrectI, s.getCorrectI(),
                     "checkTrackerState " + message +
                     "CorrectI do not match");
        assertEquals(expectedCorrectJ, s.getCorrectJ(),
                     "checkTrackerState " + message +
                     "CorrectJ do not match");
        assertEquals(expectedRotations, s.getRotations(),
                     "checkTrackerState " + message +
                     "Number of rotations do not match");
        assertEquals(expectedIsFlipped, s.getIsFlipped(),
                     "checkTrackerState " + message +
                     "IsFlipped does not match");
    }

    private static void checkTrackerEquals(Tracker s, Tracker t,
                                   String message) {
        assertEquals(t.getMixedI(), s.getMixedI(),
                     "checkTrackerEquals " + message +
                     "MixedI do not match");
        assertEquals(t.getMixedJ(), s.getMixedJ(),
                     "checkTrackerEquals " + message +
                     "MixedJ do not match");
        assertEquals(t.getCorrectI(), s.getCorrectI(),
                     "checkTrackerEquals " + message +
                     "CorrectI do not match");
        assertEquals(t.getCorrectJ(), s.getCorrectJ(),
                     "checkTrackerEquals " + message +
                     "CorrectJ do not match");
        assertEquals(t.getRotations(), s.getRotations(),
                     "checkTrackerEquals " + message +
                     "Number of rotations do not match");
        assertEquals(t.getIsFlipped(), s.getIsFlipped(),
                     "checkTrackerEquals " + message +
                     "IsFlipped does not match");
    }

    private static Tracker makeTracker(int numRotations, boolean isFlipped) {
        Tracker t = new Tracker(1, 2, 3, 4, 0, false);
        for (int i = 0; i < numRotations; ++i) {
            t.clockwise();
        }
        if (isFlipped) {
            t.hflip();
        }
        return t;
    }
    
    @Test
    public void test37() {
        Tracker s = new Tracker(1, 2, 3, 4, 0, false);
        checkTrackerState(s, 1, 2, 3, 4, 0, false, "no operation");
    }
    
    @Test
    public void test38() {
        Tracker s = new Tracker(1, 2, 3, 4, 0, false);
        s.hflip();
        checkTrackerState(s, 1, 2, 3, 4, 0, true, "one hflip");
    }
    
    @Test
    public void test39() {
        Tracker s = new Tracker(1, 2, 3, 4, 0, false);
        s.vflip();
        checkTrackerState(s, 1, 2, 3, 4, 2, true, "one vflip");
    }
    
    @Test
    public void test40() {
        Tracker s = new Tracker(1, 2, 3, 4, 0, false);
        s.clockwise();
        checkTrackerState(s, 1, 2, 3, 4, 1, false, "one clockwise");
    }
    
    @Test
    public void test41() {
        Tracker s = new Tracker(1, 2, 3, 4, 0, false);
        s.anticlockwise();
        checkTrackerState(s, 1, 2, 3, 4, 3, false, "one anticlockwise");
    }
    
    @Test
    public void test42() {
        Tracker s = new Tracker(1, 2, 3, 4, 0, false);
        s.transpose();
        checkTrackerState(s, 1, 2, 3, 4, 1, true, "one transpose");
    }
    
    // Testing some null op sequences
    
    @Test
    public void test43() {
        Tracker s = makeTracker(0, false);
        s.clockwise();
        s.clockwise();
        s.clockwise();
        s.clockwise();
        checkTrackerEquals(s, makeTracker(0, false), "four clockwise turns");
    }

    @Test
    public void test44() {
        Tracker s = makeTracker(0, false);
        s.anticlockwise();
        s.anticlockwise();
        s.anticlockwise();
        s.anticlockwise();
        checkTrackerEquals(s, makeTracker(0, false), "four anticlockwise turns");
    }

    @Test
    public void test45() {
        Tracker s = makeTracker(0, false);
        s.hflip();
        s.hflip();
        checkTrackerEquals(s, makeTracker(0, false), "two hflips");
    }

    @Test
    public void test46() {
        Tracker s = makeTracker(0, false);
        s.vflip();
        s.vflip();
        checkTrackerEquals(s, makeTracker(0, false), "two vflips");
    }

    @Test
    public void test47() {
        Tracker s = makeTracker(0, false);
        s.transpose();
        s.transpose();
        checkTrackerEquals(s, makeTracker(0, false), "two transposes");
    }

    @Test
    public void test48() {
        Tracker s = makeTracker(0, false);
        Tracker t = makeTracker(0, false);
        s.clockwise();
        s.clockwise();
        s.clockwise();
        t.anticlockwise();
        checkTrackerEquals(s, t, "three clockwise should equal one anticlockwise");
    }

    @Test
    public void test49() {
        Tracker s = makeTracker(0, false);
        Tracker t = makeTracker(0, false);
        s.clockwise();
        s.clockwise();
        t.anticlockwise();
        t.anticlockwise();
        checkTrackerEquals(s, t, "two clockwise should equal two anticlockwise");
    }

    @Test
    public void test50() {
        Tracker s = makeTracker(0, false);
        Tracker t = makeTracker(0, false);
        s.clockwise();
        t.anticlockwise();
        t.anticlockwise();
        t.anticlockwise();
        checkTrackerEquals(s, t, "one clockwise should equal three anticlockwise");
    }

    @Test
    public void test51() {
        Tracker s = makeTracker(0, false);
        Tracker t = makeTracker(0, false);
        s.clockwise();
        s.clockwise();
        s.hflip();
        t.vflip();
        checkTrackerEquals(s, t, "one vflip should equal two clockwise turns and an hflip");
    }

    @Test
    public void test52() {
        Tracker s = makeTracker(0, false);
        Tracker t = makeTracker(0, false);
        s.clockwise();
        s.hflip();
        t.transpose();
        checkTrackerEquals(s, t, "one transpose should equal one clockwise turn and an hflip");
    }

    @Test
    public void test53() {
        Tracker s = makeTracker(0, false);
        Tracker t = makeTracker(0, false);
        s.hflip();
        s.anticlockwise();
        t.transpose();
        checkTrackerEquals(s, t, "one transpose should equal one hflip and an anti");
    }

    @Test
    public void test54() {
        Tracker p = makeTracker(0, false);
        Tracker q = makeTracker(0, false);
        p.hflip();
        p.anticlockwise();
        p.anticlockwise();
        q.clockwise();
        q.clockwise();
        q.hflip();
        checkTrackerEquals(p, q, "hflip anti anti should equal clock clock hflip");
    }

    @Test
    public void test55() {
        Tracker p = makeTracker(0, false);
        Tracker q = makeTracker(0, false);
        p.hflip();
        p.vflip();
        q.vflip();
        q.hflip();
        checkTrackerEquals(p, q, "hflip vflip should equal vflip hflip");
    }

    @Test
    public void test56() {
        Tracker p = makeTracker(0, false);
        Tracker q = makeTracker(0, false);
        p.vflip();
        p.hflip();
        q.clockwise();
        q.clockwise();
        checkTrackerEquals(p, q, "hflip vflip should equal clockwise clockwise");
    }

    @Test
    public void test57() {
	Board b = new Board(4, 5);
	assertEquals(true, b.isSolved(), "Newly constructed Board should be already solved.");
	assertEquals(4, b.getNumRows(), "Should have correct number of rows.");
	assertEquals(5, b.getNumCols(), "Should have correct number of columns.");
	assertEquals(0, b.getCursorI(), "Should have initial CursorI = 0");
	assertEquals(0, b.getCursorJ(), "Should have initial CursorJ = 0");
    }

    @Test
    public void test58() {
	Board b = new Board(4, 5);
	Random rand = new Random();
	int randI = rand.nextInt(4);
	int randJ = rand.nextInt(5);
	Tracker t = b.getTracker(randI, randJ);
	String msg = "getTracker should read the correct tile";
	assertEquals(randI, t.getCorrectI(), msg);
	assertEquals(randJ, t.getCorrectJ(), msg);
    }

    @Test
    public void test59() {
	Board b = new Board(4, 5);
	// Try doing a swap of adjacent tiles
	// lift
	b.moveDown();
	b.moveDown();
	assertEquals(2, b.getCursorI(), "Moving down two steps should work.");
	b.moveRight();
	b.moveRight();
	b.moveRight();
	assertEquals(3, b.getCursorJ(), "Moving right three steps should work.");
	b.swapLeft();
	Tracker p2 = b.getTracker(2, 3);
	Tracker q2 = b.getTracker(2, 2);
	
	// compare
	checkTrackerEquals(new Tracker(2, 2, 2, 2, 0, false), p2, "Swapping left should work.");
	checkTrackerEquals(new Tracker(2, 3, 2, 3, 0, false), q2, "Swapping left should work.");
	assertEquals(2, b.getCursorI(), "Cursor should move during swap.");
	assertEquals(2, b.getCursorJ(), "Cursor should move during swap.");

	assertEquals(false, b.isSolved(), "A board one swap away from solved state should not be solved");
    }

    @Test
    public void test60() {
	Board b = new Board(4, 5);
	// Try doing a swap of adjacent tiles
	// lift
	b.moveDown();
	b.moveDown();
	b.moveRight();
	b.moveRight();
	b.moveRight();
	b.swapRight();
	Tracker p2 = b.getTracker(2, 3);
	Tracker q2 = b.getTracker(2, 4);
	
	// compare
	checkTrackerEquals(new Tracker(2, 4, 2, 4, 0, false), p2, "Swapping right should work.");
	checkTrackerEquals(new Tracker(2, 3, 2, 3, 0, false), q2, "Swapping right should work.");
	assertEquals(2, b.getCursorI(), "Cursor should move during right swap.");
	assertEquals(4, b.getCursorJ(), "Cursor should move during right swap.");
	assertEquals(false, b.isSolved(), "A board one swap away from solved state should not be solved");
    }

    @Test
    public void test61() {
	Board b = new Board(4, 5);
	// Try doing a swap of adjacent tiles
	// lift
	b.moveDown();
	b.moveDown();
	b.moveRight();
	b.moveRight();
	b.moveRight();
	b.swapDown();
	Tracker p2 = b.getTracker(2, 3);
	Tracker q2 = b.getTracker(3, 3);
	
	// compare
	checkTrackerEquals(new Tracker(3, 3, 3, 3, 0, false), p2, "Swapping down should work.");
	checkTrackerEquals(new Tracker(2, 3, 2, 3, 0, false), q2, "Swapping down should work.");
	assertEquals(3, b.getCursorI(), "Cursor should move during down swap.");
	assertEquals(3, b.getCursorJ(), "Cursor should move during down swap.");
	assertEquals(false, b.isSolved(), "A board one swap away from solved state should not be solved");
    }

    @Test
    public void test62() {
	Board b = new Board(4, 5);
	// Try doing a swap of adjacent tiles
	// lift
	b.moveDown();
	b.moveDown();
	b.moveRight();
	b.moveRight();
	b.moveRight();
	b.swapUp();
	Tracker p2 = b.getTracker(2, 3);
	Tracker q2 = b.getTracker(1, 3);
	
	// compare
	checkTrackerEquals(new Tracker(1, 3, 1, 3, 0, false), p2, "Swapping up should work.");
	checkTrackerEquals(new Tracker(2, 3, 2, 3, 0, false), q2, "Swapping up should work.");
	assertEquals(1, b.getCursorI(), "Cursor should move during up swap.");
	assertEquals(3, b.getCursorJ(), "Cursor should move during up swap.");
	assertEquals(false, b.isSolved(), "A board one swap away from solved state should not be solved");
    }

    @Test
    public void test63() {
	Board b = new Board(3, 3);
	// Try doing a swap of adjacent tiles
	// lift
	b.moveDown();
	b.moveDown();
	assertEquals(2, b.getCursorI(), "We should be in bottom row");
	assertEquals(0, b.getCursorJ(), "We should be in bottom row");
	b.moveDown();
	assertEquals(2, b.getCursorI(), "We should still be in bottom row");
	assertEquals(0, b.getCursorJ(), "We should still be in bottom row");

	b.moveRight();
	b.moveRight();
	assertEquals(2, b.getCursorI(), "We should be on right edge");
	assertEquals(2, b.getCursorJ(), "We should be on right edge");
	b.moveRight();
	assertEquals(2, b.getCursorI(), "We should still be on right edge");
	assertEquals(2, b.getCursorJ(), "We should still be on right edge");

	b.moveUp();
	b.moveUp();
	assertEquals(0, b.getCursorI(), "We should be on top edge");
	assertEquals(2, b.getCursorJ(), "We should be on top edge");
	b.moveUp();
	assertEquals(0, b.getCursorI(), "We should still be on top edge");
	assertEquals(2, b.getCursorJ(), "We should still be on top edge");

	b.moveLeft();
	b.moveLeft();
	assertEquals(0, b.getCursorI(), "We should be on left edge");
	assertEquals(0, b.getCursorJ(), "We should be on left edge");
	b.moveLeft();
	assertEquals(0, b.getCursorI(), "We should still be on left edge");
	assertEquals(0, b.getCursorJ(), "We should still be on left edge");
    }
    
    @Test
    public void test64() {
	Board b = new Board(3, 3);
	b.isSolved();
	b.moveDown();
	b.moveRight(); // to get to the middle
	assertEquals(true, b.isSolved(), "A new board should be solved");
    }
    
    @Test
    public void test65() {
	Board b = new Board(3, 3);
	b.isSolved();
	b.moveDown();
	b.moveRight(); // to get to the middle
	b.hflip();
	assertEquals(false, b.isSolved(), "A new board plus hflip should not be solved");
	Tracker t = b.getTracker(1, 1);
	checkTrackerEquals(t, new Tracker(1, 1, 1, 1, 0, true),
			"hflip on board is not correct.");
	t.hflip();
	assertEquals(true, b.isSolved(), "A new board plus cancelled hflip should be solved");
    }
    
    @Test
    public void test66() {
	Board b = new Board(3, 3);
	b.isSolved();
	b.moveDown();
	b.moveRight(); // to get to the middle
	b.vflip();
	assertEquals(false, b.isSolved(), "A new board plus vflip should not be solved");
	Tracker t = b.getTracker(1, 1);
	checkTrackerEquals(t, new Tracker(1, 1, 1, 1, 2, true),
			"vflip on board is not correct.");
	t.vflip();
	assertEquals(true, b.isSolved(), "A new board plus cancelled vflip should be solved");
    }
    
    @Test
    public void test67() {
	Board b = new Board(3, 3);
	b.isSolved();
	b.moveDown();
	b.moveRight(); // to get to the middle
	b.clockwise();
	assertEquals(false, b.isSolved(), "A new board plus clockwise should not be solved");
	Tracker t = b.getTracker(1, 1);
	checkTrackerEquals(t, new Tracker(1, 1, 1, 1, 1, false),
			"clockwise on board is not correct.");
	t.anticlockwise();
	assertEquals(true, b.isSolved(), "A new board plus cancelled clockwise should be solved");
    }
    
    @Test
    public void test68() {
	Board b = new Board(3, 3);
	b.isSolved();
	b.moveDown();
	b.moveRight(); // to get to the middle
	b.anticlockwise();
	assertEquals(false, b.isSolved(), "A new board plus anticlockwise should not be solved");
	Tracker t = b.getTracker(1, 1);
	checkTrackerEquals(t, new Tracker(1, 1, 1, 1, 3, false),
			"anticlockwise on board is not correct.");
	t.clockwise();
	assertEquals(true, b.isSolved(), "A new board plus cancelled anticlockwise should be solved");
    }
    
    @Test
    public void test69() {
	Board b = new Board(3, 3);
	b.isSolved();
	b.moveDown();
	b.moveRight(); // to get to the middle
	b.transpose();
	assertEquals(false, b.isSolved(), "A new board plus transpose should not be solved");
	Tracker t = b.getTracker(1, 1);
	checkTrackerEquals(b.getTracker(1, 1),
			new Tracker(1, 1, 1, 1, 1, true),
			"transpose on board is not correct.");
	t.transpose();
	assertEquals(true, b.isSolved(), "A new board plus cancelled transpose should be solved");
    }

    @Test
    public void test70() {
	Board b = new Board(3, 3);
	b.isSolved();
	b.moveDown();
	b.moveDown();
	b.moveRight();
	b.moveRight();
	b.transpose();
	assertEquals(false, b.isSolved(), "A new board plus transpose should not be solved");
	Tracker t = b.getTracker(2, 2);
	checkTrackerEquals(b.getTracker(2, 2),
			new Tracker(2, 2, 2, 2, 1, true),
			"transpose on board is not correct.");
	t.transpose();
	assertEquals(true, b.isSolved(), "A new board plus cancelled transpose should be solved");
    }
}

