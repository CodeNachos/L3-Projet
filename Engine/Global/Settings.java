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

    public static int fullscreen_key = KeyEvent.VK_CAPS_LOCK;
    public static int return_key = KeyEvent.VK_ESCAPE;
    public static int accept_key = KeyEvent.VK_ENTER;
    public static int undo_key = KeyEvent.VK_Q;
    public static int redo_key = KeyEvent.VK_E;
    public static int action1_key = KeyEvent.VK_Q;
    public static int action2_key = KeyEvent.VK_E;
    public static int up_key = KeyEvent.VK_W;
    public static int down_key = KeyEvent.VK_S;
    public static int left_key = KeyEvent.VK_A;
    public static int right_key = KeyEvent.VK_D;
    public static int up_arrow_key = KeyEvent.VK_UP;
    public static int down_arrow_key = KeyEvent.VK_DOWN;
    public static int left_arrow_key = KeyEvent.VK_LEFT;
    public static int right_arrow_key = KeyEvent.VK_RIGHT;
}
