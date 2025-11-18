package hw3;

/**
 * A Picture stores the actual color values of each pixel in a photograph (or
 * a scrambled version of a photograph).  It also stores the length of the
 * side of the square tiles that the picture will be broken up into in a
 * variable called tileSize.
 *
 * It provides functionality for performing "stationary" operations like
 * hflips, vflips, tranposes, clockwise and anticlockwise rotations, but it
 * does these on actual pixels of a real photograph.
 * 
 * @author Elijah Zimmerly
 */
public class Picture {
    /**
     * This is the only color whose numeric equivalent you need.  It is red.
     * It is useful for drawing the cursor.  You should never use the numeric
     * value directly, just use the color name RED.
     */
    public static final int RED = 16711680; // 16711935 is magenta
    
    /**
     * The 2D array of pixel colors represented by ints
     */
    private int[][] given;
    /**
     * The number of pixels on the side of a tile.
     */
    private int tileSize;

    /**
     * Constructs a Picture using the given two dimensional array of integer
     * colors, with the stated tileSize.
     *
     * @param given is the 2D array of pixel colors represented by ints
     * @param tileSize is the number of pixels on the side of a tile.
     */
    public Picture(int[][] given, int tileSize) {
    	this.given = given;
    	this.tileSize = tileSize;
    }

    /**
     * Constructs a Picture with pixel values (colors) taken from the disk
     * image/photo file named here.  The tileSize must be an integer parameter
     * that divides the height of the puzzle image, and also divides the width
     * of the puzzle image, so that there can be a whole number of tiles in
     * both the height and the width of the photo.
     *
     * @param filename is the name of the file to read the image from
     * @param tileSize is the number of pixels on the side of a square tile
     */
    public Picture(String filename, int tileSize) {
	// Do not modify this constructor
        this(PictureIO.getPicture(filename), tileSize);
    }

    /**
     * Converts a picture to a string representation containing the actual
     * numeric values of the pixel colors, provided it is not larger than
     * 12x12 in any dimension.  If it does not meet the size requirements, the
     * string "Too big to print.\n" must be returned instead.

     * The pixels' numbers are printed out row by row by increasing i (and by
     * increasing j within each row).
     *
     * This is useful for testing only.
     */
    public String toString() {
    	if(given.length > 12 || given[0].length > 12) {
    		return ("Too big to print.\n");
    	}
    	
    	
    	String string = "";
    	
    	for (int i = 0; i < given.length; i++) {
    		for (int j = 0; j < given[0].length; j++) {
    			if (given[i][j] <= 9) {
    				string += " ";
    			}
    			string += given[i][j];
    		}
    		string += "\n";
    	}
    	
    	return string;
    }

    /**
     * Get the height of the picture in pixels
     * @return picture height.
     */
    public int getHeight() {
    	return given.length;
    }

    /**
     * Get the width of the picture in pixels
     * @return picture width.
     */
    public int getWidth() {
    	return given[0].length;
    }
        
    /**
     * Get numeric value of one particular pixel
     * @param i is the row index of the pixel
     * @param j is the column index of the pixel
     * @return number for the color of the pixel.
     */
    public int getPixel(int i, int j) {
    	return given[i][j];
    }

    /**
     * Get the entire two dimensional array of numbers which contains
     * the numeric values of the pixel colors.
     * @return pixel array
     */
    public int[][] getPixels() {
    	return given;
    }

    /**
     * Get the tile size provided at Construction time.
     * @return the size of the tiles
     */
    public int getTileSize() {
    	return tileSize;
    }

    /**
     * Get the number of tiles in the height dimension of the photo.  For
     * example if a picture is 300 pixels tall and the tile is 100 pixels on
     * the side, the this function should return 3.
     *
     * @return the number of tiles vertically
     */
    public int getNumTilesHigh() {
    	return this.getHeight() / tileSize;
    }
    
