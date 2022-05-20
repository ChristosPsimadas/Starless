package com.shlad.berserk.Tools.Director;

import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Sprites.Enemy;
import com.shlad.berserk.Sprites.Player;

public class Director
{
    public float directorCredits;
    
    public PlayScreen screen;
    protected Player player;
    
    public float timeInMinutes;
    protected float difficultCoefficient;
    
    protected float stageFactor;
    protected int stagesCompleted;
    
    public Director(PlayScreen screen)
    {
        this.screen = screen;
        this.directorCredits = 0;
        this.player = screen.player;
        this.timeInMinutes = 0;
        this.difficultCoefficient = 0;
        this.stageFactor = 1.15f;
        this.stagesCompleted = 1;
    }
    
    public void updateTime(float deltaTime)
    {
        timeInMinutes += deltaTime / 60;
    }
    
    public void calculateDifficultyCoefficient()
    {
        stageFactor = (float) Math.pow(1.15, stagesCompleted);
        difficultCoefficient = (float) (1 + timeInMinutes * 0.1012) * stageFactor;
    }
    
    public void calcEnemyLevel()
    {
        Enemy.level = 1 + (int) (difficultCoefficient / 0.33);
    }
    
    //public void
}
