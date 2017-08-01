package dev.lab.sdemo;


public class Score {

    private ScoreType scoreType;

    public ScoreType getScoreType() {
        return scoreType;
    }

    public void setScoreType(ScoreType scoreType) {
        this.scoreType = scoreType;
    }


    public static enum ScoreType {HIGH, LOW, MEDIUM}
}
