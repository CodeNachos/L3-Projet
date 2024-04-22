package Engine.Global;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

public class Settings {
    public static Dimension resolution = new Dimension(600,600);
    public static boolean fullscreen = false;
    public static boolean resizable = false;
    public static boolean stretch = false;

    public static int fixfps = 30;

    public static String applicationName = "Game";

    public static int fullscreen_key = KeyEvent.VK_ESCAPE;
}
