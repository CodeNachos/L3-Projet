package Onitama.src.Scenes.GameScene;

import Engine.Structures.Vector2D;

public class Constants {
    public static int i = 0;   
    public static final Vector2D RED_THRONE = new Vector2D(2, 4);
    public static final Vector2D BLUE_THRONE = new Vector2D(2, 0);

    public static final int RED_PLAYER = 0; // red
    public static final int BLUE_PLAYER = 1; // blue

        
    public static enum PlayerType {
        
        HUMAN,
        EASY,
        MEDIUM,
        HARD;

        public int deatph() {
            switch (this) {
                case EASY:
                    return 1;
                case MEDIUM:
                    return 3;
                case HARD:
                    return 5;
                default:
                    break;
            }

            return 0;
        }

        @Override
        public String toString() {
            switch (this) {
                case EASY:
                    return "AI Easy";
                case MEDIUM:
                    return "AI Medium";
                case HARD:
                    return "AI Hard";
                case HUMAN:
                    return "Human";
                default:
                    break;
            }

            return "";
        }

        public PlayerType next() {
            switch (this) {
                case EASY:
                    return MEDIUM;
                case MEDIUM:
                    return HARD;
                case HARD:
                    return HUMAN;
                case HUMAN:
                    return EASY;
                default:
                    break;
            }

            return HUMAN;
        }

        public PlayerType previous() {
            switch (this) {
                case EASY:
                    return HUMAN;
                case MEDIUM:
                    return EASY;
                case HARD:
                    return MEDIUM;
                case HUMAN:
                    return HARD;
                default:
                    break;
            }

            return HUMAN;
        }

    }
}
