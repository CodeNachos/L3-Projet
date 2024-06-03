package Onitama.src.Scenes.GameScene.Scripts.States;

import java.io.Serializable;

import Onitama.src.Scenes.GameScene.Constants.PlayerType;

public class Config implements Serializable {


    public PlayerType redDifficulty;
    public PlayerType blueDifficulty;

    public int firstPlayer;

    public Config(PlayerType redDiff, PlayerType blueDiff, int firstPlayer) {
        this.redDifficulty = redDiff;
        this.blueDifficulty = blueDiff;
        this.firstPlayer = firstPlayer;
    }

    @Override
    public Config clone() {
        return new Config(redDifficulty, blueDifficulty, firstPlayer);
    }

}