    /**
     * Get the number of tiles in the width dimension of the photo.  For
     * example if a picture is 500 pixels wide and the tile is 100 pixels on
     * the side, the this function should return 5.
     *
     * @return the number of tiles horizontally
     */
    public int getNumTilesWide() {
    	return this.getWidth() / tileSize;
    }

    /**
     * Set the color of a pixel at position i and j by providing the numeric
     * equivalent of its color.
     *
     * @param i is the row index of the pixel
     * @param j is the column index of the pixel
     * @param color represents the desired color
     */
    public void setPixel(int i, int j, int color) {
    	given[i][j] = color;
    }

    /**
     * Creates and returns a new Picture object, which is the result of
     * applying an hflip to this Picture.  This Picture itself should not be
     * modified as a result of this method call.
     *
     * @return the newly created Picture object containing the hflipped Picture
     */
    public Picture hflip() {
    	int[][] flipped = new int[this.getHeight()][this.getWidth()];
    	
    	for (int i = 0; i < this.getHeight(); i++) {
    		for (int j = 0; j < this.getWidth() / 2; j++) {
    			flipped[i][j] = given[i][this.getWidth() - j - 1];
    			flipped[i][this.getWidth() - j - 1] = given[i][j];
    		}
    	}
    	return new Picture(flipped, tileSize);
    }

    /**
     * Creates and returns a new Picture object, which is the result of
     * applying an vflip to this Picture.  This Picture itself should not be
     * modified as a result of this method call.
     *
     * @return the newly created Picture object containing the vflipped Picture
     */
    public Picture vflip() {
    	int[][] flipped = new int[this.getHeight()][this.getWidth()];
    	
    	for (int i = 0; i < this.getHeight() / 2; i++) {
    		for (int j = 0; j < this.getWidth(); j++) {
    			flipped[i][j] = given[this.getHeight() - 1 - i][j];
    			flipped[this.getHeight() - 1- i][j] = given[i][j];
    		}
    	}
    	return new Picture(flipped, tileSize);
    }

    /**
     * Creates and returns a new Picture object, which is the result of
     * applying an transpose to this Picture.  This Picture itself should not
     * be modified as a result of this method call.
     *
     * @return the newly created Picture object containing the transposed Picture
     */
    public Picture transpose() {
    	int[][] transposed = new int[this.getWidth()][this.getHeight()];
    	
    	for (int i = 0; i < this.getHeight(); i++) {
    		for (int j = 0; j < this.getWidth(); j++) {
    			transposed[j][i] = given[i][j];
    		}
    	}
    	return new Picture(transposed, tileSize);
    }

    /**
     * Creates and returns a new Picture object, which is the result of
     * applying an clockwise ninety degree rotation to this Picture.  This
     * Picture itself should not be modified as a result of this method call.
     *
     * @return the newly created Picture object containing the clockwise-rotated Picture
     */
    public Picture clockwise() {
    	return this.transpose().hflip();
    }

    /**
     * Creates and returns a new Picture object, which is the result of
     * applying an anticlockwise ninety degree rotation to this Picture.  This
     * Picture itself should not be modified as a result of this method call.
     *
     * @return the newly created Picture object containing the anticlockwise-rotated Picture
     */
    public Picture anticlockwise() {
    	return this.transpose().vflip();
    }

