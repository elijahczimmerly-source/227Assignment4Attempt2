package hw3;

public class SimpleTest {
    public static void main(String[] args) {
	Picture p = new Picture("photos/bridge-9599215_1920.png", 320);
	PictureIO pio = new PictureIO();
	pio.firstShow(p.getPixels());
	Picture q = p.clockwise();
	PictureIO qio = new PictureIO();
	qio.firstShow(q.getPixels());
    }
}
