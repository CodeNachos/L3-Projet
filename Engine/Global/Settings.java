package Engine.Global;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

public class Settings {
    public static Dimension resolution = new Dimension(600,600);
    public static boolean fullscreen = false;
    public static boolean resizable = true;
    public static boolean stretch = true;

    public static int fixfps = 60;

    public static String applicationName = "Game";

    public static int fullscreen_key = KeyEvent.VK_ESCAPE;
    public static int undo_key = KeyEvent.VK_Q;
    public static int redo_key = KeyEvent.VK_E;
}
