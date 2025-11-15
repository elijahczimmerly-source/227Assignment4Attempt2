package hw3;

import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Puzzle is the top level class that contains both a board representing the
 * abstract state of the board, and the picture.  It constructs the actual
 * displayed picture on the fly.
 */
public class Puzzle {
    private Board board;
    private Picture picture;
    private Picture display;
    private Random rand;

    /**
     * Constructs a new puzzle and randomly scrambles it.  For user interation
     * only.  Randomness is not suited for testability.
     *
     * @param filename is the clean picture that is used as the basis for the
     *                 puzzle
     * @param tileSize is the number of pixels on the side of a tile.
     */
    public Puzzle(String filename, int tileSize) {
	picture = new Picture(filename, tileSize);
	display = new Picture(filename, tileSize); // to get a blank
	// lazy way to get a deep copy as a substitute for a writable blank

	board = new Board(picture.getNumTilesHigh(),
			  picture.getNumTilesWide());
	rand = new Random();
	scramble();
    }

    /**
     * Returns a one dimensional array of integers, randomly shuffled.  It
     * contains the numbers 0 to numElts - 1 in a random permutation.
     *
     * @param rand is the generator to use
     * @param numElts is how long the array is
     * @return the shuffled array
     */
    public static int[] shuffle(Random rand, int numElts) {
	    int[] pos = new int[numElts];
	    for (int t = 0; t < numElts; ++t) {
		pos[t] = t;
	    }
	    for (int t = 0; t < numElts - 1; ++t) {
		int s = rand.nextInt(numElts-t);
		int tmp = pos[s];
		pos[s] = pos[numElts - 1 - t];
		pos[numElts - 1 - t] = tmp;
	    }
	    return pos;
    }

    /**
     * This resets the board to a randomly chosen shuffled state, so that it
     * can be used to generate scrambled pictures on the fly.
     */
    public void scramble() {
	int numTiles = picture.getNumTilesHigh() * picture.getNumTilesWide();
	int[] pos = shuffle(rand, numTiles);
	int numTilesWide = picture.getNumTilesWide();
	
	for (int t = 0; t < numTiles ; ++t) {
	    // mixed (current) positions
	    int mixedI = pos[t] / numTilesWide;
	    int mixedJ = pos[t] % numTilesWide;

	    // correct positions
	    	    int correctI = t / numTilesWide;
	    int correctJ = t % numTilesWide;

	    board.setTracker(mixedI, mixedJ,
			  new Tracker(mixedI, mixedJ, correctI, correctJ, 0, false));

	    int flip = rand.nextInt(2);
	    int rots = rand.nextInt(4);
	    for (int c = 0; c < rots; ++c) {
		board.getTracker(mixedI, mixedJ).clockwise();
	    }
	    if (flip == 1) {
		board.getTracker(mixedI, mixedJ).hflip();
	    }
	}
    }

    /**
     * Returns a 2D array containing the tiled picture which is sent to
     * the graphics library for display on screen.
     *
     * @return the 2D array containing pixels ready for display
     */
    public int[][] getTiledPicture() {
	for (int i = 0; i < picture.getNumTilesHigh(); ++i) {
	    for (int j = 0; j < picture.getNumTilesWide(); ++j) {
		Picture tmpTile = picture.getTile(board.getTracker(i, j).getCorrectI(),
						  board.getTracker(i, j).getCorrectJ());
		for (int c = 0; c < board.getTracker(i, j).getRotations(); ++c) {
		    tmpTile = tmpTile.clockwise();
		}
		if (board.getTracker(i, j).getIsFlipped()) {
		    tmpTile = tmpTile.hflip();
		}
		display.setTile(i, j, tmpTile);
	    }
	}
	return display.drawCursor(board.getCursorI(),
				  board.getCursorJ());
    }

    /**
     * Returns the board so that it can be used by UI code
     * @return the Board object contained here
     */
    public Board getBoard() {
	return board;
    }

    /**
     * Returns the picture so that it can be used by UI code
     * @return the Picture object contained here
     */
    public Picture getPicture() {
	return picture;
    }
}
