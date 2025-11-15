package hw3;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * This runs the puzzle like a game but uses the keyboard
 * for input.
 */
public class KbdUI {

    public static ImageIcon picToImg(int[][] pic, int width, int height) {
	BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	if (img != null) {
	    for (int y = 0 ; y < height; ++y) {
		for (int x = 0; x < width; ++x) {
		    img.setRGB(x,  y, pic[y][x]);
		}
	    }                
	    return new ImageIcon(img);
	} else {
	    System.out.println("The buffered image was null.");
	    return null;
	}
    }
	    
		
    /**
     * This actually runs the puzzle like a game but uses the keyboard (rather
     * clumsily) for input.
     *
     * @param args is unused
     */
    public static void main(String[] args)
    {
	// All the provided photos are 960x640, meaning a tileSize of 320 will
	// yield a 3x2 tile array.  For a much more challenging (and tedious)
	// puzzle, you could reduce this to 160, to try a 6x4 puzzle.
	int tileSize = 320;
	
	String[] filenames = {
	    "bridge-9599215_1920.png",
	    "loaf-2796393_1920.png",
	    "cornucopia-1789664_1920.png",
	    "mountains-5485678_1920.png",
	    "greece-2846879_1920.png",
	    "sea-bay-1546682_1920.png",
	    "hors-doeuvre-2175326_1920.png",
	    "sunset-3354100_1920.png",
	    "lake-6798400_1920.png"
	};
	Scanner scnr = null;
	try {
	    scnr = new Scanner(System.in);
	} catch (Exception e) {
	    System.out.println("Could not open the scanner.");
	    if (scnr != null) {
		scnr.close();
	    }
	    return;
	}
	for (int i = 0; i < filenames.length; ++i) {
	    System.out.println("" + i + ") " + filenames[i]);
	}
	System.out.println("Type the number of the picture you want.");
	// It might be good to ask the user how many tiles they want, but
	// converting that into a matching number that meets constraints
	// is a little bit of work.  Then that determines tileSize instead
	// of setting it arbitrarily.  Also wish I could get a different
	// aspect ratio to give lots of options.  Other than 600 x 300.
	int selection = scnr.nextInt();
	String filename = filenames[selection];
	Puzzle p = new Puzzle("photos/" + filename, tileSize);
	p.scramble();
	int[][] pic = p.getTiledPicture();
        int width = pic[0].length;
        int height = pic.length;

	
	System.out.println("Starting JFrame");
	JLabel label1 = new JLabel();
	// panel1.add(label1);
	final JFrame frame1 = new JFrame("JFrame Keyboard UI for Jigsaw Puzzle");
        frame1.setSize(width, height);
	label1.setIcon(picToImg(pic, width, height));
	frame1.getContentPane().add(label1, BorderLayout.CENTER);
        frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	// frame1.add(panel1);
	frame1.pack();
	String help = """

Move around (right, left, up, down) with [rlud]
Swap with neighbors (right, left, up, down) using [RLUD] 
Turn clockwise and anticlockwise using [ca]
hflip, vflip and transpose with [hvt]
Quit with [Q]	

""";
	System.out.println(help);
	System.out.println("Press Enter to launch window...");
	System.out.println("When the window is launched you need to resize and");
	System.out.println("position it so that it does not overlap eclipse.");
			  
	String input = scnr.nextLine();
	System.out.println("launching window now...");

	java.awt.EventQueue.invokeLater(new Runnable()
	    {
		public void run()
		{
		    frame1.setVisible(true);
		}
	    });
	frame1.toBack();

	while (!p.getBoard().isSolved()) {
	    pic = p.getTiledPicture();

	    label1.setIcon(picToImg(pic, width, height));

	    System.out.println("Type your next move: ");
	    String move = scnr.next();
	    if (move.equals("Q")) {
		break;
	    } else if (move.equals("r")) {
		p.getBoard().moveRight();
	    } else if (move.equals("l")) {
		p.getBoard().moveLeft();
	    } else if (move.equals("u")) {
		p.getBoard().moveUp();
	    } else if (move.equals("d")) {
		p.getBoard().moveDown();
	    } else if (move.equals("R")) {
		p.getBoard().swapRight();
	    } else if (move.equals("L")) {
		p.getBoard().swapLeft();
	    } else if (move.equals("U")) {
		p.getBoard().swapUp();
	    } else if (move.equals("D")) {
		p.getBoard().swapDown();
	    } else if (move.equals("h")) {
		p.getBoard().hflip();
	    } else if (move.equals("v")) {
		p.getBoard().vflip();
	    } else if (move.equals("t")) {
		p.getBoard().transpose();
	    } else if (move.equals("c")) {
		p.getBoard().clockwise();
	    } else if (move.equals("a")) {
		p.getBoard().anticlockwise();
	    } else {
		System.out.println("Unrecognized move: " + move);
	    }
	}
	label1.setIcon(picToImg(p.getPicture().getPixels(), width, height));
	System.out.println("Type Enter again (three times) to end: ");
	String q = scnr.nextLine();
	q = scnr.nextLine();
	q = scnr.nextLine();
	q = scnr.nextLine();
	scnr.close();
	System.exit(0);
    }
}
