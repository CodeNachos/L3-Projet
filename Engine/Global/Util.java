package Engine.Global;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Util {
    public static InputStream open(String s) {
		InputStream in = null;
		try {
			in = new FileInputStream("WaffleGame/res/" + s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        return in;
    }
		
    public static Image getImage(String path) {
        InputStream in = open(path);
        try {
            // Chargement d'une image utilisable dans Swing
            return ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
