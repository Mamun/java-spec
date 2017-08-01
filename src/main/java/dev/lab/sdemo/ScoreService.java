package dev.lab.sdemo;


public class ScoreService {

    public Score getScore(Person person){
        Score score = new Score();
        score.setScoreType(Score.ScoreType.HIGH);
        return score;
    }

}
