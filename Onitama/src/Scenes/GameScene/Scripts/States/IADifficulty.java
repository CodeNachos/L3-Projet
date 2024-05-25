package Onitama.src.Scenes.GameScene.Scripts.States;

public enum IADifficulty {
    
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

    public IADifficulty next() {
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

    public IADifficulty previous() {
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