    /**
     * Given a Picture which represents an entire Puzzle, it copies out a
     * smaller part of the picture corresponding to a single tile, and returns
     * that as a new Picture object.  This Picture itself should not be
     * modified as a result of this method call.  The tile that is extracted
     * should be tileSize by tileSize, and it should be taken from a position
     * whose top left corner has row index (tileI * tileSize), and column
     * index (tileJ * tileSize).  The tileSize of the returned Picture must be
     * the same as the tileSize of the larger picture, meaning it contains
     * just one tile.
     *
     * For example, given a 500 x 300 picture, and a tileSize of 100, if we
     * are asked to getTile(1, 2), then we need to copy the pixels that are in
     * rows 100 to 199, and columns 200 to 299, into a separate new Picture,
     * and return that.  In that new picture, those same pixels should be in
     * rows 0 to 99 and columns 0 to 99.  Make sure to check with assert
     * statements that tileI and tileJ are within reasonable values.  They
     * should not be negative or too large.
     *
     * @param tileI is the tile row index of the tile it should fetch
     * @param tileJ is the tile column index of the tile it should fetch
     * @return the newly created Picture object containing the extracted tile
     */
    public Picture getTile(int tileI, int tileJ) {
    	int[][] tile = new int[tileSize][tileSize];
    	
    	tileI *= tileSize;
    	tileJ *= tileSize;
    	
    	for (int i = 0; i < tileSize; i++) {
    		for (int j = 0; j < tileSize; j++) {
        		tile[i][j] = given[i + tileI][j + tileJ];
        	}
    	}
    	
    	return new Picture(tile, tileSize);
    }

    /**
     * Given a Picture called picTile, which should be the size of a tile (it
     * should be tileSize by tileSize), copy its contents into this picture,
     * *modifying* this picture in the process.  Unlike most other methods in
     * this class, this method does not create a new Picture object, for
     * efficiency reasons, because it is called very often to change a small
     * part of a large picture.
     *
     * For example if tileI is 1 and tileJ is 3, and picTile is 100x100 with a
     * tileSize of 100, its pixels should be copied into pixels with row
     * indices 100 to 199 and column indices 300 to 399 of this picture.
     *
     * @param tileI is the tile row index of the tile it should write over
     * @param tileJ is the tile column index of the tile it should write over
     * @param picTile is the Picture that should be copied into place
     */
    public void setTile(int tileI, int tileJ, Picture picTile) {
    	
    	tileI *= tileSize;
    	tileJ *= tileSize;
    	
    	for (int i = 0; i < tileSize; i++) {
    		for (int j = 0; j < tileSize; j++) {
    			given[i + tileI][j + tileJ] = picTile.getPixel(i, j);
        	}
    	}
    }

    /**
     * Draws a cursor at position tileI and tileJ.  This requires first
     * creating a copy of the current Picture as a 2D int array, and then
     * setting the four corner pixels of the tile at position tileI and tileJ
     * in it to RED.  In addition, other pixels in that same tile should be
     * set to RED in such a way that when you are playing with the puzzle, you
     * can clearly see which tile is the one that has the cursor.  The pattern
     * you choose is entirely up to you, and should reflect some creativity on
     * your part.  Setting the four corner pixels RED is required to test for
     * the location of the cursor.  A new two-dimensional array should be
     * created and returned, and the current Picture should not be modified.
     * 
     * For example if tileI is 1 and tileJ is 2 and the tileSize is 100, then
     * the pixels in the tile with rows 100 to 199 and columns 200 to 299 must
     * have their corner pixels set to RED, and more other pixels should be
     * set to RED in that same tile, to create a visible pattern that reflects
     * your creativity.
     *
     * @param tileI is the tile row index of the tile where the cursor should
     *              be
     * @param tileJ is the tile column index of the tile where the cursor
     *        should be
     * @return the 2D array for the picture including the cursor
     */
    public int[][] drawCursor(int tileI, int tileJ) {
    	int[][] withCursor = new int[this.getHeight()][this.getWidth()];
    	
    	tileI *= tileSize;
    	tileJ *= tileSize;
    	
    	int borderWidth = tileSize / 50 + 1;
    	
    	for (int i = tileI; i < tileI + tileSize; i++) {
    		for (int j = tileJ; j < tileJ + tileSize; j++) {
    			if (i <= tileI + borderWidth || i >= tileI + tileSize - borderWidth || j <= tileJ + borderWidth || j >= tileJ + tileSize - borderWidth) {
    				withCursor[i][j] = RED;
    			}
    			else {
    				withCursor[i][j] = given[i][j];
    			}
    		}
    	}
    	
    	
    	return withCursor;
    	
    	
    }
}
