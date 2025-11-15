package hw3;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.io.File;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class PictureIO {
    private JFrame frame;
    private JLabel label;
    private int width;
    private int height;

    public PictureIO() {
        frame = new JFrame();
        label = new JLabel();
    }
    
    public void firstShow(int[][] pic) {
        width = pic[0].length;
        height = pic.length;
        frame.setSize(width, height);
        update(pic);
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void update(int[][] pic) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        if (img != null) {
            for (int y = 0 ; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    img.setRGB(x,  y, pic[y][x]);
                }
            }                
            label.setIcon(new ImageIcon(img));
        }
    }

    public static int[][] getPicture(String filename) {
        File file = new File(filename);
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        int[][] pic = new int[img.getHeight()][img.getWidth()];
        for (int y = 0 ; y < img.getHeight(); ++y) {
            for (int x = 0; x < img.getWidth(); ++x) {
                pic[y][x] = img.getRGB(x, y);
            }
        }
        return pic;
    }

    public static void showPicture(int[][] pic) {
	PictureIO api = new PictureIO();
	api.firstShow(pic);
    }
}
